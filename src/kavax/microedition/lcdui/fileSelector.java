package kavax.microedition.lcdui;


import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import java.util.*;
import java.io.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;

public class fileSelector extends List implements CommandListener {
	private static final String[] monthList = { "Jan", "Feb", "Mar", "Apr",
			"May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	private TextBox tname;
	private static final String[] attrList = { "Read", "Write", "Hidden" };
	public static fileSelector fs = null;
	private String stext;
	public static String zS;
	public static String sCopy="";
	private Display d;

	private Displayable dp;
	private Displayable dp1;
	private Object ob;
	Listener lx = new Listener();
	int di = 0;
	private static Image iFile = null;

	private static Image iFolder = null;

	private static Image iZip = null;
private static Image iTxt = null;
	private FileConnection fcFile = null;
	public TextField t1;
	public TextField t2;

	public fileSelector(String sTitle, int iType, Display dis,
			Displayable disp, Object obj) {

		super(sTitle, iType);

		fs = this;

		d = dis;

		dp = disp;

		ob = obj;

		initFL(); // ��ʼ���б�

		try { // ����ר��Ȩ�����

			FileConnection fc = (FileConnection) Connector
					.open("file:///x/x.x");

		} catch (Exception e) {
		}
		showFile(null); // ��ȡ��Ŀ¼
		try {
			Listener ll = new Listener();

			if (((TextField) ob).size() > 0) {
				int ue = ((TextField) ob).getString().lastIndexOf('/');
				String oip = ((TextField) ob).getString().substring(0, ue + 1);
				FileConnection fc = (FileConnection) Connector.open(oip
						.startsWith("file://") ? oip : "file://".concat(String
						.valueOf(oip)), 3);
				if (fc.exists()) {
					showFile(ll.replace("file://", "", oip));
				}

			}
		} catch (Exception e) {
		}

	}

	public static void getImage() {

		Object obj;

		try {

			iFolder = Image.createImage("/kavax/Image/folder.png");

		} catch (Exception e) {

			((Graphics) (obj = (iFolder = Image.createImage(16, 16))
					.getGraphics())).setColor(0xaaaa22); // �����ļ���ͼ��

			((Graphics) obj).fillRect(4, 4, 16, 16);

		}

		try {

			iFile = Image.createImage("/kavax/Image/file.png");

		} catch (Exception e) {

			((Graphics) (obj = (iFile = Image.createImage(16, 16))
					.getGraphics())).setColor(0xaaaaaa); // �����ļ���ͼ��

			((Graphics) obj).fillRect(4, 4, 16, 16);

		}

try {

			iTxt = Image.createImage("/kavax/Image/txt.png");

		} catch (Exception e) {

			((Graphics) (obj = (iTxt = Image.createImage(16, 16)).getGraphics()))
					.setColor(0x000000); // �����ļ���ͼ��

			((Graphics) obj).fillRect(4, 4, 16, 16);

		}
		try {

			iZip = Image.createImage("/kavax/Image/zip.png");

		} catch (Exception e) {

			((Graphics) (obj = (iZip = Image.createImage(16, 16)).getGraphics()))
					.setColor(0x22aaaa); // �����ļ���ͼ��

			((Graphics) obj).fillRect(4, 4, 16, 16);

		}

	}

	private String myDate(long time) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date(time));

		StringBuffer sb = new StringBuffer();

		sb.append(cal.get(Calendar.HOUR_OF_DAY));
		sb.append(':');
		sb.append(cal.get(Calendar.MINUTE));
		sb.append(':');
		sb.append(cal.get(Calendar.SECOND));
		sb.append(',');
		sb.append(' ');
		sb.append(cal.get(Calendar.DAY_OF_MONTH));
		sb.append(' ');
		sb.append(monthList[cal.get(Calendar.MONTH)]);
		sb.append(' ');
		sb.append(cal.get(Calendar.YEAR));

		return sb.toString();
	}

	private void initFL() {

		if (Listener.oos != 3) {
			Command zmOpen = new Command("ѡ��", 4, 5); // �˵���ok����

			this.addCommand(zmOpen);// Ϊ�б����Ӳ˵�

			this.setSelectCommand(zmOpen);// Ϊ�б����Ӳ˵�
		} else {
			Command zmOpen = new Command("��", 1, 3); // �˵���ok����

			this.addCommand(zmOpen);// Ϊ�б����Ӳ˵�

			this.setSelectCommand(zmOpen);// Ϊ�б����Ӳ˵�

		}
		if (Listener.oos == 1) {
			this.addCommand(new Command("����", 4, 6));
		}
		if (Listener.oos != 3) {
			this.addCommand(new Command("��", 1, 3));
		}
		this.addCommand(new Command("�½��ļ���", 1, 8));
		this.addCommand(new Command("�½��ı�", 1, 10));
		this.addCommand(new Command("����", 1, 12));
		this.addCommand(new Command("ճ��", 1, 13));
		this.addCommand(new Command("ɾ��", 1, 15));
		this.addCommand(new Command("������", 1, 16));
	    this.addCommand(new Command("ˢ��", 1, 20));
		this.addCommand(new Command("����", 1, 21));
		this.addCommand(new Command("��Ϊ����", 1, 18));
		this.addCommand(new Command("��Ϊ��ʾ", 1, 17));
		this.addCommand(new Command("����", 7, 2));// ����

		// this.addCommand(new Command("���ò���", 4, 4));//

		this.setCommandListener(this); // ע�������

	}

	void showProperties(String s) {
		di = this.getSelectedIndex();
		dp1 = d.getCurrent();
		try {
			if (zS.endsWith("*<") || zS.endsWith("..<")) {
				return;
			}

			FileConnection fc = (FileConnection) Connector.open(s
					.startsWith("file://") ? s : "file://".concat(String
					.valueOf(s)), 3);
			if (!fc.exists()) {
				/*
				 * Alert alert1 =new Alert("File does not exists", null,
				 * AlertType.ERROR); alert1.setTimeout(Alert.FOREVER);
				 * d.setCurrent(alert1);
				 */
				// throw new IOException("File does not exists");
				return;
			}

			Form props = new Form("Properties: "
					+ lx.replace("/", "", this.getString(this
							.getSelectedIndex())));
			ChoiceGroup attrs = new ChoiceGroup("Attributes:", Choice.MULTIPLE,
					attrList, null);

			attrs.setSelectedFlags(new boolean[] { fc.canRead(), fc.canWrite(),
					fc.isHidden() });

			props.append(new StringItem("Location:", "file://"
					+ fcFile.getPath() + fcFile.getName()));
			props.append(new StringItem("Type: ",
					fc.isDirectory() ? "Directory" : "Regular File"));
			props
					.append(new StringItem("Modified:", myDate(fc
							.lastModified())));
			props.append(attrs);

			props.addCommand(new Command("����", 7, 4));

			props.setCommandListener(this);

			fc.close();

			d.setCurrent(props);
		} catch (Exception e) {
			Alert alert = new Alert("Error!", "Can not access file " + s
					+ "\nException: " + e.getMessage(), null, AlertType.ERROR);
			alert.setTimeout(Alert.FOREVER);
			d.setCurrent(alert);
		}
	}

	public void showFile(String s1) { // ��ʾ�ļ�

		Enumeration e;

		if (s1 == null) {// ���δ�
			try {
				this.setTicker(new Ticker(
						"��ӭʹ��ST5,Made By GhostGzt(Gentle),Fuck Q��"));
			} catch (Exception e1) {
			}
			e = FileSystemRegistry.listRoots(); // ��ø�Ŀ¼

			this.deleteAll();// ���ԭ�б�

			for (; e.hasMoreElements(); this.append((String) e.nextElement(),
					iFolder))
				; // ���б��г���Ŀ¼
		}

		else if (s1.endsWith("/")) { // �Ǵ��ļ���

			try {

				e = (fcFile = (FileConnection) Connector.open("file://" + s1))
						.list("*", true);

				this.deleteAll();
				// Listener ll =new Listener();

				this.append(fcFile.getPath() + fcFile.getName() + ".*<",
						iFolder); // ��ӡ������ϼ���

				this.append("��Ŀ¼..<", iFolder); // ��ӡ������ϼ�����Ŀ

				do // ����ļ���
				{
					if (!e.hasMoreElements()) {

						break;

					}

					if ((s1 = (String) e.nextElement()).endsWith("/")) {

						this.append(s1, iFolder);

					}

				} while (true);

				e = fcFile.list("*", true);

				do {// ����ļ�

					if (!e.hasMoreElements()) {

						break;

					}

					s1 = (String) e.nextElement();

					if (!s1.endsWith("/")) {

						if (s1.endsWith(".jar") || s1.endsWith("_jar")
								|| s1.endsWith(".zip") || s1.endsWith(".lib")
								|| s1.endsWith(".kib")) {

							this.append(s1, iZip); // ����ܴ�����ļ�

						} else if(s1.endsWith(".txt") || s1.endsWith(".ini")
								|| s1.endsWith(".text") || s1.endsWith(".MF")
								|| s1.endsWith(".Txt")){
						this.append(s1, iTxt);
						
						}else {

							this.append(s1, iFile); // ����ܴ�����ļ�

						}

					}

				} while (true);

				// return;
			}

			catch (Exception ex) {
			}

		}

		try {
			this.setSelectedIndex(di, true);
		} catch (Exception ep) {
		}
	}

	void rename(String s, String n) {
		try {
			FileConnection fc = (FileConnection) Connector.open(s
					.startsWith("file://") ? s : "file://".concat(String
					.valueOf(s)), 3);
			fc.rename(n);
		} catch (Exception e) {
			Alert alert = new Alert("Error!", "Can not access/rename file " + s
					+ "\nException: " + e.getMessage(), null, AlertType.ERROR);
			alert.setTimeout(Alert.FOREVER);
			d.setCurrent(alert);
		}
	}

	void deleteFile(String s) {
		try {
			FileConnection fc = (FileConnection) Connector.open(s
					.startsWith("file://") ? s : "file://".concat(String
					.valueOf(s)), 3);
			fc.delete();

		} catch (Exception e) {
			Alert alert = new Alert("Error!", "Can not access/delete file " + s
					+ "\nException: " + e.getMessage(), null, AlertType.ERROR);
			alert.setTimeout(Alert.FOREVER);
			d.setCurrent(alert);
		}
	}

	void setHidden(String s, boolean t) {
		try {
			FileConnection fc = (FileConnection) Connector.open(s
					.startsWith("file://") ? s : "file://".concat(String
					.valueOf(s)), 3);

			fc.setHidden(t);
		} catch (Exception e) {
			Alert alert = new Alert("Error!", "Can not access/Hidden file " + s
					+ "\nException: " + e.getMessage(), null, AlertType.ERROR);
			alert.setTimeout(Alert.FOREVER);
			d.setCurrent(alert);
		}
	}

	static void mkdir(String s) throws Error, Exception {
		FileConnection fileconnection = (FileConnection) Connector.open(s
				.startsWith("file://") ? s : "file://"
				.concat(String.valueOf(s)), 3);
		if (!fileconnection.exists())
			fileconnection.mkdir();
	}

	public void goBack() {

		if (fcFile == null) {

			d.setCurrent(dp); // ����

			return;
		}

		if (fcFile.getName().equals("")) { // ��Ŀ¼

			showFile(null);

			fcFile = null;

		} else {

			showFile(fcFile.getPath());

		}

	}

	public void commandAction(Command command, Displayable disp) {

		int ic = command.getPriority();

		switch (ic) {

		case 1: // ѡ���ļ�

			zS = this.getString(this.getSelectedIndex());

			if (fcFile == null) {

				zS = "file://" + zS;

			} else {

				if (zS.endsWith("..<")) {

					zS = "file://" + fcFile.getPath() + fcFile.getName();

				} else {

					if (zS.endsWith("*<")) {
						zS = "file://" + fcFile.getPath() + fcFile.getName();

					} else {
						zS = "file://" + fcFile.getPath() + fcFile.getName()
								+ zS;
					}
				}

			}

			((TextField) ob).setString(zS);

			d.setCurrent(dp);

			return;

		case 2: // ����

			goBack();

			return;

		case 3: // Ĭ�ϣ��򿪹���,ѡ���ļ�

			zS = this.getString(this.getSelectedIndex());

			if (zS.endsWith("/")) {// ������ļ��У������showFile���ļ���

				showFile((fcFile != null ? fcFile.getPath() + fcFile.getName()
						: "/")
						+ zS);

				return;
			}

			else if (zS.endsWith("*<")) { // �Ƿ����ϼ�Ŀ¼ͼ�꣬���ϼ�Ŀ¼

				goBack();// �����ϼ�Ŀ¼

				return;

			} else { // �ļ�
				if (zS.endsWith("..<")) {
					showFile(null);
					fcFile = null;
				} else {
					String rname = fcFile.getName() + zS;
					zS = "file://" + fcFile.getPath() + fcFile.getName() + zS;

					String url = zS;
					FileConnection fc = null;
					InputStream is = null;

					try {

					} catch (Exception e) {
					}
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
					di = getSelectedIndex();
					dp1 = d.getCurrent();
					tname = new TextBox(rname, stext, 81920, TextField.ANY
							| TextField.UNEDITABLE);
					tname.addCommand(new Command("����", 1, 11));
					// tname.addCommand(new Command("����", 1,7));
					tname.addCommand(new Command("����", 2, 4));
					tname.setCommandListener(this);
					d.setCurrent(tname);

					// Form fa = new Form(rname);
					// fa.append(stext);

					/*
					 * if (url.endsWith("wav")) { ctype = "audio/x-wav"; } else
					 * if (url.endsWith("jts")) { ctype = "audio/x-tone-seq"; }
					 * else if (url.endsWith("mid")) { ctype = "audio/midi"; }
					 * else { throw new Exception("Cannot guess content type
					 * from URL: " + url); }
					 */

					/*
					 * try { Image img = Image
					 * .createImage("/kavax/microedition/lcdui/gentle.png");
					 * fa.append(img); fa.addCommand(new Command("����", 1,4));
					 * fa.setCommandListener(this); d.setCurrent(fa);
					 * }catch(Exception e){
					 * 
					 *  }
					 */

				}

			}
			return;

		case 4:

			d.setCurrent(dp1);
			showFile(fcFile.getPath() + fcFile.getName());

			return;
		case 6:

			zS = this.getString(this.getSelectedIndex());

			if (fcFile == null) {

				zS = "file://" + zS;

			} else {

				if (zS.endsWith("..<")) {

					zS = "file://" + fcFile.getPath() + fcFile.getName();

				} else {
					if (zS.endsWith("*<")) {
						zS = "file://" + fcFile.getPath() + fcFile.getName();

					} else {
						zS = "file://" + fcFile.getPath() + fcFile.getName()
								+ zS;
					}
				}

			}
			// disabled
			/*
			 * TextBox viewer = new TextBox("View File: " + fileName, null,
			 * 1024, TextField.ANY | TextField.UNEDITABLE);
			 */
			di = this.getSelectedIndex();
			dp1 = d.getCurrent();
			tname = new TextBox("�ļ���", "newfile.txt", 1024, 0);
			tname.addCommand(new Command("ȷ��", 1, 7));
			tname.addCommand(new Command("����", 1, 4));
			tname.setCommandListener(this);
			d.setCurrent(tname);

			return;

		case 7:

			String Tex;
			if (fcFile == null) {

				Tex = "file:///" + tname.getString();

			} else {
				Tex = "file://" + fcFile.getPath() + fcFile.getName()
						+ tname.getString();
			}
			((TextField) ob).setString(Tex);
			d.setCurrent(dp);
			return;
		case 8:
			di = this.getSelectedIndex();
			dp1 = d.getCurrent();
			tname = new TextBox("�½�Ŀ¼��", "newfolder", 1024, 0);
			tname.addCommand(new Command("ȷ��", 1, 9));
			tname.addCommand(new Command("����", 2, 4));
			tname.setCommandListener(this);
			d.setCurrent(tname);

			return;
		case 9:
			try {

				mkdir("file://" + fcFile.getPath() + fcFile.getName()
						+ tname.getString());
				d.setCurrent(dp1);
				d.setCurrent(dp1);
				showFile(fcFile.getPath() + fcFile.getName());
				/*
				 * Alert ae0 = new Alert("�½�Ŀ¼",
				 * "�½�Ŀ¼\n"+"file://"+fcFile.getPath()+
				 * fcFile.getName()+tname.getString()+"�ɹ���", null,
				 * AlertType.INFO); d.setCurrent(ae0);
				 */
			} catch (Error error) {
				Alert ae1 = new Alert("�½�Ŀ¼", "�½�Ŀ¼\n" + "file://"
						+ fcFile.getPath() + fcFile.getName()
						+ tname.getString() + "ʧ�ܣ�", null, AlertType.INFO);
				d.setCurrent(ae1);
				// alert$(midlet, error.toString(), AlertType.ERROR);
			} catch (Exception exception1) {
				Alert ae2 = new Alert("�½�Ŀ¼", "�½�Ŀ¼\n" + "file://"
						+ fcFile.getPath() + fcFile.getName()
						+ tname.getString() + "ʧ�ܣ�", null, AlertType.INFO);
				d.setCurrent(ae2);
				// alert$(midlet, exception1.toString(), AlertType.ERROR);
			}
			return;
		case 15:
			try {

				deleteFile("file://" + fcFile.getPath() + fcFile.getName()
						+ this.getString(this.getSelectedIndex()));
				d.setCurrent(dp1);

				showFile(fcFile.getPath() + fcFile.getName());
				/*
				 * Alert ax1 = new Alert("ɾ���ļ�", "ɾ���ɹ���", null, AlertType.INFO);
				 * d.setCurrent(ax1);
				 */
			} catch (Error error) {
				Alert ax2 = new Alert("ɾ���ļ�", "ɾ��ʧ�ܣ�", null, AlertType.INFO);
				d.setCurrent(ax2);
				// alert$(midlet, error.toString(), AlertType.ERROR);
			} catch (Exception exception1) {
				Alert ax3 = new Alert("ɾ���ļ�", "ɾ��ʧ�ܣ�", null, AlertType.INFO);
				d.setCurrent(ax3);
				// alert$(midlet, exception1.toString(), AlertType.ERROR);
			}

			return;
		case 11:
			Listener.sCut = tname.getString() + "��"+ Listener.sCut ;
			Alert ax4 = new Alert("����", "���Ƴɹ���", null, AlertType.INFO);
			d.setCurrent(ax4);
			return;
		case 21:
			try {
				showProperties("file://" + fcFile.getPath() + fcFile.getName()
						+ this.getString(this.getSelectedIndex()));
			} catch (Exception e) {
			}
			return;
		case 10:
			di = this.getSelectedIndex();
			dp1 = d.getCurrent();
			Form fa = new Form("�½�����");
			t1 = new TextField("�ļ���", "newtxt.txt", 1024, 0);
			fa.append(t1);
			t2 = new TextField("����", null, 81920, 0);
			fa.append(t2);
			fa.addCommand(new Command("ȷ��", 1, 14));
			fa.addCommand(new Command("����", 2, 4));
			fa.setCommandListener(this);
			d.setCurrent(fa);
			return;
		case 14:
			if (t1.size() > 0) {

				String url = "file://" + fcFile.getPath() + fcFile.getName()
						+ t1.getString();
				try {
					WRtxt wr = new WRtxt();
					wr.SaveUTF(url, t2.getString());
					d.setCurrent(dp1);
					showFile(fcFile.getPath() + fcFile.getName());
				} catch (Throwable throwable) {
					Alert axq = new Alert("�½��ļ�", "�½�ʧ�ܣ�", null,
							AlertType.ERROR);
					d.setCurrent(axq);
				}

			}

			return;
		case 20:
		try{
			showFile(fcFile.getPath() + fcFile.getName());
			}catch(Exception e){}
			return;
		case 16:
			try {
				di = this.getSelectedIndex();
				dp1 = d.getCurrent();
				Form fq = new Form("������");
				fq.append("������:"
						+ lx.replace("/", "", this.getString(this
								.getSelectedIndex())));
				t1 = new TextField("������", lx.replace("/", "", this
						.getString(this.getSelectedIndex())), 1024, 0);
				fq.append(t1);

				fq.addCommand(new Command("ȷ��", 1, 19));
				fq.addCommand(new Command("����", 2, 4));
				fq.setCommandListener(this);
				d.setCurrent(fq);
			} catch (Exception e) {
			}
			return;
		case 17:
			try {
				setHidden("file://" + fcFile.getPath() + fcFile.getName()
						+ this.getString(this.getSelectedIndex()), false);

				showProperties("file://" + fcFile.getPath() + fcFile.getName()
						+ this.getString(this.getSelectedIndex()));
			} catch (Exception e) {
			}
			// d.setCurrent(dp1);
			// showFile(fcFile.getPath()+fcFile.getName());
			return;
		case 18:
			try {
				setHidden("file://" + fcFile.getPath() + fcFile.getName()
						+ this.getString(this.getSelectedIndex()), true);

				showProperties("file://" + fcFile.getPath() + fcFile.getName()
						+ this.getString(this.getSelectedIndex()));
			} catch (Exception e) {
			}
			// d.setCurrent(dp1);
			// showFile(fcFile.getPath()+fcFile.getName());
			return;
		case 19:
			try {
				rename("file://" + fcFile.getPath() + fcFile.getName()
						+ this.getString(this.getSelectedIndex()), t1
						.getString());
				d.setCurrent(dp1);
				showFile(fcFile.getPath() + fcFile.getName());

			} catch (Throwable throwable) {
				Alert axq = new Alert("������", "������ʧ�ܣ�", null, AlertType.ERROR);
				d.setCurrent(axq);
			}
			return;
			case 12:
			di = this.getSelectedIndex();
			try{
			sCopy="file://" + fcFile.getPath() + fcFile.getName()
						+ this.getString(this.getSelectedIndex());
						}catch(Exception e){}
			break;
			case 13:
			di = this.getSelectedIndex();
			if (sCopy.length()>0&&sCopy!="file://" + fcFile.getPath() + fcFile.getName()+sCopy.substring(sCopy.lastIndexOf('/')+1,sCopy.length())){
				FileCap ca=new FileCap();
				
			ca.cap(sCopy,"file://" + fcFile.getPath() + fcFile.getName()+sCopy.substring(sCopy.lastIndexOf('/')+1,sCopy.length()));
			showFile(fcFile.getPath() + fcFile.getName());
			}
			break;
		case 5:

			zS = this.getString(this.getSelectedIndex());

			if (zS.endsWith("/")) {// ������ļ��У������showFile���ļ���

				showFile((fcFile != null ? fcFile.getPath() + fcFile.getName()
						: "/")
						+ zS);

				return;
			}

			else if (zS.endsWith("*<")) { // �Ƿ����ϼ�Ŀ¼ͼ�꣬���ϼ�Ŀ¼

				goBack();// �����ϼ�Ŀ¼

				return;

			} else { // �ļ�
				if (zS.endsWith("..<")) {

					showFile(null);
					fcFile = null;
				} else {
					// String rname=fcFile.getName()+ zS;
					zS = "file://" + fcFile.getPath() + fcFile.getName() + zS;

					((TextField) ob).setString(zS);

					d.setCurrent(dp);
					return;

				}
			}
		}

	}
}
