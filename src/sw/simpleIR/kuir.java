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
		}
	}

}
