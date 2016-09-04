package gui;

import chen.c;

public class Light implements Runnable {

    public static boolean alwaysLight = false;
    c mid;
    public static boolean paint=true;

    public Light(c m) {
        mid = m;
    }

    public void run() {

        while (true) {
            try {
                Thread.sleep(5000);
                if (alwaysLight) {
                    
                }
                if (paint) {
                    c.show.repaint();
                }
                if (System.currentTimeMillis() - Showhelper.time > 600000) {
                    mid.destroyApp(false);
                }
            } catch (Throwable ex) {
                alwaysLight = false;
                c.show.show("light", ex.toString());
            }

        }
    }
}


