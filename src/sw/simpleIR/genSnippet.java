package sw.simpleIR;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class genSnippet {
	ArrayList<String> contents = new ArrayList<>();
	
	public static void main(String[] args) {
		if(args[0].equals("-f")) {
			genSnippet mid = new genSnippet();
			mid.runMidterm(args[1], args[3]);
		}
	}
	
	public void runMidterm(String filePath, String inputQuery) {
		// read file from input.txt (filePath)
		this.fileScan(filePath);
		
		// find max row
		int max = 0;
		int maxIdx = 0;
		String[] query = inputQuery.split(" ");
		
		for(int i=0; i<this.contents.size(); i++) {
			int count = 0;
			String str = " " + this.contents.get(i) + " ";
			
			for(String inputQ : query) {
				if(str.contains(" " + inputQ + " ")) count++; 
			}
			if(max < count) {
				max = count;
				maxIdx = i;
			}
		}
		
		// print result
		System.out.println(this.contents.get(maxIdx));
		
	}
	
	public void fileScan(String filePath) {
		File path = new File(filePath);
		try {
			Scanner scan = new Scanner(path);
			while(scan.hasNext()) {
				this.contents.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
