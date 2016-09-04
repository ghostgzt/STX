package kavax.microedition.lcdui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.TextBox; //GhostGzt//
import javay.microedition.lcdui.TextBoz;
import javaz.microedition.lcdui.TextBow; //import java.io.PrintStream;

import javax.microedition.pim.PIM;

//GhostGzt//
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;
//import com.nokia.mid.ui.DeviceControl;
import java.io.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import java.util.*;

public class Listener implements CommandListener, ItemCommandListener,
		ItemStateListener {
	public static String sCut = ""; // ����������
	public static int iMax = 0;// /�ı�������
	public static int iLen = 0;// /���Ƴ���
	public static boolean hideCP = true;
	public static String sFile = "file:///E:/ST5/text.txt";
	/* GhostGzt Start */
	private String oldcontent;
	private String ooldcontent;
	private String oldstr[] = { "Ӣ��", "����(����)", "����(����)", "������������", "��������",
			"��ɳ������", "����������", "������", "������", "������", "����", "����", "����", "���ɱ���",
			"������", "�Ƕ���(������)", "������", "��̩��������(������)", "�ݿ���", "���޵�����", "����ά����",
			"��������", "����������", "�������", "Ų����", "��������", "����", "�����", "����ά����",
			"˹��ά������", "˹�工����", "̩��", "��������", "�ڿ�����", "��������", "ϣ������", "ϣ����",
			"��������", "�������", "ӡ����", "ӡ����", "Խ����" };
	private String oldstr_v[] = { "en", "zh-cn", "zh-tw", "sq", "ar", "et",
			"bg", "pl", "ko", "da", "de", "ru", "fr", "tl", "fi", "gl", "nl",
			"ca", "cs", "hr", "lv", "lt", "ro", "mt", "no", "pt", "ja", "sv",
			"sr", "sl", "sk", "th", "tr", "uk", "es", "iw", "el", "hu", "it",
			"hi", "id", "vi" };
	public String fuhao[];
	private String network[] = { "CMWAP", "CMNET" };
	// google//
	public TextField tbb;
	public static TextField tbx;
	// google//
	// replace//
	public TextField tbn;
	// replace//
	public TextFiles tfs;
	private ChoiceGroup chooseold;
	private ChoiceGroup choosenew;
	private ChoiceGroup choosenet;
	public ChoiceGroup AddFuhao;
	private ChoiceGroup FJZH;
	/*private String rres;
	private String bres;
	private String kres = "";
	private String tres = "";
	private int xres = 0;
	private String yres = "0";
	public static String zres = "";*/
	// private String xres = "";
	private String qhl[] = { "����", "����" };
	public static int ti = iMax;
	private TextBoz tx;
	public static boolean tyCP = true;
	public static String sty = "��";
	public static int uty = 0;
	//public static int tob = 0;
	public static int oos = 0;

	public static String tsfh = "file:///E:/ST5/TSFH.ini";

	private int firstmem;
	private String a;
	Form fft;
	private RecordStore rs;
	private static TextFiles tff;
	private static TextFiles tfn;
	public static int zx = 10;
	private int bx = 1;
	private int nu = 1;
	private String stxt = "";
	private String oold = "";

	private int ini = 0;
	public static Displayable lastD1;
	// private Random rand;
	/* GhostGzt Start */
	public static String sRms = "file:///E:/ST5/rms.db";
	public static String sHide = "��";
	public static String sSet = "����=" + iMax + "���Ƴ���=" + iLen + "����=" + sHide
			+ "�ļ�=" + sFile; // �������ݴ�
	// public static Command cClear,cCopy,cPaste,cSet,cImt,cExt,cCnf;
	private String oldstring = "";
	public static TextBow tshow;
	// private int is1,is2; //��һ�ڶ����λ��
	public static Displayable lastD; // ��һ����ʾ����
	public static ItemCommandListener iicl;
	public static CommandListener ccl;
	public static int iLight = 50;
	public static TextFielk tfk;
	public static TextBok tbk;

	private static boolean haveRMS = true;
	public static Command cCopy = new Command("_����", 1, 2);
	public static Command cPaste = new Command("_ճ��", 1, 3);
	public static Command cDel = new Command("_ɾ��", 1, 4);
	public static Command cClear = new Command("_���", 1, 5);
	public static Command cFn = new Command("_����", 1, 6);
	public static Command cImt = new Command("_�����ı�", 1, 7);
	public static Command cExt = new Command("_�����ı�", 1, 8);
	public static Command cGoo = new Command("_Google", 1, 9);
	public static Command cZH = new Command("_�ٻ�", 1, 10);
	public static Command cx1 = new Command("ȷ��", Command.OK, 11);
	public static Command cx2 = new Command("����", Command.BACK, 12);

	// public static String
	// sab="1.����/ճ��/���\n2.����/�����ı�\n3.RMS����/����\n4.ŵ�����ȿ���\n5.ϵͳ��Ϣ�鿴\n(���ܷ�������!)";
	// public static Command cCnf=new Command("_OK",1,1);
	// public static Command cOK;
	// ///////////////////////////
	public Listener() {
	}

	public void commandAction(Command cd, final Displayable d) {

		// System.out.println("��������ı���ť");
		if (d.equals(tshow) || d instanceof Form) {
			TextBox t = null;
			Form f = null;
			if (d instanceof TextBox) {
				System.out.println("��ô");
				t = (TextBox) d;
			} else if (d instanceof Form) {
				f = (Form) d;
			}
			int ic = cd.getPriority();
			switch (ic) {
			case 1: //
				int is1 = tshow.getCaretPosition();// ��ǰ���λ��
				int ix = tshow.getString().indexOf('��'); // ԭ��λ��
				if (-1 <= ix - is1 && ix - is1 < 1) {
					return; // λ����ͬ����������
				} else {
					String snn = tshow.getString();
					snn = snn.substring(0, ix) + snn.substring(ix + 1);
					if (is1 > ix) {
						is1 = is1 - 1;
					}
					snn = snn.substring(0, is1) + "��" + snn.substring(is1);
					tshow.setString(snn);
				}
				break;
			case 2: // ����
				int is2 = tshow.getCaretPosition();// �ڶ����λ��
				is1 = tshow.getString().indexOf('��');
				int i1 = Math.min(is1, is2);
				int i2 = Math.max(is1, is2);
				if (is1 < is2) {
					i2 = i2 - 1;
				}
				if (-1 <= is1 - is2 && is1 - is2 <= 1) {// ���λ����ͬ
					if (is1 >= oldstring.length()) { // /// ���λ����ĩβ����ȫ��

						// sCut = oldstring;
						if (oldstring.length() > 0) {
							sCut = oldstring + "��"+ sCut ;
						}
					} else if (iLen != 0 && iLen < oldstring.length() - is1) { // ///
						// ����һ�������ַ���
						// sCut = oldstring.substring(is1, is1 + iLen);

						if (oldstring.substring(is1, is1 + iLen).length() > 0) {
							sCut =oldstring.substring(is1, is1 + iLen)
									+ "��"+ sCut  ;
						}
					} else if (iLen == 0 || iLen >= oldstring.length() - is1) { // ///����֮�������ַ���
						// sCut = oldstring.substring(is1);
						if (oldstring.substring(is1).length() > 0) {
							sCut = oldstring.substring(is1) + "��" +sCut ;
						}
					}
				} else { // ���渴��
					// sCut = oldstring.substring(i1, i2);

					if (oldstring.substring(i1, i2).length() > 0) {
						sCut =  oldstring.substring(i1, i2) + "��"+sCut ;
					}
				}
				rmsDo(true);
				// System.out.println("���ƵĶ���:"+sCut+is1+is2);

				MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				break;
			case 3: // ȡ��
				/*bres = "";
				kres = "";

				zres = "";*/

				MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				/*
				 * if (cd.getCommandType() == 7) { // ��Ҫ�洢���� rmsDo(true);// д������ }
				 */
				break;
			case 4: // ����ȷ��
				String oldsSet = sSet;
				int oldiMax = iMax;
				int oldiLen = iLen;
				String oldsFile = sFile;
				String oldtsfh = tsfh;
				int oldzx = zx;
				try {
					// String sset = tshow.getString();// ��ȡ�����ַ���
					// int p1 = sset.indexOf("���Ƴ���=");
					// iMax = Integer.parseInt(sset.substring("����=".length(),
					// p1));
					iMax = Integer.parseInt(tbx.getString());
					try {
						if (iMax == 0) {
							tfk.setMaxSize(tfk.ki);
						} else {
							tfk.setMaxSize(iMax);
						}
					} catch (Exception e) {
					} finally {
						try {
							if (iMax == 0) {
								tbk.setMaxSize(tbk.ki);
							} else {
								tbk.setMaxSize(iMax);
							}
						} catch (Exception e) {
						}
					}
					// int p2 = sset.indexOf("����=");
					// iLen = Integer.parseInt(sset.substring(p1+
					// "���Ƴ���=".length(), p2));
					iLen = Integer.parseInt(tbb.getString());
					zx = Integer.parseInt(tbn.getString());
					// int p3 = sset.indexOf("�ļ�=");
					/*
					 * sHide = sset.substring(p2 + "����=".length(), p3);
					 * 
					 * hideCP = sHide.equalsIgnoreCase("��"); sHide = hideCP ?
					 * "��" : "��";
					 */
					if (AddFuhao.isSelected(0)) {
						sHide = "��";
						hideCP = true;
					} else {
						sHide = "��";
						hideCP = false;
					}
					try {
						if (FJZH.isSelected(0)) {
							sty = "��";
							tyCP = true;
							tfk.setConstraints(0);
						} else {
							sty = "����";
							tyCP = false;
							tfk.setConstraints(tfk.oi);
						}
					} catch (Exception e) {
					} finally {
						try {
							if (FJZH.isSelected(0)) {
								sty = "��";
								tyCP = true;
								tbk.setConstraints(0);
							} else {
								sty = "����";
								tyCP = false;
								tbk.setConstraints(tbk.oi);

							}
						} catch (Exception e) {
						}
					}

					try {
						hideMenu(tfk, hideCP); // �����˵�
					} catch (Exception e) {
					} finally {
						try {
							hideMenu(tbk, hideCP); // �����˵�
						} catch (Exception e) {
						}
					}
					// int p4 = sset.lastIndexOf('=');
					// sFile = sset.substring(p4 + 1);
					sFile = tfn.getString();
					tsfh = tfs.getString();
					sSet = "����=" + iMax + "���Ƴ���=" + iLen + "����=" + sHide
							+ "�ļ�=" + sFile; // �������ݴ�
					System.out.println(sSet);
					rmsDo(true);
					// tfk.setString(oldstf+"_��");
					MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				} catch (Exception e) {
					e.printStackTrace();
					sSet = oldsSet; // //����ԭԭ����
					iLen = oldiLen;
					iMax = oldiMax;
					sFile = oldsFile;
					tsfh = oldtsfh;
					zx = oldzx;
					// t.setString(t.getString()+"_��");
				}
				break;
				case 5:
				new Lock().startApp();
				break;
				case 6:
				MIDtxt.close();
				break;
			case 7:// �����ı�
				// new Thread() {
				// public void run() {
				Form fft = (Form) d;
				String url = ((TextField) fft.get(0)).getString();
				FileConnection fc = null;
				InputStream is = null;

				// try{
				//	
				// fc=(FileConnection)Connector.open("file:///x/x.x");
				//	
				//	
				// }catch(Exception e){}
				try {
					fc = (FileConnection) Connector.open(url);
					is = fc.openInputStream();
					byte[] b = new byte[(int) fc.fileSize()];
					is.read(b);
					sFile = url;
					sSet = "����=" + iMax + "���Ƴ���=" + iLen + "����=" + hideCP
							+ "�ļ�=" + sFile; // �������ݴ�
					rmsDo(true);
					String stext = "";
					if (((ChoiceGroup) fft.get(1)).isSelected(0)) {// utf-8ģʽ
						stext = new String(b, 0, b.length, "utf-8");
					} else {
						stext = new String(b);
					}
					// System.out.println("tbk="+tbk);
					if (((ChoiceGroup) fft.get(2)).isSelected(0)) { // ֱ�Ӳ���
						try {
							int in = tfk.getCaretPosition();
							tfk.insert(stext, in);
						} catch (Exception e) {
						} finally {
							try {
								int in = tbk.getCaretPosition();
								tbk.insert(stext, in);
							} catch (Exception e) {
							}
						}
					} else {
						try {
							tfk.setString(stext);
						} catch (Exception e) {
						} finally {
							try {
								tbk.setString(stext);
							} catch (Exception e) {
							}
						}
					}
					// This is very dangerous��\nPlease leave quickly��

					MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ

				} catch (Exception e) {
					e.printStackTrace();
					Alert ae = new Alert("�����ı�", url + "\n����ʧ��" + "\n"
							+ e.getMessage(), null, AlertType.ERROR);
					MIDtxt.dp.setCurrent(ae);
					// fft.append("����!" );

				} finally {
					try {
						fc.close();
						is.close();
					} catch (Exception e) {
					}
				}
				// }
				// }.start();
				break;
			case 8:// �����ı�
				new Thread() {
					public void run() {
						Form f = (Form) d;
						String url = ((TextField) f.get(0)).getString();
						FileConnection fc = null;
						OutputStream os = null;
						// try{
						//	
						// fc=(FileConnection)Connector.open("file:///x/x.x");
						//	
						//	
						// }catch(Exception e){}
						boolean bw = ((ChoiceGroup) f.get(2)).isSelected(0);// ����ĩβ
						try {
							fc = (FileConnection) Connector.open(url,
									Connector.READ_WRITE);
							if (bw) {// ����ĩβ
								if (!fc.exists()) {
									fc.create();
								}
								os = fc.openOutputStream(fc.fileSize());
							} else { // ��д
								System.out.println("��д");
								if (fc.exists()) {
									fc.delete();
								}
								fc.create();
								os = fc.openOutputStream();
							}
							String sout = "�������ı�";
							try {
								sout = tfk.getString();
							} catch (Exception e) {
							} finally {
								try {
									sout = tbk.getString();
								} catch (Exception e) {
								}
							}
							if (((ChoiceGroup) f.get(1)).isSelected(0)) {// utf-8ģʽ
								os.write(sout.getBytes("utf-8"));
							} else {
								os.write(sout.getBytes());
							}
							sFile = url;
							sSet = "����=" + iMax + "���Ƴ���=" + iLen + "����="
									+ hideCP + "�ļ�=" + sFile; // �������ݴ�
							rmsDo(true);
							// tfk.setString(oldstring+"_��"); ////�ָ��ַ���
							MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ

						} catch (Exception e) {
							e.printStackTrace();
							Alert ae = new Alert("�����ı�", url + "\n����ʧ��" + "\n"
									+ e.getMessage(), null, AlertType.ERROR);
							MIDtxt.dp.setCurrent(ae);

							/* f.append("����!" + e.getMessage()); */
						} finally {
							try {
								fc.close();
								os.close();
							} catch (Exception e) {
							}
						}
					}
				}.start();
				break;
			case 9:
				fsSet(5, "ʵ�ù���(Fuck Q��)");
				break;
				case 10:
				new kavax.http.HttpView().startApp();
				break;
			case 11: // ��������
				ksk();
				break;
			case 12: // RMS����
			MIDtxt.lastD1=MIDtxt.dp.getCurrent();
				Form frms = new Form("RMS����");
				oos = 1;
				frms.append(new TextFiles("", sRms, 1024, 0));
				frms.append(new ChoiceGroup("", 1, new String[] { "����", "����" },
						null));
				frms.addCommand(new Command("ȷ��", 4, 21));
				frms.addCommand(new Command("����", 7, 6));
				frms.setCommandListener(this);
				MIDtxt.dp.setCurrent(frms);
				break;
			case 13: // ���ȵ���
			/*	Form fl = new Form("���ȵ���");
				Gauge g1 = new Gauge("", true, 100, iLight);
				// g1.setValue(iLight);
				g1.setLayout(Gauge.LAYOUT_CENTER);
				fl.append(g1);
				Gauge g2 = new Gauge("", true, 10, iLight / 10);
				g2.setLayout(Gauge.LAYOUT_CENTER);
				// g2.setValue(iLight/10);
				fl.append(g2);
				fl.addCommand(new Command("����", 7, 3));
				fl.setCommandListener(this);
				fl.setItemStateListener(this);
				MIDtxt.dp.setCurrent(fl);*/
				MIDtxt.lastD1=MIDtxt.dp.getCurrent();
				new light(iLight);
				break;
			case 14: // �򵥱༭
				// GhostGzt//
				Form ft = new Form("��ӷ���");
				tit();
				/*if (zres == "") {
					zres = tbk.getString();
				}*/
				tbx = new TextField("����", tbk.getString(), ti , 0);

				ft.append(tbx);
				FH xxk = new FH();

				fuhao = xxk.Conversion(tsfh);

				AddFuhao = new ChoiceGroup("����������", 4, fuhao, null);
				ft.append(AddFuhao);
				/*if (String.valueOf(xres) == "") {
					xres = 0;
				}
				AddFuhao.setSelectedIndex(xres, true);
*/
				tbb = new TextField("������", String.valueOf(0), 10, 0);
				ft.append(tbb);
				ft.append("");
				/*if (yres != "") {
					tbb.setString(yres);
				}*/
				ft.addCommand(new Command("ȷ��", 4, 29));
				ft.addCommand(new Command("�鿴", 1, 58));
				ft.addCommand(new Command("�ı��滻", 2, 30));
				ft.addCommand(new Command("��׺����", 2, 31));
				ft.addCommand(new Command("����ת��", 2, 35));
				ft.addCommand(new Command("�򵥱༭", 2, 43));
				ft.addCommand(new Command("����", 7, 3));
				ft.setCommandListener(this);
				MIDtxt.dp.setCurrent(ft);
				break;
			// GhostGzt//
			// break;
			case 15: // ϵͳ��Ϣ
				/*
				 * String[] stringArray = { "Option A", "Option B", "Option C",
				 * "Option D" }; // the string elements will have no images
				 * Image[] imageArray = null; List exclusiveList = new
				 * List("Exclusive", Choice.EXCLUSIVE, stringArray, imageArray);
				 * List implicitList = new List("Implicit", Choice.IMPLICIT,
				 * stringArray, imageArray); List multipleList = new
				 * List("Multiple", Choice.MULTIPLE, stringArray, imageArray);
				 * multipleList.setCommandListener(this);
				 * MIDtxt.dp.setCurrent(multipleList);
				 */

				Form fs = new Form("ϵͳ��Ϣ");

				// Button
				/*
				 * StringItem item = new StringItem("This is a StringItem label: ",
				 * "This is the StringItems text"); fs.append(item); item = new
				 * StringItem("Short label: ", "text"); fs.append(item); item =
				 * new StringItem("Hyper-Link ", "hyperlink", Item.HYPERLINK);
				 * item.setDefaultCommand(cGoo);
				 * item.setItemCommandListener(this); fs.append(item); item =
				 * new StringItem("Button ", "Button", Item.BUTTON);
				 * item.setDefaultCommand(cGoo);
				 * item.setItemCommandListener(this); fs.append(item);
				 */
				// Gauge
				/*
				 * fs.append(new Gauge("Interactive", true, 10, 0)); //
				 * NonInteractiveGaugeRunnable nonInteractive = new
				 * NonInteractiveGaugeRunnable("Non Interactive", 10, 0); new
				 * Thread(nonInteractive).start(); fs.append(nonInteractive); //
				 * fs.append(new Gauge("Indefinite - Running", false,
				 * Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING));
				 * 
				 * IncrementalIndefiniteGaugeRunnable indefinite = new
				 * IncrementalIndefiniteGaugeRunnable("Indefinite -
				 * Incremental"); new Thread(indefinite).start();
				 * fs.append(indefinite);
				 */

				fs.setTicker(new Ticker(
						"��ӭʹ��ST5,Made By GhostGzt(Gentle),Fuck Q��"));
				// fs.append(new DateField("Date", DateField.DATE));
				// fs.append(new DateField("Time", DateField.TIME));
				//fs.append(new DateField("Date & Time", DateField.DATE_TIME));
				QH gh;
				tit();
				try {
					String sti = "";
					while (sti == "") {
						Random rand = new Random();

						sti = String.valueOf(rand.nextInt() >>> 1);
						if (sti.length() > 4) {
							sti = sti.substring(0, 4);
						}
						gh = new QH();
						if (Integer.parseInt(sti) <= 7506) {
							sti = gh.CZ(0, Integer.parseInt(sti));
						} else {
							sti = "";
						}
					}
					fs.append("����һ����: " + sti + "\n");
				} catch (Exception e) {
					fs.append("����һ����: ��\n");
				}
				try {
					fs.append("TextBox����ͳ��:\n������" + String.valueOf(tbk.size())
							+ "��\n�ܿ�����" + String.valueOf(this.ti) + "��\nԭ������"
							+ String.valueOf(tbk.ki) + "��\n");
				} catch (Exception e) {
				}
				try {
					fs.append("TextField����ͳ��:\n������"
							+ String.valueOf(tfk.size()) + "��\n�ܿ�����"
							+ String.valueOf(this.ti) + "��\nԭ������"
							+ String.valueOf(tfk.ki) + "��\n");
				} catch (Exception e1) {
				}

				fs.append("������Ϣ\n");
				fs.append("Manifest-Version:"
						+ MIDtxt.mk.getAppProperty("Manifest-Version") + "\n");
				fs.append("MIDlet-Name:"
						+ MIDtxt.mk.getAppProperty("MIDlet-Name") + "\n");
				fs.append("MIDlet-Icon:"
						+ MIDtxt.mk.getAppProperty("MIDlet-Icon") + "\n");
				fs.append("MIDlet-1:" + MIDtxt.mk.getAppProperty("MIDlet-1")
						+ "\n");
				fs.append("MIDlet-Vendor:"
						+ MIDtxt.mk.getAppProperty("MIDlet-Vendor") + "\n");
				fs.append("Created-By:"
						+ MIDtxt.mk.getAppProperty("Created-By") + "\n");

				fs
						.append("MIDlet-Permissions:"
								+ MIDtxt.mk
										.getAppProperty("MIDlet-Permissions")
								+ "\n");
				fs.append("MIDlet-Permissions-Opt:"
						+ MIDtxt.mk.getAppProperty("MIDlet-Permissions-Opt")
						+ "\n");
				fs.append("MIDlet-Info-URL:"
						+ MIDtxt.mk.getAppProperty("MIDlet-Info-URL") + "\n");
				fs.append("Nokia-MIDlet-No-Exit:"
						+ MIDtxt.mk.getAppProperty("Nokia-MIDlet-No-Exit")
						+ "\n");
				fs.append("Ant-Version:"
						+ MIDtxt.mk.getAppProperty("Ant-Version") + "\n");
				fs.append("MicroEdition-Configuration:"
						+ MIDtxt.mk
								.getAppProperty("MicroEdition-Configuration")
						+ "\n");
				fs.append("MicroEdition-Profile:"
						+ MIDtxt.mk.getAppProperty("MicroEdition-Profile")
						+ "\n");
										fs.append("keyDelete:" + (MIDtxt.mk.getAppProperty("keyDelete"))
						+ "\n");
										fs.append("kfile:" + (MIDtxt.mk.getAppProperty("kfile"))
						+ "\n");
										fs.append("kfzx:" + (MIDtxt.mk.getAppProperty("kfzx"))
						+ "\n");
										fs.append("khide:" + (MIDtxt.mk.getAppProperty("khide"))
						+ "\n");
										fs.append("klen:" + (MIDtxt.mk.getAppProperty("klen"))
						+ "\n");
										fs.append("kmax:" + (MIDtxt.mk.getAppProperty("kmax"))
						+ "\n");
										fs.append("krms:" + (MIDtxt.mk.getAppProperty("krms"))
						+ "\n");
										fs.append("ktsfh:" + (MIDtxt.mk.getAppProperty("ktsfh"))
						+ "\n");
									fs.append("kty:" + (MIDtxt.mk.getAppProperty("kty"))
						+ "\n");
				fs.append("��ܰ��ʾ:�������ݽ����ο�");
				fs.append("�ֻ��ͺ�:" + System.getProperty("microedition.platform")
						+ "\n");
				fs.append("�����˴�:" + Runtime.getRuntime().totalMemory() + "\n");
				fs.append("�����˴�:" + Runtime.getRuntime().freeMemory() + "\n");
				// fs.append("�����˴�:"+Display);
				fs.append("MIDP�汾:"
						+ System.getProperty("microedition.profiles") + "\n");
				fs.append("CLDC�汾:"
						+ System.getProperty("microedition.configuration")
						+ "\n");
				fs.append("֧�ֲ�ɫ:" + MIDtxt.dp.isColor() + "\n");
				fs.append("��ɫ�Ҷ�:" + MIDtxt.dp.numColors() + "\n");
				fs.append("֧��͸��:" + MIDtxt.dp.numAlphaLevels() + "\n");
				fs.append("֧�ֱ���:" + MIDtxt.dp.flashBacklight(0) + "\n");
				fs.append("֧����:" + MIDtxt.dp.vibrate(0) + "\n");
				fs.append("֧�ֻ���:" + System.getProperty("supports.mixing")
						+ "\n");
				fs.append("֧��¼��:"
						+ System.getProperty("supports.audio.capture") + "\n");
				fs.append("֧��¼��:"
						+ System.getProperty("supports.video.capture") + "\n");
				fs.append("��Ƶ��ʽ:" + System.getProperty("audio.encodings")
						+ "\n");
				fs.append("��Ƶ��ʽ:" + System.getProperty("video.encodings")
						+ "\n");
				fs
						.append("������ʽ"
								+ System
										.getProperty("video.snapshot.encodings")
								+ "\n");
				fs.append("��ý֧��:" + System.getProperty("streamable.contents")
						+ "\n");
				fs.append("��������:"
						+ System.getProperty("wireless.messaging.sms.smsc")
						+ "\n");
				fs.append("ͼƬĿ¼:" + System.getProperty("fileconn.dir.photos")
						+ "\n");
				fs.append("��ƵĿ¼:" + System.getProperty("fileconn.dir.videos")
						+ "\n");
				fs.append("����Ŀ¼:" + System.getProperty("fileconn.dir.tones")
						+ "\n");
				fs.append("�洢Ŀ¼:"
						+ System.getProperty("fileconn.dir.memorycard") + "\n");
				fs.append("˽��Ŀ¼:" + System.getProperty("fileconn.dir.private")
						+ "\n");
				fs
						.append("�ָ�����:" + System.getProperty("file.separator")
								+ "\n");
				fs.addCommand(new Command("����", 7, 3));
				fs.setCommandListener(this);
				MIDtxt.dp.setCurrent(fs);

				break;
			case 16: // ���Ͷ���
				// GhostGzt//
				Form fr = new Form("���Ͷ���");
				tit();
				tbx = new TextField("��������", tbk.getString(), this.ti + 1024, 0);
				fr.append(tbx);
				tbb = new TextField("�ֻ�����", null, 11, 3);
				fr.append(tbb);
				fr.addCommand(new Command("ȷ��", 4, 37));
				fr.addCommand(new Command("����", 7, 3));
				fr.addCommand(new Command("�ƶ�����", 1, 38));
				fr.addCommand(new Command("��ͨ����", 1, 39));
				fr.setCommandListener(this);
				MIDtxt.dp.setCurrent(fr);
				break;
			// GhostGzt//��ֵ����
			// keyCanvas kc=new keyCanvas(lastD);
			// kc.setTitle("��ֵ����(����0����)");
			// kc.addCommand(new Command("����",2,3));
			// kc.setCommandListener(this);
			// MIDtxt.dp.setCurrent(kc);
			// break;
			case 17:
			new KeyTest();
			break;
			case 18:
			MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				kavax.tools.Tuner tur = new kavax.tools.Tuner();
		
			break;
			case 19:
   new kavax.manyballs.ManyBalls().startApp();
	
			break;
			case 20:
			 new kavax.chooser.Color().startApp();
			break;
			case 21: // RMS����ȷ��
				String jgs = "";
				String cob = "";
				try {
					String url1 = ((TextField) f.get(0)).getString();
					if (((ChoiceGroup) f.get(1)).isSelected(0)) { // ����
						rmsDo(true);// д�뵱ǰ����
						rmsOutput(url1);
						jgs = "����";
						cob = "�ɹ���";
						// f.append("�����ɹ�!\n");
					} else { // ����
						rmsInput(url1);
						jgs = "����";
						cob = "�ɹ���";
						// f.append("����ɹ�!\n");
					}
					sRms = url1;
				} catch (Exception e) {
					jgs = "����";
					cob = "ʧ�ܣ�";
					// f.append("����ʧ��!\n");
				}
				Alert ae = new Alert(jgs + "RMS", sRms + "\n" + jgs + cob,
						null, AlertType.INFO);
				MIDtxt.dp.setCurrent(ae);
				break;
			/* GhostGzt */
			case 22:
				if (!tbb.getString().equals("")) {
					tbk.setString(tbb.getString());
					MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
					if (cd.getCommandType() == 7) { // ��Ҫ�洢����
						rmsDo(true);// д������
					}
				} else {
					if (!tbx.getString().equals("")) {
						// (new Thread(new Runnable() {
						// Form f = (Form) d;

						// public void run() {
						fft = new Form("Google");
						/*
						 * try{ fft.set(4,new StringItem(null, "���ڷ�����..."));}
						 * catch(Exception e1){ fft.append("���ڷ�����..."); }
						 */
						Alert x1 = new Alert("Google", "���ڷ���...", null,
								AlertType.INFO);
						MIDtxt.dp.setCurrent(x1);

						//tob = 0;
					//	String yyt = ggb(tbx.getString());
ggb(tbx.getString());
						//if (Integer.parseInt(yyt) == 1 &&tbb.size()>0) {
							// f.append("\n");
							// f.append("������ɣ�");
							/*
							 * try{ fft.set(4,new StringItem(null, "������ɣ�"));
							 * }catch(Exception e2){ fft.append("������ɣ�");
							 *  }
							 */
						//	Alert x2 = new Alert("Google", "������ɣ�", null,
							//		AlertType.INFO);
							//MIDtxt.dp.setCurrent(x2);
						//} else {
							// f.append("\n");
							// f.append("����ʧ�ܣ�");
							/*
							 * try{ fft.set(4,new StringItem(null, "����ʧ�ܣ�"));
							 * }catch(Exception e3){ fft.append("����ʧ�ܣ�"); }
							 */
						/*	Alert x3 = new Alert("Google", "����ʧ�ܣ�", null,
									AlertType.INFO);
							MIDtxt.dp.setCurrent(x3);
						}*/
						// }
						// })).start();
					}
				}

				break;
			case 23:
				if (!tbx.getString().equals("")) {
					// (new Thread(new Runnable() {
					// Form f = (Form) d;

					// public void run() {
					fft = new Form("Google");
					// fft.set(4,new StringItem(null, "���ڷ�����..."));
					Alert x1 = new Alert("Google", "���ڷ�����...", null,
							AlertType.INFO);
					MIDtxt.dp.setCurrent(x1);
					//tob = 0;
					//String yyt = ggb(tbx.getString());
 ggb(tbx.getString());
					//if (Integer.parseInt(yyt) == 1&&tbb.size()>0) {
						// f.append("\n");
						// f.append("������ɣ�");
						// fft.set(4,new StringItem(null, "������ɣ�"));
						//Alert x2 = new Alert("Google", "������ɣ�", null,
							//	AlertType.INFO);
						//MIDtxt.dp.setCurrent(x2);
					//} else {
						// f.append("\n");
						// f.append("����ʧ�ܣ�");
						// fft.set(4,new StringItem(null, "����ʧ�ܣ�"));
					/*	Alert x3 = new Alert("Google", "����ʧ�ܣ�", null,
								AlertType.INFO);
						MIDtxt.dp.setCurrent(x3);
					}*/
					// }
					// })).start();
				}
				return;
			case 24:
				tbb.setString(oldcontent);
				return;
			case 25:
				tbx.setString(ooldcontent);
				return;
			case 26:
				gga(null);
				return;
			case 27:
				/*
				 * if (!bres.equals("")) { (new Thread(new Runnable() { Form f =
				 * (Form) d;
				 * 
				 * public void run() { tob=1; f.set(5,new StringItem(null,
				 * "���ڷ�����...")); String yyt= ggb(bres);
				 * 
				 * if (yyt=="1") { //f.append("\n"); //f.append("������ɣ�");
				 * f.set(5,new StringItem(null, "������ɣ�"));
				 *  } else { //f.append("\n"); //f.append("����ʧ�ܣ�"); f.set(5,new
				 * StringItem(null, "����ʧ�ܣ�")); } } })).start(); } return;
				 */
			case 28:
				/*
				 * tfk.setString(rres); MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ if
				 * (cd.getCommandType() == 7) { // ��Ҫ�洢���� rmsDo(true);// д������ }
				 * return;
				 */
			case 29:
				new Thread() {
					public void run() {
						Form f = (Form) d;
						String tqd = "";
						String pls = "0";
						FH xxk = new FH();

						fuhao = xxk.Conversion(tsfh);
						FH xxk1 = new FH();

						fuhao = xxk1.Conversion(tsfh);

						String iio = fuhao[AddFuhao.getSelectedIndex()];
						if (replace("|", "", iio).length() == iio.length()) {
							try {
								pls = (tbb.getString());
								if (pls.equals("0")) {
									tqd = fuhao[AddFuhao.getSelectedIndex()];
								} else {
									tqd = (fuhao[AddFuhao.getSelectedIndex()])
											.substring(
													Integer.parseInt(pls) - 1,
													Integer.parseInt(pls));
									if (tqd.equals(null)) {
										tqd = fuhao[AddFuhao.getSelectedIndex()];
									}
								}
							} catch (Exception e) {
								tqd = fuhao[AddFuhao.getSelectedIndex()];
							}

						} else {

							int k = fuhao[AddFuhao.getSelectedIndex()].length();
							String tmpStr = fuhao[AddFuhao.getSelectedIndex()];

							try {

								int k1 = tmpStr.lastIndexOf('|');
								int k2 = k1;
								int ol1 = 0;
								int ol2 = 1;
								int ol3 = 0;
								while (ol3 + ol2 - 2 < k2
										&& ol2 <= Integer.parseInt(tbb
												.getString())) {
									ol1 = tmpStr.indexOf('|');
									String jStr = tmpStr.substring(0, ol1);
									if (ol2 == Integer
											.parseInt(tbb.getString())) {
										if (tmpStr.substring(ol1 + 1) == "") {
											tqd = (tmpStr);
										} else {
											tqd = (jStr);
											ol3 = ol3 + tmpStr.length();
										}
									}
									tmpStr = tmpStr.substring(ol1 + 1);
									ol3 = ol3 + jStr.length();
									k1 = tmpStr.lastIndexOf('|');
									ol2 = ol2 + 1;
								}

							} catch (Exception e) {

							}

							if (tqd == ""
									|| Integer.parseInt(tbb.getString()) == 0) {
								k = fuhao[AddFuhao.getSelectedIndex()].length();
								tmpStr = fuhao[AddFuhao.getSelectedIndex()];
								try {

									int k1 = tmpStr.lastIndexOf('|');
									int k2 = k1;
									int ol1 = 0;
									int ol2 = 1;
									int ol3 = 0;
									String summ = "";
									while (ol3 + ol2 - 2 < k2) {
										ol1 = tmpStr.indexOf('|');
										String jStr = tmpStr.substring(0, ol1);

										if (tmpStr.substring(ol1 + 1) == "") {
											summ = summ
													+ (String.valueOf(ol2)
															+ "." + tmpStr + "\n");
											ol3 = ol3 + tmpStr.length();
										} else {
											summ = summ
													+ (String.valueOf(ol2)
															+ "." + jStr + "\n");
											ol3 = ol3 + jStr.length();
										}

										tmpStr = tmpStr.substring(ol1 + 1);
										k1 = tmpStr.lastIndexOf('|');
										ol2 = ol2 + 1;

									}
									f.set(3, new StringItem(null, summ));

								} catch (Exception e1) {
								}

							}

						}
						if (tqd != "") {
							tbx.setString(tbx.getString() + tqd);
							tbk.setString(tbx.getString());
							f.set(3, new StringItem(null, "�����" + tqd + "\n"));
							// f.append("�����" + tqd + "\n");
						}
					}
				}.start();

				return;
			case 30:
				Form fh = new Form("�ı��滻");
				tit();
				tbx = new TextField("ԭ������", tbk.getString(), this.ti , 0);
				fh.append(tbx);
				tbb = new TextField("��������", null, 1024 , 0);
				fh.append(tbb);
				tbn = new TextField("�滻����", null, 1024 , 0);
				fh.append(tbn);
				fh.addCommand(new Command("ȷ��", 4, 32));
				fh.addCommand(new Command("��ӷ���", 2, 14));
				fh.addCommand(new Command("��׺����", 2, 31));
				fh.addCommand(new Command("����ת��", 2, 35));
				fh.addCommand(new Command("�򵥱༭", 2, 43));
				fh.addCommand(new Command("����", 7, 3));
				fh.setCommandListener(this);
				MIDtxt.dp.setCurrent(fh);
				break;
			case 31:
				Form fz = new Form("��׺����");
				tit();
				/*if (kres == "") {
					kres = tbk.getString();
				}*/
				tbx = new TextField("ԭ������",  tbk.getString(), this.ti , 0);
				fz.append(tbx);
				tbb = new TextField("�������", null, 2048, 0);
				fz.append(tbb);
				fz.addCommand(new Command("ȷ��", 4, 33));
				fz.addCommand(new Command("Ԥ��", 1, 34));
				fz.addCommand(new Command("��д", 1, 40));
				fz.addCommand(new Command("ժд", 1, 41));
				fz.addCommand(new Command("����", 1, 42));
				fz.addCommand(new Command("��ӷ���", 2, 14));
				fz.addCommand(new Command("�ı��滻", 2, 30));
				fz.addCommand(new Command("����ת��", 2, 35));
				fz.addCommand(new Command("�򵥱༭", 2, 43));
				fz.addCommand(new Command("����", 7, 3));
				fz.setCommandListener(this);
				MIDtxt.dp.setCurrent(fz);
				break;
			case 32:
				new Thread() {
					public void run() {
						Form f = (Form) d;
						if (tbx.size() > 0 && tbb.size() > 0) {
							tbx.setString(replace(tbb.getString(), tbn
									.getString(), tbx.getString()));
							tbk.setString(tbx.getString());
							f.append("�ѽ�" + tbb.getString() + "�滻Ϊ"
									+ tbn.getString() + "\n");
							tbb.setString(null);
							tbn.setString(null);
						}
					}
				}.start();
				break;
			case 33:
				if (tbb.size() > 0) {
					//tres = tbb.getString();
					tbx.setString(tbx.getString() + tbb.getString());
					tbk.setString(tbx.getString());
				}
				break;
			case 34:
				//tres = tbb.getString();
				//kres = tbx.getString();
				MIDtxt.lastD1=MIDtxt.dp.getCurrent();
				Form yl = new Form("Ԥ��");
				yl.append(tbx.getString() + tbb.getString());
				yl.addCommand(new Command("����", 1, 6));
				yl.addCommand(new Command("ȡ��", 2, 3));
				yl.setCommandListener(this);
				MIDtxt.dp.setCurrent(yl);
				break;
			case 35:
				Form ff = new Form("����ת��");
				tit();
				tbx = new TextField("ԭ������", tbk.getString(), this.ti, 0);
				ff.append(tbx);
				FJZH = new ChoiceGroup("ѡ������", 4, qhl, null);
				ff.append(FJZH);
				ff.addCommand(new Command("ȷ��", 4, 36));
				ff.addCommand(new Command("��ӷ���", 2, 14));
				ff.addCommand(new Command("�ı��滻", 2, 30));
				ff.addCommand(new Command("��׺����", 2, 31));
				ff.addCommand(new Command("�򵥱༭", 2, 43));
				ff.addCommand(new Command("����", 7, 3));
				ff.setCommandListener(this);
				MIDtxt.dp.setCurrent(ff);
				break;
			case 36:
				if (tbx.size() > 0) {
					String news = new String();
					int ssg = 0;
					ssg = FJZH.getSelectedIndex();
					QH xxx = new QH();
					switch (ssg) {
					case 0:
						news = xxx.Conversion(tbx.getString(), 1, 0);
						break;
					case 1:
						news = xxx.Conversion(tbx.getString(), 0, 1);
						break;
					}
					xxx = null;
					tbx.delete(0, tbx.size());
					tbx.insert(news, tbx.getString().length());
					tbk.setString(tbx.getString());
				}
				break;
			case 37:
				if (tbx.size() > 0 && tbb.size() > 0) {
					run(tbb.getString(), tbx.getString());
				}
				break;
			case 38:
				tbx.setString("CXGPRSTC");
				tbb.setString("10086");
				break;
			case 39:
				tbx.setString("CXGPRSLL");
				tbb.setString("10010");
				break;
			case 40:
				tbb.setString(null);
				break;
			case 41:
				tbb.setString(tbx.getString());
				break;
			case 42:
				tbx.setString(tbb.getString());
				break;
			case 43:
				/*
				 * System.out.println("Starting..."); tit(); tshow = new
				 * TextBoz("Reader", tbk.getString(), this.ti, 0);
				 * tshow.addCommand(new Command("ȷ��", 1, 44));
				 * tshow.addCommand(new Command("ȡ��", 2, 3));
				 * tshow.setCommandListener(this); MIDtxt.dp.setCurrent(tshow);
				 */
				System.out.println("Starting...");
				tit();
				tx = new TextBoz("�򵥱༭", tbk.getString(), this.ti, 0);
				tx.addCommand(new Command("ȷ��", 4, 44));
				tx.addCommand(new Command("��ӷ���", 2, 14));
				tx.addCommand(new Command("�ı��滻", 2, 30));
				tx.addCommand(new Command("��׺����", 2, 31));
				tx.addCommand(new Command("����ת��", 2, 35));
				tx.addCommand(new Command("ȡ��", 7, 3));
				tx.setCommandListener(this);
				MIDtxt.dp.setCurrent(tx);
				break;
			case 44:
				tbk.setString(tx.getString());
				MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				if (cd.getCommandType() == 7) { // ��Ҫ�洢����
					rmsDo(true);// д������
				}
				break;
			case 45:
				/*
				 * ini=0; ggy(tfk.getString(),3);
				 */
				break;
			case 47:// ��ȱ

				initApp();
				// refreshData();
				// (new Thread(this)).start();
				break;
			case 48:
				/*
				 * int isx = tshow.getCaretPosition();// ��ǰ���λ�� if(isx==0){
				 * }else{ String snh = tshow.getString(); String snn =
				 * snh.substring(0, isx-1); tshow.setString("");
				 * tshow.insert(snn,0); tshow.insert(snh.substring(isx),
				 * tshow.getString().length()); }
				 */
				if (tshow.size() > 0) {
					oldstring = tshow.getString(); // ������ַ���
					int isx = tshow.getCaretPosition();// ��һ���λ��
					String stemp = oldstring.substring(0, isx) + "��"
							+ oldstring.substring(isx);
					tit();
					tshow = new TextBow("����ɾ��", stemp, ti + 1, 0);
					tshow.setCommandListener(this);
					tshow.addCommand(new Command("�������", 7, 1));
					tshow.addCommand(new Command("ɾ���ı�", 4, 54));
					tshow.addCommand(new Command("ȡ��", 2, 55));
					MIDtxt.dp.setCurrent(tshow);
				}
				break;
			case 49:
				ini = 0;
				ggy(tbk.getString(), 3);
				break;
			case 50:
				try {
					tbk.setString(tshow.getString());
				} catch (Exception e) {

				}
				try {
					tfk.setString(tshow.getString());
				} catch (Exception e) {

				}

				MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				if (cd.getCommandType() == 7) { // ��Ҫ�洢����
					rmsDo(true);// д������
				}
				break;
			case 51:
				/*
				 * if (tshow.getString()==null){}else{ sCut=tshow.getString(); }
				 */

				int isd = tshow.getCaretPosition();// �ڶ����λ��
				int isc = tshow.getString().indexOf('��');
				int ix3 = Math.min(isc, isd);
				int ix4 = Math.max(isc, isd);
				if (isc < isd) {
					ix4 = ix4 - 1;
				}
				if (-1 <= isc - isd && isc - isd <= 0) {// ���λ����ͬ
					if (isc >= oldstring.length()) { // /// ���λ����ĩβ����ȫ��
						tshow.setString(oldstring);
						if (oldstring.length() > 0) {
							sCut =oldstring + "��" + sCut ;
						}
					} else if (iLen != 0 && iLen < oldstring.length() - isc) { // ///
						// ����һ�������ַ���
						tshow.setString(oldstring);

						// sCut = oldstring.substring(isc, isc + iLen);
						if (oldstring.substring(isc, isc + iLen).length() > 0) {
							sCut = oldstring.substring(isc, isc + iLen)
									+ "��"+sCut  ;
						}
					} else if (iLen == 0 || iLen >= oldstring.length() - isc) { // ///����֮�������ַ���
						tshow.setString(oldstring);
						// sCut = oldstring.substring(isc);
						if (oldstring.substring(isc).length() > 0) {
							sCut = oldstring.substring(isc) + "��" +sCut ;
						}

					}
				} else { // ���渴��
					tshow.setString(oldstring);
					// sCut = oldstring.substring(ix3, ix4);
					if (oldstring.substring(ix3, ix4).length() > 0) {
						sCut =oldstring.substring(ix3, ix4) + "��"  + sCut;
					}

				}
				rmsDo(true);

				// System.out.println("���ƵĶ���:"+sCut+isc+isd);
				// MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ

				if (ini == 0) {
					ini = 0;
					ggy(tshow.getString(), 3);
				} else {
					ini = 1;
					ggy(tshow.getString(), 69);
				}

				break;
			case 52:
				if (sCut == "") {
				} else {
					MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
					Plist xlx = new Plist(2,null, null);
					// int ii = tshow.getCaretPosition();
					// tshow.insert(sCut, ii);

				}
				break;
			case 53:
				tshow.setString("");
				break;
			case 54:
				int isy = tshow.getCaretPosition();// �ڶ����λ��
				int isx = tshow.getString().indexOf('��');
				int ix1 = Math.min(isx, isy);
				int ix2 = Math.max(isx, isy);
				if (isx < isy) {
					ix2 = ix2 - 1;
				}
				if (-1 <= isx - isy && isx - isy <= 0) {// ���λ����ͬ
					if (isx >= oldstring.length()) { // /// ���λ����ĩβ����ȫ��
						tshow.setString(oldstring);
					} else if (iLen != 0 && iLen < oldstring.length() - isx) { // ///
						// ����һ�������ַ���
						tshow.setString(oldstring);
						tshow.delete(isx, isx + iLen);
						// sCut = oldstring.substring(isx, isx + iLen);
					} else if (iLen == 0 || iLen >= oldstring.length() - isx) { // ///����֮�������ַ���
						// sCut = oldstring.substring(isx);
						if (isx > 0) {
							tshow.setString(oldstring);
							tshow.delete(isx);
						} else {
							if (tshow.getString().indexOf('��') == 0) {
								tshow.setString(oldstring);
							}
						}

					}
				} else { // ���渴��
					// sCut = oldstring.substring(ix1, ix2);
					tshow.setString(oldstring);
					tshow.delete(ix1, ix2);

				}
				rmsDo(true);

				// System.out.println("���ƵĶ���:"+sCut+isx+isy);
				// MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				if (ini == 0) {
					ini = 0;
					ggy(tshow.getString(), 3);
				} else {
					ini = 1;
					ggy(tshow.getString(), 69);
				}
				break;
			case 55:
				tshow.setString(oldstring);
				// ini=0;
				if (ini == 0) {
					ini = 0;
					ggy(oldstring, 3);
				} else {
					ini = 1;
					ggy(tshow.getString(), 69);
				}
				break;
			case 56:

				if (tshow.size() > 0) {
					oldstring = tshow.getString(); // ������ַ���
					int isk = tshow.getCaretPosition();// ��һ���λ��
					String stemp = oldstring.substring(0, isk) + "��"
							+ oldstring.substring(isk);
					tit();

					tshow = new TextBow("���ɸ���", stemp, ti + 1, 0);
					tshow.setCommandListener(this);
					tshow.addCommand(new Command("�������", 1, 1));
					tshow.addCommand(new Command("�����ı�", 1, 51));
					tshow.addCommand(new Command("ȡ��", 2, 55));
					MIDtxt.dp.setCurrent(tshow);
				}
				break;
			case 57:
				int isy2 = tshow.getCaretPosition();// �ڶ����λ��
				int isx2 = tshow.getString().indexOf('��');
				int ix12 = Math.min(isx2, isy2);
				int ix22 = Math.max(isx2, isy2);
				if (isx2 < isy2) {
					ix22 = ix22 - 1;
				}
				if (-1 <= isx2 - isy2 && isx2 - isy2 <= 0) {// ���λ����ͬ
					if (isx2 >= oldstring.length()) { // /// ���λ����ĩβ����ȫ��
						tshow.setString(oldstring);
					} else if (iLen != 0 && iLen < oldstring.length() - isx2) { // ///
						// ����һ�������ַ���
						tshow.setString(oldstring);
						tshow.delete(isx2, isx2 + iLen);
						// sCut = oldstring.substring(isx2, isx2 + iLen);
					} else if (iLen == 0 || iLen >= oldstring.length() - isx2) { // ///����֮�������ַ���
						// sCut = oldstring.substring(isx2);
						if (isx2 > 0) {
							tshow.setString(oldstring);
							tshow.delete(isx2);
						} else {
							if (tshow.getString().indexOf('��') == 0) {
								tshow.setString(oldstring);
							}
						}

					}
				} else { // ���渴��
					// sCut = oldstring.substring(ix12, ix22);
					tshow.setString(oldstring);
					tshow.delete(ix12, ix22);

				}
				rmsDo(true);
				try {
					tbk.setString(tshow.getString());
				} catch (Exception e) {
				}
				try {
					tfk.setString(tshow.getString());
				} catch (Exception e1) {
				}
				// System.out.println("���ƵĶ���:"+sCut+isx2+isy2);
				MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				break;
			case 58:
			MIDtxt.lastD1=MIDtxt.dp.getCurrent();
				//xres = AddFuhao.getSelectedIndex();

				//yres = tbb.getString();
				//zres = tbx.getString();
				int k = fuhao[AddFuhao.getSelectedIndex()].length();
				String tmpStr = fuhao[AddFuhao.getSelectedIndex()];

				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				LFH rp = new LFH(null, tmpStr, k);
				rp.addCommand(new Command("����", 2, 6));
				rp.setCommandListener(this);
				MIDtxt.dp.setCurrent(rp);
				// tqd = fuhao[AddFuhao.getSelectedIndex()];

				// xres=AddFuhao.getSelectedIndex();
				// yres=tbb.getString();

				break;
			case 59:
				System.gc();
				Runtime.getRuntime().gc();
				refreshData();
				break;
			case 60:
				getHEAP();
				System.gc();
				Runtime.getRuntime().gc();
				refreshData();
				break;
			case 61:
				a = (tbx.getString());
				if (Integer.parseInt(a) != 0)
					try {
						rs.setRecord(1, a.getBytes(), 0, a.length());
					} catch (Exception exception) {
						System.out.println(exception);
					}
				try {
					rs.closeRecordStore();
				} catch (Exception exception1) {
					System.out.println(exception1);
				}

				MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				if (cd.getCommandType() == 7) { // ��Ҫ�洢����
					rmsDo(true);// д������
				}
				break;
			case 62:

				bx = 1;
				try {
					oold = tbk.getString();

				} catch (Exception e1) {
					oold = tfk.getString();
				}
				try {
					ggf(zx, bx, oold);
					if (zx == 0) {
						zx = 1024;
					}
					ggx(tbb.getString());
				} catch (Exception e) {

					Alert k1 = new Alert("��ҳ�༭", "��������ÿҳ����\n�ʲ��÷�ҳ�༭��", null,
							AlertType.ERROR);
					MIDtxt.dp.setCurrent(k1);
				}

				// System.out.println(stxt);
				// ggk();
				break;
			case 63:
				if (tbb.size() < zx * bx && nu == 1) {

				} else {
					ggx(tbb.getString());
					if ((bx + 1) <= nu && nu != 0) {

						bx = bx + 1;
						ggf(zx, bx, oold);
					}
					// tbb.setString(stxt);
				}
				break;
			case 64:
				if (tbb.size() < zx * bx && nu == 1) {

				} else {
					ggx(tbb.getString());
					if ((bx - 1) > 0 && nu != 0) {

						bx = bx - 1;

						ggf(zx, bx, oold);
						// tbb.setString(stxt);
					}
				}
				break;
			case 65:
				if (tbb.size() < zx * bx && nu == 1) {
					oold = tbb.getString();

				} else {
					ggx(tbb.getString());
				}
				Form px = new Form("��ҳ��" + String.valueOf(nu));
				tbx = new TextField("ת��ҳ", String.valueOf(bx), String.valueOf(
						nu).length(), 0);
				px.append(tbx);
				tbn = new TextField("ÿҳ�ַ���", String.valueOf(zx), 5, 0);
				px.append(tbn);
				px.setCommandListener(this);
				px.addCommand(new Command("ת��", 1, 66));
				px.addCommand(new Command("ȡ��", 2, 69));

				MIDtxt.dp.setCurrent(px);
				// ggf( zx, bx);
				// tbb.setString(stxt);*/
				break;
			case 66:
				try {
					zx = Integer.parseInt(tbn.getString());
					if (zx == 0) {
						zx = 1024;
					}
				} catch (Exception e1) {
					zx = 1024;
				}
				rmsDo(true);
				try {
					if (bx <= nu && bx > 0) {
						bx = Integer.parseInt(tbx.getString());
					} else {
						if (bx > nu) {
							bx = nu;
						} else {
							bx = 1;
						}
					}
					// ggk();
					// ggx(tbb.getString());
					ggf(zx, bx, oold);
				} catch (Exception e) {
					ggf(zx, 1, oold);

				}
				break;
			case 67:
				ini = 1;
				ggy(tbb.getString(), 69);

				// tbb.setString(stxt);
				/*
				 * if( bx<nu&&bx>0){ try{ stxt=
				 * old.substring(px1,px2);}catch(Exception e){
				 * stxt=old.substring(px1,old.length());} } else{ if (bx<=0){
				 * stxt=old.substring(px1,old.length()); }else{
				 * stxt=old.substring(0,old.length()); } }
				 */
				break;
			case 68:
				if (tbb.size() < zx * bx && nu == 1) {
					oold = tbb.getString();

				} else {
					ggx(tbb.getString());
				}
				try {
					tbk.setString(oold);
				} catch (Exception e) {
				}
				try {
					tfk.setString(oold);
				} catch (Exception e1) {
				}
				MIDtxt.dp.setCurrent(lastD);// �ָ���ʾ
				break;
			case 69:

				ggf(zx, bx, oold);
				// tbb.setString(stxt);
				break;
			case 70:
				try {
					tbb.setString(tshow.getString());
					if (tbb.size() < zx * bx && nu == 1) {
						oold = tbb.getString();

					} else {
						ggx(tbb.getString());
					}
				} catch (Exception e) {
					oold = tbb.getString();
				}
				ggf(zx, bx, oold);
				break;
			case 71:
				MIDtxt.mk.rtn();
				break;
			case 72:
				MIDtxt.mk.g1();
				break;
			case 73:
				MIDtxt.mk.g2();
				break;
			case 74:
				MIDtxt.mk.g3();
				break;
			case 88:

				lastD1 = MIDtxt.dp.getCurrent();
				fsSet(2, "��չ����(Fuck Q��)");
				break;
			case 77:
				MIDtxt.dp.setCurrent(lastD1);
				break;
			case 76:
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				kavax.svgbrowser.SVGBrowser sv = new kavax.svgbrowser.SVGBrowser();
				break;
			case 78:
				tst();

				break;
			case 79:
				fsSet(3, "������Ϸ(Fuck Q��)");
				break;
			case 80:
				fsSet(4, "3D����(Fuck Q��)");
				break;
			case 81:
				fsSet(2, "��չ����(Fuck Q��)");
				break;
			case 82:
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				kavax.life3d.Life3D d1 = new kavax.life3d.Life3D();
				break;
			case 83:
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				kavax.pogoroo.PogoRooMIDlet d2 = new kavax.pogoroo.PogoRooMIDlet();
				break;
			case 84:
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				kavax.retainedmode.RetainedModeMidlet d3 = new kavax.retainedmode.RetainedModeMidlet();
				break;
			case 85:
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				MIDZH zxe = new MIDZH();
				break;
			case 86:
			try{
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				chen.c xe1 = new chen.c();
				}catch(Exception e){
Alert cs = new Alert("��˵�ٻ�", "��Ҫ������,��ֻ�Ǹ���˵��\n"
							+ e.getMessage(), null, AlertType.ERROR);
					MIDtxt.dp.setCurrent(cs);				
				}
				break;
			case 75:
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				Plist xlx = new Plist(1, null,null);
				break;
			case 87:
				textCanvas tc = new textCanvas(MIDtxt.dp, lastD, "Bok", tbk);
				MIDtxt.dp.setCurrent(tc);
				break;
			case 89:
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				kavax.tools.Camera cas = new kavax.tools.Camera();
				break;
			case 90:
				//MIDtxt.dp.setCurrent(new Splash(MIDtxt.mk));
				MIDtxt.dp.setCurrent(MIDtxt.lastDy);
				break;
			/* GhostGzt */
			}
		} else { // ���ı���
			try {
				tbk = ((TextBok) d);
				lastD = MIDtxt.dp.getCurrent();
				if (!sameCmd(cClear, cd) && cd.getLabel().indexOf("���") != -1) { // //�����������գ����ر���Ĳ˵�
					tbk.removeCommand(cClear);
				}
			} catch (Exception e) {
			}
			if (!cd.equals(tbk.cOK) && !sameCmd(cCopy, cd)
					&& !sameCmd(cPaste, cd) && !sameCmd(cDel, cd)
					&& !sameCmd(cClear, cd) && !sameCmd(cFn, cd)
					&& !sameCmd(cImt, cd) && !sameCmd(cExt, cd)
					&& !sameCmd(tbk.cCnf, cd) && !sameCmd(cGoo, cd)
					&& !sameCmd(cZH, cd) && !sameCmd(cx1, cd)
					&& !sameCmd(cx2, cd)) { // ///ԭ���˵�
				ccl.commandAction(cd, d);
				return;
			}
			if (sameCmd(tbk.cCnf, cd)) {
				System.out.println("ȷ��");
				ccl.commandAction(tbk.cOK, d);
				return;
			}
			int p = cd.getPriority();
			switch (p) {
			case 2: // ///����
				oldstring = tbk.getString(); // ������ַ���
				int is1 = tbk.getCaretPosition();// ��һ���λ��
				String stemp = oldstring.substring(0, is1) + "��"
						+ oldstring.substring(is1);
				tit();
				tshow = new TextBow("���ɸ���", stemp, ti + 1, 0);
				tshow.setCommandListener(this);
				tshow.addCommand(new Command("�������", 7, 1));
				tshow.addCommand(new Command("�����ı�", 4, 2));
				tshow.addCommand(new Command("ȡ��", 1, 3));
				MIDtxt.dp.setCurrent(tshow);
				break;
			case 3: // ///ճ��
				/*
				 * int ii = tbk.getCaretPosition(); tbk.insert(sCut, ii);
				 */
				MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				Plist xlx = new Plist(1, null,null);
				/*
				 * String s = tbk.getString().substring(0, ii) + sCut+
				 * tbk.getString().substring(ii); System.out.println(s+ii);
				 * tbk.setString(s);
				 */
				break;
			case 4:
				oldstring = tbk.getString(); // ������ַ���
				int isn = tbk.getCaretPosition();// ��һ���λ��
				String stemp1 = oldstring.substring(0, isn) + "��"
						+ oldstring.substring(isn);
				tit();
				tshow = new TextBow("����ɾ��", stemp1, this.ti + 1, 0);
				tshow.setCommandListener(this);
				tshow.addCommand(new Command("�������", 7, 1));
				tshow.addCommand(new Command("ɾ���ı�", 4, 57));
				tshow.addCommand(new Command("ȡ��", 1, 3));
				MIDtxt.dp.setCurrent(tshow);
				break;
			case 5:// ///���
				tbk.setString("");
				break;
			case 6:// ////����
				// GhostGzt//
			//	fsSet(1, "��������(Fuck Q��)");
			//lastD=MIDtxt.dp.getCurrent();
			MIDtxt.lastDx= MIDtxt.dp.getCurrent();
			MIDtxt.dp.setCurrent(new Splash(MIDtxt.mk));
				break;
			case 7: // �����ı�
				Form f = new Form("��������");
				tit();
				oos = 0;
				f.append(new TextFiles("����·��", sFile,1024, 0));
				f.append(new ChoiceGroup("����", ChoiceGroup.POPUP, new String[] {
						"UTF-8", "ANSI" }, null));
				f.append(new ChoiceGroup("��ʽ", ChoiceGroup.POPUP, new String[] {
						"���뵱ǰλ��", "�滻ԭ�ַ���" }, null));
				f.addCommand(new Command("ȷ��", 4, 7));
				f.addCommand(new Command("ȡ��", 7, 3));
				f.setCommandListener(this);
				MIDtxt.dp.setCurrent(f);
				break;
			case 8:// �����ı�
				f = new Form("��������");
				tit();
				oos = 1;
				tff = new TextFiles("����·��", sFile,1024, 0);
				f.append(tff);
				// f.append(new TextField("����·��", sFile, this.ti, 0));
				f.append(new ChoiceGroup("����", ChoiceGroup.POPUP, new String[] {
						"UTF-8", "ANSI" }, null));
				f.append(new ChoiceGroup("��ʽ", ChoiceGroup.POPUP, new String[] {
						"�����ļ�ĩβ", "��д�ı��ļ�" }, null));
				f.addCommand(new Command("ȷ��", 4, 8));
				f.addCommand(new Command("ȡ��", 7, 3));
				f.setCommandListener(this);
				MIDtxt.dp.setCurrent(f);
				break;
			case 9:
				gga(null);
				break;
			case 11:
				tfk.setString(tbk.getString());
				MIDtxt.dp.setCurrent(MIDtxt.back);
				break;
			case 12:
				MIDtxt.dp.setCurrent(MIDtxt.back);
				break;
			}
		}
	}

	public void commandAction(Command cd, Item item) {
		// System.out.println("��������ı���f��ť");
		Fork fi = (Fork) MIDtxt.dp.getCurrent(); // ���������Fork
		try {
			tfk = ((TextFielk) item);
			lastD = MIDtxt.dp.getCurrent();
			// //////////////////////////////////////////////
			if (sameCmd(tfk.cOK, new Command("_OK", 1, 1))) {
				// System.out.println("����fork:"+fi.getTitle()+"��һ�˵�"+fi.cFst.getLabel());
				tfk.cOK = fi.cFst;
				tfk
						.addCommand(tfk.cCnf = new Command(tfk.cOK.getLabel(),
								1, 1));
				fi.removeCommand(fi.cFst);
			}
			// //////////////////////////////////////////////
			if (!sameCmd(cClear, cd) && cd.getLabel().indexOf("���") != -1) { // //�����������գ����ر���Ĳ˵�
				tfk.removeCommand(cClear);
			}
		} catch (Exception e) {
		}
		System.out.println("���" + cd.getLabel() + cd.getCommandType()
				+ cd.getPriority());
		if (!sameCmd(cCopy, cd) && !sameCmd(cPaste, cd) && !sameCmd(cDel, cd)
				&& !sameCmd(cClear, cd) && !sameCmd(cFn, cd)
				&& !sameCmd(cImt, cd) && !sameCmd(cExt, cd)
				&& !sameCmd(tfk.cCnf, cd) && !sameCmd(cGoo, cd)
				&& !sameCmd(cZH, cd) && !sameCmd(cx1, cd) && !sameCmd(cx2, cd)) { // ///ԭ���˵�
			iicl.commandAction(cd, item);
			return;
		}
		// System.out.println("ȷ��");
		if (sameCmd(tfk.cCnf, cd)) {
			System.out.println("�����ת�ƵĲ˵�Ŷ����" + fi);
			try {
				// iicl.commandAction(tfk.cOK, item); // �ı���˵�
				fi.cl.commandAction(tfk.cOK, fi); // ����˵�
			} catch (Exception e) {
				// fi.cl.commandAction(tfk.cOK, fi); //����˵�
				// iicl.commandAction(tfk.cOK, item); // �ı���˵�
				System.out.println(fi.cl);
			}
			return;
		}
		// System.out.println("���ı���f��ť");
		int p = cd.getPriority();
		switch (p) {
		/*
		 * case 2: // ///���� oldstring = tfk.getString(); // ������ַ��� int is1 =
		 * tfk.getCaretPosition();// ��һ���λ�� String stemp =
		 * oldstring.substring(0, is1) + "��" + oldstring.substring(is1); tit();
		 * tshow = new TextBow("���ɸ���", stemp, this.ti + 1, 0);
		 * tshow.setCommandListener(this); tshow.addCommand(new Command("�������",
		 * 7, 1)); tshow.addCommand(new Command("�����ı�", 4, 2));
		 * tshow.addCommand(new Command("ȡ��", 1, 3));
		 * MIDtxt.dp.setCurrent(tshow); break; case 3: // ///ճ�� int ii =
		 * tfk.getCaretPosition(); tfk.insert(sCut, ii); //String s =
		 * tfk.getString().substring(0, ii) + sCut +
		 * tfk.getString().substring(ii); // System.out.println(s+ii);
		 * //tfk.setString(s); break; case 4: oldstring = tfk.getString(); //
		 * ������ַ��� int ism = tfk.getCaretPosition();// ��һ���λ�� String stemp3 =
		 * oldstring.substring(0, ism) + "��" + oldstring.substring(ism); tit();
		 * tshow = new TextBow("����ɾ��", stemp3, this.ti + 1, 0);
		 * tshow.setCommandListener(this); tshow.addCommand(new Command("�������",
		 * 1, 1)); tshow.addCommand(new Command("ɾ���ı�", 2, 57));
		 * tshow.addCommand(new Command("ȡ��", 1, 3));
		 * MIDtxt.dp.setCurrent(tshow); break; case 5:// ///���
		 * tfk.setString(""); break; case 6:// ////���� // GhostGzt// Form fs =
		 * new Form("��������(Fuck Q��)"); try { Image img = Image
		 * .createImage("/kavax/microedition/lcdui/gentle.png"); fs.append(img); }
		 * catch (Throwable throwable) { } fs.append("����:
		 * GhostGzt(Gentle)\nQQ:1275731466\n");
		 * fs.append("������ף:���˿����ر�����\n��Ȼ������õ�,����Ҳ���ر�Ŷ��\n"); fs .append("E-mail:
		 * GhostGzt@163.com\nP.S:������ı��ȫ�ü��±���д,�в���ָ���������������\n��л���ǵ�֧��,ʹ���ҵ����������壡����\n");
		 * fs.append("Super Text 5.0\n��лrsRk����Դ��֧�֣�����"); // GhostGzt// // Form
		 * fs=new Form("ʵ�ù���"); // fs.append(sab); fs.addCommand(new
		 * Command("��������", 1, 11)); fs.addCommand(new Command("RMS����", 1, 12));
		 * fs.addCommand(new Command("���ȿ���", 1, 13)); fs.addCommand(new
		 * Command("ϵͳ��Ϣ", 1, 15)); fs.addCommand(new Command("�Ƿ��Ķ�", 1, 45));
		 * fs.addCommand(new Command("�ڴ�����", 1, 47)); fs.addCommand(new
		 * Command("��ҳ�༭", 1, 62)); fs.addCommand(new Command("PIM����", 1, 71)); //
		 * fs.addCommand(new Command("��ֵ����",1,16)); fs.addCommand(new
		 * Command("����", 2, 3)); fs.setCommandListener(this);
		 * MIDtxt.dp.setCurrent(fs); break;
		 * 
		 * case 7: // �����ı� Form f = new Form("��������"); tit(); oos=0; tff=new
		 * TextFiles("����·��", sFile, this.ti, 0); f.append(tff); //f.append(new
		 * TextField("����·��", sFile, this.ti, 0)); f.append(new ChoiceGroup("����",
		 * ChoiceGroup.POPUP, new String[] { "UTF-8", "ANSI" }, null));
		 * f.append(new ChoiceGroup("��ʽ", ChoiceGroup.POPUP, new String[] {
		 * "���뵱ǰλ��", "�滻ԭ�ַ���" }, null)); f.addCommand(new Command("ȷ��", 4, 7));
		 * f.addCommand(new Command("ȡ��", 7, 3)); f.setCommandListener(this);
		 * MIDtxt.dp.setCurrent(f); break; case 8:// �����ı� f = new Form("��������");
		 * tit(); oos=1; tff=new TextFiles("����·��", sFile, this.ti, 0);
		 * f.append(tff); //f.append(new TextField("����·��", sFile, this.ti, 0));
		 * f.append(new ChoiceGroup("����", ChoiceGroup.POPUP, new String[] {
		 * "UTF-8", "ANSI" }, null)); f.append(new ChoiceGroup("��ʽ",
		 * ChoiceGroup.POPUP, new String[] { "�����ļ�ĩβ", "��д�ı��ļ�" }, null));
		 * f.addCommand(new Command("ȷ��", 4, 8)); f.addCommand(new Command("ȡ��",
		 * 7, 3)); f.setCommandListener(this); MIDtxt.dp.setCurrent(f); break;
		 * case 9: if (tfk.getString()!=""){ f = new Form("Google"); chooseold =
		 * new ChoiceGroup("ԭ����", 4, oldstr, null); choosenew = new
		 * ChoiceGroup("��������", 4, oldstr, null); choosenet = new
		 * ChoiceGroup("��������", 4, network, null); Database.load();
		 * fft.append("ԭ��:\n" + tfk.getString() + "\n"); bres = tfk.getString();
		 * fft.append(chooseold); chooseold.setSelectedIndex(Database.oldindex,
		 * true); fft.append(choosenew);
		 * choosenew.setSelectedIndex(Database.newindex, true);
		 * fft.append(choosenet); choosenet.setSelectedIndex(Database.net,
		 * true); fft.append("\n"); fft.append(""); fft.addCommand(new
		 * Command("����", 3, 27)); fft.addCommand(new Command("ȡ��", 2, 3));
		 * fft.setCommandListener(this); MIDtxt.dp.setCurrent(f);
		 *  } break;
		 */
		case 10:
			tit();
			tbk = MIDtxt.mk.call(null, tfk.getString(), this.ti, tfk
					.getConstraints());

			break;

		}
	}

	// GhostGzt//

	public void fsSet(int X, String str) {

		Form fs = new Form(str);

		if (X == 1) {
			try {
				Image img = Image.createImage("/kavax/Image/GentlePad1.png");
				fs.append(img);
			} catch (Throwable throwable) {
			}
		} else if (X == 2) {

			try {
				Image img = Image.createImage("/kavax/Image/GentlePad2.png");
				fs.append(img);
			} catch (Throwable throwable) {
			}

		} else if (X == 3) {

			try {
				Image img = Image.createImage("/kavax/Image/GentlePad3.png");
				fs.append(img);
			} catch (Throwable throwable) {
			}

		} else if (X == 4) {

			try {
				Image img = Image.createImage("/kavax/Image/GentlePad4.png");
				fs.append(img);
			} catch (Throwable throwable) {
			}

		} else if (X == 5) {

			try {
				Image img = Image.createImage("/kavax/Image/GentlePad5.png");
				fs.append(img);
			} catch (Throwable throwable) {
			}

		}

		fs.append("����: GhostGzt(Gentle)\nQQ:1275731466\n");
		fs.append("������ף:���˿����ر�����\n��Ȼ������õ�,����Ҳ���ر�Ŷ��\n");
		fs
				.append("E-mail: GhostGzt@163.com\nP.S:������ı��ȫ�ü��±���д,�в���ָ���������������\n��л���ǵ�֧��,ʹ���ҵ����������壡����\n");
		fs
				.append("Super Text 5.0\n��лrsRk����Դ��֧�֣�����\nSuper Text �����(�ں�: kavaText 4.21)\n�Ϸ��̱�: Fuck Q��\nFuck Q������һ�н���Ȩ��������");
		if (X == 1) {
			// GhostGzt//
			// fs.append(sab);

			fs.addCommand(new Command("��������", 1, 11));
			fs.addCommand(new Command("RMS����", 1, 12));
		
			fs.addCommand(new Command("�ı��༭", 1, 14));
			fs.addCommand(new Command("ϵͳ��Ϣ", 1, 15));
			// fs.addCommand(new Command("���Ͷ���", 1, 16));
			fs.addCommand(new Command("�Ƿ��Ķ�", 1, 49));
			fs.addCommand(new Command("�ڴ�����", 1, 47));
			fs.addCommand(new Command("��ҳ�༭", 1, 62));
			fs.addCommand(new Command("����ǿ��", 1, 75));
			fs.addCommand(new Command("��ǿ�༭", 1, 87));
			//fs.addCommand(new Command("��չ����", 1, 88));
			fs.addCommand(new Command("����", 2, 90));
		} else if (X == 2) {

			// fs.setTicker(new Ticker("��ӭʹ��ST5,Made By GhostGzt(Gentle),Fuck
			// Q��"));
			//fs.addCommand(new Command("��������", 2, 77));
			//fs.addCommand(new Command("ʵ�ù���", 1, 9));
			fs.addCommand(new Command("һ������", 1, 5));
			fs.addCommand(new Command("�������", 1, 10));
				fs.addCommand(new Command("���Ͷ���", 1, 16));
			fs.addCommand(new Command("��ֵ����", 1, 17));
			//fs.addCommand(new Command("���ȿ���", 1, 13));
			fs.addCommand(new Command("ʰɫ����", 1, 20));
			//fs.addCommand(new Command("PIM����", 1, 71));
			fs.addCommand(new Command("SVG���", 1, 76));
			fs.addCommand(new Command("�ļ�����", 1, 78));
			//fs.addCommand(new Command("������Ϸ", 1, 79));
			//fs.addCommand(new Command("3D����", 1, 80));
			fs.addCommand(new Command("����ת��", 1, 85));
			fs.addCommand(new Command("��˵�ٻ�", 1, 86));
             fs.addCommand(new Command("����", 2, 90));
			// fs.addCommand(new Command("����", 2, 3));
		} else if (X == 3) {
			// fs.setTicker(new Ticker("��ӭʹ��ST5,Made By GhostGzt(Gentle),Fuck
			// Q��"));
			fs.addCommand(new Command("TilepuzzleGame", 1, 72));
			fs.addCommand(new Command("PushpuzzleGame", 1, 73));
			fs.addCommand(new Command("WormGame", 1, 74));
			//fs.addCommand(new Command("��չ����", 2, 81));
			fs.addCommand(new Command("����", 2, 90));
		} else if (X == 4) {
			// fs.setTicker(new Ticker("��ӭʹ��ST5,Made By GhostGzt(Gentle),Fuck
			// Q��"));
			  fs.addCommand(new Command("ManyBalls", 1, 19));
			fs.addCommand(new Command("Life3D", 1, 82));
			fs.addCommand(new Command("PogoRoo", 1, 83));
			fs.addCommand(new Command("RetainedMode", 1, 84));
			//fs.addCommand(new Command("��չ����", 2, 81));
			fs.addCommand(new Command("����", 2, 90));
		} else if (X == 5) {
		    fs.addCommand(new Command("���ȿ���", 1, 13));
		    fs.addCommand(new Command("PIM����", 1, 71));
			fs.addCommand(new Command("Camera", 1, 89));
			fs.addCommand(new Command("Tuner", 1, 18));
			//fs.addCommand(new Command("��չ����", 2, 81));
			fs.addCommand(new Command("����", 2, 90));
		}
		fs.setCommandListener(this);
		MIDtxt.dp.setCurrent(fs);
	}

	private void initApp() {

		fft = new Form("�ڴ�����");
		firstmem = (int) Runtime.getRuntime().totalMemory();
		try {
			rs = RecordStore.openRecordStore("[AutoHeap]", true);
		} catch (Exception exception) {
			System.out.println(exception);
		}
		a = new String("10");
		try {
			if (rs.getNumRecords() == 0)
				rs.addRecord(a.getBytes(), 0, a.length());
			a = new String(rs.getRecord(1));
		} catch (Exception exception1) {
			System.out.println(exception1);
		}
		tbx = new TextField("���������룩:", a, 4, 2);
		fft.append(tbx);
		fft.append("���ڴ�: " + Long.toString(Runtime.getRuntime().totalMemory())
				+ "\n");
		fft.append("�����ڴ�: " + Long.toString(Runtime.getRuntime().freeMemory()));
		fft.append("����: "
				+ Long.toString(Runtime.getRuntime().totalMemory()
						- (long) firstmem) + "\n");

		fft.addCommand(new Command("����", 4, 59));
		fft.addCommand(new Command("����", 1, 60));
		// fft.addCommand(new Command("��С��", 2, 61));
		fft.addCommand(new Command("����", 7, 61));

		fft.setCommandListener(this);
		MIDtxt.dp.setCurrent(fft);
		new Thread() {
			public void run() {
				do
					try {
						System.gc();
						Runtime.getRuntime().gc();
						refreshData();
						Thread.currentThread().setPriority(10);
						Thread.currentThread();
						Thread.sleep(1000 * Integer.parseInt(tbx.getString()));
					} catch (Exception exception) {
					}
				while (true);

			}
		}.start();

	}

	private void refreshData() {
		fft.set(1, new StringItem(null, "���ڴ�: "
				+ Long.toString(Runtime.getRuntime().totalMemory()) + "\n"));
		fft.set(2, new StringItem(null, "�����ڴ�: "
				+ Long.toString(Runtime.getRuntime().freeMemory()) + "\n"));
		fft.set(3, new StringItem(null, "����: "
				+ Long.toString(Runtime.getRuntime().totalMemory()
						- (long) firstmem) + "\n"));
	}

	private void getHEAP() {
		int i = 0;
		boolean flag;
		do {
			flag = false;
			i += 10240;
			try {
				int ai[] = new int[i];
				flag = true;
			} catch (OutOfMemoryError outofmemoryerror) {
			}
		} while (flag);
	}

	public void tit() {
		try {
			ti = tbk.ki;
		} catch (Exception exception1) {
		} finally {
			try {
				ti = tfk.ki;
			} catch (Exception exception1) {

			}
		}
	}

	public void run(String num, String Str) {
	MIDtxt.lastD1=MIDtxt.dp.getCurrent();
		Form yl = new Form("���ͽ��");
		yl.addCommand(new Command("����", 1, 6));
		Tsms sms1 = new Tsms();
		String db = null;
		int hy;
		hy = sms1.send(num, Str);
		if (hy==1) {
			db = "���ͳɹ���";
		} else {
			db = "����ʧ�ܣ�";
		}
		yl.append("����" + Str + "��" + num + "\n" + db);
		yl.setCommandListener(this);
		MIDtxt.dp.setCurrent(yl);
	}

	public void ggk() {
		/*
		 * tshow = new TextBow("["+String.valueOf(bx)+"]", stxt, ti, 0);
		 * tshow.setCommandListener(this); tshow.addCommand(new Command("����", 1,
		 * 68)); tshow.addCommand(new Command("�޸�", 1, 67));
		 * tshow.addCommand(new Command("��һҳ", 1, 63)); tshow.addCommand(new
		 * Command("��һҳ", 1, 64)); tshow.addCommand(new Command("��λҳ", 1, 65));
		 * tshow.addCommand(new Command("ȡ��", 2, 3));
		 * MIDtxt.dp.setCurrent(tshow);
		 */
		Form py = new Form("��ҳ�༭");
		tit();
		tbb = new TextField("[" + String.valueOf(bx) + "/" + String.valueOf(nu)
				+ "]" + " �ַ���" + String.valueOf(stxt.length()), stxt, this.ti
				+ zx, 0);
		py.append(tbb);
		py.setCommandListener(this);
		py.addCommand(new Command("����", 4, 68));

		py.addCommand(new Command("��һҳ", 1, 63));
		py.addCommand(new Command("��һҳ", 1, 64));
		py.addCommand(new Command("��λҳ", 1, 65));

		py.addCommand(new Command("��д", 1, 62));
		py.addCommand(new Command("ȡ��", 2, 3));
		py.addCommand(new Command("�޸�", 7, 67));
		MIDtxt.dp.setCurrent(py);
	}

	public void ggf(int fzx, int fbx, String fold) {
		zx = fzx;
		bx = fbx;
		String old = fold;
		// old=tbk.getString();
		stxt = "";
		// Integer.parseInt()
		// zx=1000;
		// bx=1;
		nu = old.length() / zx;
		if (nu == 0) {
			nu = nu + 1;
		}
		if (old.length() > (nu * zx)) {
			nu = nu + 1;
		}
		int px1 = (bx - 1) * zx;
		int px2 = (bx) * zx;
		if (bx < nu && bx > 0) {
			try {
				stxt = old.substring(px1, px2);
			} catch (Exception e) {
				stxt = old.substring(px1, old.length());
			}
		} else {
			if (bx <= 0) {
				stxt = old.substring(0, old.length());
				// stxt="123";
			} else {
				// stxt=old.substring(0,old.length());
				stxt = old.substring(px1, old.length());
				// stxt="456";
			}
		}
		// tbb.setTitle("["+String.valueOf(bx)+"]")
		ggk();
	}

	public void ggy(String stt, int n) {
		System.out.println("Starting...");
		tit();
		tshow = new TextBow("Reader", stt, this.ti, 0);
		if (n == 3) {
			tshow.addCommand(new Command("ȷ��", 4, 50));
			ini = 0;
		} else {
			ini = 1;
			tshow.addCommand(new Command("ȷ��", 4, 70));
		}

		tshow.addCommand(new Command("����", 1, 56));
		tshow.addCommand(new Command("ɾ��", 1, 48));
		tshow.addCommand(new Command("ճ��", 1, 52));

		tshow.addCommand(new Command("���", 1, 53));
		tshow.addCommand(new Command("ȡ��", 7, n));
		tshow.setCommandListener(this);
		MIDtxt.dp.setCurrent(tshow);
	}

	/*public void ggw(String str) {
	MIDtxt.lastD1=MIDtxt.dp.getCurrent();
		Form fq = new Form("����");
		fq.append(str);
		fq.addCommand(new Command("ȷ��", 4, 6));
		fq.addCommand(new Command("ȡ��", 7, 3));
		fq.setCommandListener(this);
		MIDtxt.dp.setCurrent(fq);
	}*/

	public void ggb(String str) {

		//bres = str;

		//rres = null;
		try {
			// f.append("\n");
			// f.append("���ڷ�����...");

			int i = chooseold.getSelectedIndex();
			int j = choosenew.getSelectedIndex();
			Database.oldindex = i;
			Database.newindex = j;
			Database.net = choosenet.getSelectedIndex();
			Database.saveInterface();
			String txt = str;
			oldcontent = txt;
			txt = URLEncoder.encode(txt, "utf-8");
			String res = "";
			res = get("http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&q="
					+ txt + "&langpair=" + oldstr_v[i] + "%7C" + oldstr_v[j]);
			System.out.println("res:" + res);
			res = getvalue("translatedText", res);
			res = res.replace('\u201D', '"');
			res = replace("\\u0026#39;", "'", res);
			res = replace("\\u0026gt;", ">", res);
			res = replace("\\u0026lt;", "<", res);
			res = replace("\\u0026amp;", "&", res);
			res = replace("\\u0026quot;", "\"", res);
			// f.append("\n");
			// res="This is a Test!!";
			if (res != ("") && res != null && res.length() > 0) {
				// f.append("\n");
				// f.append("������ɣ�");
				// f.set(5,new StringItem(null, "������ɣ�"));
				//rres = res;
				//if (tob == 0) {
				Alert x3 = new Alert("����", res, null,
									AlertType.INFO);
									x3.setTimeout(-2);
							MIDtxt.dp.setCurrent(x3);
					//ggs(res);
				/*} else {
					ggw(res);
				}*/
				tbb.setString(res);
				//return "1";
			} else {
				// f.append("\n");
				// f.append("����ʧ�ܣ�");
				// f.set(5,new StringItem(null, "����ʧ�ܣ�"));
					Alert x3 = new Alert("Google", "����ʧ�ܣ�", null,
									AlertType.INFO);
							MIDtxt.dp.setCurrent(x3);
			//	return "0";
			}
		} catch (Throwable throwable) {
			Alert x3 = new Alert("Google", "����ʧ�ܣ�", null,
									AlertType.ERROR);
							MIDtxt.dp.setCurrent(x3);
			//return "0";
			// f.append("\n");
			// f.append("����ʧ�ܣ�");
			// f.set(5,new StringItem(null, "����ʧ�ܣ�"));
		}
	}

	public void ggx(String str) {
		try {
			if (oold.length() > (nu * zx)) {
				nu = nu + 1;
			}
			if (nu > 1 || (nu == 1 && tbb.size() > zx)) {

				int px1 = (bx - 1) * zx;
				int px2 = (bx) * zx;
				if (px1 == 0) {
					oold = str + oold.substring(px2, oold.length());
				} else {
					if (px2 > oold.length()) {
						oold = oold.substring(0, px1) + str;

					} else {

						oold = oold.substring(0, px1) + str
								+ oold.substring(px2, oold.length());

					}

				}
				nu = oold.length() / zx;
				if (oold.length() > (nu * zx)) {
					nu = nu + 1;
				}
			} else {

				// oold=str;
				ggf(zx, bx, oold);
			}
		} catch (Exception e) {
			/*
			 * bx=1; nu=1;
			 */

			oold = str;
			// stxt=oold;
			ggf(zx, bx, oold);
			// ggk();
		}

	}

public void ksk (){
MIDtxt.lastD1=MIDtxt.dp.getCurrent();
System.out.println("sSet=" + sSet);
				/*
				 * tshow = new TextBow("��������", sSet, 2048, 0);
				 * tshow.addCommand(new Command("ȷ��", 1, 4));
				 * tshow.addCommand(new Command("ɾ��", 1, 48));
				 * tshow.addCommand(new Command("ȡ��", 7, 3));
				 * tshow.setCommandListener(this); MIDtxt.dp.setCurrent(tshow);
				 */
				Form setx = new Form("��������");
				/* try { */
				rmsDo(false);
				tbx = new TextField("����", String.valueOf(iMax), 1024, 0);
				setx.append(tbx);
				tbb = new TextField("���Ƴ���", String.valueOf(iLen), 1024, 0);
				setx.append(tbb);
				tbn = new TextField("��ҳ����", String.valueOf(zx), 5, 0);
				setx.append(tbn);
				AddFuhao = new ChoiceGroup("����", 1, new String[] { "��", "��" },
						null);
				setx.append(AddFuhao);
				FJZH = new ChoiceGroup("ͳһ�����", 1, new String[] { "��", "��" },
						null);
				setx.append(FJZH);
				if (tyCP == true) {
					FJZH.setSelectedIndex(0, true);
				} else {
					FJZH.setSelectedIndex(1, true);
				}
				if (hideCP == true) {
					AddFuhao.setSelectedIndex(0, true);
				} else {
					AddFuhao.setSelectedIndex(1, true);
				}
				oos = 1;
				tfn = new TextFiles("�ļ�", sFile, 1024, 0);
				setx.append(tfn);
				tfs = new TextFiles("��������ļ�", tsfh, 1024, 0);
				setx.append(tfs);
				/*
				 * } catch (Exception e) { setx.deleteAll(); setx.append(new
				 * TextField("����", String.valueOf(iMax), 1024, 0));
				 * setx.append(new TextField("���Ƴ���", String.valueOf(iLen), 1024,
				 * 0)); if (hideCP==true){ AddFuhao =new ChoiceGroup("����", 1,
				 * new String[] { "��", "��" },null); }else{ AddFuhao =new
				 * ChoiceGroup("����", 1, new String[] {"��", "��"},null); }
				 * AddFuhao =new ChoiceGroup("����", 1, new String[] { "��", "��"
				 * },null); setx.append(AddFuhao); AddFuhao.setSelectedIndex(1,
				 * true); setx.append(new TextField("�ļ�", sFile, 1024, 0)); }
				 */
				setx.addCommand(new Command("ȷ��", 4, 4));
				setx.addCommand(new Command("ȡ��", 7, 6));
				setx.setCommandListener(this);

				MIDtxt.dp.setCurrent(setx);
				}

	public void tst() {
MIDtxt.lastD = MIDtxt.dp1.getCurrent();
				fileSelector.fs = null;
				if (fileSelector.fs == null) {
					oos = 3;
					fileSelector fxs = new fileSelector("�ļ��б�", 3, MIDtxt.dp1,
							MIDtxt.lastD, this);

					MIDtxt.dp1.setCurrent(fxs);

				} else {

					MIDtxt.dp1.setCurrent(fileSelector.fs);
				}
				}


	public void gga(String str) {
		fft = new Form("Google");
		chooseold = new ChoiceGroup("ԭ����", 4, oldstr, null);
		choosenew = new ChoiceGroup("��������", 4, oldstr, null);
		choosenet = new ChoiceGroup("��������", 4, network, null);
		tit();
		tbb = new TextField("����", str, this.ti + 1024, 0);
		tbx = new TextField("ԭ��", tbk.getString(), this.ti + 1024, 0);
		fft.append(tbx);
		fft.append(tbb);
		fft.append(chooseold);
		fft.append(choosenew);
		fft.append(choosenet);
		oldcontent = tbb.getString();
		ooldcontent = tbk.getString();
		int od;
		int nx;
		int nt;
		try {
			Database.load();
			od = Database.oldindex;
			nx = Database.newindex;
			nt = Database.net;
		} catch (Exception e) {
			od = 0;
			nx = 1;
			nt = 0;
		}
		chooseold.setSelectedIndex(od, true);
		choosenew.setSelectedIndex(nx, true);
		choosenet.setSelectedIndex(nt, true);
		//fft.append("");
		fft.addCommand(new Command("ȷ��", 4, 22));
		fft.addCommand(new Command("ȡ��", 7, 3));
		fft.addCommand(new Command("����", 3, 23));
		fft.addCommand(new Command("����", 4, 24));
		fft.addCommand(new Command("�ָ�", 5, 25));
		fft.setCommandListener(this);
		MIDtxt.dp.setCurrent(fft);
		return;
	}

	public void ggs(String str) {
	MIDtxt.lastD1=MIDtxt.dp.getCurrent();
		Form fa = new Form("����");
		fa.append(str);
		fa.addCommand(new Command("����", 1, 6));
		fa.setCommandListener(this);
		MIDtxt.dp.setCurrent(fa);
	}

	private String getvalue(String str, String text) {
		int i = text.indexOf("\"" + str + "\"");
		if (i != -1) {
			text = text.substring(i + str.length() + 4, text.length());
			int j = text.indexOf("\"");
			if (j != -1) {
				text = text.substring(0, j);
			}
		}
		return text;
	}

	public String replace(String s, String s1, String s2) {
		if (s2 == null || s == null || s1 == null) {
			return null;
		}
		StringBuffer stringbuffer = new StringBuffer();
		for (int i = -1; (i = s2.indexOf(s)) != -1; i = -1) {
			stringbuffer.append(s2.substring(0, i) + s1);
			s2 = s2.substring(i + s.length());
		}
		stringbuffer.append(s2);
		return stringbuffer.toString();
	}

	private String[] spliturl(String a) {
		String domain = "";
		String url = "";
		int i = a.indexOf("http://");
		if (i != -1) {
			a = a.substring(7, a.length());
		}
		i = a.indexOf("/");
		if (i != -1) {
			url = a.substring(i + 1, a.length());
			domain = a.substring(0, i);
		}
		return (new String[] { domain, url });
	}

	private String get(String url) {
		HttpConnection hc;
		InputStream in;
		String rs;
		StringBuffer sb;
		System.out.println(url);
		hc = null;
		in = null;
		rs = "";
		sb = new StringBuffer();
		try {
			if (Database.net == 0) {
				String urlarr[] = spliturl(url);
				hc = (HttpConnection) Connector.open("http://10.0.0.172:80/"
						+ urlarr[1], 1, true);
				hc.setRequestProperty("X-Online-Host", urlarr[0]);
				hc.setRequestProperty("Accept", "*/*");
				hc.setRequestProperty("Connection", "Keep-Alive");
				hc.setRequestMethod("GET");
				String s = hc.getHeaderField("Content-Type");
				if (s.indexOf("text/vnd.wap.wml") > -1) {
					hc.close();
					hc = null;
					hc = (HttpConnection) Connector.open(
							"http://10.0.0.172:80/" + urlarr[1], 1, true);
					hc.setRequestProperty("X-Online-Host", urlarr[0]);
					hc.setRequestProperty("Accept", "*/*");
					hc.setRequestProperty("Connection", "Keep-Alive");
					hc.setRequestMethod("GET");
				}
			} else if (Database.net == 1) {
				hc = (HttpConnection) Connector.open(url, 1, true);
				hc.setRequestProperty("Accept", "*/*");
				hc.setRequestMethod("GET");
				hc.setRequestProperty("Connection", "Keep-Alive");
				String s = hc.getHeaderField("Content-Type");
				if (s.indexOf("text/vnd.wap.wml") > -1) {
					hc.close();
					hc = null;
					hc = (HttpConnection) Connector.open(
							"http://10.0.0.172:80/" + url, 1, true);
					hc.setRequestProperty("Accept", "*/*");
					hc.setRequestProperty("Connection", "Keep-Alive");
					hc.setRequestMethod("GET");
				}
			}
			in = hc.openInputStream();
			int ch = 0;
			InputStreamReader isr = new InputStreamReader(in, "utf-8");
			try {
				while ((ch = isr.read()) != -1) {
					sb.append((char) ch);
				}
			} catch (Exception ex) {
				sb.append("\r\n");
				sb.append(ex.toString());
			}
			rs = sb.toString();
			isr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (hc != null) {
			try {
				hc.close();
				hc = null;
			} catch (Exception exception1) {
			}
		}
		if (in != null) {
			try {
				in.close();
				in = null;
			} catch (Exception exception2) {
			}
		}
		if (hc != null) {
			try {
				hc.close();
				hc = null;
			} catch (Exception exception3) {
			}
		}
		if (in != null) {
			try {
				in.close();
				in = null;
			} catch (Exception exception4) {
			}
		}
		return rs;
	}

	// GhostGzt//
	public static void hideMenu(TextBox tbx, boolean hide) { // �����˵�
		if (hide) { // /////// ��Ҫ���ز˵�
			tbx.removeCommand(cClear);
			tbx.removeCommand(cCopy);
			tbx.removeCommand(cDel);
			tbx.removeCommand(cPaste);
		} else {
			tbx.addCommand(cClear);
			tbx.addCommand(cCopy);
			tbx.addCommand(cDel);
			tbx.addCommand(cPaste);
		}
	}

	public static void hideMenu(TextFielk tfk, boolean hide) { // �����˵�
		/*
		 * if (hide) { // /////// ��Ҫ���ز˵� tfk.removeCommand(cClear);
		 * tfk.removeCommand(cCopy); tfk.removeCommand(cDel);
		 * tfk.removeCommand(cPaste); } else { tfk.addCommand(cDel);
		 * tfk.addCommand(cClear); tfk.addCommand(cCopy);
		 * tfk.addCommand(cPaste); }
		 */
	}

	public static void rmsDo(boolean write) { // /rms����
		// MIDtxt.mk.getAppProperty("MIDlet-Name")
		try {
			if (write) { // //д
				RecordStore rms = RecordStore.openRecordStore("kavaXText",
						true, RecordStore.AUTHMODE_ANY, true);
				System.out.println(rms);
				if (!haveRMS) {
					System.out.println("haveRMS" + haveRMS);
					rms.addRecord(sSet.getBytes("utf-8"), 0, sSet
							.getBytes("utf-8").length);
					rms.addRecord((zx + "").getBytes("utf-8"), 0, (zx + "")
							.getBytes("utf-8").length);
					rms.addRecord((iLight + "").getBytes(), 0, (iLight + "")
							.getBytes("utf-8").length);
					if (sty.length() >= 2) {
						sty = "����";
					} else {
						sty = "��";
					}
					rms.addRecord(sty.getBytes("utf-8"), 0, sty
							.getBytes("utf-8").length);
					// if(sty=="����"){sty="��";}
					rms.addRecord(tsfh.getBytes("utf-8"), 0, tsfh
							.getBytes("utf-8").length);
					rms.addRecord(sRms.getBytes("utf-8"), 0, sRms
							.getBytes("utf-8").length);
					System.out.println("����" + sSet);
				} else {
					System.out.println(sSet);
					rms.setRecord(1, sSet.getBytes("utf-8"), 0, sSet
							.getBytes("utf-8").length);
					rms.setRecord(2, (zx + "").getBytes("utf-8"), 0, (zx + "")
							.getBytes("utf-8").length);
					rms.setRecord(3, (iLight + "").getBytes(), 0, (iLight + "")
							.getBytes("utf-8").length);
					if (sty.length() >= 2) {
						sty = "����";
					} else {
						sty = "��";
					}
					rms.setRecord(4, sty.getBytes("utf-8"), 0, sty
							.getBytes("utf-8").length);
					// if(sty=="����"){sty="��";}
					rms.setRecord(5, tsfh.getBytes("utf-8"), 0, tsfh
							.getBytes("utf-8").length);
					rms.setRecord(6, sRms.getBytes("utf-8"), 0, sRms
							.getBytes("utf-8").length);
				}
				haveRMS = true;
				rms.closeRecordStore();
			} else {
				sSet = "";
				RecordStore rms = RecordStore
						.openRecordStore("kavaXText", true);
				sSet = (new String(rms.getRecord(1), 0,
						rms.getRecord(1).length, "utf-8"));
				String xL = (new String(rms.getRecord(2), 0,
						rms.getRecord(2).length, "utf-8"));
				zx = Integer.parseInt(xL);
				String sL = (new String(rms.getRecord(3), 0,
						rms.getRecord(3).length, "utf-8"));
				iLight = Integer.parseInt(sL);
				sty = (new String(rms.getRecord(4), 0, rms.getRecord(4).length,
						"utf-8"));
				tsfh = (new String(rms.getRecord(5), 0,
						rms.getRecord(5).length, "utf-8"));
				sRms = (new String(rms.getRecord(6), 0,
						rms.getRecord(6).length, "utf-8"));

				if (sty.length() <= 1) {
					sty = "��";
					tyCP = true;
					uty = 0;
				} else {
					sty = "����";

					tyCP = false;
					try {
						uty = tbk.oi;
					} catch (Exception exception1) {
						uty = tfk.oi;
					}
				}

				// sSet="";
				// sSet=MIDtxt.mk.getAppProperty("sSet");
				// System.out.println(sty);
				String sset = sSet;
				System.out.println("RMS=" + sSet);
				int p1 = sset.indexOf("���Ƴ���=");
				iMax = Integer.parseInt(sset.substring("����=".length(), p1));
				if (iMax == 0) {
					try {
						ti = tbk.ki;
					} catch (Exception exception1) {
					} finally {
						try {
							ti = tfk.ki;
						} catch (Exception exception1) {
						}
					}

				} else {
					ti = iMax;
				}
				int p2 = sset.indexOf("����=");
				iLen = Integer.parseInt(sset.substring(p1 + "���Ƴ���=".length(),
						p2));
				int p3 = sset.indexOf("�ļ�=");
				hideCP = (sset.substring(p2 + "����=".length(), p3)
						.equalsIgnoreCase("��"));
				sHide = hideCP ? "��" : "��";
				int p4 = sset.lastIndexOf('=');
				sFile = sset.substring(p4 + 1);
				rms.closeRecordStore();
			}
		} catch (Exception e) {
			System.out.println("ò��RMS����ʧ�ܣ�");
			haveRMS = false;
		}
	}

	public static void ReadMF() {

		System.out.println(MIDtxt.mk.getAppProperty("khide"));
		try {
			if (MIDtxt.mk.getAppProperty("khide").length() > 0
					&& MIDtxt.mk.getAppProperty("khide") != null) {
				hideCP = (MIDtxt.mk.getAppProperty("khide")
						.equalsIgnoreCase("��"));
				sHide = hideCP ? "��" : "��";
			}
		} catch (Exception e1) {
		}
		try {
			if (MIDtxt.mk.getAppProperty("kfile").length() > 0
					&& MIDtxt.mk.getAppProperty("kfile") != null) {
				sFile = MIDtxt.mk.getAppProperty("kfile");
			}
		} catch (Exception e2) {
		}
		try {
			if (MIDtxt.mk.getAppProperty("ktsfh").length() > 0
					&& MIDtxt.mk.getAppProperty("ktsfh") != null) {
				tsfh = MIDtxt.mk.getAppProperty("ktsfh");
			}
		} catch (Exception e1) {
		}
		try {
			if (MIDtxt.mk.getAppProperty("kmax").length() > 0
					&& MIDtxt.mk.getAppProperty("kmax") != null) {
				iMax = Integer.parseInt(MIDtxt.mk.getAppProperty("kmax"));
			}
		} catch (Exception e3) {
		}
		try {
			if (MIDtxt.mk.getAppProperty("klen").length() > 0
					&& MIDtxt.mk.getAppProperty("klen") != null) {
				iLen = Integer.parseInt(MIDtxt.mk.getAppProperty("klen"));
			}
		} catch (Exception e4) {
		}
		try {
			if (MIDtxt.mk.getAppProperty("kty").length() > 0
					&& MIDtxt.mk.getAppProperty("kty") != null) {
				sty = MIDtxt.mk.getAppProperty("kty");
			}
		} catch (Exception e5) {
		}
		try {
			if (MIDtxt.mk.getAppProperty("krms").length() > 0
					&& MIDtxt.mk.getAppProperty("krms") != null) {
				sRms = MIDtxt.mk.getAppProperty("krms");
			}
		} catch (Exception e6) {
		}

		try {
			if (MIDtxt.mk.getAppProperty("kfzx").length() > 0
					&& MIDtxt.mk.getAppProperty("kfzx") != null) {
				zx = Integer.parseInt(MIDtxt.mk.getAppProperty("kfzx"));
			}
		} catch (Exception e7) {
		}
		try {
			if (sty.length() <= 1) {
				sty = "��";
				tyCP = true;
				uty = 0;
			} else {
				sty = "����";

				tyCP = false;
				try {
					uty = tbk.oi;
				} catch (Exception exception1) {
					uty = tfk.oi;
				}
			}
		} catch (Exception e7) {
		}

	}

	public static void rmsOutput(String url) throws Exception {
		try {
			FileConnection fileconnection = (FileConnection) Connector.open(
					url, 3);
			if (fileconnection.exists())
				fileconnection.delete();
			fileconnection.create();
			DataOutputStream dataoutputstream = fileconnection
					.openDataOutputStream();
			dataoutputstream.writeInt(0x524d5300);
			String as[] = RecordStore.listRecordStores();
			if (as != null) {
				dataoutputstream.writeInt(as.length);
				for (int i = 0; i < as.length; i++) {
					RecordStore recordstore = RecordStore.openRecordStore(
							as[i], false);
					dataoutputstream.writeUTF(recordstore.getName());
					int j = recordstore.getNextRecordID();
					dataoutputstream.writeInt(j);
					for (int k = 1; k < j; k++) {
						try {
							byte abyte0[] = new byte[recordstore
									.getRecordSize(k)];
							recordstore.getRecord(k, abyte0, 0);
							dataoutputstream.writeInt(k);
							dataoutputstream.writeChar(43);
							dataoutputstream.writeInt(abyte0.length);
							dataoutputstream.write(abyte0);
							abyte0 = null;
							continue;
						} catch (Exception exception1) {
							dataoutputstream.writeInt(k);
						}
						dataoutputstream.writeChar(45);
					}
					recordstore.closeRecordStore();
				}
			} else {
				dataoutputstream.writeInt(0);
			}
			dataoutputstream.close();
			fileconnection.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public static void rmsInput(String url) throws Exception {
		try {
			FileConnection fileconnection = (FileConnection) Connector.open(
					url, 1);
			DataInputStream datainputstream = fileconnection
					.openDataInputStream();
			if (datainputstream.readInt() == 0x524d5300) {
				int i = datainputstream.readInt();
				for (int j = 0; j < i; j++) {
					String s = datainputstream.readUTF();
					try {
						RecordStore.deleteRecordStore(s);
					} catch (Exception exception1) {
					}
					RecordStore recordstore = RecordStore.openRecordStore(s,
							true);
					int k = datainputstream.readInt();
					for (int l = 1; l < k; l++) {
						int i1 = datainputstream.readInt();
						char c = datainputstream.readChar();
						int j1 = recordstore.addRecord(null, 0, 0);
						if (c == '+') {
							int k1 = datainputstream.readInt();
							byte abyte0[] = new byte[k1];
							datainputstream.read(abyte0);
							recordstore.setRecord(j1, abyte0, 0, abyte0.length);
						} else {
							recordstore.deleteRecord(j1);
						}
					}
					recordstore.closeRecordStore();
				}
			}
			datainputstream.close();
			fileconnection.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public void itemStateChanged(Item item) {
		/*try {
			Form form = (Form) MIDtxt.dp.getCurrent();
			Gauge g = (Gauge) item;
			if (g.getMaxValue() > 10) {
				iLight = g.getValue();
				((Gauge) form.get(1)).setValue(iLight / 10);
			} else {
				iLight = g.getValue() * 10;
				((Gauge) form.get(0)).setValue(iLight);
			}
			DeviceControl.setLights(0, iLight);
			return;
		} catch (Exception e) {
			return;
		}*/
	}

	public static boolean sameCmd(Command c1, Command c2) {
		return (c1.getCommandType() == c2.getCommandType()
				&& c1.getPriority() == c2.getPriority() && c1.getLabel()
				.equals(c2.getLabel()));
	}
}