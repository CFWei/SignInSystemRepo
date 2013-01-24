package SignInSystem.outputdata;

import SignInSystem.database.ConnectDatabase;

public class OutputHtml {

	public static void main(String[] args) {
		ConnectDatabase db=new ConnectDatabase();
		db.connect("sample1.db");
	}

}
