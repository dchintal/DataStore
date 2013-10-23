import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class SubSetRetrival {

ArrayList<String> addDat = new ArrayList<>();	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SubSetRetrival sdda = new SubSetRetrival();
	//String data[]= {"a", "b", "c"};
	ArrayList<String> dataContents = new ArrayList<>();
		dataContents.add("a");
		dataContents.add("b");
		dataContents.add("c");
		dataContents.add("d");
		
		
		//System.out.println(fin);
		
	}
	

	public ArrayList<String> findSubSets(ArrayList<String> subDat){
		ArrayList<String> fin= getDatas(subDat);
		//Collections.sort
		return	sortData(fin,subDat.size(),subDat);
		
	}
	
	private ArrayList<String> sortData(ArrayList<String> fin,int m ,ArrayList<String> dataContents) {
		ArrayList<String> finalDat = new ArrayList<>();
		ArrayList<String> keypairs = new ArrayList<>();
		for(String buff : fin){
			if(buff.length() != 0 && buff.length() != m + (m-1)){
				finalDat.add(buff);
			}
		}
		//for (String sdat : dataContents){
			for(String fdat : finalDat){
				String sdat="";
				for (String sbuff : dataContents){
					if(!fdat.contains(sbuff)){
						if(sdat.isEmpty()){
							sdat = sdat+sbuff;
						}else{
						sdat = sdat + ":"+sbuff;
						}
				}
				}
				keypairs.add(sdat+"-->"+fdat);
			}
			
		//}
		//System.out.println(finalDat);
		//System.out.println(keypairs + "++++++++++++" );
		return keypairs;
	}



	public ArrayList<String> getDatas(ArrayList<String> val){
		//ArrayList<ArrayList<String>> subsetCollection = new ArrayList<ArrayList<String>>();
		ArrayList<String> subSetsDot = new ArrayList<>();
		if (val.size() == 0) {
			subSetsDot.add(new String());
		} else {
			ArrayList<String> reducedSet = new ArrayList<String>();

			reducedSet.addAll(val);

			String first = reducedSet.remove(0);
			ArrayList<String> subsets = getDatas(reducedSet);
			for(String subVal : subsets){
				if(!subSetsDot.contains(subVal))
					subSetsDot.addAll(subsets);
			}
			subsets = getDatas(reducedSet);

			for (String subset : subsets) {
				//if(!first.isEmpty())
				//subset.add(0, first);
				if(subset.compareTo(first) > 0) {
				subset =  first + ":"+subset;
				}else{
					if(!subset.isEmpty()){
					subset = subset+":"+first;
					}else{
						subset = subset+first;
					}
					
				}
				
				//System.out.println("For Loop" + first);
				if(!subSetsDot.contains(subset))
				subSetsDot.add(subset);
			}

			//if(subsets.size() != 0 && subsets.size() != val.size())
			//System.out.println("+++++++");
			for(String subVal : subsets){
				if(!subSetsDot.contains(subVal))
					subSetsDot.addAll(subsets);
			}
		}
		
	//System.out.println(subSetsDot);
		return subSetsDot;

	}
	
	public ArrayList<ArrayList<String>> getData(ArrayList<String> val){
		ArrayList<ArrayList<String>> subsetCollection = new ArrayList<ArrayList<String>>();
		if (val.size() == 0) {
			subsetCollection.add(new ArrayList<String>());
		} else {
			ArrayList<String> reducedSet = new ArrayList<String>();

			reducedSet.addAll(val);

			String first = reducedSet.remove(0);
			ArrayList<ArrayList<String>> subsets = getData(reducedSet);
			subsetCollection.addAll(subsets);

			subsets = getData(reducedSet);

			for (ArrayList<String> subset : subsets) {
				//if(!first.isEmpty())
				subset.add(0, first);
				//System.out.println("For Loop" + first);
			}

			//if(subsets.size() != 0 && subsets.size() != val.size())
			//System.out.println("+++++++");
			subsetCollection.addAll(subsets);
			
		}
		
	
		return subsetCollection;

	}
	
	}
	

