// Source File Name:   Database.java
package kavax.microedition.lcdui;

import java.io.*;
import javax.microedition.rms.RecordStore;

public class Database {
	public Database() {
	}

	public static void load() {
		try {
			RecordStore rs = RecordStore.openRecordStore("trans_setting", true);
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
					rs.getRecord(1)));
			oldindex = dis.readInt();
			newindex = dis.readInt();
			net = dis.readInt();
			rs.closeRecordStore();
		} catch (Exception e) {
			try {
				RecordStore.deleteRecordStore("trans_setting");
			} catch (Exception exception) {
			}
			try {
				RecordStore rs = RecordStore.openRecordStore("trans_setting",
						true);
				byte temp[] = new byte[0];
				rs.addRecord(temp, 0, temp.length);
				rs.addRecord(temp, 0, temp.length);
				rs.addRecord(temp, 0, temp.length);
				rs.closeRecordStore();
				saveInterface();
			} catch (Exception exception1) {
			}
		}
	}

	public static void saveInterface() {
		try {
			RecordStore rs = RecordStore.openRecordStore("trans_setting", true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeInt(oldindex);
			dos.writeInt(newindex);
			dos.writeInt(net);
			byte temp[] = baos.toByteArray();
			dos.close();
			baos.close();
			rs.setRecord(1, temp, 0, temp.length);
			rs.closeRecordStore();
		} catch (Exception exception) {
		}
	}

	private static final String CONFIG_STORE = "trans_setting";
	public static int oldindex = 0;
	public static int newindex = 1;
	public static int net = 0;
}