

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import java.io.*;
import org.apache.tools.bzip2.*;
//import org.apache.commons.compress.compressors.bzip2.*;

class ReadData
{
    
        public static int EOF=0;
        public static void main( String args[]) throws IOException
        {


String str_person=new String("Abraham_Lincoln");

    File F;
      
        F=new File("C:\\Users\\Dheeeraj\\dworkspace\\dataRetrivalFiles\\infobox_property_definitions_en.nq.bz2");
        

        FileInputStream fin = new FileInputStream(F);
        BufferedInputStream in = new BufferedInputStream(fin);

        CBZip2InputStream bzIn = new CBZip2InputStream(in);

        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

char c;
/*      do{

        System.out.print(readBZ(bzIn));
         c=br.readLine().charAt(0);
}while(c=='c');
*/
int count=0;
int person_count=0;
String person_data="";
String per="";
String val="";
String typ="";
while(EOF!=-1)
        {
                String str=readBZ(bzIn);
//                System.out.println(str);

                if(str.charAt(0)!='<')continue;

                StringTokenizer st=new StringTokenizer(str);
                String str1=st.nextToken();
                String str2=st.nextToken();
                String str3=st.nextToken();
        
        
                count++;



                int m=str1.length()-1;
                while(str1.charAt(m)!='/')
                {
                        m--;
                }
                String per_new=str1.substring(m+1,str1.length()-1);

                if(str_person.contains(per_new))
                {
                     System.out.println(per_new);
                        int sl=str1.length()+str2.length()+2;
                        int p=sl+1;

                        if(str.charAt(sl)=='"')
                        {
                val=new String(str.substring(str1.length()+str2.length()+3,str.length()-6));


                            person_data+=val;
                        }
                        else
                        {
                              int  n=str3.length()-1;
                               while(str3.charAt(n)!='/')
                               {
                                n--;
                               }
                               val=new String(str3.substring(n+1,str3.length()-1));
                               person_data+=val;
                        }

                }
                
                
        /*
                        //writing existing person details on file
                        if(person_data.length()!=0)
                        {
                              String filename="person_infobox_";
                              filename+=Character.toLowerCase(per.charAt(0));
                              filename.concat(".txt");
                               PrintWriter pw=new PrintWriter(new FileWriter(filename,true));
                               person_data+=';';
                               pw.println(person_data);
                               //System.out.println(person_data);
                               person_count++;
                            System.out.print(count+"-");
                               System.out.println(person_count);
                              person_data=new String("");
                               pw.close();

                        }

                      
*/
               
       
System.out.println(person_data);

if(!person_data.equals(""))System.exit(0);
 }
System.out.println("total lines read in File:"+count);

System.out.println("successful");
System.out.println("total Number of People:"+person_count);



        bzIn.close();
        }

public static String readBZ(CBZip2InputStream bz) throws IOException
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
        System.out.println(s);
return(s);
}
}