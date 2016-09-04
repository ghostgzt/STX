package kavax.microedition.lcdui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.*;

public class TextFiles extends TextField implements ItemCommandListener {

	public ItemCommandListener iicl;

	public TextFiles(String s, String s1, int i, int j) {

		super(s, s1, 1024, 0);

		super.setItemCommandListener(this);

		addCommand(MIDtxt.cFile);

	}

	public void commandAction(Command cd, Item item) {

		if (cd.equals(MIDtxt.cFile)) { // ///_浏览菜单
			MIDtxt.lastD = MIDtxt.dp1.getCurrent();
			fileSelector.fs = null;
			if (fileSelector.fs == null) {

				fileSelector fs = new fileSelector("文件列表", 3, MIDtxt.dp1,
						MIDtxt.lastD, this);

				MIDtxt.dp1.setCurrent(fs);

			} else {

				MIDtxt.dp1.setCurrent(fileSelector.fs);
			}

		} else {

			iicl.commandAction(cd, item);

			try {

				MIDtxt.lastD = (Displayable) iicl; // 获取显示对象,注意是否会引起不良反应

			} catch (Exception e) {
			}

		}

	}

	public void setItemCommandListener(ItemCommandListener icl) {

		iicl = icl;

		super.setItemCommandListener(this);

	}
}
