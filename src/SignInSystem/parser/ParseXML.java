package SignInSystem.parser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.events.Attribute;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import SignInSystem.component.ParseResult;
import SignInSystem.component.PersonData;
import SignInSystem.component.ReferenceTitle;


public class ParseXML {

	public ParseResult parse(String filePath){
		
		ReferenceTitle rt=null;
		ArrayList<PersonData> personDataList=new ArrayList<PersonData>();
		
		
		HashMap<String,String> key=new HashMap<String,String>();
		
		try {
			
			//Creating A Java DOM XML Parser
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			
			//Parsing XML with a Java DOM Parser
			Document document = builder.parse(new FileInputStream(filePath));
			
			//Get root Element called Document Element
			Element rootElement = document.getDocumentElement();
			
			//Get ChildNode
			NodeList nodes = rootElement.getChildNodes();
			
			for(int i=0;i<nodes.getLength();i++){
				Node node = nodes.item(i);
				
				if(node instanceof Element){
					Element element=(Element)node;
					String tagName=element.getTagName();
					
					//Set the Title column
					if(tagName.equals("Titles"))
						rt=setTitle(element);
						
					if(tagName.equals("Record"))
						setRecord(element,personDataList);
						
					

				}
			}
			
			
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return new ParseResult(rt,personDataList);
	}
	
	
	private void setRecord(Element element,ArrayList<PersonData> personDataList){
		NodeList childnodes = element.getChildNodes();
		PersonData persondata=new PersonData();
		
		for(int i = 0 ; i < childnodes.getLength() ; i++)
		{	
			
			
			String tagName=""; 
			String content="";
			
			Node node = childnodes.item(i);
			
			//test whether it is Element Type or not
			if(node instanceof Element){
				//Get TagName
				Element dataElement = (Element)node;
				tagName = dataElement.getTagName();
			}
			
			//parse content
			NodeList contentNodeList = node.getChildNodes();
			if(contentNodeList.item(0) instanceof Node){
				Node contentNode = (Node)contentNodeList.item(0);
				content=contentNode.getNodeValue();
			}
			
			persondata.put(tagName,content);
			
			
		}
		personDataList.add(persondata);
		
		
		
	}
	
	private ReferenceTitle setTitle(Element element){
		ReferenceTitle rt=new ReferenceTitle();
		NodeList childnodes = element.getChildNodes();
		for(int i = 0 ; i < childnodes.getLength() ; i++)
		{
			Node node = childnodes.item(i);
			if(node instanceof Element){
				Element titleElement = (Element)node;
				String name = titleElement.getAttribute("name");
				String value = titleElement.getAttribute("value");
				rt.put(name, value);
			}
				
			
			
		}
		return rt;
		
	}
	
	
	

}
