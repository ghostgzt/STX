package kavax.microedition.lcdui;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class MIDZH implements CommandListener {

	protected void startApp() {
		flist$(this);
	}

	protected void pauseApp() {
		// notifyPaused();
	}

	protected void destroyApp(boolean flag) {
		MIDtxt.close();
		// notifyDestroyed();
	}

	public void commandAction(Command command, Displayable displayable) {
		if (displayable == flist) {
			if (command == List.SELECT_COMMAND) {
				if (flist.getString(flist.getSelectedIndex()).endsWith("/")) {
					s$fc0 += flist.getString(flist.getSelectedIndex());
					flist$(this);
				}
			} else if (command == ok)
				Current$(this, form);
			else if (command == about)
				alert$(this, "请进入放有要转换的文件的目录后按'确定'！！！", null);
			else if (command == back) {
				s$fc0 = s$fc0.substring(0, s$fc0.lastIndexOf('/', s$fc0
						.length() - 2) + 1);
				flist$(this);
			} else if (command == quit)
				destroyApp(false);
		} else if (displayable == form)
			if (command == ok)
				Ok$(this, s$fc0, tf0.getString(), tf1.getString());
			else if (command == back)
				Current$(this, flist);
	}

	static void Current$(MIDZH midlet, Displayable displayable) {
		MIDtxt.dp.setCurrent(displayable);
	}

	static void alert$(MIDZH midlet, String s, AlertType alerttype) {
		Current$(midlet, new Alert(null, s, null, alerttype));
	}

	static void flist$(MIDZH midlet) {
		midlet.flist.deleteAll();
		String as[];
		try {
			as = filelist$(midlet);
		} catch (Exception exception) {
			midlet.s$fc0 = "/";
			as = EnumerationToString(FileSystemRegistry.listRoots());
		}
		midlet.flist.setTitle(midlet.s$fc0);
		if (!midlet.s$fc0.equals("/"))
			midlet.flist.addCommand(midlet.back);
		else
			midlet.flist.removeCommand(midlet.back);
		for (int i = 0; i < as.length; i++)
			if (as[i].endsWith("/"))
				midlet.flist.append(as[i], null);

		Current$(midlet, midlet.flist);

	}

	static String[] filelist$(MIDZH midlet) throws Exception {
		Enumeration enumeration = null;
		if (!midlet.s$fc0.equals("/")) {
			FileConnection fileconnection = (FileConnection) Connector.open(
					"file://".concat(String.valueOf(midlet.s$fc0)), 1, true);
			enumeration = fileconnection.list("*", true);
			fileconnection.close();
		} else {
			enumeration = FileSystemRegistry.listRoots();
		}
		return EnumerationToString(enumeration);
	}

	static String[] EnumerationToString(Enumeration enumeration) {
		Vector vector = new Vector();
		for (; enumeration.hasMoreElements(); vector
				.addElement((String) enumeration.nextElement()))
			;
		return VectorToString(vector);
	}

	static String[] VectorToString(Vector vector) {
		String as[] = new String[vector.size()];
		for (int i = 0; i < as.length; i++)
			as[i] = (String) vector.elementAt(i);

		return as;
	}

	static byte[] ReadBytes$(MIDZH midlet, String s) throws Error, Exception {
		FileConnection fileconnection = (FileConnection) Connector.open(s
				.startsWith("file://") ? s : "file://"
				.concat(String.valueOf(s)), 1);
		byte abyte0[] = new byte[(int) fileconnection.fileSize()];
		DataInputStream datainputstream = fileconnection.openDataInputStream();
		if (abyte0.length != datainputstream.read(abyte0))
			abyte0 = null;
		datainputstream.close();
		fileconnection.close();
		datainputstream = null;
		fileconnection = null;
		return abyte0;
	}

	static void SaveBytes(String s, byte abyte0[]) throws Error, Exception {
		FileConnection fileconnection = (FileConnection) Connector.open(s
				.startsWith("file://") ? s : "file://"
				.concat(String.valueOf(s)), 3);
		if (!fileconnection.exists()) {
			fileconnection.create();
		} else {
			fileconnection.delete();
			fileconnection.create();
		}
		DataOutputStream dataoutputstream = fileconnection
				.openDataOutputStream();
		dataoutputstream.write(abyte0);
		dataoutputstream.flush();
		dataoutputstream.close();
		fileconnection.close();
		dataoutputstream = null;
		fileconnection = null;
	}

	static void mkdir(String s) throws Error, Exception {
		FileConnection fileconnection = (FileConnection) Connector.open(s
				.startsWith("file://") ? s : "file://"
				.concat(String.valueOf(s)), 3);
		if (!fileconnection.exists())
			fileconnection.mkdir();
	}

	static void Ok$(MIDZH midlet, String s, String s1, String s2) {
		boolean flag = false;
		try {
			String s3 = new String(" ".getBytes(s1), s2);
			flag = true;
		} catch (Exception exception) {
			flag = false;
		}
		if (flag)
			try {
				Vector vector = new Vector();
				String as[] = filelist$(midlet);
				for (int i = 0; i < as.length; i++)
					if (!as[i].endsWith("/"))
						vector.addElement(as[i]);

				String as1[] = VectorToString(vector);
				if (as1.length != 0) {
					mkdir(String.valueOf(s).concat("new/"));
					Code$(midlet, s, as1, s1, s2);
					alert$(midlet, "处理完毕! ", AlertType.CONFIRMATION);
				} else {
					alert$(midlet, "没有文件可处理! ", AlertType.INFO);
				}
			} catch (Error error) {
				alert$(midlet, error.toString(), AlertType.ERROR);
			} catch (Exception exception1) {
				alert$(midlet, exception1.toString(), AlertType.ERROR);
			}
		else
			alert$(midlet, "你的设备不支持该编码! ", AlertType.WARNING);
	}

	static void Code$(MIDZH midlet, String s, String as[], String s1, String s2)
			throws Error, Exception {
		for (int i = 0; i < as.length; i++)
			SaveBytes(String.valueOf(s).concat("new/").concat(
					String.valueOf(as[i])), (new String(ReadBytes$(midlet, s
					+ as[i]), s1)).getBytes(s2));

	}

	public MIDZH() {

		flist = new List(null, 3);
		form = new Form("编码");
		tf0 = new TextField("输入编码", "GB2312", 32, 0);
		tf1 = new TextField("输出编码", "UTF-8", 32, 0);
		s$fc0 = "/";
		ok = new Command("确认", 4, 0);
		about = new Command("帮助", 8, 0);
		back = new Command("返回", 2, 0);
		quit = new Command("退出", 7, 0);
		form.append(tf0);
		form.append(tf1);
		flist.addCommand(ok);
		flist.addCommand(about);
		flist.addCommand(quit);
		form.addCommand(ok);
		form.addCommand(back);
		flist.setCommandListener(this);
		form.setCommandListener(this);
		startApp();

	}

	List flist;
	Form form;
	TextField tf0;
	TextField tf1;
	String s$fc0;
	Command ok;
	Command about;
	Command back;
	Command quit;
}
