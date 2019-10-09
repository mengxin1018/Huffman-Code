package domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class HuffmanCode {
	
	/**
	 * 计算字符的频率，将结果保存至map
	 * 
	 * @param msg
	 * @return
	 */
	public Map<String, Integer> getCount(String msg) {
		Map<String, Integer> count = new HashMap<String, Integer>();
		// 循环遍历字符串
		for (int i = 0; i < msg.length(); i++) {
			String s = msg.substring(i, i + 1);
			if (count.keySet().contains(s)) {
				Integer c = count.get(s);
				count.put(s, ++c);
			} else {
				count.put(s, 1);
			}
		}
		return count;
	}

	/**
	 * 利用字符的频率构建赫夫曼树
	 * 
	 * @param count
	 * @return
	 */
	public HuffmanTree getHuffmanTreeByCount(Map<String, Integer> count) {
		HuffmanTree tree = new HuffmanTree();
		// 将Map转化为List集合，集合中存放着Node节点
		List<Node> listNode = mapToListNode(count);
		// 将List结合转化为优先队列
		Queue<Node> nodeQueue = leastQueue(listNode);
		// 根据优先队列构建赫夫曼树
		tree = getHefumanTree(nodeQueue);
		return tree;
	}

	/**
	 * 将Map转化为List集合，集合中存放着Node节点
	 * 
	 * @param map
	 * @return
	 */
	public List<Node> mapToListNode(Map<String, Integer> map) {
		List<Node> listNode = new ArrayList<Node>();
		Set<String> valueSet = map.keySet();
		Node node = null;
		for (String value : valueSet) {
			node = new Node();
			// 设置Node节点形状为方形
			node.setShape(Node.SQUARE);
			node.setValue(value);
			node.setCount(map.get(value));
			listNode.add(node);
		}
		return listNode;
	}

	/**
	 * 将List转化为最小优先队列（最小堆），以Node节点的count排序
	 * 
	 * @param list
	 * @return
	 */
	public Queue<Node> leastQueue(List<Node> list) {

		// 设置节点之间的比较器，按照节点中count值大小比较，自然顺序排序
		// 设置优先队列的初始容量为26
		Queue<Node> queue = new PriorityQueue<Node>(26, new Comparator<Node>() {

			public int compare(Node o1, Node o2) {
				// TODO Auto-generated method stub
				return o1.getCount() - o2.getCount();
			}
		});
		for (Node n : list) {
			queue.add(n);
		}
		return queue;
	}

	/**
	 * 利用优先队列来构建赫夫曼树
	 * 
	 * @param queue
	 */
	public HuffmanTree getHefumanTree(Queue<Node> queue) {
		HuffmanTree tree = new HuffmanTree();
		tree.setNodeQueue(new PriorityQueue<Node>(queue));
		Node right = null;
		Node left = null;
		Node freq = null;
		int length = queue.size();
		// 遍历此优先队列，不断将count最小的两个节点合并为一个新节点，同时将新节点插入优先队列中
		for (int i = 0; i < length - 1; i++) {
			freq = new Node();
			// 调用优先队列的poll方法，取出并删除此队列的首部
			left = queue.poll();
			right = queue.poll();
			freq.setCount(left.getCount() + right.getCount());
			freq.setLeft(left);
			freq.setRight(right);
			left.setParent(freq);
			right.setParent(freq);
			queue.add(freq);
		}
		Node root = queue.poll();
		root.setParent(null);
		// 设置赫夫曼树的根节点
		tree.setRoot(root);
		tree.setSiez(length);
		return tree;
	}

	/**
	 * 通过赫夫曼树得到每个字符的赫夫曼编码，将结果放入Map中 情况1：key代表字符，value代表该字符的赫夫曼编码
	 * 情况2：key代表赫夫曼编码，value代表该编码所代表的字符
	 * 
	 * @param tree
	 * @return
	 */
	public Map<String, Map<String, String>> getCompressCodeMap(HuffmanTree tree) {
		Map<String, Map<String, String>> resp = new HashMap<String, Map<String, String>>();
		Map<String, String> press = new HashMap<String, String>();
		Map<String, String> depress = new HashMap<String, String>();
		Node root = tree.getRoot();
		Queue<Node> nodeQueue = tree.getNodeQueue();
		Node n = null;
		String code = null;
		String str = null;
		while (!nodeQueue.isEmpty()) {
			code = "";
			n = nodeQueue.poll();
			str = n.getValue();
			while (n != null) {
				if (n.isRight())
					code = "1" + code;
				else if (n.isLeft())
					code = "0" + code;
				n = n.getParent();
				if (n == root)
					break;
			}
			press.put(str, code);
			depress.put(code, str);
		}
		resp.put("press", press);
		resp.put("dePress", depress);
		return resp;
	}

	/**
	 * 根据赫夫曼编码map得到编码后的编码表达式
	 * 
	 * @param msg
	 * @param map
	 * @return
	 */
	public String getFileByCode(String msg, Map<String, String> map) {
		String str = "";
		for (int i = 0; i < msg.length(); i++) {
			String s = msg.substring(i, i + 1);
			str += map.get(s);
		}
		return str;
	}

	/**
	 * 打印Map
	 */
	public <T> void printPressCode(String print, Map<String, T> map) {
		System.out.println(print + "：");
		Set<String> key = map.keySet();
		for (String s : key) {
			System.out.print(s + ":" + map.get(s).toString() + "\t");
		}
		System.out.println();
	}

	/**
	 * 根据赫夫曼编码解码
	 * 
	 * @param map
	 * @param msg
	 * @return
	 */
	public String deComPress(Map<String, String> map, String msg) {
		String info = "";
		String s = "";
		boolean flag = false;
		for (int i = 0; i < msg.length(); i++) {
			if (flag == false) {
				s += msg.substring(i, i + 1);
			} else {
				s = msg.substring(i, i + 1);
			}
			String value = map.get(s);
			if (value != null) {
				info += value;
				flag = true;
			} else {
				flag = false;
			}
		}
		return info;
	}
}
