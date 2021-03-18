package sw.simpleIR;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
//import org.jsoup.nodes.Document
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeCollection {
	ArrayList<String> htmlContents = new ArrayList<>();
	
	public void crawlingHtmlToXml(String dirPath) {
		this.getFolderFiles(dirPath);
		this.makeXmlFile_basic();
	}
	
	public void showAll() {
		for(String content : htmlContents) System.out.println(content);
	}
	
	public void makeXmlFile_basic() {
		// parse HTML content (using Jsoup) and write to file (XML)
		
		// make Document
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = docBuilder.newDocument();
		
		
		//docs element
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		
		//All Html
		for(int i=0; i<this.htmlContents.size(); i++) {
			//doc id element
			Element docE = doc.createElement("doc");
			docE.setAttribute("id", Integer.toString(i));
			docs.appendChild(docE);
						
			//parse html
			org.jsoup.nodes.Document docJsoup = Jsoup.parse(htmlContents.get(i));
			
			//title element
			Element title = doc.createElement("title");
			String originalTitle = docJsoup.getElementsByTag("title").text();
			title.appendChild(doc.createTextNode(originalTitle));
			docE.appendChild(title);
			
			//body element
			Element body = doc.createElement("body");
			String originalBody = docJsoup.getElementById("content").text();
			body.appendChild(doc.createTextNode(originalBody));
			docE.appendChild(body);
		}
		
		// Write XML file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		String xmlFilePath = "./data/collection.xml";
		
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
	
	public void getFolderFiles (String dirPath) {
		// get content of HTML files from folder (Recursively)
		
		File path = new File(dirPath);
		File[] flist = path.listFiles();
		
		for(File file : flist) {
			if(file.isDirectory()) this.getFolderFiles(file.getPath());
			else {
				try {
					Scanner scan = new Scanner(file);
					String content = "";
					while(scan.hasNext()) {
						content += scan.nextLine();
					}
					this.htmlContents.add(content);
					scan.close();
				} catch (FileNotFoundException e) {
					e.getStackTrace();
					System.out.println("파일 경로를 확인해주세요.");
				} catch (IOException e) {
					e.getStackTrace();
					System.out.println("파일 입출력 오류");
				}
			}
		}
		
	}
}
