package rar.ppm;

public class SEE2Context {

    private int summ, shift, count;

    public void init(int initVal) {
        shift = 3;
        summ = initVal << shift & 0xffff;
        count = 4;
    }

    public int getMean() {
        int retVal = summ >>> shift;
        summ -= retVal;
        return retVal + (retVal != 0 ? 0 : 1);
    }

    public void update() {
        if (shift < 7 && --count == 0) {
            summ += summ;
            count = 3 << shift++;
        }
        summ &= 0xffff;
        count &= 0xff;
        shift &= 0xff;
    }

    public void setShift(int shift) {
        this.shift = shift & 0xff;
    }

    public void incSumm(int dSumm) {
        summ = (summ + dSumm) & 0xffff;
    }
}
