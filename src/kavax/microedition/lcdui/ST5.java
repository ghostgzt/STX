package kavax.microedition.lcdui;

import javax.microedition.lcdui.*;
import kavax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class ST5 extends MIDtxt implements CommandListener {

	public ST5() {
		screenCommand2 = new Command("����", 1, 1);
		screenCommand3 = new Command("ͳ��", 1, 1);
		screenCommandx = new Command("�˳�", 2, 1);

		tb = new TextBok("ST5 - ���±�", "", 0x186a0, 0);
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
			alt.setTitle("��������:");
			alt
					.setString("����:GhostGzt(Gentle)\nQQ:1275731466\nE-Mail:GhostGzt@163.com\nSuper Text �����(�ں�: kavaText 4.21)\n�Ϸ��̱�: Fuck Q��\nFuck Q������һ�н���Ȩ��������");
			alt.setType(AlertType.INFO);
			alt.setTimeout(-2);
			disp.setCurrent(alt, tb);
		}

		if (cmd.equals("�˳�")) {
			notifyDestroyed();
		}

		if (c == screenCommand3) {
			alt.setTitle("ͳ��:");
			alt.setString("��һ������д" + tb.getMaxSize() + "����,�����Ѿ�д��" + tb.size()
					+ "����,������д" + (tb.getMaxSize() - tb.size()) + "����.");
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
