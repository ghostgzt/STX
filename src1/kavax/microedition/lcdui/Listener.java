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

	
public static String sCut="\n艾网rsRk为你奉上自由复制随便字限文本框[吉它潮人]\n"; //剪贴板内容
	
	
	public static int iMax=88888;///文本框字限
	
	
	public static int iLen=0;///复制长度
	
	
	public static boolean hideCP=false;
	
	
	public static String sFile="file:///E:/text.txt";
	
	
	public static String sRms="file:///E:/rms.db";
	
	
	public static String sHide="否";
	
	
	public static String sSet="字限="+iMax+"复制长度="+iLen+"隐藏="+sHide+"文件="+sFile; //设置内容串
	
	
	//public static Command cClear,cCopy,cPaste,cSet,cImt,cExt,cCnf;
	
	private String oldstring="";
	
	private TextBox tshow;
	
	//private int is1,is2; //第一第二光标位置
	
	private Displayable lastD; //上一个显示对象
	
	
	public static ItemCommandListener iicl;
	
	public static CommandListener ccl;
	
	public static int iLight=50;
	
	
	private static TextFielk tfk;
	
	private static TextBok tbk;
	
	private static boolean haveRMS=true;
	
	public static Command cCopy=new Command("_复制",1,2);
	
	public static Command cPaste=new Command("_粘贴",1,3);
	
	public static Command cClear=new Command("_清空",1,4);
	

	public static Command cImt=new Command("_导入文本",1,7);
	
	public static Command cExt=new Command("_导出文本",1,8);		
	
	public static Command cFn=new Command("_功能",1,5);
	
	
	
	public static String sab="1.复制/粘贴/清空\n2.导入/导出文本\n3.RMS导入/导出\n4.诺机亮度控制\n5.系统信息查看\n(可能发出异响!)";
			
	
	//public static Command cCnf=new Command("_OK",1,1);
	
	//public static Command cOK;
	
	/////////////////////////////
	
	
	public Listener(){
		
		
	}
	
	
	public void commandAction(Command cd, final Displayable d){
		
		//System.out.println("点击了新文本框按钮");
		
		if(d.equals(tshow)|| d instanceof Form){
			
			TextBox t=null;
			
			Form f=null;
		
			if(d instanceof TextBox){
				
				System.out.println("是么");
				
				t=(TextBox)d;
			}
			else if(d instanceof Form){
				
				f=(Form)d;
			}
			
			int ic=cd.getPriority();
			
			
			
			switch(ic){
			
			case 1: //
				
				int is1=t.getCaretPosition();//当前光标位置
				
				int ix=t.getString().indexOf('※'); //原先位置
				
				
				if(-1<=ix-is1 && ix-is1<1){
					
					return; //位置相同，不用折腾
					
				}else{
					
					String snn=t.getString();
					
					snn=snn.substring(0, ix)+snn.substring(ix+1);
					
					if(is1>ix){
						
						is1=is1-1;
					}
					
					snn=snn.substring(0, is1)+"※"+snn.substring(is1);
					
					t.setString(snn);
					
				}
				
				
				break;
				
			case 2: //复制
				
				int is2=t.getCaretPosition();//第二光标位置
				
				is1=t.getString().indexOf('※');
				
				int i1=Math.min(is1, is2);
				
				int i2=Math.max(is1, is2);
				
				if(is1<is2){
					
					i2=i2-1;
				}
				
				if(-1<=is1-is2 && is1-is2<=1){//光标位置相同
					
					if(is1>=oldstring.length()){ ///// 光标位置在末尾复制全部
						
						sCut=oldstring;
						
						
					}
					else if(iLen!=0 && iLen<oldstring.length()-is1){ ///// 复制一定长度字符串
						
						sCut=oldstring.substring(is1,is1+iLen);
						
					}else if(iLen==0 || iLen>=oldstring.length()-is1){ /////复制之后所有字符串
						
						
						sCut=oldstring.substring(is1);
								
					}
					
				}else{ //常规复制
					
					sCut=oldstring.substring(i1, i2);
				}
				
				
				
				rmsDo(true);
				
				
				//System.out.println("复制的东东:"+sCut+is1+is2);
				
				MIDtxt.dp.setCurrent(lastD);//恢复显示
				
				break;
			
				
			case 3: //取消
				
				MIDtxt.dp.setCurrent(lastD);//恢复显示
				
				if(cd.getCommandType()==7){ //需要存储数据
					
					rmsDo(true);//写入数据
				}
				
				break;
				
			case 4: //设置确认
				
				String oldsSet=sSet;
				
				int oldiMax=iMax;
				
				int oldiLen=iLen;
				
				String oldsFile=sFile;
				
				
				
				try{
					
					String sset=t.getString();//获取设置字符串
					
					int p1=sset.indexOf("复制长度=");
					
					iMax=Integer.parseInt(sset.substring("字限=".length(), p1));
					
					try{
						
						tfk.setMaxSize(iMax);
						
					}catch(Exception e){}
					
					finally{
						
						try{
							
							tbk.setMaxSize(iMax);
							
						}catch(Exception e){}
						
					}
					
					
					int p2=sset.indexOf("隐藏=");
					
					iLen=Integer.parseInt(sset.substring(p1+"复制长度=".length(),p2));
					
					int p3=sset.indexOf("文件=");
					
					sHide=sset.substring(p2+"隐藏=".length(),p3);
					
					hideCP=sHide.equalsIgnoreCase("是");
					
					sHide=hideCP?"是":"否";
					

					
					try{
						
						hideMenu(tfk,hideCP); //显隐菜单
											
						
					}catch(Exception e){}
					
					finally{
						
						
						try{
							
							hideMenu(tbk,hideCP); //显隐菜单
							
						}catch(Exception e){}
						
					}
					
					
					int p4=sset.lastIndexOf('=');
					
					sFile=sset.substring(p4+1);
					
					sSet="字限="+iMax+"复制长度="+iLen+"隐藏="+sHide+"文件="+sFile; //设置内容串
					
					System.out.println(sSet);
					
					
					
					rmsDo(true);
					
					//tfk.setString(oldstf+"_成");
					
					MIDtxt.dp.setCurrent(lastD);//恢复显示
					
					
				}catch(Exception e){
					
					e.printStackTrace();
					
					sSet=oldsSet; ////出错复原原设置
					
					iLen=oldiLen;
					
					iMax=oldiMax;
					
					sFile=oldsFile;
									
					t.setString(t.getString()+"_败");
				}
			
			
				break;
				
			case 7://导入文本
				
				
				
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
							
							sSet="字限="+iMax+"复制长度="+iLen+"隐藏="+hideCP+"文件="+sFile; //设置内容串
							
							
							rmsDo(true);
							
							String stext="";
							
							if(((ChoiceGroup)f.get(1)).isSelected(0)){//utf-8模式
								
								stext=new String(b,0,b.length,"utf-8"); 
								
							}else{
								
								stext=new String(b);
							}
							
							//System.out.println("tbk="+tbk);
							
							if(((ChoiceGroup)f.get(2)).isSelected(0)){ //直接插入
								
								
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
								
							MIDtxt.dp.setCurrent(lastD);//恢复显示
							
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
			
			
				break;	
			
			case 8://导出文本
				
				
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
						
						boolean bw=((ChoiceGroup)f.get(2)).isSelected(0);//附于末尾
						
						try{
							
							fc=(FileConnection)Connector.open(url,Connector.READ_WRITE);
							
							
							if(bw){//附于末尾
								
								
								if(!fc.exists()){
									
									fc.create();
									
								}
								
								os=fc.openOutputStream(fc.fileSize());
								
							}else{ //重写
								
								System.out.println("重写");

								if(fc.exists()){
									
									fc.delete();
									
								}
								
								fc.create();
								
								os=fc.openOutputStream();
							}
							
					
							
							
							String sout="导出的文本";
							
							try{
								
								sout=tfk.getString();
							
							}catch(Exception e){}
							
							finally{
								
								try{
									
									sout=tbk.getString();
								
								}catch(Exception e){}
								
							}
							
							
							
						
							if(((ChoiceGroup)f.get(1)).isSelected(0)){//utf-8模式
								
								os.write(sout.getBytes("utf-8"));
								
							}else{
								
								os.write(sout.getBytes());
							}
							
							
							sFile=url;
							
							sSet="字限="+iMax+"复制长度="+iLen+"隐藏="+hideCP+"文件="+sFile; //设置内容串
							
							
							
							
							rmsDo(true);
							
							//tfk.setString(oldstring+"_成"); ////恢复字符串
							
							MIDtxt.dp.setCurrent(lastD);//恢复显示
							
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
			
			
				break;
				
				
			case 11: //参数设置
				
				System.out.println("sSet="+sSet);
				
				tshow=new TextBox("参数设置",sSet,this.iMax,0);
				
				tshow.setCommandListener(this);
				
				tshow.addCommand(new Command("确定",1,4));
							
				tshow.addCommand(new Command("取消",7,3));
			
				MIDtxt.dp.setCurrent(tshow);
				
				break;
				
				
			case 12: //RMS操作
				
				
				Form frms=new Form("RMS操作");
				
				frms.append(new TextField("",sRms,1024,0));
				
				frms.append(new ChoiceGroup("",1,new String[]{"导出","导入"},null));
				
				frms.addCommand(new Command("确定",1,21));
				
				frms.addCommand(new Command("返回",7,3));
				
				frms.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(frms);
				
				break;
				
			
			case 13: //亮度调节
				
				Form fl=new Form("亮度调节");
				
				Gauge g1=new Gauge("",true,100,iLight);
				
				//g1.setValue(iLight);
				
				g1.setLayout(Gauge.LAYOUT_CENTER);
				
				fl.append(g1);
				
				Gauge g2=new Gauge("",true,10,iLight/10);
				
				g2.setLayout(Gauge.LAYOUT_CENTER);
				
				//g2.setValue(iLight/10);
				
				fl.append(g2);
				
				fl.addCommand(new Command("返回",7,3));
				
				fl.setCommandListener(this);
				
				fl.setItemStateListener(this);
				
				MIDtxt.dp.setCurrent(fl);
				
				break;
				
			case 14: //简单编辑
				
				break;
				
			case 15: //系统信息
				
				Form fs=new Form("系统信息");
				
				fs.append("温馨提示:以下内容仅供参考");
				
				fs.append("\n手机型号:"+System.getProperty("microedition.platform"));
				
				fs.append("\n总体运存:"+Runtime.getRuntime().totalMemory());
				
				fs.append("\n可用运存:"+Runtime.getRuntime().freeMemory());
				
				//fs.append("\n可用运存:"+Display);
				
				
				
				fs.append("\nMIDP版本:"+System.getProperty("microedition.profiles"));

				fs.append("\nCLDC版本:"+System.getProperty("microedition.configuration"));
				
				
				fs.append("\n支持彩色:"+MIDtxt.dp.isColor());
				
				fs.append("\n彩色灰度:"+MIDtxt.dp.numColors());
				
				fs.append("\n支持透明:"+MIDtxt.dp.numAlphaLevels());
				
				fs.append("\n支持背闪:"+MIDtxt.dp.flashBacklight(0));
				
				fs.append("\n支持振动:"+MIDtxt.dp.vibrate(0));
				
				
				fs.append("\n支持混音:"+System.getProperty("supports.mixing"));
				
				fs.append("\n支持录音:"+System.getProperty("supports.audio.capture"));
				
				fs.append("\n支持录像:"+System.getProperty("supports.video.capture"));
				
				fs.append("\n音频格式:"+System.getProperty("audio.encodings"));
				
				fs.append("\n视频格式:"+System.getProperty("video.encodings"));
					
				fs.append("\n截屏格式"+System.getProperty("video.snapshot.encodings"));
				
				fs.append("\n流媒支持:"+System.getProperty("streamable.contents"));
				
				
				
				
				fs.append("\n短信中心:"+System.getProperty("wireless.messaging.sms.smsc"));
							
				fs.append("\n图片目录:"+System.getProperty("fileconn.dir.photos"));
				
				fs.append("\n视频目录:"+System.getProperty("fileconn.dir.videos"));
							
				fs.append("\n声音目录:"+System.getProperty("fileconn.dir.tones"));
				
				fs.append("\n存储目录:"+System.getProperty("fileconn.dir.memorycard"));
				
				fs.append("\n私有目录:"+System.getProperty("fileconn.dir.private"));
				
				fs.append("\n分隔符号:"+System.getProperty("file.separator"));
				
				
				
				fs.addCommand(new Command("返回",7,3));
				
				fs.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(fs);
				
				break;
				
			case 16: //键值测试
				
				//keyCanvas kc=new keyCanvas(lastD);
				
				//kc.setTitle("键值测试(长按0返回)");
			
				
				//kc.addCommand(new Command("返回",2,3));
				
				//kc.setCommandListener(this);
				
				//MIDtxt.dp.setCurrent(kc);
				
				//break;
				
			case 21: //RMS操作确认
				
				
				try{
					
					
				
					String url=((TextField)f.get(0)).getString();
					
					if(((ChoiceGroup)f.get(1)).isSelected(0)){ //导出
						
						rmsDo(true);//写入当前设置
						
						rmsOutput(url);
						
						f.append("导出成功!\n");
						
						
					}else{ //导入
						
						rmsInput(url);
						
						f.append("导入成功!\n");
					
					}
					
					sRms=url;
					
					
				}catch(Exception e){
					
					
					f.append("操作失败!\n");
					
				}
				
				
				
				break;
			
			}
			
			
		}else { //新文本框
			
						
			
			try{
				
				
				tbk=((TextBok)d);
				
				lastD=MIDtxt.dp.getCurrent();
				
			
				if(!sameCmd(cClear,cd)&&cd.getLabel().indexOf("清空")!=-1){ ////如果本身有清空，隐藏本身的菜单
					
					tbk.removeCommand(cClear);
					
				}	
				
				
				
			}catch(Exception e){}
			
			
			
			if(!cd.equals(tbk.cOK)&&!sameCmd(cCopy,cd)&&!sameCmd(cPaste,cd)&&!sameCmd(cClear,cd)&&!sameCmd(cFn,cd)
					
					
					&&!sameCmd(cImt,cd)&&!sameCmd(cExt,cd)&&!sameCmd(tbk.cCnf,cd)){ /////原带菜单
				
				
				ccl.commandAction(cd, d);
				
				
				return;
			}
			
			
			if(sameCmd(tbk.cCnf,cd)){
				
				System.out.println("确认");
				
				ccl.commandAction(tbk.cOK, d);
				
				
				
				return;
			}
			
			
			
			int p=cd.getPriority();
			
			
			switch(p){
			
			
			
			case 2: /////复制
				
				
				oldstring=tbk.getString(); //保存旧字符串
				
				int is1=tbk.getCaretPosition();//第一光标位置
				
				String stemp=oldstring.substring(0, is1)+"※"+oldstring.substring(is1);
				
				tshow=new TextBox("自由复制",stemp,iMax+1,0);
				
				tshow.setCommandListener(this);
				
				tshow.addCommand(new Command("更改起点",1,1));
				
				tshow.addCommand(new Command("复制文本",2,2));
				
				tshow.addCommand(new Command("取消",1,3));
				
				MIDtxt.dp.setCurrent(tshow);
				
				
				break;
				
			case 3: /////粘贴
				
						
				int ii=tbk.getCaretPosition();
	        	
	        	String s=tbk.getString().substring(0, ii)+sCut+tbk.getString().substring(ii);
	        	
	        	//System.out.println(s+ii);
	        	
	        	tbk.setString(s);
				
				break;
				
			case 4://///清空
				
				tbk.setString("");
				
				break;
			
				
			case 5://////设置
				
				
				Form fs=new Form("实用功能");
				
				fs.append(sab);
				
				fs.addCommand(new Command("参数设置",1,11));
				
				fs.addCommand(new Command("RMS操作",1,12));
				
				fs.addCommand(new Command("亮度控制",1,13));
				
				fs.addCommand(new Command("简单编辑",1,14));
				
				fs.addCommand(new Command("系统信息",1,15));
				
				fs.addCommand(new Command("键值测试",1,16));
				
				fs.addCommand(new Command("返回",2,3));
				
				fs.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(fs);
				
				
				break;
				
				
			case 7: //导入文本
				
				
				Form f=new Form("导入设置");
				
				f.append(new TextField("导入路径",sFile,this.iMax,0));
				
				f.append(new ChoiceGroup("编码",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
				
				f.append(new ChoiceGroup("方式",ChoiceGroup.POPUP,new String[]{"插入当前位置","替换原字符串"},null));
				
				f.addCommand(new Command("确定",1,7));
				
				f.addCommand(new Command("取消",2,3));
				
				f.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(f);
				
				break;	
				
			case 8://导出文本
				
				f=new Form("导出设置");
				
				f.append(new TextField("导出路径",sFile,this.iMax,0));
				
				f.append(new ChoiceGroup("编码",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
				
				f.append(new ChoiceGroup("方式",ChoiceGroup.POPUP,new String[]{"附于文件末尾","重写文本文件"},null));
				
				f.addCommand(new Command("确定",1,8));
				
				f.addCommand(new Command("取消",2,3));
				
				f.setCommandListener(this);
				
				MIDtxt.dp.setCurrent(f);
				
				break;	
				

			
			}
			
		
		}
		
	}
	
	
	public void commandAction(Command cd, Item item){
		
	
		//System.out.println("点击了新文本框f按钮");
		
		Fork fi=(Fork)MIDtxt.dp.getCurrent(); //获得了所在Fork
		
		
		try{
			
			
			tfk=((TextFielk)item);
			
			lastD=MIDtxt.dp.getCurrent();
			
			
			////////////////////////////////////////////////
			
			
			if(sameCmd(tfk.cOK,new Command("_OK",1,1))){
				

				//System.out.println("所在fork:"+fi.getTitle()+"第一菜单"+fi.cFst.getLabel());
				
				tfk.cOK=fi.cFst;
				
				tfk.addCommand(tfk.cCnf=new Command(tfk.cOK.getLabel(),1,1));
				
				fi.removeCommand(fi.cFst);
			}
			
			////////////////////////////////////////////////
			
		
			if(!sameCmd(cClear,cd)&&cd.getLabel().indexOf("清空")!=-1){ ////如果本身有清空，隐藏本身的菜单
				
				tfk.removeCommand(cClear);
				
			}	
			
			
			
			
		}catch(Exception e){}
		
		System.out.println("点击"+cd.getLabel()+cd.getCommandType()+cd.getPriority());
		
		
		if(!sameCmd(cCopy,cd)&&!sameCmd(cPaste,cd)&&!sameCmd(cClear,cd)&&!sameCmd(cFn,cd)
				
				
				&&!sameCmd(cImt,cd)&&!sameCmd(cExt,cd)&&!sameCmd(tfk.cCnf,cd)){ /////原带菜单
			
			iicl.commandAction(cd, item);
			
			
			return;
		}
		
		//System.out.println("确认");
		
		if(sameCmd(tfk.cCnf,cd)){
			
			System.out.println("这个是转移的菜单哦拉力"+fi);
			
			try{
				
				
				//iicl.commandAction(tfk.cOK, item); // 文本框菜单
								
				fi.cl.commandAction(tfk.cOK, fi); //窗体菜单
				
				
				
			}catch(Exception e){
				
				
				//fi.cl.commandAction(tfk.cOK, fi); //窗体菜单
								
				//iicl.commandAction(tfk.cOK, item); // 文本框菜单
				
				System.out.println(fi.cl);
			}
			
		
			
			return;
		}
		
		
		
		//System.out.println("新文本框f按钮");
		
		int p=cd.getPriority();
		
		
		
	
		switch(p){
		
		
		
		
		case 2: /////复制
			
			
			oldstring=tfk.getString(); //保存旧字符串
			
			int is1=tfk.getCaretPosition();//第一光标位置
			
			String stemp=oldstring.substring(0, is1)+"※"+oldstring.substring(is1);
			
			tshow=new TextBox("自由复制",stemp,this.iMax+1,0);
			
			tshow.setCommandListener(this);
			
			tshow.addCommand(new Command("更改起点",1,1));
			
			tshow.addCommand(new Command("复制文本",2,2));
			
			tshow.addCommand(new Command("取消",1,3));
			
			MIDtxt.dp.setCurrent(tshow);
			
			
			break;
			
		case 3: /////粘贴
			
					
			int ii=tfk.getCaretPosition();
        	
        	String s=tfk.getString().substring(0, ii)+sCut+tfk.getString().substring(ii);
        	
        	//System.out.println(s+ii);
        	
        	tfk.setString(s);
			
			break;
			
		case 4://///清空
			
			tfk.setString("");
			
			break;
	
			
			
		case 5://////功能
			
			Form fs=new Form("实用功能");
			
			fs.append(sab);
			
			fs.addCommand(new Command("参数设置",1,11));
			
			fs.addCommand(new Command("RMS操作",1,12));
			
			fs.addCommand(new Command("亮度控制",1,13));
			
			fs.addCommand(new Command("简单编辑",1,14));
			
			fs.addCommand(new Command("系统信息",1,15));
			
			fs.addCommand(new Command("键值测试",1,16));
			
			fs.addCommand(new Command("返回",2,3));
			
			fs.setCommandListener(this);
			
			MIDtxt.dp.setCurrent(fs);
			
			
			break;
			
		case 7: //导入文本
			
			
			Form f=new Form("导入设置");
			
			f.append(new TextField("导入路径",sFile,this.iMax,0));
			
			f.append(new ChoiceGroup("编码",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
			
			f.append(new ChoiceGroup("方式",ChoiceGroup.POPUP,new String[]{"插入当前位置","替换原字符串"},null));
			
			f.addCommand(new Command("确定",1,7));
			
			f.addCommand(new Command("取消",2,3));
			
			f.setCommandListener(this);
			
			MIDtxt.dp.setCurrent(f);
			
			break;	
			
		case 8://导出文本
			
		
			f=new Form("导出设置");
			
			f.append(new TextField("导出路径",sFile,this.iMax,0));
			
			f.append(new ChoiceGroup("编码",ChoiceGroup.POPUP,new String[]{"UTF-8","ANSI"},null));
			
			f.append(new ChoiceGroup("方式",ChoiceGroup.POPUP,new String[]{"附于文件末尾","重写文本文件"},null));
			
			f.addCommand(new Command("确定",1,8));
			
			f.addCommand(new Command("取消",2,3));
			
			f.setCommandListener(this);
			
			MIDtxt.dp.setCurrent(f);
			
			break;	
		
		}
	
		
	}
	
	
	public static void hideMenu(TextBox tbx,boolean hide){ //显隐菜单
		
		if(hide){ /////////  需要隐藏菜单
			
			tbx.removeCommand(cClear);
			
			tbx.removeCommand(cCopy);
			
			tbx.removeCommand(cPaste);
			
			
		}else{
			
			tbx.addCommand(cClear);
			
			tbx.addCommand(cCopy);
			
			tbx.addCommand(cPaste);
			
		
		}
		
	}
	
	public static void hideMenu(TextFielk tfk,boolean hide){ //显隐菜单
		
		if(hide){ /////////  需要隐藏菜单
			
			tfk.removeCommand(cClear);
			
			tfk.removeCommand(cCopy);
			
			tfk.removeCommand(cPaste);
		
		}else{
			
			tfk.addCommand(cClear);
			
			tfk.addCommand(cCopy);
			
			tfk.addCommand(cPaste);
			
			
		}
		
	}
	
	public static void rmsDo(boolean write){ ///rms操作
		
		try{
			
			if(write){ ////写
				
				
				RecordStore rms = RecordStore.openRecordStore("kavaXText", true, RecordStore.AUTHMODE_ANY, true); 
			
				System.out.println(rms);
				
				
				if(!haveRMS){
					
					
					System.out.println("haveRMS"+haveRMS);
					
					rms.addRecord(sSet.getBytes("utf-8"), 0, sSet.getBytes("utf-8").length);
					
					rms.addRecord(sRms.getBytes("utf-8"), 0, sRms.getBytes("utf-8").length);
					
					rms.addRecord((iLight+"").getBytes(), 0, (iLight+"").getBytes("utf-8").length);
					
					System.out.println("设置"+sSet);
					
				
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
				
				int p1=sset.indexOf("复制长度=");
				
				iMax=Integer.parseInt(sset.substring("字限=".length(), p1));
				
				int p2=sset.indexOf("隐藏=");
				
				iLen=Integer.parseInt(sset.substring(p1+"复制长度=".length(),p2));
				
				int p3=sset.indexOf("文件=");
				
				hideCP=(sset.substring(p2+"隐藏=".length(),p3).equalsIgnoreCase("是"));
				
				
				sHide=hideCP?"是":"否";
				
				int p4=sset.lastIndexOf('=');
				
				sFile=sset.substring(p4+1);
				
				
				rms.closeRecordStore(); 
		
				
			}
			
	
			
		}catch(Exception e){
			
			System.out.println("貌似RMS操作失败！");
			
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
	

