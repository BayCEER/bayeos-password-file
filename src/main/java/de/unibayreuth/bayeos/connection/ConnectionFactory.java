package de.unibayreuth.bayeos.connection;

public class ConnectionFactory {
	
	private static ConnectionFileAdapter adapter;
	
	
	public static void setFileAdapter(ConnectionFileAdapter a){
		adapter = a;
	}
	
	public static ConnectionFileAdapter getFileAdpater(){
		return adapter;
	}

}
