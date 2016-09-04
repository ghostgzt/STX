package kavax.microedition.lcdui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.*;

public class FileCap {
public static String scp1,scp2;
public static byte[] b;
public FileCap (){

}


public void cap(String str1,String str2){

	scp1=str1;
	scp2=str2;
				 new Thread() {
				 public void run() {
				
				String url = scp1;//路径
				FileConnection fc = null;
				InputStream is = null;

			 try{
				
			 fc=(FileConnection)Connector.open("file:///x/x.x");
				
					
				}catch(Exception e){}
				try {
					fc = (FileConnection) Connector.open(url);
					is = fc.openInputStream();
					 b = new byte[(int) fc.fileSize()];//读取数据
					 is.read(b);
				} catch (Exception e) {
					e.printStackTrace();
					Alert ae = new Alert("读入文件", url + "\n读入失败" + "\n"
							+ e.getMessage(), null, AlertType.ERROR);
					MIDtxt.dp.setCurrent(ae);
					// fft.append("错误!" );

				} finally {
					try {
						fc.close();
						is.close();
					} catch (Exception e) {
					}
				}
			 }
			}.start();
					new Thread() {
					public void run() {
						String url = scp2;//路径
						FileConnection fc = null;
						OutputStream os = null;
						try{
						 fc=(FileConnection)Connector.open("file:///x/x.x");
						 }catch(Exception e){}
						try {
							fc = (FileConnection) Connector.open(url,
									Connector.READ_WRITE);
								if (fc.exists()) {
									fc.delete();
								}
								fc.create();
								os = fc.openOutputStream();
							os.write(b);
						} catch (Exception e) {
							e.printStackTrace();
							Alert ae = new Alert("输出文件", url + "\n输出失败" + "\n"
									+ e.getMessage(), null, AlertType.ERROR);
							MIDtxt.dp.setCurrent(ae);

							/* f.append("错误!" + e.getMessage()); */
						} finally {
							try {
								fc.close();
								os.close();
							} catch (Exception e) {
							}
						}
					}
				}.start();

			}
			}
		