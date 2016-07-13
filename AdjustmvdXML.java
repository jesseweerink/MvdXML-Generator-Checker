package nl.tue.generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.util.UUID;

public class AdjustmvdXML {
	
	private File file;
	private Document doc;
	
    public AdjustmvdXML(File file) throws ParserConfigurationException, SAXException, IOException{
    	this.file=file;
    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		this.doc = docBuilder.parse(file);
    }
	
    public AdjustmvdXML(InputStream input) throws ParserConfigurationException, SAXException, IOException{
    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		this.doc = docBuilder.parse(input);
    }

    public void apply(Rule rule) {
			
		NodeList list= doc.getElementsByTagName("Templates");
				
		Element templates = (Element) list.item(0);
		
		Element Roots=(Element)doc.getElementsByTagName("Roots").item(0);
		
		UUID uuid = UUID.randomUUID();
		String randomUUIDCTT = uuid.toString();
		
		if (rule.getTemplateElements().contains("IfcPropertySingleValue")){
			createConcept("7a13d17c-20a0-4117-8abc-050d0c67e6ec",doc,Roots,rule);
			//System.out.println(rule.getTemplateElements().contains("IfcPropertySingleValue"));
		}
		
		else if (rule.getTemplateElements().contains("IfcQuantityArea")){
			createConcept("46fba748-b6c7-48a3-b81a-af259c83aaa4",doc,Roots,rule);
		}
		
		else if (rule.getTemplateElements().contains("IfcQuantityVolume")){
			createConcept("0f7f7621-dc0a-4ab8-8118-64ead2ee3cca",doc,Roots,rule);
		}
		
		else if (rule.getTemplateElements().contains("IfcQuantityLength")){
			createConcept("6816f366-c607-43f4-a96a-b57be006ff6d",doc,Roots,rule);
			System.out.println(rule.getTemplateElements().contains("IfcQuantityLength"));
		}
		
		else{createTemplate(randomUUIDCTT, doc,templates,rule);
		createConcept(randomUUIDCTT, doc,Roots,rule);	}		
	  
	}
	
	public void generateMvd(String outputPath){
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
	
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		File newFile=new File(outputPath);
		StreamResult result = new StreamResult(newFile);
		transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
			
	
	public void createTemplate(String randomUUIDCTT, Document doc,Element Templates,Rule rule){
		Element ConceptTemplate=doc.createElement("ConceptTemplate");
		Templates.appendChild(ConceptTemplate);
		
		ConceptTemplate.setAttribute("applicableEntity", rule.getTemplateElements().get(0));
		ConceptTemplate.setAttribute("applicableSchema", "IFC4");
		ConceptTemplate.setAttribute("name", "");
		ConceptTemplate.setAttribute("uuid", randomUUIDCTT);
		
		Element Rules = doc.createElement("Rules");
		ConceptTemplate.appendChild(Rules);
		
		ArrayList<Element> rules=new ArrayList<Element>();
		for (int i=1;i<rule.getTemplateElements().size();i++){
			
			int d=rule.getTemplateElements().size()-1;
			int e=d-1;
			
			if (i==1){
				Element attributeRule=doc.createElement("AttributeRule");
				attributeRule.setAttribute("AttributeName", rule.getTemplateElements().get(i));
				attributeRule.setAttribute("Cardinality", "_asSchema");
				rules.add(attributeRule);
			
				}
			
			 else if (i==d){
				Element attributeRules=doc.createElement("AttributeRules");
				rules.add(attributeRules);
				Element attributeRule=doc.createElement("AttributeRule");
				attributeRule.setAttribute("AttributeName", rule.getTemplateElements().get(i));
				attributeRule.setAttribute("Cardinality", "_asSchema");
				attributeRule.setAttribute("RuleID", rule.getTemplateElements().get(e)+rule.getTemplateElements().get(d));
				rules.add(attributeRule);
						}
							
			 else if(i%2==1){
				Element attributeRules=doc.createElement("AttributeRules");
				rules.add(attributeRules);
				Element attributeRule=doc.createElement("AttributeRule");
				attributeRule.setAttribute("AttributeName", rule.getTemplateElements().get(i));
				attributeRule.setAttribute("Cardinality", "_asSchema");
				rules.add(attributeRule);
		}
	
			else if(i%2==0){
				Element entityRules=doc.createElement("EntityRules");
				rules.add(entityRules);
				Element entityRule=doc.createElement("EntityRule");
				entityRule.setAttribute("EntityName", rule.getTemplateElements().get(i));
				rules.add(entityRule);
				entityRule.setAttribute("Cardinality", "_asSchema");
					}
				}
		
			for(int i=0;i<rules.size();i++){
			if(i==0){
				Rules.appendChild(rules.get(i));
			}
			else{rules.get(i-1).appendChild(rules.get(i));
			}
		
		}
	
			System.out.println("ConceptTemplate added to mvdXML");
			}
	
	
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public void createConcept(String randomUUIDCTT, Document doc,Element Roots,Rule rule){
		
		UUID uuid = UUID.randomUUID();
		String randomUUIDConceptRoot = uuid.toString();
		
		Element ConceptRoot=doc.createElement("ConceptRoot");
		Roots.appendChild(ConceptRoot);
		ConceptRoot.setAttribute("uuid", randomUUIDConceptRoot);
		ConceptRoot.setAttribute("name", "");
		ConceptRoot.setAttribute("applicableRootEntity", rule.getApplicableEntity());
					
		Element Concepts=doc.createElement("Concepts");
		ConceptRoot.appendChild(Concepts);
		
		UUID uuid1 = UUID.randomUUID();
		String randomUUIDConcept = uuid1.toString();
		Element Concept=doc.createElement("Concept");
		Concepts.appendChild(Concept);
		Concept.setAttribute("uuid", randomUUIDConcept);
		Concept.setAttribute("name", "");
		Concept.setAttribute("override", "false");
						
		Element Definitions=doc.createElement("Definitions");
		Concept.appendChild(Definitions);
						
		Element Definition=doc.createElement("Definition");
		Definitions.appendChild(Definition);
		
		Element Body=doc.createElement("Body");
		Definition.appendChild(Body);
		
		Element Template=doc.createElement("Template");
		Concept.appendChild(Template);
		Template.setAttribute("ref", randomUUIDCTT);
		
		Element Requirements=doc.createElement("Requirements");
		Concept.appendChild(Requirements);
						
		Element Requirement=doc.createElement("Requirement");
		Requirements.appendChild(Requirement);
				
		Element Rules=doc.createElement("Rules");
		Concept.appendChild(Rules);
		
		Element TemplateRule=doc.createElement("TemplateRule");
		Rules.appendChild(TemplateRule);
		
		int d=rule.getTemplateElements().size()-1;
		int e=d-1;
		
		String te = rule.getTemplateElements().get(e)+rule.getTemplateElements().get(d);
		String v = "[Value]='"; 
		String gv =  rule.getValue();
		String a = "'";
		String tr = te+v+ gv + a;
		TemplateRule.setAttribute("Parameters", tr);
		
		
		System.out.println("Concept added to mvdXML");
				   }

	
				
			}
