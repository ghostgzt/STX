package MJDecompiler;

import java.io.IOException;

final class Access {

    int acc;

    Access(ClazzInputStream bc1) throws IOException {
        acc = bc1.readUShort();
    }

    final void writeBytecode(ByteCodeOutput bf1) throws IOException {
        bf1.writeUshort(acc);
    }

    boolean isInterface() {
        return (acc & 512) > 0;
    }

    boolean isStatic() {
        return (acc & 8) > 0;
    }

    boolean isFinal() {
        return (acc & 16) > 0;
    }

    public final String toString() {

        StringBuffer sb = new StringBuffer();
        if ((acc & 1) > 0) {
            sb.append("public ");
        }
        if ((acc & 2) > 0) {
            sb.append("private ");
        }
        if ((acc & 4) > 0) {
            sb.append("protected ");
        }
        if ((acc & 8) > 0) {
            sb.append("static ");
        }
        if ((acc & 16) > 0) {
            sb.append("final ");
        }

        if ((acc & 64) > 0) {
            sb.append("volatile ");
        }

        if ((acc & 128) > 0) {
            sb.append("transient ");
        }
        if ((acc & 256) > 0) {
            sb.append("native ");
        }

        if ((acc & 1024) > 0) {
            sb.append("abstract ");
        }
        if ((acc & 2048) > 0) {
            sb.append("strict ");
        }
        if ((acc & 512) > 0) {
            sb.append("interface ");
        }

        return sb.toString();


    }
}
