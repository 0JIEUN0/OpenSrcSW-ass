package sw.simpleIR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MovieAPI {
	String clientId = ""; //API_KEY
	String clientSecret = ""; //API_KEY
	String url = "https://openapi.naver.com/v1/search/movie.json";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MovieAPI movieApi = new MovieAPI();
		movieApi.searchMovie();
		
	}
	
	private void searchMovie() {

		Scanner scan = new Scanner(System.in);
		System.out.print("검색어를 입력하세요: ");
		String input = scan.nextLine();
		
		
		// openAPI Request (naver open API)
		String inputQuery = "";
		try {
			inputQuery = URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String apiURL = url + "?query=" + inputQuery;
		HttpURLConnection con;
		BufferedReader br = null;
		try {
			URL url = new URL(apiURL);
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			int responseCode = con.getResponseCode();
			
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scan.close();
		
		
		
		// print response
		String inputLine;
		//StringBuffer response = new StringBuffer();
		String responseString = "";
		try {
			while((inputLine = br.readLine())!=null) {
				//response.append(inputLine);
				responseString += inputLine;
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// JSON parsing
		// using json-simple library
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(responseString);
			JSONArray infoArray = (JSONArray) jsonObject.get("items");
			
			for(int i=0; i<infoArray.size(); i++) {
				System.out.println("=item_" + i + " ==========================================================");
				JSONObject itemObject = (JSONObject) infoArray.get(i);
				System.out.println("title:\t"+itemObject.get("title"));
				System.out.println("subtitle:\t"+itemObject.get("subtitle"));
				System.out.println("director:\t"+itemObject.get("director"));
				System.out.println("actor:\t"+itemObject.get("actor"));
				System.out.println("userRating:\t"+itemObject.get("userRating"));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
