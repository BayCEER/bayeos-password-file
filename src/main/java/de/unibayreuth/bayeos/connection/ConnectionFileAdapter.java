package de.unibayreuth.bayeos.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

public class ConnectionFileAdapter {

	private static final int KEY_LENGTH = 32;

	private File homeFolder;

	public File getHomeFolder() {
		return homeFolder;
	}
	
	
	private String prefix = "bayeos"; 
	
	
	public String getPwdFile() {
		return "." + prefix + ".pwd";
	}


	public String getKeyFile() {
		return "." + prefix + ".pwd_key";
	}




	private static final Logger logger = Logger.getLogger(ConnectionFileAdapter.class);

	
	public ConnectionFileAdapter(){
		this(new File( new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath()));
	}
	
	public ConnectionFileAdapter(String prefix, File homeFolder) {
		this.prefix = prefix;
		this.homeFolder = homeFolder;		
	}
	
	
	public ConnectionFileAdapter(String prefix) {
		this.prefix = prefix;
		this.homeFolder = new File( new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath());
		
	}
	
	

	
	public ConnectionFileAdapter(File homeFolder) {
		this.homeFolder = homeFolder;
	}
		
	
	
	

	private File getFile(String name) {
		return new File(homeFolder.getAbsolutePath() + File.separator + name);
	}
	
	
	
	/*
	 * Read connections in homeFolder
	 */
	public List<Connection> read() throws IOException {
		return readConnections(readKey());			
	}
	
	
	/*
	 * Write encrypted connections in home folder. Creates a key file if it doesn't exist. 
	 */
	public void writeEncrypted(List<Connection> cons) throws IOException {
		String key = readKey();
		if (key == null) {
			key = RandomStringUtils.randomNumeric(KEY_LENGTH);
			writeKey(key);
		}
		writeConnections(key, cons);		
	}

	
	/*
	 * Writes connections in home folder. Passwords are not encrypted. 
	 */
	public void write(List<Connection> cons) throws IOException {			
			writeConnections(null, cons);
	}
	
	
	/*
	 * Read connections in pwd file in home folder 
	 */
	private List<Connection> readConnections(String key) {
		List<Connection> cons = new ArrayList<Connection>(5);
		BufferedReader br = null;
		 
		try {
			File file = getFile(getPwdFile());
			if (!file.exists()) {
				return cons;
			} else {
				br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null) {
					cons.add(ConnectionStringAdapter.fromString(key, line));
				}
			}
			return cons;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
		}
	}

	/*
	 * Writes pwd file
	 */
	private void writeConnections(String key, List<Connection> cons) {
		BufferedWriter bw = null;							
		try {
			File file = getFile(getPwdFile());
			bw = new BufferedWriter(new FileWriter(file));
			for (Connection connection : cons) {
				bw.write(ConnectionStringAdapter.toString(key, connection));
				bw.write('\n');
			}
			bw.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
		}
	}
	/*
	 * Writes pwd_key file 
	 */
	private void writeKey(String key) throws IOException{
		File file = getFile(getKeyFile()); 
		FileWriter fw = null;		
		try {
			fw = new FileWriter(file);
			fw.write(key);
			fw.write('\n');
			fw.flush();		
		} finally {
			if (fw!=null)
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
		}
	}

	/*
	 * Reades key in keyFile 
	 */
	private String readKey() throws IOException  {
		BufferedReader fr = null;
		try {
			File file = getFile(getKeyFile());
			if (file.exists()) {
				fr = new BufferedReader(new FileReader(file));
				return fr.readLine();
			} else {
				return null;
			}

		
		} finally {			
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
		}
	}

	
}
