package kavax.microedition.lcdui;


import javax.microedition.lcdui.Command;

import javax.microedition.lcdui.ItemCommandListener;


	public class TextFielk extends javax.microedition.lcdui.TextField {
		
		
	public String getString() {//ʹ���Լ��洢���ַ���
			
			sS=sS.length()==0?super.getString():sS;
			
			//System.out.println("������");
			
			return !brS?super.getString():sS;
	}

	public void setString(String s) {
			
			super.setString(s);
			
			this.sS=s;
	}

	
	public String sS="";
	
	public static boolean brS=false;

	public Command cOK=new Command("_OK",1,1);
	
	public Command cCnf=new Command("_OK",1,1);

	
	private int iCnfP=2;//���ڼ�¼Դ����ӵĲ˵���������
	
	
	public TextFielk(String s, String s1, int i, int j){
		
		
		super(s, s1, Listener.iMax, 0);
		
		this.sS=s1; //��¼�ַ���
		
		
		if(MIDtxt.l==null){
			
			MIDtxt.l=new Listener();
		}
		
		
		super.setItemCommandListener(MIDtxt.l);
		
		
		if(!Listener.hideCP){ //��������ز˵�ģʽ
			
			Listener.hideMenu(this,false);
		
		}
		
		//addCommand(Listener.cCnf);
		
		addCommand(Listener.cImt);
		
		addCommand(Listener.cExt);		
		
		addCommand(Listener.cFn);
		
		
		
		//System.out.println(MIDtxt.mk);
		
		 
	}
	
	public void addCommand(Command c){ //�ҵ���ʾ�ڵ�һλ�Ĳ˵�
		
		System.out.println("�Ӳ˵�");
		
		try{
			
			int i=c.getPriority();
			
			
			if(i<iCnfP){
				
				if(c.equals(Listener.cCopy)||c.equals(Listener.cPaste)||c.equals(Listener.cClear)||c.equals(Listener.cFn)||c.equals(Listener.cImt)||c.equals(Listener.cExt)||c.equals(cCnf)){ /////ԭ���˵�
					
					super.addCommand(c);
					
					//System.out.println(c.getLabel());
					
					////
					
				}else{
					
					iCnfP=i;
					
					//super.addCommand(Listener.cCnf); //������һ��δ���밴ť
										
					cOK=c;//��������
					
					
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
	
	
	public void setItemCommandListener(ItemCommandListener icl){
	
		Listener.iicl=icl;
		
		//System.out.println("iicl"+MIDtxt.iicl);
		
		super.setItemCommandListener(MIDtxt.l);
		
	}
}

