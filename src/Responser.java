

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Responser extends Thread {
	
	private Socket socket;
	private Peer peer;
	private Table table;
	
	
	
	
	public Responser(Socket socket, Peer peer, Table table) {
		super();
		this.socket = socket;
		this.peer = peer;
		this.table = table;
	}



	public void run() {
		try {
			String msg = "";
			

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			String input = in.readLine();
			String[] inputs = input.split("\\+");		
			
			switch(inputs[0]) {
			case "Put": msg = this.put(inputs); break;	
			case "Get": msg = this.get(inputs); break;
			case "Del": msg = this.del(inputs); break;
			default: msg = "The server " + peer.getID() + " can NOT identify the request!!!"; break;
			}

			out.println(msg); 
			System.out.println(msg);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(socket!=null)
					socket.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private synchronized String put(String[] inputs) {
		String msg = "";
		
		long key = Long.parseLong(inputs[1].trim());
		String value = inputs[2].trim();
		
		table.put(key, value);
		msg = "The pair (" + key + ", " + value + ") is put in " + peer.getID() + " successfully!!!";		
		
		return msg;
	}
	
	
	private String get(String[] inputs) {
		String msg = "";
		
		long key = Long.parseLong(inputs[1].trim());		
		
		String value = table.get(key);
	
		msg = "The pair is (" + key + ", " + value + ") !!!";
		
		return msg;
	}
	
	private synchronized String del(String[] inputs) {
		String msg = "";
		
		long key = Long.parseLong(inputs[1].trim());
		
		table.del(key);
		
		msg = "The pair with KEY " + key + ") is deleted in " + peer.getID() + " successfully!!!";
		
		return msg;
	}
	


	
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
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
