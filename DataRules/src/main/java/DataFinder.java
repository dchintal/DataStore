import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;


public class DataFinder {
	static HashMap<Integer, String> userRecordsOrdered = new HashMap<>();
	static HashMap<Integer,HashMap<Integer, Integer>> UserCount = new HashMap<>();
	static HashMap<Integer,HashMap<Integer, String>> UserLevels = new HashMap<>();
	SubSetRetrival subSetData = new SubSetRetrival();
	static int minConfidenceLevel=60;
	static int minSupportLevel=20;
	static int totalNoOfRec=0;


	
	//Loading the content from Data File for processing.
	public HashMap<Integer, String> loadContentFromFile(Properties data){
		HashMap<Integer, String> userRecords = new HashMap<>();
		int test=1;
		totalNoOfRec=data.size();
		for (int i=1; i<=data.size(); i++ ) {			
			String userBuff = data.getProperty(String.valueOf(i));
			int dataCount = userBuff.length();
			int datatoVal = dataCount-2;
			String userdata = userBuff.substring(1, datatoVal);
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
			}
			userRecordsOrdered.put(i, dataFormat);
		}

		return userRecords;
	}

	//Reading the Data for making.
	public void DataFormationLevels(HashMap<Integer, String> userRecords){
		boolean minDataExist;
		for(int m=1; m <=userRecords.size(); m++){
			int recordCounter=1;	
			HashMap<Integer, String> getValCount = new HashMap<>();
			HashMap<Integer, String> setValCount = new HashMap<>();
			if(m ==1){
				for(int p=1; p<=userRecords.size(); p++){
					minDataExist=checkCount(m,userRecords.get(p));
					if(minDataExist){
						setValCount.put(recordCounter,userRecords.get(p));
						recordCounter++;
					}						
				}
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
								minDataExist=checkCount(m,sdata);
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
			UserCount.put(m, UpdateRecordsCount(m,UserLevels));

		}

	}

	/**
	 * @param args
	 */

	// This program containg 5 data sets where on exectuion asks for choice of data base and minimum support and minimum confidence. Based on Inputs 
	//it genrates rules and Display them.
	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			boolean turnTrip=true;
			while(turnTrip){
				System.out.println("Enter the DataBase no to Select");
				System.out.println("1. Amazon Data on 09/27/2013");
				System.out.println("2. Amazon Data on 09/28/2013");
				System.out.println("3. Amazon Data on 09/29/2013");
				System.out.println("4. Amazon Data on 09/30/2013");
				System.out.println("5. Amazon Data on 10/01/2013");
				BufferedReader dataFetch = new BufferedReader(new InputStreamReader(System.in));
				int fNo=Integer.parseInt(dataFetch.readLine());
				System.out.println("Enter the Minimum Support");
				minSupportLevel = Integer.parseInt(dataFetch.readLine());
				System.out.println("Enter the Minimum Confidence");
				minConfidenceLevel = Integer.parseInt(dataFetch.readLine());
				System.out.println("Data Obtained is" + fNo );
				System.out.println("Data Obtained is" + minConfidenceLevel );
				System.out.println("Data Obtained is" + minSupportLevel );
				Properties data  = new Properties();
				String filePath ="src/main/resources/DataSet"+ fNo;
				System.out.println(filePath);
				File location = new File(filePath);
				FileInputStream fds;
				fds = new FileInputStream(location);
				data.load(fds);
				System.out.println("Loaded property file");
				System.out.println(data.size());
				DataFinder daf = new DataFinder();
				HashMap<Integer, String> userRecords =daf.loadContentFromFile(data); 
				daf.DataFormationLevels(userRecords);	
				daf.generateAssocRules();
				System.out.println("Do you want to get other Database Association Rules(yes/no)");
				String conVal =dataFetch.readLine();
				if(conVal.equals("yes")){
					turnTrip = true;
				}else{
					turnTrip = false;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//Checking the COnfidence of the Data.
	public boolean checkConfidence(String assoRule,String totPair){
		String confVal[] =assoRule.split("-->");
		int totNo = UpdateRecordCounts(5,totPair);
		int leftCount;
		if(confVal[0].length() > 1){
			leftCount=UpdateRecordCounts(5,confVal[0]);
		}else{
			leftCount=UpdateRecordCounts(1,confVal[0]);
		}
		double minConfidence = (double)(minConfidenceLevel*leftCount)/100;
		
		if(totNo >=  minConfidence){
			//System.out.println("pair" +  assoRule + "total No" + totNo + "minCOnfi" + minConfidence);
			return true;
		}
		else{ 
			return false;
		}
	}
	

	// Generating the complete Assocaition Rules with Minimum Support and Min COnfidence
	private void generateAssocRules() {
		for(int j=2;  j<= UserLevels.size(); j++){
			
			System.out.println("++++++++++ Assocation Rules +++++++++ of Size : " + j);
			HashMap<Integer, String> dataPairs = UserLevels.get(j);
			for(int k=1; k <=dataPairs.size(); k++) {
				String dsfConts[] = dataPairs.get(k).split(":");
				ArrayList<String> subSetElements = new ArrayList<>();
				for(String dfsBuff : dsfConts){
					subSetElements.add(dfsBuff);
				}
				ArrayList<String> finalVal = subSetData.findSubSets(subSetElements);
				ArrayList<String> updatedConf=new ArrayList<>();
				for(String bufVal : finalVal){
					boolean suportVal= checkConfidence(bufVal,dataPairs.get(k));
					if(suportVal){
						updatedConf.add(bufVal);}
				}
				if(updatedConf.size() >0){
					// ---> re un comment System.out.println(updatedConf.toString());
					displayRule(updatedConf,dataPairs.get(k),j);
				}
			}
		}
	}

	//Dispalying Rules with Support and Confidence
	private void displayRule(ArrayList<String> updatedConf, String dataPair, int m) {
		// TODO Auto-generated method stub
		
		HashMap<Integer,String> DataMaps = new HashMap<>();
		HashMap<Integer,Integer> DataMapCounts = new HashMap<>();
		DataMaps = UserLevels.get(m);
		DataMapCounts = UserCount.get(m);
		int support =0;
		for(int k=1; k<=DataMaps.size();k++){
			if(DataMaps.get(k).equals(dataPair)){
				support = DataMapCounts.get(k);
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		double exisSupport = ((double)support/(double)totalNoOfRec)*100;
		for(String bufVal : updatedConf){
			double exisConfi = caliculateConfidence(bufVal, dataPair);
			System.out.println(bufVal + " [ " + df.format(exisSupport) + " , " + df.format(exisConfi) + " ]");
			//System.out.println(exisConfi);
		}
		
	}
	//Dispalying Rules with Support and Confidence
	public double caliculateConfidence(String bufVal,String dataPair){
		
		String confVal[] =bufVal.split("-->");
		int totNo = UpdateRecordCounts(5,dataPair);
		int leftCount;
		if(confVal[0].length() > 1){
			leftCount=UpdateRecordCounts(5,confVal[0]);
		}else{
			leftCount=UpdateRecordCounts(1,confVal[0]);
		}
		double Confidence = ((double)totNo/(double)leftCount)*100;
		
		return Confidence;
	}
	
	

	// Checking Data to maintain minimum Support.
	public boolean checkCount(int roundVal,String dataPairVal){
		int dataCount = UpdateRecordCounts(roundVal,dataPairVal);
		float minSupport = (minSupportLevel * totalNoOfRec)/100;
		
		//System.out.println();
		if(Float.compare(dataCount,minSupport) > 0)
			return true;
		else 
			return false;	
	}

	// Check the user to maintain Minimum Confidence it it does not contain it discards data from it.
	public void updateDataSetMinConfi(int dataRound){
		HashMap<Integer,String> DataMaps = new HashMap<>();
		HashMap<Integer,Integer> DataMapCounts = new HashMap<>();
		DataMaps = UserLevels.get(dataRound);
		DataMapCounts = UserCount.get(dataRound); 
		int minSupport = (minSupportLevel * totalNoOfRec)/100;

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
	}


	//To check values in existing database and get the count.
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

	// Update the total no of records so that used for maintaing minimum support.
	public HashMap<Integer,Integer> UpdateRecordsCount(int m, HashMap<Integer, HashMap<Integer, String>> userRecords){
		HashMap<Integer,String> userLevelData = userRecords.get(m);
		HashMap<Integer,Integer> rowDataCount = new HashMap<>();
		for (int uld =1; uld <= userLevelData.size(); uld++){
			String data = userLevelData.get(uld);
			int recordVal=0;
			for (int ur =1; ur <= userRecordsOrdered.size(); ur++){
				if(m ==1){
					if(userRecordsOrdered.get(ur).contains(data))
						recordVal++; }
				else {
					boolean dataExist = true;
					String dataPairs[] = data.split(":");
					for( String dataSet : dataPairs){
						if(!userRecordsOrdered.get(ur).contains(dataSet)) {
							dataExist =false;
						}
					}if(dataExist)
						recordVal++;
				}
			}
			rowDataCount.put(uld, recordVal);
		}
		return rowDataCount;
	}
}
