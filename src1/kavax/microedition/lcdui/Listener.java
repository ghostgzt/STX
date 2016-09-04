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
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;

import com.nokia.mid.ui.DeviceControl;

public class Listener implements CommandListener,ItemCommandListener,ItemStateListener{

	
public static String sCut="\n����rsRkΪ��������ɸ�����������ı���[��������]\n"; //����������
	
	
	public static int iMax=88888;///�ı�������
	
	
	public static int iLen=0;///���Ƴ���
	
	
	public static boolean hideCP=false;
	
	
	public static String sFile="file:///E:/text.txt";
	
	
	public static String sRms="file:///E:/rms.db";
	
	
	public static String sHide="��";
	
	
	public static String sSet="����="+iMax+"���Ƴ���="+iLen+"����="+sHide+"�ļ�="+sFile; //�������ݴ�
	
	
	//public static Command cClear,cCopy,cPaste,cSet,cImt,cExt,cCnf;
	
	private String oldstring="";
	
	private TextBox tshow;
	
	//private int is1,is2; //��һ�ڶ����λ��
	
	private Displayable lastD; //��һ����ʾ����
	
	
	public static ItemCommandListener iicl;
	
	public static CommandListener ccl;
	
	public static int iLight=50;
	
	
	private static TextFielk tfk;
	
	private static TextBok tbk;
	
	private static boolean haveRMS=true;
	
	public static Command cCopy=new Command("_����",1,2);
	
	public static Command cPaste=new Command("_ճ��",1,3);
	
	public static Command cClear=new Command("_���",1,4);
	

	public static Command cImt=new Command("_�����ı�",1,7);
	
	public static Command cExt=new Command("_�����ı�",1,8);		
	
	public static Command cFn=new Command("_����",1,5);
	
	
	
	public static String sab="1.����/ճ��/���\n2.����/�����ı�\n3.RMS����/����\n4.ŵ�����ȿ���\n5.ϵͳ��Ϣ�鿴\n(���ܷ�������!)";
			
	
	//public static Command cCnf=new Command("_OK",1,1);
	
	//public static Command cOK;
	
	/////////////////////////////
	
	
	public Listener(){
		
		
	}
	
	
	public void commandAction(Command cd, final Displayable d){
		
		//System.out.println("��������ı���ť");
		
		if(d.equals(tshow)|| d instanceof Form){
			
			TextBox t=null;
			
			Form f=null;
		
			if(d instanceof TextBox){
				
				System.out.println("��ô");
				
				t=(TextBox)d;
			}
			else if(d instanceof Form){
				
				f=(Form)d;
			}
			
			int ic=cd.getPriority();
			
			
			
			switch(ic){
			
			case 1: //
				
				int is1=t.getCaretPosition();//��ǰ���λ��
				
				int ix=t.getString().indexOf('��'); //ԭ��λ��
				
				
				if(-1<=ix-is1 && ix-is1<1){
					
					return; //λ����ͬ����������
					
				}else{
					
					String snn=t.getString();
					
					snn=snn.substring(0, ix)+snn.substring(ix+1);
					
					if(is1>ix){
						
						is1=is1-1;
					}
					
					snn=snn.substring(0, is1)+"��"+snn.substring(is1);
					
					t.setString(snn);
					
				}
				
				
				break;
				
			case 2: //����
				
				int is2=t.getCaretPosition();//�ڶ����λ��
				
				is1=t.getString().indexOf('��');
				
				int i1=Math.min(is1, is2);
				
				int i2=Math.max(is1, is2);
				
				if(is1<is2){
					
					i2=i2-1;
				}
				
				if(-1<=is1-is2 && is1-is2<=1){//���λ����ͬ
					
					if(is1>=oldstring.length()){ ///// ���λ����ĩβ����ȫ��
						
						sCut=oldstring;
						
						
					}
					else if(iLen!=0 && iLen<oldstring.length()-is1){ ///// ����һ�������ַ���
						
						sCut=oldstring.substring(is1,is1+iLen);
						
					}else if(iLen==0 || iLen>=oldstring.length()-is1){ /////����֮�������ַ���
						
						
						sCut=oldstring.substring(is1);
								
					}
					
				}else{ //���渴��
					
					sCut=oldstring.substring(i1, i2);
				}
				
				
				
				rmsDo(true);
				
				
				//System.out.println("���ƵĶ���:"+sCut+is1+is2);
				
				MIDtxt.dp.setCurrent(lastD);//�ָ���ʾ
				
				break;
			
				
			case 3: //ȡ��
				
				MIDtxt.dp.setCurrent(lastD);//�ָ���ʾ
				
				if(cd.getCommandType()==7){ //��Ҫ�洢����
					
					rmsDo(true);//д������
				}
				
				break;
				
			case 4: //����ȷ��
				
				String oldsSet=sSet;
				
				int oldiMax=iMax;
				
				int oldiLen=iLen;
				
				String oldsFile=sFile;
				
				
				
				try{
					
					String sset=t.getString();//��ȡ�����ַ���
					
					int p1=sset.indexOf("���Ƴ���=");
					
					iMax=Integer.parseInt(sset.substring("����=".length(), p1));
					
					try{
						
						tfk.setMaxSize(iMax);
						
					}catch(Exception e){}
					
					finally{
						
						try{
							
							tbk.setMaxSize(iMax);
							
						}catch(Exception e){}
						
					}
					
					
					int p2=sset.indexOf("����=");
					
					iLen=Integer.parseInt(sset.substring(p1+"���Ƴ���=".length(),p2));
					
					int p3=sset.indexOf("�ļ�=");
					
					sHide=sset.substring(p2+"����=".length(),p3);
					
					hideCP=sHide.equalsIgnoreCase("��");
					
					sHide=hideCP?"��":"��";
					

					
					try{
						
						hideMenu(tfk,hideCP); //�����˵�
											
						
					}catch(Exception e){}
					
					finally{
						
						
						try{
							
							hideMenu(tbk,hideCP); //�����˵�
							
						}catch(Exception e){}
						
					}
					
					
					int p4=sset.lastIndexOf('=');
					
					sFile=sset.substring(p4+1);
					
					sSet="����="+iMax+"���Ƴ���="+iLen+"����="+sHide+"�ļ�="+sFile; //�������ݴ�
					
					System.out.println(sSet);
					
					
					
					rmsDo(true);
					
					//tfk.setString(oldstf+"_��");
					
					MIDtxt.dp.setCurrent(lastD);//�ָ���ʾ
					
					
				}catch(Exception e){
					
					e.printStackTrace();
					
					sSet=oldsSet; ////����ԭԭ����
					
					iLen=oldiLen;
					
					iMax=oldiMax;
					
					sFile=oldsFile;
									
					t.setString(t.getString()+"_��");
				}
			
			
				break;
				
			case 7://�����ı�
				
				
				
				new Thread(){
					
					
					public void run(){
						
						Form f=(Form)d;
						
						String url=((TextField)f.get(0)).getString();			
						
						FileConnection fc=null;
						
						InputStream is=null;
						
//						try{
//							
//							fc=(FileConnection)Connector.open("file:///x/x.x");
//							
//							
//						}catch(Exception e){}
						
						try{
							
							fc=(FileConnection)Connector.open(url);
							
							is=fc.openInputStream();
							
							byte[] b=new byte[(int)fc.fileSize()];
							
							is.read(b);
							
							sFile=url;
							
							sSet="����="+iMax+"���Ƴ���="+iLen+"����="+hideCP+"�ļ�="+sFile; //�������ݴ�
							
							
							rmsDo(true);
							
							String stext="";
							
							if(((ChoiceGroup)f.get(1)).isSelected(0)){//utf-8ģʽ
								
								stext=new String(b,0,b.length,"utf-8"); 
								
							}else{
								
								stext=new String(b);
							}
							
							//System.out.println("tbk="+tbk);
							
							if(((ChoiceGroup)f.get(2)).isSelected(0)){ //ֱ�Ӳ���
								
								
								try{
									
									int in=tfk.getCaretPosition();
									
									tfk.insert(stext, in);
									
								
								}catch(Exception e){}
								
								finally{
									

									try{
										
										int in=tbk.getCaretPosition();
										
										tbk.insert(stext, in);
									
									}catch(Exception e){}
									
									
								}
								
								
							}else{
								
								try{
									
									tfk.setString(stext);
									
							
								}catch(Exception e){}
								
								finally{
									
									
									try{
										
										tbk.setString(stext);
										
									}catch(Exception e){}
									
								}
								
							}
								
							MIDtxt.dp.setCurrent(lastD);//�ָ���ʾ
							
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
			
			
				break;	
			
			case 8://�����ı�
				
				
				new Thread(){
					
					
					public void run(){
						
						Form f=(Form)d;
						
						String url=((TextField)f.get(0)).getString();
						
						FileConnection fc=null;
						
						OutputStream os=null;
						
//						try{
//							
//							 fc=(FileConnection)Connector.open("file:///x/x.x");
//							
//							
//						}catch(Exception e){}
						
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
							
					
							
							
							String sout="�������ı�";
							
							try{
								
								sout=tfk.getString();
							
							}catch(Exception e){}
							
							finally{
								
								try{
									
									sout=tbk.getString();
								
								}catch(Exception e){}
								
							}
							
							
							
						
							if(((ChoiceGroup)f.get(1)).isSelected(0)){//utf-8ģʽ
								
								os.write(sout.getBytes("utf-8"));
								
							}else{
								
								os.write(sout.getBytes());
							}
							
							
							sFile=url;
							
							sSet="����="+iMax+"���Ƴ���="+iLen+"����="+hideCP+"�ļ�="+sFile; //�������ݴ�
							
							
							
							
							rmsDo(true);
							
							//tfk.setString(oldstring+"_��"); ////�ָ��ַ���
							
							MIDtxt.dp.setCurrent(lastD);//�ָ���ʾ
							
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
			
			
				break;
				
				
			case 11: //��������
				
				System.out.println("sSet="+sSet);
				
				tshow=new TextBox("��������",sSet,this.iMax,0);
				
				tshow.setCommandListener(this);
				
				tshow.addCommand(new Command("ȷ��",1,4));
							
				tshow.addCommand(new Command("ȡ��",7,3));
			
				MIDtxt.dp.setCurrent(tshow);
				
				break;
				
				
			case 12: //RMS����
				
				
				Form frms=new Form("RMS����");
				
				frms.append(new TextField("",sRms,1024,0));
				
				frms.append(new ChoiceGroup("",1,new String[]{"����","����"},null));
				
				frms.addCommand(new Command("ȷ��",1,21));
				
				frms.addCommand(new Command("����",7,3));
				
				frms.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(frms);
				
				break;
				
			
			case 13: //���ȵ���
				
				Form fl=new Form("���ȵ���");
				
				Gauge g1=new Gauge("",true,100,iLight);
				
				//g1.setValue(iLight);
				
				g1.setLayout(Gauge.LAYOUT_CENTER);
				
				fl.append(g1);
				
				Gauge g2=new Gauge("",true,10,iLight/10);
				
				g2.setLayout(Gauge.LAYOUT_CENTER);
				
				//g2.setValue(iLight/10);
				
				fl.append(g2);
				
				fl.addCommand(new Command("����",7,3));
				
				fl.setCommandListener(this);
				
				fl.setItemStateListener(this);
				
				MIDtxt.dp.setCurrent(fl);
				
				break;
				
			case 14: //�򵥱༭
				
				break;
				
			case 15: //ϵͳ��Ϣ
				
				Form fs=new Form("ϵͳ��Ϣ");
				
				fs.append("��ܰ��ʾ:�������ݽ����ο�");
				
				fs.append("\n�ֻ��ͺ�:"+System.getProperty("microedition.platform"));
				
				fs.append("\n�����˴�:"+Runtime.getRuntime().totalMemory());
				
				fs.append("\n�����˴�:"+Runtime.getRuntime().freeMemory());
				
				//fs.append("\n�����˴�:"+Display);
				
				
				
				fs.append("\nMIDP�汾:"+System.getProperty("microedition.profiles"));

				fs.append("\nCLDC�汾:"+System.getProperty("microedition.configuration"));
				
				
				fs.append("\n֧�ֲ�ɫ:"+MIDtxt.dp.isColor());
				
				fs.append("\n��ɫ�Ҷ�:"+MIDtxt.dp.numColors());
				
				fs.append("\n֧��͸��:"+MIDtxt.dp.numAlphaLevels());
				
				fs.append("\n֧�ֱ���:"+MIDtxt.dp.flashBacklight(0));
				
				fs.append("\n֧����:"+MIDtxt.dp.vibrate(0));
				
				
				fs.append("\n֧�ֻ���:"+System.getProperty("supports.mixing"));
				
				fs.append("\n֧��¼��:"+System.getProperty("supports.audio.capture"));
				
				fs.append("\n֧��¼��:"+System.getProperty("supports.video.capture"));
				
				fs.append("\n��Ƶ��ʽ:"+System.getProperty("audio.encodings"));
				
				fs.append("\n��Ƶ��ʽ:"+System.getProperty("video.encodings"));
					
				fs.append("\n������ʽ"+System.getProperty("video.snapshot.encodings"));
				
				fs.append("\n��ý֧��:"+System.getProperty("streamable.contents"));
				
				
				
				
				fs.append("\n��������:"+System.getProperty("wireless.messaging.sms.smsc"));
							
				fs.append("\nͼƬĿ¼:"+System.getProperty("fileconn.dir.photos"));
				
				fs.append("\n��ƵĿ¼:"+System.getProperty("fileconn.dir.videos"));
							
				fs.append("\n����Ŀ¼:"+System.getProperty("fileconn.dir.tones"));
				
				fs.append("\n�洢Ŀ¼:"+System.getProperty("fileconn.dir.memorycard"));
				
				fs.append("\n˽��Ŀ¼:"+System.getProperty("fileconn.dir.private"));
				
				fs.append("\n�ָ�����:"+System.getProperty("file.separator"));
				
				
				
				fs.addCommand(new Command("����",7,3));
				
				fs.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(fs);
				
				break;
				
			case 16: //��ֵ����
				
				//keyCanvas kc=new keyCanvas(lastD);
				
				//kc.setTitle("��ֵ����(����0����)");
			
				
				//kc.addCommand(new Command("����",2,3));
				
				//kc.setCommandListener(this);
				
				//MIDtxt.dp.setCurrent(kc);
				
				//break;
				
			case 21: //RMS����ȷ��
				
				
				try{
					
					
				
					String url=((TextField)f.get(0)).getString();
					
					if(((ChoiceGroup)f.get(1)).isSelected(0)){ //����
						
						rmsDo(true);//д�뵱ǰ����
						
						rmsOutput(url);
						
						f.append("�����ɹ�!\n");
						
						
					}else{ //����
						
						rmsInput(url);
						
						f.append("����ɹ�!\n");
					
					}
					
					sRms=url;
					
					
				}catch(Exception e){
					
					
					f.append("����ʧ��!\n");
					
				}
				
				
				
				break;
			
			}
			
			
		}else { //���ı���
			
						
			
			try{
				
				
				tbk=((TextBok)d);
				
				lastD=MIDtxt.dp.getCurrent();
				
			
				if(!sameCmd(cClear,cd)&&cd.getLabel().indexOf("���")!=-1){ ////�����������գ����ر���Ĳ˵�
					
					tbk.removeCommand(cClear);
					
				}	
				
				
				
			}catch(Exception e){}
			
			
			
			if(!cd.equals(tbk.cOK)&&!sameCmd(cCopy,cd)&&!sameCmd(cPaste,cd)&&!sameCmd(cClear,cd)&&!sameCmd(cFn,cd)
					
					
					&&!sameCmd(cImt,cd)&&!sameCmd(cExt,cd)&&!sameCmd(tbk.cCnf,cd)){ /////ԭ���˵�
				
				
				ccl.commandAction(cd, d);
				
				
				return;
			}
			
			
			if(sameCmd(tbk.cCnf,cd)){
				
				System.out.println("ȷ��");
				
				ccl.commandAction(tbk.cOK, d);
				
				
				
				return;
			}
			
			
			
			int p=cd.getPriority();
			
			
			switch(p){
			
			
			
			case 2: /////����
				
				
				oldstring=tbk.getString(); //������ַ���
				
				int is1=tbk.getCaretPosition();//��һ���λ��
				
				String stemp=oldstring.substring(0, is1)+"��"+oldstring.substring(is1);
				
				tshow=new TextBox("���ɸ���",stemp,iMax+1,0);
				
				tshow.setCommandListener(this);
				
				tshow.addCommand(new Command("�������",1,1));
				
				tshow.addCommand(new Command("�����ı�",2,2));
				
				tshow.addCommand(new Command("ȡ��",1,3));
				
				MIDtxt.dp.setCurrent(tshow);
				
				
				break;
				
			case 3: /////ճ��
				
						
				int ii=tbk.getCaretPosition();
	        	
	        	String s=tbk.getString().substring(0, ii)+sCut+tbk.getString().substring(ii);
	        	
	        	//System.out.println(s+ii);
	        	
	        	tbk.setString(s);
				
				break;
				
			case 4://///���
				
				tbk.setString("");
				
				break;
			
				
			case 5://////����
				
				
				Form fs=new Form("ʵ�ù���");
				
				fs.append(sab);
				
				fs.addCommand(new Command("��������",1,11));
				
				fs.addCommand(new Command("RMS����",1,12));
				
				fs.addCommand(new Command("���ȿ���",1,13));
				
				fs.addCommand(new Command("�򵥱༭",1,14));
				
				fs.addCommand(new Command("ϵͳ��Ϣ",1,15));
				
				fs.addCommand(new Command("��ֵ����",1,16));
				
				fs.addCommand(new Command("����",2,3));
				
				fs.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(fs);
				
				
				break;
				
				
			case 7: //�����ı�
				
				
				Form f=new Form("��������");
				
				f.append(new TextField("����·��",sFile,this.iMax,0));
				
				f.append(new ChoiceGroup("����",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
				
				f.append(new ChoiceGroup("��ʽ",ChoiceGroup.POPUP,new String[]{"���뵱ǰλ��","�滻ԭ�ַ���"},null));
				
				f.addCommand(new Command("ȷ��",1,7));
				
				f.addCommand(new Command("ȡ��",2,3));
				
				f.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(f);
				
				break;	
				
			case 8://�����ı�
				
				f=new Form("��������");
				
				f.append(new TextField("����·��",sFile,this.iMax,0));
				
				f.append(new ChoiceGroup("����",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
				
				f.append(new ChoiceGroup("��ʽ",ChoiceGroup.POPUP,new String[]{"�����ļ�ĩβ","��д�ı��ļ�"},null));
				
				f.addCommand(new Command("ȷ��",1,8));
				
				f.addCommand(new Command("ȡ��",2,3));
				
				f.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(f);
				
				break;	
				

			
			}
			
		
		}
		
	}
	
	
	public void commandAction(Command cd, Item item){
		
	
		//System.out.println("��������ı���f��ť");
		
		Fork fi=(Fork)MIDtxt.dp.getCurrent(); //���������Fork
		
		
		try{
			
			
			tfk=((TextFielk)item);
			
			lastD=MIDtxt.dp.getCurrent();
			
			
			////////////////////////////////////////////////
			
			
			if(sameCmd(tfk.cOK,new Command("_OK",1,1))){
				

				//System.out.println("����fork:"+fi.getTitle()+"��һ�˵�"+fi.cFst.getLabel());
				
				tfk.cOK=fi.cFst;
				
				tfk.addCommand(tfk.cCnf=new Command(tfk.cOK.getLabel(),1,1));
				
				fi.removeCommand(fi.cFst);
			}
			
			////////////////////////////////////////////////
			
		
			if(!sameCmd(cClear,cd)&&cd.getLabel().indexOf("���")!=-1){ ////�����������գ����ر���Ĳ˵�
				
				tfk.removeCommand(cClear);
				
			}	
			
			
			
			
		}catch(Exception e){}
		
		System.out.println("���"+cd.getLabel()+cd.getCommandType()+cd.getPriority());
		
		
		if(!sameCmd(cCopy,cd)&&!sameCmd(cPaste,cd)&&!sameCmd(cClear,cd)&&!sameCmd(cFn,cd)
				
				
				&&!sameCmd(cImt,cd)&&!sameCmd(cExt,cd)&&!sameCmd(tfk.cCnf,cd)){ /////ԭ���˵�
			
			iicl.commandAction(cd, item);
			
			
			return;
		}
		
		//System.out.println("ȷ��");
		
		if(sameCmd(tfk.cCnf,cd)){
			
			System.out.println("�����ת�ƵĲ˵�Ŷ����"+fi);
			
			try{
				
				
				//iicl.commandAction(tfk.cOK, item); // �ı���˵�
								
				fi.cl.commandAction(tfk.cOK, fi); //����˵�
				
				
				
			}catch(Exception e){
				
				
				//fi.cl.commandAction(tfk.cOK, fi); //����˵�
								
				//iicl.commandAction(tfk.cOK, item); // �ı���˵�
				
				System.out.println(fi.cl);
			}
			
		
			
			return;
		}
		
		
		
		//System.out.println("���ı���f��ť");
		
		int p=cd.getPriority();
		
		
		
	
		switch(p){
		
		
		
		
		case 2: /////����
			
			
			oldstring=tfk.getString(); //������ַ���
			
			int is1=tfk.getCaretPosition();//��һ���λ��
			
			String stemp=oldstring.substring(0, is1)+"��"+oldstring.substring(is1);
			
			tshow=new TextBox("���ɸ���",stemp,this.iMax+1,0);
			
			tshow.setCommandListener(this);
			
			tshow.addCommand(new Command("�������",1,1));
			
			tshow.addCommand(new Command("�����ı�",2,2));
			
			tshow.addCommand(new Command("ȡ��",1,3));
			
			MIDtxt.dp.setCurrent(tshow);
			
			
			break;
			
		case 3: /////ճ��
			
					
			int ii=tfk.getCaretPosition();
        	
        	String s=tfk.getString().substring(0, ii)+sCut+tfk.getString().substring(ii);
        	
        	//System.out.println(s+ii);
        	
        	tfk.setString(s);
			
			break;
			
		case 4://///���
			
			tfk.setString("");
			
			break;
	
			
			
		case 5://////����
			
			Form fs=new Form("ʵ�ù���");
			
			fs.append(sab);
			
			fs.addCommand(new Command("��������",1,11));
			
			fs.addCommand(new Command("RMS����",1,12));
			
			fs.addCommand(new Command("���ȿ���",1,13));
			
			fs.addCommand(new Command("�򵥱༭",1,14));
			
			fs.addCommand(new Command("ϵͳ��Ϣ",1,15));
			
			fs.addCommand(new Command("��ֵ����",1,16));
			
			fs.addCommand(new Command("����",2,3));
			
			fs.setCommandListener(this);
			
			MIDtxt.dp.setCurrent(fs);
			
			
			break;
			
		case 7: //�����ı�
			
			
			Form f=new Form("��������");
			
			f.append(new TextField("����·��",sFile,this.iMax,0));
			
			f.append(new ChoiceGroup("����",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
			
			f.append(new ChoiceGroup("��ʽ",ChoiceGroup.POPUP,new String[]{"���뵱ǰλ��","�滻ԭ�ַ���"},null));
			
			f.addCommand(new Command("ȷ��",1,7));
			
			f.addCommand(new Command("ȡ��",2,3));
			
			f.setCommandListener(this);
			
			MIDtxt.dp.setCurrent(f);
			
			break;	
			
		case 8://�����ı�
			
		
			f=new Form("��������");
			
			f.append(new TextField("����·��",sFile,this.iMax,0));
			
			f.append(new ChoiceGroup("����",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
			
			f.append(new ChoiceGroup("��ʽ",ChoiceGroup.POPUP,new String[]{"�����ļ�ĩβ","��д�ı��ļ�"},null));
			
			f.addCommand(new Command("ȷ��",1,8));
			
			f.addCommand(new Command("ȡ��",2,3));
			
			f.setCommandListener(this);
			
			MIDtxt.dp.setCurrent(f);
			
			break;	
		
		}
	
		
	}
	
	
	public static void hideMenu(TextBox tbx,boolean hide){ //�����˵�
		
		if(hide){ /////////  ��Ҫ���ز˵�
			
			tbx.removeCommand(cClear);
			
			tbx.removeCommand(cCopy);
			
			tbx.removeCommand(cPaste);
			
			
		}else{
			
			tbx.addCommand(cClear);
			
			tbx.addCommand(cCopy);
			
			tbx.addCommand(cPaste);
			
		
		}
		
	}
	
	public static void hideMenu(TextFielk tfk,boolean hide){ //�����˵�
		
		if(hide){ /////////  ��Ҫ���ز˵�
			
			tfk.removeCommand(cClear);
			
			tfk.removeCommand(cCopy);
			
			tfk.removeCommand(cPaste);
		
		}else{
			
			tfk.addCommand(cClear);
			
			tfk.addCommand(cCopy);
			
			tfk.addCommand(cPaste);
			
			
		}
		
	}
	
	public static void rmsDo(boolean write){ ///rms����
		
		try{
			
			if(write){ ////д
				
				
				RecordStore rms = RecordStore.openRecordStore("kavaXText", true, RecordStore.AUTHMODE_ANY, true); 
			
				System.out.println(rms);
				
				
				if(!haveRMS){
					
					
					System.out.println("haveRMS"+haveRMS);
					
					rms.addRecord(sSet.getBytes("utf-8"), 0, sSet.getBytes("utf-8").length);
					
					rms.addRecord(sRms.getBytes("utf-8"), 0, sRms.getBytes("utf-8").length);
					
					rms.addRecord((iLight+"").getBytes(), 0, (iLight+"").getBytes("utf-8").length);
					
					System.out.println("����"+sSet);
					
				
				}else{
					
					System.out.println(sSet);
					
					rms.setRecord(1,sSet.getBytes("utf-8"), 0, sSet.getBytes("utf-8").length);
					
					rms.setRecord(2,sRms.getBytes("utf-8"), 0, sRms.getBytes("utf-8").length);
					
					rms.setRecord(3,(iLight+"").getBytes(), 0, (iLight+"").getBytes("utf-8").length);
					
				}
				
								
				haveRMS=true;
				
				rms.closeRecordStore();  
				
			}else{ 
				
				RecordStore rms = RecordStore.openRecordStore("kavaXText",true); 
				
				sSet=(new String(rms.getRecord(1),0,rms.getRecord(1).length,"utf-8"));
				
				sRms=(new String(rms.getRecord(2),0,rms.getRecord(2).length,"utf-8"));
				
				
				String sL=(new String(rms.getRecord(3),0,rms.getRecord(3).length,"utf-8"));
				
				iLight=Integer.parseInt(sL);
				
				
				
				String sset=sSet;
				
				System.out.println("RMS="+sSet);
				
				int p1=sset.indexOf("���Ƴ���=");
				
				iMax=Integer.parseInt(sset.substring("����=".length(), p1));
				
				int p2=sset.indexOf("����=");
				
				iLen=Integer.parseInt(sset.substring(p1+"���Ƴ���=".length(),p2));
				
				int p3=sset.indexOf("�ļ�=");
				
				hideCP=(sset.substring(p2+"����=".length(),p3).equalsIgnoreCase("��"));
				
				
				sHide=hideCP?"��":"��";
				
				int p4=sset.lastIndexOf('=');
				
				sFile=sset.substring(p4+1);
				
				
				rms.closeRecordStore(); 
		
				
			}
			
	
			
		}catch(Exception e){
			
			System.out.println("ò��RMS����ʧ�ܣ�");
			
			haveRMS=false;
			
		
		}
		
	
		
		
	}
	
	
	public static void rmsOutput(String url)throws Exception
	{
		try
		{
			FileConnection fileconnection = (FileConnection)Connector.open(url, 3);
			
			if (fileconnection.exists())
				
				fileconnection.delete();
			
			fileconnection.create();
			
			DataOutputStream dataoutputstream = fileconnection.openDataOutputStream();
			
			dataoutputstream.writeInt(0x524d5300);
			
			String as[] = RecordStore.listRecordStores();
			
			if (as != null)
			{
				dataoutputstream.writeInt(as.length);
				
				for (int i = 0; i < as.length; i++)
				{
					RecordStore recordstore = RecordStore.openRecordStore(as[i], false);
					
					dataoutputstream.writeUTF(recordstore.getName());
					
					int j = recordstore.getNextRecordID();
					
					dataoutputstream.writeInt(j);
					
					for (int k = 1; k < j; k++)
					{
						try
						{
							byte abyte0[] = new byte[recordstore.getRecordSize(k)];
							
							recordstore.getRecord(k, abyte0, 0);
							
							dataoutputstream.writeInt(k);
							
							dataoutputstream.writeChar(43);
							
							dataoutputstream.writeInt(abyte0.length);
							
							dataoutputstream.write(abyte0);
							
							abyte0 = null;
							
							continue;
							
						}
						
						catch (Exception exception1)
						{
							
							dataoutputstream.writeInt(k);
							
						}
						
						dataoutputstream.writeChar(45);
					}

					recordstore.closeRecordStore();
				}

			} else
			{
				
				dataoutputstream.writeInt(0);
				
			}
			
			dataoutputstream.close();
			
			fileconnection.close();
			
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public static void rmsInput(String url) throws Exception
	{
		try
		{
			FileConnection fileconnection = (FileConnection)Connector.open(url, 1);
			
			DataInputStream datainputstream = fileconnection.openDataInputStream();
			
			if (datainputstream.readInt() == 0x524d5300)
			{
				int i = datainputstream.readInt();
				
				for (int j = 0; j < i; j++)
				{
					String s = datainputstream.readUTF();
					
					try
					{
						
						RecordStore.deleteRecordStore(s);
						
					}
					catch (Exception exception1) { }
					
					RecordStore recordstore = RecordStore.openRecordStore(s, true);
					
					int k = datainputstream.readInt();
					
					for (int l = 1; l < k; l++)
					{
						int i1 = datainputstream.readInt();
						
						char c = datainputstream.readChar();
						
						int j1 = recordstore.addRecord(null, 0, 0);
						
						if (c == '+')
						{
							int k1 = datainputstream.readInt();
							
							byte abyte0[] = new byte[k1];
							
							datainputstream.read(abyte0);
							
							recordstore.setRecord(j1, abyte0, 0, abyte0.length);
							
						} else
						{
							recordstore.deleteRecord(j1);
							
						}
					}

					recordstore.closeRecordStore();
				}

			}
			datainputstream.close();
			
			fileconnection.close();
			
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	
	public void itemStateChanged(Item item){
		
		try
		{
			Form form = (Form)MIDtxt.dp.getCurrent();
			
			Gauge g=(Gauge)item;
			
			if (g.getMaxValue() > 10)
			{
				iLight = g.getValue();
				
				((Gauge)form.get(1)).setValue(iLight/ 10);
				
			} else
			{
				iLight = g.getValue() * 10;
				
				((Gauge)form.get(0)).setValue(iLight);
			}
			
			DeviceControl.setLights(0, iLight);
			
			return;
		}
		
		catch (Exception e)
		{
			return;
		}
		
	}
	
	
	public static boolean sameCmd(Command c1,Command c2){
		
		
		return (c1.getCommandType()==c2.getCommandType()&&c1.getPriority()
				
				==c2.getPriority()&&c1.getLabel().equals(c2.getLabel()));
		
	
	}
	
		
	
}
	

