package MJDecompiler;

final class dod extends db {

    final Operation a(Operation ai1, CodeInfo ah1) {
        Operation ai2;
        if (!((ai2 = ai1) instanceof af)) {
            return null;
        }
        af af1 = (af) ai2;
        if (!((ai2 = ah1.getOperation(ai1.b(0))) instanceof bd) || ai2.getIndex() != 1) {
            return null;
        }
        bd bd1 = (bd) ai2;
        if (!((ai2 = ah1.getOperation(ai1.b(1))) instanceof bd) || ai2.getIndex() != 1) {
            return null;
        }
        bd bd2 = (bd) ai2;
        if (bd1.b(0) != bd2.b(0)) {
            return null;
        } else {
            super.a++;
            Object obj;
            obj = ((aj) (aj) (obj = new v(af1.a, bd2, bd1))).a(af1).b(bd1);
            ah1.deleteOperation(af1);
            ah1.deleteOperation(bd2);
            ah1.deleteOperation(bd1);
            ah1.getOperation(bd2.b(0)).minusIndex();
            return ah1.setOperation((Operation) (Operation) obj);
        }
    }
}
