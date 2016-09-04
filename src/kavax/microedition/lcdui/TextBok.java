package kavax.microedition.lcdui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;

public class TextBok extends javax.microedition.lcdui.TextBox {

	public String getString() {// 使用自己存储的字符串

		sS = sS.length() == 0 ? super.getString() : sS;

		return !brS ? super.getString() : sS;
	}

	public void add() {
		addCommand(Listener.cx1);
		addCommand(Listener.cx2);
	}

	public void setString(String s) {

		super.setString(s);

		this.sS = s;
	}

	public String sS = "";
	public int ki = 0;
	public int oi = 0;
	public static boolean brS = false; // 是否强制托管字符串

	public Command cOK = new Command("_OK", 1, 1);

	public Command cCnf = new Command("_OK", 1, 1);

	private int iCnfP = 2;// 用于记录源程序加的菜单的优先性

	public TextBok(String s, String s1, int i, int j) {

		super(s, s1, i, j);
		Listener.rmsDo(false);
		if (Listener.sty == "是") {
			Listener.sty = "是";
			Listener.tyCP = true;
			Listener.uty = 0;
		} else {
			Listener.sty = "不是";
			Listener.tyCP = false;
			Listener.uty = j;
		}
		setConstraints(Listener.uty);
		oi = j;
		if (Listener.iMax == 0) {
		} else {
			setMaxSize(Listener.iMax);
		}

		this.sS = s1;
		ki = i;

		if (MIDtxt.l == null) {

			MIDtxt.l = new Listener();
		}

		super.setCommandListener(MIDtxt.l);

		if (!Listener.hideCP) { // 如果非隐藏菜单模式

			Listener.hideMenu(this, false);

		}

		// addCommand(Listener.cCnf);

		addCommand(Listener.cImt);

		addCommand(Listener.cExt);

		addCommand(Listener.cFn);

		addCommand(Listener.cGoo);

	}

	public void addCommand(Command c) { // 找到显示在第一位的菜单

		System.out.println("加菜单");

		try {

			int i = c.getPriority();

			if (i < iCnfP) {

				if (c.equals(Listener.cCopy) || c.equals(Listener.cPaste)
						|| c.equals(Listener.cDel) || c.equals(Listener.cClear)
						|| c.equals(Listener.cFn) || c.equals(Listener.cImt)
						|| c.equals(Listener.cExt) || c.equals(cCnf)
						|| c.equals(Listener.cGoo) || c.equals(Listener.cx1)
						|| c.equals(Listener.cx2)) { // ///原带菜单

					super.addCommand(c);

					// System.out.println(c.getLabel());

					// //

				} else {

					iCnfP = i;

					// super.addCommand(Listener.cCnf); //加入上一个未加入按钮

					cOK = c;// 更改索引

					if (cCnf != null) {

						removeCommand(cCnf);
					}

					if (!Listener.sameCmd(cOK, new Command("_OK", 1, 1))) {

						super.addCommand(cCnf = new Command(cOK.getLabel(), cOK
								.getCommandType(), 1));
					}

				}

			} else {

				super.addCommand(c);
			}

		} catch (Exception e) {

			super.addCommand(c);

		}

	}

	public void setCommandListener(CommandListener ccl) {

		Listener.ccl = ccl;

		// System.out.println("ccl"+ccl);

		super.setCommandListener(MIDtxt.l);

	}
}
