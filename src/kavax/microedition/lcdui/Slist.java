package kavax.microedition.lcdui;

import javax.microedition.lcdui.*;
import kavax.microedition.lcdui.*;


public class Slist implements CommandListener {

	public Slist(int i) {
	MIDtxt.lastDz=MIDtxt.dp.getCurrent();
	ix=i;
		l = new List("Fuck Q！", 3);
		l.append("基本功能",null);
		l.append("拓展功能",null);
		l.append("实用工具",null);
		l.append("3D屏保",null);
		l.append("经典游戏",null);
		l.append("帮助",null);
		l.append("退出",null);
		l.addCommand(cx);
		l.addCommand(c1);
		l.setSelectCommand(c1);
		l.setCommandListener(this);
		MIDtxt.dp.setCurrent(l);
	}

	

	public void commandAction(Command c, Displayable d) {
		// String cmd = c.getLabel();
		if (c == c1){
		  Listener ll=new Listener();
		 MIDtxt.lastDy=MIDtxt.dp.getCurrent();
		switch (l.getSelectedIndex()) {
		case 0:
		ll.fsSet(1, "功能中心(Fuck Q！)");
		break;
		    case 1:
    ll.fsSet(2, "拓展功能(Fuck Q！)");
    break;
    case 2:
      ll.fsSet(5, "实用工具(Fuck Q！)");
    break;
    case 3:
      ll.fsSet(4, "3D屏保(Fuck Q！)");
    break;
    case 4:
      ll.fsSet(3, "经典游戏(Fuck Q！)");
    break;
case 5:
 Alert alert = new Alert("关于", "作者:GhostGzt(Gentle)\nQQ:1275731466\nE-Mail:GhostGzt@163.com\n编辑器:Notepad+WTK\nSuper Text 第五版(内核: kavaText 4.21)\n合法商标: Fuck Q！\nFuck Q！保留一切解释权利！！！", null, AlertType.INFO);
    alert.setTimeout(2560);
    MIDtxt.dp.setCurrent(alert);
break;
			case 6:
		MIDtxt.dp.setCurrent(MIDtxt.lastDx);
		break;
		}
		}
	else if (c == cx) {
	if (ix==0){
			MIDtxt.dp.setCurrent(MIDtxt.lastDz);}
			else{
			MIDtxt.dp.setCurrent(MIDtxt.lastDx);
			}
		} 

	}

	private Command c1 = new Command("选择", 1, 1);
	private Command cx = new Command("返回", 2, 1);
	private List l;
	private int ix;
}