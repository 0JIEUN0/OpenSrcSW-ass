package sw.simpleIR;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {
	String filePath;
	final static int NUM = 5;
	private String[] bodys;

	public indexer(String filePath) {
		super();
		this.filePath = filePath;
	}
	
	public void makeInvertedFile() {
		this.parseXmlToString();
		this.hashMapToInversedFile();
		this.printPostFile();
	}
	
	public void parseXmlToString() {
		// parse XML and make String[]
		
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
		
		bodys = new String[nl.getLength()];
		
		for(int i=0; i<nl.getLength(); i++) {
			// get body contents
			NodeList list = nl.item(i).getChildNodes();
			String originalBody = list.item(1).getTextContent();
			bodys[i] = originalBody;
		}
	}
	
	public void hashMapToInversedFile () {		
		HashMap<String, Keyword> map = new HashMap<>();
		for(int i=0; i<bodys.length; i++) {
			String[] tmp = bodys[i].split("#");
			for(String t : tmp) {
				String[] pair = t.split(":");
				String key = pair[0];
				String tfi = pair[1];
				
				if(map.containsKey(key)) {
					map.get(key).setFrequency(i, Integer.parseInt(tfi));
				}else {
					Keyword keyword = new Keyword(key);
					keyword.setFrequency(i, Integer.parseInt(tfi));
					map.put(key, keyword);
				}
			}
		}
		
		// Write to File (index.post)
		ObjectOutputStream outputStream = null;
		try {
			FileOutputStream fileStream = new FileOutputStream("./data/index.post");
			outputStream = new ObjectOutputStream(fileStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, String> result = new HashMap();
		Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()){
            String key = keys.next();
            Keyword keyword = map.get(key);
            result.put(key, keyword.getWeightString());
        }
        
        try {
        	outputStream.writeObject(result);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void printPostFile() {
		Object object = null;
		try {
			FileInputStream fileStream = new FileInputStream("./data/index.post");
			ObjectInputStream inputStream = new ObjectInputStream(fileStream);
			object = inputStream.readObject();
			inputStream.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("읽어올 객체의 type : " + object.getClass());
		
		HashMap map = (HashMap) object;
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = (String) map.get(key);
			System.out.println(key + " -> " + value);
			/*
			ArrayList value = (ArrayList) map.get(key);
			System.out.print(key + " -> ");
			for(int i=0; i<value.size(); i++)
				System.out.print(value.get(i) + " ");
				*/
		}
		
		
	}
	static class Keyword {
		String keyword;
		int[] tf;
		int df = 0;
		
		public Keyword(String keyword) {
			super();
			this.keyword = keyword;
			tf = new int[NUM];
			Arrays.fill(tf, 0);
		}
		
		public void setFrequency(int y, int count) {
			tf[y] = count;
			df++;
		}
		
		public ArrayList<Float> getWeightList() {
			ArrayList<Float> list = new ArrayList<>();
			float idf = (float) Math.log(NUM/df);
			for(int i=0; i<NUM; i++) {
				if(tf[i] == 0) continue;
				list.add((float) i);
				list.add((float) (tf[i]*idf));
			}
			return list;
		}
		
		public String getWeightString () {
			String str = "";
			//float idf = (float) Math.log(NUM/df);
			float idf = (float) Math.log((float)NUM/(float)df);
			for(int i=0; i<NUM; i++) {
				if(tf[i] == 0) continue;
				str += i + " ";
				str += String.format("%.2f", (tf[i]*idf)) + " ";
			}
			return str;
		}
		
	}
}
