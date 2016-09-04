// Source File Name:   URLEncoder.java
package kavax.microedition.lcdui;

import java.io.*;

public class URLEncoder {
	private URLEncoder() {
	}

	public static String encode(String s) {
		String str = null;
		str = encode(s, defaultEncName);
		return str;
	}

	public static String encode(String s, String enc) {
		boolean needToChange = false;
		boolean wroteUnencodedChar = false;
		int maxBytesPerChar = 10;
		StringBuffer out = new StringBuffer(s.length());
		ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(buf, enc);
		} catch (UnsupportedEncodingException ex) {
			try {
				writer = new OutputStreamWriter(buf, defaultEncName);
			} catch (UnsupportedEncodingException unsupportedencodingexception) {
			}
		}
		for (int i = 0; i < s.length(); i++) {
			int c = s.charAt(i);
			if (c < 256 && dontNeedEncoding[c]) {
				if (c == 32) {
					c = 43;
					needToChange = true;
				}
				out.append((char) c);
				wroteUnencodedChar = true;
				continue;
			}
			try {
				if (wroteUnencodedChar) {
					writer = new OutputStreamWriter(buf, enc);
					wroteUnencodedChar = false;
				}
				if (writer != null)
					writer.write(c);
				if (c >= 55296 && c <= 56319 && i + 1 < s.length()) {
					int d = s.charAt(i + 1);
					if (d >= 56320 && d <= 57343) {
						writer.write(d);
						i++;
					}
				}
				writer.flush();
			} catch (IOException e) {
				buf.reset();
				continue;
			}
			byte ba[] = buf.toByteArray();
			for (int j = 0; j < ba.length; j++) {
				out.append('%');
				char ch = forDigit(ba[j] >> 4 & 0xf, 16);
				if (isLetter(ch))
					ch -= ' ';
				out.append(ch);
				ch = forDigit(ba[j] & 0xf, 16);
				if (isLetter(ch))
					ch -= ' ';
				out.append(ch);
			}
			buf.reset();
			needToChange = true;
		}
		return needToChange ? out.toString() : s;
	}

	private static boolean isLetter(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}

	private static char forDigit(int digit, int radix) {
		if (digit >= radix || digit < 0)
			return '\0';
		if (radix < 2 || radix > 36)
			return '\0';
		if (digit < 10)
			return (char) (48 + digit);
		else
			return (char) (87 + digit);
	}

	private static boolean dontNeedEncoding[];
	private static String defaultEncName;
	static final int caseDiff = 32;
	public static final int MIN_RADIX = 2;
	public static final int MAX_RADIX = 36;
	static {
		defaultEncName = "";
		dontNeedEncoding = new boolean[256];
		for (int i = 97; i <= 122; i++)
			dontNeedEncoding[i] = true;
		for (int i = 65; i <= 90; i++)
			dontNeedEncoding[i] = true;
		for (int i = 48; i <= 57; i++)
			dontNeedEncoding[i] = true;
		dontNeedEncoding[32] = true;
		dontNeedEncoding[45] = true;
		dontNeedEncoding[95] = true;
		dontNeedEncoding[46] = true;
		dontNeedEncoding[42] = true;
		defaultEncName = System.getProperty("microedition.encoding");
		if (defaultEncName == null || defaultEncName.trim().length() == 0)
			defaultEncName = "UTF-8";
	}
}