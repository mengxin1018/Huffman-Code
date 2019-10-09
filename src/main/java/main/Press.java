package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import constants.FileConstants;
import domain.Code;
import domain.HuffmanCode;
import domain.HuffmanTree;
import util.FileUtil;
import util.IOUtil;

public class Press {

	public static void main(String[] args) {
		String name = FileConstants.NAME;
		String fileName = FileConstants.BASE_FILE + "\\"+name+".txt";
		Press press = new Press();
		try {
			Map<String,Object> result = press.compress(fileName);
			Code code = (Code) result.get("code");
			String pressCode = result.get("pressCode").toString();
			File file = (File) result.get("file");
			press.codeToFile(file, pressCode, code);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String,Object> compress(String fileName) throws IOException {
		HuffmanCode huffman = new HuffmanCode();
		Map<String,Object> resp = new HashMap<>();
		Code code = new Code();
		File file = new File(fileName);
		// 读取文件内容
		String msg = FileUtil.readText(file);
		// 获取各个字符的频率
		Map<String, Integer> count = huffman.getCount(msg);
		// 获得赫夫曼树
		HuffmanTree huffmanTree = huffman.getHuffmanTreeByCount(count);
		// 获取赫夫曼编码Map
		Map<String, Map<String, String>> codeMap = huffman.getCompressCodeMap(huffmanTree);
		Map<String,String> pressMap = codeMap.get("press");
		Map<String,String> dePressMap = codeMap.get("dePress");
		code.setDePressCode(dePressMap);
		// 得到压缩后的赫夫曼编码表示
		String press = huffman.getFileByCode(msg,pressMap);
		System.out.println(">>>>>>>>>>赫夫曼编码<<<<<<<<<<"+"\n原文本：\n" + msg);
		huffman.printPressCode("字符与频率", count);
		// 打印赫夫曼编码表
		huffman.printPressCode("编码表", dePressMap);
		System.out.println("压缩后：\n" + press);
		resp.put("code", code);
		resp.put("pressCode", press);
		resp.put("file", file);
		return resp;
	}

	
	public File codeToFile(File file,String press,Code code) throws Exception{
		String path = file.getName();
		String[] s = path.split("\\.");
		File parent = file.getParentFile();
		// 创建存放压缩文件的文件夹
		String pressFileName = parent.getPath()+"\\"+s[0];
		File pressFile = new File(pressFileName);
		pressFile.mkdirs();
		// 将压缩内容写入二进制文件中
		String byteFileName = pressFile.getPath()+"\\"+s[0]+".bin";
		IOUtil.writeHuffman(press, byteFileName);
		// 将code保存到code.txt中
		String codeFileName = pressFile.getPath()+"\\code.txt";
		FileUtil.saveObjToFile(code,codeFileName);
		
		return pressFile;
	}
}
