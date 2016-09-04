package bytecode;

import java.io.ByteArrayOutputStream;


public class ByteCodeOutput {

    ByteArrayOutputStream os;
    int bytenum;
   public ByteCodeOutput(){
    os=new ByteArrayOutputStream();
    bytenum=0;
    }

    void writeByte(int num) {
        os.write(num);
        bytenum++;
    }

    int getBytesWritten() {
        return bytenum;
    }

    void writeShort(int branchOffset) {
        os.write(branchOffset & 0xff00);
        os.write(branchOffset & 0xff);
        bytenum += 2;
    }

    void writeInt(int lowByte) {
        os.write(lowByte & 0xff000000);
        os.write(lowByte & 0xff0000);
        os.write(lowByte & 0xff00);
        os.write(lowByte & 0xff);
        bytenum += 4;
    }

 
}
