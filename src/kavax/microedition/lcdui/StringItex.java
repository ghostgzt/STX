package kavax.microedition.lcdui;

import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.*;

public class StringItex extends StringItem implements ItemCommandListener {

	public ItemCommandListener iicl;

	public StringItex(String str1, String str2, int any) {

		super(str1, str2, any);
		super.setItemCommandListener(this);

		addCommand(MIDtxt.cAdd);
		addCommand(MIDtxt.cEnt);
	}

	public void commandAction(Command cd, Item item) {

		// MIDtxt.lastD=MIDtxt.dp1.getCurrent();
		if (cd.equals(MIDtxt.cAdd)) {
			MIDtxt.dp.setCurrent(MIDtxt.lastD1);
			MIDtxt.lastD1 = null;
			// Listener.zres=Listener.zres+String.valueOf(((StringItem)this).getText());
			int p = ((StringItem) this).getText().indexOf('.');
			if (String.valueOf(((StringItem) this).getText()).substring(p + 1,
					(((StringItem) this).getText().length()) - 1).length() != 0) {
				Listener.tbx.insert(String.valueOf(
						((StringItem) this).getText()).substring(p + 1,
						(((StringItem) this).getText().length()) - 1),
						Listener.tbx.getCaretPosition());

			} else {
				Listener.tbx.insert(String.valueOf(
						((StringItem) this).getText()).substring(p + 1,
						(((StringItem) this).getText().length())), Listener.tbx
						.getCaretPosition());

			}
			Listener.tbk.setString(Listener.tbx.getString());
			// System.out.println(String.valueOf(((StringItem)this).getText()));

		} else if (cd.equals(MIDtxt.cEnt)) {
			MIDtxt.dp.setCurrent(MIDtxt.lastD1);
			MIDtxt.lastD1 = null;
			Listener.tbx.insert("\n", Listener.tbx.getCaretPosition());
			Listener.tbk.setString(Listener.tbx.getString());
		}

	}

	public void setItemCommandListener(ItemCommandListener icl) {

		iicl = icl;

		super.setItemCommandListener(this);

	}
}
