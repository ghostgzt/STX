package rar.unpack;

import rar.vm.BitInput;
import java.io.IOException;

public abstract class Unpack15 extends BitInput {

    protected int readBorder;
    protected boolean suspended, unpAllBuf;
    protected ComprDataIO unpIO;
    protected boolean unpSomeRead;
    protected int readTop;
    protected long destUnpSize;
    protected byte window[];
    protected int oldDist[];
    protected int unpPtr, wrPtr, oldDistPtr;
    protected int ChSet[], ChSetA[], ChSetB[], ChSetC[];
    protected int Place[], PlaceA[], PlaceB[], PlaceC[];
    protected int NToPl[], NToPlB[], NToPlC[];
    protected int FlagBuf;
    protected int AvrPlc, AvrPlcB;
    protected int AvrLn1, AvrLn2, AvrLn3;
    protected int Buf60;
    protected int NumHuf, StMode, LCount, FlagsCnt;
    protected int Nhfb, Nlzb, MaxDist3, lastDist, lastLength;
    private static int DecL1[] = {
        32768, 40960, 49152, 53248, 57344, 59904, 60928, 61440, 61952, 61952,
        65535
    };
    private static int PosL1[] = {
        0, 0, 0, 2, 3, 5, 7, 11, 16, 20,
        24, 32, 32
    };
    private static int DecL2[] = {
        40960, 49152, 53248, 57344, 59904, 60928, 61440, 61952, 62016, 65535
    };
    private static int PosL2[] = {
        0, 0, 0, 0, 5, 7, 9, 13, 18, 22,
        26, 34, 36
    };
    private static int DecHf0[] = {
        32768, 49152, 57344, 61952, 61952, 61952, 61952, 61952, 65535
    };
    private static int PosHf0[] = {
        0, 0, 0, 0, 0, 8, 16, 24, 33, 33,
        33, 33, 33
    };
    private static int DecHf1[] = {
        8192, 49152, 57344, 61440, 61952, 61952, 63456, 65535
    };
    private static int PosHf1[] = {
        0, 0, 0, 0, 0, 0, 4, 44, 60, 76,
        80, 80, 127
    };
    private static int DecHf2[] = {
        4096, 9216, 32768, 49152, 64000, 65535, 65535, 65535
    };
    private static int PosHf2[] = {
        0, 0, 0, 0, 0, 0, 2, 7, 53, 117,
        233, 0, 0
    };
    private static int DecHf3[] = {
        2048, 9216, 60928, 65152, 65535, 65535, 65535
    };
    private static int PosHf3[] = {
        0, 0, 0, 0, 0, 0, 0, 2, 16, 218,
        251, 0, 0
    };
    private static int DecHf4[] = {
        65280, 65535, 65535, 65535, 65535, 65535
    };
    private static int PosHf4[] = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 255,
        0, 0, 0
    };
    static int ShortLen1[] = {
        1, 3, 4, 4, 5, 6, 7, 8, 8, 4,
        4, 5, 6, 6, 4, 0
    };
    static int ShortXor1[] = {
        0, 160, 208, 224, 240, 248, 252, 254, 255, 192,
        128, 144, 152, 156, 176
    };
    static int ShortLen2[] = {
        2, 3, 3, 3, 4, 4, 5, 6, 6, 4,
        4, 5, 6, 6, 4, 0
    };
    static int ShortXor2[] = {
        0, 64, 96, 160, 208, 224, 240, 248, 252, 192,
        128, 144, 152, 156, 176
    };

    public Unpack15() {
        oldDist = new int[4];
        ChSet = new int[256];
        ChSetA = new int[256];
        ChSetB = new int[256];
        ChSetC = new int[256];
        Place = new int[256];
        PlaceA = new int[256];
        PlaceB = new int[256];
        PlaceC = new int[256];
        NToPl = new int[256];
        NToPlB = new int[256];
        NToPlC = new int[256];
    }

    protected abstract void unpInitData(boolean flag);

    protected void unpack15(boolean solid) throws IOException {
        if (suspended) {
            unpPtr = wrPtr;
        } else {
            unpInitData(solid);
            oldUnpInitData(solid);
            unpReadBuf();
            if (!solid) {
                initHuff();
                unpPtr = 0;
            } else {
                unpPtr = wrPtr;
            }
            destUnpSize--;
        }
        if (destUnpSize >= 0L) {
            getFlagsBuf();
            FlagsCnt = 8;
        }
        do {
            if (destUnpSize < 0L) {
                break;
            }
            unpPtr &= Unpack.Maxwink;
            if (inAddr > readTop - 30 && !unpReadBuf()) {
                break;
            }
            if ((wrPtr - unpPtr & Unpack.Maxwink) < 270 && wrPtr != unpPtr) {
                oldUnpWriteBuf();
                if (suspended) {
                    return;
                }
            }
            if (StMode != 0) {
                huffDecode();
            } else {
                if (--FlagsCnt < 0) {
                    getFlagsBuf();
                    FlagsCnt = 7;
                }
                if ((FlagBuf & 0x80) != 0) {
                    FlagBuf <<= 1;
                    if (Nlzb > Nhfb) {
                        longLZ();
                    } else {
                        huffDecode();
                    }
                } else {
                    FlagBuf <<= 1;
                    if (--FlagsCnt < 0) {
                        getFlagsBuf();
                        FlagsCnt = 7;
                    }
                    if ((FlagBuf & 0x80) != 0) {
                        FlagBuf <<= 1;
                        if (Nlzb > Nhfb) {
                            huffDecode();
                        } else {
                            longLZ();
                        }
                    } else {
                        FlagBuf <<= 1;
                        shortLZ();
                    }
                }
            }
        } while (true);
        oldUnpWriteBuf();
    }

    protected boolean unpReadBuf() throws IOException {
        int dataSize = readTop - inAddr;
        if (dataSize < 0) {
            return false;
        }
        if (inAddr > 16384) {
            if (dataSize > 0) {
                System.arraycopy(inBuf, inAddr, inBuf, 0, dataSize);
            }
            inAddr = 0;
            readTop = dataSize;
        } else {
            dataSize = readTop;
        }
        int readCode = unpIO.unpRead(inBuf, dataSize, 32768 - dataSize & 0xfffffff0);
        if (readCode > 0) {
            readTop += readCode;
        }
        readBorder = readTop - 30;
        return readCode != -1;
    }

    private int getShortLen1(int pos) {
        return pos != 1 ? ShortLen1[pos] : Buf60 + 3;
    }

    private int getShortLen2(int pos) {
        return pos != 3 ? ShortLen2[pos] : Buf60 + 3;
    }

    protected void shortLZ() {
        NumHuf = 0;
        int BitField = getbits();
        if (LCount == 2) {
            faddbits(1);
            if (BitField >= 32768) {
                oldCopyString(lastDist, lastLength);
                return;
            }
            BitField <<= 1;
            LCount = 0;
        }
        BitField >>>= 8;
        int Length;
        if (AvrLn1 < 37) {
            for (Length = 0; ((BitField ^ ShortXor1[Length]) & ~(255 >>> getShortLen1(Length))) != 0;) {
                Length++;
            }
            faddbits(getShortLen1(Length));
        } else {
            for (Length = 0; ((BitField ^ ShortXor2[Length]) & ~(255 >> getShortLen2(Length))) != 0;) {
                Length++;
            }
            faddbits(getShortLen2(Length));
        }
        int Distance;
        if (Length >= 9) {
            if (Length == 9) {
                LCount++;
                oldCopyString(lastDist, lastLength);
                return;
            }
            if (Length == 14) {
                LCount = 0;
                Length = decodeNum(getbits(), 3, DecL2, PosL2) + 5;
                Distance = getbits() >> 1 | 0x8000;
                faddbits(15);
                lastLength = Length;
                lastDist = Distance;
                oldCopyString(Distance, Length);
                return;
            }
            LCount = 0;
            int SaveLength = Length;
            Distance = oldDist[oldDistPtr - (Length - 9) & 3];
            Length = decodeNum(getbits(), 2, DecL1, PosL1) + 2;
            if (Length == 257 && SaveLength == 10) {
                Buf60 ^= 1;
                return;
            }
            if (Distance > 256) {
                Length++;
            }
            if (Distance >= MaxDist3) {
                Length++;
            }
            oldDist[oldDistPtr++] = Distance;
            oldDistPtr = oldDistPtr & 3;
            lastLength = Length;
            lastDist = Distance;
            oldCopyString(Distance, Length);
            return;
        }
        LCount = 0;
        AvrLn1 += Length;
        AvrLn1 -= AvrLn1 >> 4;
        int DistancePlace = decodeNum(getbits(), 5, DecHf2, PosHf2) & 0xff;
        Distance = ChSetA[DistancePlace];
        if (--DistancePlace != -1) {
            PlaceA[Distance]--;
            int LastDistance = ChSetA[DistancePlace];
            PlaceA[LastDistance]++;
            ChSetA[DistancePlace + 1] = LastDistance;
            ChSetA[DistancePlace] = Distance;
        }
        Length += 2;
        oldDist[oldDistPtr++] = ++Distance;
        oldDistPtr = oldDistPtr & 3;
        lastLength = Length;
        lastDist = Distance;
        oldCopyString(Distance, Length);
    }

    protected void longLZ() {
        NumHuf = 0;
        Nlzb += 16;
        if (Nlzb > 255) {
            Nlzb = 144;
            Nhfb >>>= 1;
        }
        int OldAvr2 = AvrLn2;
        int BitField = getbits();
        int Length;
        if (AvrLn2 >= 122) {
            Length = decodeNum(BitField, 3, DecL2, PosL2);
        } else if (AvrLn2 >= 64) {
            Length = decodeNum(BitField, 2, DecL1, PosL1);
        } else if (BitField < 256) {
            Length = BitField;
            faddbits(16);
        } else {
            for (Length = 0; (BitField << Length & 0x8000) == 0;) {
                Length++;
            }
            faddbits(Length + 1);
        }
        AvrLn2 += Length;
        AvrLn2 -= AvrLn2 >>> 5;
        BitField = getbits();
        int DistancePlace;
        if (AvrPlcB > 10495) {
            DistancePlace = decodeNum(BitField, 5, DecHf2, PosHf2);
        } else if (AvrPlcB > 1791) {
            DistancePlace = decodeNum(BitField, 5, DecHf1, PosHf1);
        } else {
            DistancePlace = decodeNum(BitField, 4, DecHf0, PosHf0);
        }
        AvrPlcB += DistancePlace;
        AvrPlcB -= AvrPlcB >> 8;
        int Distance;
        int NewDistancePlace;
        do {
            Distance = ChSetB[DistancePlace & 0xff];
            NewDistancePlace = NToPlB[Distance++ & 0xff]++;
            if ((Distance & 0xff) != 0) {
                break;
            }
            corrHuff(ChSetB, NToPlB);
        } while (true);
        ChSetB[DistancePlace] = ChSetB[NewDistancePlace];
        ChSetB[NewDistancePlace] = Distance;
        Distance = (Distance & 0xff00 | getbits() >>> 8) >>> 1;
        faddbits(7);
        int OldAvr3 = AvrLn3;
        if (Length != 1 && Length != 4) {
            if (Length == 0 && Distance <= MaxDist3) {
                AvrLn3++;
                AvrLn3 -= AvrLn3 >> 8;
            } else if (AvrLn3 > 0) {
                AvrLn3--;
            }
        }
        Length += 3;
        if (Distance >= MaxDist3) {
            Length++;
        }
        if (Distance <= 256) {
            Length += 8;
        }
        if (OldAvr3 > 176 || AvrPlc >= 10752 && OldAvr2 < 64) {
            MaxDist3 = 32512;
        } else {
            MaxDist3 = 8193;
        }
        oldDist[oldDistPtr++] = Distance;
        oldDistPtr = oldDistPtr & 3;
        lastLength = Length;
        lastDist = Distance;
        oldCopyString(Distance, Length);
    }

    protected void huffDecode() {
        int BitField = getbits();
        int BytePlace;
        if (AvrPlc > 30207) {
            BytePlace = decodeNum(BitField, 8, DecHf4, PosHf4);
        } else if (AvrPlc > 24063) {
            BytePlace = decodeNum(BitField, 6, DecHf3, PosHf3);
        } else if (AvrPlc > 13823) {
            BytePlace = decodeNum(BitField, 5, DecHf2, PosHf2);
        } else if (AvrPlc > 3583) {
            BytePlace = decodeNum(BitField, 5, DecHf1, PosHf1);
        } else {
            BytePlace = decodeNum(BitField, 4, DecHf0, PosHf0);
        }
        BytePlace &= 0xff;
        if (StMode != 0) {
            if (BytePlace == 0 && BitField > 4095) {
                BytePlace = 256;
            }
            if (--BytePlace == -1) {
                BitField = getbits();
                faddbits(1);
                if ((BitField & 0x8000) != 0) {
                    NumHuf = StMode = 0;
                    return;
                } else {
                    int Length = (BitField & 0x4000) == 0 ? 3 : 4;
                    faddbits(1);
                    int Distance = decodeNum(getbits(), 5, DecHf2, PosHf2);
                    Distance = Distance << 5 | getbits() >>> 11;
                    faddbits(5);
                    oldCopyString(Distance, Length);
                    return;
                }
            }
        } else if (NumHuf++ >= 16 && FlagsCnt == 0) {
            StMode = 1;
        }
        AvrPlc += BytePlace;
        AvrPlc -= AvrPlc >>> 8;
        Nhfb += 16;
        if (Nhfb > 255) {
            Nhfb = 144;
            Nlzb >>>= 1;
        }
        window[unpPtr++] = (byte) (ChSet[BytePlace] >>> 8);
        destUnpSize--;
        do {
            int CurByte = ChSet[BytePlace];
            int NewBytePlace = NToPl[CurByte++ & 0xff]++;
            if ((CurByte & 0xff) > 161) {
                corrHuff(ChSet, NToPl);
            } else {
                ChSet[BytePlace] = ChSet[NewBytePlace];
                ChSet[NewBytePlace] = CurByte;
                return;
            }
        } while (true);
    }

    protected void getFlagsBuf() {
        int FlagsPlace = decodeNum(getbits(), 5, DecHf2, PosHf2);
        do {
            int Flags = ChSetC[FlagsPlace];
            FlagBuf = Flags >>> 8;
            int NewFlagsPlace = NToPlC[Flags++ & 0xff]++;
            if ((Flags & 0xff) == 0) {
                corrHuff(ChSetC, NToPlC);
            } else {
                ChSetC[FlagsPlace] = ChSetC[NewFlagsPlace];
                ChSetC[NewFlagsPlace] = Flags;
                return;
            }
        } while (true);
    }

    protected void oldUnpInitData(boolean Solid) {
        if (!Solid) {
            AvrPlcB = AvrLn1 = AvrLn2 = AvrLn3 = NumHuf = Buf60 = 0;
            AvrPlc = 13568;
            MaxDist3 = 8193;
            Nhfb = Nlzb = 128;
        }
        FlagsCnt = 0;
        FlagBuf = 0;
        StMode = 0;
        LCount = 0;
        readTop = 0;
    }

    protected void initHuff() {
        for (int I = 0; I < 256; I++) {
            Place[I] = PlaceA[I] = PlaceB[I] = I;
            PlaceC[I] = ~I + 1 & 0xff;
            ChSet[I] = ChSetB[I] = I << 8;
            ChSetA[I] = I;
            ChSetC[I] = (~I + 1 & 0xff) << 8;
        }

        fill(NToPl);
        fill(NToPlB);
        fill(NToPlC);
        corrHuff(ChSetB, NToPlB);
    }

    protected void corrHuff(int CharSet[], int NumToPlace[]) {
        int pos = 0;
        for (int I = 7; I >= 0; I--) {
            for (int J = 0; J < 32;) {
                CharSet[pos] = CharSet[pos] & 0xffffff00 | I;
                J++;
                pos++;
            }

        }

        fill(NumToPlace);
        for (int I = 6; I >= 0; I--) {
            NumToPlace[I] = (7 - I) * 32;
        }

    }

    protected void oldCopyString(int Distance, int Length) {
        destUnpSize -= Length;
        while (Length-- != 0) {
            window[unpPtr] = window[(unpPtr - Distance) & Unpack.Maxwink];
            unpPtr = (unpPtr + 1) & Unpack.Maxwink;
        }
    }

    protected int decodeNum(int Num, int StartPos, int DecTab[], int PosTab[]) {
        Num &= 0xfff0;
        int I;
        for (I = 0; DecTab[I] <= Num; I++) {
            StartPos++;
        }

        faddbits(StartPos);
        return (Num - (I == 0 ? 0 : DecTab[I - 1]) >>> 16 - StartPos) + PosTab[StartPos];
    }

    protected void oldUnpWriteBuf() throws IOException {
        if (unpPtr != wrPtr) {
            unpSomeRead = true;
        }
        if (unpPtr < wrPtr) {
            unpIO.unpWrite(window, wrPtr, -wrPtr & Unpack.Maxwink);
            unpIO.unpWrite(window, 0, unpPtr);
            unpAllBuf = true;
        } else {
            unpIO.unpWrite(window, wrPtr, unpPtr - wrPtr);
        }
        wrPtr = unpPtr;
    }

    private void fill(int data[]) {
        for (int i = data.length - 1; i >= 0; i--) {
            data[i] = 0;
        }

    }
}
