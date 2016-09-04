package kavax.microedition.lcdui;

import java.io.ByteArrayOutputStream;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import com.nokia.mid.ui.DeviceControl;
import javax.microedition.lcdui.*;
import kavax.microedition.lcdui.StringItex;
import kavax.microedition.lcdui.TextFed;
import javax.microedition.lcdui.ItemStateListener;

public class light  implements CommandListener, ItemCommandListener,
		ItemStateListener{
int iLight;
	public light(int i)

	{
		iLight=i;

	Form fl = new Form("亮度调节");
				Gauge g1 = new Gauge("", true, 100, iLight);
				// g1.setValue(iLight);
				g1.setLayout(Gauge.LAYOUT_CENTER);
				fl.append(g1);
				Gauge g2 = new Gauge("", true, 10, iLight / 10);
				g2.setLayout(Gauge.LAYOUT_CENTER);
				// g2.setValue(iLight/10);
				fl.append(g2);
				fl.addCommand(new Command("返回", 7, 1));
				fl.setCommandListener(this);
				fl.setItemStateListener(this);
				MIDtxt.dp.setCurrent(fl);

	}

	public void itemStateChanged(Item item) {
		try {
			Form form = (Form) MIDtxt.dp.getCurrent();
			Gauge g = (Gauge) item;
			if (g.getMaxValue() > 10) {
				iLight = g.getValue();
				((Gauge) form.get(1)).setValue(iLight / 10);
			} else {
				iLight = g.getValue() * 10;
				((Gauge) form.get(0)).setValue(iLight);
			}
			DeviceControl.setLights(0, iLight);
			return;
		} catch (Exception e) {
			return;
		}
	}
	public void commandAction(final Command command, Displayable displayable) {
	int ic = command.getPriority();
			switch (ic) {
			case 1:
			MIDtxt.close();
			break;
			
			}
	}

	public void commandAction(final Command command, final Item formItem) {

	}
}
