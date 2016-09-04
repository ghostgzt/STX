package rar.unpack;

import rar.ppm.ModelPPM;
import rar.ppm.SubAllocator;
import rar.vm.*;
import java.io.IOException;
import java.util.Vector;

public final class Unpack extends Unpack20 {

    private final ModelPPM ppm = new ModelPPM();
    private int ppmEscChar;
    private RarVM rarVM;
    private Vector filters, prgStack, oldFilterLengths;
    private int lastFilter;
    private boolean tablesRead;
    private byte unpOldTable[];
    private int unpBlockType;
    private long writtenFileSize;
    private boolean fileExtracted;
    private boolean ppmError;
    private int prevLowDist;
    private int lowDistRepCount,csize;
    public static int DBitLengthCounts[] = {
        4, 2, 2, 2, 2, 2, 2, 2, 2, 2,
        2, 2, 2, 2, 2, 2, 14, 0, 12
    };

    public final static int Maxwin = 0x400000;
    public final static int Maxwink = Maxwin - 1;

    public Unpack(ComprDataIO DataIO) {
        rarVM = new RarVM();
        filters = new Vector();
        prgStack = new Vector();
        oldFilterLengths = new Vector();
        unpOldTable = new byte[404];
        unpIO = DataIO;
        window = null;
        suspended = false;
        unpAllBuf = false;
        unpSomeRead = false;
    }

    public void init(byte win[]) {
        if (win != null) {
            window = win;
            System.gc();
        } else if (window == null) {
            window = new byte[Maxwin];
        } else {
            for (int i = window.length - 1; i >= 0; i--) {
                window[i] = 0;
            }
        }
        inAddr = 0;
        unpInitData(false);
    }

    public void doUnpack(int method, boolean solid) throws IOException {
       switch (method) {
            case 15: // '\017'
                unpack15(solid);
                break;

            case 20: // '\024'
            case 26: // '\032'
                unpack20(solid);
                break;

            case 29: // '\035'
            case 36: // '$'
                unpack29(solid);
                break;
        }
    }

    private void unpack29(boolean solid) throws IOException {
        int Ddecode[] = new int[60];
        byte Dbites[] = new byte[60];
        if (Ddecode[1] == 0) {
            int Dist = 0;
            int BitLength = 0;
            int Slot = 0;
            for (int I = 0; I < 19; I++) {
                int count = DBitLengthCounts[I];
                for (int J = 0; J < count; J++) {
                    Ddecode[Slot] = Dist;
                    Dbites[Slot] = (byte) BitLength;
                    Slot++;
                    Dist += 1 << BitLength;
                }
                BitLength++;
            }

        }
        fileExtracted = true;
        if (!suspended) {
            unpInitData(solid);
            if (!unpReadBuf()) {
                return;
            }
            if ((!solid || !tablesRead) && !readTables()) {
                return;
            }
        }
        if (ppmError) {
            return;
        }
        do {
            unpPtr &= Maxwink;
            if (inAddr > readBorder && !unpReadBuf()) {
                break;
            }
            if (((wrPtr - unpPtr) & Maxwink) < 260 && wrPtr != unpPtr) {
                UnpWriteBuf();
                if (writtenFileSize > destUnpSize) {
                    return;
                }
                if (suspended) {
                    fileExtracted = false;
                    return;
                }
            }
            if (unpBlockType == 1) {
                int Ch = ppm.decodeChar();
                if (Ch == -1) {
                    ppmError = true;
                    break;
                }
                if (Ch == ppmEscChar) {
                    int NextCh = ppm.decodeChar();
                    if (NextCh == 0) {
                        if (!readTables()) {
                            break;
                        }
                        continue;
                    }
                    if (NextCh == 2 || NextCh == -1) {
                        break;
                    }
                    if (NextCh == 3) {
                        if (!readVMCodePPM()) {
                            break;
                        }
                        continue;
                    }
                    if (NextCh == 4) {
                        int Distance = 0;
                        int Length = 0;
                        boolean failed = false;
                        for (int I = 0; I < 4 && !failed; I++) {
                            int ch = ppm.decodeChar();
                            if (ch == -1) {
                                failed = true;
                                continue;
                            }
                            if (I == 3) {
                                Length = ch & 0xff;
                            } else {
                                Distance = (Distance << 8) + (ch & 0xff);
                            }
                        }

                        if (failed) {
                            break;
                        }
                        copyString(Length + 32, Distance + 2);
                        continue;
                    }
                    if (NextCh == 5) {
                        int Length = ppm.decodeChar();
                        if (Length == -1) {
                            break;
                        }
                        copyString(Length + 4, 1);
                        continue;
                    }
                }
                
                window[unpPtr++] = (byte) Ch;
                continue;
            }
            int Number = decodeNumber(LD);
            if (Number < 256) {
              
                window[unpPtr++] = (byte) Number;
                continue;
            }
            if (Number >= 271) {
                int Length = LDecode[Number -= 271] + 3;
                int Bits;
                if ((Bits = LBits[Number]) > 0) {
                    Length += getbits() >>> 16 - Bits;
                    addbits(Bits);
                }
                int DistNumber = decodeNumber(DD);
                int Distance = Ddecode[DistNumber] + 1;
                if ((Bits = Dbites[DistNumber]) > 0) {
                    if (DistNumber > 9) {
                        if (Bits > 4) {
                            Distance += (getbits() >>> 20 - Bits) << 4;
                            addbits(Bits - 4);
                        }
                        if (lowDistRepCount > 0) {
                            lowDistRepCount--;
                            Distance += prevLowDist;
                        } else {
                            int LowDist = decodeNumber(LDD);
                            if (LowDist == 16) {
                                lowDistRepCount = 15;
                                Distance += prevLowDist;
                            } else {
                                Distance += LowDist;
                                prevLowDist = LowDist;
                            }
                        }
                    } else {
                        Distance += getbits() >>> 16 - Bits;
                        addbits(Bits);
                    }
                }
                if (Distance >= 8192) {
                    Length++;
                    if ((long) Distance >= 0x40000L) {
                        Length++;
                    }
                }
                insertOldDist(Distance);
                insertLastMatch(Length, Distance);
                copyString(Length, Distance);
                continue;
            }
            if (Number == 256) {
                if (!readEndOfBlock()) {
                    break;
                }
                continue;
            }
            if (Number == 257) {
                if (!readVMCode()) {
                    break;
                }
            } else if (Number == 258) {
                if (lastLength != 0) {
                    copyString(lastLength, lastDist);
                }
            } else if (Number < 263) {
                int DistNum = Number - 259;
                int Distance = oldDist[DistNum];
                for (int I = DistNum; I > 0; I--) {
                    oldDist[I] = oldDist[I - 1];
                }

                oldDist[0] = Distance;
                int LengthNumber = decodeNumber(RD);
                int Length = LDecode[LengthNumber] + 2;
                int Bits;
                if ((Bits = LBits[LengthNumber]) > 0) {
                    Length += getbits() >>> 16 - Bits;
                    addbits(Bits);
                }
                insertLastMatch(Length, Distance);
                copyString(Length, Distance);
            } else if (Number < 272) {
                int Distance = SDDecode[Number -= 263] + 1;
                int Bits;
                if ((Bits = SDBits[Number]) > 0) {
                    Distance += getbits() >>> 16 - Bits;
                    addbits(Bits);
                }
                insertOldDist(Distance);
                insertLastMatch(2, Distance);
                copyString(2, Distance);
            }
        } while (true);
        UnpWriteBuf();
    }

    private void UnpWriteBuf() throws IOException {
        int WrittenBorder = wrPtr;
        int WriteSize = (unpPtr - WrittenBorder) & Maxwink;
        int l = prgStack.size();
        for (int I = 0; I < l; I++) {
            UnpackFilter flt = (UnpackFilter) prgStack.elementAt(I);
            if (flt == null) {
                continue;
            }
            if (flt.isNextWindow()) {
                flt.setNextWindow(false);
                continue;
            }
            int BlockStart = flt.getBlockStart();
            int BlockLength = flt.getBlockLength();
            if (((BlockStart - WrittenBorder) & Maxwink) >= WriteSize) {
                continue;
            }
            if (WrittenBorder != BlockStart) {
                UnpWriteArea(WrittenBorder, BlockStart);
                WrittenBorder = BlockStart;
                WriteSize = (unpPtr - WrittenBorder) & Maxwink;
            }
            if (BlockLength <= WriteSize) {
                int BlockEnd = (BlockStart + BlockLength) & Maxwink;
                if (BlockStart < BlockEnd || BlockEnd == 0) {
                    rarVM.setMemory(0, window, BlockStart, BlockLength);
                } else {
                    int FirstPartLength = Maxwin - BlockStart;
                    rarVM.setMemory(0, window, BlockStart, FirstPartLength);
                    rarVM.setMemory(FirstPartLength, window, 0, BlockEnd);
                }
                VMPreparedProgram ParentPrg = ((UnpackFilter) filters.elementAt(flt.getParentFilter())).getPrg();
                VMPreparedProgram Prg = flt.getPrg();
                if (ParentPrg.getGlobalData().size() > 64) {
                    Prg.getGlobalData().setSize(ParentPrg.getGlobalData().size());
                    int len = ParentPrg.getGlobalData().size() - 64;
                    for (int i = 0; i < len; i++) {
                        Prg.getGlobalData().setElementAt(ParentPrg.getGlobalData().elementAt(64 + i), 64 + i);
                    }

                }
                ExecuteCode(Prg);
                if (Prg.getGlobalData().size() > 64) {
                    if (ParentPrg.getGlobalData().size() < Prg.getGlobalData().size()) {
                        ParentPrg.getGlobalData().setSize(Prg.getGlobalData().size());
                    }
                    int len = Prg.getGlobalData().size() - 64;
                    for (int i = 0; i < len; i++) {
                        ParentPrg.getGlobalData().setElementAt(Prg.getGlobalData().elementAt(64 + i), 64 + i);
                    }

                } else {
                    ParentPrg.getGlobalData().removeAllElements();
                }
                int FilteredDataOffset = Prg.getFilteredDataOffset();
                int FilteredDataSize = Prg.getFilteredDataSize();
                byte FilteredData[] = new byte[FilteredDataSize];
                for (int i = 0; i < FilteredDataSize; i++) {
                    FilteredData[i] = rarVM.getMem()[FilteredDataOffset + i];
                }

                prgStack.setElementAt(null, I);
                do {
                    if (I + 1 >= prgStack.size()) {
                        break;
                    }
                    UnpackFilter NextFilter = (UnpackFilter) prgStack.elementAt(I + 1);
                    if (NextFilter == null || NextFilter.getBlockStart() != BlockStart || NextFilter.getBlockLength() != FilteredDataSize || NextFilter.isNextWindow()) {
                        break;
                    }
                    rarVM.setMemory(0, FilteredData, 0, FilteredDataSize);
                    VMPreparedProgram pPrg = ((UnpackFilter) filters.elementAt(NextFilter.getParentFilter())).getPrg();
                    VMPreparedProgram NextPrg = NextFilter.getPrg();
                    if (pPrg.getGlobalData().size() > 64) {
                        NextPrg.getGlobalData().setSize(pPrg.getGlobalData().size());
                        int len = pPrg.getGlobalData().size() - 64;
                        for (int i = 0; i < len; i++) {
                            NextPrg.getGlobalData().setElementAt(pPrg.getGlobalData().elementAt(64 + i), 64 + i);
                        }

                    }
                    ExecuteCode(NextPrg);
                    if (NextPrg.getGlobalData().size() > 64) {
                        if (pPrg.getGlobalData().size() < NextPrg.getGlobalData().size()) {
                            pPrg.getGlobalData().setSize(NextPrg.getGlobalData().size());
                        }
                        int len = NextPrg.getGlobalData().size() - 64;
                        for (int i = 0; i < len; i++) {
                            pPrg.getGlobalData().setElementAt(NextPrg.getGlobalData().elementAt(64 + i), 64 + i);
                        }

                    } else {
                        pPrg.getGlobalData().removeAllElements();
                    }
                    FilteredDataOffset = NextPrg.getFilteredDataOffset();
                    FilteredDataSize = NextPrg.getFilteredDataSize();
                    FilteredData = new byte[FilteredDataSize];
                    for (int i = 0; i < FilteredDataSize; i++) {
                        FilteredData[i] = ((Byte) NextPrg.getGlobalData().elementAt(FilteredDataOffset + i)).byteValue();
                    }

                    I++;
                    prgStack.setElementAt(null, I);
                } while (true);
                unpIO.unpWrite(FilteredData, 0, FilteredDataSize);
                unpSomeRead = true;
                writtenFileSize += FilteredDataSize;
                WrittenBorder = BlockEnd;
                WriteSize = (unpPtr - WrittenBorder) & Maxwink;
                continue;
            }
            int len = prgStack.size();
            for (int J = I; J < len; J++) {
                UnpackFilter filt = (UnpackFilter) prgStack.elementAt(J);
                if (filt != null && filt.isNextWindow()) {
                    filt.setNextWindow(false);
                }
            }

            wrPtr = WrittenBorder;
            return;
        }

        UnpWriteArea(WrittenBorder, unpPtr);
        wrPtr = unpPtr;
    }

    private void UnpWriteArea(int startPtr, int endPtr)
            throws IOException {
        if (endPtr != startPtr) {
            unpSomeRead = true;
        }
        if (endPtr < startPtr) {
            UnpWriteData(window, startPtr, -startPtr & Maxwink);
            UnpWriteData(window, 0, endPtr);
            unpAllBuf = true;
        } else {
            UnpWriteData(window, startPtr, endPtr - startPtr);
        }
    }

    private void UnpWriteData(byte data[], int offset, int size)
            throws IOException {
        if (writtenFileSize >= destUnpSize) {
            return;
        }
        int writeSize = size;
        long leftToWrite = destUnpSize - writtenFileSize;
        if ((long) writeSize > leftToWrite) {
            writeSize = (int) leftToWrite;
        }
        unpIO.unpWrite(data, offset, writeSize);
        writtenFileSize += size;
    }

    private void insertOldDist(int distance) {
        oldDist[3] = oldDist[2];
        oldDist[2] = oldDist[1];
        oldDist[1] = oldDist[0];
        oldDist[0] = distance;
    }

    private void insertLastMatch(int length, int distance) {
        lastDist = distance;
        lastLength = length;
    }

    private void copyString(int length, int distance) {
        int destPtr = unpPtr - distance;
        if (destPtr >= 0 && destPtr < Maxwin - 260 && unpPtr < Maxwin - 260) {
            for (window[unpPtr++] = window[destPtr++]; --length > 0;) {
                window[unpPtr++] = window[destPtr++];
            }
        } else {
            while (length-- != 0) {
                window[unpPtr] = window[destPtr++ & Maxwink];
                unpPtr = (unpPtr + 1) & Maxwink;
            }
        }
    }

    protected void unpInitData(boolean solid) {
        if (!solid) {
            tablesRead = false;
            oldDist[0] = oldDist[1] = oldDist[2] = oldDist[3] = 0;
            oldDistPtr = 0;
            lastDist = 0;
            lastLength = 0;
            for (int i = unpOldTable.length - 1; i >= 0; i--) {
                unpOldTable[i] = 0;
            }

            unpPtr = 0;
            wrPtr = 0;
            ppmEscChar = 2;
            initFilters();
        }
        InitBitInput();
        ppmError = false;
        writtenFileSize = 0L;
        readTop = 0;
        readBorder = 0;
        unpInitData20(solid);
    }

    private void initFilters() {
        oldFilterLengths.removeAllElements();
        lastFilter = 0;
        filters.removeAllElements();
        prgStack.removeAllElements();
    }

    private boolean readEndOfBlock() throws IOException {
        int BitField = getbits();
        boolean NewFile = false;
        boolean NewTable;
        if ((BitField & 0x8000) != 0) {
            NewTable = true;
            addbits(1);
        } else {
            NewFile = true;
            NewTable = (BitField & 0x4000) != 0;
            addbits(2);
        }
        tablesRead = !NewTable;
        return !NewFile && (!NewTable || readTables());
    }

    private boolean readTables() throws IOException {
        byte bitLength[] = new byte[20];
        byte table[] = new byte[404];
        if (inAddr > readTop - 25 && !unpReadBuf()) {
            return false;
        }
        faddbits(8 - inBit & 7);
        long bitField = getbits() & -1;
        if ((bitField & 32768L) != 0L) {
            unpBlockType = 1;
            return ppm.decodeInit(this, ppmEscChar);
        }
        unpBlockType = 0;
        prevLowDist = 0;
        lowDistRepCount = 0;
        if ((bitField & 16384L) == 0L) {
            for (int i = unpOldTable.length - 1; i >= 0; i--) {
                unpOldTable[i] = 0;
            }

        }
        faddbits(2);
        for (int i = 0; i < 20; i++) {
            int length = getbits() >>> 12 & 0xff;
            faddbits(4);
            if (length == 15) {
                int zeroCount = getbits() >>> 12 & 0xff;
                faddbits(4);
                if (zeroCount == 0) {
                    bitLength[i] = 15;
                    continue;
                }
                for (zeroCount += 2; zeroCount-- > 0 && i < bitLength.length;) {
                    bitLength[i++] = 0;
                }

                i--;
            } else {
                bitLength[i] = (byte) length;
            }
        }

        makeDecodeTables(bitLength, 0, BD, 20);
        for (int i = 0; i < 404;) {
            if (inAddr > readTop - 5 && !unpReadBuf()) {
                return false;
            }
            int Number = decodeNumber(BD);
            if (Number < 16) {
                table[i] = (byte) (Number + unpOldTable[i] & 0xf);
                i++;
            } else if (Number < 18) {
                int N;
                if (Number == 16) {
                    N = (getbits() >>> 13) + 3;
                    faddbits(3);
                } else {
                    N = (getbits() >>> 9) + 11;
                    faddbits(7);
                }
                while (N-- > 0 && i < 404) {
                    table[i] = table[i - 1];
                    i++;
                }
            } else {
                int N;
                if (Number == 18) {
                    N = (getbits() >>> 13) + 3;
                    faddbits(3);
                } else {
                    N = (getbits() >>> 9) + 11;
                    faddbits(7);
                }
                while (N-- > 0 && i < 404) {
                    table[i++] = 0;
                }
            }
        }

        tablesRead = true;
        if (inAddr > readTop) {
            return false;
        }
        makeDecodeTables(table, 0, LD, 299);
        makeDecodeTables(table, 299, DD, 60);
        makeDecodeTables(table, 359, LDD, 17);
        makeDecodeTables(table, 376, RD, 28);
        for (int i = unpOldTable.length - 1; i >= 0; i--) {
            unpOldTable[i] = table[i];
        }

        return true;
    }

    private boolean readVMCode() throws IOException {
        int FirstByte = getbits() >> 8;
        addbits(8);
        int Length = (FirstByte & 7) + 1;
        if (Length == 7) {
            Length = (getbits() >> 8) + 7;
            addbits(8);
        } else if (Length == 8) {
            Length = getbits();
            addbits(16);
        }
        Vector vmCode = new Vector();
        for (int I = 0; I < Length; I++) {
            if (inAddr >= readTop - 1 && !unpReadBuf() && I < Length - 1) {
                return false;
            }
            vmCode.addElement(new Byte((byte) (getbits() >> 8)));
            addbits(8);
        }

        return addVMCode(FirstByte, vmCode);
    }

    private boolean readVMCodePPM()
            throws IOException {
        int FirstByte = ppm.decodeChar();
        if (FirstByte == -1) {
            return false;
        }
        int Length = (FirstByte & 7) + 1;
        if (Length == 7) {
            int B1 = ppm.decodeChar();
            if (B1 == -1) {
                return false;
            }
            Length = B1 + 7;
        } else if (Length == 8) {
            int B1 = ppm.decodeChar();
            if (B1 == -1) {
                return false;
            }
            int B2 = ppm.decodeChar();
            if (B2 == -1) {
                return false;
            }
            Length = B1 * 256 + B2;
        }
        Vector vmCode = new Vector();
        for (int I = 0; I < Length; I++) {
            int Ch = ppm.decodeChar();
            if (Ch == -1) {
                return false;
            }
            vmCode.addElement(new Byte((byte) Ch));
        }

        return addVMCode(FirstByte, vmCode);
    }

    private boolean addVMCode(int firstByte, Vector vmCode) {
        BitInput Inp = new BitInput();
        Inp.InitBitInput();
        int len = Math.min(32768, vmCode.size());
        for (int i = 0; i < len; i++) {
            Inp.getInBuf()[i] = ((Byte) vmCode.elementAt(i)).byteValue();
        }

        rarVM.init();
        int FiltPos;
        if ((firstByte & 0x80) != 0) {
            FiltPos = RarVM.ReadData(Inp);
            if (FiltPos == 0) {
                initFilters();
            } else {
                FiltPos--;
            }
        } else {
            FiltPos = lastFilter;
        }
        if (FiltPos > filters.size() || FiltPos > oldFilterLengths.size()) {
            return false;
        }
        lastFilter = FiltPos;
        boolean NewFilter = FiltPos == filters.size();
        UnpackFilter StackFilter = new UnpackFilter();
        UnpackFilter Filter;
        if (NewFilter) {
            if (FiltPos > 1024) {
                return false;
            }
            Filter = new UnpackFilter();
            filters.addElement(Filter);
            StackFilter.setParentFilter(filters.size() - 1);
            oldFilterLengths.addElement(new Integer(0));
            Filter.setExecCount(0);
        } else {
            Filter = (UnpackFilter) filters.elementAt(FiltPos);
            StackFilter.setParentFilter(FiltPos);
            Filter.setExecCount(Filter.getExecCount() + 1);
        }
        prgStack.addElement(StackFilter);
        StackFilter.setExecCount(Filter.getExecCount());
        int BlockStart = RarVM.ReadData(Inp);
        if ((firstByte & 0x40) != 0) {
            BlockStart += 258;
        }
        StackFilter.setBlockStart(BlockStart + unpPtr & Maxwink);
        if ((firstByte & 0x20) != 0) {
            StackFilter.setBlockLength(RarVM.ReadData(Inp));
        } else {
            StackFilter.setBlockLength(FiltPos >= oldFilterLengths.size() ? 0 : ((Integer) oldFilterLengths.elementAt(FiltPos)).intValue());
        }
        StackFilter.setNextWindow(wrPtr != unpPtr && ((wrPtr - unpPtr) & Maxwink) <= BlockStart);
        oldFilterLengths.setElementAt(new Integer(StackFilter.getBlockLength()), FiltPos);
        int get[] = StackFilter.getPrg().getInitR();
        for (int i = get.length - 1; i >= 0; i--) {
            get[i] = 0;
        }

        StackFilter.getPrg().getInitR()[3] = 0x3c000;
        StackFilter.getPrg().getInitR()[4] = StackFilter.getBlockLength();
        StackFilter.getPrg().getInitR()[5] = StackFilter.getExecCount();
        if ((firstByte & 0x10) != 0) {
            int InitMask = Inp.getbits() >>> 9;
            Inp.faddbits(7);
            for (int I = 0; I < 7; I++) {
                if ((InitMask & 1 << I) != 0) {
                    StackFilter.getPrg().getInitR()[I] = RarVM.ReadData(Inp);
                }
            }

        }
        if (NewFilter) {
            int VMCodeSize = RarVM.ReadData(Inp);
            if (VMCodeSize >= 0x10000 || VMCodeSize == 0) {
                return false;
            }
            byte VMCode[] = new byte[VMCodeSize];
            for (int I = 0; I < VMCodeSize; I++) {
                if (Inp.Overflow(3)) {
                    return false;
                }
                VMCode[I] = (byte) (Inp.getbits() >> 8);
                Inp.faddbits(8);
            }

            rarVM.prepare(VMCode, VMCodeSize, Filter.getPrg());
        }
        StackFilter.getPrg().setAltCmd(Filter.getPrg().getCmd());
        StackFilter.getPrg().setCmdCount(Filter.getPrg().getCmdCount());
        int StaticDataSize = Filter.getPrg().getStaticData().size();
        if (StaticDataSize > 0 && StaticDataSize < 8192) {
            StackFilter.getPrg().setStaticData(Filter.getPrg().getStaticData());
        }
        if (StackFilter.getPrg().getGlobalData().size() < 64) {
            StackFilter.getPrg().getGlobalData().removeAllElements();
            StackFilter.getPrg().getGlobalData().setSize(64);
        }
        Vector globalData = StackFilter.getPrg().getGlobalData();
        for (int I = 0; I < 7; I++) {
            rarVM.setLowEndianValue(globalData, I * 4, StackFilter.getPrg().getInitR()[I]);
        }

        rarVM.setLowEndianValue(globalData, 28, StackFilter.getBlockLength());
        rarVM.setLowEndianValue(globalData, 32, 0);
        rarVM.setLowEndianValue(globalData, 36, 0);
        rarVM.setLowEndianValue(globalData, 40, 0);
        rarVM.setLowEndianValue(globalData, 44, StackFilter.getExecCount());
        for (int i = 0; i < 16; i++) {
            globalData.setElementAt(new Byte((byte) 0), 48 + i);
        }

        if ((firstByte & 8) != 0) {
            if (Inp.Overflow(3)) {
                return false;
            }
            int DataSize = RarVM.ReadData(Inp);
            if (DataSize > 8128) {
                return false;
            }
            int CurSize = StackFilter.getPrg().getGlobalData().size();
            if (CurSize < DataSize + 64) {
                StackFilter.getPrg().getGlobalData().setSize((DataSize + 64) - CurSize);
            }
            int offset = 64;
            globalData = StackFilter.getPrg().getGlobalData();
            for (int I = 0; I < DataSize; I++) {
                if (Inp.Overflow(3)) {
                    return false;
                }
                globalData.setElementAt(new Byte((byte) (Inp.getbits() >>> 8)), offset + I);
                Inp.faddbits(8);
            }

        }
        return true;
    }

    private void ExecuteCode(VMPreparedProgram Prg) {
        if (Prg.getGlobalData().size() > 0) {
            Prg.getInitR()[6] = (int) writtenFileSize;
            rarVM.setLowEndianValue(Prg.getGlobalData(), 36, (int) writtenFileSize);
            rarVM.setLowEndianValue(Prg.getGlobalData(), 40, (int) (writtenFileSize >>> 32));
            rarVM.execute(Prg);
        }
    }

    public boolean isFileExtracted() {
        return fileExtracted;
    }

    public void setDestSize(long destSize,int csi) {
        destUnpSize = destSize;
        csize=csi;
        fileExtracted = false;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public int getChar()throws IOException {
        if (inAddr > 32738) {
            unpReadBuf();
        }
        return inBuf[inAddr++] & 0xff;
    }

    public int getPpmEscChar() {
        return ppmEscChar;
    }

    public void setPpmEscChar(int ppmEscChar) {
        this.ppmEscChar = ppmEscChar;
    }

    public void clear() {
        window = null;
    }

    ;

    public void cleanUp() {
        if (ppm != null) {
            SubAllocator allocator = ppm.getSubAlloc();
            if (allocator != null) {
                allocator.stopSubAllocator();
            }
        }
    }
}
