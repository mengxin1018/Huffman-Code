package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import domain.Code;

public class FileUtil {

	// 读取文件信息
	public static String readText(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/*
	 * 将对象保存到文件中
	 */
	public static void saveObjToFile(Code code,String fileName) {
		ObjectOutputStream oos = null;
		try {
			// 写对象流的对象
			oos = new ObjectOutputStream(new FileOutputStream(fileName));
			// 将Person对象p写入到oos中
			oos.writeObject(code);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭文件流
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * 从文件中读出对象
	 */
	public static Object getObjFromFile(String fileName) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fileName));
			return ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// 关闭文件流
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	 public static void WriteStringToFile(String filePath,String msg) {
	        try {
	            File file = new File(filePath);
	            PrintStream ps = new PrintStream(new FileOutputStream(file));
	            ps.print(msg);
	            ps.flush();
	            ps.close();
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
}
