
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
 
public class TestCase {
 
    public static void main(String[] args) {
 
        String fileName = "config.txt";
         
        Scanner scanner = new Scanner(System.in);       
        int nodeNum = 0;
 
        System.out.println("Enter the node number:");
        nodeNum = scanner.nextInt();
        List<String> IPlist = readConfig(fileName, nodeNum);
        List<Peer> peerList = init(nodeNum, IPlist);
 
        Table table = new Table();
        Servers servers = new Servers();
        servers.setServerList(peerList);
        Requester requester = new Requester(peerList.get(0), servers, table);
         
        //start listener thread
        Listener listen=new Listener(peerList.get(0), table);
        listen.start();
 
        int testNum = 100000;
        //int testNum=100;
        testInsert(testNum, requester);
        testSearch(testNum, requester);
        testDelete(testNum, requester);
 
        // List<Peer> peerList = init(nodeNum);
 
        // Peer client = peerList.get(0);
 
        //listen.stop();
    }
 
    public static Peer init1(int nodeIndex) {
        String ID = "P" + nodeIndex;
        String IP = "127.0.0.1";
        int port = 9090;
        long keyMin = (nodeIndex - 1) * 100000;
        long keyMax = nodeIndex * 100000 - 1;
 
        String path = "";
 
        return new Peer(ID, IP, port, path, keyMin, keyMax);
    }
 
    public static List<String> readConfig(String fileName, int nodeNum) {
 
        List<String> infoList = new ArrayList<String>();
        String line = "";
 
        try {
 
            FileReader fileReader = new FileReader(fileName);
 
            BufferedReader bufferedReader = new BufferedReader(fileReader);
             
            for(int i = 0; i < nodeNum; i++)
            {
                line = bufferedReader.readLine();
                infoList.add(line);
            }
 
            /*
            while ((line = bufferedReader.readLine()) != null) {
                infoList.add(line);
            }
            */
 
            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
 
        return infoList;
    }
 
    public static List<Peer> init(int nodeNum, List<String> IPlist) {
        List<Peer> peerList = new ArrayList<Peer>();
 
        for (int i = 0; i < nodeNum; i++) {
            String ID = "P" + i;
            String IP = IPlist.get(i);
            int port = 9090;
            long keyMin = (i - 1) * 100000;
            long keyMax = i * 100000 - 1;
 
            String path = "";
 
            peerList.add(new Peer(ID, IP, port, path, keyMin, keyMax));
        }
 
        return peerList;
    }
 
    public static void testInsert(int number, Requester requester) {
 
        long totalTime = 0, endTime, startTime, tempTime, outTime, minTime = 100000000, maxTime = 0;
        double averageTime;
        for (int i = 0; i < number; i++) {
 
            long key = getRandomKey();
            String value = getRandomName();
            System.out.println(value);
            //long key=value.hashCode();
            startTime = System.currentTimeMillis();
            requester.put(key, value);
            // boolean test=client.sendFileShareRequest(name, value + name +
            // ".txt"); //System.out.println(value);
            endTime = System.currentTimeMillis();
 
            // System.out.println(test);
            outTime = endTime - startTime;
            if (minTime > outTime) {
                minTime = outTime;
            }
            if (maxTime < outTime) {
                maxTime = outTime;
            }
            totalTime += outTime;
 
        }
        averageTime = totalTime / number * 1.0;
        System.out.println("Insert Operation: Total Number: " + number
                + " Total Time: " + totalTime + " Min Time: " + minTime
                + " Max Time: " + maxTime + " Average Time: " + averageTime);
    }
 
    public static void testDelete(int number, Requester requester) {
        long totalTime = 0, endTime, startTime, tempTime, outTime, minTime = 100000000, maxTime = 0;
        double averageTime;
        for (int i = 0; i < number; i++) {
 
            long key = getRandomKey();
            // String value = getRandomName();
 
            startTime = System.currentTimeMillis();
            requester.del(key);
            // boolean test=client.sendFileShareRequest(name, value + name +
            // ".txt"); //System.out.println(value);
            endTime = System.currentTimeMillis();
 
            // System.out.println(test);
            outTime = endTime - startTime;
            if (minTime > outTime) {
                minTime = outTime;
            }
            if (maxTime < outTime) {
                maxTime = outTime;
            }
            totalTime += outTime;
 
        }
        averageTime = totalTime / number * 1.0;
        System.out.println("Remove Operation: Total Number: " + number
                + " Total Time: " + totalTime + " Min Time: " + minTime
                + " Max Time: " + maxTime + " Average Time: " + averageTime);
    }
 
    public static void testSearch(int number, Requester requester) {
        long totalTime = 0, endTime, startTime, tempTime, outTime, minTime = 100000000, maxTime = 0;
        double averageTime;
        for (int i = 0; i < number; i++) {
 
            long key = getRandomKey();
            // String value = getRandomName();
 
            startTime = System.currentTimeMillis();
            requester.get(key);
            // boolean test=client.sendFileShareRequest(name, value + name +
            // ".txt"); //System.out.println(value);
            endTime = System.currentTimeMillis();
 
            // System.out.println(test);
            outTime = endTime - startTime;
            if (minTime > outTime) {
                minTime = outTime;
            }
            if (maxTime < outTime) {
                maxTime = outTime;
            }
            totalTime += outTime;
 
        }
        averageTime = totalTime / number * 1.0;
        System.out.println("LookUp Operation: Total Number: " + number
                + " Total Time: " + totalTime + " Min Time: " + minTime
                + " Max Time: " + maxTime + " Average Time: " + averageTime);
    }
 
    public static String getRandomName() {
        Random gen = new Random(); // put in random seed
        int min = 2;
        int max = 10;
        int len = min + gen.nextInt(max - min + 1);
        StringBuilder s = new StringBuilder(len);
        while (s.length() < len) {
            // 97 is ASCII for character 'a', and 26 is number of alphabets
            s.append((char) (97 + gen.nextInt(26)));
        }
        return s.toString();
 
    }
 
    public static long getRandomKey() {
        return getRandomName().hashCode();
    }
 
}