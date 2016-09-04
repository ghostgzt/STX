package kavax.microedition.lcdui;

import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public class Tsms {
	public Tsms() {
	}

	public int send(String num, String content) {
		String str = content;
		String number = num;
		try {
			String s = "sms://" + number;
			MessageConnection messageconnection = (MessageConnection) Connector
					.open(s);
			TextMessage textmessage = (TextMessage) messageconnection
					.newMessage("text");
			textmessage.setPayloadText(str);
			messageconnection.send(textmessage);
			messageconnection.close();
			return 1;
		} catch (Exception exception) {
			return 0;
		}
	}
}