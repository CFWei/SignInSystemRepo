package SignInSystem.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


//Define all column in Title
public class ReferenceTitle extends LinkedHashMap<String,String> {

	public ArrayList<String> getAllTitle(){
		ArrayList<String> returnContent=new ArrayList<String>();
		
		Collection collection = values();
		Iterator iterator = collection.iterator();
		while(iterator.hasNext()){
			String title=iterator.next().toString();
			returnContent.add(title);
			
		}
		
		return returnContent;
		
		
	}
	
	public ArrayList<String>  getAllKey(){
		ArrayList<String> returnKey=new ArrayList<String>();
		
		Set<String> keyList=keySet();
		Iterator iterator = keyList.iterator();
		while(iterator.hasNext()){
			String key=iterator.next().toString();
			returnKey.add(key);
			
		}
		return returnKey;
		
		
	}
	
	
	
	public void getAllData(){
		
		Set<Map.Entry<String, String>> aEntrySet= entrySet();
		for(Map.Entry<String, String> aEntry: aEntrySet){

		          String key=aEntry.getKey();
		          String title=aEntry.getValue();
		         // System.out.println(key+" "+title);
		}
			
			
		
	}

}
