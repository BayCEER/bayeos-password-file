package de.unibayreuth.bayeos.connection;

public class Connection {
	
	private String name;
	private String URL;	
	private String userName;
	private String password;
	
	
	public Connection() {		
	}
	
	public Connection(String name, String URL, String user, String password) {
		this.name = name;
		this.URL = URL;
		this.userName = user;
		this.password = password;				
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String user) {
		this.userName = user;
	}

	public String getPassword() {		
			return password;					
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {		 
		return name;
	}
		
}


