
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

//import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
class ReadContent
{   
        public static int EOF=0;
        public static void main( String args[]) throws IOException
        {
        	System.out.print("Enter File Name => ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String fileName = br.readLine();
//String  fName= "infobox_property_definitions_en.nq.bz2";
    File F;
        
        F=new File(fileName);
        FileInputStream fin = new FileInputStream(F);
        BufferedInputStream in = new BufferedInputStream(fin);
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
int count=0;
int person_count=0;
String person_data="";

while(EOF >= 0)
 {
           String str=readBZ(bzIn);
           System.out.println(str);

                 ReadContent rcBuff = new ReadContent();

               StringTokenizer st=new StringTokenizer(str);
               int ijk = st.countTokens();
               while(ijk > 0) {
    String strBuff = st.nextToken();
            	   if(strBuff.contains("wikipedia")) {
            		  // sbuffData.append(st.toString());
            		   System.out.println("String Token " + strBuff);
            		   rcBuff.writeToFile(strBuff,fileName.substring(0, fileName.length()-4));
            	   }
            	   ijk--;
               }
                          
       
System.out.println(person_data);

//if(!person_data.equals(""))System.exit(0);
 }
System.out.println("total lines read in File:"+count);

System.out.println("successful");
System.out.println("total Number of People:"+person_count);



        bzIn.close();
        }
        public void writeToFile(String word,String fName) {
        	String buffArray[] = word.split("#");
        	String filename=fName +".txt";
         	  //File file = new File(file_name);
         	 // boolean exist = file.exists();
         	  System.out.println("to File" + word);
         	 boolean dataExist = checkNoiseWord(buffArray[0], filename);
         	if(!dataExist) {
         	 try {
         		FileWriter fstream = new FileWriter(filename,true);  
         		BufferedWriter out = new BufferedWriter(fstream);
         		out.write(buffArray[0]);
         		out.newLine();
         		out.flush();
         		fstream.close();     	
         		} catch (IOException e) {
         			// TODO Auto-generated catch block
         			e.printStackTrace();
         		}  }	  
         	 // System.out.println("File created successfully.");
         	  
        }
        
        public boolean checkNoiseWord(String chkWord,String filename){

       	 boolean containsWord = false;
       		
       	 try {
       		 BufferedReader br;
       		 
       		br = new BufferedReader(new FileReader(filename));
       	    String line = br.readLine();
       	        while (line != null && !containsWord) {
       	           // System.out.println("++++++++++++++++ !!! __-------------------");
       	        	if(line.contains(chkWord)){
       	        		containsWord = true;
       	        	}
       	            line = br.readLine();	           
       	        }
       	       
       	        br.close();
       	    } catch (IOException e) {
       			// TODO Auto-generated catch block
       			e.printStackTrace();
       		} 			
           	return containsWord;
           }  
public static String readBZ(BZip2CompressorInputStream bz) throws IOException
{
        String s="";
        char  n ='0';
        char prev ='0';
        int t=0;


        while (-1 != (t = bz.read())) 
        {

            
            
                n=(char) t;

                if(prev=='.' && t==10)break;

                prev=n;
                s+=n;
            
        }
        if(t==-1)
        {
        EOF=-1;
        }
        //System.out.println("string" + s);
return(s);
}
}