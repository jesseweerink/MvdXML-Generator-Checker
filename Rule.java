package nl.tue.generator;

import java.util.ArrayList;

public class Rule {
	
	private String applicableEntity;
	private ArrayList<String> templateElements=new ArrayList<String>();
	private String operator;
	private String value;
	
	public String getApplicableEntity() {
		return applicableEntity;
	}
	public void setApplicableEntity(String applicableEntity) {
		this.applicableEntity = applicableEntity;
	}
	public ArrayList<String> getTemplateElements() {
		return templateElements;
	}
	public void setTemplateElements(ArrayList<String> templateElements) {
		this.templateElements = templateElements;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
