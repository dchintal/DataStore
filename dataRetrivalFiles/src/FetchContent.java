


	public class FetchContent  
	{  
	    public static void main( String args[])  
	    {  
	        String sToken    = "match" ;  
	        String sFilename = "example.txt" ;  
	        try   
	        {  
	            String sGrepCommand = "bzgrep -i '"+sToken+"' " + sFilename.trim() + ">" + sFilename.trim();  
	              
	            System.out.println("Command: " + sGrepCommand);  
	              
	            // Execute UNIX command  
	            Process pGrep = Runtime.getRuntime().exec(sGrepCommand);  
	              
	            int iTerminationStatus = pGrep.waitFor();  
	              
	            if (pGrep.exitValue() != 0)   
	            {  
	                System.out.println("Grep Command failed. Exit value=" +   
	                            pGrep.exitValue());  
	            }  
	        }  
	        catch( Exception ex)  
	        {  
	        }  
	    }  
	}  

