package kavax.microedition.lcdui;

import java.io.ByteArrayOutputStream;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.*;
import kavax.microedition.lcdui.StringItex;
import kavax.microedition.lcdui.TextFed;

public class LFH extends Form implements CommandListener, ItemCommandListener {

	public LFH(String str, String sf, int dk)

	{
		super("²é¿´·ûºÅ");

		doit(sf, dk);

	}

	public void doit(String str, int dk) {
		String tmpStr = str;
		Listener ll = new Listener();
		int k = dk;

		try {

			int k1 = tmpStr.lastIndexOf('|');
			int k2 = k1;
			int ol1 = 0;
			int ol2 = 1;
			int ol3 = 0;
			while (ol3 + ol2 - 2 < k2) {
				ol1 = tmpStr.indexOf('|');
				String jStr = tmpStr.substring(0, ol1);
				if (tmpStr.substring(ol1 + 1) == "") {

					// append(String.valueOf(ol2)+"."+tmpStr+"\n");
					StringItex d1 = new StringItex(null, String.valueOf(ol2)
							+ "." + tmpStr + "\n", Item.HYPERLINK);

					append(d1);
					ol3 = ol3 + tmpStr.length();
				} else {
					tmpStr = tmpStr.substring(ol1 + 1);
				}
				// append(String.valueOf(ol2)+"."+jStr+"\n");
				StringItex d2 = new StringItex(null, String.valueOf(ol2) + "."
						+ jStr + "\n", Item.HYPERLINK);

				append(d2);
				k1 = tmpStr.lastIndexOf('|');
				ol3 = ol3 + jStr.length();
				ol2 = ol2 + 1;
			}

		} catch (Exception e) {
		}
		String iio = str;
		if (ll.replace("|", "", iio).length() == iio.length()) {
			for (int l = 1; l < k + 1; l++) {
				// append( String.valueOf(l)+"."+iio.substring(l-1, l));

				StringItex d3 = new StringItex(null, String.valueOf(l) + "."
						+ iio.substring(l - 1, l), Item.HYPERLINK);

				append(d3);
			}
			append("\n");
			// append("0."+ iio);
			StringItex d4 = new StringItex(null, "0." + iio, Item.HYPERLINK);

			append(d4);
		}

	}

	public void commandAction(final Command command, Displayable displayable) {

	}

	public void commandAction(final Command command, final Item formItem) {

	}
}
