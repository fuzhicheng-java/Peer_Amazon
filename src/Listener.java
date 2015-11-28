

import java.net.ServerSocket;

public class Listener extends Thread {

	private Peer peer;	
	private Table table;

	public Listener(Peer peer, Table table) {
		super();
		this.peer = peer;
		this.table = table;
	}
	
	public void run() {

		ServerSocket listener = null;


		try {
			listener = new ServerSocket(peer.getPort());
			while (true) {
				try {
					new Responser(listener.accept(), peer, table).start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} finally {
			try {
				if(listener!=null)
					listener.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}

	}

	public Peer getPeer() {
		return peer;
	}
	public void setPeer(Peer peer) {
		this.peer = peer;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
}
