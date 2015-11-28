

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {

	private String fileName; //filePath+fileName
	private Peer selfPeer;
	private Servers servers;

	
	public Config(Peer peer, Servers servers) {
		super();
		this.fileName = "";
		this.selfPeer = peer;
		this.servers = servers;
	}
	
	
	public Config(String fileName, Peer peer, Servers servers) {
		super();
		this.fileName = fileName;
		this.selfPeer = peer;
		this.servers = servers;
	}
	
	
	public void configure() {
		List<String> infoList = read();
		
		buildServers(infoList);
	}


	private List<String> read() {
		
		List<String> infoList = new ArrayList<String>();
		String line = "";

        try {
            
            FileReader fileReader = new FileReader(fileName);

            
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                infoList.add(line);
            }   
            
            bufferedReader.close();         
        }
        catch(IOException ex) {
        	ex.printStackTrace();
        }
        
        return infoList;
	}
	
	
	private void buildServers(List<String> infoList) {
		
		String[] infos;
		String[] keyRange;
		String[] peerInfo;
		
		String ID;
		String IP;
		int port;
		String path;
		int keyMin;
		int keyMax;
		
		Peer peerServer = null;
		
		for(int i = 0; i < infoList.size(); i++) {
			String info = infoList.get(i);		
			infos = info.split(";");
			
			keyRange = infos[0].split("\\+");
			keyMin = Integer.parseInt(keyRange[0]);
			keyMax = Integer.parseInt(keyRange[1]);
			
			peerInfo = infos[1].split("\\+");
			ID = peerInfo[0];
			IP = peerInfo[1];
			port = Integer.parseInt(peerInfo[2]);
			path = peerInfo[3];
			
			if(i==0) {
				selfPeer.setID(ID);
				selfPeer.setIP(IP);
				selfPeer.setPort(port);
				selfPeer.setPath(path);
				selfPeer.setKeyMin(keyMin);
				selfPeer.setKeyMax(keyMax);
			}
			else {
				peerServer = new Peer(ID, IP, port, path, keyMin, keyMax);				
			}
			
			
			
			//backup
			if(infos.length >2) {
				peerInfo = infos[2].split("\\+");
				ID = peerInfo[0];
				IP = peerInfo[1];
				port = Integer.parseInt(peerInfo[2]);
				path = peerInfo[3];
				
				if(i==0) {
					selfPeer.addBackup(ID, IP, port, path, keyMin, keyMax);
				}
				else {
					peerServer.addBackup(ID, IP, port, path, keyMin, keyMax);
				}
			}
			
			
			if(i!=0) {
				servers.addServer(peerServer);
			}
			
		}
	}

	
	
	
	
	
	

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	

	public Peer getSelfPeer() {
		return selfPeer;
	}


	public void setSelfPeer(Peer selfPeer) {
		this.selfPeer = selfPeer;
	}


	public Servers getServers() {
		return servers;
	}


	public void setServers(Servers servers) {
		this.servers = servers;
	}




}
