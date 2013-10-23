


	import java.net.*;
	import java.io.*;
	public class UrlContentRead {
	
	    public static void main(String[] args) throws Exception {

	        URL oracle = new URL("http://en.wikipedia.org/wiki/Henry_the_Navigator");
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));

	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            System.out.println(inputLine);
	        in.close();
	    }
	}

