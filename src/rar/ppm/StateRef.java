package rar.ppm;

public class StateRef {

    private int symbol,freq,successor;

   
    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol & 0xff;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq & 0xff;
    }

    public void incFreq(int dFreq) {
        freq = freq + dFreq & 0xff;
    }

    public void decFreq(int dFreq) {
        freq = freq - dFreq & 0xff;
    }

    public void setValues(State statePtr) {
        setFreq(statePtr.getFreq());
        setSuccessor(statePtr.getSuccessor());
        setSymbol(statePtr.getSymbol());
    }

    public int getSuccessor() {
        return successor;
    }

    public void setSuccessor(PPMContext successor) {
        setSuccessor(successor.getAddress());
    }

    public void setSuccessor(int successor) {
        this.successor = successor;
    }
}
