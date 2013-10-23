import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;


public class DataFinder {
	static HashMap<Integer, String> userRecordsOrdered = new HashMap<>();
	static HashMap<Integer,HashMap<Integer, Integer>> UserCount = new HashMap<>();
	static HashMap<Integer,HashMap<Integer, String>> UserLevels = new HashMap<>();
	static int dataTuples;
	SubSetRetrival subSetData = new SubSetRetrival();
	int minConfidenceLevel=60;
	int minSupportLevel=20;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO Auto-generated method stub

		Properties data  = new Properties();
		//HashMap<String,Integer> recordValues = new HashMap<String,Integer>();
		File location = new File("src/main/resources/UserData");
		//System.out.println(location.getAbsolutePath());
		FileInputStream fds;
		try {
//			/HashSet<String> datas = new HashSet<String>();
			fds = new FileInputStream(location);
			data.load(fds);
			System.out.println("Loaded property file");
			System.out.println(data.size());
			int test=1;
			HashMap<Integer, String> userRecords = new HashMap<>();
			
			for (int i=1; i<=data.size(); i++ ) {			
				String userBuff = data.getProperty(String.valueOf(i));
				int dataCount = userBuff.length();
				//System.out.println(userBuff);
				String userdata = userBuff.substring(1, dataCount-2);
				String userList[] = userdata.split(",");
				Arrays.sort(userList);
				
				String dataFormat="";
				for (int j=0; j<userList.length; j++ ){
					if(!userRecords.containsValue(userList[j])){
						userRecords.put(test, userList[j]);
						test++;				
					}
					if(!(j == userList.length-1))
						dataFormat = dataFormat + userList[j] + ":";
						else
							dataFormat = dataFormat + userList[j];	
					//}
				}
				dataTuples = test;
				userRecordsOrdered.put(i, dataFormat);
			}
			//System.out.println(datas.size() + recordValues.toString());
			
			
			DataFinder daf = new DataFinder();
			boolean minDataExist;
			
			for(int m=1; m <=userRecords.size(); m++){
				int recordCounter=1;	
				HashMap<Integer, String> getValCount = new HashMap<>();
				HashMap<Integer, String> setValCount = new HashMap<>();
				if(m ==1){
					for(int p=1; p<=userRecords.size(); p++){
					minDataExist=daf.checkCount(m,userRecords.get(p));
						if(minDataExist){
							setValCount.put(recordCounter,userRecords.get(p));
							recordCounter++;
						}						
				}
				//	UserLevels.put(m,setValCount);
			//	Update	
					
			}  else {
				getValCount = UserLevels.get(m-1);
				HashMap<Integer, String> firsData = UserLevels.get(1);
				 for (int k=1; k<=getValCount.size(); k++){
					 for(int j=1; j <= firsData.size(); j++ ) {
						 String sdata = "";
						 if(!getValCount.get(k).contains(firsData.get(j))) {
							 String dataBuild  = getValCount.get(k) + ":" +firsData.get(j);
							 String dataBuff[] = dataBuild.split(":");
							 Arrays.sort(dataBuff);
							 for(int p=0;p<dataBuff.length;p++){
								
								 if( p+1 == dataBuff.length )
									sdata = sdata+dataBuff[p];
								 else
									 sdata= sdata + dataBuff[p] + ":";
							 }
							 
							 if(!setValCount.containsValue(sdata)){
								 //System.out.println("Sdataaaaaaaaa" + sdata);
								 minDataExist=daf.checkCount(m,sdata);
									if(minDataExist){
										setValCount.put(recordCounter,sdata);
										recordCounter++;
									}								 
							 }	
						 }
						}	
		
				 }
				
			}			
				UserLevels.put(m,setValCount);
				UserCount.put(m, daf.UpdateRecordsCount(m,UserLevels));
			
			}
			
			daf.generateAssocRules();
		//	System.out.println("Obatine Value" + UserLevels);
			System.out.println("Ordered Data List Records" + userRecordsOrdered);
	//WSS		System.out.println("ordered Values with count" + UserCount);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public boolean checkConfidence(String assoRule,String totPair){
		
		String confVal[] =assoRule.split("-->");
		//String[] lefData=new String[1];
		//String[] rightData=new String[1];
		String completeAppnd;
		
		int totNo = UpdateRecordCounts(5,totPair);
		int leftCount,rightCount;
		if(confVal[0].length() > 1){
			leftCount=UpdateRecordCounts(5,confVal[0]);
		}else{
			leftCount=UpdateRecordCounts(1,confVal[0]);
		}
		
		if(confVal[1].length() > 1){
			rightCount=UpdateRecordCounts(5,confVal[1]);
		}else{
			rightCount=UpdateRecordCounts(1,confVal[1]);
		}
		
		int minConfidence = minConfidenceLevel*leftCount/100;
		
		
		//System.out.println("to pair " + totPair + " totNo "+  totNo + " left avl:  "+ confVal[0] + "     Right Val:   " + confVal[1]);
		
		//System.out.println("left Count " +leftCount + "Right COunt"+ rightCount );
		//System.out.println((totNo)+ "min"+ minConfidence);
		if(totNo >=  minConfidence){
			return true;
		}
		else{ 
			return false;
		}
	}
	
	
	private void generateAssocRules() {
		// TODO Auto-generated method stub
	//	System.out.println("size" + UserLevels.size());
	for(int j=2;  j<= UserLevels.size(); j++){
		HashMap<Integer, String> dataPairs = UserLevels.get(j);
			for(int k=1; k <=dataPairs.size(); k++) {
			//	System.out.println("enter kloop");
				String dsfConts[] = dataPairs.get(k).split(":");
				ArrayList<String> subSetElements = new ArrayList<>();
				for(String dfsBuff : dsfConts){
					subSetElements.add(dfsBuff);
				}
				ArrayList<String> finalVal = subSetData.findSubSets(subSetElements);
				ArrayList<String> updatedConf=new ArrayList<>();
				for(String bufVal : finalVal){
				boolean suportVal= checkConfidence(bufVal,dataPairs.get(k));
				//System.out.println(suportVal);
					if(suportVal){
					updatedConf.add(bufVal);}
				}
				if(updatedConf.size() >0){
				System.out.println("Final VA;" +updatedConf.toString());
				}
				/*
				int l=0;
				String dataAppender ="";		
				while(l < dsfConts.length-1){							
					dataAppender = dataAppender +":"+dsfConts[l]; 
				l++;
				}
				dataAppender = dataAppender + "-->" + dsfConts[dsfConts.length-1];
				*/
						
		//	System.out.println("Assoc Rules" + dataAppender);
					//}
				}
			}
		
		//}
	
		
	}

	public boolean checkCount(int roundVal,String dataPairVal){
		int dataCount = UpdateRecordCounts(roundVal,dataPairVal);
		float minSupport = (minSupportLevel * 7)/100;
		//System.out.println("datacount" + dataCount + "minsupport" +minSupport );
		if(Float.compare(dataCount,minSupport) > 0)
			return true;
		else 
			return false;	
	}
	
	public void updateDataSetMinConfi(int dataRound){
		HashMap<Integer,String> DataMaps = new HashMap<>();
		HashMap<Integer,Integer> DataMapCounts = new HashMap<>();
		DataMaps = UserLevels.get(dataRound);
		DataMapCounts = UserCount.get(dataRound); 
		int minSupport = (minSupportLevel * 7)/100;
		
		for(int dataSi=1; dataSi <= DataMaps.size(); dataSi++){
			int conVal = DataMapCounts.get(dataSi);
			if(conVal < minSupport)
			{
			DataMapCounts.remove(dataSi);
			DataMaps.remove(dataSi);
			}
			
		}
		UserLevels.put(dataRound, DataMaps);
		UserCount.put(dataRound, DataMapCounts);
		//System.out.println(20 * 7/100);
		System.out.println("+++++++++++++++" + UserCount);
		System.out.println("+++++++++++++++" + UserLevels);
		
	}
	
	public int UpdateRecordCounts(int m, String dataPairVal ){
		
			int recordVal=0;
			
			for (int ur =1; ur <= userRecordsOrdered.size(); ur++){
			if(m ==1){
				if(userRecordsOrdered.get(ur).contains(dataPairVal))
					recordVal++; }
			else {
				boolean dataExist = true;
				String dataPairs[] = dataPairVal.split(":");
				for( String dataSet : dataPairs){
		
					if(!userRecordsOrdered.get(ur).contains(dataSet)) {
		
						dataExist =false;
					}
				}if(dataExist)
					recordVal++;
				
			}
				
				
			}
		return recordVal;
	}
	
	
	
	public HashMap<Integer,Integer> UpdateRecordsCount(int m, HashMap<Integer, HashMap<Integer, String>> userRecords){
		HashMap<Integer,String> userLevelData = userRecords.get(m);
		HashMap<Integer,Integer> rowDataCount = new HashMap<>();
		for (int uld =1; uld <= userLevelData.size(); uld++){
			String data = userLevelData.get(uld);
			int recordVal=0;
			//String dataPairs[] = data.split(":");
			//System.out.println();
			
			for (int ur =1; ur <= userRecordsOrdered.size(); ur++){
			if(m ==1){
				if(userRecordsOrdered.get(ur).contains(data))
					recordVal++; }
			else {
				boolean dataExist = true;
				String dataPairs[] = data.split(":");
				for( String dataSet : dataPairs){
					//System.out.println(data);
					if(!userRecordsOrdered.get(ur).contains(dataSet)) {
				//	System.out.println("Cmg to false");
						dataExist =false;
					}
				}if(dataExist)
					recordVal++;
				
			}
				
				
			}
			
			rowDataCount.put(uld, recordVal);
		}
	//	UserCount.put(m, rowDataCount);
		return rowDataCount;
	}
		
}
