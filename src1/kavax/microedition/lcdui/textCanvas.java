

package kavax.microedition.lcdui;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.*;


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
	
	public Image imglft;
	
	public Graphics glft;
	
	
	public Image imgrgt;
	
	public Graphics grgt;
	
	public Image imgcur;
	
	public Graphics gcur;
	
	public static int cLable = 0xaaaaaa;
	
	public static int clF = 0xFF00FF;//�к�����
	
	public static int cpF = 0x00FFFF;//�κ�����
	
	public static int cpB = 0xFF00bb;//�κŵ�ɫ
	
	public static int cBgd = 0xbbffff;
	
	public static int cFont = 255;
	
	public static int cCur = 0xff0000;
	
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
	
	public static int bordW = 2;
	
	public static int rbW = 4;
	
	public static int showW;
	
	public static int LW;//�к���ʾ���
	
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
	
	
	public static int ikDel=-8; //ɾ������ֵ
	
	////////////////////////////////////////Ԥ����˵� ����ģʽ
	
	public Command cBack=new Command("�˸�", 2, 1);
	
	public Command cExt=new Command("�����ı�", 4, 2);
	
	public Command cOK=new Command("ȷ�Ϸ���", 4, 1);
	
	
	////////////////////////////////////////Ԥ����˵� ���ģʽ
	
	public Command cC=new Command("����", 4, 1);
	
	public Command cX=new Command("����", 7, 2);
	

	////////////////////////////////////////
	
	
	public static String sC=""; //��������
	
	
	
	public textCanvas(Display d, Displayable dp, String stype,Object sob){
		
		setTitle("�ı��༭");
		
		this.d = d;
		
		this.dp = dp;
		
		this.sType=stype;
		
		this.sOb=sob;
		
		
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
		
		
		System.out.println(sText);
		
		
		addCommand(cBack);
		
		addCommand(cExt);
		
		addCommand(cOK);
		
		//addCommand(new Command("����", 2, 2));
		
		setCommandListener(this);
		
		W = super.getWidth();
		
		H = super.getHeight();
		
		f = Font.getFont(0, 0, 0);
		
		charW = f.charWidth('��');
		
		System.out.println(charW);
		
		charEW = f.charWidth(' ');
		
		charEW =1;
		
		System.out.println("��ĸ" + charEW);
		
		charH = f.getHeight();
		
		
		///////////////////////////////////////����������
		
		showW = W -rbW - bordW-4*charW-1;//������ʾ�к�(����Ϊ4λ)�����
		
		minN = showW / charW;
		
		int tl=sText.length()/minN+1; //Ԥ��������
		
		
		System.out.println("Ԥ��������"+tl);
		
		
		////////////////////����к���ʾ���
		
		if(iln>=1000){ //4λ
			
			LW=4*charW+2;
			
		}else if(iln>=100){//3λ
			
			LW=3*charW+2;
			
		}else if(iln>=10){//2λ
			
			LW=2*charW+2;
			
		}else{ ////1λ
			
			LW=1*charW+2;
			
		}
			
		///////////////////// ������ʾ���ַ�
		
		showW = W - rbW - 2*bordW-1-LW;//����
		
		maxN = showW / charEW;
		
		System.out.println("���" + maxN);
		
		minN = showW / charW;
		
		System.out.println("��С" + minN);
		
		lineN = (H)/charH;//����
		
		System.out.println("ÿҳ������"+lineN);
		
		
		pText(sText);
		
		pCur(0, 0, false);
	}

	public void pText(String s){
		
		
		
		/////////////////////////
		
		//vPara = new Vector();
		
		saaText=null;
		
		ssf=null;
		
		iln=iSn=0;
		
		//sl1=sl2=sr1=sr2=0;
		
		/////////////////////////
		
	
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
				
				if (saa != null){
				
					int nn = saa.length;
					
					saaText[i] = saa;
					
					for (int ii = 0; ii < nn; ii++){
					
						String sf = i + ":" + ii;
						
						//System.out.println(sf);
						
						lsV.addElement(sf);
						
						j++;
					}

				} else{
				
					System.out.println("saa=null");
				}
			}

		}
		
		
		
		ssf = new String[lsV.size()];
		
		lsV.copyInto(ssf);
		
		iln = j;
		
		if(ilc>iln){ //������
			
			ilc=iln;
		}
		
		System.out.println("iln=" + iln);
		
		
/*		////////////////////����к���ʾ���
		
		if(iln>=1000){ //4λ
			
			LW=4*charEW+2;
			
		}else if(iln>=100){//3λ
			
			LW=3*charEW+2;
			
		}else if(iln>=10){//2λ
			
			LW=2*charEW+2;
			
		}else{ ////1λ
			
			LW=1*charEW+2;
			
		}
		
		
		
		///////////////////// ������ʾ���ַ�
		
		showW = W - 2 * rbW - 2*bordW-1-LW;//������ʾ���
		
		maxN = showW / charEW;
		
		System.out.println("���" + maxN);
		
		minN = showW / charW;
		
		
		////////////////////
		
		System.out.println("��2")*/;
		
		
		if(j==0){ //�����ٲ�
			
			iSn = 1;
			
		}else{
			
			iSn = (j-1)/ lineN +1;
		}
		
		if(iScreen>iSn-1){//������
			
			ilc=ilc-(iScreen-iSn+1)*lineN;
			
			iScreen=iSn-1;
			
			
		}
		
		System.out.println("iSn=" + iSn);
		
		imgbuf = Image.createImage(W - rbW -bordW, iSn * H);
		
		gbuf = imgbuf.getGraphics();
		
		imgrgt = Image.createImage(rbW, iSn *H);
		
		grgt = imgrgt.getGraphics();
		
		clearS();
		
		pLable(sText);
		
		j = 0;
		
		n = saaText.length;
		
		int ip=0;
		
		for (int i = 0; i < n; i++){
		
			int nn = saaText[i].length;
			
			for (int ii = 0; ii < nn; ii++){
				
				if(ii==0){
					
					ip=j; //�ε�����
				}
				
				pString(saaText[i][ii], j++);
			}
				
			////���϶κ�	
			
			if(j<=iln){
				
				
				int h;  //=line * charH;
				
				int iSh=(ip)/lineN; //�ڼ���
				
				h=iSh*H+((ip)%lineN)* charH; //���ǻ��Ʊ߿�
				
				

				gbuf.setColor(cpB);
				
				gbuf.fillRect(0, h+2, LW-1,charH-2);
				
				gbuf.setColor(cpF);
				
			
				
				gbuf.drawString((i+1)+"", 0, h, Graphics.TOP| Graphics.LEFT); //���϶κ�
				
				gbuf.setColor(cBgd);
			}
			

		}

		//System.out.println("saa=" + saaText[0][0]);
	}

	public void pLable(String s){
	
		grgt.setColor(cLable);
		
		grgt.fillRect(0, 0, imgrgt.getWidth(), imgrgt.getHeight());
	}

	public void clearS(){
		
	
		gbuf.setColor(cBgd);
		
		
		gbuf.fillRect(0, 0, W, iSn * H);
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
		
		
		sText = saa2Text(saaText);
		
		pText(sText);
		
		pCur(ilc, irc, false);
		
		repaint();
		
		
	}

	public void pString(String s, int line){ //δ���
	
		gbuf.setColor(cFont);
		
		
		int h;  //=line * charH;
		
		int iSh=(line)/lineN; //�ڼ���
		
		h=iSh*H+((line)%lineN)* charH; //���ǻ��Ʊ߿�
		
		
		gbuf.drawString(s, LW, h, 20); //


		gbuf.setColor(clF);
		
		
		gbuf.drawString((line+1)+"", LW-2, h, Graphics.TOP| Graphics.RIGHT); //�����к�
		
		
	}

	public void pCur(int line, int row, boolean clear){
	
		try{
		
			/*if(!clear){
				
				pCur(ilc, irc, true);
			}
			*/
			
			String sl = getlString(line);
			
			int cx = 0;
			
			if (row >= sl.length())
				
				cx = LW+f.stringWidth(sl);
			
			else
				cx = LW+f.stringWidth(sl.substring(0, row));
			
			int cy = line * charH;
			
			
			int iSh=(line)/lineN; //�ڼ���
			
			cy=iSh*H+((line)%lineN)* charH; //���ǻ��Ʊ߿�
			
			
			
			int color = clear ? cBgd - cCur : cCur;
			
			gbuf.setColor(color);
			
			gbuf.drawLine(cx, cy+2, cx, (cy + charH) - 3);
			
			gbuf.setColor(cBgd);
		}
		
		catch (Exception e) { 
			
			e.printStackTrace();
			
			//ilc=irc=0;
			
		}
	}

	public void paint(Graphics g){
	
		g.setColor(cBgd);
		
		g.fillRect(0, 0, W, H);
		
		g.drawImage(imgbuf,0, -iScreen * H, 20);
		
		//g.drawImage(imgrgt, 0, 0, 20);
		
		g.drawImage(imgrgt, W - rbW, 0, 20);
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
		
		pText(sText);
		
		pCur(ilc, irc, false);
		
		repaint();
	}
	
	
	public void normalCmd(boolean normal){
		
		try{
			
			
			if(!normal){ //������ģʽ
			
				this.removeCommand(cOK);
				
				this.removeCommand(cExt);
				
				this.removeCommand(cBack);
				
				this.addCommand(cC);
				
				this.addCommand(cX);
				
			}else{
				
				this.addCommand(cOK);
				
				this.addCommand(cExt);
				
				this.addCommand(cBack);
				
				this.removeCommand(cC);
				
				this.removeCommand(cX);
				
			}
				
				
				
			
			
			
		}catch(Exception e){}
		
		
		
		
	}
	
	
	public static void toZero(){ //���ݹ���
		
		
		vPara = new Vector();
		
		saaText=null;
		
		ssf=null;
		
		iln=iScreen=iSn=0;
		
		sl1=sl2=sr1=sr2=0;
		
		ilc=irc=0;
		
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
			

			System.out.println("��ĩ");
		
		}
		
		if(l1>l2&&rmax==0){ //���� ��������
			
			lmax--;
			
			rmax=getlString(lmax).length();
			
			System.out.println("����");
		}
		
			
		
		
		StringBuffer sb=new StringBuffer();
		
		
		int lend=rmax;//ĩβ
		
		if(lmax>lmin){ //����һ��
			
			lend=getlString(lmin).length();
			
			sb.append(getlString(lmin).substring(rmin,lend)); //
			
			System.out.println("������");
			
			if(getPL(ssf,lmin)[0]!=getPL(ssf,lmin+1)[0]){ //˵���Ƕ�β
				
				System.out.println("������");
				
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
		
		
		
		
		System.out.println(sb.toString());
		
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
			

			System.out.println("��ĩ");
		
		}
		
		if(l1>l2&&rmax==0){ //���� ��������
			
			lmax--;
			
			rmax=getlString(lmax).length();
			
			System.out.println("����");
		}
		
		
		if(lmin==lmax&&rmin==rmax){ //��Ч
			
			haveCut=false;
			
			return;
		}
		
		haveCut = true;
		
		reFresh(); //�ػ�
	
		////////////////////////����
		
		gbuf.setColor(cCur);
		
		
		//		����ǰ����
		
		int h;  //=line * charH;
		
		int iSh=(lmin)/lineN; //�ڼ���
		
		h=iSh*H+((lmin)%lineN)* charH; //���ǻ��Ʊ߿�
		
		int lend=rmax;
		
		if(lmax>lmin){ //����һ��
			
			lend=getlString(lmin).length();
			
			System.out.println("������"+rmin);
			
			int cx=LW+f.stringWidth(getlString(lmin).substring(0, rmin));
			
			gbuf.drawString(getlString(lmin).substring(rmin,lend), cx, h, 20); //
			
			for(int i=lmin+1;i<lmax;i++){ //��������
				
				
				iSh=(i)/lineN; //�ڼ���
				
				h=iSh*H+((i)%lineN)* charH; //���ǻ��Ʊ߿�
				
				gbuf.setColor(cCur);
				
				gbuf.drawString(getlString(i), LW, h, 20); //
				
				
			}
			
			

			//		���ƺ����
			
		
			iSh=(lmax)/lineN; //�ڼ���
			
			h=iSh*H+((lmax)%lineN)* charH; //���ǻ��Ʊ߿�
			
			cx=LW;
			
			
			
			gbuf.drawString(getlString(lmax).substring(0,rmax), cx, h, 20); //
				
			
			
			
		}else{ //һ��
			
			
			int rleft=Math.min(rmin, rmax);
			
			int rright=Math.max(rmin,rmax);
			
			int cx=LW+f.stringWidth(getlString(lmin).substring(0, rleft));
			
			gbuf.drawString(getlString(lmin).substring(rleft,rright), cx, h, 20); //
			
		}
		
		
		
		
		
		
		
	
		repaint();
		
		
		
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
				
				System.out.println("�ҵ�"); 
				
				sb.append(stf.substring(0,isf)+sdes);
				
				stf=stf.substring(isf+ssrc.length());
				
				
				
			}else{
				
				sb.append(stf);
			}
			
			
		}while(isf>-1);
		
		
		return sb.toString();
	}
	
	
	public int getPara(int l, int r){
		
		int sfn=ssf.length;
		
		for(int k=0;k<sfn;k++){
			
			if(ssf[k].equals(l+":"+r)){ //ĩ�����ڶ�
				
				return k;
			}
			
			
		}
		
		return -1;
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
		
		
		switch (key){
		
		case Canvas.KEY_NUM1: // '1' //����
			
			if(doS){ //��ǽ���
				
				sr2=0;
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				return;
			}
			
			pCur(ilc, irc, true);
			
			irc=0;
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
			
		case Canvas.KEY_NUM3: // '3' //��ĩ
			
			
			if(doS){ //��ǽ���
				
				sr2=irc=getlString(ilc).length();
				
				drawSelect(sl1,sr1,sl2,sr2);
				
				return;
			}
			
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
			
			
			pCur(ilc, irc, true);
			
			iScreen=iSn-1;
			
			ilc=Math.min(iln-1, ((iScreen+1)*lineN-1));
			
			irc=getlString(ilc).length();
			
			pCur(ilc, irc, false);
					
			repaint();
			
			return;
			
			
/*		case Canvas.KEY_STAR: // '*' //������ʾ 
			
			reFresh();
			
			return;*/
			
		
			
		case Canvas.KEY_POUND: // '#' //�л�״̬
			
			
			doS =!doS; //�л�
			
			reFresh();
			
			haveCut=false;
			
			if(doS){
				
				setTitle("����ı�");
				
			}else{
				
				setTitle("�ı��༭");
								
			}
			
			normalCmd(!doS);
			
			sl1=sl2 = ilc;
			
			sr1 =sr2= irc;
			
			
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
					
			TextBox ti=new TextBox("����",sC,88888,0);
			
			ti.addCommand(new Command("�س�",1,42));
			
			ti.addCommand(new Command("txt",1,43));
			
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
				
				fFR.addCommand(new Command("�鿴",2,2));
				
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
						
					}else{
						
						ilc = l2 - 1;
					}
				
					
				}
					
				if(getlString(ilc).length()<irc){ //������
					
					irc=getlString(ilc).length();
				}
			
			
				
				break;

			case Canvas.DOWN : // '\006'
				
				if (ilc < l2 - 1){
					
					ilc++;
					
				}else{
					
					// ilc = l1;�ػ�
					
					
					if(iScreen!=iSn-1){ //�ҷ�ҳ
						
						//ilc++;
						
						iScreen++;
						
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
			
			pCur(ilc, irc, false);
			
			repaint();
			
			
			if(doS){ //��ǽ���
				
				sl2=ilc;
				
				sr2=irc;
				
				drawSelect(sl1,sr1,sl2,sr2);
			
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
			
			pCur(ilc, irc, true);
			
			ilc = iScreen * lineN;
			
			irc = 0;
			
			pCur(ilc , irc, false);
			
			repaint();
			
			return;

		case Canvas.KEY_NUM4: // '4'
						
			iScreen = 0;
			
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

		
	}	

	public static String saa2Text(String saa[][]){
	
		StringBuffer sb = new StringBuffer();
		
		int n = saa.length;
		
		for (int i = 0; i < n; i++){
			
			
			if(saa[i]==null){ //��Ч�Թ�
				
				System.out.println("����Ϊ���Թ�");
				
				continue;
			}
		
			int nn = saa[i].length;
			
			for (int j = 0; j < nn; j++){
				
				
				if(saa[i][j]==null){ //��Ч�Թ�
					
					System.out.println("����Ϊ���Թ�");
					
					continue;
				}
				
				sb.append(saa[i][j]);
				
			}

			if (i < n-1){
				
				
				sb.append("\n");
				
				
				System.out.println("i="+i);
			}
				
				
			
		}
		
		System.out.println("���"+sb.toString().endsWith("\n"));

		return sb.toString();
		
		
	}

	public void commandAction(Command c, Displayable dis){
		
		
		
		if (c.getLabel().equals("����")){
			
			sC=getSelect(sl1,sr1,sl2,sr2);
			
			int ic=sText.indexOf(sC);
			
			if(ic>-1){			
				
				System.out.println("����");
				
				sText=sText.substring(0,ic)+sText.substring(ic+sC.length());
			}
			
			pText(sText);
			
			pCur(ilc, irc, false);
			
			repaint();
			
			return;
			
		}
		
		
		
		if (c.getLabel().equals("����")){
			
			
			sC=getSelect(sl1,sr1,sl2,sr2);
			
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
					
				}else{ //��һ��
					
					try{  //ɾ��
						
						if(st.length()>0){
							/*
							st = st.substring(0, st.length()-1);
							
							saaText[i][j] = st;*/
							
						}else{
							
							saaText[i]=null;
						}
							
						if(saaText.length==i+1){
							
							//System.out.println("oh");
						
							//ɶҲ����
						}
						
						else if(saaText[i+1].length==1&&getlString(ilc).length()==0){ //ɾ����
							
							
							//System.out.println("ɾ����"+(i+1));
							
							//System.out.println("��"+saaText[i+2].length);
							
							saaText[i+1]=null;
						}
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
				
			}
			
			//System.out.println(sText+"\n\n");
			
			//System.out.println(saa2Text(saaText));
		
			
			reFresh();//����
			
	
			
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
		
		if (c.getLabel().equals("����")){
			
			toZero();
			
			ilc=irc=0;
			
			d.setCurrent(dp);
		}
		
		if (c.getLabel().equals("ȡ��")||c.getLabel().equals("�鿴")){
			
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
				
				if(ilc>iln-1){
					
					ilc=iln-1;
				}
				
				iScreen=ilc/lineN;
				
				break;	
			
			
			}
		
			
			irc = 0;
			
			pCur(ilc, irc, false);
			
			repaint();
			
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
				
				break;
				
			case 1: //ɾ����
					
				saaText[i]=null; //ɾ����
				
				break;	
				
			case 2: //ɾ��ȫ��
				
				sText=""; //ɾ��ȫ��
				
				pText(sText);
				
				pCur(ilc, irc, false);
				
				repaint();
				
				d.setCurrent(this);
				
				return;
			
			
			}
		
			sText = saa2Text(saaText);
			
			pText(sText);
			
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
									
									System.out.println(li+":"+isf);
									
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
									
									System.out.println(li+"��ʼ:"+isf);
								}
								
								
							}
							
							//System.out.println("here");
						
							
						} //�ڲ�for����
						
						
						
					}
				
					
				}//���for����
				
				
				
				String[] saf=new String[vsf.size()];
				
				vsf.copyInto(saf);
				
				
				/*���*/
				
				System.out.println("��="+"��=");
				
				
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
					
					
					
					
					System.out.println("��="+li+"��="+ri);
					
					
					////// ʹ��һ������ɫ��ǲ��ҵ��Ķ���
					
					
					int cx = 0;
					
					cx = LW+this.f.stringWidth(saaText[pi][pp].substring(0, ri));
					
					int cy = li * charH;
				
					
					int w=this.f.stringWidth(ssrc);
					
					int h=charH-2;
					
					//gbuf.drawLine(cx, cy, cx, (cy + charH) - 2);
					
					gbuf.setColor(cBgd);
					
					gbuf.fillRect(cx, cy, w, h);
					
					
					int color = cCur;
					
					gbuf.setColor(color);
					
					
					gbuf.drawString(ssrc, cx, li * charH, 20); //���»����ַ�
					
					gbuf.setColor(cBgd);
					
					//////////////////////////////////�迼�ǻ������
					
					
					//boolean bnl=false;
					
					int nl=ri+ssrc.length()-saaText[pi][pp].length();
					
					if(nl>0){
						
						//bnl=true;
						
						
						System.out.println("nl="+nl);
						
						String ss=saaText[pi][pp+1].substring(0, nl);
						
						System.out.println("��Ҫ������:"+ss);
						
						
						cx = LW;//��ʼ����
						
						cy = (li+1) * charH;
					
						
						w=this.f.stringWidth(ss);
						
						h=charH-2;
						
						//gbuf.drawLine(cx, cy, cx, (cy + charH) - 2);
						
						gbuf.setColor(cBgd);
						
						gbuf.fillRect(cx, cy, w, h);
						
						
						color = cCur;
						
						gbuf.setColor(color);
						
						
						gbuf.drawString(ss, cx, (li+1) * charH, 20); //���»����ַ�
						
						gbuf.setColor(cBgd);
					
						
					}
					
			
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
							
							MIDtxt.sFile=url;
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
		
		if (c.getLabel().equals("txt")){//����txt
			

			Form f=new Form("�����ı�");
			
			String sFile="file:///E:/text.txt";
			
			try{
				
				sFile=MIDtxt.sFile;
				
			}catch(Exception e){
				
				sFile="file:///doot1/text.txt";
				
			}
			
			//sFile="file:///E:/text.txt"; //������
			
			
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
			
			String sFile="file:///E:/text.txt";
			
			try{
				
				sFile=MIDtxt.sFile;
				
			}catch(Exception e){
				
				sFile="file:///doot1/text.txt";
				
			}
			
			//sFile="file:///E:/text.txt"; //������
			
			
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
							
							System.out.println("��д");

							if(fc.exists()){
								
								fc.delete();
								
							}
							
							fc.create();
							
							os=fc.openOutputStream();
						}
						
						
						try{
							
							
						}catch(Exception e){
							
							MIDtxt.sFile=url;
						}
						
					
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
