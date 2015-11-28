


public class Peer {
	
	private String ID;
	private String IP;
	private int port;
	private String path;
	private long keyMin;
	private long keyMax;
	
	private Peer backup;	
	
	
	
	public Peer() {		
		super();
		
		ID = "";
		IP = "";
		port = 0;
		path = "";
		keyMin = 0;
		keyMax = 0;
		
		backup = null;		
	}
	
	
	

	public Peer(String iD, String iP, int port, String path, long keyMin, long keyMax) {
		super();
		ID = iD;
		IP = iP;
		this.port = port;
		this.path = path;
		this.keyMin = keyMin;
		this.keyMax = keyMax;
		
		//backup = null;	
	}

	
	public void addBackup(String ID, String IP, int port, String path, int keyMin, int keyMax) {
		backup = new Peer(ID, IP, port, path, keyMin, keyMax);
	}






	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public long getKeyMin() {
		return keyMin;
	}
	public void setKeyMin(int keyMin) {
		this.keyMin = keyMin;
	}

	public long getKeyMax() {
		return keyMax;
	}
	public void setKeyMax(int keyMax) {
		this.keyMax = keyMax;
	}
	public Peer getBackup() {
		return backup;
	}
	public void setBackup(Peer backup) {
		this.backup = backup;
	}
	
}
