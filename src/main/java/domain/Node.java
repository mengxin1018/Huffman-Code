package domain;

import lombok.Data;

@Data
public class Node {

	// 父节点
	private Node parent;
	// 左孩子节点
	private Node left;
	// 右孩子节点
	private Node right;
	// 圆形
	public static final boolean CIRCLE = false;
	// 矩形
	public static final boolean SQUARE = true;
	// 频率
	private Integer count = 0;
	// 字符
	private String value;
	// 节点默认为圆形
	private boolean shape = CIRCLE;

	/**
	 * 判断该节点是否为右节点
	 * @return
	 */
	public boolean isRight(){
		boolean flag = false;
		if(this.getParent().getRight()==this)
			flag = true;
		return flag;
	}
	
	/**
	 * 判断该节点是否为右节点
	 * @return
	 */
	public boolean isLeft(){
		boolean flag = false;
		if(this.getParent().getLeft()==this)
			flag = true;
		return flag;
	}

	public String getValue() {
		if (this.shape == CIRCLE)
			return null;
		return value;
	}

	public void setValue(String value) {
		if (this.shape != CIRCLE)
			this.value = value;
	}
}
