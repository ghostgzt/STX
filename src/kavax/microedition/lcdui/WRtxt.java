package kavax.microedition.lcdui;

import java.io.*;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

public class WRtxt {

	public WRtxt() {
	}

	public String ReadUTF$(String s) throws Throwable {
		FileConnection fileconnection = (FileConnection) Connector.open(s
				.startsWith("file://") ? s : "file://"
				.concat(String.valueOf(s)), 1);
		byte abyte0[] = new byte[(int) fileconnection.fileSize()];
		DataInputStream datainputstream = fileconnection.openDataInputStream();
		if (abyte0.length != datainputstream.read(abyte0)) {
			abyte0 = null;
		}
		datainputstream.close();
		fileconnection.close();
		datainputstream = null;
		fileconnection = null;
		return new String(abyte0, "UTF-8");
	}

	public String SaveUTF(String s, String s1) throws Throwable {
		FileConnection fileconnection = (FileConnection) Connector.open(s
				.startsWith("file://") ? s : "file://"
				.concat(String.valueOf(s)), 3);
		if (fileconnection.exists()) {
			fileconnection.delete();
		}
		fileconnection.create();
		DataOutputStream dataoutputstream = fileconnection
				.openDataOutputStream();
		dataoutputstream.write(s1.getBytes("UTF-8"));
		dataoutputstream.flush();
		dataoutputstream.close();
		fileconnection.close();
		dataoutputstream = null;
		fileconnection = null;
		return null;
	}
}