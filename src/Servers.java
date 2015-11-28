
import java.util.ArrayList;
import java.util.List;

public class Servers {

	private List<Peer> serverList;	

	public Servers() {
		super();		
		
		serverList = new ArrayList<Peer>();		
	}	
	
	
	
	
	public void addServer(Peer peer) {		
		serverList.add(peer);
	}
	
	
	public Peer findByKeyRange(long key) {
		Peer server = null;
		for(int i = 0; i < serverList.size(); i++) {			
			server = serverList.get(i);
			
			if(key>=server.getKeyMin() && key <= server.getKeyMax())
				return server;			
		}
		
		return serverList.get(0);
	}
	
	
	public Peer findBackupByID(String serverID) {
		for(int i = 0; i < serverList.size(); i++) {
			if(serverList.get(i).getID().equals(serverID))
				return serverList.get(i).getBackup();
		}
		
		return null;
	}




	public List<Peer> getServerList() {
		return serverList;
	}
	public void setServerList(List<Peer> serverList) {
		this.serverList = serverList;
	}
}
