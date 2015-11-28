

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Requester extends Thread {

	private Peer peer;
	private Servers servers;
	private Table table;
//	public long key;
//	public String value;
//	public int option;

	public Requester(Peer peer, Servers servers, Table table) {
		super();
		this.peer = peer;
		this.servers = servers;
		this.table = table;
	}




	public void run() {

		/*
		Scanner scanner = new Scanner(System.in);		
		String command = "";

		while(true) {

			System.out.println("Enter the hash table OPERATION (such as put, get, del):");
			command = scanner.next().trim();

			switch(command) {
			//case "put": this.put(); break;		
			///case "get": this.get(); break;
			//case "del": this.del(); break;
			default: break;
			}			
		}
		*/		
		
	}


	public void put(long key,String value) {

		/*
		//input key
		int key = getInputKey();
			


		//input value
		Scanner scanner = new Scanner(System.in);		
		String value = "";						
		System.out.println("Enter the VALUE:");
		value = scanner.next().trim();
		*/


		Peer server = null;
		while(server==null) {			
			server = findPeerByKey(key);
			if(server == null) {
				System.out.println("No server stores the input KEY, re-enter KEY!!!");
				key = getInputKey();
			}
			else {
				break;
			}
		}
		//request
		Socket socket = null;	

		//request to self
		if(server.equals(peer)) {
			table.put(key, value);
			System.out.println("The pair (" + key + ", " + value + ") is put in " + server.getID() + " successfully!!!");
		}
		else {
			try {	
				socket = new Socket(server.getIP(), server.getPort());

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Put+" + key + "+" + value);

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String answer = in.readLine();	

				System.out.println(answer);

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

		/*
		//request to backup
		Peer serverBackup = server.getBackup();		
		if(serverBackup != null) {
			try {	
				socket = new Socket(serverBackup.getIP(), serverBackup.getPort());

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Put+" + key + "+" + value);

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String answer = in.readLine();	

				System.out.println(answer);

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
		*/		
	}

	public void get(long key) {

		//input key
		//int key = getInputKey();
		Peer server = null;
		while(server==null) {			
			server = findPeerByKey(key);
			if(server == null) {
				System.out.println("No server stores the input KEY, re-enter KEY!!!");
				key = getInputKey();
			}
			else {
				break;
			}
		}		


		//request
		Socket socket = null;

		//request to self
		if(server.equals(peer)) {
			String value = table.get(key);			
			System.out.println("The pair is (" + key + ", " + value + ") !!!");
		}
		else {
			try {	
				socket = new Socket(server.getIP(), server.getPort());

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Get+" + key);

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String answer = in.readLine();	

				System.out.println(answer);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				
				System.out.println("The server is DOWN, request to the BACKUP server!!!");

				//request to backup
				Peer serverBackup = server.getBackup();
				if(serverBackup == null) {
					System.out.println("There is NO BACKUP server!!!");
				}
				else {
					Socket socketBackup = null;
					try {	
						socketBackup = new Socket(serverBackup.getIP(), serverBackup.getPort());

						PrintWriter out = new PrintWriter(socketBackup.getOutputStream(), true);
						out.println("Get+" + key);

						BufferedReader in = new BufferedReader(new InputStreamReader(socketBackup.getInputStream()));
						String answer = in.readLine();	

						System.out.println(answer);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						try {
							if(socketBackup!=null)
								socketBackup.close();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}		
					}
				}
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
	}




	public void del(long key) {

		//input key
		//int key = getInputKey();
		Peer server = null;
		while(server==null) {			
			server = findPeerByKey(key);
			if(server == null) {
				System.out.println("No server stores the input KEY, re-enter KEY!!!");
				key = getInputKey();
			}
			else {
				break;
			}
		}

		//request
		Socket socket = null;	

		//request to self
		if(server.equals(peer)) {
			table.del(key);					
			System.out.println("The pair with KEY " + key + ") is deleted in " + server.getID() + " successfully!!!");
		}
		else {
			try {	
				socket = new Socket(server.getIP(), server.getPort());

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Del+" + key);

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String answer = in.readLine();	

				System.out.println(answer);

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
		
		


		/*
		//request to backup
		Peer serverBackup = server.getBackup();		
		if(serverBackup != null) {
			try {	
				socket = new Socket(serverBackup.getIP(), serverBackup.getPort());

				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Del+" + key);

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String answer = in.readLine();	

				System.out.println(answer);

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
		*/
	}




	private Peer findPeerByKey(long key) {
		if(peer.getKeyMin()<= key && key<=peer.getKeyMax()) {
			return peer;
		}
		else {
			return servers.findByKeyRange(key);
		}
	}


	private int getInputKey() {

		Scanner scanner = new Scanner(System.in);		
		String keyStr = "";
		int key = -1;		

		boolean reinput = true;
		while(reinput) {
			keyStr = "";
			key = -1;			

			System.out.println("Enter the KEY (only contain numbers):");
			keyStr = scanner.next().trim();

			if(keyStr.matches("[0-9]+") && keyStr.length() > 0) {
				key = Integer.parseInt(keyStr);
				reinput = false;

				return key;
			}
			else {
				System.out.println("The KEY can only contain numbers, re-enter KEY!!!");
				continue;
			}			
		}

		return key;
	}




	public Peer getPeer() {
		return peer;
	}
	public void setPeer(Peer peer) {
		this.peer = peer;
	}
	public Servers getServers() {
		return servers;
	}
	public void setServers(Servers servers) {
		this.servers = servers;
	}
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
}
