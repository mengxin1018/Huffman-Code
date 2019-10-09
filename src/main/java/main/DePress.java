package main;

import java.util.Map;
import constants.FileConstants;
import domain.Code;
import domain.HuffmanCode;
import util.FileUtil;
import util.IOUtil;

public class DePress {

	public static void main(String[] args) {
		DePress depress = new DePress();
		String name = FileConstants.NAME;
		String fileName = FileConstants.BASE_FILE + "\\"+name+"\\"+name+".bin";
		try {
			String press = IOUtil.readHuffman(fileName);
			String codeName = FileConstants.BASE_FILE + "\\"+name+"\\code.txt";
			Code code = (Code) FileUtil.getObjFromFile(codeName);
			String msg = depress.deCompress(press, code.getDePressCode());
			String dePressName = FileConstants.BASE_FILE + "\\"+name+"\\dePress.txt";
			FileUtil.WriteStringToFile(dePressName, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String deCompress(String press, Map<String, String> depressCode) {
		HuffmanCode huffman = new HuffmanCode();
		System.out.println(">>>>>>>>>>赫夫曼解码<<<<<<<<<<");
		System.out.println("解压文本：\n" + press);
		huffman.printPressCode("解码表", depressCode);
		// 获得解压后的字符串
		String info = huffman.deComPress(depressCode, press);
		System.out.println("解压后：\n" + info);
		return info;
	}

}
