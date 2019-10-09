package domain;

import java.util.Queue;

public class HuffmanTree{
	
	private Node root;
	private int siez;
	private Queue<Node> nodeQueue;

	public Queue<Node> getNodeQueue() {
		return nodeQueue;
	}

	public void setNodeQueue(Queue<Node> nodeQueue) {
		this.nodeQueue = nodeQueue;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public int getSiez() {
		return siez;
	}

	public void setSiez(int siez) {
		this.siez = siez;
	}
}
