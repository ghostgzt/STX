package gui;

import io.FileSys;
import chen.c;
import java.io.ByteArrayInputStream;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;

public class Video extends Canvas implements Runnable {

    Showable d;
    private javax.microedition.media.Player player;
    private VideoControl vc;
    private boolean active = false;
    String url;
    int level = 10;
    int num;

    public Video(String url, Showable l) {
        this.url = url;
        d = l;
        num = 0;
        try {
            if (url.toLowerCase().endsWith("gif")) {
                player = Manager.createPlayer(new ByteArrayInputStream(FileSys.getdata(url, 0, 0)), "image/gif");
            } else if (url.toLowerCase().endsWith("mp4")) {
                player = Manager.createPlayer(url);
            } else {
                player = Manager.createPlayer("capture://video");
            }
            player.realize();
            vc = (VideoControl) (player.getControl("VideoControl"));
            vc.initDisplayMode(VideoControl.USE_DIRECT_VIDEO, c.show);
            vc.setDisplayLocation(0, 0);
            changeVolume();
            repaint();
            start();
            Light.paint = false;
        } catch (Exception e) {
            c.show.showError(e.toString());
        }
        Showhelper.show = this;
    }

    private void changeVolume() {

        if (player != null) {
            VolumeControl c = (VolumeControl) player.getControl("VolumeControl");
            if (c != null) {
                c.setLevel(level);
            }
        }
    }

    private void discardPlayer() {
        if (player != null) {
            player.close();
            player = null;
        }
        vc = null;
    }

    public void paint() {
        gc.setFont(Canvas.font);
        gc.setColor(List.bcolor);
        gc.fillRect(0, 0, Canvas.width, Canvas.height);
        gc.setColor(List.fcolor);
        gc.drawString("返回", Canvas.width, Canvas.height, Graphics.BOTTOM | Graphics.RIGHT);
        gc.drawString("开始  音量:" + level, 0, Canvas.height, Graphics.BOTTOM | Graphics.LEFT);
    }

    synchronized void start() {
        if ((player != null) && !active) {
            try {
                player.start();
                vc.setVisible(true);
            } catch (MediaException me) {
            } catch (SecurityException se) {
            }
            active = true;
        }
    }

    synchronized void stop() {
        if ((player != null) && active) {
            try {
                vc.setVisible(false);
                player.stop();
            } catch (MediaException e) {
                c.show.showError(e.toString());

            }
            active = false;
        }
    }

    public void keyRepeated(int code) {
        keyPressed(code);
    }

    public void keyPressed(int code) {
        switch (code) {
            case -5:
                num++;
                new Thread(this).start();
                break;
            case -6:
                start();
                break;
            case -7:
                Light.paint = true;
                stop();
                discardPlayer();
                Showhelper.show = d;
                break;
            case 48:
                stop();
                break;
            case -4:
                vc.setDisplayLocation(vc.getDisplayX() - 10, vc.getDisplayY());
                repaint();
                break;

            case -3:
                vc.setDisplayLocation(vc.getDisplayX() + 10, vc.getDisplayY());
                repaint();
                break;

            case -2:
                vc.setDisplayLocation(vc.getDisplayX(), vc.getDisplayY() - 10);
                repaint();
                break;

            case -1:
                vc.setDisplayLocation(vc.getDisplayX(), vc.getDisplayY() + 10);
                repaint();
                break;
            case 50:
                level += 5;
                changeVolume();
                break;
            case 56:
                level -= 5;
                changeVolume();
                break;
        }
        c.show.repaint();
    }

    public void run() {
        if (player != null) {
            try {
                byte[] pngImage = vc.getSnapshot(null);
                FileSys.savefile(url + num + ".png", pngImage, 0, 0);
                pngImage = null;
            } catch (MediaException e) {
                c.show.showError(e.toString());

            }
        }
    }
}
