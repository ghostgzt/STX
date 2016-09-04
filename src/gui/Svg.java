package gui;

import io.FileSys;
import chen.c;
import java.io.InputStream;
import javax.microedition.m2g.SVGAnimator;
import javax.microedition.m2g.SVGImage;
import javax.microedition.m2g.ScalableGraphics;
import javax.microedition.m2g.ScalableImage;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGSVGElement;

public class Svg extends Canvas  {

    private SVGSVGElement svgRoot;
    private SVGAnimator svgAnimator;
    Showable d;
    boolean play = true;
    private long sleeptime;
    private ScalableGraphics sg;
    private SVGImage svgImage;

    public void open(String svgPath, Showable p) {
        try {
            jiex = jiew = 0;
            jiey = width;
            jieh = height;
            sleeptime = 100;
            sg = ScalableGraphics.createInstance();
            sg.setRenderingQuality(ScalableGraphics.RENDERING_QUALITY_HIGH);
            d = p;
            InputStream is = FileSys.getfile(svgPath).openInputStream();
            svgImage = (SVGImage) ScalableImage.createImage(is, null);
            svgImage.setViewportHeight(Canvas.height);
            svgImage.setViewportWidth(Canvas.width);
            Document newSVGDocument = svgImage.getDocument();
            svgRoot = (SVGSVGElement) newSVGDocument.getDocumentElement();
            svgAnimator = SVGAnimator.createAnimator(svgImage);
            svgAnimator.setTimeIncrement(0.01f);
            play();
            Showhelper.show = this;
            new Thread(this).start();
        } catch (Exception e) {
            c.show.showError( e.toString());

        }
    }

    private void play() {
        try {
            svgAnimator.play();
        } catch (Exception e) {
        }
    }

    private void pause() {
        svgAnimator.pause();
    }

    private void stop() {
        try {
            svgAnimator.stop();
            svgRoot.setCurrentTime(0);
        } catch (Exception e) {
        }
    }

    public void keyPressed(int k) {
        SVGPoint p;
        try {
            switch (k) {
                case 55:
                    sleeptime -= 10;
                    if (sleeptime < 15) {
                        sleeptime = 100;
                    }
                    break;
                case 57:
                    sleeptime += 10;
                    break;
                case 35:
                    jiepin = true;
                    break;
                case -1://up
                    p = svgRoot.getCurrentTranslate();
                    p.setY(p.getY() + 5);
                    break;
                case -2://down
                    p = svgRoot.getCurrentTranslate();
                    p.setY(p.getY() - 5);
                    break;
                case -3://left
                    p = svgRoot.getCurrentTranslate();
                    p.setX(p.getX() + 5);
                    break;
                case -4://right
                    p = svgRoot.getCurrentTranslate();
                    p.setX(p.getX() - 5);
                    break;
                case 52:
                    svgRoot.setCurrentScale(svgRoot.getCurrentScale() * 1.2f);
                    break;
                case 54:
                    svgRoot.setCurrentScale(svgRoot.getCurrentScale() * 0.8f);
                    break;
                case -5:
                case 49:
                    play();
                    break;
                case 51:
                    pause();
                    break;
                case 53:
                    stop();
                    break;
                case -7:
                    stop();
                    play = false;
                    Showhelper.show = d;
            }
        } catch (Exception e) {
        }
       repaint();
    }

    public void paint() {
        try {
            gc.setColor(255, 255, 255);
            gc.fillRect(0, 0, width, height);
            sg.bindTarget(gc);
            sg.setTransparency(1f);
            sg.render(0, 0, svgImage);
            sg.releaseTarget();
        } catch (Exception e) {
        }
    }

    public void keyRepeated(int key) {
        keyPressed(key);
    }

    public void run() {
        while (play) {
            try {
                if (!jiepin) {
                    Thread.sleep(sleeptime);
                    repaint();
                } else {
                    super.run();
                    jiepin = false;
                }
            } catch (InterruptedException ex) {
            }
        }
    }
}
