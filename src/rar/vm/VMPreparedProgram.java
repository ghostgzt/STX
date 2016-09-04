package rar.vm;

import java.util.Vector;

public class VMPreparedProgram {

    private Vector Cmd, AltCmd;
    private int CmdCount;
    private Vector GlobalData, StaticData;
    private int InitR[];
    private int FilteredDataOffset, FilteredDataSize;

    public VMPreparedProgram() {
        Cmd = new Vector();
        AltCmd = new Vector();
        GlobalData = new Vector();
        StaticData = new Vector();
        InitR = new int[7];
        AltCmd = null;
    }

    public Vector getAltCmd() {
        return AltCmd;
    }

    public void setAltCmd(Vector altCmd) {
        AltCmd = altCmd;
    }

    public Vector getCmd() {
        return Cmd;
    }

    public void setCmd(Vector cmd) {
        Cmd = cmd;
    }

    public int getCmdCount() {
        return CmdCount;
    }

    public void setCmdCount(int cmdCount) {
        CmdCount = cmdCount;
    }

    public int getFilteredDataOffset() {
        return FilteredDataOffset;
    }

    public void setFilteredDataOffset(int filteredDataOffset) {
        FilteredDataOffset = filteredDataOffset;
    }

    public int getFilteredDataSize() {
        return FilteredDataSize;
    }

    public void setFilteredDataSize(int filteredDataSize) {
        FilteredDataSize = filteredDataSize;
    }

    public Vector getGlobalData() {
        return GlobalData;
    }

    public void setGlobalData(Vector globalData) {
        GlobalData = globalData;
    }

    public int[] getInitR() {
        return InitR;
    }

    public void setInitR(int initR[]) {
        InitR = initR;
    }

    public Vector getStaticData() {
        return StaticData;
    }

    public void setStaticData(Vector staticData) {
        StaticData = staticData;
    }
}
