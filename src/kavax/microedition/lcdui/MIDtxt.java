package kavax.microedition.lcdui;

//import com.nokia.mid.ui.DeviceControl;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;

import javax.microedition.lcdui.*;

import javax.microedition.pim.PIM;
import kavax.tilepuzzle.*;
import kavax.pushpuzzle.*;
import kavax.wormgame.*;

public abstract class MIDtxt extends MIDlet implements CommandListener {
	public static MIDtxt mk = null;
	public static MIDtxt mk1 = null;
	public static Listener l;

	public static Display dp;
	public static Display dp1;

	public static String sFile;

	public static Command cAdd = new Command("���", 1, 1);
	public static Command cEnt = new Command("�س�", 1, 2);
	public static Command cFile = new Command("_���", 1, 1);
	public static Command fdcopy = new Command("_����", 1, 1);
	public static Command fdpaste = new Command("_ճ��", 1, 2);
	public static Command fdkong = new Command("_���", 1, 3);
	public static Command fdtq = new Command("_��ȡ", 1, 4);
	public static Displayable lastD2;
	public static Displayable lastD1;
		public static Displayable lastDx;
			public static Displayable lastDy;
				public static Displayable lastDz;
				public static Displayable back;
	public static Displayable lastD; // ��һ����ʾ����

	void rtn() {
		Display.getDisplay(this).setCurrent(new ListTypeSelectionScreen(this));
	}




	void g1() {
		lastD1 = dp.getCurrent();

		kavax.tilepuzzle.TilePuzzle d1 = new kavax.tilepuzzle.TilePuzzle();
	}

	void g2() {

		lastD1 = dp.getCurrent();
		kavax.pushpuzzle.PushPuzzle d2 = new kavax.pushpuzzle.PushPuzzle();
	}

	void g3() {

		lastD1 = dp.getCurrent();
		kavax.wormgame.WormMain d2 = new kavax.wormgame.WormMain();

	}

	public static void close() {
		dp.setCurrent(lastD1);
	}
	
	public static void back() {
		dp.setCurrent(lastDx);
	}

	public static void exit() {
		dp.setCurrent(Listener.lastD);
	}

	void reportException(Exception e, Displayable d) {
		Alert alert = new Alert("Error", e.getMessage(), null, AlertType.ERROR);
		alert.setTimeout(Alert.FOREVER);
		Display.getDisplay(this).setCurrent(alert, d);
		e.printStackTrace();
	}

	public TextBok call(String title, String str, int zx, int ys) {
		Listener.lastD = dp.getCurrent();
		back = dp.getCurrent();
		TextBok tk = new TextBok(title, str, zx, ys);
		tk.add();
		tk.setTicker(new Ticker("��ӭʹ��ST5,Made By GhostGzt(Gentle),Fuck Q��"));
		dp.setCurrent(tk);
		return tk;
	}

	public MIDtxt()

	{
   

		if (mk == null) {
			if (mk1 == null) {
				try {

					mk1 = this;

					dp1 = Display.getDisplay(this);

					fileSelector.getImage();// //��ȡͼƬ

				} catch (Exception e) {
				}
			}
			mk = this;

			dp = Display.getDisplay(this);

			l = new Listener();

			System.out.println("l=" + l);

			Listener.sSet = "����=" + Listener.iMax + "���Ƴ���=" + Listener.iLen
					+ "����=" + Listener.sHide + "�ļ�=" + Listener.sFile;
			Listener ll = new Listener();
			ll.ReadMF();

			Listener.rmsDo(false); // //��

			try {

				textCanvas.ikDel = Integer.parseInt(mk
						.getAppProperty("keyDelete"));

			} catch (Exception e) {
			}

			System.out.println("keyDelete=" + textCanvas.ikDel);

			try {

				FileConnection fc = (FileConnection) Connector
						.open("file:///x/x.x");

			} catch (Exception e) {
			}

			System.out.println("there is it");
		}
	
		// rtn();
	}

}