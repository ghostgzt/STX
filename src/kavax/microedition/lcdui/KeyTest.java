package kavax.microedition.lcdui;
	
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
public class KeyTest  implements CommandListener{
public Display display;
private Displayable d;
Displayable dp;
private Command exitCommand;
private Command backCommand;

public KeyTest(){
MIDtxt.lastD1=MIDtxt.dp.getCurrent();
Form ks=new Form("KeyTest");
ks.append("欢迎使用KeyTest\nMade By GhostGzt(Gentle)\nFuck Q！\n说明:\n双击右软键或确定键返回\n请开始...");
exitCommand = new Command("Exit", Command.EXIT,2);
backCommand = new Command("Start", Command.BACK,1);
ks.addCommand(exitCommand);
ks.addCommand(backCommand);
ks.setCommandListener(this);
 MIDtxt.dp.setCurrent(ks);
 dp= ks;
	d = new KeyCanvas(MIDtxt.dp,dp);
	d.setCommandListener(this);
}
public void startApp(){
	display =MIDtxt.dp;
    display.setCurrent(d);
	
}
public void pauseApp(){
}

public void destroyApp(boolean unconditional){
}
public void commandAction(Command command, Displayable screen){
	if (command == exitCommand){ 
	
destroyApp(false);
MIDtxt.close();
    //notifyDestroyed();
    }else if (command == backCommand){
 startApp();
    }
}

}


class KeyCanvas extends Canvas{
Displayable dp1;
Display display1;
public static String sShow = "按按看";
public static String sw = "1";
int x1,y1;
int x2,y2;
int x3,y3;
int xx,yy;
int w,h;
public KeyCanvas(Display display,Displayable dp){
dp1=dp;
display1=display;
	w = getWidth();
	h = getHeight(); 
	x1 = 10;
	y1 = (2*h/3)-30;
	x2 = 25;
	y2 = (2*h/3)-45;
	x3 = 70;
	y3 = (2*h/3)-25;
xx=0;
yy=0;
}
public void paint(Graphics g){
  setFullScreenMode(true);
	g.setColor(0xFFFFFF);
    g.fillRect(0,0,w,h);
    g.setColor(0x000000);
	g.drawRect(0,2*h/3,w,5);
	g.fillRoundRect(x1,y1,60,30,15,15);
	g.fillRect(x2,y2,30,15);
	g.fillRect(x3,y3,15,5);
		g.fillRect(x3+xx,y3-yy,15,5);
	 g.drawString(sShow, getWidth() / 2, getHeight() / 3, 17);
}
protected  void keyPressed(int keyCode){
sShow ="Pressed Key = "+getKeyName(keyCode)+": "+ (new StringBuffer(String.valueOf(keyCode))).toString();
   //System.out.println("pressed key ="+getKeyName(keyCode));
	if (keyCode == KEY_NUM4){
		if(x1<=5)
		{	x1 = x1;
			x2 = x2;
			x3 = x3;
		}
		else
		{	
			x1 = x1-5;
			x2 = x2-5;
			x3 = x3-5;
		}
	}
	else if(keyCode == KEY_NUM6){
		if(x1>=w-60)
		{	x1 = x1;
			x2 = x2;
			x3 = x3;
		}
		else
		{	
			x1 = x1+5;
			x2 = x2+5;
			x3 = x3+5;
		}
    }else if (keyCode == KEY_NUM5){
			if (y3-yy-1<2*h/3-5){
			 xx=xx+5;
			 if (xx>10){
    yy=yy-1;}}else{xx=0;
    yy=0;
    }   
    }
    	repaint();
    try{
 if((getKeyName(keyCode).length()==5&&Integer.parseInt(getKeyName(keyCode).substring(4,5))==2)||(getKeyName(keyCode).length()==6)){
 if(sw.length()==1){
 sw="12";
 }else{
 sw="1";
display1.setCurrent(dp1);
}
}else{
 sw="1";
}
//repaint();
}catch(Exception e){}


}
}
