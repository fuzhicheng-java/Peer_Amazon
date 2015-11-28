

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Init {

	
	public static void main(String[] args) throws IOException {
		
		Peer peer = new Peer();
		Servers servers = new Servers();
		Table table = new Table();
		Config configureFile = new Config(peer, servers);
		
					
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\n");
		
		String fileName = "";
		boolean reinput = true;
		while(reinput) {
			System.out.println("Enter the config file path and name (such as \"/root/CH/P1/config.txt\"):");
			fileName = scanner.next().trim();
			
			if(new File(fileName).isFile()) {
				reinput = false;
				break;
			}
			else {
				System.out.println("The input config file does NOT exist, re-enter!!!");
			}
		}
		
		
		configureFile.setFileName(fileName);
		configureFile.configure();		
		
		System.out.println("***********************************");
		System.out.println("The system is configured successfully!");
		System.out.println("***********************************");		
		
		
		
		//start listener thread
		new Listener(peer, table).start();
		
		
		//start requester thread
		new Requester(peer, servers, table).start();
	}

}
