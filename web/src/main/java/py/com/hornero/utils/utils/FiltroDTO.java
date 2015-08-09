package py.com.hornero.utils.utils;

import java.util.List;


public class FiltroDTO {
	
	private String data;
	
	private List<ReglaDTO> rules;
	
	private String groupOp;
	
	
	/**
	 * 
	 */
	public FiltroDTO() {
		super();
	}


	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}


	

	public List<ReglaDTO> getRules() {
		return rules;
	}


	public void setRules(List<ReglaDTO> rules) {
		this.rules = rules;
	}


	/**
	 * @return the groupOp
	 */
	public String getGroupOp() {
		return groupOp;
	}


	/**
	 * @param groupOp the groupOp to set
	 */
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
		
}
