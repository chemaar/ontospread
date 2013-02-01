package org.ontospread.state;

import java.io.Serializable;
import java.util.HashMap;

import org.ontospread.to.PathTO;

public class PrizePath implements Serializable {

	private HashMap<PathTO,Integer> prizeTable;
	public PrizePath(){
		this.prizeTable = new HashMap<PathTO,Integer>();
	}
	public void prizePaths(PathTO[] path_old, PathTO[] path_new){
		prizePath(path_old);
        prizePath(path_new);
	}
	
	protected void prizePath(PathTO []path){
		int hits = 0;       
		for(int i = 0; i<path.length;i++){
			Integer hitsStored = (Integer) this.prizeTable.get(path[i]);
			if(hitsStored!=null){                   
				hits = hitsStored.intValue();
			}           
			this.prizeTable.put(path[i],new Integer(hits+1));
		}
	}
	public HashMap<PathTO, Integer> getPrizeTable() {
		return prizeTable;
	}
	public void setPrizeTable(HashMap<PathTO, Integer> prizeTable) {
		this.prizeTable = prizeTable;
	}
	   
}
