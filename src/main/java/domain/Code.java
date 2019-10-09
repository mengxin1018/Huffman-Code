package domain;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class Code implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 8041289016056454340L;
	// 解码表
	private Map<String,String> dePressCode;
	
}
