package util;

import java.io.*;

public class IOUtil {

	// 每次传进去的字符串都是8个字符长度，刚好能够表示一个byte
	public static byte encode(String s) {
		int a = 0;
		for (int i = 0; i < 8; i++) {
			char ch = s.charAt(i);
			a = a << 1;
			if (ch == '1') {
				a = a | 0x1;
			}
		}
		return (byte) a;
	}

	// 上一步的逆操作
	public static String decode(byte b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			sb.append((b & (0x1 << (7 - i))) > 0 ? '1' : '0');
		}
		return sb.toString();
	}

	/**
	 * 将二进制串写入指定文件
	 * @param s
	 * @param fileName
	 * @throws Exception
	 */
	public static void writeHuffman(String s,String fileName) throws Exception {
		// 因为huffman编码字符串不总是8个字符的倍数，那么我们不足8时补0，并记录我们到底补了几个。
		// 我们把补位数放在文件的第一个字节
		int z = 8 - s.length() % 8;
		if (z == 8) {
			z = 0;
		}
		byte[] buffer = new byte[1024];
		buffer[0] = (byte) z;
		int pos = 1, nBytes = (int) (Math.ceil(s.length() / ((double) 8)));
		File f = new File(fileName);
		FileOutputStream os = new FileOutputStream(f);
		for (int i = 0; i < nBytes; i++) {
			String ss;
			if (s.length() >= (i + 1) * 8) {
				ss = s.substring(i * 8, (i + 1) * 8);
			} else {
				ss = s.substring(i * 8);
				while (ss.length() < 8) {
					ss = new StringBuilder(ss).append('0').toString();
				}
			}
			buffer[pos] = encode(ss);
			pos++;
			if (pos == 1024) {
				os.write(buffer);
				pos = 0;
			}
		}
		if (pos > 0) {
			os.write(buffer, 0, pos);
		}
		os.close();
	}

	/**
	 * 读取指定的文件的内容，并转化为二进制流
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String readHuffman(String fileName) throws Exception {
		File f = new File(fileName);
		FileInputStream fs = new FileInputStream(f);
		byte[] buffer = new byte[(int) f.length()];
		int len = 0;
		StringBuilder sb = new StringBuilder();
		byte z = (byte) fs.read();
		while ((len = fs.read(buffer)) != -1) {
			for (int i = 0; i < len; i++) {
				sb.append(decode(buffer[i]));
			}
		}
		fs.close();
		return sb.substring(0, sb.length() - z);
	}

}
