package kavax.microedition.lcdui;

import javax.microedition.lcdui.*;
import kavax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class ST5 extends MIDtxt implements CommandListener {

	public ST5() {
		screenCommand2 = new Command("关于", 1, 1);
		screenCommand3 = new Command("统计", 1, 1);
		screenCommandx = new Command("退出", 2, 1);

		tb = new TextBok("ST5 - 记事本", "", 0x186a0, 0);
	}

	protected void startApp()
	// throws MIDletStateChangeException
	{
		Display.getDisplay(this).setCurrent(tb);
		System.out.println("startApp");
		disp = Display.getDisplay(this);
		alt = new Alert("");

		tb.addCommand(screenCommand3);
		tb.addCommand(screenCommand2);
		tb.addCommand(screenCommandx);
		tb.setCommandListener(this);

		disp.setCurrent(tb);
	}

	protected void pauseApp() {
		System.out.println("pauseApp");
	}

	protected void destroyApp(boolean arg0)
	// throws MIDletStateChangeException
	{
		System.out.println("destroyApp");
	}

	public void commandAction(Command c, Displayable d) {
		String cmd = c.getLabel();

		if (c == screenCommand2) {
			alt.setTitle("关于作者:");
			alt
					.setString("作者:GhostGzt(Gentle)\nQQ:1275731466\nE-Mail:GhostGzt@163.com\nSuper Text 第五版(内核: kavaText 4.21)\n合法商标: Fuck Q！\nFuck Q！保留一切解释权利！！！");
			alt.setType(AlertType.INFO);
			alt.setTimeout(-2);
			disp.setCurrent(alt, tb);
		}

		if (cmd.equals("退出")) {
			notifyDestroyed();
		}

		if (c == screenCommand3) {
			alt.setTitle("统计:");
			alt.setString("您一共可以写" + tb.getMaxSize() + "个字,现在已经写了" + tb.size()
					+ "个字,还可以写" + (tb.getMaxSize() - tb.size()) + "个字.");
			alt.setType(AlertType.INFO);
			alt.setTimeout(-2);
			disp.setCurrent(alt, tb);
		}
	}

	private Command screenCommand2;
	private Command screenCommand3;

	private Command screenCommandx;
	private TextBok tb;
	private Alert alt;
	private Display disp;

}
