package gui;

import io.FileSys;
import chen.c;
import java.io.ByteArrayInputStream;

import javax.microedition.lcdui.*;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public class Player extends Canvas implements Runnable, PlayerListener {

    javax.microedition.media.Player med;
    boolean first;
    List his;
    //   private InputStream is;
    FileNode urls;
    String name;
    private int hs;
    int level;
    int type;
    int x, y, z, mh;
    byte[] data;
    boolean zip;
    private boolean run;
    private int h;

    public Player(String fg, int type, List brow) {
        urls = c.browser.getDir();
        his = brow;
        name = fg;
        hs = c.browser.hs;
        level = 20;
        this.type = type;
        zip = false;
        first = true;

    }

    public Player(byte[] tb, String nam, int p, List browser) {
        urls = c.browser.getDir();
        name = nam;
        data = tb;
        his = browser;
        hs = c.browser.hs;
        level = 20;
        type = p;
        zip = true;
        first = true;
    }

    private String guess(int t) {

        switch (t) {
            case 108104:// mid
                return "audio/midi";
            case 96323:// aac
                return "audio/aac";
            case 3124:// au
                return "audio/basic";
            case 117484:// wav
                return "audio/audio/x-wav";
            case 105577:// jts
                return "audio/x-tone-seq";
       ///    case 108273://mp4
         //       return "video/mpeg";
         //       return "audio/amr";
            default:
                //mp3
                return "audio/mpeg";
        }

    }

    public void keyRepeated(int keyCode) {
        keyPressed(keyCode);
    }

    public void keyPressed(int code) {
        switch (code) {
            case -2:
                down();

                break;

            case -1:
                up();

                break;

            case 50:
                for (int i = urls.zhs - 1; i > 0; i--) {
                    hs--;
                    if (hs < 1) {
                        hs = urls.zhs;
                    }

                    if (urls.image[hs - 1] == 2) {
                        if (!zip) {
                            name = name.substring(0, name.lastIndexOf('/') + 1) + urls.show[hs - 1];
                            type = c.guesstype(urls.show[hs - 1]);
                        } else {
                            name = urls.show[hs - 1];
                            data = c.zipsy.getdata(name);
                        }
                        init();
                        break;
                    }
                }
                break;
            case -5:
            case 56:


                for (int i = urls.zhs - 1; i > 0; i--) {
                    hs++;
                    if (urls.zhs < hs) {
                        hs = 1;
                    }

                    if (urls.image[hs - 1] == 2) {
                        if (!zip) {
                            name = name.substring(0, name.lastIndexOf('/') + 1) + urls.show[hs - 1];
                            type = c.guesstype(urls.show[hs - 1]);
                        } else {
                            name = urls.show[hs - 1];
                            data = c.zipsy.getdata(name);
                        }

                        init();
                        break;
                    }
                }
                break;
            case 48:
                if (med != null) {
                    try {
                        if (med.getState() == javax.microedition.media.Player.STARTED) {
                            med.stop();
                        } else {
                            med.start();
                        }
                    } catch (MediaException ex) {
                    }
                }
                break;
            case 35:
                jiepin = true;
                jiey = width;
                jieh = height;
                break;
            case -3:
            case 55:
                try {
                    med.setMediaTime(med.getMediaTime() - 5000000);
                } catch (MediaException ex) {
                }
                break;
            case -4:
            case 57:
                try {
                    med.setMediaTime(med.getMediaTime() + 5000000);
                } catch (MediaException ex) {
                }
                break;
            case -8:
                run = false;
                Showhelper.show = c.browser;
                break;
            case -7:
                if (med != null) {
                    try {
                        run = false;
                        med.deallocate();
                        med.close();


                    } catch (Exception ex) {
                    }
                }
                Showhelper.show = c.browser;
                break;
        }
        repaint();
    }

    public void paint() {
        if (first) {
            gc.setFont(Canvas.font);
            h = Canvas.font.getHeight() + 2;
            first = false;
        }
        gc.setColor(List.bcolor);
        gc.fillRect(0, 0, Canvas.width, Canvas.height);
        gc.setColor(List.fcolor);
        gc.drawString(name, 3, 8, Graphics.LEFT | Graphics.TOP);
        gc.drawString("音量:  " + level, 3, 8 + h, Graphics.LEFT | Graphics.TOP);
        gc.drawString("调低音量  dowm", 3, 8 + 3 * h, Graphics.LEFT | Graphics.TOP);
        gc.drawString("调高音量    up", 3, 8 + 2 * h, Graphics.LEFT | Graphics.TOP);
        gc.drawString("前一首   2", 3, 8 + 4 * h, Graphics.LEFT | Graphics.TOP);
        gc.drawString("后一首   8", 3, 8 + 5 * h, Graphics.LEFT | Graphics.TOP);
        gc.drawString("时间: " + med.getMediaTime() / 1000000 + " s 总时间 " + med.getDuration() / 1000000 + " s", 3, 8 + 6 * h, Graphics.LEFT | Graphics.TOP);
        gc.drawString("类型:  " + med.getContentType(), 3, 8 + 7 * h, Graphics.LEFT | Graphics.TOP);
        gc.drawString("返回", width - 2, height - 2, 72);
    }

    private void changeVolume() {

        if (med != null) {
            VolumeControl vc = (VolumeControl) med.getControl("VolumeControl");
            if (vc != null) {
                vc.setLevel(level);
            }
        }
    }

    public void run() {
        init();
        while (run) {
            if (jiepin) {
                super.run();
                jiepin = false;
            } else {
                try {
                    Thread.sleep(300);
                    chen.c.show.repaint(3, 8 + 6 * h, width, h);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private void down() {
        level -= 5;
        if (level < 0) {
            level = 100;
        }
        changeVolume();
    }

    private void init() {
        run = true;
        Showhelper.show = this;
        if (med != null) {
            try {
                med.close();
                //      is.close();

            } catch (Exception ex) {
            }
        }
        try {
            if (!zip) {
                if (name.endsWith("mid")) {
                    data = FileSys.getdata(name, 0, 0);
                    med = Manager.createPlayer(new ByteArrayInputStream(data), "audio/midi");
                } else {
                    med = Manager.createPlayer(name);
                }
            } else {
                med = Manager.createPlayer(new ByteArrayInputStream(data), guess(type));
            }
            med.realize();
            med.prefetch();
            med.addPlayerListener(this);
            changeVolume();
            med.start();
        } catch (Exception e) {
            c.show.showError(e.toString());
        }
    }

    private void up() {
        level += 5;
        if (level > 100) {
            level = 0;
        }
        changeVolume();
    }

    public void playerUpdate(javax.microedition.media.Player player, String event, Object eventData) {

        if (event.equals("endOfMedia")) {
            keyPressed(-5);
        }
        repaint();
    }
}
