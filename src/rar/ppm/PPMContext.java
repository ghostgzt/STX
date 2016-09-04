package rar.ppm;

import rar.unpack.Raw;





public class PPMContext extends Pointer {

    public static final int size = 12;
    private int numStats;
    private final FreqData freqData;
    private final State oneState;
    private int suffix;
    public static final int ExpEscape[] = {
        25, 14, 9, 7, 5, 5, 4, 4, 4, 3,
        3, 3, 2, 2, 2, 2
    };
    private final State tempState1 = new State(null);
    private final State tempState2 = new State(null);
    private final State tempState3 = new State(null);
    private final State tempState4 = new State(null);
    private final State tempState5 = new State(null);
    private PPMContext tempPPMContext;
    private final int ps[] = new int[256];

    public PPMContext(byte mem[]) {
        super(mem);
        tempPPMContext = null;
        oneState = new State(mem);
        freqData = new FreqData(mem);
    }

    public PPMContext init(byte mem[]) {
        this.mem = mem;
        pos = 0;
        oneState.init(mem);
        freqData.init(mem);
        return this;
    }

    public FreqData getFreqData() {
        return freqData;
    }

    public void setFreqData(FreqData freqData) {
        this.freqData.setSummFreq(freqData.getSummFreq());
        this.freqData.setStats(freqData.getStats());
    }

    public final int getNumStats() {
        if (mem != null) {
            numStats = Raw.readShortLittleEndian(mem, pos) & 0xffff;
        }
        return numStats;
    }

    public final void setNumStats(int numStats) {
        this.numStats = numStats & 0xffff;
        if (mem != null) {
            Raw.writeShortLittleEndian(mem, pos, (short) numStats);
        }
    }

    public State getOneState() {
        return oneState;
    }

    public void setOneState(StateRef oneState) {
        this.oneState.setValues(oneState);
    }

    public int getSuffix() {
        if (mem != null) {
            suffix = Raw.readIntLittleEndian(mem, pos + 8);
        }
        return suffix;
    }

    public void setSuffix(PPMContext suffix) {
        setSuffix(suffix.getAddress());
    }

    public void setSuffix(int suffix) {
        this.suffix = suffix;
        if (mem != null) {
            Raw.writeIntLittleEndian(mem, pos + 8, suffix);
        }
    }

    public void setAddress(int pos) {
        super.setAddress(pos);
        oneState.setAddress(pos + 2);
        freqData.setAddress(pos + 2);
    }

    private PPMContext getTempPPMContext(byte mem[]) {
        if (tempPPMContext == null) {
            tempPPMContext = new PPMContext(null);
        }
        return tempPPMContext.init(mem);
    }

    public int createChild(ModelPPM model, State pStats, StateRef firstState) {
        PPMContext pc = getTempPPMContext(model.getSubAlloc().getHeap());
        pc.setAddress(model.getSubAlloc().allocContext());
        if (pc != null) {
            pc.setNumStats(1);
            pc.setOneState(firstState);
            pc.setSuffix(this);
            pStats.setSuccessor(pc);
        }
        return pc.getAddress();
    }

    public void rescale(ModelPPM model) {
        int OldNS = getNumStats();
        int i = getNumStats() - 1;
        State p1 = new State(model.getHeap());
        State p = new State(model.getHeap());
        State temp = new State(model.getHeap());
        p.setAddress(model.getFoundState().getAddress());
        for (; p.getAddress() != freqData.getStats(); p.decAddress()) {
            temp.setAddress(p.getAddress() - 6);
            State.ppmdSwap(p, temp);
        }

        temp.setAddress(freqData.getStats());
        temp.incFreq(4);
        freqData.incSummFreq(4);
        int EscFreq = freqData.getSummFreq() - p.getFreq();
        int Adder = model.getOrderFall() == 0 ? 0 : 1;
        p.setFreq(p.getFreq() + Adder >>> 1);
        freqData.setSummFreq(p.getFreq());
        do {
            p.incAddress();
            EscFreq -= p.getFreq();
            p.setFreq(p.getFreq() + Adder >>> 1);
            freqData.incSummFreq(p.getFreq());
            temp.setAddress(p.getAddress() - 6);
            if (p.getFreq() > temp.getFreq()) {
                p1.setAddress(p.getAddress());
                StateRef tmp = new StateRef();
                tmp.setValues(p1);
                State temp2 = new State(model.getHeap());
                State temp3 = new State(model.getHeap());
                do {
                    temp2.setAddress(p1.getAddress() - 6);
                    p1.setValues(temp2);
                    p1.decAddress();
                    temp3.setAddress(p1.getAddress() - 6);
                } while (p1.getAddress() != freqData.getStats() && tmp.getFreq() > temp3.getFreq());
                p1.setValues(tmp);
            }
        } while (--i != 0);
        if (p.getFreq() == 0) {
            do {
                i++;
                p.decAddress();
            } while (p.getFreq() == 0);
            EscFreq += i;
            setNumStats(getNumStats() - i);
            if (getNumStats() == 1) {
                StateRef tmp = new StateRef();
                temp.setAddress(freqData.getStats());
                tmp.setValues(temp);
                do {
                    tmp.decFreq(tmp.getFreq() >>> 1);
                    EscFreq >>>= 1;
                } while (EscFreq > 1);
                model.getSubAlloc().freeUnits(freqData.getStats(), OldNS + 1 >>> 1);
                oneState.setValues(tmp);
                model.getFoundState().setAddress(oneState.getAddress());
                return;
            }
        }
        EscFreq -= EscFreq >>> 1;
        freqData.incSummFreq(EscFreq);
        int n0 = OldNS + 1 >>> 1;
        int n1 = getNumStats() + 1 >>> 1;
        if (n0 != n1) {
            freqData.setStats(model.getSubAlloc().shrinkUnits(freqData.getStats(), n0, n1));
        }
        model.getFoundState().setAddress(freqData.getStats());
    }

    private int getArrayIndex(ModelPPM Model, State rs) {
        PPMContext tempSuffix = getTempPPMContext(Model.getSubAlloc().getHeap());
        tempSuffix.setAddress(getSuffix());
        int ret = 0;
        ret += Model.getPrevSuccess();
        ret += Model.getNS2BSIndx()[tempSuffix.getNumStats() - 1];
        ret += Model.getHiBitsFlag() + 2 * Model.getHB2Flag()[rs.getSymbol()];
        ret += Model.getRunLength() >>> 26 & 0x20;
        return ret;
    }

    public int getMean(int summ, int shift, int round) {
        return summ + (1 << shift - round) >>> shift;
    }

    public void decodeBinSymbol(ModelPPM model) {
        State rs = tempState1.init(model.getHeap());
        rs.setAddress(oneState.getAddress());
        model.setHiBitsFlag(model.getHB2Flag()[model.getFoundState().getSymbol()]);
        int off1 = rs.getFreq() - 1;
        int off2 = getArrayIndex(model, rs);
        int bs = model.getBinSumm()[off1][off2];
        if (model.getCoder().getCurrentShiftCount(14) < (long) bs) {
            model.getFoundState().setAddress(rs.getAddress());
            rs.incFreq(rs.getFreq() >= 128 ? 0 : 1);
            model.getCoder().getSubRange().setLowCount(0L);
            model.getCoder().getSubRange().setHighCount(bs);
            bs = (bs + 128) - getMean(bs, 7, 2) & 0xffff;
            model.getBinSumm()[off1][off2] = bs;
            model.setPrevSuccess(1);
            model.incRunLength(1);
        } else {
            model.getCoder().getSubRange().setLowCount(bs);
            bs = bs - getMean(bs, 7, 2) & 0xffff;
            model.getBinSumm()[off1][off2] = bs;
            model.getCoder().getSubRange().setHighCount(16384L);
            model.setInitEsc(ExpEscape[bs >>> 10]);
            model.setNumMasked(1);
            model.getCharMask()[rs.getSymbol()] = model.getEscCount();
            model.setPrevSuccess(0);
            model.getFoundState().setAddress(0);
        }
    }

    public void update1(ModelPPM model, int p) {
        model.getFoundState().setAddress(p);
        model.getFoundState().incFreq(4);
        freqData.incSummFreq(4);
        State p0 = tempState3.init(model.getHeap());
        State p1 = tempState4.init(model.getHeap());
        p0.setAddress(p);
        p1.setAddress(p - 6);
        if (p0.getFreq() > p1.getFreq()) {
            State.ppmdSwap(p0, p1);
            model.getFoundState().setAddress(p1.getAddress());
            if (p1.getFreq() > 124) {
                rescale(model);
            }
        }
    }

    public boolean decodeSymbol2(ModelPPM model) {
        int i = getNumStats() - model.getNumMasked();
        SEE2Context psee2c = makeEscFreq2(model, i);
        RangeCoder coder = model.getCoder();
        State p = tempState1.init(model.getHeap());
        State temp = tempState2.init(model.getHeap());
        p.setAddress(freqData.getStats() - 6);
        int pps = 0;
        int hiCnt = 0;
        do {
            do {
                p.incAddress();
            } while (model.getCharMask()[p.getSymbol()] == model.getEscCount());
            hiCnt += p.getFreq();
            ps[pps++] = p.getAddress();
        } while (--i != 0);
        coder.getSubRange().incScale(hiCnt);
        long count = coder.getCurrentCount();
        if (count >= coder.getSubRange().getScale()) {
            return false;
        }
        pps = 0;
        p.setAddress(ps[pps]);
        if (count < (long) hiCnt) {
            for (hiCnt = 0; (long) (hiCnt += p.getFreq()) <= count;) {
                p.setAddress(ps[++pps]);
            }

            coder.getSubRange().setHighCount(hiCnt);
            coder.getSubRange().setLowCount(hiCnt - p.getFreq());
            psee2c.update();
            update2(model, p.getAddress());
        } else {
            coder.getSubRange().setLowCount(hiCnt);
            coder.getSubRange().setHighCount(coder.getSubRange().getScale());
            i = getNumStats() - model.getNumMasked();
            pps--;
            do {
                temp.setAddress(ps[++pps]);
                model.getCharMask()[temp.getSymbol()] = model.getEscCount();
            } while (--i != 0);
            psee2c.incSumm((int) coder.getSubRange().getScale());
            model.setNumMasked(getNumStats());
        }
        return true;
    }

    public void update2(ModelPPM model, int p) {
        State temp = tempState5.init(model.getHeap());
        temp.setAddress(p);
        model.getFoundState().setAddress(p);
        model.getFoundState().incFreq(4);
        freqData.incSummFreq(4);
        if (temp.getFreq() > 124) {
            rescale(model);
        }
        model.incEscCount(1);
        model.setRunLength(model.getInitRL());
    }

    private SEE2Context makeEscFreq2(ModelPPM model, int Diff) {
        int numstates = getNumStats();
        SEE2Context psee2c;
        if (numstates != 256) {
            PPMContext suff = getTempPPMContext(model.getHeap());
            suff.setAddress(getSuffix());
            int idx1 = model.getNS2Indx()[Diff - 1];
            int idx2 = 0;
            idx2 += Diff >= suff.getNumStats() - numstates ? 0 : 1;
            idx2 += 2 * (freqData.getSummFreq() >= 11 * numstates ? 0 : 1);
            idx2 += 4 * (model.getNumMasked() <= Diff ? 0 : 1);
            idx2 += model.getHiBitsFlag();
            psee2c = model.getSEE2Cont()[idx1][idx2];
            model.getCoder().getSubRange().setScale(psee2c.getMean());
        } else {
            psee2c = model.getDummySEE2Cont();
            model.getCoder().getSubRange().setScale(1L);
        }
        return psee2c;
    }

    public boolean decodeSymbol1(ModelPPM model) {
        RangeCoder coder = model.getCoder();
        coder.getSubRange().setScale(freqData.getSummFreq());
        State p = new State(model.getHeap());
        p.setAddress(freqData.getStats());
        long count = coder.getCurrentCount();
        if (count >= coder.getSubRange().getScale()) {
            return false;
        }
        int HiCnt;
        if (count < (long) (HiCnt = p.getFreq())) {
            coder.getSubRange().setHighCount(HiCnt);
            model.setPrevSuccess((long) (2 * HiCnt) <= coder.getSubRange().getScale() ? 0 : 1);
            model.incRunLength(model.getPrevSuccess());
            HiCnt += 4;
            model.getFoundState().setAddress(p.getAddress());
            model.getFoundState().setFreq(HiCnt);
            freqData.incSummFreq(4);
            if (HiCnt > 124) {
                rescale(model);
            }
            coder.getSubRange().setLowCount(0L);
            return true;
        }
        if (model.getFoundState().getAddress() == 0) {
            return false;
        }
        model.setPrevSuccess(0);
        int numstates = getNumStats();
        int i = numstates - 1;
        while ((long) (HiCnt += p.incAddress().getFreq()) <= count) {
            if (--i == 0) {
                model.setHiBitsFlag(model.getHB2Flag()[model.getFoundState().getSymbol()]);
                coder.getSubRange().setLowCount(HiCnt);
                model.getCharMask()[p.getSymbol()] = model.getEscCount();
                model.setNumMasked(numstates);
                i = numstates - 1;
                model.getFoundState().setAddress(0);
                do {
                    model.getCharMask()[p.decAddress().getSymbol()] = model.getEscCount();
                } while (--i != 0);
                coder.getSubRange().setHighCount(coder.getSubRange().getScale());
                return true;
            }
        }
        coder.getSubRange().setLowCount(HiCnt - p.getFreq());
        coder.getSubRange().setHighCount(HiCnt);
        update1(model, p.getAddress());
        return true;
    }
}
