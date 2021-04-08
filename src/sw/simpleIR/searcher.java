package sw.simpleIR;

<<<<<<< HEAD
public class searcher {

=======
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class searcher {
	HashMap<String, Float[]> keywordWeights;
	static final int RANK_NUM = 3;

	public void showHighSimilarityDoc(String filePath, String query) {
		Float[] result = CalcSim(query, filePath);
		
		int[] docId = new int[result.length];
		for(int i=0; i<result.length; i++) docId[i] = i;
		
		// Sorting
		for(int i=0; i<result.length; i++) {
			for(int j=i; j<result.length; j++) {
				if(result[i]<result[j]) {
					Float tmp = result[i];
					result[i] = result[j];
					result[j] = tmp;
					
					docId[i] = docId[i]^docId[j];
					docId[j] = docId[i]^docId[j];
					docId[i] = docId[i]^docId[j];
				}
				// 만일 유사도가 같으면, 문서 ID 가 빠른게 먼저 온다.
				else if(result[i].equals(result[j])) {
					if(docId[i] > docId[j]) {
						Float tmp = result[i];
						result[i] = result[j];
						result[j] = tmp;
						
						docId[i] = docId[i]^docId[j];
						docId[j] = docId[i]^docId[j];
						docId[i] = docId[i]^docId[j];
					}
				}
			}
		}
		
		String[] titles = getTitleFromXML(filePath);
		System.out.println("===== 유사도 검색 결과 =====");
		for(int i=0; i<RANK_NUM; i++) {
			System.out.println((i+1) + ". " + titles[docId[i]] + " (" +result[i] + ")");
		}
		
		
	}
	
	public Float[] CalcSim(String query, String filePath) {
		this.getWeightFromPost(filePath);
		String[] queryKeywords = this.extractKeywordUsingkkma(query);
		
		Float[] result = new Float[indexer.NUM];
		
		float queryKeywordWeight = 1.0f; // 모든 qeury 의 keyword 의 weight 는 1 로 고정
		for(int i=0; i<result.length; i++) { // index of result is Doc ID
			// calculate inner-product : Q * Doc
			Float tmp = 0.0f;
			for(String keyword: queryKeywords) {
				Float docWeight = this.keywordWeights.get(keyword)[i];
				tmp += queryKeywordWeight * docWeight;
			}
			result[i] = (float) (Math.round(tmp*100)/100.0);
		}
		return result;
	}
	
	public String[] getTitleFromXML(String postFilePath) {
		// get doc title using doc ID
		
		// find XML file path (./data/collection.xml)
		String[] tmp = postFilePath.split("/");
		String xmlFilePath = "";
		for(int i=0; i<tmp.length-1; i++) {
			xmlFilePath += tmp[i] + "/";
		}
		xmlFilePath += "collection.xml";
		
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
			xmlDocument = docBuilder.parse(xmlFilePath);
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
		
		String[] titles = new String[nl.getLength()];
		
		for(int i=0; i<nl.getLength(); i++) {
			// get body contents
			NodeList list = nl.item(i).getChildNodes();
			String originalTitle = list.item(0).getTextContent();
			titles[i] = originalTitle;
		}
		return titles;
	}
	
	public String[] extractKeywordUsingkkma(String str) {
		// init KeywordExtractor
		KeywordExtractor ke = new KeywordExtractor();
		// extract keywords
		KeywordList kl = ke.extractKeyword(str, true);
		
		String[] result = new String[kl.size()];
		int i=0;
		for(Keyword kwrd : kl)
			result[i++] = kwrd.getString();
		return result;
	}
	
	public void getWeightFromPost(String filePath) {
		// get weight of keyword from index.post
		
		Object object = null;
		try {
			FileInputStream fileStream = new FileInputStream(filePath);
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
		
		//System.out.println("읽어올 객체의 type : " + object.getClass());
		
		HashMap map = (HashMap) object;
		Iterator<String> it = map.keySet().iterator();
		
		keywordWeights = new HashMap<>(); 
		while(it.hasNext()) {
			String key = it.next();
			String value = (String) map.get(key);
			String[] values = value.split(" ");
			
			Float[] weights = new Float[indexer.NUM];
			Arrays.fill(weights, 0.0f);
			for(int i=0; i<values.length; i+=2) {
				weights[Integer.parseInt(values[i])] = Float.parseFloat(values[i + 1]);
			}
			keywordWeights.put(key, weights);
			
			/*
			System.out.print(key + " -> ");
			for(int i=0; i<weights.length; i++)
				System.out.print(weights[i] + " ");
			System.out.println();
			*/
		}
		
	}
	
>>>>>>> feature
}
