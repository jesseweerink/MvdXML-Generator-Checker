package nl.tue.generator;

import java.util.regex.*;

public class IfcSupportRule {
	
		public static Rule parse(String s) {
			
			Rule rule=new Rule();
			if (s.contains("->")) {
			Pattern arrow = Pattern.compile("(->)");
			String[] token = arrow.split(s);

	    	rule.setApplicableEntity(token[0]);
	       	String templateElements = token[1];
		
	       	if (templateElements.contains(".")){       	
	       	Pattern dot = Pattern.compile("\\.");
	       	String[] token2= dot.split(templateElements);

			for (int i=0;i<token2.length;i++){
				if (i<token2.length-1){
					rule.getTemplateElements().add(token2[i]);
				}
				else{
					
					if (token2[i].contains("=")){
						rule.setOperator("=");
						Pattern operator = Pattern.compile("=");
						String[] token3= operator.split(token2[i]);
						rule.getTemplateElements().add(token3[0]);
						rule.setValue(token3[1]);					
					}	
					
				}
			}
		
		
		}			
					 
		}
		
		/*	
	 	System.out.println(rule.getApplicableEntity());
		System.out.println(rule.getTemplateElements());
		System.out.println(rule.getOperator());
		System.out.println(rule.getValue());
		*/
			
		return rule;
	
		
}
}