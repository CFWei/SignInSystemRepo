package SignInSystem.component;

import java.util.ArrayList;

public class ParseResult {
	private ReferenceTitle title;
	private ArrayList<PersonData> contentList;
	
	public ParseResult(ReferenceTitle title ,ArrayList<PersonData> contentList ){
		this.title=title;
		this.contentList=contentList;
		
	}
	
	
	public ReferenceTitle getTitle(){
		return title;
		
	}
	
	
	public ArrayList<PersonData> getContentList(){
		return contentList;
		
	}
	

}
