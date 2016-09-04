

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


	public static String sText = "文本编辑测试";
	
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
	
	public static int cBord = 0xBBBBBB;//边框颜色
	
	public static int clFont = 0xFF00FF;//行号颜色
	
	public static int cpFont = 0x00FFFF;//段号颜色
	
	public static int cpBgd = 0xFF00bb;//段号底色
	
	public static int cBgd = 0xbbffff;//背景颜色
	
	public static int cFont = 0x0000ff;//字体颜色
	
	public static int cCur = 0xff0000;//光标颜色
	
	public static int cFind = 0xff00dd;//查找颜色
	
	public static int cSelect = 0xff0000;//选中颜色
	
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
	
	public static int LW=2;//行号显示宽度
	
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
	
	public Form fFR=null;//用于查找替换
	
	
	public Form fSet=null;//用于系统设置
	
	
	public static int ikDel=-8; //删除键键值
	
	////////////////////////////////////////预定义菜单 正常模式
	
	public Command cBack=new Command("退格", 2, 1);
	
	public Command cExt=new Command("导出文本", 4, 2);
	
	public Command cOK=new Command("确认返回", 4, 1);
	
	public Command cImt=new Command("导入文本", 4, 3);
	
	public Command cSet=new Command("系统设置", 4, 4);
	

	
	
	////////////////////////////////////////预定义菜单 标记模式
	
	public Command cC=new Command("复制", 4, 1);
	
	public Command cX=new Command("剪切", 7, 2);
	

	////////////////////////////////////////
	
	
	public static String sC=""; //复制内容
	
	
	public static boolean bFull=false;//是否全屏
	
	////////////////////////////////////////
	
	public static boolean bFMod=false; //全屏修正
	
	
	public static String sSize="240*320";//默认全屏尺寸
	
	
	public static boolean bNoSelect=false;//不标记重绘
	
	
	////////////////////////////////////////
	
	
	private static boolean haveRMS=true;//是否存在rms
	
	public static boolean brS=false;//是否托管
	
	public static String sFile="file:///E:///text.txt";
	
	public static textCanvas tc=null; //避免多次生成占用资源
	
	
	private static int ifSize=8;//字体大小
	
	
	private int headORend=0;
	
	
	
	public textCanvas(Display d, Displayable dp, String stype,Object sob){
		
		setTitle("文本编辑");
		
		this.d = d;
		
		this.dp = dp;
		
		this.sType=stype;
		
		
		this.sOb=sob;
		

		if(tc==null){
			
			tc=this;
		}

/*		
		rmsDo(false);//读取设置
		
		System.out.println("托管="+brS);*/ //放到启动时读取
		
		
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
		
		
		//System.out.println("画布"+ sText);
		
		
		addCommand(cBack);
		
		addCommand(cExt);
		
		addCommand(cImt);
		
		addCommand(cOK);
		
		addCommand(cSet);
		
		//addCommand(cFS);
		
		//addCommand(new Command("返回", 2, 2));
		
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
		
		/////////////////////////  处理全屏及修正
		
		if(bFMod){ //全屏修正
			
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
		
		charW = f.charWidth('圜');
		
		//System.out.println(charW);
		
		charEW = f.charWidth(' ');
		
		charEW =1;
		
		//System.out.println("字母" + charEW);
		
		charH = f.getHeight();
			
		
		int numW=f.stringWidth("9");
		
///////////////////////////////////////总行数估算
		
		/*showW = W -rbW - bordW-4*numW-1;//用于显示行号(假设为4位)及鼠标
		 * 
*/		
		showW = W -rbW-2;//用于不现实行号，仅仅表明段
		
		minN = showW / charW;
		
		int tl=s.length()/minN+1; //预估总行数
		
		
		//System.out.println("预估行数："+tl);
		
		
		////////////////////算出行号显示宽度
		
		
		/*if(tl>=1000){ //4位
			
			LW=4*numW+2;
			
		}else if(tl>=100&&tl<1000){//3位
			
			LW=3*numW+2;
			
		}else if(tl>=10&&tl<100){//2位
			
			LW=2*numW+2;
			
		}else if(tl>=0&&tl<10){ ////1位
			
			LW=1*numW+2;
			
		}*/
		
		LW=2;
		
		//System.out.println("段号宽度:"+LW);
			
		///////////////////// 计算显示的字符
		
		showW = W -rbW-2;//用于不现实行号，仅仅表明段
		
		maxN = showW / charEW;
		
		//System.out.println("最大" + maxN);
		
		minN = showW / charW;
		
		//System.out.println("最小" + minN);
		
		lineN = (H)/charH;//调整
		
		//System.out.println("每页行数："+lineN)
		
	
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
				
				//System.out.println((System.currentTimeMillis()-lstart)+"大小"+i);
				
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
		
		
		//System.out.println("计算完成"+(System.currentTimeMillis()-lstart));
		
		ssf = new String[lsV.size()];
		
		lsV.copyInto(ssf);
		
		iln = j;
		
		if(ilc>iln){ //防错处理
			
			ilc=iln;
		}
		
		//System.out.println("iln=" + iln);
		
		
		
		if(j==0){ //多退少补
			
			iSn = 1;
			
		}else{
			
			iSn = (j-1)/ lineN +1;
		}
		
		if(iScreen>iSn-1){//防错处理
			
			ilc=ilc-(iScreen-iSn+1)*lineN;
			
			iScreen=iSn-1;
			
			
		}
		
		//改为只显示一页，需要时再重新绘制
		
		
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
					
					ip=j; //段的首行
				}
				
				pString(saaText[i][ii], j++);
				
				System.out.println(System.currentTimeMillis()-lstart);
			}
				
			////标上段号	
			
			if(j<=iln){  
				
				
				int h;  //=line * charH;
				
				int iSh=(ip)/lineN; //第几屏
				
				h=iSh*H+((ip)%lineN)* charH; //考虑绘制边框
				
				

				gbuf.setColor(cpBgd);
				
				gbuf.fillRect(0, h+2, LW-1,charH-2);
				
				gbuf.setColor(cpFont);
				
			
				
				gbuf.drawString((i+1)+"", 0, h, Graphics.TOP| Graphics.LEFT); //标上段号
				
				gbuf.setColor(cBgd);
			}
			

		}*/
		
		
		/*改为只绘制当前页*/
		
		
		//计算绘制始末行
		
/*		int lst=iScreen*lineN;
		
		int led=iScreen*lineN+lineN-1;
		
		
		for(int li=lst;li<=led;li++){
			
			System.out.println("这二？"+lst);
			
			int pp=getPL(ssf,li)[0];
			
			int ll=getPL(ssf,li)[1];
			
			System.out.println("这二？");
			
			pString(saaText[pp][ll], li%lineN);
			
			
			if(ll==0){ //段首
				
				
				int h=(li%lineN)* charH; //考虑绘制边框
				
		
				gbuf.setColor(cpBgd);
				
				gbuf.fillRect(0, h+2, LW-1,charH-2);
				
				gbuf.setColor(cpFont);
		
				
				gbuf.drawString((li+1)+"", 0, h, Graphics.TOP| Graphics.LEFT); //标上段号
				
				gbuf.setColor(cBgd);
				
			}
			
		}
		*/
	
		//System.out.println("绘制完成"+(System.currentTimeMillis()-lstart));

		//System.out.println("saa=" + saaText[0][0]);
	}

	public void drawScreen(int iS){ //需要处理空行段
		
		
		clearS();//改为只显示一页，需要时再重新绘制
		
		pLable(sText);//改为只显示一页，需要时再重新绘制
	
		
		
		int lst=iS*lineN;
		
		int ln = (iS + 1) * lineN >= iln ? iln : (iS + 1) * lineN;
		
		
		int led=ln-1;
		
		
		
		
		for(int li=lst;li<=led;li++){
			
			//System.out.println("这二？"+lst);
			
			int pp=getPL(ssf,li)[0];
			
			int ll=getPL(ssf,li)[1];
			
			//System.out.println("这二？");
			
			if(saaText[pp]!=null&&saaText[pp][ll]!=null&&saaText[pp][ll].length()!=0){
				
				pString(saaText[pp][ll], li%lineN);
				
				/*if(ll==0){ //段首
					
					
					int h=(li%lineN)* charH; //考虑绘制边框
					
			
					gbuf.setColor(cpBgd);
					
					gbuf.fillRect(0, h+4, LW,charH-8);
					
					//gbuf.setColor(cpFont);
			
					
					//gbuf.drawString((pp+1)+"", 0, h, Graphics.TOP| Graphics.LEFT); //标上段号
					
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
		
		//System.out.println("嵌入完成"+(System.currentTimeMillis()-lstart));
		
		sText = saa2Text(saaText);
		
		System.out.println("转化完成"+(System.currentTimeMillis()-lstart)+"大小"+sText.length());
		
		pText(sText);
		
		//System.out.println("嵌入完成"+(System.currentTimeMillis()-lstart));
		
		pCur(ilc, irc, false);
		
		repaint();
		
		
	}

	public void pString(String s, int line){ //未解决
	
		gbuf.setColor(cFont);
		
		
		int h=line* charH; //考虑绘制边框
		
		
		gbuf.drawString(s, LW, h, 20); //


		//gbuf.setColor(clFont);
		
		
		//gbuf.drawString((iScreen*lineN+line+1)+"", LW-2, h, Graphics.TOP| Graphics.RIGHT); //标上行号
		
		
	}

	public void pCur(int line, int row, boolean clear){
		
		
		StringBuffer sb=new StringBuffer();
		
		if(!doS){
			
			sb.append("文本编辑 [页").append((iScreen+1)).append("-行").append(ilc+1)
			
			.append("-列").append(irc).append("]");

			
		}else{
			
			
			sb.append("标记文本 [").append(sl1).append(",").append(sr1)
			
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
			
			
			int iSh=(line)/lineN; //第几屏
			
			/*cy=iSh*H+((line)%lineN)* charH; //考虑绘制边框
*/			
			cy=((line)%lineN)* charH; //考虑绘制边框
			
			int color = clear ? cBgd - cCur : cCur;
			
			gbuf.setColor(color);
			
			gbuf.drawLine(cx, cy+2, cx, (cy + charH) - 3);
			
			gbuf.setColor(cBgd);
		}
		
		catch (Exception e) { 
			
			
			
			try{
				
				pCur(line+1, 0,clear);//尝试画在下一行
				
			}catch(Exception ex){
				
				e.printStackTrace();
				
				pCur(line-2, 0,clear);//尝试画在上一行
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
		
		//g.drawImage(imgrgt, 0, 0, 20);//左框
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
		
		if (f.stringWidth(s) <= showW ){//可以直接显示
			
			//System.out.println(s + "              直接");
		
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
			
			
			if(!normal){ //非正常模式
			
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
	
	
	public static void toZero(){ //数据归零
		
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
		
		if(!haveCut){//无效数据
			
			return sC;
		}
		
		
		int lmin=Math.min(l1, l2);
		
		int lmax=Math.max(l1, l2);
		
		int rmin=0;
		
		int rmax=0;
		
		
		if(lmin==l1){ //正向复制
			
			rmin=r1;
			
			rmax=r2;
			 
		}else{       //负向复制
			
			rmin=r2;
			
			rmax=r1;
		}
		
		////////////////////////处理行首行末
		
		if(l1<l2&&rmin==getlString(lmin).length()){ //上一行末 ,正向复制
			
			lmin++;
			
			rmin=0;
			

			//System.out.println("行末");
		
		}
		
		if(l1>l2&&rmax==0){ //行首 ，逆向复制
			
			lmax--;
			
			rmax=getlString(lmax).length();
			
			//System.out.println("行首");
		}
		
			
		
		
		StringBuffer sb=new StringBuffer();
		
		
		int lend=rmax;//末尾
		
		if(lmax>lmin){ //超过一行
			
			lend=getlString(lmin).length();
			
			sb.append(getlString(lmin).substring(rmin,lend)); //
			
			//System.out.println("测试下");
			
			if(getPL(ssf,lmin)[0]!=getPL(ssf,lmin+1)[0]){ //说明是段尾
				
				//System.out.println("测试上");
				
				sb.append("\n");
			}
			
			
			
			for(int i=lmin+1;i<lmax;i++){ //复制整行
				
				sb.append(getlString(i)); //
				
				if(getPL(ssf,i)[0]!=getPL(ssf,i+1)[0]){ //说明是段尾
					
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
		
		
		
		
		if(l1==l2&&r1==r2){ //无效
			
			
			haveCut=false;
			
			return;
		}
		
		////////////////////////排序
		
		int lmin=Math.min(l1, l2);
		
		int lmax=Math.max(l1, l2);
		
		int rmin=0;
		
		int rmax=0;
		
		
		if(lmin==l1){ //正向复制
			
			rmin=r1;
			
			rmax=r2;
			 
		}else{       //负向复制
			
			rmin=r2;
			
			rmax=r1;
		}
		
		
		
		////////////////////////处理行首行末
		
		if(l1<l2&&rmin==getlString(lmin).length()){ //上一行末 ,正向复制
			
			lmin++;
			
			rmin=0;
			

			//System.out.println("行末");
		
		}
		
		if(l1>l2&&rmax==0){ //行首 ，逆向复制
			
			lmax--;
			
			rmax=getlString(lmax).length();
			
			//System.out.println("行首");
		}
		
		
		if(lmin==lmax&&rmin==rmax){ //无效
			
			haveCut=false;
			
			return;
		}
		
		haveCut = true;
		

		if(bNoSelect){ //不需要重新绘屏
			
			
			
			return;
			
		}
		
		
		//reFresh(); //重绘
	
		////////////////////////绘制
		
		gbuf.setColor(cSelect);
		
		
		//		绘制前半行
		
/*		int h;  //=line * charH;
		
		int iSh=(lmin)/lineN; //第几屏
		
		h=iSh*H+((lmin)%lineN)* charH; //考虑绘制边框
		
		
*/		
		int h=((lmin)%lineN)* charH; //考虑绘制边框
		
		int lend=rmax;
		
		if(lmax>lmin){ //超过一行
			
			/////////////////////////////////只绘制最后一屏幕
			
			int iSmin=(l1)/lineN; //第几屏
			
			int iSmax=(l2)/lineN; //第几屏
			
			if(iSmax>iSmin){ //前后跨屏
				
				System.out.println("跨屏幕复制");
				
				lmin=iSmax*lineN;
				
				rmin=0;//必然是首列
				
				if(lmin==l2) { //从头开始一行
					

					int cx=LW+0;
					
					h=((lmin)%lineN)* charH; //考虑绘制边框
					
					gbuf.drawString(getlString(lmin).substring(0,r2), cx, h, 20); //
					
					return;
					
				}
				
			}
			
			if(iSmax<iSmin){ //后前跨屏
				
				System.out.println("跨屏幕复制");
				
				lmax=(iSmax+1)*lineN-1;//屏幕末尾
				
				rmax=getlString(lmax).length();//必然是末列
				
				if(lmax==l1) { //从尾开始一行
					

					int cx=LW+f.stringWidth(getlString(lmin));
					
					h=((lmax)%lineN)* charH; //考虑绘制边框
					
					gbuf.drawString(getlString(lmax).substring(r1,rmax), cx, h, 20); //
					
					return;
					
				}
				
			}
			
			
			
			h=((lmin)%lineN)* charH; //考虑绘制边框
			
			lend=rmax;
			
			
			///////////////////////////////
			
			lend=getlString(lmin).length();
			
			//System.out.println("问题呢"+rmin);
			
			int cx=LW+f.stringWidth(getlString(lmin).substring(0, rmin));
			
			gbuf.drawString(getlString(lmin).substring(rmin,lend), cx, h, 20); //
			
			for(int i=lmin+1;i<lmax;i++){ //绘制整行
				
				
				h=((i)%lineN)* charH; //考虑绘制边框
				
				gbuf.setColor(cCur);
				
				gbuf.drawString(getlString(i), LW, h, 20); //
				
				
			}
			
			

			//		绘制后半行
			
		
			/*iSh=(lmax)/lineN; //第几屏
			*/
			h=((lmax)%lineN)* charH; //考虑绘制边框
			
			cx=LW;
			
			
			gbuf.drawString(getlString(lmax).substring(0,rmax), cx, h, 20); //
				
			
			
			
		}else{ //一行
			
			
			int rleft=Math.min(rmin, rmax);
			
			int rright=Math.max(rmin,rmax);
			
			int cx=LW+f.stringWidth(getlString(lmin).substring(0, rleft));
			
			gbuf.drawString(getlString(lmin).substring(rleft,rright), cx, h, 20); //
			
		}
		
		
		
	
		//repaint();
		
		
		
	}
	
	
	
	
	public String replaceString(String s,String ssrc,String sdes){
		
		
		if(ssrc.equals(sdes)){ //无需替换
			
			return s;
		}
		
		
		/////////////////////// 不等替换
		
		int isf=-1;
		
		String stf=s;
		
		StringBuffer sb=new StringBuffer();
		
		do{  ///一举干净
			
			
			isf=stf.indexOf(ssrc);
			
			if(isf!=-1){ //找到  需要避免循环替换死循环
				
				//System.out.println("找到"); 
				
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
			
			if(ssf[k].equals(p+":"+l)){ //段行所在某行
				
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
			
			//System.out.println("不对");
			
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
		
		//System.out.println("当前行"+ilc);
		
		switch (key){
		
		case Canvas.KEY_NUM1: // '1' //行首
			
			/*if(doS){ //标记界面
				
				sr2=0;
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				return;
			}*/
			
			pCur(ilc, irc, true);
			
			irc=0;
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
			
		case Canvas.KEY_NUM3: // '3' //行末
			
			/*
			if(doS){ //标记界面
				
				sr2=irc=getlString(ilc).length();
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				return;
			}*/
			
			pCur(ilc, irc, true);
			
			irc=getlString(ilc).length();
			
			pCur(ilc, irc, false);
			
			
			repaint();
			
			return;
			
			
		case Canvas.KEY_NUM9: // '9' //文末
			
			
			/*if(doS){ //标记界面
				
				iScreen=iSn-1;
				
				sl2=Math.min(iln-1, ((iScreen+1)*lineN-1));
				
				sr2=getlString(ilc).length();
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				return;
			}
			*/
			
			
			headORend=(headORend+1)%2; //第一次为1
			
			
			if(headORend==1){ //文末
				
				iScreen=iSn-1;
				
				ilc=Math.min(iln-1, ((iScreen+1)*lineN-1));
				
				irc=getlString(ilc).length();
				
			}else{ //文首
				
				iScreen=0;
				
				ilc=0;
				
				irc=0;
				
			}
			
			
			
			
			
			pCur(ilc, irc, true);
			
		
			drawScreen(iScreen);
			
			pCur(ilc, irc, false);
					
			repaint();
			
			
			
			
			return;
			
			
/*		case Canvas.KEY_STAR: // '*' //更新显示 
			
	
			
			return;*/
			
		
		case Canvas.KEY_NUM0: // '0' //更新数据
			
			//this.setFullScreenMode(bFull=!bFull);
			
			
			if(doS){ //标记界面 重订起点
				
				//System.out.println("ddd");
				
			
				/*int oc=cCur;
					
				cCur=cSelect;//
					
				pCur(sl1,sr1,false);//绘制起点
					
				cCur=oc;
				*/
				
				drawScreen(iScreen);//重绘
				
				sl1=sl2 = ilc;
				
				sr1 =sr2= irc;
				
				pCur(sl1,sr1,false);//绘制起点
				
				repaint();
			
				
				
				return;
				
			}
			
			reFresh();
			
			return;
			
			
		case Canvas.KEY_POUND: // '#' //切换状态
			
			
			doS =!doS; //切换
			
			reFresh();
			
			
			haveCut=false;
			
			if(doS){
				

				sl1=sl2 = ilc;
				
				sr1 =sr2= irc;
				
				setTitle("标记模式[文本总字数:"+sText.length()+"]");
				
			}
			
			
			normalCmd(!doS);
			
			
			
			return;
			
		case Canvas.KEY_NUM7: // '7' //跳转
			
			
			Form fj=new Form("跳转");
			
			fj.append(new TextField("","1",1024,TextField.NUMERIC));
			
			String[] s=new String[]{"第 页","第 行","第 段"};
					
			fj.append(new ChoiceGroup("",1,s,null));	
			
			fj.addCommand(new Command("跳转",1,40));
			
			fj.addCommand(new Command("取消",2,2));
			
			fj.setCommandListener(this);
			
			d.setCurrent(fj);
			
			return;
			
		case Canvas.KEY_NUM2: // '2' //删除
			
			Form fd=new Form("删除");
			
			String[] sd=new String[]{"当前行","当前段","清空文本"};
					
			fd.append(new ChoiceGroup("",1,sd,null));	
			
			fd.addCommand(new Command("删除",1,41));
			
			fd.addCommand(new Command("取消",2,2));
			
			fd.setCommandListener(this);
			
			d.setCurrent(fd);
			
			return;
			
		case Canvas.KEY_NUM8: // '8' //插入
			
			if(doS){ //粘贴
				
				insertText(sC);
				
				d.setCurrent(this);
				
				return;
				
			}
					
			TextBox ti=new TextBox("插入","",88888,0);
			
			//TextBox ti=new TextBox("插入",sC,88888,0);
			
			ti.addCommand(new Command("回车",1,42));
			
			ti.addCommand(new Command("粘贴",1,43));
			
			ti.addCommand(new Command("插入",2,44));
			
			ti.addCommand(new Command("取消",2,222));
			
			ti.setCommandListener(this);
			
			d.setCurrent(ti);
			
			return;
			
		case Canvas.KEY_NUM6: // '6' 右边翻页
			
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

		case Canvas.KEY_NUM4: // '4' 左翻页
			
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
				
				fFR=new Form("查找替换");
				
				fFR.append(new ChoiceGroup("",1,new String[]{"查找","替换一个","替换全部"},null));
				
				fFR.append(new TextField("查找","春风",88888,0));
				
				fFR.append(new TextField("替换","秋风",88888,0));
				
				fFR.addCommand(new Command("执行",1,50));
				
				fFR.addCommand(new Command("返回",2,2));
				
				fFR.setCommandListener(this);
				
			}
		
			d.setCurrent(fFR);
			
			return;
			
		}
		
		
		if(key==ikDel){ // 'C' //删除
			
			commandAction(cBack,this);
			
			return;
			
		}
		
		//虚拟导航键处理
		
		
		
		int gakey = getGameAction(key);
		
		if (gakey == Canvas.UP || gakey ==  Canvas.DOWN || gakey ==  Canvas.LEFT || gakey ==  Canvas.RIGHT ){
		
			pCur(ilc, irc, true);
			
			l1 = iScreen * lineN;
			
			l2 = (iScreen + 1) * lineN >= iln ? iln : (iScreen + 1) * lineN;
			
			r2 = sl.length();
			
			switch (gakey){
	
			case Canvas.UP: // '\001' 上
				
				if (ilc > l1){
					
					ilc--;
					
				}else{
					
					//ilc = l2 - 1;回环
					
					if(ilc!=0){ //左翻页
						
						ilc--;
						
						iScreen--;
						
						drawScreen(iScreen);
						
					}else{
						
						ilc = l2 - 1;
					}
				
					
				}
					
				if(getlString(ilc).length()<irc){ //修正行
					
					irc=getlString(ilc).length();
				}
			
			
				
				break;

			case Canvas.DOWN : // '\006'
				
				if (ilc < l2-1){
					
					ilc++;
					
				}else{
					
					// ilc = l1;回环
					
					
					if(iScreen!=iSn-1){ //右翻页
						
						ilc++;
						
						iScreen++;
						
						drawScreen(iScreen);
						
					}else{
						
						ilc = l1;
					}
				
				}
					
				if(getlString(ilc).length()<irc){ //修正行
					
					irc=getlString(ilc).length();
				}
				
				break;

			case  Canvas.LEFT: // '\002'
				
				if (irc > 0){ 
				
					irc--;
					
					break;
				}
				
				/////处理行首
				
				ilc--;
				
				if (ilc == l1 - 1){
					
					ilc = lineN - 1;
				}
					
				irc=getlString(ilc).length(); //上一行末尾
				
				
				if (ilc == lineN - 1){ //行首，翻页
					
					keyPressed(Canvas.KEY_NUM4);//左翻页
					
					return;
				}
			
				break;

			case Canvas.RIGHT: // '\005'
				
				if (irc < r2){
				
					irc++;
					
					break;
					
				}
			
				//////处理行末
				
				irc = 0;
				
				ilc++;
				
				if (ilc == l2){ //页末，翻页
					
					keyPressed(Canvas.KEY_NUM6);//右翻页
					
					return;
				}
			
				
				break;
			}
			
			//drawScreen(iScreen);
			
			pCur(ilc, irc, false);
			
			repaint();
			
			
			if(doS){ //标记界面
				
				sl2=ilc;
				
				sr2=irc;
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				if(doS){//标记模式
					
					int oc=cCur;
					
					cCur=cSelect;//标记色
					
					
					int iSmin=(sl1)/lineN; //第几屏
					
					int iSmax=(sl2)/lineN; //第几屏
					
					if(iSmin==iSmax){ //同一屏幕才绘制
						
						pCur(sl1,sr1,false);//绘制起点
					}
					
					
					
					cCur=oc;
				}
				
			
			}
				
			
			return;
		}
		
		/*if (gakey == Canvas.FIRE){//中键
		
			setTitle("标记文本");
			
			doS = true;
			
			sl1 = ilc;
			
			sr1 = irc;
			
			return;
		}*/
		
	}

	protected void keyRepeated(int key){ //长按
	
	
		switch (key){
		
		case Canvas.KEY_NUM1: // '1' //页首
			
			pCur(ilc, irc, true);
			
			ilc=iScreen*lineN;
			
			irc=0;
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
	
		case Canvas.KEY_NUM3: // '3' //页末
			
			pCur(ilc, irc, true);
			
			ilc=Math.min(iln-1, ((iScreen+1)*lineN-1));
			
			irc=getlString(ilc).length();
			
			pCur(ilc, irc, false);
			
			
			repaint();
			
			return;
		
			
		case Canvas.KEY_NUM0:// 0键 //返回
		
		
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
			
			
/*		case Canvas.KEY_NUM8: // '8' //粘贴
			
			insertText(sC);
			
			d.setCurrent(this);*/
		}
		
		
		if(key==ikDel){ //连续删除功能
			
			
			keyPressed(key);
			
		}
		
		
		//虚拟导航键处理
		
		int gakey = getGameAction(key);
		
		if (gakey == Canvas.UP || gakey ==  Canvas.DOWN || gakey ==  Canvas.LEFT || gakey ==  Canvas.RIGHT ){
		
				
			keyPressed(key);
			
			return;
		}
		
	
		
	}	

	public static String saa2Text(String saa[][]){
	
		StringBuffer sb = new StringBuffer();
		
		int n = saa.length;
		
		//System.out.println("字符串大小："+n);
		
		for (int i = 0; i < n; i++){
			
			
			if(saa[i]==null){ //无效略过
				
				//System.out.println("整段为空略过");
				
				continue;
			}
		
			int nn = saa[i].length;
			
			for (int j = 0; j < nn; j++){
				
				
				if(saa[i][j]==null){ //无效略过
					
					//System.out.println("整行为空略过");
					
					continue;
				}
				
				sb.append(saa[i][j]);
				
			}

			if (i < n-1){
				
				
				sb.append("\n");
				
				
				//System.out.println("i="+i);
			}
				
				
			
		}
		
		//System.out.println("检查"+sb.toString().endsWith("\n"));

		return sb.toString();
		
		
	}
	
	
	public static void rmsDo(boolean write){ ///rms操作
		
		try{
			
			String sp="";//空字符串
			
			if(write){ ////写
				
				
				RecordStore rms = RecordStore.openRecordStore("kTextor", true, RecordStore.AUTHMODE_ANY, true); 
			
				//System.out.println(rms);
				
				
				if(!haveRMS){
					
					
					//System.out.println("haveRMS"+haveRMS);
					
					rms.addRecord((brS+sp).getBytes("utf-8"), 0, (brS+sp).getBytes("utf-8").length);//1
					
					rms.addRecord(sFile.getBytes("utf-8"), 0, sFile.getBytes("utf-8").length);//2
					
					System.out.println("设置"+brS);
					
					
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
					
					//System.out.println("设置"+sFile);
					
					
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
			
			System.out.println("貌似设置读取失败！");
			
			haveRMS=false;
			
		
		}
		
	
		
		
	}
	
	

	public void commandAction(Command c, Displayable dis){
		
		if (c.getLabel().equals("粘贴")){
			
			insertText(sC);
			
			d.setCurrent(this);
			
			return;
			
		}
		
		
		if (c.getLabel().equals("全屏模式")){
			
			this.setFullScreenMode(true);
			
			pText(sText);
			
			//pText(sText); //drawScreen
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
			
		}
		
		if (c.getLabel().equals("系统设置")){
			
			
			if(fSet==null){
				
				
				fSet=new Form("系统设置");
				
				fSet.append(new ChoiceGroup("",2,new String[]{"文框托管"},null));
				
				
				((ChoiceGroup)fSet.get(0)).setSelectedIndex(0, brS);//强制托管
				
				
				fSet.append(new ChoiceGroup("",2,new String[]{"颜色标记"},null));
				
				
				((ChoiceGroup)fSet.get(1)).setSelectedIndex(0, !bNoSelect);//颜色标记
				
				fSet.append(new ChoiceGroup("",2,new String[]{"全屏模式"},null));
				
				
				((ChoiceGroup)fSet.get(2)).setSelectedIndex(0, bFMod);//全屏修正
				
				
				fSet.append(new TextField("",sSize,1024,0));
				
				
				fSet.append(new TextField("导入导出路径",sFile,1024,0));
				
				fSet.append(new TextField("删除键值",ikDel+"",6,0));
				

				fSet.append(new ChoiceGroup("字体大小",4,new String[]{"小","中","大"},null));
				
				int fsize=0;
				
				switch(ifSize){
				
				case 0:  //中
					
					fsize=1;
					
					break;
					
				case 8: //小
					
					fsize=0;
					
					break;
					
				case 16: //大
					
					fsize=2;
					
					break;
				
				
				}
							
				
				((ChoiceGroup)fSet.get(6)).setSelectedIndex(fsize, true);//强制托管
				
				//fSet.append("显示颜色设定");
				
				fSet.append(new TextField("显示颜色设定\n正文",int2HS(cFont),6,0));
				
				fSet.append(new TextField("背景",int2HS(cBgd),6,0));			
				
				//fSet.append(new TextField("行号",int2HS(clFont),6,0));
				
				//fSet.append(new TextField("段号",int2HS(cpFont),6,0));
				
				//fSet.append(new TextField("段底",int2HS(cpBgd),6,0));
				
				fSet.append(new TextField("光标",int2HS(cCur),6,0));
				
				//fSet.append(new TextField("查找",int2HS(cFind),6,0));
				
				fSet.append(new TextField("选中",int2HS(cSelect),6,0));
								
				fSet.append(new TextField("边框",int2HS(cBord),6,0));
				
				
				
				
				fSet.addCommand(new Command("保存",2,52));
				
				fSet.addCommand(new Command("默认",1,53));
				
				fSet.addCommand(new Command("取消",1,54));
				
				fSet.setCommandListener(this);
			}
			
			d.setCurrent(fSet);
			
			return;
		}
		
		
		if (c.getLabel().equals("保存")){
			
			brS=((ChoiceGroup)((Form)dis).get(0)).isSelected(0);//强制托管
			
			bNoSelect=!((ChoiceGroup)((Form)dis).get(1)).isSelected(0);//颜色标记
			
			
			boolean bfold=bFMod;
			
			bFMod=((ChoiceGroup)((Form)dis).get(2)).isSelected(0);//全屏修正
			
			
			
			sSize=((TextField)((Form)dis).get(3)).getString();
			
			sFile=((TextField)((Form)dis).get(4)).getString();
			
			try{
				
				int k=Integer.parseInt(((TextField)((Form)dis).get(5)).getString());
				
				ikDel=k; //删除键值
				
				
			}catch(Exception e){}
			
			
			
			//字体大小
			
			int isize=((ChoiceGroup)((Form)dis).get(6)).getSelectedIndex();
			
			if(isize==0){
				
				isize=8; //小
				
			}else if(isize==1){
				
				isize=0;//中
				
			}else{
				
				isize=16;//大
			}
			
			
			//颜色读取
			
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
				
				
				if(bfold!=bFMod){//有屏幕变化
					
					this.setFullScreenMode(bFMod);
									
				}	
				
				
				pText(sText); //更改字体
				
				pCur(ilc, irc, false);
				
				repaint();
				
			}else{
				
				

				if(bfold!=bFMod){//有屏幕变化
					
					this.setFullScreenMode(bFMod);
					
					pText(sText); //更改字体
					
					pCur(ilc, irc, false);
					
					repaint();
					
									
				}else{
					
					
					drawScreen(iScreen);//pText(sText);//重绘
					
				}
				
				
				
			}
				
		
			
			rmsDo(true);//保存设置
			
			
			d.setCurrent(this);
			
			
			
		}
		
		if (c.getLabel().equals("默认")){
			
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
		
		if (c.getLabel().equals("剪切")){
			
			sC=getSelect(sl1,sr1,sl2,sr2);
			
			int ic=sText.indexOf(sC);
			
			if(ic>-1){			
				
				//System.out.println("剪切");
				
				sText=sText.substring(0,ic)+sText.substring(ic+sC.length());
			}
			
			pText(sText);
			
			drawScreen(iScreen);
			
			pCur(ilc, irc, false);
			
			repaint();
			
			if(sl1>sl2){ //重置标记起点
				
				ilc=sl1=sl2;
				
				irc=sr1=sr2;
				
			}else{
				

				ilc=sl2=sl1;
				
				irc=sr2=sr1;
			}
			
			return;
			
		}
		
		
		
		if (c.getLabel().equals("复制")){
			
			
			sC=getSelect(sl1,sr1,sl2,sr2);
			
			sl1=sl2 = ilc; //重置标记起点
			
			sr1 =sr2= irc;
			
			return;
			
		}
	
		if (c.getLabel().equals("退格")){
		
	
			if(ilc==0&&irc==0){
				
				return;
			}
		
			
			int i = 0;
			
			int j = 0;
			
			int ld=0;
			
			if (irc == 0){ //删到上一行
				
				
				i = getPL(ssf,ilc)[0];
				
				j = getPL(ssf,ilc)[1];
				
				
				if(saaText[i][j].length()==0){
					
					saaText[i][j]=null; //本行为空删除
					
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
				
				
				if((ilc)%lineN==0){ //上一页
					
					iScreen--;
					
					ilc--;
					
					drawScreen(iScreen);
					
					pCur(ilc, irc, false);
					
				}else{ //上一行
					
					try{  //删除
						
						/*if(st.length()>0){
							
							st = st.substring(0, st.length()-1);
							
							saaText[i][j] = st;
							
						}else{
							
							saaText[i]=null;
						}
							*/
						/*if(saaText.length==i+1||saaText[i+1]==null){
							
							//System.out.println("oh");
						
							//啥也不干
						}
						
						else if(saaText[i+1].length==1&&getlString(ilc).length()==0){ //删除段
							
							
							//System.out.println("删除段"+(i+1));
							
							//System.out.println("段"+saaText[i+2].length);
							
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
				
				
				
			}else{ //非行首
				
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
		
			
			/*reFresh();//更新
			 * 
			 * 
*/			
			
			
			pCur(ilc, irc, false);
			
			repaint();
			
		}
		
		if (c.getLabel().equals("确认返回")){
		
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
		
		/*if (c.getLabel().equals("返回")){
			
			toZero();
			
			ilc=irc=0;
			
			d.setCurrent(dp);
		}*/
		
		if (c.getLabel().equals("取消")||c.getLabel().equals("查看")||c.getLabel().equals("返回")){
			
			d.setCurrent(this);
		}
		
/*		if (c.getLabel().equals("更新显示")){
		
			sText = saa2Text(saaText);
			
			pText(sText);
			
			pCur(ilc, irc, false);
			
			repaint();
		}*/
		
		if (c.getLabel().equals("跳转")){
			
			
			Form fj=(Form)dis;
			
			String sc=((TextField)fj.get(0)).getString();//数字输入框
			
			int ic=((ChoiceGroup)fj.get(1)).getSelectedIndex();
			
			int in=1;
			
			try{
				
				in=Integer.parseInt(sc);
				
			}catch(Exception e){
				
				in=1;
			}
			
			
			pCur(ilc, irc, true);
			
			
			switch(ic){
			
			case 0:  //跳转页
				
				iScreen=in-1;
				
				if(iScreen>iSn-1){
					
					iScreen=iSn-1;
				}
				
				ilc = iScreen * lineN;
				
				break;
				
			case 1: //跳转行
				
				ilc = in-1;
				
				if(ilc>iln-1){
					
					ilc=iln-1;
				}
				
				iScreen=ilc/lineN;
				
				break;
				
			case 2: //跳转段
					
				int sn=ssf.length;
				
				
				int i=0;
				
				for( i=0;i<sn;i++){
					
					if(ssf[i].startsWith((in-1)+":")){
						
						break;
					}
					
				}
				
				ilc=i; //第一个就是段所在的行数
				
				System.out.println("段="+i);
				
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
		
		if (c.getLabel().equals("删除")){
			
			
			Form fd=(Form)dis;
			
			
			int ic=((ChoiceGroup)fd.get(0)).getSelectedIndex(); //删除方式
			
			
			//////////////////////////////////////////定位要删除的文段
			
			
			/*String si = ssf[ilc].substring(0, ssf[ilc].indexOf(':'));
			
			String sj = ssf[ilc].substring(ssf[ilc].indexOf(':') + 1);
			
			int i = Integer.parseInt(si);
			
			int j = Integer.parseInt(sj);*/
			
			
			int i = getPL(ssf,ilc)[0];
			
			int j = getPL(ssf,ilc)[1];
			
			
			//String st = getlString(ilc);
			
			
			
			
			//////////////////////////////////////////
			
			
			switch(ic){
			
			case 0: //删除行
				
				saaText[i][j]=null; //删除行
				
				
				if(ilc>0){ //光标上移
					
					ilc--;
				}
				
				if(saaText[i]!=null){ //顺便处理段
					
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
				
			case 1: //删除段
					
				saaText[i]=null; //删除段
				
				int fl=getL(i,0);//首行所在行
				
				if(fl>0){
					
					ilc=fl;
				}
			
				break;	
				
			case 2: //删除全部
				
				sText=""; //删除全部
				
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
		
		if (c.getLabel().equals("执行")){//查找替换
			
			reFresh();//清除已经标记内容
			
			Form f=(Form)dis;
			
			int fn=f.size();
			
			for(int fi=3;fi<fn;fi++){ //去除打印内容
				
				try{
					
					f.delete(3); //会自动减少
					
				}catch(Exception e){
					
					e.printStackTrace();
				}
				
				
			}
			
			
			int iRep=((ChoiceGroup)f.get(0)).getSelectedIndex();//是否替换
			
			String ssrc=((TextField)f.get(1)).getString();//查找的东东
			
			String sdes=((TextField)f.get(2)).getString();//用于替换的东东
			
			
			/*替换全部*/
			
			
			
			if(iRep==2){ //替换全部,不用再再标记了
				
				
				sText=replaceString(sText,ssrc,sdes);
					
				pText(sText);
				
				
				fFR.append("替换完成");
				
				return;
			}
			
			
			/*查找*/
			
			
			Vector vsf=new Vector();//记录查找到的位置
			
			try{
				
				
				String[] saP=dowithString(sText); //获取每段文本的字符串
				
				int np=saP.length;
				
				for(int i=0;i<np;i++){
					
					String st=saP[i];
				
					//System.out.println(st+np);
					
					if(st.indexOf(ssrc)==-1){ //不包含目标字符串
						
						//System.out.println(st);
						
						continue;
						
					}else{ // 找到
						
						
						//System.out.println(st);
						
						int jn=saaText[i].length;
						
						System.out.println("jn="+jn);
						
						for(int j=0;j<jn;j++){
							
							
							String stf=saaText[i][j];
							
							int isf=-1;
							
							do{  ///一举干净
								
								
								isf=stf.indexOf(ssrc);
								
								if(isf!=-1){ //找到
									
									int sfn=ssf.length;
									
									int li=1;
									
									for(int k=0;k<sfn;k++){
										
										if(ssf[k].equals(i+":"+j)){
											
											li=k;
											
											break;
										}
									}
									
									vsf.addElement(li+":"+isf);
									
									fFR.append("找到:"+li+"行"+isf+"列");
									
									//System.out.println(li+":"+isf);
									
									stf=stf.substring(isf+ssrc.length());
									
								}
								
								
							}while(isf>-1);
							
							/////各在一行的处理
							
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
									
									fFR.append("找到:"+li+"行"+isf+"列");
									
									//System.out.println(li+"起始:"+isf);
								}
								
								
							}
							
							//System.out.println("here");
						
							
						} //内层for结束
						
						
						
					}
				
					
				}//外层for结束
				
				
				
				String[] saf=new String[vsf.size()];
				
				vsf.copyInto(saf);
				
				
				/*标记*/
				
				//System.out.println("行="+"列=");
				
				
				int an=saf.length;
				
				for(int i=0;i<an;i++){
					
					String si=saf[i];
					
					
					String sl = si.substring(0, si.indexOf(':')); //行
					
					String sr = si.substring(si.lastIndexOf(':') + 1);//列
					
					//int pi = Integer.parseInt(sp);
					
					int li = Integer.parseInt(sl);
					
					int ri = Integer.parseInt(sr);
					
					
					int pi = getPL(ssf,li)[0];
					
					int pp = getPL(ssf,li)[1];
					
					
					
					
					//System.out.println("行="+li+"列="+ri);
					
					
					////// 使用一定的颜色标记查找到的东东
					
					
					/*int cx = 0;
					
					cx = LW+this.f.stringWidth(saaText[pi][pp].substring(0, ri));
					
					int cy = li * charH;
				
					
					int w=this.f.stringWidth(ssrc);
					
					int h=charH-2;
					
					//gbuf.drawLine(cx, cy, cx, (cy + charH) - 2);
					
					gbuf.setColor(cBgd);
					
					gbuf.fillRect(cx, cy, w, h);
					
					
					gbuf.setColor(cFind);
					
					
					gbuf.drawString(ssrc, cx, li * charH, 20); //重新绘制字符
					
					gbuf.setColor(cBgd);
					
					//////////////////////////////////需考虑换行情况
					
					
					//boolean bnl=false;
					
					int nl=ri+ssrc.length()-saaText[pi][pp].length();
					
					if(nl>0){
						
						//bnl=true;
						
						
						//System.out.println("nl="+nl);
						
						String ss=saaText[pi][pp+1].substring(0, nl);
						
						//System.out.println("需要处理换行:"+ss);
						
						
						cx = LW;//开始就是
						
						cy = (li+1) * charH;
					
						
						w=this.f.stringWidth(ss);
						
						h=charH-2;
						
						//gbuf.drawLine(cx, cy, cx, (cy + charH) - 2);
						
						gbuf.setColor(cBgd);
						
						gbuf.fillRect(cx, cy, w, h);
						
						
						gbuf.setColor(cFind);
						
						
						gbuf.drawString(ss, cx, (li+1) * charH, 20); //重新绘制字符
						
						gbuf.setColor(cBgd);
					
						
					}*/
					
			
				}
				
				/*替换*/
				
				if(iRep==1){//替换第一个
					
					int isf=sText.indexOf(ssrc);
					
					if(isf>-1){
						
						sText=sText.substring(0,isf)+sdes+sText.substring(isf+ssrc.length());
					}
					
					try{
						
						
						f.set(3,new StringItem("","已替换"));
						
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
		
		if (c.getLabel().equals("导入")){//导入txt
			
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
						
						if(((ChoiceGroup)f.get(1)).isSelected(0)){//utf-8模式
							
							stext=new String(b,0,b.length,"utf-8"); 
							
						}else{
							
							stext=new String(b);
						}
						
						/////////////////////////////
						
						
						insertText(stext);
						
						/////////////////////////// 插入更新文本
					
						
						d.setCurrent(tc);//恢复显示
						
						
						
					}catch(Exception e){
						
						f.append("错误!"+e.getMessage());
						
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
		
		if (c.getLabel().equals("导入文本")){//导入txt
			

			Form f=new Form("导入文本");

			
			f.append(new TextField("导入路径",sFile,88888,0));
			
			f.append(new ChoiceGroup("编码",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
			
			//f.append(new ChoiceGroup("方式",ChoiceGroup.POPUP,new String[]{"插入当前位置","替换原字符串"},null));
			
			f.addCommand(new Command("导入",1,50));
			
			f.addCommand(new Command("取消",2,2));
			
			f.setCommandListener(this);
			
			d.setCurrent(f);
			
			return;
			
		}
		
		if (c.getLabel().equals("回车")||c.getLabel().equals("插入")){
			
			
			String sit=""; //要插入的文本
			
			
			if(c.getLabel().equals("插入")){
				
		
				sit=((TextBox)dis).getString();//插入文本框
				
				
			}else if(c.getLabel().equals("回车")){
				
				 sit="\n";
				
			}
			
			insertText(sit);
			
			d.setCurrent(this);
			
			return;
			
		}
		
		if (c.getLabel().equals("导出文本")){
			
			
			Form f=new Form("导出文本");
			
			
			f.append(new TextField("导出路径",sFile,88888,0));
			
			f.append(new ChoiceGroup("编码",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
			
			f.append(new ChoiceGroup("方式",ChoiceGroup.POPUP,new String[]{"附于文件末尾","重写文本文件"},null));
			
			f.addCommand(new Command("导出",1,51));
			
			f.addCommand(new Command("取消",2,2));
			
			f.setCommandListener(this);
			
			d.setCurrent(f);
			
			return;
			
		}
		
		if (c.getLabel().equals("导出")){
			
			
			final Displayable dp=dis;
			
			final textCanvas tc=this;
			
			
			new Thread(){
				
				
				public void run(){
					
					Form f=(Form)dp;
					
					String url=((TextField)f.get(0)).getString();
					
					FileConnection fc=null;
					
					OutputStream os=null;
					
					
					boolean bw=((ChoiceGroup)f.get(2)).isSelected(0);//附于末尾
					
					try{
						
						fc=(FileConnection)Connector.open(url,Connector.READ_WRITE);
						
						
						if(bw){//附于末尾
							
							
							if(!fc.exists()){
								
								fc.create();
								
							}
							
							os=fc.openOutputStream(fc.fileSize());
							
						}else{ //重写
							
							//System.out.println("重写");

							if(fc.exists()){
								
								fc.delete();
								
							}
							
							fc.create();
							
							os=fc.openOutputStream();
						}
						
					
						
						sFile=url;
						
						
					
						if(((ChoiceGroup)f.get(1)).isSelected(0)){//utf-8模式
							
							os.write(sText.getBytes("utf-8"));
							
						}else{
							
							os.write(sText.getBytes());
						}
						
						
						d.setCurrent(tc);//恢复显示
						
					}catch(Exception e){
						
						e.printStackTrace();
						
						f.append("错误!"+e.getMessage());
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
