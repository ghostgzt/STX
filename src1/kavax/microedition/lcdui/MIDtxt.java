package kavax.microedition.lcdui;

//import com.nokia.mid.ui.DeviceControl;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public abstract class MIDtxt extends MIDlet{

	public static MIDtxt mk=null;
	
	public static Listener l;
	
	public static Display dp;
	
	public static String sFile;
	
	
	
	public MIDtxt()
	
	{
		
		if(mk==null){
			
		
			mk = this;
			
			dp = Display.getDisplay(this);
			
			
			l=new Listener();
			
			
			System.out.println("l="+l);
			
			
			Listener.sSet="����="+Listener.iMax+"���Ƴ���="+Listener.iLen+"����="+Listener.sHide+"�ļ�="+Listener.sFile;
			
			Listener.rmsDo(false); ////��
			
			
			
			try{ 
				
				textCanvas.ikDel=Integer.parseInt(mk.getAppProperty("keyDelete"));
				
			}catch(Exception e){}
			
			
			System.out.println("keyDelete="+textCanvas.ikDel);
			
			
			try{ 
				
				
				FileConnection fc=(FileConnection)Connector.open("file:///x/x.x");
				
			}catch(Exception e){}
			
			System.out.println("there is it");
		}
	}

	
	
}
