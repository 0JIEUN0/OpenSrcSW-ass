package sw.simpleIR;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
//import org.jsoup.nodes.Document
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeKeyword {

	public void makeXmlFile_keywords(String filePath) {
		// parse XML, extract keywords (using kkma), and write to file (XML)
		
		// get XML file
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document xmlDocument = null;
		try {
			xmlDocument = docBuilder.parse(filePath);
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// find root
		Element root = xmlDocument.getDocumentElement();
		NodeList nl = root.getChildNodes();
		
		
		// make new Document (-> index.xml)
		Document doc = docBuilder.newDocument();
		
		//docs element
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		
		for(int i=0; i<nl.getLength(); i++) {
			Node node = nl.item(i);
			String originalId = ((Element)node).getAttribute("id");
			
			//doc id element
			Element docE = doc.createElement("doc");
			docE.setAttribute("id", originalId);
			docs.appendChild(docE);
			
			NodeList list = node.getChildNodes();
			for(int j=0; j<list.getLength(); j+=2) {
				// get title, body content from xml
				String originalTitle = list.item(j).getTextContent();
				String originalBody = list.item(j+1).getTextContent();
				
				// title element
				Element title = doc.createElement("title");
				title.appendChild(doc.createTextNode(originalTitle));
				docE.appendChild(title);
				
				// body element (extract keywords using kkma)
				Element body = doc.createElement("body");
				String extractKeywordsStr = this.extractKeywordUsingkkma(originalBody);
				body.appendChild(doc.createTextNode(extractKeywordsStr));
				docE.appendChild(body);
			}
		}
		
		
		// Write XML file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		String xmlFilePath = "./data/index.xml";
		
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result;
		try {
			result = new StreamResult(new FileOutputStream(new File(xmlFilePath)));
			transformer.transform(source, result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		System.out.println("XML 파일로 성공적으로 저장하였습니다. (" + xmlFilePath + ")");
	}

	public String extractKeywordUsingkkma(String str) {
		// init KeywordExtractor
		KeywordExtractor ke = new KeywordExtractor();
		// extract keywords
		KeywordList kl = ke.extractKeyword(str, true);
		// make required string format (keyword:count#)
		String result = "";
		for(Keyword kwrd : kl)
			result += kwrd.getString() + ":" + kwrd.getCnt() + "#";
		return result;
	}
}
