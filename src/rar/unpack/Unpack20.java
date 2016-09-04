package rar.unpack;

import java.io.IOException;

public abstract class Unpack20 extends Unpack15 {

    protected byte UnpOldTable20[];
    protected int UnpAudioBlock, UnpChannels, UnpCurChannel, UnpChannelDelta;
    protected AudioVariables AudV[];
    protected Decode LD, DD, LDD, RD, BD;
    public static final int LDecode[] = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 10,
        12, 14, 16, 20, 24, 28, 32, 40, 48, 56,
        64, 80, 96, 112, 128, 160, 192, 224
    };
    public static final byte LBits[] = {
        0, 0, 0, 0, 0, 0, 0, 0, 1, 1,
        1, 1, 2, 2, 2, 2, 3, 3, 3, 3,
        4, 4, 4, 4, 5, 5, 5, 5
    };
    public static final int DDecode[] = {
        0, 1, 2, 3, 4, 6, 8, 12, 16, 24,
        32, 48, 64, 96, 128, 192, 256, 384, 512, 768,
        1024, 1536, 2048, 3072, 4096, 6144, 8192, 12288, 16384, 24576,
        32768, 49152, 0x10000, 0x18000, 0x20000, 0x30000, 0x40000, 0x50000, 0x60000, 0x70000,
        0x80000, 0x90000, 0xa0000, 0xb0000, 0xc0000, 0xd0000, 0xe0000, 0xf0000
    };
    public static final int DBits[] = {
        0, 0, 0, 0, 1, 1, 2, 2, 3, 3,
        4, 4, 5, 5, 6, 6, 7, 7, 8, 8,
        9, 9, 10, 10, 11, 11, 12, 12, 13, 13,
        14, 14, 15, 15, 16, 16, 16, 16, 16, 16,
        16, 16, 16, 16, 16, 16, 16, 16
    };
    public static final int SDDecode[] = {
        0, 4, 8, 16, 32, 64, 128, 192
    };
    public static final int SDBits[] = {
        2, 2, 3, 4, 5, 6, 6, 6
    };

    public Unpack20() {
        UnpOldTable20 = new byte[1028];
        AudV = new AudioVariables[4];
        LD = new Decode(299);
        DD = new Decode(60);
        LDD = new Decode(17);
        RD = new Decode(28);
        BD = new Decode(20);
    }

    protected void unpack20(boolean solid) throws IOException {
        if (suspended) {
            unpPtr = wrPtr;
        } else {
            unpInitData(solid);
            if (!unpReadBuf()) {
                return;
            }
            if (!solid && !ReadTables20()) {
                return;
            }
            destUnpSize--;
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
            if (UnpAudioBlock != 0) {
                int AudioNumber = 256;
                //decodeNumber(MD[UnpCurChannel]);
                if (AudioNumber == 256) {
                    if (!ReadTables20()) {
                        break;
                    }
                } else {
                    window[unpPtr++] = DecodeAudio(AudioNumber);
                    if (++UnpCurChannel == UnpChannels) {
                        UnpCurChannel = 0;
                    }
                    destUnpSize--;
                }
                continue;
            }
            int Number = decodeNumber(LD);
            if (Number < 256) {
                window[unpPtr++] = (byte) Number;
                destUnpSize--;
                continue;
            }
            if (Number > 269) {
                int Length = LDecode[Number -= 270] + 3;
                int Bits;
                if ((Bits = LBits[Number]) > 0) {
                    Length += getbits() >>> 16 - Bits;
                    addbits(Bits);
                }
                int DistNumber = decodeNumber(DD);
                int Distance = DDecode[DistNumber] + 1;
                if ((Bits = DBits[DistNumber]) > 0) {
                    Distance += getbits() >>> 16 - Bits;
                    addbits(Bits);
                }
                if (Distance >= 8192) {
                    Length++;
                    if ((long) Distance >= 0x40000L) {
                        Length++;
                    }
                }
                CopyString20(Length, Distance);
                continue;
            }
            if (Number == 269) {
                if (!ReadTables20()) {
                    break;
                }
            } else if (Number == 256) {
                CopyString20(lastLength, lastDist);
            } else if (Number < 261) {
                int Distance = oldDist[oldDistPtr - (Number - 256) & 3];
                int LengthNumber = decodeNumber(RD);
                int Length = LDecode[LengthNumber] + 2;
                int Bits;
                if ((Bits = LBits[LengthNumber]) > 0) {
                    Length += getbits() >>> 16 - Bits;
                    addbits(Bits);
                }
                if (Distance >= 257) {
                    Length++;
                    if (Distance >= 8192) {
                        Length++;
                        if (Distance >= 0x40000) {
                            Length++;
                        }
                    }
                }
                CopyString20(Length, Distance);
            } else if (Number < 270) {
                int Distance = SDDecode[Number -= 261] + 1;
                int Bits;
                if ((Bits = SDBits[Number]) > 0) {
                    Distance += getbits() >>> 16 - Bits;
                    addbits(Bits);
                }
                CopyString20(2, Distance);
            }
        } while (true);
        ReadLastTables();
        oldUnpWriteBuf();
    }

    protected void CopyString20(int Length, int Distance) {
        lastDist = oldDist[oldDistPtr++ & 3] = Distance;
        lastLength = Length;
        destUnpSize -= Length;
        int DestPtr = unpPtr - Distance;
        if (DestPtr < Unpack.Maxwin - 300 && unpPtr < Unpack.Maxwin - 300) {
            window[unpPtr++] = window[DestPtr++];
            for (window[unpPtr++] = window[DestPtr++]; Length > 2; window[unpPtr++] = window[DestPtr++]) {
                Length--;
            }

        } else {
            while (Length-- != 0) {
                window[unpPtr] = window[DestPtr++ & Unpack.Maxwink];
                unpPtr = (unpPtr + 1) & Unpack.Maxwink;
            }
        }
    }

    protected void makeDecodeTables(byte lenTab[], int offset, Decode dec, int size) {
        int lenCount[] = new int[16];
        int tmpPos[] = new int[16];
        fill(lenCount);
        fill(dec.decodeNum);
        for (int i = 0; i < size; i++) {
            lenCount[lenTab[offset + i] & 0xf]++;
        }

        lenCount[0] = 0;
        tmpPos[0] = 0;
        dec.decodePos[0] = 0;
        dec.decodeLen[0] = 0;
        long N = 0L;
        for (int i = 1; i < 16; i++) {
            N = 2L * (N + (long) lenCount[i]);
            long M = N << 15 - i;
            if (M > 65535L) {
                M = 65535L;
            }
            dec.decodeLen[i] = (int) M;
            tmpPos[i] = dec.decodePos[i] = dec.decodePos[i - 1] + lenCount[i - 1];
        }

        for (int i = 0; i < size; i++) {
            if (lenTab[offset + i] != 0) {
                dec.decodeNum[tmpPos[lenTab[offset + i] & 0xf]++] = i;
            }
        }

        dec.maxNum = size;
    }

    protected int decodeNumber(Decode dec) {
        long bitField = getbits() & 0xfffe;
        int decodeLen[] = dec.decodeLen;
        int bits;
        if (bitField < (long) decodeLen[8]) {
            if (bitField < (long) decodeLen[4]) {
                if (bitField < (long) decodeLen[2]) {
                    if (bitField < (long) decodeLen[1]) {
                        bits = 1;
                    } else {
                        bits = 2;
                    }
                } else if (bitField < (long) decodeLen[3]) {
                    bits = 3;
                } else {
                    bits = 4;
                }
            } else if (bitField < (long) decodeLen[6]) {
                if (bitField < (long) decodeLen[5]) {
                    bits = 5;
                } else {
                    bits = 6;
                }
            } else if (bitField < (long) decodeLen[7]) {
                bits = 7;
            } else {
                bits = 8;
            }
        } else if (bitField < (long) decodeLen[12]) {
            if (bitField < (long) decodeLen[10]) {
                if (bitField < (long) decodeLen[9]) {
                    bits = 9;
                } else {
                    bits = 10;
                }
            } else if (bitField < (long) decodeLen[11]) {
                bits = 11;
            } else {
                bits = 12;
            }
        } else if (bitField < (long) decodeLen[14]) {
            if (bitField < (long) decodeLen[13]) {
                bits = 13;
            } else {
                bits = 14;
            }
        } else {
            bits = 15;
        }
        addbits(bits);
        int N = dec.decodePos[bits] + ((int) bitField - decodeLen[bits - 1] >>> 16 - bits);
        if (N >= dec.maxNum) {
            N = 0;
        }
        return dec.decodeNum[N];
    }

    protected boolean ReadTables20()
            throws IOException {
        byte BitLength[] = new byte[19];
        byte Table[] = new byte[1028];
        if (inAddr > readTop - 25 && !unpReadBuf()) {
            return false;
        }
        int BitField = getbits();
        UnpAudioBlock = BitField & 0x8000;
        if (0 == (BitField & 0x4000)) {
            fill(UnpOldTable20);
        }
        addbits(2);
        int TableSize;
        if (UnpAudioBlock != 0) {
            UnpChannels = (BitField >>> 12 & 3) + 1;
            if (UnpCurChannel >= UnpChannels) {
                UnpCurChannel = 0;
            }
            addbits(2);
            TableSize = 257 * UnpChannels;
        } else {
            TableSize = 374;
        }
        for (int I = 0; I < 19; I++) {
            BitLength[I] = (byte) (getbits() >>> 12);
            addbits(4);
        }

        makeDecodeTables(BitLength, 0, BD, 19);
        for (int I = 0; I < TableSize;) {
            if (inAddr > readTop - 5 && !unpReadBuf()) {
                return false;
            }
            int Number = decodeNumber(BD);
            if (Number < 16) {
                Table[I] = (byte) (Number + UnpOldTable20[I] & 0xf);
                I++;
            } else if (Number == 16) {
                int N = (getbits() >>> 14) + 3;
                addbits(2);
                while (N-- > 0 && I < TableSize) {
                    Table[I] = Table[I - 1];
                    I++;
                }
            } else {
                int N;
                if (Number == 17) {
                    N = (getbits() >>> 13) + 3;
                    addbits(3);
                } else {
                    N = (getbits() >>> 9) + 11;
                    addbits(7);
                }
                while (N-- > 0 && I < TableSize) {
                    Table[I++] = 0;
                }
            }
        }

        if (inAddr > readTop) {
            return true;
        }
        if (UnpAudioBlock != 0) {
            //    for (int I = 0; I < UnpChannels; I++) {
            //       makeDecodeTables(Table, I * 257, MD[I], 257);
            //    }
        } else {
            makeDecodeTables(Table, 0, LD, 298);
            makeDecodeTables(Table, 298, DD, 48);
            makeDecodeTables(Table, 346, RD, 28);
        }
        for (int i = 0; i < 1028; i++) {
            UnpOldTable20[i] = Table[i];
        }

        return true;
    }

    protected void unpInitData20(boolean Solid) {
        if (!Solid) {
            UnpChannelDelta = UnpCurChannel = 0;
            UnpChannels = 1;
            for (int i = 3; i >= 0; i--) {
                AudV[i] = new AudioVariables();
            }
            fill(UnpOldTable20);
        }
    }

    protected void ReadLastTables() throws IOException {
        if (readTop >= inAddr + 5) {
            if (UnpAudioBlock != 0) {
                //     if (decodeNumber(MD[UnpCurChannel]) == 256) {
                //          ReadTables20();
                //      }
            } else if (decodeNumber(LD) == 269) {
                ReadTables20();
            }
        }
    }

    protected byte DecodeAudio(int Delta) {
        AudioVariables v = AudV[UnpCurChannel];
        v.byteCount++;
        v.d4 = v.d3;
        v.d3 = v.d2;
        v.d2 = v.lastDelta - v.d1;
        v.d1 = v.lastDelta;
        int PCh = 8 * v.lastChar + v.k1 * v.d1;
        PCh += v.k2 * v.d2 + v.k3 * v.d3;
        PCh += v.k4 * v.d4 + v.k5 * UnpChannelDelta;
        PCh = PCh >>> 3 & 0xff;
        int Ch = PCh - Delta;
        int D = (byte) Delta << 3;
        v.dif[0] += Math.abs(D);
        v.dif[1] += Math.abs(D - v.d1);
        v.dif[2] += Math.abs(D + v.d1);
        v.dif[3] += Math.abs(D - v.d2);
        v.dif[4] += Math.abs(D + v.d2);
        v.dif[5] += Math.abs(D - v.d3);
        v.dif[6] += Math.abs(D + v.d3);
        v.dif[7] += Math.abs(D - v.d4);
        v.dif[8] += Math.abs(D + v.d4);
        v.dif[9] += Math.abs(D - UnpChannelDelta);
        v.dif[10] += Math.abs(D + UnpChannelDelta);
        v.lastDelta = (byte) (Ch - v.lastChar);
        UnpChannelDelta = v.lastDelta;
        v.lastChar = Ch;
        if ((v.byteCount & 0x1f) == 0) {
            int MinDif = v.dif[0];
            int NumMinDif = 0;
            v.dif[0] = 0;
            int l = v.dif.length;
            for (int I = 1; I < l; I++) {
                if (v.dif[I] < MinDif) {
                    MinDif = v.dif[I];
                    NumMinDif = I;
                }
                v.dif[I] = 0;
            }

            switch (NumMinDif) {
                default:
                    break;

                case 1: // '\001'
                    if (v.k1 >= -16) {
                        v.k1--;
                    }
                    break;

                case 2: // '\002'
                    if (v.k1 < 16) {
                        v.k1++;
                    }
                    break;

                case 3: // '\003'
                    if (v.k2 >= -16) {
                        v.k2--;
                    }
                    break;

                case 4: // '\004'
                    if (v.k2 < 16) {
                        v.k2++;
                    }
                    break;

                case 5: // '\005'
                    if (v.k3 >= -16) {
                        v.k3--;
                    }
                    break;

                case 6: // '\006'
                    if (v.k3 < 16) {
                        v.k3++;
                    }
                    break;

                case 7: // '\007'
                    if (v.k4 >= -16) {
                        v.k4--;
                    }
                    break;

                case 8: // '\b'
                    if (v.k4 < 16) {
                        v.k4++;
                    }
                    break;

                case 9: // '\t'
                    if (v.k5 >= -16) {
                        v.k5--;
                    }
                    break;

                case 10: // '\n'
                    if (v.k5 < 16) {
                        v.k5++;
                    }
                    break;
            }
        }
        return (byte) Ch;
    }

    private void fill(byte bt[]) {
        for (int i = bt.length - 1; i >= 0; i--) {
            bt[i] = 0;
        }

    }

    private void fill(int bt[]) {
        for (int i = bt.length - 1; i >= 0; i--) {
            bt[i] = 0;
        }

    }
}
