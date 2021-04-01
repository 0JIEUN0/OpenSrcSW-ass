package sw.simpleIR;

public class kuir {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * run first assignment :
		 * $ java kuir -c ./HtmlFiles
		 * run second assignment :
		 * $ java kuir -k ./data/collection.xml
		 * run third assignment:
		 * $ java kuir -i ./data/index.xml
		 * run 4th assignment:
		 * $ java kuir -s ./data/index.post -q QUERY
		 */
		
		
		if(args[0].equals("-c")) {
			makeCollection maker = new makeCollection();
			maker.crawlingHtmlToXml(args[1]);
		}else if(args[0].equals("-k")){
			makeKeyword maker = new makeKeyword();
			maker.makeXmlFile_keywords(args[1]);
		}else if(args[0].equals("-i")) {
			indexer idxer = new indexer(args[1]);
			idxer.makeInvertedFile();
		}else if(args[0].equals("-s")) {
			searcher ser = new searcher();
			ser.showHighSimilarityDoc(args[1], args[3]);
		}
	}

}
