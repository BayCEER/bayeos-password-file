package de.unibayreuth.bayeos.connection;


public class ConnectionStringAdapter {
	
	public static  String toString(String key, Connection con) {
		StringBuffer a = new StringBuffer();
		a.append(con.getName());
		a.append(" ");
		a.append(con.getURL());
		a.append(" ");
		a.append(con.getUserName());
		a.append(" ");
		if (con.getPassword()!=null){
			if (key!=null){
				a.append(SimpleEncrypter.encrypt(key, con.getPassword()));	
			} else {
				a.append(con.getPassword());
			}
				
		}
		
		return a.toString();
	}

	public static Connection fromString(String key, String in) {
		Connection con = new Connection();
		String[] ca = in.split("\\s");
		con.setName((ca.length > 0) ? ca[0] : null);
		con.setURL((ca.length > 1) ? ca[1] : null);
		con.setUserName((ca.length > 2) ? ca[2] : null);
		if (ca.length > 3){		
			if (key!=null){
				con.setPassword(SimpleEncrypter.decrypt(key, ca[3]));	
			} else {
				con.setPassword(ca[3]);
			}
			
		} else {
			con.setPassword(null);
		}		
		return con;
	}


}
