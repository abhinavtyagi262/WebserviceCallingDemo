package com.abhinav;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class WebserviceCallingClass {
	private static Logger log = Logger.getLogger(WebserviceCallingClass.class.getName());

	
	public static void main(String[] args) throws IOException {

		String requestJson = "{\"name\":\"name\"}";	//RequestJson is: {"name":"name"}
		System.out.println("RequestJson is: " + requestJson);
		
		//Calling Webservice
		WebserviceCallingClass webserviceCallingClass = new WebserviceCallingClass();
		String responseJson = webserviceCallingClass.WebserviceCallingMethod(requestJson);
		
		System.out.println("ResponseJson is: " + responseJson);
	}
	
	
	
	
	public String WebserviceCallingMethod(String requestJson) throws IOException {
		URL url = new URL("http://localhost:8080/WebserviceCreationDemo/ApplicationPath/WebservicePath/");
		URLConnection urlConnection = url.openConnection();
		
		log.info("Connection to Webservice formed is: " + urlConnection);
		System.out.println("Connection to Webservice formed is: " + urlConnection);
		
		urlConnection.setDoOutput(true);			//DoOutput must be true for POST Request and false for GET Request
		urlConnection.setRequestProperty("Content-Type", "application/json");	//Request Format is application/json
		urlConnection.setRequestProperty("Accept", "application/json");			//Response Format is application/json
		urlConnection.setConnectTimeout(5000);		//ConnectTimeout is 5000 ms = 5 seconds
		urlConnection.setReadTimeout(5000);			//ReadTimeout is 5000 ms = 5 seconds
		
		
		System.out.println("RequestJson is: " + requestJson);
		System.out.println("Passing RequestJson in Webservice");
		OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());	//POST Request
		out.write(requestJson);	
		out.close();
		
		System.out.println("Receiving ResponseJson from Webservice");
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = null;
			while((line = in.readLine())!=null) {
				stringBuilder.append(line);
			}
			
			String responseJson = stringBuilder.toString();
			System.out.println("ResponseJson is: " + responseJson);
			
			return responseJson;
		}	
		catch(Exception e) {
			log.info("Error occurred while receiving ResponseJson from Webservice: " + e );
			return null;
		}		
	}

}
