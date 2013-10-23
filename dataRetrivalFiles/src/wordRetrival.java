
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import org.apache.poi.hdf.extractor.WordDocument;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.util.LittleEndian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;


public class wordRetrival {
    StringBuffer sb = new StringBuffer(8192);
    StringBuffer TextBuffer = new StringBuffer();

   

    public String doc2text(String fileName) throws IOException {
        WordDocument wd = new WordDocument(fileName);
        StringWriter docTextWriter = new StringWriter();
        wd.writeAllText(new PrintWriter(docTextWriter));
        docTextWriter.close();
        return docTextWriter.toString();
    }

    public String rtf2text(InputStream is) throws Exception {
        DefaultStyledDocument styledDoc = new DefaultStyledDocument();
        new RTFEditorKit().read(is, styledDoc, 0);
        return styledDoc.getText(0, styledDoc.getLength());
    }
    public void createFile(String buffData){  	 
   	 // System.out.println(file.getAbsolutePath());
   	  //System.out.println(file.getPath());
   	  String[] buffDataSplit = buffData.split("\\s");
   	  boolean noiseCheck = false,dupValue=false;
   	  //String buuKeyWord = null;
   	  if(buffDataSplit.length > 0) {  
   	  for(int i=0;i < buffDataSplit.length ; i++) {
   		  System.out.println("+++++" + buffDataSplit[i]);
   		noiseCheck = checkNoiseWord(buffDataSplit[i],1);
   		if(!noiseCheck) {
   			dupValue = checkNoiseWord(buffDataSplit[i],2);
   			if(!dupValue){
   			writeToFile(buffDataSplit[i]);
   			}
   		}	
   	  }
   	} else {
   		noiseCheck = checkNoiseWord(buffData, 1);
   		if(!noiseCheck) {
   			checkNoiseWord(buffData,2);
   			if(!dupValue){
   			writeToFile(buffData);
   			}
   		}
   	  }
   	  
   }
public boolean checkNoiseWord(String chkWord,int dataFlag){

	 boolean containsWord = false;
		
	 try {
		 BufferedReader br;
		 if(dataFlag == 1) {
		br = new BufferedReader(new FileReader("noiseData.txt"));
		 } else {
			br = new BufferedReader(new FileReader("retriveData.txt"));
		 }
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
    
    public void writeToFile(String word) {
    	String file_name = "retriveData.txt";
     	  //File file = new File(file_name);
     	 // boolean exist = file.exists();
     	  System.out.println("to File" + word);
     		try {
     		FileWriter fstream = new FileWriter(file_name,true);  
     		BufferedWriter out = new BufferedWriter(fstream);
     		out.write(word);
     		out.newLine();
     		out.flush();
     		fstream.close();     	
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}  	  
     	 // System.out.println("File created successfully.");
     	  
    }
    public String ppt2text(String fileName) throws Exception {
        POIFSReader poifReader = new POIFSReader();
        poifReader.registerListener(new wordRetrival.MyPOIFSReaderListener());
        poifReader.read(new FileInputStream(fileName));
        
        return sb.toString();
    }
   

    class MyPOIFSReaderListener implements POIFSReaderListener {

        public void processPOIFSReaderEvent(POIFSReaderEvent event) {
            char ch0 = (char) 0;
            char ch11 = (char) 20;
            try {
                DocumentInputStream dis = null;
                dis = event.getStream();
                byte btoWrite[] = new byte[dis.available()];
                dis.read(btoWrite, 0, dis.available());
                
                for (int i = 0; i < btoWrite.length - 20; i++) {
                    long type = LittleEndian.getUShort(btoWrite, i + 2);
                    long size = LittleEndian.getUInt(btoWrite, i + 4);
                    if (type == 4008) {
                        try {
                            String s = new String(btoWrite, i + 4 + 1, (int) size + 3).replace(ch0, ' ').replace(ch11, ' ');
                            if (s.trim().startsWith("Click to edit") == false) {
                            	sb.append(s).append("\n");
                            	createFile(s);	
                            }
                        } catch (Exception ee) {
                            System.out.println("error:" + ee);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
    }
/*
    public String xls2text(InputStream in) throws Exception {
        HSSFWorkbook excelWb = new HSSFWorkbook(in);
        StringBuffer result = new StringBuffer(4096);
        int numberOfSheets = excelWb.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            HSSFSheet sheet = excelWb.getSheetAt(i);
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            if (numberOfRows > 0) {
                if (excelWb.getSheetName(i) != null && excelWb.getSheetName(i).length() != 0) {
// append sheet name to content
                    if (i > 0) {
                        result.append("\n\n");
                    }
                    result.append(excelWb.getSheetName(i).trim());
                    result.append(":\n\n");
                }

                Iterator<HSSFRow> rowIt = sheet.rowIterator();
                while (rowIt.hasNext()) {
                    HSSFRow row = rowIt.next();
                    if (row != null) {
                        boolean hasContent = false;
                        Iterator<HSSFCell> it = row.cellIterator();
                        while (it.hasNext()) {
                            HSSFCell cell = it.next();
                            String text = null;
                            try {
                                switch (cell.getCellType()) {
                                    case HSSFCell.CELL_TYPE_BLANK:
                                    case HSSFCell.CELL_TYPE_ERROR:
// ignore all blank or error cells
                                        break;
                                    case HSSFCell.CELL_TYPE_NUMERIC:
                                        text = Double.toString(cell.getNumericCellValue());
                                        break;
                                    case HSSFCell.CELL_TYPE_BOOLEAN:
                                        text = Boolean.toString(cell.getBooleanCellValue());
                                        break;
                                    case HSSFCell.CELL_TYPE_STRING:
                                    default:
                                        text = cell.getStringCellValue();
                                        break;
                                }
                            } catch (Exception e) {
                            }
                            if ((text != null) && (text.length() != 0)) {
                                result.append(text.trim());
                                result.append(' ');
                                hasContent = true;
                            }
                        }
                        if (hasContent) {
// append a newline at the end of each row that has content
                            result.append('\n');
                        }
                    }
                }
            }
        }
        return result.toString();
    }
*/
    /*
    public void processElement(Object o) {
        if (o instanceof Element) {
            Element e = (Element) o;
            String elementName = e.getQualifiedName();
            if (elementName.startsWith("text")) {
                if (elementName.equals("text:tab")) // add tab for text:tab
                {
                    TextBuffer.append("\t");
                } else if (elementName.equals("text:s")) // add space for text:s
                {
                    TextBuffer.append(" ");
                } else {
                    List children = e.getContent();
                    Iterator iterator = children.iterator();
                    while (iterator.hasNext()) {
                        Object child = iterator.next();
//If Child is a Text Node, then append the text
                        if (child instanceof Text) {
                            Text t = (Text) child;
                            TextBuffer.append(t.getValue());
                        } else {
                            processElement(child); // Recursively process the child element
                        }
                    }
                }
                if (elementName.equals("text:p")) {
                    TextBuffer.append("\n");
                }
            } else {
                List non_text_list = e.getContent();
                Iterator it = non_text_list.iterator();
                while (it.hasNext()) {
                    Object non_text_child = it.next();
                    processElement(non_text_child);
                }
            }
        }
    } */
/*
    public String getOpenOfficeText(String fileName) throws Exception {
        TextBuffer = new StringBuffer();
//Unzip the openOffice Document
        ZipFile zipFile = new ZipFile(fileName);
        Enumeration entries = zipFile.entries();
        ZipEntry entry;
        while (entries.hasMoreElements()) {
            entry = (ZipEntry) entries.nextElement();
            if (entry.getName().equals("content.xml")) {
                TextBuffer = new StringBuffer();
                SAXBuilder sax = new SAXBuilder();
                Document doc = sax.build(zipFile.getInputStream(entry));
                Element rootElement = doc.getRootElement();
                processElement(rootElement);
                break;
            }
        }
        return TextBuffer.toString();
    }
*/
    public String fileToStringNow(File f) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String nextLine = "";
        StringBuffer sbuff = new StringBuffer();
        while ((nextLine = br.readLine()) != null) {
            sbuff.append(nextLine);
            sbuff.append(System.getProperty("line.separator"));
        }
        br.close();
        return sbuff.toString();
    }

    public static void main(String[] args) throws Exception {
    	wordRetrival rff = new wordRetrival();
        System.out.print("Enter File Name => ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String fileName = br.readLine();
        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println("Sorry File does not Exists!");
        } else {
             if (f.getName().endsWith(".doc") || f.getName().endsWith(".DOC")) {
                System.out.println(rff.doc2text(fileName));
            } else if (f.getName().endsWith(".rtf") || f.getName().endsWith(".RTF")) {
                System.out.println(rff.rtf2text(new FileInputStream(f)));
            } else if (f.getName().endsWith(".ppt") || f.getName().endsWith(".PPT")) {
                System.out.println(rff.ppt2text(fileName));
            } else if (f.getName().endsWith(".xls") || f.getName().endsWith(".XLS")) {
              //  System.out.println(rff.xls2text(new FileInputStream(f)));
            	System.out.println("Not Supported");
            } else if (f.getName().endsWith(".odt") || f.getName().endsWith(".ODT") || f.getName().endsWith(".ods") || f.getName().endsWith(".ODS") || f.getName().endsWith(".odp") || f.getName().endsWith(".ODP")) {
               // System.out.println(rff.getOpenOfficeText(fileName));
            	System.out.println("Not Supported");
            } else {
                System.out.println(rff.fileToStringNow(f));
            }
        }
        br.close();
    }
}