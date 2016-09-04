

package kavax.microedition.lcdui;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;


public class textCanvas extends Canvas implements CommandListener{


	public static String sText = "�ı��༭����";
	
	public static Vector vPara = new Vector();
	
	public static String saaText[][];
	
	public static String ssf[];
	
	public static int iScreen = 0;
	
	public static int iSn = 0;
	
	public static int iln = 0;
	
	public static Display d;
	
	public static Displayable dp;
	
	public Image imgbuf;
	
	public Graphics gbuf;
	
/*	public Image imglft;
	
	public Graphics glft;*/
	
	
	public Image imgrgt;
	
	public Graphics grgt;
	
	public Image imgcur;
	
	public Graphics gcur;
	
	public static int cBord = 0xBBBBBB;//�߿���ɫ
	
	public static int clFont = 0xFF00FF;//�к���ɫ
	
	public static int cpFont = 0x00FFFF;//�κ���ɫ
	
	public static int cpBgd = 0xFF00bb;//�κŵ�ɫ
	
	public static int cBgd = 0xbbffff;//������ɫ
	
	public static int cFont = 0x0000ff;//������ɫ
	
	public static int cCur = 0xff0000;//�����ɫ
	
	public static int cFind = 0xff00dd;//������ɫ
	
	public static int cSelect = 0xff0000;//ѡ����ɫ
	
	public static int W;
	
	public static int H;
	
	public static int charW;
	
	public static int charEW;
	
	public static int charH;
	
/*	public static int lineH = 10;
	
	public static int paraH = 12;*/
	
	public static int maxN;
	
	public static int minN;
	
	public static int lineN;
	
	public static int bordW = 1;
	
	public static int rbW = 2;
	
	public static int showW;
	
	public static int LW=2;//�к���ʾ���
	
	public static Font f;
	
	public static int ilc = 0;
	
	public static int irc = 0;
	
	public static boolean doS = false;
	
	public static boolean haveCut = false;
	
	
	public static int sl1 = 0;
	
	public static int sr1 = 0;
	
	public static int sl2 = 0;
	
	public static int sr2 = 0;
	
	public static String sType = "Bok";
	
	public static Object sOb = null;
	
	public Form fFR=null;//���ڲ����滻
	
	
	public Form fSet=null;//����ϵͳ����
	
	
	public static int ikDel=-8; //ɾ������ֵ
	
	////////////////////////////////////////Ԥ����˵� ����ģʽ
	
	public Command cBack=new Command("�˸�", 2, 1);
	
	public Command cExt=new Command("�����ı�", 4, 2);
	
	public Command cOK=new Command("ȷ�Ϸ���", 4, 1);
	
	public Command cImt=new Command("�����ı�", 4, 3);
	
	public Command cSet=new Command("ϵͳ����", 4, 4);
	

	
	
	////////////////////////////////////////Ԥ����˵� ���ģʽ
	
	public Command cC=new Command("����", 4, 1);
	
	public Command cX=new Command("����", 7, 2);
	

	////////////////////////////////////////
	
	
	public static String sC=""; //��������
	
	
	public static boolean bFull=false;//�Ƿ�ȫ��
	
	////////////////////////////////////////
	
	public static boolean bFMod=false; //ȫ������
	
	
	public static String sSize="240*320";//Ĭ��ȫ���ߴ�
	
	
	public static boolean bNoSelect=false;//������ػ�
	
	
	////////////////////////////////////////
	
	
	private static boolean haveRMS=true;//�Ƿ����rms
	
	public static boolean brS=false;//�Ƿ��й�
	
	public static String sFile="file:///E:///text.txt";
	
	public static textCanvas tc=null; //����������ռ����Դ
	
	
	private static int ifSize=8;//�����С
	
	
	private int headORend=0;
	
	
	
	public textCanvas(Display d, Displayable dp, String stype,Object sob){
		
		setTitle("�ı��༭");
		
		this.d = d;
		
		this.dp = dp;
		
		this.sType=stype;
		
		
		this.sOb=sob;
		

		if(tc==null){
			
			tc=this;
		}

/*		
		rmsDo(false);//��ȡ����
		
		System.out.println("�й�="+brS);*/ //�ŵ�����ʱ��ȡ
		
		
		try{
			
			if (sType.equals("Fielk"))
				
				this.sText = ((TextFielk)sOb).getString();
			
			else
			if (sType.equals("Bok"))
				
				this.sText = ((TextBok)sOb).getString();
			
			else
			if (sType.equals("Field"))
				
				this.sText = ((TextField)sOb).getString();
			
			else
			if (sType.equals("Box"))
				
				this.sText = ((TextBox)sOb).getString();
		}
		
		catch (Exception exception) { }
		
		
		//System.out.println("����"+ sText);
		
		
		addCommand(cBack);
		
		addCommand(cExt);
		
		addCommand(cImt);
		
		addCommand(cOK);
		
		addCommand(cSet);
		
		//addCommand(cFS);
		
		//addCommand(new Command("����", 2, 2));
		
		setCommandListener(this);
		
		
		pText(sText);
		
		pCur(0, 0, false);
	}
	
	
	public void resetCanvas(Display dd, Displayable ddp, String stype,Object sob){
		
		d = dd;
		
		dp = ddp;
		
		sType=stype;
		
		sOb=sob;
		
		try{
			
			if (sType.equals("Fielk"))
				
				this.sText = ((TextFielk)sOb).getString();
			
			else
			if (sType.equals("Bok"))
				
				this.sText = ((TextBok)sOb).getString();
			
			else
			if (sType.equals("Field"))
				
				this.sText = ((TextField)sOb).getString();
			
			else
			if (sType.equals("Box"))
				
				
				this.sText = ((TextBox)sOb).getString();
		}
		
		catch (Exception exception) { }
		
		
		pText(sText);
		
		pCur(ilc, irc, false);
		
		repaint();
		
		
	}

	public void pText(String s){
		
		long  lstart=System.currentTimeMillis();
		
		/////////////////////////
		
		//vPara = new Vector();
		
		saaText=null;
		
		ssf=null;
		
		iln=iSn=0;
		
		//sl1=sl2=sr1=sr2=0;
		
		/////////////////////////  ����ȫ��������
		
		if(bFMod){ //ȫ������
			
			this.setFullScreenMode(bFMod);
			
			try{
				
				String sw=sSize.substring(0, sSize.indexOf('*'));
				
				String sh=sSize.substring(sSize.indexOf('*')+1);
				
				W=Integer.parseInt(sw);
				
				H=Integer.parseInt(sh);
				
				//System.out.println("W="+W);
				
			}catch(Exception e){
				
				W = super.getWidth();
				
				H = super.getHeight();
				
			}
			
			
			
		}else{
			
			W = super.getWidth();
			
			H = super.getHeight();
			
		}
		
		
		
		
		f = Font.getFont(0, 0, ifSize);
		
		charW = f.charWidth('��');
		
		//System.out.println(charW);
		
		charEW = f.charWidth(' ');
		
		charEW =1;
		
		//System.out.println("��ĸ" + charEW);
		
		charH = f.getHeight();
			
		
		int numW=f.stringWidth("9");
		
///////////////////////////////////////����������
		
		/*showW = W -rbW - bordW-4*numW-1;//������ʾ�к�(����Ϊ4λ)�����
		 * 
*/		
		showW = W -rbW-2;//���ڲ���ʵ�кţ�����������
		
		minN = showW / charW;
		
		int tl=s.length()/minN+1; //Ԥ��������
		
		
		//System.out.println("Ԥ��������"+tl);
		
		
		////////////////////����к���ʾ���
		
		
		/*if(tl>=1000){ //4λ
			
			LW=4*numW+2;
			
		}else if(tl>=100&&tl<1000){//3λ
			
			LW=3*numW+2;
			
		}else if(tl>=10&&tl<100){//2λ
			
			LW=2*numW+2;
			
		}else if(tl>=0&&tl<10){ ////1λ
			
			LW=1*numW+2;
			
		}*/
		
		LW=2;
		
		//System.out.println("�κſ��:"+LW);
			
		///////////////////// ������ʾ���ַ�
		
		showW = W -rbW-2;//���ڲ���ʵ�кţ�����������
		
		maxN = showW / charEW;
		
		//System.out.println("���" + maxN);
		
		minN = showW / charW;
		
		//System.out.println("��С" + minN);
		
		lineN = (H)/charH;//����
		
		//System.out.println("ÿҳ������"+lineN)
		
	
		Vector lsV = new Vector();
		
		int j = 0;
		
		vPara.removeAllElements();
		
		String sa[] = dowithString(s);
		
		int n;
		
		
		
		if (sa != null){
		
			n = sa.length;
			
			saaText = new String[n][];
			
			
			
			//System.out.println("n=" + n);
			
			for (int i = 0; i < n; i++){
			
				vPara.removeAllElements();
				
				String saa[] = dowithPara(sa[i]);
				
				//System.gc();
				
				//System.out.println((System.currentTimeMillis()-lstart)+"��С"+i);
				
				if (saa != null){
				
					int nn = saa.length;
					
					saaText[i] = saa;
					
					for (int ii = 0; ii < nn; ii++){
						
						StringBuffer sb=new StringBuffer();
						
						sb.append(i).append(':').append(ii);
					
						//String sf = i + ":" + ii;
						
						//System.out.println(sf);
						
						lsV.addElement(sb.toString());
						
						j++;
					}

				} 
			}

		}
		
		
		//System.out.println("�������"+(System.currentTimeMillis()-lstart));
		
		ssf = new String[lsV.size()];
		
		lsV.copyInto(ssf);
		
		iln = j;
		
		if(ilc>iln){ //������
			
			ilc=iln;
		}
		
		//System.out.println("iln=" + iln);
		
		
		
		if(j==0){ //�����ٲ�
			
			iSn = 1;
			
		}else{
			
			iSn = (j-1)/ lineN +1;
		}
		
		if(iScreen>iSn-1){//������
			
			ilc=ilc-(iScreen-iSn+1)*lineN;
			
			iScreen=iSn-1;
			
			
		}
		
		//��Ϊֻ��ʾһҳ����Ҫʱ�����»���
		
		
		//System.out.println("iSn=" + iSn); 
		
		/*imgbuf = Image.createImage(W - rbW -bordW, iSn * H);*/
		
		imgbuf = Image.createImage(W - rbW, H);
		
		gbuf = imgbuf.getGraphics();
		
		gbuf.setFont(f);
		
		/*imgrgt = Image.createImage(rbW, iSn *H);*/
		
		imgrgt = Image.createImage(rbW, H);
		
		grgt = imgrgt.getGraphics();
		
		
		drawScreen(iScreen);
		
/*		j = 0;
		
		n = saaText.length;
		
		int ip=0;
		
		for (int i = 0; i < n; i++){
		
			int nn = saaText[i].length;
			
			for (int ii = 0; ii < nn; ii++){
				
				if(ii==0){
					
					ip=j; //�ε�����
				}
				
				pString(saaText[i][ii], j++);
				
				System.out.println(System.currentTimeMillis()-lstart);
			}
				
			////���϶κ�	
			
			if(j<=iln){  
				
				
				int h;  //=line * charH;
				
				int iSh=(ip)/lineN; //�ڼ���
				
				h=iSh*H+((ip)%lineN)* charH; //���ǻ��Ʊ߿�
				
				

				gbuf.setColor(cpBgd);
				
				gbuf.fillRect(0, h+2, LW-1,charH-2);
				
				gbuf.setColor(cpFont);
				
			
				
				gbuf.drawString((i+1)+"", 0, h, Graphics.TOP| Graphics.LEFT); //���϶κ�
				
				gbuf.setColor(cBgd);
			}
			

		}*/
		
		
		/*��Ϊֻ���Ƶ�ǰҳ*/
		
		
		//�������ʼĩ��
		
/*		int lst=iScreen*lineN;
		
		int led=iScreen*lineN+lineN-1;
		
		
		for(int li=lst;li<=led;li++){
			
			System.out.println("�����"+lst);
			
			int pp=getPL(ssf,li)[0];
			
			int ll=getPL(ssf,li)[1];
			
			System.out.println("�����");
			
			pString(saaText[pp][ll], li%lineN);
			
			
			if(ll==0){ //����
				
				
				int h=(li%lineN)* charH; //���ǻ��Ʊ߿�
				
		
				gbuf.setColor(cpBgd);
				
				gbuf.fillRect(0, h+2, LW-1,charH-2);
				
				gbuf.setColor(cpFont);
		
				
				gbuf.drawString((li+1)+"", 0, h, Graphics.TOP| Graphics.LEFT); //���϶κ�
				
				gbuf.setColor(cBgd);
				
			}
			
		}
		*/
	
		//System.out.println("�������"+(System.currentTimeMillis()-lstart));

		//System.out.println("saa=" + saaText[0][0]);
	}

	public void drawScreen(int iS){ //��Ҫ������ж�
		
		
		clearS();//��Ϊֻ��ʾһҳ����Ҫʱ�����»���
		
		pLable(sText);//��Ϊֻ��ʾһҳ����Ҫʱ�����»���
	
		
		
		int lst=iS*lineN;
		
		int ln = (iS + 1) * lineN >= iln ? iln : (iS + 1) * lineN;
		
		
		int led=ln-1;
		
		
		
		
		for(int li=lst;li<=led;li++){
			
			//System.out.println("�����"+lst);
			
			int pp=getPL(ssf,li)[0];
			
			int ll=getPL(ssf,li)[1];
			
			//System.out.println("�����");
			
			if(saaText[pp]!=null&&saaText[pp][ll]!=null&&saaText[pp][ll].length()!=0){
				
				pString(saaText[pp][ll], li%lineN);
				
				/*if(ll==0){ //����
					
					
					int h=(li%lineN)* charH; //���ǻ��Ʊ߿�
					
			
					gbuf.setColor(cpBgd);
					
					gbuf.fillRect(0, h+4, LW,charH-8);
					
					//gbuf.setColor(cpFont);
			
					
					//gbuf.drawString((pp+1)+"", 0, h, Graphics.TOP| Graphics.LEFT); //���϶κ�
					
					//gbuf.setColor(cBgd);
					
				}*/
			}
			
			
			
			
		}
		
		
	}
	
	public void pLable(String s){
	
		grgt.setColor(cBord);
		
		grgt.fillRect(0, 0, imgrgt.getWidth(), imgrgt.getHeight());
	}

	public void clearS(){
		
	
		gbuf.setColor(cBgd);
		
		
		/*gbuf.fillRect(0, 0, W, iSn * H);*/
		
		gbuf.fillRect(0, 0, W, H);
	}
	
	
	public void insertText(String s){
		
		
		int i = getPL(ssf,ilc)[0];
		
		int j = getPL(ssf,ilc)[1];
		
		
		String st = getlString(ilc);
		
		
		
		
		try{
			
			if (irc == 0){
				
				st =s+st;
				
			}else{
				
				st = st.substring(0, irc) +s+st.substring(irc);
				
			}
		
			saaText[i][j] = st;
		}
		catch (Exception exception1) { }
		
		
		long lstart=System.currentTimeMillis();
		
		//System.out.println("Ƕ�����"+(System.currentTimeMillis()-lstart));
		
		sText = saa2Text(saaText);
		
		System.out.println("ת�����"+(System.currentTimeMillis()-lstart)+"��С"+sText.length());
		
		pText(sText);
		
		//System.out.println("Ƕ�����"+(System.currentTimeMillis()-lstart));
		
		pCur(ilc, irc, false);
		
		repaint();
		
		
	}

	public void pString(String s, int line){ //δ���
	
		gbuf.setColor(cFont);
		
		
		int h=line* charH; //���ǻ��Ʊ߿�
		
		
		gbuf.drawString(s, LW, h, 20); //


		//gbuf.setColor(clFont);
		
		
		//gbuf.drawString((iScreen*lineN+line+1)+"", LW-2, h, Graphics.TOP| Graphics.RIGHT); //�����к�
		
		
	}

	public void pCur(int line, int row, boolean clear){
		
		
		StringBuffer sb=new StringBuffer();
		
		if(!doS){
			
			sb.append("�ı��༭ [ҳ").append((iScreen+1)).append("-��").append(ilc+1)
			
			.append("-��").append(irc).append("]");

			
		}else{
			
			
			sb.append("����ı� [").append(sl1).append(",").append(sr1)
			
									.append("][").append(sl2).append(",").append(sr2).append("]");			
			
		}
		
		
		this.setTitle(sb.toString());
		
		
	
		try{
	
			
			String sl = getlString(line);
			
			int cx = 0;
			
			if (row >= sl.length())
				
				cx = LW+f.stringWidth(sl);
			
			else
				cx = LW+f.stringWidth(sl.substring(0, row));
			
			int cy = line * charH;
			
			
			int iSh=(line)/lineN; //�ڼ���
			
			/*cy=iSh*H+((line)%lineN)* charH; //���ǻ��Ʊ߿�
*/			
			cy=((line)%lineN)* charH; //���ǻ��Ʊ߿�
			
			int color = clear ? cBgd - cCur : cCur;
			
			gbuf.setColor(color);
			
			gbuf.drawLine(cx, cy+2, cx, (cy + charH) - 3);
			
			gbuf.setColor(cBgd);
		}
		
		catch (Exception e) { 
			
			
			
			try{
				
				pCur(line+1, 0,clear);//���Ի�����һ��
				
			}catch(Exception ex){
				
				e.printStackTrace();
				
				pCur(line-2, 0,clear);//���Ի�����һ��
			}
			
			
			
			//ilc=irc=0;
			
		}
	}

	public void paint(Graphics g){
	
		g.setColor(cBgd);
		
		g.fillRect(0, 0, W, iScreen * H);
		
		/*g.drawImage(imgbuf,0, -iScreen * H, 20);*/
		
		g.drawImage(imgbuf,0,0, 20);
			
		//g.drawImage(imgrgt, 0, 0, 20);
		
		g.drawImage(imgrgt, W - rbW, 0, 20);
		
		//g.drawImage(imgrgt, 0, 0, 20);//���
	}

	public static String[] dowithString(String s){
	
		Vector vPara = new Vector();
		
		int ifn = 0;
		
		do{
		
			ifn = s.indexOf('\n');
			
			if (ifn > -1){
			
				vPara.addElement(s.substring(0, ifn));
				
				//System.out.println(s.substring(0, ifn));
				
				s = s.substring(ifn + 1);
				
				
				
			} else if(ifn == -1){
			
				vPara.addElement(s);
				
				//System.out.println(s);
			}
			
		} while (ifn > -1);
		
		String sa[] = new String[vPara.size()];
		
		vPara.copyInto(sa);
		
		return sa;
	}

	public static String[] dowithPara(String s){
	
		//System.out.println(s+"\n\n");
		
		if (f.stringWidth(s) <= showW ){//����ֱ����ʾ
			
			//System.out.println(s + "              ֱ��");
		
			vPara.addElement(s);
			
		} else{
		
			int im = Math.min(s.length() - 1, maxN);
			
			int n=s.length();
			
			int i;
			
			int ns=s.length();
			
			for (i = 1; i < ns; i++){
			
				String st = s.substring(0, i);
				
				String st1 = s.substring(0, i + 1);
				
				if (f.stringWidth(st) <= showW && f.stringWidth(st1) >showW){
					
					//System.out.println(st);
					
					vPara.addElement(st);
					
					break;
					
				}else{
					
					continue;
				
				}
				
			}
			

			if (i < s.length()){
			
				String sn = s.substring(i);
				
				//System.out.println(sn);
				
				dowithPara(sn);
			}
		}
		
		String sa[] = new String[vPara.size()];
		
		vPara.copyInto(sa);
		
		return sa;
	}
	
	
	public  void reFresh(){
		
		sText = saa2Text(saaText);
		
		pText(sText); //drawScreen
		
		pCur(ilc, irc, false);
		
		repaint();
	}
	
	
	public void normalCmd(boolean normal){
		
		try{
			
			
			if(!normal){ //������ģʽ
			
				this.removeCommand(cOK);
				
				this.removeCommand(cExt);
				
				this.removeCommand(cBack);
				
				this.removeCommand(cSet);
				
				this.removeCommand(cImt);
				
				this.addCommand(cC);
				
				this.addCommand(cX);
				
			}else{
				
				this.addCommand(cOK);
				
				this.addCommand(cExt);
				
				this.addCommand(cImt);
				
				this.addCommand(cBack);
				
				this.addCommand(cSet);
				
				this.removeCommand(cC);
				
				this.removeCommand(cX);
				
			}
				
				
				
			
			
			
		}catch(Exception e){}
		
		
		
		
	}
	
	
	public static void toZero(){ //���ݹ���
		
		/*
		vPara = new Vector();
		
		saaText=null;
		
		ssf=null;
		
		iln=iScreen=iSn=0;
		
		sl1=sl2=sr1=sr2=0;
		
		ilc=irc=0;*/
		
	}

	public static String getlString(int line){
		
		try{
			
			
			int[] iPL=getPL(ssf,line);
			
	
			return saaText[iPL[0]][iPL[1]];
			
			
		}catch(Exception e){
			
			return "";
			
		}
	
		
	}
	
	/*public static int getP(int line){
		
		try{
			
			
			int[] iPL=getPL(line);
			
	
			return iPL[0];
			
			
		}catch(Exception e){
			
			return -1;
			
		}
	
		
	}*/
	
	public String getSelect(int l1,int r1,int l2,int r2){
		
		if(!haveCut){//��Ч����
			
			return sC;
		}
		
		
		int lmin=Math.min(l1, l2);
		
		int lmax=Math.max(l1, l2);
		
		int rmin=0;
		
		int rmax=0;
		
		
		if(lmin==l1){ //������
			
			rmin=r1;
			
			rmax=r2;
			 
		}else{       //������
			
			rmin=r2;
			
			rmax=r1;
		}
		
		////////////////////////����������ĩ
		
		if(l1<l2&&rmin==getlString(lmin).length()){ //��һ��ĩ ,������
			
			lmin++;
			
			rmin=0;
			

			//System.out.println("��ĩ");
		
		}
		
		if(l1>l2&&rmax==0){ //���� ��������
			
			lmax--;
			
			rmax=getlString(lmax).length();
			
			//System.out.println("����");
		}
		
			
		
		
		StringBuffer sb=new StringBuffer();
		
		
		int lend=rmax;//ĩβ
		
		if(lmax>lmin){ //����һ��
			
			lend=getlString(lmin).length();
			
			sb.append(getlString(lmin).substring(rmin,lend)); //
			
			//System.out.println("������");
			
			if(getPL(ssf,lmin)[0]!=getPL(ssf,lmin+1)[0]){ //˵���Ƕ�β
				
				//System.out.println("������");
				
				sb.append("\n");
			}
			
			
			
			for(int i=lmin+1;i<lmax;i++){ //��������
				
				sb.append(getlString(i)); //
				
				if(getPL(ssf,i)[0]!=getPL(ssf,i+1)[0]){ //˵���Ƕ�β
					
					sb.append("\n");
				}
				
			}
			
			if(lmax>lmin){
				
				sb.append(getlString(lmax).substring(0,rmax));
				
				
			}
			
			
		}else{
			

			int rleft=Math.min(rmin, rmax);
			
			int rright=Math.max(rmin,rmax);
			
			sb.append(getlString(lmax).substring(rleft,rright));
			
		}
		
	
		//System.out.println(sb.toString());
		
		return sb.toString();
		
		
	}
	
	
	public void drawSelect(int l1,int r1,int l2,int r2){
		
		
		
		
		if(l1==l2&&r1==r2){ //��Ч
			
			
			haveCut=false;
			
			return;
		}
		
		////////////////////////����
		
		int lmin=Math.min(l1, l2);
		
		int lmax=Math.max(l1, l2);
		
		int rmin=0;
		
		int rmax=0;
		
		
		if(lmin==l1){ //������
			
			rmin=r1;
			
			rmax=r2;
			 
		}else{       //������
			
			rmin=r2;
			
			rmax=r1;
		}
		
		
		
		////////////////////////����������ĩ
		
		if(l1<l2&&rmin==getlString(lmin).length()){ //��һ��ĩ ,������
			
			lmin++;
			
			rmin=0;
			

			//System.out.println("��ĩ");
		
		}
		
		if(l1>l2&&rmax==0){ //���� ��������
			
			lmax--;
			
			rmax=getlString(lmax).length();
			
			//System.out.println("����");
		}
		
		
		if(lmin==lmax&&rmin==rmax){ //��Ч
			
			haveCut=false;
			
			return;
		}
		
		haveCut = true;
		

		if(bNoSelect){ //����Ҫ���»���
			
			
			
			return;
			
		}
		
		
		//reFresh(); //�ػ�
	
		////////////////////////����
		
		gbuf.setColor(cSelect);
		
		
		//		����ǰ����
		
/*		int h;  //=line * charH;
		
		int iSh=(lmin)/lineN; //�ڼ���
		
		h=iSh*H+((lmin)%lineN)* charH; //���ǻ��Ʊ߿�
		
		
*/		
		int h=((lmin)%lineN)* charH; //���ǻ��Ʊ߿�
		
		int lend=rmax;
		
		if(lmax>lmin){ //����һ��
			
			/////////////////////////////////ֻ�������һ��Ļ
			
			int iSmin=(l1)/lineN; //�ڼ���
			
			int iSmax=(l2)/lineN; //�ڼ���
			
			if(iSmax>iSmin){ //ǰ�����
				
				System.out.println("����Ļ����");
				
				lmin=iSmax*lineN;
				
				rmin=0;//��Ȼ������
				
				if(lmin==l2) { //��ͷ��ʼһ��
					

					int cx=LW+0;
					
					h=((lmin)%lineN)* charH; //���ǻ��Ʊ߿�
					
					gbuf.drawString(getlString(lmin).substring(0,r2), cx, h, 20); //
					
					return;
					
				}
				
			}
			
			if(iSmax<iSmin){ //��ǰ����
				
				System.out.println("����Ļ����");
				
				lmax=(iSmax+1)*lineN-1;//��Ļĩβ
				
				rmax=getlString(lmax).length();//��Ȼ��ĩ��
				
				if(lmax==l1) { //��β��ʼһ��
					

					int cx=LW+f.stringWidth(getlString(lmin));
					
					h=((lmax)%lineN)* charH; //���ǻ��Ʊ߿�
					
					gbuf.drawString(getlString(lmax).substring(r1,rmax), cx, h, 20); //
					
					return;
					
				}
				
			}
			
			
			
			h=((lmin)%lineN)* charH; //���ǻ��Ʊ߿�
			
			lend=rmax;
			
			
			///////////////////////////////
			
			lend=getlString(lmin).length();
			
			//System.out.println("������"+rmin);
			
			int cx=LW+f.stringWidth(getlString(lmin).substring(0, rmin));
			
			gbuf.drawString(getlString(lmin).substring(rmin,lend), cx, h, 20); //
			
			for(int i=lmin+1;i<lmax;i++){ //��������
				
				
				h=((i)%lineN)* charH; //���ǻ��Ʊ߿�
				
				gbuf.setColor(cCur);
				
				gbuf.drawString(getlString(i), LW, h, 20); //
				
				
			}
			
			

			//		���ƺ����
			
		
			/*iSh=(lmax)/lineN; //�ڼ���
			*/
			h=((lmax)%lineN)* charH; //���ǻ��Ʊ߿�
			
			cx=LW;
			
			
			gbuf.drawString(getlString(lmax).substring(0,rmax), cx, h, 20); //
				
			
			
			
		}else{ //һ��
			
			
			int rleft=Math.min(rmin, rmax);
			
			int rright=Math.max(rmin,rmax);
			
			int cx=LW+f.stringWidth(getlString(lmin).substring(0, rleft));
			
			gbuf.drawString(getlString(lmin).substring(rleft,rright), cx, h, 20); //
			
		}
		
		
		
	
		//repaint();
		
		
		
	}
	
	
	
	
	public String replaceString(String s,String ssrc,String sdes){
		
		
		if(ssrc.equals(sdes)){ //�����滻
			
			return s;
		}
		
		
		/////////////////////// �����滻
		
		int isf=-1;
		
		String stf=s;
		
		StringBuffer sb=new StringBuffer();
		
		do{  ///һ�ٸɾ�
			
			
			isf=stf.indexOf(ssrc);
			
			if(isf!=-1){ //�ҵ�  ��Ҫ����ѭ���滻��ѭ��
				
				//System.out.println("�ҵ�"); 
				
				sb.append(stf.substring(0,isf)+sdes);
				
				stf=stf.substring(isf+ssrc.length());
				
				
				
			}else{
				
				sb.append(stf);
			}
			
			
		}while(isf>-1);
		
		
		return sb.toString();
	}
	
	
	public int getL(int p, int l){
		
		int sfn=ssf.length;
		
		for(int k=0;k<sfn;k++){
			
			if(ssf[k].equals(p+":"+l)){ //��������ĳ��
				
				return k;
			}
			
			
		}
		
		return -1;
	}
	
	
 private char[] cHex=new char[]{'0','1','2','3','4','5','6','7','8','9',
			
			
			'A','B','C','D','E','F'};
	
	public int char2int(char ch){
		
		int ic=0;
		
		int d='A'-'a';
		
		for(int i=0;i<16;i++){
			
			if(ch==cHex[i]||ch==cHex[i]-d){
				
				ic=i;
				
				break;
			}
			
		}
		
		//System.out.println("ic="+(ic<<(5*4)));
		
		return ic;
		
	}
	
	
	
	public int HS2int(String sh){ // FFFFFF
		
		int icolor=0x000000;
		
		try{
			
			
			icolor=(char2int(sh.charAt(0))<<5*4)|(char2int(sh.charAt(1))<<4*4)|
			
					(char2int(sh.charAt(2))<<3*4)|(char2int(sh.charAt(3))<<2*4)|
					
					(char2int(sh.charAt(4))<<1*4)|(char2int(sh.charAt(5)));
			
			
			return icolor;
			
		}catch(Exception e){
			
			//System.out.println("����");
			
			return 0x000000;
			
		}
		
	
		
	}
	
	public String int2HS(int color){ // FFFFFF
		
		StringBuffer sb=new StringBuffer();
		
		try{
			
			for(int i=0;i<6;i++){
				
				int ic=((color&(15<<(5-i)*4))>>((5-i)*4));
				
				//int ic=(color&(0xffffff));
				
				//System.out.println(ic);
				
				sb.append(cHex[ic]);
				
			}
			
			
			return sb.toString();
			
		}catch(Exception e){
			
			
			return "000000";
		}
		
		
		
	}
	
	
	public static int[] getPL(String[] ssf,int line){
		
		try{
			
			String si = ssf[line].substring(0, ssf[line].indexOf(':'));
			
			String sj = ssf[line].substring(ssf[line].indexOf(':') + 1);
			
			int i = Integer.parseInt(si);
			
			int j = Integer.parseInt(sj);
			
			return new int[]{i,j};
			
			
		}catch(Exception e){
			
			return null;
			
		}
	
		
	}
	
	

	protected void keyPressed(int key){
	
		int l1 = 0;
		
		int l2 = 0;
		
		int r2 = 0;
		
		String sl = getlString(ilc);
		
		//System.out.println("��ǰ��"+ilc);
		
		switch (key){
		
		case Canvas.KEY_NUM1: // '1' //����
			
			/*if(doS){ //��ǽ���
				
				sr2=0;
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				return;
			}*/
			
			pCur(ilc, irc, true);
			
			irc=0;
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
			
		case Canvas.KEY_NUM3: // '3' //��ĩ
			
			/*
			if(doS){ //��ǽ���
				
				sr2=irc=getlString(ilc).length();
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				return;
			}*/
			
			pCur(ilc, irc, true);
			
			irc=getlString(ilc).length();
			
			pCur(ilc, irc, false);
			
			
			repaint();
			
			return;
			
			
		case Canvas.KEY_NUM9: // '9' //��ĩ
			
			
			/*if(doS){ //��ǽ���
				
				iScreen=iSn-1;
				
				sl2=Math.min(iln-1, ((iScreen+1)*lineN-1));
				
				sr2=getlString(ilc).length();
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				return;
			}
			*/
			
			
			headORend=(headORend+1)%2; //��һ��Ϊ1
			
			
			if(headORend==1){ //��ĩ
				
				iScreen=iSn-1;
				
				ilc=Math.min(iln-1, ((iScreen+1)*lineN-1));
				
				irc=getlString(ilc).length();
				
			}else{ //����
				
				iScreen=0;
				
				ilc=0;
				
				irc=0;
				
			}
			
			
			
			
			
			pCur(ilc, irc, true);
			
		
			drawScreen(iScreen);
			
			pCur(ilc, irc, false);
					
			repaint();
			
			
			
			
			return;
			
			
/*		case Canvas.KEY_STAR: // '*' //������ʾ 
			
	
			
			return;*/
			
		
		case Canvas.KEY_NUM0: // '0' //��������
			
			//this.setFullScreenMode(bFull=!bFull);
			
			
			if(doS){ //��ǽ��� �ض����
				
				//System.out.println("ddd");
				
			
				/*int oc=cCur;
					
				cCur=cSelect;//
					
				pCur(sl1,sr1,false);//�������
					
				cCur=oc;
				*/
				
				drawScreen(iScreen);//�ػ�
				
				sl1=sl2 = ilc;
				
				sr1 =sr2= irc;
				
				pCur(sl1,sr1,false);//�������
				
				repaint();
			
				
				
				return;
				
			}
			
			reFresh();
			
			return;
			
			
		case Canvas.KEY_POUND: // '#' //�л�״̬
			
			
			doS =!doS; //�л�
			
			reFresh();
			
			
			haveCut=false;
			
			if(doS){
				

				sl1=sl2 = ilc;
				
				sr1 =sr2= irc;
				
				setTitle("���ģʽ[�ı�������:"+sText.length()+"]");
				
			}
			
			
			normalCmd(!doS);
			
			
			
			return;
			
		case Canvas.KEY_NUM7: // '7' //��ת
			
			
			Form fj=new Form("��ת");
			
			fj.append(new TextField("","1",1024,TextField.NUMERIC));
			
			String[] s=new String[]{"�� ҳ","�� ��","�� ��"};
					
			fj.append(new ChoiceGroup("",1,s,null));	
			
			fj.addCommand(new Command("��ת",1,40));
			
			fj.addCommand(new Command("ȡ��",2,2));
			
			fj.setCommandListener(this);
			
			d.setCurrent(fj);
			
			return;
			
		case Canvas.KEY_NUM2: // '2' //ɾ��
			
			Form fd=new Form("ɾ��");
			
			String[] sd=new String[]{"��ǰ��","��ǰ��","����ı�"};
					
			fd.append(new ChoiceGroup("",1,sd,null));	
			
			fd.addCommand(new Command("ɾ��",1,41));
			
			fd.addCommand(new Command("ȡ��",2,2));
			
			fd.setCommandListener(this);
			
			d.setCurrent(fd);
			
			return;
			
		case Canvas.KEY_NUM8: // '8' //����
			
			if(doS){ //ճ��
				
				insertText(sC);
				
				d.setCurrent(this);
				
				return;
				
			}
					
			TextBox ti=new TextBox("����","",88888,0);
			
			//TextBox ti=new TextBox("����",sC,88888,0);
			
			ti.addCommand(new Command("�س�",1,42));
			
			ti.addCommand(new Command("ճ��",1,43));
			
			ti.addCommand(new Command("����",2,44));
			
			ti.addCommand(new Command("ȡ��",2,222));
			
			ti.setCommandListener(this);
			
			d.setCurrent(ti);
			
			return;
			
		case Canvas.KEY_NUM6: // '6' �ұ߷�ҳ
			
			iScreen++;
			
			if (iScreen > iSn - 1){
			
				iScreen = iSn - 1;
				
				return;
			}
			
			pCur(ilc, irc, true);
			
			ilc = iScreen * lineN;
			
			irc = 0;
			
			
			drawScreen(iScreen);
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;

		case Canvas.KEY_NUM4: // '4' ��ҳ
			
			iScreen--;
			
			if (iScreen < 0){
				
				iScreen = 0;
				
				return;
			}
			
			pCur(ilc, irc, true);
			
			ilc = (iScreen+1) * lineN-1;
			
			irc = getlString(ilc).length();
			
			drawScreen(iScreen);
			
			pCur(ilc, irc, false);
				
			repaint();
			
			return;
			
		case Canvas.KEY_NUM5: // '5'
			
			if(fFR==null){
				
				fFR=new Form("�����滻");
				
				fFR.append(new ChoiceGroup("",1,new String[]{"����","�滻һ��","�滻ȫ��"},null));
				
				fFR.append(new TextField("����","����",88888,0));
				
				fFR.append(new TextField("�滻","���",88888,0));
				
				fFR.addCommand(new Command("ִ��",1,50));
				
				fFR.addCommand(new Command("����",2,2));
				
				fFR.setCommandListener(this);
				
			}
		
			d.setCurrent(fFR);
			
			return;
			
		}
		
		
		if(key==ikDel){ // 'C' //ɾ��
			
			commandAction(cBack,this);
			
			return;
			
		}
		
		//���⵼��������
		
		
		
		int gakey = getGameAction(key);
		
		if (gakey == Canvas.UP || gakey ==  Canvas.DOWN || gakey ==  Canvas.LEFT || gakey ==  Canvas.RIGHT ){
		
			pCur(ilc, irc, true);
			
			l1 = iScreen * lineN;
			
			l2 = (iScreen + 1) * lineN >= iln ? iln : (iScreen + 1) * lineN;
			
			r2 = sl.length();
			
			switch (gakey){
	
			case Canvas.UP: // '\001' ��
				
				if (ilc > l1){
					
					ilc--;
					
				}else{
					
					//ilc = l2 - 1;�ػ�
					
					if(ilc!=0){ //��ҳ
						
						ilc--;
						
						iScreen--;
						
						drawScreen(iScreen);
						
					}else{
						
						ilc = l2 - 1;
					}
				
					
				}
					
				if(getlString(ilc).length()<irc){ //������
					
					irc=getlString(ilc).length();
				}
			
			
				
				break;

			case Canvas.DOWN : // '\006'
				
				if (ilc < l2-1){
					
					ilc++;
					
				}else{
					
					// ilc = l1;�ػ�
					
					
					if(iScreen!=iSn-1){ //�ҷ�ҳ
						
						ilc++;
						
						iScreen++;
						
						drawScreen(iScreen);
						
					}else{
						
						ilc = l1;
					}
				
				}
					
				if(getlString(ilc).length()<irc){ //������
					
					irc=getlString(ilc).length();
				}
				
				break;

			case  Canvas.LEFT: // '\002'
				
				if (irc > 0){ 
				
					irc--;
					
					break;
				}
				
				/////��������
				
				ilc--;
				
				if (ilc == l1 - 1){
					
					ilc = lineN - 1;
				}
					
				irc=getlString(ilc).length(); //��һ��ĩβ
				
				
				if (ilc == lineN - 1){ //���ף���ҳ
					
					keyPressed(Canvas.KEY_NUM4);//��ҳ
					
					return;
				}
			
				break;

			case Canvas.RIGHT: // '\005'
				
				if (irc < r2){
				
					irc++;
					
					break;
					
				}
			
				//////������ĩ
				
				irc = 0;
				
				ilc++;
				
				if (ilc == l2){ //ҳĩ����ҳ
					
					keyPressed(Canvas.KEY_NUM6);//�ҷ�ҳ
					
					return;
				}
			
				
				break;
			}
			
			//drawScreen(iScreen);
			
			pCur(ilc, irc, false);
			
			repaint();
			
			
			if(doS){ //��ǽ���
				
				sl2=ilc;
				
				sr2=irc;
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				if(doS){//���ģʽ
					
					int oc=cCur;
					
					cCur=cSelect;//���ɫ
					
					
					int iSmin=(sl1)/lineN; //�ڼ���
					
					int iSmax=(sl2)/lineN; //�ڼ���
					
					if(iSmin==iSmax){ //ͬһ��Ļ�Ż���
						
						pCur(sl1,sr1,false);//�������
					}
					
					
					
					cCur=oc;
				}
				
			
			}
				
			
			return;
		}
		
		/*if (gakey == Canvas.FIRE){//�м�
		
			setTitle("����ı�");
			
			doS = true;
			
			sl1 = ilc;
			
			sr1 = irc;
			
			return;
		}*/
		
	}

	protected void keyRepeated(int key){ //����
	
	
		switch (key){
		
		case Canvas.KEY_NUM1: // '1' //ҳ��
			
			pCur(ilc, irc, true);
			
			ilc=iScreen*lineN;
			
			irc=0;
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
	
		case Canvas.KEY_NUM3: // '3' //ҳĩ
			
			pCur(ilc, irc, true);
			
			ilc=Math.min(iln-1, ((iScreen+1)*lineN-1));
			
			irc=getlString(ilc).length();
			
			pCur(ilc, irc, false);
			
			
			repaint();
			
			return;
		
			
		case Canvas.KEY_NUM0:// 0�� //����
		
		
			sText = saa2Text(saaText);
			
			if(sText.endsWith("\n")){
				
				sText=sText.substring(0, sText.length()-1);
			}
			
			try{
			
				if (sType.equals("Fielk"))
					
					((TextFielk)sOb).setString(sText);
				
				else
				if (sType.equals("Bok"))
					
					((TextBok)sOb).setString(sText);
				
				else
				if (sType.equals("Field"))
					
					((TextField)sOb).setString(sText);
				
				else
				if (sType.equals("Box"))
					
					((TextBox)sOb).setString(sText);
			}
			catch (Exception exception) { }
			
			toZero();
			
			ilc=irc=0;
			
			d.setCurrent(dp);
			
			
		case Canvas.KEY_NUM6: // '6'
			
			
			iScreen = iSn - 1;
			
			
			drawScreen(iScreen);
			
			pCur(ilc, irc, true);
			
			ilc = iScreen * lineN;
			
			irc = 0;
			
			pCur(ilc , irc, false);
			
			repaint();
			
			return;

		case Canvas.KEY_NUM4: // '4'
						
			iScreen = 0;
			
			drawScreen(iScreen);
			
			pCur(ilc, irc, true);
			
			ilc = iScreen * lineN;
			
			irc = 0;
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
			
			
/*		case Canvas.KEY_NUM8: // '8' //ճ��
			
			insertText(sC);
			
			d.setCurrent(this);*/
		}
		
		
		if(key==ikDel){ //����ɾ������
			
			
			keyPressed(key);
			
		}
		
		
		//���⵼��������
		
		int gakey = getGameAction(key);
		
		if (gakey == Canvas.UP || gakey ==  Canvas.DOWN || gakey ==  Canvas.LEFT || gakey ==  Canvas.RIGHT ){
		
				
			keyPressed(key);
			
			return;
		}
		
	
		
	}	

	public static String saa2Text(String saa[][]){
	
		StringBuffer sb = new StringBuffer();
		
		int n = saa.length;
		
		//System.out.println("�ַ�����С��"+n);
		
		for (int i = 0; i < n; i++){
			
			
			if(saa[i]==null){ //��Ч�Թ�
				
				//System.out.println("����Ϊ���Թ�");
				
				continue;
			}
		
			int nn = saa[i].length;
			
			for (int j = 0; j < nn; j++){
				
				
				if(saa[i][j]==null){ //��Ч�Թ�
					
					//System.out.println("����Ϊ���Թ�");
					
					continue;
				}
				
				sb.append(saa[i][j]);
				
			}

			if (i < n-1){
				
				
				sb.append("\n");
				
				
				//System.out.println("i="+i);
			}
				
				
			
		}
		
		//System.out.println("���"+sb.toString().endsWith("\n"));

		return sb.toString();
		
		
	}
	
	
	public static void rmsDo(boolean write){ ///rms����
		
		try{
			
			String sp="";//���ַ���
			
			if(write){ ////д
				
				
				RecordStore rms = RecordStore.openRecordStore("kTextor", true, RecordStore.AUTHMODE_ANY, true); 
			
				//System.out.println(rms);
				
				
				if(!haveRMS){
					
					
					//System.out.println("haveRMS"+haveRMS);
					
					rms.addRecord((brS+sp).getBytes("utf-8"), 0, (brS+sp).getBytes("utf-8").length);//1
					
					rms.addRecord(sFile.getBytes("utf-8"), 0, sFile.getBytes("utf-8").length);//2
					
					System.out.println("����"+brS);
					
					
					rms.addRecord((cFont+sp).getBytes("utf-8"), 0, (cFont+sp).getBytes("utf-8").length);//3
					
					rms.addRecord((cBgd+sp).getBytes("utf-8"), 0, (cBgd+sp).getBytes("utf-8").length);//4
					
					rms.addRecord((clFont+sp).getBytes("utf-8"), 0, (clFont+sp).getBytes("utf-8").length);//5
					
					rms.addRecord((cpFont+sp).getBytes("utf-8"), 0, (cpFont+sp).getBytes("utf-8").length);//6
					
					rms.addRecord((cpBgd+sp).getBytes("utf-8"), 0, (cpBgd+sp).getBytes("utf-8").length);//7
					
					rms.addRecord((cCur+sp).getBytes("utf-8"), 0, (cCur+sp).getBytes().length);//8
					
					rms.addRecord((cFind+sp).getBytes("utf-8"), 0, (cFind+sp).getBytes("utf-8").length);//9
					
					rms.addRecord((cSelect+sp).getBytes("utf-8"), 0, (cSelect+sp).getBytes("utf-8").length);//10
					
					rms.addRecord((cBord+sp).getBytes("utf-8"), 0, (cBord+sp).getBytes("utf-8").length);//11
					
					rms.addRecord((ikDel+sp).getBytes("utf-8"), 0, (ikDel+sp).getBytes("utf-8").length);//12
					
					rms.addRecord((ifSize+sp).getBytes("utf-8"), 0, (ifSize+sp).getBytes("utf-8").length);//13
					
					rms.addRecord((bNoSelect+sp).getBytes(), 0, (bNoSelect+sp).getBytes("utf-8").length);//14
					
					rms.addRecord((bFMod+sp).getBytes("utf-8"), 0, (bFMod+sp).getBytes("utf-8").length);//15
					
					rms.addRecord(sSize.getBytes("utf-8"), 0, sSize.getBytes("utf-8").length);//16
					
					
					System.out.println("sSize="+sSize);
				
				}else{
					
					rms.setRecord(1,(brS+sp).getBytes("utf-8"), 0, (brS+sp).getBytes("utf-8").length);
					
					rms.setRecord(2,sFile.getBytes("utf-8"), 0, sFile.getBytes("utf-8").length);
					
					//System.out.println("����"+sFile);
					
					
					rms.setRecord(3,(cFont+sp).getBytes("utf-8"), 0, (cFont+sp).getBytes("utf-8").length);
					
					rms.setRecord(4,(cBgd+sp).getBytes("utf-8"), 0, (cBgd+sp).getBytes("utf-8").length);
					
					rms.setRecord(5,(clFont+sp).getBytes("utf-8"), 0, (clFont+sp).getBytes("utf-8").length);
					
					rms.setRecord(6,(cpFont+sp).getBytes("utf-8"), 0, (cpFont+sp).getBytes("utf-8").length);
					
					rms.setRecord(7,(cpBgd+sp).getBytes("utf-8"), 0, (cpBgd+sp).getBytes("utf-8").length);
					
					rms.setRecord(8,(cCur+sp).getBytes("utf-8"), 0, (cCur+sp).getBytes("utf-8").length);
					
					rms.setRecord(9,(cFind+sp).getBytes("utf-8"), 0, (cFind+sp).getBytes("utf-8").length);
					
					rms.setRecord(10,(cSelect+sp).getBytes("utf-8"), 0, (cSelect+sp).getBytes("utf-8").length);
					
					rms.setRecord(11,(cBord+sp).getBytes("utf-8"), 0, (cBord+sp).getBytes("utf-8").length);
					
					rms.setRecord(12,(ikDel+sp).getBytes("utf-8"), 0, (ikDel+sp).getBytes("utf-8").length);
					
					rms.setRecord(13,(ifSize+sp).getBytes("utf-8"), 0, (ifSize+sp).getBytes("utf-8").length);
					
					rms.setRecord(14,(bNoSelect+sp).getBytes("utf-8"), 0, (bNoSelect+sp).getBytes("utf-8").length);
					
					rms.setRecord(15,(bFMod+sp).getBytes("utf-8"), 0, (bFMod+sp).getBytes("utf-8").length);
					
					rms.setRecord(16,sSize.getBytes("utf-8"), 0, sSize.getBytes("utf-8").length);
				}
				
								
				haveRMS=true;
				
				rms.closeRecordStore();  
				
			}else{ 
				
				RecordStore rms = RecordStore.openRecordStore("kTextor",true); 
				
				brS=(new String(rms.getRecord(1),0,rms.getRecord(1).length,"utf-8")).equalsIgnoreCase("true");
				
				sFile=(new String(rms.getRecord(2),0,rms.getRecord(2).length,"utf-8"));
				
				
				cFont=Integer.parseInt(new String(rms.getRecord(3),0,rms.getRecord(3).length,"utf-8"));
				
				cBgd=Integer.parseInt(new String(rms.getRecord(4),0,rms.getRecord(4).length,"utf-8"));
				
				clFont=Integer.parseInt(new String(rms.getRecord(5),0,rms.getRecord(5).length,"utf-8"));
				
				cpFont=Integer.parseInt(new String(rms.getRecord(6),0,rms.getRecord(6).length,"utf-8"));
				
				cpBgd=Integer.parseInt(new String(rms.getRecord(7),0,rms.getRecord(7).length,"utf-8"));
				
				cCur=Integer.parseInt(new String(rms.getRecord(8),0,rms.getRecord(8).length,"utf-8"));
				
				cFind=Integer.parseInt(new String(rms.getRecord(9),0,rms.getRecord(9).length,"utf-8"));
				
				cSelect=Integer.parseInt(new String(rms.getRecord(10),0,rms.getRecord(10).length,"utf-8"));
				
				cBord=Integer.parseInt(new String(rms.getRecord(11),0,rms.getRecord(11).length,"utf-8"));
				
				ikDel=Integer.parseInt(new String(rms.getRecord(12),0,rms.getRecord(12).length,"utf-8"));
				
				ifSize=Integer.parseInt(new String(rms.getRecord(13),0,rms.getRecord(13).length,"utf-8"));
				
				bNoSelect=(new String(rms.getRecord(14),0,rms.getRecord(14).length,"utf-8")).equalsIgnoreCase("true");
				
				bFMod=(new String(rms.getRecord(15),0,rms.getRecord(15).length,"utf-8")).equalsIgnoreCase("true");
				
				sSize=(new String(rms.getRecord(16),0,rms.getRecord(16).length,"utf-8"));
				
				
				//System.out.println("sSize="+sSize);
				
				System.out.println("bFMod="+new String(rms.getRecord(15),0,rms.getRecord(15).length,"utf-8"));
				
				rms.closeRecordStore(); 
		
				
			}
			
	
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			System.out.println("ò�����ö�ȡʧ�ܣ�");
			
			haveRMS=false;
			
		
		}
		
	
		
		
	}
	
	

	public void commandAction(Command c, Displayable dis){
		
		if (c.getLabel().equals("ճ��")){
			
			insertText(sC);
			
			d.setCurrent(this);
			
			return;
			
		}
		
		
		if (c.getLabel().equals("ȫ��ģʽ")){
			
			this.setFullScreenMode(true);
			
			pText(sText);
			
			//pText(sText); //drawScreen
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
			
		}
		
		if (c.getLabel().equals("ϵͳ����")){
			
			
			if(fSet==null){
				
				
				fSet=new Form("ϵͳ����");
				
				fSet.append(new ChoiceGroup("",2,new String[]{"�Ŀ��й�"},null));
				
				
				((ChoiceGroup)fSet.get(0)).setSelectedIndex(0, brS);//ǿ���й�
				
				
				fSet.append(new ChoiceGroup("",2,new String[]{"��ɫ���"},null));
				
				
				((ChoiceGroup)fSet.get(1)).setSelectedIndex(0, !bNoSelect);//��ɫ���
				
				fSet.append(new ChoiceGroup("",2,new String[]{"ȫ��ģʽ"},null));
				
				
				((ChoiceGroup)fSet.get(2)).setSelectedIndex(0, bFMod);//ȫ������
				
				
				fSet.append(new TextField("",sSize,1024,0));
				
				
				fSet.append(new TextField("���뵼��·��",sFile,1024,0));
				
				fSet.append(new TextField("ɾ����ֵ",ikDel+"",6,0));
				

				fSet.append(new ChoiceGroup("�����С",4,new String[]{"С","��","��"},null));
				
				int fsize=0;
				
				switch(ifSize){
				
				case 0:  //��
					
					fsize=1;
					
					break;
					
				case 8: //С
					
					fsize=0;
					
					break;
					
				case 16: //��
					
					fsize=2;
					
					break;
				
				
				}
							
				
				((ChoiceGroup)fSet.get(6)).setSelectedIndex(fsize, true);//ǿ���й�
				
				//fSet.append("��ʾ��ɫ�趨");
				
				fSet.append(new TextField("��ʾ��ɫ�趨\n����",int2HS(cFont),6,0));
				
				fSet.append(new TextField("����",int2HS(cBgd),6,0));			
				
				//fSet.append(new TextField("�к�",int2HS(clFont),6,0));
				
				//fSet.append(new TextField("�κ�",int2HS(cpFont),6,0));
				
				//fSet.append(new TextField("�ε�",int2HS(cpBgd),6,0));
				
				fSet.append(new TextField("���",int2HS(cCur),6,0));
				
				//fSet.append(new TextField("����",int2HS(cFind),6,0));
				
				fSet.append(new TextField("ѡ��",int2HS(cSelect),6,0));
								
				fSet.append(new TextField("�߿�",int2HS(cBord),6,0));
				
				
				
				
				fSet.addCommand(new Command("����",2,52));
				
				fSet.addCommand(new Command("Ĭ��",1,53));
				
				fSet.addCommand(new Command("ȡ��",1,54));
				
				fSet.setCommandListener(this);
			}
			
			d.setCurrent(fSet);
			
			return;
		}
		
		
		if (c.getLabel().equals("����")){
			
			brS=((ChoiceGroup)((Form)dis).get(0)).isSelected(0);//ǿ���й�
			
			bNoSelect=!((ChoiceGroup)((Form)dis).get(1)).isSelected(0);//��ɫ���
			
			
			boolean bfold=bFMod;
			
			bFMod=((ChoiceGroup)((Form)dis).get(2)).isSelected(0);//ȫ������
			
			
			
			sSize=((TextField)((Form)dis).get(3)).getString();
			
			sFile=((TextField)((Form)dis).get(4)).getString();
			
			try{
				
				int k=Integer.parseInt(((TextField)((Form)dis).get(5)).getString());
				
				ikDel=k; //ɾ����ֵ
				
				
			}catch(Exception e){}
			
			
			
			//�����С
			
			int isize=((ChoiceGroup)((Form)dis).get(6)).getSelectedIndex();
			
			if(isize==0){
				
				isize=8; //С
				
			}else if(isize==1){
				
				isize=0;//��
				
			}else{
				
				isize=16;//��
			}
			
			
			//��ɫ��ȡ
			
			cFont=HS2int(((TextField)((Form)dis).get(7)).getString());
			
			cBgd=HS2int(((TextField)((Form)dis).get(8)).getString());
			
			//clFont=HS2int(((TextField)((Form)dis).get(4)).getString());
			
			//cpFont=HS2int(((TextField)((Form)dis).get(5)).getString());
			
			//cpBgd=HS2int(((TextField)((Form)dis).get(6)).getString());
			
			cCur=HS2int(((TextField)((Form)dis).get(9)).getString());
			
			//cFind=HS2int(((TextField)((Form)dis).get(8)).getString());
			
			cSelect=HS2int(((TextField)((Form)dis).get(10)).getString());
			
			cBord=HS2int(((TextField)((Form)dis).get(11)).getString());
			
		
			
			if(isize!=ifSize){
				
				ifSize=isize;
				
				
				if(bfold!=bFMod){//����Ļ�仯
					
					this.setFullScreenMode(bFMod);
									
				}	
				
				
				pText(sText); //��������
				
				pCur(ilc, irc, false);
				
				repaint();
				
			}else{
				
				

				if(bfold!=bFMod){//����Ļ�仯
					
					this.setFullScreenMode(bFMod);
					
					pText(sText); //��������
					
					pCur(ilc, irc, false);
					
					repaint();
					
									
				}else{
					
					
					drawScreen(iScreen);//pText(sText);//�ػ�
					
				}
				
				
				
			}
				
		
			
			rmsDo(true);//��������
			
			
			d.setCurrent(this);
			
			
			
		}
		
		if (c.getLabel().equals("Ĭ��")){
			
			((TextField)((Form)dis).get(7)).setString("0000FF");
			
			((TextField)((Form)dis).get(8)).setString("BBFFFF");
			
			//((TextField)((Form)dis).get(4)).setString("FF00FF");
			
			//((TextField)((Form)dis).get(5)).setString("00FFFF");
			
			//((TextField)((Form)dis).get(6)).setString("FF00BB");
			
			((TextField)((Form)dis).get(9)).setString("FF0000");
			
			//((TextField)((Form)dis).get(8)).setString("FF00DD");
			
			((TextField)((Form)dis).get(10)).setString("FF0000");
			
			((TextField)((Form)dis).get(11)).setString("BBBBBB");
			
		}
		
		if (c.getLabel().equals("����")){
			
			sC=getSelect(sl1,sr1,sl2,sr2);
			
			int ic=sText.indexOf(sC);
			
			if(ic>-1){			
				
				//System.out.println("����");
				
				sText=sText.substring(0,ic)+sText.substring(ic+sC.length());
			}
			
			pText(sText);
			
			drawScreen(iScreen);
			
			pCur(ilc, irc, false);
			
			repaint();
			
			if(sl1>sl2){ //���ñ�����
				
				ilc=sl1=sl2;
				
				irc=sr1=sr2;
				
			}else{
				

				ilc=sl2=sl1;
				
				irc=sr2=sr1;
			}
			
			return;
			
		}
		
		
		
		if (c.getLabel().equals("����")){
			
			
			sC=getSelect(sl1,sr1,sl2,sr2);
			
			sl1=sl2 = ilc; //���ñ�����
			
			sr1 =sr2= irc;
			
			return;
			
		}
	
		if (c.getLabel().equals("�˸�")){
		
	
			if(ilc==0&&irc==0){
				
				return;
			}
		
			
			int i = 0;
			
			int j = 0;
			
			int ld=0;
			
			if (irc == 0){ //ɾ����һ��
				
				
				i = getPL(ssf,ilc)[0];
				
				j = getPL(ssf,ilc)[1];
				
				
				if(saaText[i][j].length()==0){
					
					saaText[i][j]=null; //����Ϊ��ɾ��
					
					if(saaText[i]!=null){
						
						boolean noPara=true;
						
						for(int li=saaText[i].length-1;li>=0;li--){
							
							if(saaText[i][li]!=null){
								
								noPara=false;
							}
						}
						
						if(noPara){
							
							saaText[i]=null;
						}
					}
					
				}	
					
				String st = null;
					
				try{
					
					
					ld=ilc-1;
					
					i = getPL(ssf,ld)[0];
					
					j = getPL(ssf,ld)[1];
					
					st = getlString(ld);	
					
					irc=st.length();
					
					
				}catch(Exception e){
					
					
					
				}
				
				
				if((ilc)%lineN==0){ //��һҳ
					
					iScreen--;
					
					ilc--;
					
					drawScreen(iScreen);
					
					pCur(ilc, irc, false);
					
				}else{ //��һ��
					
					try{  //ɾ��
						
						/*if(st.length()>0){
							
							st = st.substring(0, st.length()-1);
							
							saaText[i][j] = st;
							
						}else{
							
							saaText[i]=null;
						}
							*/
						/*if(saaText.length==i+1||saaText[i+1]==null){
							
							//System.out.println("oh");
						
							//ɶҲ����
						}
						
						else if(saaText[i+1].length==1&&getlString(ilc).length()==0){ //ɾ����
							
							
							//System.out.println("ɾ����"+(i+1));
							
							//System.out.println("��"+saaText[i+2].length);
							
							saaText[i+1]=null;
						}
						*/
						
						
						drawScreen(iScreen);
						
						/*sText=saa2Text(saaText);
						
						pText(sText);*/
						
					}
					catch (Exception e) { 
						
						e.printStackTrace();
					}
										
					ilc--;
				}
				
				
				
			}else{ //������
				
				ld=ilc;
				
				i = getPL(ssf,ld)[0];
				
				j = getPL(ssf,ld)[1];
				
				String st = getlString(ld);
				
				try{
					
					st = st.substring(0, irc - 1) + st.substring(irc);
					
					saaText[i][j] = st;
				}
				catch (Exception exception1) { }
				
				irc--;	
				
				
				
				drawScreen(iScreen);
				
			}
			
			//System.out.println(sText+"\n\n");
			
			//System.out.println(saa2Text(saaText));
		
			
			/*reFresh();//����
			 * 
			 * 
*/			
			
			
			pCur(ilc, irc, false);
			
			repaint();
			
		}
		
		if (c.getLabel().equals("ȷ�Ϸ���")){
		
			sText = saa2Text(saaText);
			
			if(sText.endsWith("\n")){
				
				sText=sText.substring(0, sText.length()-1);
			}
			
			try{
			
				if (sType.equals("Fielk"))
					
					((TextFielk)sOb).setString(sText);
				
				else
				if (sType.equals("Bok"))
					
					((TextBok)sOb).setString(sText);
				
				else
				if (sType.equals("Field"))
					
					((TextField)sOb).setString(sText);
				
				else
				if (sType.equals("Box"))
					
					((TextBox)sOb).setString(sText);
			}
			catch (Exception exception) { }
			
			toZero();
			
			ilc=irc=0;
			
			d.setCurrent(dp);
			
		}
		
		/*if (c.getLabel().equals("����")){
			
			toZero();
			
			ilc=irc=0;
			
			d.setCurrent(dp);
		}*/
		
		if (c.getLabel().equals("ȡ��")||c.getLabel().equals("�鿴")||c.getLabel().equals("����")){
			
			d.setCurrent(this);
		}
		
/*		if (c.getLabel().equals("������ʾ")){
		
			sText = saa2Text(saaText);
			
			pText(sText);
			
			pCur(ilc, irc, false);
			
			repaint();
		}*/
		
		if (c.getLabel().equals("��ת")){
			
			
			Form fj=(Form)dis;
			
			String sc=((TextField)fj.get(0)).getString();//���������
			
			int ic=((ChoiceGroup)fj.get(1)).getSelectedIndex();
			
			int in=1;
			
			try{
				
				in=Integer.parseInt(sc);
				
			}catch(Exception e){
				
				in=1;
			}
			
			
			pCur(ilc, irc, true);
			
			
			switch(ic){
			
			case 0:  //��תҳ
				
				iScreen=in-1;
				
				if(iScreen>iSn-1){
					
					iScreen=iSn-1;
				}
				
				ilc = iScreen * lineN;
				
				break;
				
			case 1: //��ת��
				
				ilc = in-1;
				
				if(ilc>iln-1){
					
					ilc=iln-1;
				}
				
				iScreen=ilc/lineN;
				
				break;
				
			case 2: //��ת��
					
				int sn=ssf.length;
				
				
				int i=0;
				
				for( i=0;i<sn;i++){
					
					if(ssf[i].startsWith((in-1)+":")){
						
						break;
					}
					
				}
				
				ilc=i; //��һ�����Ƕ����ڵ�����
				
				System.out.println("��="+i);
				
				if(ilc>iln-1){
					
					ilc=iln-1;
				}
				
				iScreen=ilc/lineN;
				
				break;	
			
			
			}
		
			
			irc = 0;
			
			
			
			drawScreen(iScreen);
			
			pCur(ilc, irc, false);
			
			//repaint();
			
			d.setCurrent(this);
			
			return;
			
			
		}
		
		if (c.getLabel().equals("ɾ��")){
			
			
			Form fd=(Form)dis;
			
			
			int ic=((ChoiceGroup)fd.get(0)).getSelectedIndex(); //ɾ����ʽ
			
			
			//////////////////////////////////////////��λҪɾ�����Ķ�
			
			
			/*String si = ssf[ilc].substring(0, ssf[ilc].indexOf(':'));
			
			String sj = ssf[ilc].substring(ssf[ilc].indexOf(':') + 1);
			
			int i = Integer.parseInt(si);
			
			int j = Integer.parseInt(sj);*/
			
			
			int i = getPL(ssf,ilc)[0];
			
			int j = getPL(ssf,ilc)[1];
			
			
			//String st = getlString(ilc);
			
			
			
			
			//////////////////////////////////////////
			
			
			switch(ic){
			
			case 0: //ɾ����
				
				saaText[i][j]=null; //ɾ����
				
				
				if(ilc>0){ //�������
					
					ilc--;
				}
				
				if(saaText[i]!=null){ //˳�㴦���
					
					boolean noPara=true;
					
					for(int li=saaText[i].length-1;li>=0;li--){
						
						if(saaText[i][li]!=null){
							
							noPara=false;
						}
					}
					
					if(noPara){
						
						saaText[i]=null;
					}
				}
				
				break;
				
			case 1: //ɾ����
					
				saaText[i]=null; //ɾ����
				
				int fl=getL(i,0);//����������
				
				if(fl>0){
					
					ilc=fl;
				}
			
				break;	
				
			case 2: //ɾ��ȫ��
				
				sText=""; //ɾ��ȫ��
				
				pText(sText);
				
				pCur(ilc, irc, true);
				
				pCur(ilc=0, irc=0, false);
				
				repaint();
				
				d.setCurrent(this);
				
				return;
			
			
			}
		
			sText = saa2Text(saaText);
			
			pText(sText);
			
			/*drawScreen(iScreen);*/
			
			pCur(ilc, irc, false);
			
			repaint();
			
			d.setCurrent(this);
			
			return;
			
			
		}
		
		if (c.getLabel().equals("ִ��")){//�����滻
			
			reFresh();//����Ѿ��������
			
			Form f=(Form)dis;
			
			int fn=f.size();
			
			for(int fi=3;fi<fn;fi++){ //ȥ����ӡ����
				
				try{
					
					f.delete(3); //���Զ�����
					
				}catch(Exception e){
					
					e.printStackTrace();
				}
				
				
			}
			
			
			int iRep=((ChoiceGroup)f.get(0)).getSelectedIndex();//�Ƿ��滻
			
			String ssrc=((TextField)f.get(1)).getString();//���ҵĶ���
			
			String sdes=((TextField)f.get(2)).getString();//�����滻�Ķ���
			
			
			/*�滻ȫ��*/
			
			
			
			if(iRep==2){ //�滻ȫ��,�������ٱ����
				
				
				sText=replaceString(sText,ssrc,sdes);
					
				pText(sText);
				
				
				fFR.append("�滻���");
				
				return;
			}
			
			
			/*����*/
			
			
			Vector vsf=new Vector();//��¼���ҵ���λ��
			
			try{
				
				
				String[] saP=dowithString(sText); //��ȡÿ���ı����ַ���
				
				int np=saP.length;
				
				for(int i=0;i<np;i++){
					
					String st=saP[i];
				
					//System.out.println(st+np);
					
					if(st.indexOf(ssrc)==-1){ //������Ŀ���ַ���
						
						//System.out.println(st);
						
						continue;
						
					}else{ // �ҵ�
						
						
						//System.out.println(st);
						
						int jn=saaText[i].length;
						
						System.out.println("jn="+jn);
						
						for(int j=0;j<jn;j++){
							
							
							String stf=saaText[i][j];
							
							int isf=-1;
							
							do{  ///һ�ٸɾ�
								
								
								isf=stf.indexOf(ssrc);
								
								if(isf!=-1){ //�ҵ�
									
									int sfn=ssf.length;
									
									int li=1;
									
									for(int k=0;k<sfn;k++){
										
										if(ssf[k].equals(i+":"+j)){
											
											li=k;
											
											break;
										}
									}
									
									vsf.addElement(li+":"+isf);
									
									fFR.append("�ҵ�:"+li+"��"+isf+"��");
									
									//System.out.println(li+":"+isf);
									
									stf=stf.substring(isf+ssrc.length());
									
								}
								
								
							}while(isf>-1);
							
							/////����һ�еĴ���
							
							if(saaText[i].length>j+1){
								
								isf=(saaText[i][j]+saaText[i][j+1]).indexOf(ssrc);
								
								if(isf>=saaText[i][j].length()){
									
									int sfn=ssf.length;
									
									int li=1;
									
									for(int k=0;k<sfn;k++){
										
										if(ssf[k].equals(i+":"+j)){
											
											li=k;
											
											break;
										}
									}
									
									vsf.addElement(li+":"+isf);
									
									fFR.append("�ҵ�:"+li+"��"+isf+"��");
									
									//System.out.println(li+"��ʼ:"+isf);
								}
								
								
							}
							
							//System.out.println("here");
						
							
						} //�ڲ�for����
						
						
						
					}
				
					
				}//���for����
				
				
				
				String[] saf=new String[vsf.size()];
				
				vsf.copyInto(saf);
				
				
				/*���*/
				
				//System.out.println("��="+"��=");
				
				
				int an=saf.length;
				
				for(int i=0;i<an;i++){
					
					String si=saf[i];
					
					
					String sl = si.substring(0, si.indexOf(':')); //��
					
					String sr = si.substring(si.lastIndexOf(':') + 1);//��
					
					//int pi = Integer.parseInt(sp);
					
					int li = Integer.parseInt(sl);
					
					int ri = Integer.parseInt(sr);
					
					
					int pi = getPL(ssf,li)[0];
					
					int pp = getPL(ssf,li)[1];
					
					
					
					
					//System.out.println("��="+li+"��="+ri);
					
					
					////// ʹ��һ������ɫ��ǲ��ҵ��Ķ���
					
					
					/*int cx = 0;
					
					cx = LW+this.f.stringWidth(saaText[pi][pp].substring(0, ri));
					
					int cy = li * charH;
				
					
					int w=this.f.stringWidth(ssrc);
					
					int h=charH-2;
					
					//gbuf.drawLine(cx, cy, cx, (cy + charH) - 2);
					
					gbuf.setColor(cBgd);
					
					gbuf.fillRect(cx, cy, w, h);
					
					
					gbuf.setColor(cFind);
					
					
					gbuf.drawString(ssrc, cx, li * charH, 20); //���»����ַ�
					
					gbuf.setColor(cBgd);
					
					//////////////////////////////////�迼�ǻ������
					
					
					//boolean bnl=false;
					
					int nl=ri+ssrc.length()-saaText[pi][pp].length();
					
					if(nl>0){
						
						//bnl=true;
						
						
						//System.out.println("nl="+nl);
						
						String ss=saaText[pi][pp+1].substring(0, nl);
						
						//System.out.println("��Ҫ������:"+ss);
						
						
						cx = LW;//��ʼ����
						
						cy = (li+1) * charH;
					
						
						w=this.f.stringWidth(ss);
						
						h=charH-2;
						
						//gbuf.drawLine(cx, cy, cx, (cy + charH) - 2);
						
						gbuf.setColor(cBgd);
						
						gbuf.fillRect(cx, cy, w, h);
						
						
						gbuf.setColor(cFind);
						
						
						gbuf.drawString(ss, cx, (li+1) * charH, 20); //���»����ַ�
						
						gbuf.setColor(cBgd);
					
						
					}*/
					
			
				}
				
				/*�滻*/
				
				if(iRep==1){//�滻��һ��
					
					int isf=sText.indexOf(ssrc);
					
					if(isf>-1){
						
						sText=sText.substring(0,isf)+sdes+sText.substring(isf+ssrc.length());
					}
					
					try{
						
						
						f.set(3,new StringItem("","���滻"));
						
						pText(sText);
						
						
					}catch(Exception e){}
					
					
					
					//d.setCurrent(this);
					
				}
				
			
						
				
			}catch(Exception e){
				
				e.printStackTrace();
			}
			
			//d.setCurrent(this);
			
			return;
			
		}
		
		if (c.getLabel().equals("����")){//����txt
			
			final Displayable dp=dis;
			
			final textCanvas tc=this;
			
			new Thread(){
				
				
				public void run(){
					
					Form f=(Form)dp;
					
					String url=((TextField)f.get(0)).getString();			
					
					FileConnection fc=null;
					
					InputStream is=null;
					
					try{
						
						fc=(FileConnection)Connector.open("file:///x/x.x");
						
						
					}catch(Exception e){}
					
					try{
						
						fc=(FileConnection)Connector.open(url);
						
						is=fc.openInputStream();
						
						byte[] b=new byte[(int)fc.fileSize()];
						
						is.read(b);
						
						try{
							
							
						}catch(Exception e){
							
							sFile=url;
						}
					
						
						String stext="";
						
						if(((ChoiceGroup)f.get(1)).isSelected(0)){//utf-8ģʽ
							
							stext=new String(b,0,b.length,"utf-8"); 
							
						}else{
							
							stext=new String(b);
						}
						
						/////////////////////////////
						
						
						insertText(stext);
						
						/////////////////////////// ��������ı�
					
						
						d.setCurrent(tc);//�ָ���ʾ
						
						
						
					}catch(Exception e){
						
						f.append("����!"+e.getMessage());
						
						e.printStackTrace();
					}
					
					finally{
						
						try{
							
							fc.close();
							
							is.close();
						
						}catch(Exception e){}
						
						
					}
					
				}
		
			}.start();
		
		
			return;
			
		}
		
		if (c.getLabel().equals("�����ı�")){//����txt
			

			Form f=new Form("�����ı�");

			
			f.append(new TextField("����·��",sFile,88888,0));
			
			f.append(new ChoiceGroup("����",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
			
			//f.append(new ChoiceGroup("��ʽ",ChoiceGroup.POPUP,new String[]{"���뵱ǰλ��","�滻ԭ�ַ���"},null));
			
			f.addCommand(new Command("����",1,50));
			
			f.addCommand(new Command("ȡ��",2,2));
			
			f.setCommandListener(this);
			
			d.setCurrent(f);
			
			return;
			
		}
		
		if (c.getLabel().equals("�س�")||c.getLabel().equals("����")){
			
			
			String sit=""; //Ҫ������ı�
			
			
			if(c.getLabel().equals("����")){
				
		
				sit=((TextBox)dis).getString();//�����ı���
				
				
			}else if(c.getLabel().equals("�س�")){
				
				 sit="\n";
				
			}
			
			insertText(sit);
			
			d.setCurrent(this);
			
			return;
			
		}
		
		if (c.getLabel().equals("�����ı�")){
			
			
			Form f=new Form("�����ı�");
			
			
			f.append(new TextField("����·��",sFile,88888,0));
			
			f.append(new ChoiceGroup("����",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
			
			f.append(new ChoiceGroup("��ʽ",ChoiceGroup.POPUP,new String[]{"�����ļ�ĩβ","��д�ı��ļ�"},null));
			
			f.addCommand(new Command("����",1,51));
			
			f.addCommand(new Command("ȡ��",2,2));
			
			f.setCommandListener(this);
			
			d.setCurrent(f);
			
			return;
			
		}
		
		if (c.getLabel().equals("����")){
			
			
			final Displayable dp=dis;
			
			final textCanvas tc=this;
			
			
			new Thread(){
				
				
				public void run(){
					
					Form f=(Form)dp;
					
					String url=((TextField)f.get(0)).getString();
					
					FileConnection fc=null;
					
					OutputStream os=null;
					
					
					boolean bw=((ChoiceGroup)f.get(2)).isSelected(0);//����ĩβ
					
					try{
						
						fc=(FileConnection)Connector.open(url,Connector.READ_WRITE);
						
						
						if(bw){//����ĩβ
							
							
							if(!fc.exists()){
								
								fc.create();
								
							}
							
							os=fc.openOutputStream(fc.fileSize());
							
						}else{ //��д
							
							//System.out.println("��д");

							if(fc.exists()){
								
								fc.delete();
								
							}
							
							fc.create();
							
							os=fc.openOutputStream();
						}
						
					
						
						sFile=url;
						
						
					
						if(((ChoiceGroup)f.get(1)).isSelected(0)){//utf-8ģʽ
							
							os.write(sText.getBytes("utf-8"));
							
						}else{
							
							os.write(sText.getBytes());
						}
						
						
						d.setCurrent(tc);//�ָ���ʾ
						
					}catch(Exception e){
						
						e.printStackTrace();
						
						f.append("����!"+e.getMessage());
					}
					
					finally{
						
						
						try{
							
							fc.close();
							
							os.close();
						
						}catch(Exception e){}
						
					}
					
					
				}
				
			}.start();
		
			
		return;
			
		}
		
		
		
	}

}
