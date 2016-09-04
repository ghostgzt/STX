package kavax.microedition.lcdui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.*;

public class TextFed extends TextField implements ItemCommandListener {

	public ItemCommandListener iicl;

	public TextFed(String s, String s1, int i, int j) {

		super(s, s1, 1024, 0);

		super.setItemCommandListener(this);

		addCommand(MIDtxt.fdcopy);
		addCommand(MIDtxt.fdpaste);
		addCommand(MIDtxt.fdkong);
		addCommand(MIDtxt.fdtq);
	}

	public void commandAction(Command cd, Item item) {

		// MIDtxt.lastD=MIDtxt.dp1.getCurrent();
		if (cd.equals(MIDtxt.fdcopy)) {
			Listener.sCut =((TextField) this).getString()
					+ "¡ù"+  Listener.sCut ;
		} else if (cd.equals(MIDtxt.fdpaste)) {
			MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
			Plist xlx = new Plist(3, (TextField) this,null);

			// ((TextField)this).insert(
			// Listener.sCut,((TextField)this).getCaretPosition());

		} else if (cd.equals(MIDtxt.fdkong)) {
			((TextField) this).setString(null);
		} else if (cd.equals(MIDtxt.fdtq)) {
			try {
				Listener.tbk.setString(((TextField) this).getString());
			} catch (Exception e1) {
			}
			try {
				Listener.tfk.setString(((TextField) this).getString());
			} catch (Exception e2) {
			}
			MIDtxt.dp.setCurrent(Listener.lastD);
		}

	}

	public void setItemCommandListener(ItemCommandListener icl) {

		iicl = icl;

		super.setItemCommandListener(this);

	}
}
