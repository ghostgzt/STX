
package kavax.tilepuzzle;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;


public class TilePuzzle  {
    Board b;

    public TilePuzzle() {
        b = new Board(kavax.microedition.lcdui.MIDtxt.mk);
        kavax.microedition.lcdui.MIDtxt.dp.setCurrent(b);
    }
}
