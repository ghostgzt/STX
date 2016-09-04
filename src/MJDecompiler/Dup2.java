package MJDecompiler;

final class Dup2 extends Dup {

    Dup2(int i, int j, int k) {
        super(i, j, k);
        super.g = 2;
    }
}
