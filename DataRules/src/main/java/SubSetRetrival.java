import java.util.ArrayList;


public class SubSetRetrival {

	ArrayList<String> addDat = new ArrayList<>();	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<String> dataContents = new ArrayList<>();
		dataContents.add("a");
		dataContents.add("b");
		dataContents.add("c");
		dataContents.add("d");
	}

	public ArrayList<String> findSubSets(ArrayList<String> subDat){
		ArrayList<String> fin= getDatas(subDat);
		return	sortData(fin,subDat.size(),subDat);

	}
	
	// It sorts all the elements of the rule obtianed from Get Datas.
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
		return keypairs;
	}

	// This Method is used to obtain all the subsets combination for each rule fromed in level 
	// EX AB->c it genrates A->BC, B->AC, C-> AB.. 
	
	public ArrayList<String> getDatas(ArrayList<String> val){
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
				if(subset.compareTo(first) > 0) {
					subset =  first + ":"+subset;
				}else{
					if(!subset.isEmpty()){
						subset = subset+":"+first;
					}else{
						subset = subset+first;
					}
				}
				if(!subSetsDot.contains(subset))
					subSetsDot.add(subset);
			}
			for(String subVal : subsets){
				if(!subSetsDot.contains(subVal))
					subSetsDot.addAll(subsets);
			}
		}
		return subSetsDot;
	}

}


