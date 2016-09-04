package rar.ppm;

import java.io.IOException;
import rar.unpack.Unpack;

public class RangeCoder {

    public static class SubRange {

        private long lowCount;
        private long highCount;
        private long scale;

        public long getHighCount() {
            return highCount;
        }

        public void setHighCount(long highCount) {
            this.highCount = highCount & 0xffffffffL;
        }

        public long getLowCount() {
            return lowCount & 0xffffffffL;
        }

        public void setLowCount(long lowCount) {
            this.lowCount = lowCount & 0xffffffffL;
        }

        public long getScale() {
            return scale;
        }

        public void setScale(long scale) {
            this.scale = scale & 0xffffffffL;
        }

        public void incScale(int dScale) {
            setScale(getScale() + (long) dScale);
        }

        public SubRange() {
        }
    }
    private long low;
    private long code;
    private long range;
    private final SubRange subRange = new SubRange();
    private Unpack unpackRead;

    public RangeCoder() {
    }

    public SubRange getSubRange() {
        return subRange;
    }

    public void initDecoder(Unpack unpackRead)
            throws IOException {
        this.unpackRead = unpackRead;
        low = code = 0L;
        range = 0xffffffffL;
        for (int i = 0; i < 4; i++) {
            code = (code << 8 | (long) getChar()) & 0xffffffffL;
        }

    }

    public int getCurrentCount() {
        range = range / subRange.getScale() & 0xffffffffL;
        return (int) ((code - low) / range);
    }

    public long getCurrentShiftCount(int SHIFT) {
        range = range >>> SHIFT;
        return (code - low) / range & 0xffffffffL;
    }

    public void decode() {
        low = low + range * subRange.getLowCount() & 0xffffffffL;
        range = range * (subRange.getHighCount() - subRange.getLowCount()) & 0xffffffffL;
    }

    private int getChar()
            throws IOException {
        return unpackRead.getChar();
    }

    public void ariDecNormalize()
            throws IOException {
        for (boolean c2 = false; (low ^ low + range) < 0x1000000L || (c2 = range < 32768L); low = low << 8 & 0xffffffffL) {
            if (c2) {
                range = -low & 32767L & 0xffffffffL;
                c2 = false;
            }
            code = (code << 8 | (long) getChar()) & 0xffffffffL;
            range = range << 8 & 0xffffffffL;
        }

    }
}
