package kavax.microedition.lcdui;

import javax.microedition.lcdui.*;
import kavax.microedition.lcdui.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

public class Plist implements CommandListener {

	public Plist(int sd, TextField x,TextBox y) {
	MIDtxt.lastD1=MIDtxt.dp.getCurrent();
		sdk = sd;
		xi = x;
		yi=y;
		l = new List("剪辑板", 1);
		l.addCommand(c1);
		l.addCommand(c2);
		l.addCommand(cb);
		l.addCommand(c3);
		l.addCommand(ck);
		l.addCommand(c4);
		l.addCommand(c5);
		l.addCommand(cx);
		l.setCommandListener(this);
		MIDtxt.dp.setCurrent(l);
		QG(Listener.sCut);
	}

	public void Importi(String str) {
		String url = str;
		String stext = "";
		FileConnection fc = null;
		InputStream is = null;

		try {

			try {
				fc = (FileConnection) Connector.open(url);
				is = fc.openInputStream();
				byte[] b = new byte[(int) fc.fileSize()];
				is.read(b);

				try {
					stext = new String(b, 0, b.length, "utf-8");
				} catch (Exception e) {

					stext = new String(b);
				}

			} catch (Exception e) {

			} finally {
				try {
					fc.close();
					is.close();
				} catch (Exception e) {
				}
			}
			if (stext.length() > 0) {
				Listener.sCut = stext;
				l.deleteAll();
				QG(Listener.sCut);
				MIDtxt.dp.setCurrent(MIDtxt.lastD2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Alert ae = new Alert("导出文本",
					url + "\n导出失败" + "\n" + e.getMessage(), null,
					AlertType.ERROR);
			MIDtxt.dp.setCurrent(ae);

		}
	}

	public void Exporti(String str) {
		tys = str;

		new Thread() {
			public void run() {
				String url = tys;
				FileConnection fc = null;
				OutputStream os = null;
				try {
					fc = (FileConnection) Connector.open(url,
							Connector.READ_WRITE);
					if (fc.exists()) {
						fc.delete();
					}
					fc.create();
					os = fc.openOutputStream();

					String sout = "";
					try {
						sout = Listener.sCut;
					} catch (Exception e) {
					}
					try {
						os.write(sout.getBytes("utf-8"));
					} catch (Exception e) {
						os.write(sout.getBytes());
					}

					MIDtxt.dp.setCurrent(MIDtxt.lastD2);// 恢复显示

				} catch (Exception e) {
					e.printStackTrace();
					Alert ae = new Alert("导出文本", url + "\n导出失败" + "\n"
							+ e.getMessage(), null, AlertType.ERROR);
					MIDtxt.dp.setCurrent(ae);

				} finally {
					try {
						fc.close();
						os.close();
					} catch (Exception e) {
					}
				}
			}
		}.start();

	}

	public void QG(String str) {
		if (str.length() > 0 && str != "※" && str != "※※") {
			String sts = str;

			int k = sts.length();
			String tmpStr = sts;
			try {

				int k1 = tmpStr.lastIndexOf('※');
				int k2 = k1;
				int ol1 = 0;
				int ol2 = 1;
				int ol3 = 0;

				while (ol3 + ol2 - 2 < k2) {
					ol1 = tmpStr.indexOf('※');
					String jStr = tmpStr.substring(0, ol1);

					if (tmpStr.substring(ol1 + 1) == "") {
					if (tmpStr.length()>0){
						l.append(tmpStr, null);
}
						ol3 = ol3 + tmpStr.length();
					} else {
					if (jStr.length()>0){
						l.append(jStr, null);
}
						ol3 = ol3 + jStr.length();
					}

					tmpStr = tmpStr.substring(ol1 + 1);

					k1 = tmpStr.lastIndexOf('※');
					ol2 = ol2 + 1;

				}

			} catch (Exception e1) {
			}
		}
	}

	public void Add() {

		try {

			String str = tx.getString();
			if (str.length() > 0) {
				l.append(str, null);
				Listener.sCut = Listener.sCut + str + "※";
			}
		} catch (Exception e) {
		}
		MIDtxt.dp.setCurrent(MIDtxt.lastD2);
	}

	public void Edit() {
		String str = tx.getString();
		try {

			if (str.length() > 0) {
				l.set(l.getSelectedIndex(), str, null);
				int kz = l.getSelectedIndex();
				int ko = 0;
				String tst = "";
				try {
					while (l.getString(ko).length() > 0) {
						tst = tst + l.getString(ko) + "※";
						ko = ko + 1;
					}
				} catch (Exception e) {
					if (tst.length() > 0) {
						Listener.sCut = tst;
						l.deleteAll();
						QG(Listener.sCut);
						l.setSelectedIndex(kz, true);
					}
				}

			}
		} catch (Exception e) {
			l.append(str, null);
			Listener.sCut = Listener.sCut + str + "※";
		}
		MIDtxt.dp.setCurrent(MIDtxt.lastD2);
	}

	public void commandAction(Command c, Displayable d) {
		// String cmd = c.getLabel();
		if (c == c1) {
		try{
			if (sdk == 1) {
				Listener.tbk.insert(l.getString(l.getSelectedIndex()),
						Listener.tbk.getCaretPosition());
				MIDtxt.dp.setCurrent(Listener.lastD);
			} else if (sdk == 2) {
				Listener.tshow.insert(l.getString(l.getSelectedIndex()),
						Listener.tshow.getCaretPosition());
				MIDtxt.close();
			} else if(sdk==3){
				xi.insert(l.getString(l.getSelectedIndex()), xi
						.getCaretPosition());
				MIDtxt.close();
			}else{
			yi.insert(l.getString(l.getSelectedIndex()), yi
						.getCaretPosition());
				MIDtxt.close();
			}
}catch(Exception e){}
		} else if (c == c2) {

			MIDtxt.lastD2 = MIDtxt.dp.getCurrent();
			tx = new TextBox("添加字符", null, 8192, 0);
			tx.addCommand(s1);
			tx.addCommand(s2);
			tx.setCommandListener(this);
			MIDtxt.dp.setCurrent(tx);
		} else if (c == cb) {
		try{
			MIDtxt.lastD2 = MIDtxt.dp.getCurrent();
			tx = new TextBox("编辑字符",l.getString(l.getSelectedIndex()), 8192, 0);
			tx.addCommand(cbx);
			tx.addCommand(s2);
			tx.setCommandListener(this);
			MIDtxt.dp.setCurrent(tx);
			}catch(Exception e){
			MIDtxt.lastD2 = MIDtxt.dp.getCurrent();
			tx = new TextBox("添加字符", null, 8192, 0);
			tx.addCommand(s1);
			tx.addCommand(s2);
			tx.setCommandListener(this);
			MIDtxt.dp.setCurrent(tx);
			}
		} else if (c == c3) {
			try {
				MIDtxt.dp.setCurrent(l);
				l.delete(l.getSelectedIndex());

			} catch (Exception e) {
			}
		} else if (c == cbx) {
			Edit();
		} else if (c == c4) {
			String str = Listener.sCut;
			if (str.length() > 0 && str != "※" && str != "※※") {
				MIDtxt.lastD2 = MIDtxt.dp.getCurrent();
				Form f = new Form("导出剪辑板");
				Listener.oos = 1;
				tt = new TextFiles("导出路径", Listener.sFile, 1024, 0);
				f.append(tt);
				f.addCommand(s1e);
				f.addCommand(s2);
				f.setCommandListener(this);
				MIDtxt.dp.setCurrent(f);

			}

		} else if (c == c5) {
			MIDtxt.lastD2 = MIDtxt.dp.getCurrent();
			Form f = new Form("导入剪辑板");
			Listener.oos = 2;
			tt = new TextFiles("导入路径", Listener.sFile, 1024, 0);
			f.append(tt);
			f.addCommand(s1i);
			f.addCommand(s2);
			f.setCommandListener(this);
			MIDtxt.dp.setCurrent(f);

		} else if (c == cx) {
			MIDtxt.close();
		} else if (c == ck) {
			l.deleteAll();
			Listener.sCut = "";
		} else if (c == s1) {
			Add();
		} else if (c == s2) {
			MIDtxt.dp.setCurrent(MIDtxt.lastD2);
		} else if (c == s1i) {
			Importi(tt.getString());
		} else if (c == s1e) {
			Exporti(tt.getString());
		}

	}

	private Command c1 = new Command("选择", 1, 1);
	private Command c2 = new Command("添加", 1, 2);
	private Command cb = new Command("编辑", 1, 3);
	private Command c3 = new Command("删除", 1, 4);
	private Command ck = new Command("清空", 1, 5);
	private Command c4 = new Command("导出", 1, 6);
	private Command c5 = new Command("导入", 1, 7);
	private Command cx = new Command("返回", 2, 1);
	private Command s1 = new Command("添加", 1, 1);
	private Command cbx = new Command("修改", 1, 1);
	private Command s1i = new Command("导入", 1, 1);
	private Command s1e = new Command("导出", 1, 1);
	private Command s2 = new Command("取消", 2, 2);
	private List l;
	TextField xi;
		TextBox yi;
	TextBox tx;
	TextFiles tt;
	Form f;
	String tys;
	int sdk;
}