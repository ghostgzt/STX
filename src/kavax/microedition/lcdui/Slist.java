package kavax.microedition.lcdui;

import javax.microedition.lcdui.*;
import kavax.microedition.lcdui.*;


public class Slist implements CommandListener {

	public Slist(int i) {
	MIDtxt.lastDz=MIDtxt.dp.getCurrent();
	ix=i;
		l = new List("Fuck Q��", 3);
		l.append("��������",null);
		l.append("��չ����",null);
		l.append("ʵ�ù���",null);
		l.append("3D����",null);
		l.append("������Ϸ",null);
		l.append("����",null);
		l.append("�˳�",null);
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
		ll.fsSet(1, "��������(Fuck Q��)");
		break;
		    case 1:
    ll.fsSet(2, "��չ����(Fuck Q��)");
    break;
    case 2:
      ll.fsSet(5, "ʵ�ù���(Fuck Q��)");
    break;
    case 3:
      ll.fsSet(4, "3D����(Fuck Q��)");
    break;
    case 4:
      ll.fsSet(3, "������Ϸ(Fuck Q��)");
    break;
case 5:
 Alert alert = new Alert("����", "����:GhostGzt(Gentle)\nQQ:1275731466\nE-Mail:GhostGzt@163.com\n�༭��:Notepad+WTK\nSuper Text �����(�ں�: kavaText 4.21)\n�Ϸ��̱�: Fuck Q��\nFuck Q������һ�н���Ȩ��������", null, AlertType.INFO);
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

	private Command c1 = new Command("ѡ��", 1, 1);
	private Command cx = new Command("����", 2, 1);
	private List l;
	private int ix;
}