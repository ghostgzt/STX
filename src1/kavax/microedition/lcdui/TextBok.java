package kavax.microedition.lcdui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;



		public class TextBok extends javax.microedition.lcdui.TextBox {
			
			
		public String getString() {//使用自己存储的字符串
				
				sS=sS.length()==0?super.getString():sS;
				
				return !brS?super.getString():sS;
		}

		public void setString(String s) {
				
				super.setString(s);
				
				this.sS=s;
		}

		
		public String sS="";
		
		public static boolean brS=false; //是否强制托管字符串
			
		public Command cOK=new Command("_OK",1,1);
			
		public Command cCnf=new Command("_OK",1,1);
	
		
		private int iCnfP=2;//用于记录源程序加的菜单的优先性
		
		
		public TextBok(String s, String s1, int i, int j){
			
			

			super(s, s1, Listener.iMax, 0);
			
			this.sS=s1;

			if(MIDtxt.l==null){
				
				MIDtxt.l=new Listener();
			}
			
			super.setCommandListener(MIDtxt.l);
			
			
			
			if(!Listener.hideCP){ //如果非隐藏菜单模式
				
				Listener.hideMenu(this,false);
			
			}
			
			//addCommand(Listener.cCnf);
			
			addCommand(Listener.cImt);
			
			addCommand(Listener.cExt);		
			
			addCommand(Listener.cFn);
			
			
			 
		}
		
		public void addCommand(Command c){ //找到显示在第一位的菜单
			
			System.out.println("加菜单");
			
			try{
				
				int i=c.getPriority();
				
				
				if(i<iCnfP){
					
					if(c.equals(Listener.cCopy)||c.equals(Listener.cPaste)||c.equals(Listener.cClear)||c.equals(Listener.cFn)||c.equals(Listener.cImt)||c.equals(Listener.cExt)||c.equals(cCnf)){ /////原带菜单
						
						super.addCommand(c);
						
						//System.out.println(c.getLabel());
						
						////
						
					}else{
						
						iCnfP=i;
						
						//super.addCommand(Listener.cCnf); //加入上一个未加入按钮
											
						cOK=c;//更改索引
						
						if(cCnf!=null){
							
							removeCommand(cCnf);
						}
					
						
						if(!Listener.sameCmd(cOK,new Command("_OK",1,1))){
							
							super.addCommand(cCnf=new Command(cOK.getLabel(),cOK.getCommandType(),1));
						}
					
		
					}
					
					
				
				}else{
					
					super.addCommand(c);
				}
						
				
			}catch(Exception e){
				
				super.addCommand(c);
				
			}
			
			
			
		}
		
		
		public void setCommandListener(CommandListener ccl){
		
			Listener.ccl=ccl;
			
			//System.out.println("ccl"+ccl);
			
			super.setCommandListener(MIDtxt.l);
			
		}
	}

