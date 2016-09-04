package util;

import java.util.Vector;

public class Observable {

    public Observable() {
        changed = false;
        obs = new Vector();
    }

    public synchronized void addObserver(Observer o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    public synchronized void deleteObserver(Observer o) {
        obs.removeElement(o);
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Object arg) {
        Object arrLocal[];
        label0:
        {
            arrLocal = new Object[obs.size()];
            synchronized (this) {
                if (changed) {
                    break label0;
                }
            }
            return;
        }
        obs.copyInto(arrLocal);
        clearChanged();
        //todo    observable;
        //     JVM INSTR monitorexit ;
        for (int i = arrLocal.length - 1; i >= 0; i--) {
            ((Observer) arrLocal[i]).update(this, arg);
        }

        return;
    }

    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    protected synchronized void setChanged() {
        changed = true;
    }

    protected synchronized void clearChanged() {
        changed = false;
    }

    public synchronized boolean hasChanged() {
        return changed;
    }

    public synchronized int countObservers() {
        return obs.size();
    }
    private boolean changed;
    private Vector obs;
}
