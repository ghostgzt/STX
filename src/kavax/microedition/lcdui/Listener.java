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
	public static String sCut = ""; // 剪贴板内容
	public static int iMax = 0;// /文本框字限
	public static int iLen = 0;// /复制长度
	public static boolean hideCP = true;
	public static String sFile = "file:///E:/ST5/text.txt";
	/* GhostGzt Start */
	private String oldcontent;
	private String ooldcontent;
	private String oldstr[] = { "英语", "中文(简体)", "中文(繁体)", "阿尔巴尼亚文", "阿拉伯文",
			"爱沙尼亚语", "保加利亚文", "波兰语", "朝鲜语", "丹麦语", "德语", "俄语", "法语", "菲律宾文",
			"芬兰语", "盖尔文(爱尔兰)", "荷兰语", "加泰罗尼亚文(西班牙)", "捷克语", "克罗地亚文", "拉脱维亚语",
			"立陶宛语", "罗马尼亚语", "马耳他文", "挪威语", "葡萄牙语", "日语", "瑞典语", "塞尔维亚文",
			"斯拉维尼亚文", "斯洛伐克文", "泰文", "土耳其文", "乌克兰文", "西班牙语", "希伯来语", "希腊语",
			"匈牙利语", "意大利语", "印度文", "印尼文", "越南文" };
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
	private String qhl[] = { "简体", "繁体" };
	public static int ti = iMax;
	private TextBoz tx;
	public static boolean tyCP = true;
	public static String sty = "是";
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
	public static String sHide = "是";
	public static String sSet = "字限=" + iMax + "复制长度=" + iLen + "隐藏=" + sHide
			+ "文件=" + sFile; // 设置内容串
	// public static Command cClear,cCopy,cPaste,cSet,cImt,cExt,cCnf;
	private String oldstring = "";
	public static TextBow tshow;
	// private int is1,is2; //第一第二光标位置
	public static Displayable lastD; // 上一个显示对象
	public static ItemCommandListener iicl;
	public static CommandListener ccl;
	public static int iLight = 50;
	public static TextFielk tfk;
	public static TextBok tbk;

	private static boolean haveRMS = true;
	public static Command cCopy = new Command("_复制", 1, 2);
	public static Command cPaste = new Command("_粘贴", 1, 3);
	public static Command cDel = new Command("_删除", 1, 4);
	public static Command cClear = new Command("_清空", 1, 5);
	public static Command cFn = new Command("_功能", 1, 6);
	public static Command cImt = new Command("_导入文本", 1, 7);
	public static Command cExt = new Command("_导出文本", 1, 8);
	public static Command cGoo = new Command("_Google", 1, 9);
	public static Command cZH = new Command("_召唤", 1, 10);
	public static Command cx1 = new Command("确定", Command.OK, 11);
	public static Command cx2 = new Command("返回", Command.BACK, 12);

	// public static String
	// sab="1.复制/粘贴/清空\n2.导入/导出文本\n3.RMS导入/导出\n4.诺机亮度控制\n5.系统信息查看\n(可能发出异响!)";
	// public static Command cCnf=new Command("_OK",1,1);
	// public static Command cOK;
	// ///////////////////////////
	public Listener() {
	}

	public void commandAction(Command cd, final Displayable d) {

		// System.out.println("点击了新文本框按钮");
		if (d.equals(tshow) || d instanceof Form) {
			TextBox t = null;
			Form f = null;
			if (d instanceof TextBox) {
				System.out.println("是么");
				t = (TextBox) d;
			} else if (d instanceof Form) {
				f = (Form) d;
			}
			int ic = cd.getPriority();
			switch (ic) {
			case 1: //
				int is1 = tshow.getCaretPosition();// 当前光标位置
				int ix = tshow.getString().indexOf('※'); // 原先位置
				if (-1 <= ix - is1 && ix - is1 < 1) {
					return; // 位置相同，不用折腾
				} else {
					String snn = tshow.getString();
					snn = snn.substring(0, ix) + snn.substring(ix + 1);
					if (is1 > ix) {
						is1 = is1 - 1;
					}
					snn = snn.substring(0, is1) + "※" + snn.substring(is1);
					tshow.setString(snn);
				}
				break;
			case 2: // 复制
				int is2 = tshow.getCaretPosition();// 第二光标位置
				is1 = tshow.getString().indexOf('※');
				int i1 = Math.min(is1, is2);
				int i2 = Math.max(is1, is2);
				if (is1 < is2) {
					i2 = i2 - 1;
				}
				if (-1 <= is1 - is2 && is1 - is2 <= 1) {// 光标位置相同
					if (is1 >= oldstring.length()) { // /// 光标位置在末尾复制全部

						// sCut = oldstring;
						if (oldstring.length() > 0) {
							sCut = oldstring + "※"+ sCut ;
						}
					} else if (iLen != 0 && iLen < oldstring.length() - is1) { // ///
						// 复制一定长度字符串
						// sCut = oldstring.substring(is1, is1 + iLen);

						if (oldstring.substring(is1, is1 + iLen).length() > 0) {
							sCut =oldstring.substring(is1, is1 + iLen)
									+ "※"+ sCut  ;
						}
					} else if (iLen == 0 || iLen >= oldstring.length() - is1) { // ///复制之后所有字符串
						// sCut = oldstring.substring(is1);
						if (oldstring.substring(is1).length() > 0) {
							sCut = oldstring.substring(is1) + "※" +sCut ;
						}
					}
				} else { // 常规复制
					// sCut = oldstring.substring(i1, i2);

					if (oldstring.substring(i1, i2).length() > 0) {
						sCut =  oldstring.substring(i1, i2) + "※"+sCut ;
					}
				}
				rmsDo(true);
				// System.out.println("复制的东东:"+sCut+is1+is2);

				MIDtxt.dp.setCurrent(lastD);// 恢复显示
				break;
			case 3: // 取消
				/*bres = "";
				kres = "";

				zres = "";*/

				MIDtxt.dp.setCurrent(lastD);// 恢复显示
				/*
				 * if (cd.getCommandType() == 7) { // 需要存储数据 rmsDo(true);// 写入数据 }
				 */
				break;
			case 4: // 设置确认
				String oldsSet = sSet;
				int oldiMax = iMax;
				int oldiLen = iLen;
				String oldsFile = sFile;
				String oldtsfh = tsfh;
				int oldzx = zx;
				try {
					// String sset = tshow.getString();// 获取设置字符串
					// int p1 = sset.indexOf("复制长度=");
					// iMax = Integer.parseInt(sset.substring("字限=".length(),
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
					// int p2 = sset.indexOf("隐藏=");
					// iLen = Integer.parseInt(sset.substring(p1+
					// "复制长度=".length(), p2));
					iLen = Integer.parseInt(tbb.getString());
					zx = Integer.parseInt(tbn.getString());
					// int p3 = sset.indexOf("文件=");
					/*
					 * sHide = sset.substring(p2 + "隐藏=".length(), p3);
					 * 
					 * hideCP = sHide.equalsIgnoreCase("是"); sHide = hideCP ?
					 * "是" : "否";
					 */
					if (AddFuhao.isSelected(0)) {
						sHide = "是";
						hideCP = true;
					} else {
						sHide = "否";
						hideCP = false;
					}
					try {
						if (FJZH.isSelected(0)) {
							sty = "是";
							tyCP = true;
							tfk.setConstraints(0);
						} else {
							sty = "不是";
							tyCP = false;
							tfk.setConstraints(tfk.oi);
						}
					} catch (Exception e) {
					} finally {
						try {
							if (FJZH.isSelected(0)) {
								sty = "是";
								tyCP = true;
								tbk.setConstraints(0);
							} else {
								sty = "不是";
								tyCP = false;
								tbk.setConstraints(tbk.oi);

							}
						} catch (Exception e) {
						}
					}

					try {
						hideMenu(tfk, hideCP); // 显隐菜单
					} catch (Exception e) {
					} finally {
						try {
							hideMenu(tbk, hideCP); // 显隐菜单
						} catch (Exception e) {
						}
					}
					// int p4 = sset.lastIndexOf('=');
					// sFile = sset.substring(p4 + 1);
					sFile = tfn.getString();
					tsfh = tfs.getString();
					sSet = "字限=" + iMax + "复制长度=" + iLen + "隐藏=" + sHide
							+ "文件=" + sFile; // 设置内容串
					System.out.println(sSet);
					rmsDo(true);
					// tfk.setString(oldstf+"_成");
					MIDtxt.dp.setCurrent(lastD);// 恢复显示
				} catch (Exception e) {
					e.printStackTrace();
					sSet = oldsSet; // //出错复原原设置
					iLen = oldiLen;
					iMax = oldiMax;
					sFile = oldsFile;
					tsfh = oldtsfh;
					zx = oldzx;
					// t.setString(t.getString()+"_败");
				}
				break;
				case 5:
				new Lock().startApp();
				break;
				case 6:
				MIDtxt.close();
				break;
			case 7:// 导入文本
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
					sSet = "字限=" + iMax + "复制长度=" + iLen + "隐藏=" + hideCP
							+ "文件=" + sFile; // 设置内容串
					rmsDo(true);
					String stext = "";
					if (((ChoiceGroup) fft.get(1)).isSelected(0)) {// utf-8模式
						stext = new String(b, 0, b.length, "utf-8");
					} else {
						stext = new String(b);
					}
					// System.out.println("tbk="+tbk);
					if (((ChoiceGroup) fft.get(2)).isSelected(0)) { // 直接插入
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
					// This is very dangerous！\nPlease leave quickly！

					MIDtxt.dp.setCurrent(lastD);// 恢复显示

				} catch (Exception e) {
					e.printStackTrace();
					Alert ae = new Alert("导入文本", url + "\n导入失败" + "\n"
							+ e.getMessage(), null, AlertType.ERROR);
					MIDtxt.dp.setCurrent(ae);
					// fft.append("错误!" );

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
			case 8:// 导出文本
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
						boolean bw = ((ChoiceGroup) f.get(2)).isSelected(0);// 附于末尾
						try {
							fc = (FileConnection) Connector.open(url,
									Connector.READ_WRITE);
							if (bw) {// 附于末尾
								if (!fc.exists()) {
									fc.create();
								}
								os = fc.openOutputStream(fc.fileSize());
							} else { // 重写
								System.out.println("重写");
								if (fc.exists()) {
									fc.delete();
								}
								fc.create();
								os = fc.openOutputStream();
							}
							String sout = "导出的文本";
							try {
								sout = tfk.getString();
							} catch (Exception e) {
							} finally {
								try {
									sout = tbk.getString();
								} catch (Exception e) {
								}
							}
							if (((ChoiceGroup) f.get(1)).isSelected(0)) {// utf-8模式
								os.write(sout.getBytes("utf-8"));
							} else {
								os.write(sout.getBytes());
							}
							sFile = url;
							sSet = "字限=" + iMax + "复制长度=" + iLen + "隐藏="
									+ hideCP + "文件=" + sFile; // 设置内容串
							rmsDo(true);
							// tfk.setString(oldstring+"_成"); ////恢复字符串
							MIDtxt.dp.setCurrent(lastD);// 恢复显示

						} catch (Exception e) {
							e.printStackTrace();
							Alert ae = new Alert("导出文本", url + "\n导出失败" + "\n"
									+ e.getMessage(), null, AlertType.ERROR);
							MIDtxt.dp.setCurrent(ae);

							/* f.append("错误!" + e.getMessage()); */
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
				fsSet(5, "实用工具(Fuck Q！)");
				break;
				case 10:
				new kavax.http.HttpView().startApp();
				break;
			case 11: // 参数设置
				ksk();
				break;
			case 12: // RMS操作
			MIDtxt.lastD1=MIDtxt.dp.getCurrent();
				Form frms = new Form("RMS操作");
				oos = 1;
				frms.append(new TextFiles("", sRms, 1024, 0));
				frms.append(new ChoiceGroup("", 1, new String[] { "导出", "导入" },
						null));
				frms.addCommand(new Command("确定", 4, 21));
				frms.addCommand(new Command("返回", 7, 6));
				frms.setCommandListener(this);
				MIDtxt.dp.setCurrent(frms);
				break;
			case 13: // 亮度调节
			/*	Form fl = new Form("亮度调节");
				Gauge g1 = new Gauge("", true, 100, iLight);
				// g1.setValue(iLight);
				g1.setLayout(Gauge.LAYOUT_CENTER);
				fl.append(g1);
				Gauge g2 = new Gauge("", true, 10, iLight / 10);
				g2.setLayout(Gauge.LAYOUT_CENTER);
				// g2.setValue(iLight/10);
				fl.append(g2);
				fl.addCommand(new Command("返回", 7, 3));
				fl.setCommandListener(this);
				fl.setItemStateListener(this);
				MIDtxt.dp.setCurrent(fl);*/
				MIDtxt.lastD1=MIDtxt.dp.getCurrent();
				new light(iLight);
				break;
			case 14: // 简单编辑
				// GhostGzt//
				Form ft = new Form("添加符号");
				tit();
				/*if (zres == "") {
					zres = tbk.getString();
				}*/
				tbx = new TextField("内容", tbk.getString(), ti , 0);

				ft.append(tbx);
				FH xxk = new FH();

				fuhao = xxk.Conversion(tsfh);

				AddFuhao = new ChoiceGroup("特殊符号添加", 4, fuhao, null);
				ft.append(AddFuhao);
				/*if (String.valueOf(xres) == "") {
					xres = 0;
				}
				AddFuhao.setSelectedIndex(xres, true);
*/
				tbb = new TextField("序列数", String.valueOf(0), 10, 0);
				ft.append(tbb);
				ft.append("");
				/*if (yres != "") {
					tbb.setString(yres);
				}*/
				ft.addCommand(new Command("确定", 4, 29));
				ft.addCommand(new Command("查看", 1, 58));
				ft.addCommand(new Command("文本替换", 2, 30));
				ft.addCommand(new Command("添缀输入", 2, 31));
				ft.addCommand(new Command("繁简转换", 2, 35));
				ft.addCommand(new Command("简单编辑", 2, 43));
				ft.addCommand(new Command("返回", 7, 3));
				ft.setCommandListener(this);
				MIDtxt.dp.setCurrent(ft);
				break;
			// GhostGzt//
			// break;
			case 15: // 系统信息
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

				Form fs = new Form("系统信息");

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
						"欢迎使用ST5,Made By GhostGzt(Gentle),Fuck Q！"));
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
					fs.append("送你一个字: " + sti + "\n");
				} catch (Exception e) {
					fs.append("送你一个字: 好\n");
				}
				try {
					fs.append("TextBox文字统计:\n已输入" + String.valueOf(tbk.size())
							+ "字\n总可输入" + String.valueOf(this.ti) + "字\n原可输入"
							+ String.valueOf(tbk.ki) + "字\n");
				} catch (Exception e) {
				}
				try {
					fs.append("TextField文字统计:\n已输入"
							+ String.valueOf(tfk.size()) + "字\n总可输入"
							+ String.valueOf(this.ti) + "字\n原可输入"
							+ String.valueOf(tfk.ki) + "字\n");
				} catch (Exception e1) {
				}

				fs.append("程序信息\n");
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
				fs.append("温馨提示:以下内容仅供参考");
				fs.append("手机型号:" + System.getProperty("microedition.platform")
						+ "\n");
				fs.append("总体运存:" + Runtime.getRuntime().totalMemory() + "\n");
				fs.append("可用运存:" + Runtime.getRuntime().freeMemory() + "\n");
				// fs.append("可用运存:"+Display);
				fs.append("MIDP版本:"
						+ System.getProperty("microedition.profiles") + "\n");
				fs.append("CLDC版本:"
						+ System.getProperty("microedition.configuration")
						+ "\n");
				fs.append("支持彩色:" + MIDtxt.dp.isColor() + "\n");
				fs.append("彩色灰度:" + MIDtxt.dp.numColors() + "\n");
				fs.append("支持透明:" + MIDtxt.dp.numAlphaLevels() + "\n");
				fs.append("支持背闪:" + MIDtxt.dp.flashBacklight(0) + "\n");
				fs.append("支持振动:" + MIDtxt.dp.vibrate(0) + "\n");
				fs.append("支持混音:" + System.getProperty("supports.mixing")
						+ "\n");
				fs.append("支持录音:"
						+ System.getProperty("supports.audio.capture") + "\n");
				fs.append("支持录像:"
						+ System.getProperty("supports.video.capture") + "\n");
				fs.append("音频格式:" + System.getProperty("audio.encodings")
						+ "\n");
				fs.append("视频格式:" + System.getProperty("video.encodings")
						+ "\n");
				fs
						.append("截屏格式"
								+ System
										.getProperty("video.snapshot.encodings")
								+ "\n");
				fs.append("流媒支持:" + System.getProperty("streamable.contents")
						+ "\n");
				fs.append("短信中心:"
						+ System.getProperty("wireless.messaging.sms.smsc")
						+ "\n");
				fs.append("图片目录:" + System.getProperty("fileconn.dir.photos")
						+ "\n");
				fs.append("视频目录:" + System.getProperty("fileconn.dir.videos")
						+ "\n");
				fs.append("声音目录:" + System.getProperty("fileconn.dir.tones")
						+ "\n");
				fs.append("存储目录:"
						+ System.getProperty("fileconn.dir.memorycard") + "\n");
				fs.append("私有目录:" + System.getProperty("fileconn.dir.private")
						+ "\n");
				fs
						.append("分隔符号:" + System.getProperty("file.separator")
								+ "\n");
				fs.addCommand(new Command("返回", 7, 3));
				fs.setCommandListener(this);
				MIDtxt.dp.setCurrent(fs);

				break;
			case 16: // 发送短信
				// GhostGzt//
				Form fr = new Form("发送短信");
				tit();
				tbx = new TextField("发送内容", tbk.getString(), this.ti + 1024, 0);
				fr.append(tbx);
				tbb = new TextField("手机号码", null, 11, 3);
				fr.append(tbb);
				fr.addCommand(new Command("确定", 4, 37));
				fr.addCommand(new Command("返回", 7, 3));
				fr.addCommand(new Command("移动流量", 1, 38));
				fr.addCommand(new Command("联通流量", 1, 39));
				fr.setCommandListener(this);
				MIDtxt.dp.setCurrent(fr);
				break;
			// GhostGzt//键值测试
			// keyCanvas kc=new keyCanvas(lastD);
			// kc.setTitle("键值测试(长按0返回)");
			// kc.addCommand(new Command("返回",2,3));
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
			case 21: // RMS操作确认
				String jgs = "";
				String cob = "";
				try {
					String url1 = ((TextField) f.get(0)).getString();
					if (((ChoiceGroup) f.get(1)).isSelected(0)) { // 导出
						rmsDo(true);// 写入当前设置
						rmsOutput(url1);
						jgs = "导出";
						cob = "成功！";
						// f.append("导出成功!\n");
					} else { // 导入
						rmsInput(url1);
						jgs = "导入";
						cob = "成功！";
						// f.append("导入成功!\n");
					}
					sRms = url1;
				} catch (Exception e) {
					jgs = "操作";
					cob = "失败！";
					// f.append("操作失败!\n");
				}
				Alert ae = new Alert(jgs + "RMS", sRms + "\n" + jgs + cob,
						null, AlertType.INFO);
				MIDtxt.dp.setCurrent(ae);
				break;
			/* GhostGzt */
			case 22:
				if (!tbb.getString().equals("")) {
					tbk.setString(tbb.getString());
					MIDtxt.dp.setCurrent(lastD);// 恢复显示
					if (cd.getCommandType() == 7) { // 需要存储数据
						rmsDo(true);// 写入数据
					}
				} else {
					if (!tbx.getString().equals("")) {
						// (new Thread(new Runnable() {
						// Form f = (Form) d;

						// public void run() {
						fft = new Form("Google");
						/*
						 * try{ fft.set(4,new StringItem(null, "正在翻译中..."));}
						 * catch(Exception e1){ fft.append("正在翻译中..."); }
						 */
						Alert x1 = new Alert("Google", "正在翻译...", null,
								AlertType.INFO);
						MIDtxt.dp.setCurrent(x1);

						//tob = 0;
					//	String yyt = ggb(tbx.getString());
ggb(tbx.getString());
						//if (Integer.parseInt(yyt) == 1 &&tbb.size()>0) {
							// f.append("\n");
							// f.append("翻译完成！");
							/*
							 * try{ fft.set(4,new StringItem(null, "翻译完成！"));
							 * }catch(Exception e2){ fft.append("翻译完成！");
							 *  }
							 */
						//	Alert x2 = new Alert("Google", "翻译完成！", null,
							//		AlertType.INFO);
							//MIDtxt.dp.setCurrent(x2);
						//} else {
							// f.append("\n");
							// f.append("翻译失败！");
							/*
							 * try{ fft.set(4,new StringItem(null, "翻译失败！"));
							 * }catch(Exception e3){ fft.append("翻译失败！"); }
							 */
						/*	Alert x3 = new Alert("Google", "翻译失败！", null,
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
					// fft.set(4,new StringItem(null, "正在翻译中..."));
					Alert x1 = new Alert("Google", "正在翻译中...", null,
							AlertType.INFO);
					MIDtxt.dp.setCurrent(x1);
					//tob = 0;
					//String yyt = ggb(tbx.getString());
 ggb(tbx.getString());
					//if (Integer.parseInt(yyt) == 1&&tbb.size()>0) {
						// f.append("\n");
						// f.append("翻译完成！");
						// fft.set(4,new StringItem(null, "翻译完成！"));
						//Alert x2 = new Alert("Google", "翻译完成！", null,
							//	AlertType.INFO);
						//MIDtxt.dp.setCurrent(x2);
					//} else {
						// f.append("\n");
						// f.append("翻译失败！");
						// fft.set(4,new StringItem(null, "翻译失败！"));
					/*	Alert x3 = new Alert("Google", "翻译失败！", null,
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
				 * "正在翻译中...")); String yyt= ggb(bres);
				 * 
				 * if (yyt=="1") { //f.append("\n"); //f.append("翻译完成！");
				 * f.set(5,new StringItem(null, "翻译完成！"));
				 *  } else { //f.append("\n"); //f.append("翻译失败！"); f.set(5,new
				 * StringItem(null, "翻译失败！")); } } })).start(); } return;
				 */
			case 28:
				/*
				 * tfk.setString(rres); MIDtxt.dp.setCurrent(lastD);// 恢复显示 if
				 * (cd.getCommandType() == 7) { // 需要存储数据 rmsDo(true);// 写入数据 }
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
							f.set(3, new StringItem(null, "已添加" + tqd + "\n"));
							// f.append("已添加" + tqd + "\n");
						}
					}
				}.start();

				return;
			case 30:
				Form fh = new Form("文本替换");
				tit();
				tbx = new TextField("原文内容", tbk.getString(), this.ti , 0);
				fh.append(tbx);
				tbb = new TextField("查找内容", null, 1024 , 0);
				fh.append(tbb);
				tbn = new TextField("替换内容", null, 1024 , 0);
				fh.append(tbn);
				fh.addCommand(new Command("确定", 4, 32));
				fh.addCommand(new Command("添加符号", 2, 14));
				fh.addCommand(new Command("添缀输入", 2, 31));
				fh.addCommand(new Command("繁简转换", 2, 35));
				fh.addCommand(new Command("简单编辑", 2, 43));
				fh.addCommand(new Command("返回", 7, 3));
				fh.setCommandListener(this);
				MIDtxt.dp.setCurrent(fh);
				break;
			case 31:
				Form fz = new Form("添缀输入");
				tit();
				/*if (kres == "") {
					kres = tbk.getString();
				}*/
				tbx = new TextField("原文内容",  tbk.getString(), this.ti , 0);
				fz.append(tbx);
				tbb = new TextField("添加内容", null, 2048, 0);
				fz.append(tbb);
				fz.addCommand(new Command("确定", 4, 33));
				fz.addCommand(new Command("预览", 1, 34));
				fz.addCommand(new Command("重写", 1, 40));
				fz.addCommand(new Command("摘写", 1, 41));
				fz.addCommand(new Command("覆盖", 1, 42));
				fz.addCommand(new Command("添加符号", 2, 14));
				fz.addCommand(new Command("文本替换", 2, 30));
				fz.addCommand(new Command("繁简转换", 2, 35));
				fz.addCommand(new Command("简单编辑", 2, 43));
				fz.addCommand(new Command("返回", 7, 3));
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
							f.append("已将" + tbb.getString() + "替换为"
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
				Form yl = new Form("预览");
				yl.append(tbx.getString() + tbb.getString());
				yl.addCommand(new Command("返回", 1, 6));
				yl.addCommand(new Command("取消", 2, 3));
				yl.setCommandListener(this);
				MIDtxt.dp.setCurrent(yl);
				break;
			case 35:
				Form ff = new Form("繁简转换");
				tit();
				tbx = new TextField("原文内容", tbk.getString(), this.ti, 0);
				ff.append(tbx);
				FJZH = new ChoiceGroup("选译字体", 4, qhl, null);
				ff.append(FJZH);
				ff.addCommand(new Command("确定", 4, 36));
				ff.addCommand(new Command("添加符号", 2, 14));
				ff.addCommand(new Command("文本替换", 2, 30));
				ff.addCommand(new Command("添缀输入", 2, 31));
				ff.addCommand(new Command("简单编辑", 2, 43));
				ff.addCommand(new Command("返回", 7, 3));
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
				 * tshow.addCommand(new Command("确定", 1, 44));
				 * tshow.addCommand(new Command("取消", 2, 3));
				 * tshow.setCommandListener(this); MIDtxt.dp.setCurrent(tshow);
				 */
				System.out.println("Starting...");
				tit();
				tx = new TextBoz("简单编辑", tbk.getString(), this.ti, 0);
				tx.addCommand(new Command("确定", 4, 44));
				tx.addCommand(new Command("添加符号", 2, 14));
				tx.addCommand(new Command("文本替换", 2, 30));
				tx.addCommand(new Command("添缀输入", 2, 31));
				tx.addCommand(new Command("繁简转换", 2, 35));
				tx.addCommand(new Command("取消", 7, 3));
				tx.setCommandListener(this);
				MIDtxt.dp.setCurrent(tx);
				break;
			case 44:
				tbk.setString(tx.getString());
				MIDtxt.dp.setCurrent(lastD);// 恢复显示
				if (cd.getCommandType() == 7) { // 需要存储数据
					rmsDo(true);// 写入数据
				}
				break;
			case 45:
				/*
				 * ini=0; ggy(tfk.getString(),3);
				 */
				break;
			case 47:// 空缺

				initApp();
				// refreshData();
				// (new Thread(this)).start();
				break;
			case 48:
				/*
				 * int isx = tshow.getCaretPosition();// 当前光标位置 if(isx==0){
				 * }else{ String snh = tshow.getString(); String snn =
				 * snh.substring(0, isx-1); tshow.setString("");
				 * tshow.insert(snn,0); tshow.insert(snh.substring(isx),
				 * tshow.getString().length()); }
				 */
				if (tshow.size() > 0) {
					oldstring = tshow.getString(); // 保存旧字符串
					int isx = tshow.getCaretPosition();// 第一光标位置
					String stemp = oldstring.substring(0, isx) + "※"
							+ oldstring.substring(isx);
					tit();
					tshow = new TextBow("自由删除", stemp, ti + 1, 0);
					tshow.setCommandListener(this);
					tshow.addCommand(new Command("更改起点", 7, 1));
					tshow.addCommand(new Command("删除文本", 4, 54));
					tshow.addCommand(new Command("取消", 2, 55));
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

				MIDtxt.dp.setCurrent(lastD);// 恢复显示
				if (cd.getCommandType() == 7) { // 需要存储数据
					rmsDo(true);// 写入数据
				}
				break;
			case 51:
				/*
				 * if (tshow.getString()==null){}else{ sCut=tshow.getString(); }
				 */

				int isd = tshow.getCaretPosition();// 第二光标位置
				int isc = tshow.getString().indexOf('※');
				int ix3 = Math.min(isc, isd);
				int ix4 = Math.max(isc, isd);
				if (isc < isd) {
					ix4 = ix4 - 1;
				}
				if (-1 <= isc - isd && isc - isd <= 0) {// 光标位置相同
					if (isc >= oldstring.length()) { // /// 光标位置在末尾复制全部
						tshow.setString(oldstring);
						if (oldstring.length() > 0) {
							sCut =oldstring + "※" + sCut ;
						}
					} else if (iLen != 0 && iLen < oldstring.length() - isc) { // ///
						// 复制一定长度字符串
						tshow.setString(oldstring);

						// sCut = oldstring.substring(isc, isc + iLen);
						if (oldstring.substring(isc, isc + iLen).length() > 0) {
							sCut = oldstring.substring(isc, isc + iLen)
									+ "※"+sCut  ;
						}
					} else if (iLen == 0 || iLen >= oldstring.length() - isc) { // ///复制之后所有字符串
						tshow.setString(oldstring);
						// sCut = oldstring.substring(isc);
						if (oldstring.substring(isc).length() > 0) {
							sCut = oldstring.substring(isc) + "※" +sCut ;
						}

					}
				} else { // 常规复制
					tshow.setString(oldstring);
					// sCut = oldstring.substring(ix3, ix4);
					if (oldstring.substring(ix3, ix4).length() > 0) {
						sCut =oldstring.substring(ix3, ix4) + "※"  + sCut;
					}

				}
				rmsDo(true);

				// System.out.println("复制的东东:"+sCut+isc+isd);
				// MIDtxt.dp.setCurrent(lastD);// 恢复显示

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
				int isy = tshow.getCaretPosition();// 第二光标位置
				int isx = tshow.getString().indexOf('※');
				int ix1 = Math.min(isx, isy);
				int ix2 = Math.max(isx, isy);
				if (isx < isy) {
					ix2 = ix2 - 1;
				}
				if (-1 <= isx - isy && isx - isy <= 0) {// 光标位置相同
					if (isx >= oldstring.length()) { // /// 光标位置在末尾复制全部
						tshow.setString(oldstring);
					} else if (iLen != 0 && iLen < oldstring.length() - isx) { // ///
						// 复制一定长度字符串
						tshow.setString(oldstring);
						tshow.delete(isx, isx + iLen);
						// sCut = oldstring.substring(isx, isx + iLen);
					} else if (iLen == 0 || iLen >= oldstring.length() - isx) { // ///复制之后所有字符串
						// sCut = oldstring.substring(isx);
						if (isx > 0) {
							tshow.setString(oldstring);
							tshow.delete(isx);
						} else {
							if (tshow.getString().indexOf('※') == 0) {
								tshow.setString(oldstring);
							}
						}

					}
				} else { // 常规复制
					// sCut = oldstring.substring(ix1, ix2);
					tshow.setString(oldstring);
					tshow.delete(ix1, ix2);

				}
				rmsDo(true);

				// System.out.println("复制的东东:"+sCut+isx+isy);
				// MIDtxt.dp.setCurrent(lastD);// 恢复显示
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
					oldstring = tshow.getString(); // 保存旧字符串
					int isk = tshow.getCaretPosition();// 第一光标位置
					String stemp = oldstring.substring(0, isk) + "※"
							+ oldstring.substring(isk);
					tit();

					tshow = new TextBow("自由复制", stemp, ti + 1, 0);
					tshow.setCommandListener(this);
					tshow.addCommand(new Command("更改起点", 1, 1));
					tshow.addCommand(new Command("复制文本", 1, 51));
					tshow.addCommand(new Command("取消", 2, 55));
					MIDtxt.dp.setCurrent(tshow);
				}
				break;
			case 57:
				int isy2 = tshow.getCaretPosition();// 第二光标位置
				int isx2 = tshow.getString().indexOf('※');
				int ix12 = Math.min(isx2, isy2);
				int ix22 = Math.max(isx2, isy2);
				if (isx2 < isy2) {
					ix22 = ix22 - 1;
				}
				if (-1 <= isx2 - isy2 && isx2 - isy2 <= 0) {// 光标位置相同
					if (isx2 >= oldstring.length()) { // /// 光标位置在末尾复制全部
						tshow.setString(oldstring);
					} else if (iLen != 0 && iLen < oldstring.length() - isx2) { // ///
						// 复制一定长度字符串
						tshow.setString(oldstring);
						tshow.delete(isx2, isx2 + iLen);
						// sCut = oldstring.substring(isx2, isx2 + iLen);
					} else if (iLen == 0 || iLen >= oldstring.length() - isx2) { // ///复制之后所有字符串
						// sCut = oldstring.substring(isx2);
						if (isx2 > 0) {
							tshow.setString(oldstring);
							tshow.delete(isx2);
						} else {
							if (tshow.getString().indexOf('※') == 0) {
								tshow.setString(oldstring);
							}
						}

					}
				} else { // 常规复制
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
				// System.out.println("复制的东东:"+sCut+isx2+isy2);
				MIDtxt.dp.setCurrent(lastD);// 恢复显示
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
				rp.addCommand(new Command("返回", 2, 6));
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

				MIDtxt.dp.setCurrent(lastD);// 恢复显示
				if (cd.getCommandType() == 7) { // 需要存储数据
					rmsDo(true);// 写入数据
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

					Alert k1 = new Alert("分页编辑", "字数少于每页字数\n故不用分页编辑！", null,
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
				Form px = new Form("总页数" + String.valueOf(nu));
				tbx = new TextField("转至页", String.valueOf(bx), String.valueOf(
						nu).length(), 0);
				px.append(tbx);
				tbn = new TextField("每页字符数", String.valueOf(zx), 5, 0);
				px.append(tbn);
				px.setCommandListener(this);
				px.addCommand(new Command("转至", 1, 66));
				px.addCommand(new Command("取消", 2, 69));

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
				MIDtxt.dp.setCurrent(lastD);// 恢复显示
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
				fsSet(2, "拓展功能(Fuck Q！)");
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
				fsSet(3, "经典游戏(Fuck Q！)");
				break;
			case 80:
				fsSet(4, "3D屏保(Fuck Q！)");
				break;
			case 81:
				fsSet(2, "拓展功能(Fuck Q！)");
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
Alert cs = new Alert("传说召唤", "不要迷恋哥,个只是个传说！\n"
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
		} else { // 新文本框
			try {
				tbk = ((TextBok) d);
				lastD = MIDtxt.dp.getCurrent();
				if (!sameCmd(cClear, cd) && cd.getLabel().indexOf("清空") != -1) { // //如果本身有清空，隐藏本身的菜单
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
					&& !sameCmd(cx2, cd)) { // ///原带菜单
				ccl.commandAction(cd, d);
				return;
			}
			if (sameCmd(tbk.cCnf, cd)) {
				System.out.println("确认");
				ccl.commandAction(tbk.cOK, d);
				return;
			}
			int p = cd.getPriority();
			switch (p) {
			case 2: // ///复制
				oldstring = tbk.getString(); // 保存旧字符串
				int is1 = tbk.getCaretPosition();// 第一光标位置
				String stemp = oldstring.substring(0, is1) + "※"
						+ oldstring.substring(is1);
				tit();
				tshow = new TextBow("自由复制", stemp, ti + 1, 0);
				tshow.setCommandListener(this);
				tshow.addCommand(new Command("更改起点", 7, 1));
				tshow.addCommand(new Command("复制文本", 4, 2));
				tshow.addCommand(new Command("取消", 1, 3));
				MIDtxt.dp.setCurrent(tshow);
				break;
			case 3: // ///粘贴
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
				oldstring = tbk.getString(); // 保存旧字符串
				int isn = tbk.getCaretPosition();// 第一光标位置
				String stemp1 = oldstring.substring(0, isn) + "※"
						+ oldstring.substring(isn);
				tit();
				tshow = new TextBow("自由删除", stemp1, this.ti + 1, 0);
				tshow.setCommandListener(this);
				tshow.addCommand(new Command("更改起点", 7, 1));
				tshow.addCommand(new Command("删除文本", 4, 57));
				tshow.addCommand(new Command("取消", 1, 3));
				MIDtxt.dp.setCurrent(tshow);
				break;
			case 5:// ///清空
				tbk.setString("");
				break;
			case 6:// ////设置
				// GhostGzt//
			//	fsSet(1, "功能中心(Fuck Q！)");
			//lastD=MIDtxt.dp.getCurrent();
			MIDtxt.lastDx= MIDtxt.dp.getCurrent();
			MIDtxt.dp.setCurrent(new Splash(MIDtxt.mk));
				break;
			case 7: // 导入文本
				Form f = new Form("导入设置");
				tit();
				oos = 0;
				f.append(new TextFiles("导入路径", sFile,1024, 0));
				f.append(new ChoiceGroup("编码", ChoiceGroup.POPUP, new String[] {
						"UTF-8", "ANSI" }, null));
				f.append(new ChoiceGroup("方式", ChoiceGroup.POPUP, new String[] {
						"插入当前位置", "替换原字符串" }, null));
				f.addCommand(new Command("确定", 4, 7));
				f.addCommand(new Command("取消", 7, 3));
				f.setCommandListener(this);
				MIDtxt.dp.setCurrent(f);
				break;
			case 8:// 导出文本
				f = new Form("导出设置");
				tit();
				oos = 1;
				tff = new TextFiles("导出路径", sFile,1024, 0);
				f.append(tff);
				// f.append(new TextField("导出路径", sFile, this.ti, 0));
				f.append(new ChoiceGroup("编码", ChoiceGroup.POPUP, new String[] {
						"UTF-8", "ANSI" }, null));
				f.append(new ChoiceGroup("方式", ChoiceGroup.POPUP, new String[] {
						"附于文件末尾", "重写文本文件" }, null));
				f.addCommand(new Command("确定", 4, 8));
				f.addCommand(new Command("取消", 7, 3));
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
		// System.out.println("点击了新文本框f按钮");
		Fork fi = (Fork) MIDtxt.dp.getCurrent(); // 获得了所在Fork
		try {
			tfk = ((TextFielk) item);
			lastD = MIDtxt.dp.getCurrent();
			// //////////////////////////////////////////////
			if (sameCmd(tfk.cOK, new Command("_OK", 1, 1))) {
				// System.out.println("所在fork:"+fi.getTitle()+"第一菜单"+fi.cFst.getLabel());
				tfk.cOK = fi.cFst;
				tfk
						.addCommand(tfk.cCnf = new Command(tfk.cOK.getLabel(),
								1, 1));
				fi.removeCommand(fi.cFst);
			}
			// //////////////////////////////////////////////
			if (!sameCmd(cClear, cd) && cd.getLabel().indexOf("清空") != -1) { // //如果本身有清空，隐藏本身的菜单
				tfk.removeCommand(cClear);
			}
		} catch (Exception e) {
		}
		System.out.println("点击" + cd.getLabel() + cd.getCommandType()
				+ cd.getPriority());
		if (!sameCmd(cCopy, cd) && !sameCmd(cPaste, cd) && !sameCmd(cDel, cd)
				&& !sameCmd(cClear, cd) && !sameCmd(cFn, cd)
				&& !sameCmd(cImt, cd) && !sameCmd(cExt, cd)
				&& !sameCmd(tfk.cCnf, cd) && !sameCmd(cGoo, cd)
				&& !sameCmd(cZH, cd) && !sameCmd(cx1, cd) && !sameCmd(cx2, cd)) { // ///原带菜单
			iicl.commandAction(cd, item);
			return;
		}
		// System.out.println("确认");
		if (sameCmd(tfk.cCnf, cd)) {
			System.out.println("这个是转移的菜单哦拉力" + fi);
			try {
				// iicl.commandAction(tfk.cOK, item); // 文本框菜单
				fi.cl.commandAction(tfk.cOK, fi); // 窗体菜单
			} catch (Exception e) {
				// fi.cl.commandAction(tfk.cOK, fi); //窗体菜单
				// iicl.commandAction(tfk.cOK, item); // 文本框菜单
				System.out.println(fi.cl);
			}
			return;
		}
		// System.out.println("新文本框f按钮");
		int p = cd.getPriority();
		switch (p) {
		/*
		 * case 2: // ///复制 oldstring = tfk.getString(); // 保存旧字符串 int is1 =
		 * tfk.getCaretPosition();// 第一光标位置 String stemp =
		 * oldstring.substring(0, is1) + "※" + oldstring.substring(is1); tit();
		 * tshow = new TextBow("自由复制", stemp, this.ti + 1, 0);
		 * tshow.setCommandListener(this); tshow.addCommand(new Command("更改起点",
		 * 7, 1)); tshow.addCommand(new Command("复制文本", 4, 2));
		 * tshow.addCommand(new Command("取消", 1, 3));
		 * MIDtxt.dp.setCurrent(tshow); break; case 3: // ///粘贴 int ii =
		 * tfk.getCaretPosition(); tfk.insert(sCut, ii); //String s =
		 * tfk.getString().substring(0, ii) + sCut +
		 * tfk.getString().substring(ii); // System.out.println(s+ii);
		 * //tfk.setString(s); break; case 4: oldstring = tfk.getString(); //
		 * 保存旧字符串 int ism = tfk.getCaretPosition();// 第一光标位置 String stemp3 =
		 * oldstring.substring(0, ism) + "※" + oldstring.substring(ism); tit();
		 * tshow = new TextBow("自由删除", stemp3, this.ti + 1, 0);
		 * tshow.setCommandListener(this); tshow.addCommand(new Command("更改起点",
		 * 1, 1)); tshow.addCommand(new Command("删除文本", 2, 57));
		 * tshow.addCommand(new Command("取消", 1, 3));
		 * MIDtxt.dp.setCurrent(tshow); break; case 5:// ///清空
		 * tfk.setString(""); break; case 6:// ////功能 // GhostGzt// Form fs =
		 * new Form("功能中心(Fuck Q！)"); try { Image img = Image
		 * .createImage("/kavax/microedition/lcdui/gentle.png"); fs.append(img); }
		 * catch (Throwable throwable) { } fs.append("作者:
		 * GhostGzt(Gentle)\nQQ:1275731466\n");
		 * fs.append("热烈庆祝:本人考上重本啦！\n虽然不是最好的,但是也是重本哦！\n"); fs .append("E-mail:
		 * GhostGzt@163.com\nP.S:本程序的编程全用记事本编写,有不善指出请多多包涵！！！\n感谢你们的支持,使我找到人生的意义！！！\n");
		 * fs.append("Super Text 5.0\n感谢rsRk大哥的源码支持！！！"); // GhostGzt// // Form
		 * fs=new Form("实用功能"); // fs.append(sab); fs.addCommand(new
		 * Command("参数设置", 1, 11)); fs.addCommand(new Command("RMS操作", 1, 12));
		 * fs.addCommand(new Command("亮度控制", 1, 13)); fs.addCommand(new
		 * Command("系统信息", 1, 15)); fs.addCommand(new Command("非凡阅读", 1, 45));
		 * fs.addCommand(new Command("内存整理", 1, 47)); fs.addCommand(new
		 * Command("分页编辑", 1, 62)); fs.addCommand(new Command("PIM功能", 1, 71)); //
		 * fs.addCommand(new Command("键值测试",1,16)); fs.addCommand(new
		 * Command("返回", 2, 3)); fs.setCommandListener(this);
		 * MIDtxt.dp.setCurrent(fs); break;
		 * 
		 * case 7: // 导入文本 Form f = new Form("导入设置"); tit(); oos=0; tff=new
		 * TextFiles("导入路径", sFile, this.ti, 0); f.append(tff); //f.append(new
		 * TextField("导入路径", sFile, this.ti, 0)); f.append(new ChoiceGroup("编码",
		 * ChoiceGroup.POPUP, new String[] { "UTF-8", "ANSI" }, null));
		 * f.append(new ChoiceGroup("方式", ChoiceGroup.POPUP, new String[] {
		 * "插入当前位置", "替换原字符串" }, null)); f.addCommand(new Command("确定", 4, 7));
		 * f.addCommand(new Command("取消", 7, 3)); f.setCommandListener(this);
		 * MIDtxt.dp.setCurrent(f); break; case 8:// 导出文本 f = new Form("导出设置");
		 * tit(); oos=1; tff=new TextFiles("导出路径", sFile, this.ti, 0);
		 * f.append(tff); //f.append(new TextField("导出路径", sFile, this.ti, 0));
		 * f.append(new ChoiceGroup("编码", ChoiceGroup.POPUP, new String[] {
		 * "UTF-8", "ANSI" }, null)); f.append(new ChoiceGroup("方式",
		 * ChoiceGroup.POPUP, new String[] { "附于文件末尾", "重写文本文件" }, null));
		 * f.addCommand(new Command("确定", 4, 8)); f.addCommand(new Command("取消",
		 * 7, 3)); f.setCommandListener(this); MIDtxt.dp.setCurrent(f); break;
		 * case 9: if (tfk.getString()!=""){ f = new Form("Google"); chooseold =
		 * new ChoiceGroup("原语言", 4, oldstr, null); choosenew = new
		 * ChoiceGroup("翻译语言", 4, oldstr, null); choosenet = new
		 * ChoiceGroup("网络设置", 4, network, null); Database.load();
		 * fft.append("原文:\n" + tfk.getString() + "\n"); bres = tfk.getString();
		 * fft.append(chooseold); chooseold.setSelectedIndex(Database.oldindex,
		 * true); fft.append(choosenew);
		 * choosenew.setSelectedIndex(Database.newindex, true);
		 * fft.append(choosenet); choosenet.setSelectedIndex(Database.net,
		 * true); fft.append("\n"); fft.append(""); fft.addCommand(new
		 * Command("翻译", 3, 27)); fft.addCommand(new Command("取消", 2, 3));
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

		fs.append("作者: GhostGzt(Gentle)\nQQ:1275731466\n");
		fs.append("热烈庆祝:本人考上重本啦！\n虽然不是最好的,但是也是重本哦！\n");
		fs
				.append("E-mail: GhostGzt@163.com\nP.S:本程序的编程全用记事本编写,有不善指出请多多包涵！！！\n感谢你们的支持,使我找到人生的意义！！！\n");
		fs
				.append("Super Text 5.0\n感谢rsRk大哥的源码支持！！！\nSuper Text 第五版(内核: kavaText 4.21)\n合法商标: Fuck Q！\nFuck Q！保留一切解释权利！！！");
		if (X == 1) {
			// GhostGzt//
			// fs.append(sab);

			fs.addCommand(new Command("参数设置", 1, 11));
			fs.addCommand(new Command("RMS操作", 1, 12));
		
			fs.addCommand(new Command("文本编辑", 1, 14));
			fs.addCommand(new Command("系统信息", 1, 15));
			// fs.addCommand(new Command("发送短信", 1, 16));
			fs.addCommand(new Command("非凡阅读", 1, 49));
			fs.addCommand(new Command("内存整理", 1, 47));
			fs.addCommand(new Command("分页编辑", 1, 62));
			fs.addCommand(new Command("剪辑强板", 1, 75));
			fs.addCommand(new Command("超强编辑", 1, 87));
			//fs.addCommand(new Command("拓展功能", 1, 88));
			fs.addCommand(new Command("返回", 2, 90));
		} else if (X == 2) {

			// fs.setTicker(new Ticker("欢迎使用ST5,Made By GhostGzt(Gentle),Fuck
			// Q！"));
			//fs.addCommand(new Command("基本功能", 2, 77));
			//fs.addCommand(new Command("实用工具", 1, 9));
			fs.addCommand(new Command("一键锁屏", 1, 5));
			fs.addCommand(new Command("网络浏览", 1, 10));
				fs.addCommand(new Command("发送短信", 1, 16));
			fs.addCommand(new Command("键值测试", 1, 17));
			//fs.addCommand(new Command("亮度控制", 1, 13));
			fs.addCommand(new Command("拾色秘器", 1, 20));
			//fs.addCommand(new Command("PIM功能", 1, 71));
			fs.addCommand(new Command("SVG浏览", 1, 76));
			fs.addCommand(new Command("文件管理", 1, 78));
			//fs.addCommand(new Command("经典游戏", 1, 79));
			//fs.addCommand(new Command("3D屏保", 1, 80));
			fs.addCommand(new Command("编码转换", 1, 85));
			fs.addCommand(new Command("传说召唤", 1, 86));
             fs.addCommand(new Command("返回", 2, 90));
			// fs.addCommand(new Command("返回", 2, 3));
		} else if (X == 3) {
			// fs.setTicker(new Ticker("欢迎使用ST5,Made By GhostGzt(Gentle),Fuck
			// Q！"));
			fs.addCommand(new Command("TilepuzzleGame", 1, 72));
			fs.addCommand(new Command("PushpuzzleGame", 1, 73));
			fs.addCommand(new Command("WormGame", 1, 74));
			//fs.addCommand(new Command("拓展功能", 2, 81));
			fs.addCommand(new Command("返回", 2, 90));
		} else if (X == 4) {
			// fs.setTicker(new Ticker("欢迎使用ST5,Made By GhostGzt(Gentle),Fuck
			// Q！"));
			  fs.addCommand(new Command("ManyBalls", 1, 19));
			fs.addCommand(new Command("Life3D", 1, 82));
			fs.addCommand(new Command("PogoRoo", 1, 83));
			fs.addCommand(new Command("RetainedMode", 1, 84));
			//fs.addCommand(new Command("拓展功能", 2, 81));
			fs.addCommand(new Command("返回", 2, 90));
		} else if (X == 5) {
		    fs.addCommand(new Command("亮度控制", 1, 13));
		    fs.addCommand(new Command("PIM功能", 1, 71));
			fs.addCommand(new Command("Camera", 1, 89));
			fs.addCommand(new Command("Tuner", 1, 18));
			//fs.addCommand(new Command("拓展功能", 2, 81));
			fs.addCommand(new Command("返回", 2, 90));
		}
		fs.setCommandListener(this);
		MIDtxt.dp.setCurrent(fs);
	}

	private void initApp() {

		fft = new Form("内存整理");
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
		tbx = new TextField("清理间隔（秒）:", a, 4, 2);
		fft.append(tbx);
		fft.append("总内存: " + Long.toString(Runtime.getRuntime().totalMemory())
				+ "\n");
		fft.append("可用内存: " + Long.toString(Runtime.getRuntime().freeMemory()));
		fft.append("分配: "
				+ Long.toString(Runtime.getRuntime().totalMemory()
						- (long) firstmem) + "\n");

		fft.addCommand(new Command("清理", 4, 59));
		fft.addCommand(new Command("区分", 1, 60));
		// fft.addCommand(new Command("最小化", 2, 61));
		fft.addCommand(new Command("返回", 7, 61));

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
		fft.set(1, new StringItem(null, "总内存: "
				+ Long.toString(Runtime.getRuntime().totalMemory()) + "\n"));
		fft.set(2, new StringItem(null, "可用内存: "
				+ Long.toString(Runtime.getRuntime().freeMemory()) + "\n"));
		fft.set(3, new StringItem(null, "分配: "
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
		Form yl = new Form("发送结果");
		yl.addCommand(new Command("返回", 1, 6));
		Tsms sms1 = new Tsms();
		String db = null;
		int hy;
		hy = sms1.send(num, Str);
		if (hy==1) {
			db = "发送成功！";
		} else {
			db = "发送失败！";
		}
		yl.append("发送" + Str + "到" + num + "\n" + db);
		yl.setCommandListener(this);
		MIDtxt.dp.setCurrent(yl);
	}

	public void ggk() {
		/*
		 * tshow = new TextBow("["+String.valueOf(bx)+"]", stxt, ti, 0);
		 * tshow.setCommandListener(this); tshow.addCommand(new Command("保存", 1,
		 * 68)); tshow.addCommand(new Command("修改", 1, 67));
		 * tshow.addCommand(new Command("下一页", 1, 63)); tshow.addCommand(new
		 * Command("上一页", 1, 64)); tshow.addCommand(new Command("定位页", 1, 65));
		 * tshow.addCommand(new Command("取消", 2, 3));
		 * MIDtxt.dp.setCurrent(tshow);
		 */
		Form py = new Form("分页编辑");
		tit();
		tbb = new TextField("[" + String.valueOf(bx) + "/" + String.valueOf(nu)
				+ "]" + " 字符数" + String.valueOf(stxt.length()), stxt, this.ti
				+ zx, 0);
		py.append(tbb);
		py.setCommandListener(this);
		py.addCommand(new Command("保存", 4, 68));

		py.addCommand(new Command("下一页", 1, 63));
		py.addCommand(new Command("上一页", 1, 64));
		py.addCommand(new Command("定位页", 1, 65));

		py.addCommand(new Command("重写", 1, 62));
		py.addCommand(new Command("取消", 2, 3));
		py.addCommand(new Command("修改", 7, 67));
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
			tshow.addCommand(new Command("确定", 4, 50));
			ini = 0;
		} else {
			ini = 1;
			tshow.addCommand(new Command("确定", 4, 70));
		}

		tshow.addCommand(new Command("复制", 1, 56));
		tshow.addCommand(new Command("删除", 1, 48));
		tshow.addCommand(new Command("粘贴", 1, 52));

		tshow.addCommand(new Command("清空", 1, 53));
		tshow.addCommand(new Command("取消", 7, n));
		tshow.setCommandListener(this);
		MIDtxt.dp.setCurrent(tshow);
	}

	/*public void ggw(String str) {
	MIDtxt.lastD1=MIDtxt.dp.getCurrent();
		Form fq = new Form("译文");
		fq.append(str);
		fq.addCommand(new Command("确定", 4, 6));
		fq.addCommand(new Command("取消", 7, 3));
		fq.setCommandListener(this);
		MIDtxt.dp.setCurrent(fq);
	}*/

	public void ggb(String str) {

		//bres = str;

		//rres = null;
		try {
			// f.append("\n");
			// f.append("正在翻译中...");

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
				// f.append("翻译完成！");
				// f.set(5,new StringItem(null, "翻译完成！"));
				//rres = res;
				//if (tob == 0) {
				Alert x3 = new Alert("译文", res, null,
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
				// f.append("翻译失败！");
				// f.set(5,new StringItem(null, "翻译失败！"));
					Alert x3 = new Alert("Google", "翻译失败！", null,
									AlertType.INFO);
							MIDtxt.dp.setCurrent(x3);
			//	return "0";
			}
		} catch (Throwable throwable) {
			Alert x3 = new Alert("Google", "翻译失败！", null,
									AlertType.ERROR);
							MIDtxt.dp.setCurrent(x3);
			//return "0";
			// f.append("\n");
			// f.append("翻译失败！");
			// f.set(5,new StringItem(null, "翻译失败！"));
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
				 * tshow = new TextBow("参数设置", sSet, 2048, 0);
				 * tshow.addCommand(new Command("确定", 1, 4));
				 * tshow.addCommand(new Command("删除", 1, 48));
				 * tshow.addCommand(new Command("取消", 7, 3));
				 * tshow.setCommandListener(this); MIDtxt.dp.setCurrent(tshow);
				 */
				Form setx = new Form("参数设置");
				/* try { */
				rmsDo(false);
				tbx = new TextField("字限", String.valueOf(iMax), 1024, 0);
				setx.append(tbx);
				tbb = new TextField("复制长度", String.valueOf(iLen), 1024, 0);
				setx.append(tbb);
				tbn = new TextField("分页字限", String.valueOf(zx), 5, 0);
				setx.append(tbn);
				AddFuhao = new ChoiceGroup("隐藏", 1, new String[] { "是", "否" },
						null);
				setx.append(AddFuhao);
				FJZH = new ChoiceGroup("统一输入框", 1, new String[] { "是", "否" },
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
				tfn = new TextFiles("文件", sFile, 1024, 0);
				setx.append(tfn);
				tfs = new TextFiles("特殊符号文件", tsfh, 1024, 0);
				setx.append(tfs);
				/*
				 * } catch (Exception e) { setx.deleteAll(); setx.append(new
				 * TextField("字限", String.valueOf(iMax), 1024, 0));
				 * setx.append(new TextField("复制长度", String.valueOf(iLen), 1024,
				 * 0)); if (hideCP==true){ AddFuhao =new ChoiceGroup("隐藏", 1,
				 * new String[] { "是", "否" },null); }else{ AddFuhao =new
				 * ChoiceGroup("隐藏", 1, new String[] {"否", "是"},null); }
				 * AddFuhao =new ChoiceGroup("隐藏", 1, new String[] { "是", "否"
				 * },null); setx.append(AddFuhao); AddFuhao.setSelectedIndex(1,
				 * true); setx.append(new TextField("文件", sFile, 1024, 0)); }
				 */
				setx.addCommand(new Command("确定", 4, 4));
				setx.addCommand(new Command("取消", 7, 6));
				setx.setCommandListener(this);

				MIDtxt.dp.setCurrent(setx);
				}

	public void tst() {
MIDtxt.lastD = MIDtxt.dp1.getCurrent();
				fileSelector.fs = null;
				if (fileSelector.fs == null) {
					oos = 3;
					fileSelector fxs = new fileSelector("文件列表", 3, MIDtxt.dp1,
							MIDtxt.lastD, this);

					MIDtxt.dp1.setCurrent(fxs);

				} else {

					MIDtxt.dp1.setCurrent(fileSelector.fs);
				}
				}


	public void gga(String str) {
		fft = new Form("Google");
		chooseold = new ChoiceGroup("原语言", 4, oldstr, null);
		choosenew = new ChoiceGroup("翻译语言", 4, oldstr, null);
		choosenet = new ChoiceGroup("网络设置", 4, network, null);
		tit();
		tbb = new TextField("译文", str, this.ti + 1024, 0);
		tbx = new TextField("原文", tbk.getString(), this.ti + 1024, 0);
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
		fft.addCommand(new Command("确定", 4, 22));
		fft.addCommand(new Command("取消", 7, 3));
		fft.addCommand(new Command("翻译", 3, 23));
		fft.addCommand(new Command("撤销", 4, 24));
		fft.addCommand(new Command("恢复", 5, 25));
		fft.setCommandListener(this);
		MIDtxt.dp.setCurrent(fft);
		return;
	}

	public void ggs(String str) {
	MIDtxt.lastD1=MIDtxt.dp.getCurrent();
		Form fa = new Form("译文");
		fa.append(str);
		fa.addCommand(new Command("返回", 1, 6));
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
	public static void hideMenu(TextBox tbx, boolean hide) { // 显隐菜单
		if (hide) { // /////// 需要隐藏菜单
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

	public static void hideMenu(TextFielk tfk, boolean hide) { // 显隐菜单
		/*
		 * if (hide) { // /////// 需要隐藏菜单 tfk.removeCommand(cClear);
		 * tfk.removeCommand(cCopy); tfk.removeCommand(cDel);
		 * tfk.removeCommand(cPaste); } else { tfk.addCommand(cDel);
		 * tfk.addCommand(cClear); tfk.addCommand(cCopy);
		 * tfk.addCommand(cPaste); }
		 */
	}

	public static void rmsDo(boolean write) { // /rms操作
		// MIDtxt.mk.getAppProperty("MIDlet-Name")
		try {
			if (write) { // //写
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
						sty = "不是";
					} else {
						sty = "是";
					}
					rms.addRecord(sty.getBytes("utf-8"), 0, sty
							.getBytes("utf-8").length);
					// if(sty=="不是"){sty="否";}
					rms.addRecord(tsfh.getBytes("utf-8"), 0, tsfh
							.getBytes("utf-8").length);
					rms.addRecord(sRms.getBytes("utf-8"), 0, sRms
							.getBytes("utf-8").length);
					System.out.println("设置" + sSet);
				} else {
					System.out.println(sSet);
					rms.setRecord(1, sSet.getBytes("utf-8"), 0, sSet
							.getBytes("utf-8").length);
					rms.setRecord(2, (zx + "").getBytes("utf-8"), 0, (zx + "")
							.getBytes("utf-8").length);
					rms.setRecord(3, (iLight + "").getBytes(), 0, (iLight + "")
							.getBytes("utf-8").length);
					if (sty.length() >= 2) {
						sty = "不是";
					} else {
						sty = "是";
					}
					rms.setRecord(4, sty.getBytes("utf-8"), 0, sty
							.getBytes("utf-8").length);
					// if(sty=="不是"){sty="否";}
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
					sty = "是";
					tyCP = true;
					uty = 0;
				} else {
					sty = "不是";

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
				int p1 = sset.indexOf("复制长度=");
				iMax = Integer.parseInt(sset.substring("字限=".length(), p1));
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
				int p2 = sset.indexOf("隐藏=");
				iLen = Integer.parseInt(sset.substring(p1 + "复制长度=".length(),
						p2));
				int p3 = sset.indexOf("文件=");
				hideCP = (sset.substring(p2 + "隐藏=".length(), p3)
						.equalsIgnoreCase("是"));
				sHide = hideCP ? "是" : "否";
				int p4 = sset.lastIndexOf('=');
				sFile = sset.substring(p4 + 1);
				rms.closeRecordStore();
			}
		} catch (Exception e) {
			System.out.println("貌似RMS操作失败！");
			haveRMS = false;
		}
	}

	public static void ReadMF() {

		System.out.println(MIDtxt.mk.getAppProperty("khide"));
		try {
			if (MIDtxt.mk.getAppProperty("khide").length() > 0
					&& MIDtxt.mk.getAppProperty("khide") != null) {
				hideCP = (MIDtxt.mk.getAppProperty("khide")
						.equalsIgnoreCase("是"));
				sHide = hideCP ? "是" : "否";
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
				sty = "是";
				tyCP = true;
				uty = 0;
			} else {
				sty = "不是";

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