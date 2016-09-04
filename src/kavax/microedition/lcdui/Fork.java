package kavax.microedition.lcdui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;

public class Fork extends Form {

	public CommandListener cl; // 监听器

	public Command cFst = null; // 第一个菜单

	public void addCommand(Command c) {

		if (cFst == null) {

			cFst = c;

		} else {

			if (cFst.getPriority() > c.getPriority()) { // 取优先权较大的说

				cFst = c;
			}

		}

		super.addCommand(c);
	}

	public void setCommandListener(CommandListener cl) {

		this.cl = cl;

		System.out.println("Fork监听器");

		super.setCommandListener(cl);
	}

	public Fork(String t) {

		super(t);
	}

	public Fork(String t, Item[] ai) {

		super(t, ai);
	}

}