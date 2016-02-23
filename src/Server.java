import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.ByteBuffer;

/*
* Server class
* CIS 457 Project 2, part 1
* Kevin Anderson, Brett Greenman, Jonathan Powers
* This is the main server class the listens for the requests
* and forwards them onto the DNS servers. 
*/
class Server {
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		while (true) {
			byte[] receiveData = new byte[512];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			serverSocket.receive(receivePacket);

			String message = new String(receiveData);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			
			ByteBuffer buf = ByteBuffer.wrap(message.getBytes());
			Header header = new Header();
			header = header.fromBytes(buf);
			Question ques = new Question();
			ques = ques.fromBytes(buf);
			
			System.out.println("Client Request: ");
			System.out.println("Header: ");
			System.out.println(header.toString());
			System.out.println("Question: ");
			System.out.println(ques.toString());           
			/*if(ques.getqClass() != (short) 1){
			    byte[] sendData = ("The class of domain you requested is not valid." +
			    " Please use an Internet address").getBytes();
			    DatagramPacket messageReturn = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			    serverSocket.send(messageReturn);
			}
			else if(ques.getqType() != (short) 1){
			    byte[] sendData = ("The type of address you requested is not valid. " +
			    "Please use a host address.").getBytes();
			    DatagramPacket messageReturn = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			    serverSocket.send(messageReturn);
			}
			else{*/
			InetAddress address = InetAddress.getByName("192.33.4.12");
			System.out.println("LENGTH: " + (header.toBytes().length + ques.toBytes().length));
			
			System.out.print(new String(receiveData));
			DatagramPacket dnsRequest = new DatagramPacket(receiveData, receiveData.length, address, 53);
			System.out.println("\nRequest sent to Servers");
			serverSocket.send(dnsRequest);
			
			byte[] rData = new byte[512];
			DatagramPacket dnsResponse = new DatagramPacket(rData, rData.length);
			serverSocket.receive(dnsResponse);
			System.out.println("Response Recieved");
			String message2 = new String(rData);
			
			
			System.out.println("Response Message: " + message2);
			ByteBuffer rBuf = ByteBuffer.wrap(message2.getBytes());
			Header rHeader = new Header();
			rHeader = rHeader.fromBytes(rBuf);
			Question rQuestion = new Question();
			rQuestion = rQuestion.fromBytes(rBuf);
			
			//Start the recursion process. 
			if(rHeader.getAuthorityRecords() > (short) 0 || rHeader.getAdditionalRecords() > (short) 0){
			    System.out.println("Not the droids you are looking for\n\n");
			    address = InetAddress.getByName("192.5.6.30"); //Hard coded a value for .edu domain for testing
			    dnsRequest = new DatagramPacket(receiveData, receiveData.length, address, 53);
			    dnsResponse = new DatagramPacket(rData, rData.length);
			    serverSocket.receive(dnsResponse);
			    System.out.println("Response Recieved 2\n");
			}

			System.out.println("DNS Response: ");
			System.out.println("Header: ");
			System.out.println(rHeader.toString());
			System.out.println("Question: ");
			System.out.println(rQuestion.toString());
			
			byte[] sendData = rData;
			DatagramPacket sendPacket = new DatagramPacket(rData, rData.length, IPAddress, port);
			System.out.println("Response sent to Client\n\n\n\n");
			serverSocket.send(sendPacket);
			//}
		}
	}

	/*
	 * This method checks domain extension of the request of a string.
	 * It never gets used because we decided not to go this route. 
	 */
	public static boolean checkDomainRequest(String m)
			throws FileNotFoundException {
		List<String> domainExtensions = new ArrayList<String>();
		File text = new File("domain_extensions.txt");
		Scanner fileScanner = new Scanner(text);
		while (fileScanner.hasNext()) {
			domainExtensions.add(fileScanner.next());
		}
		int index = m.lastIndexOf('.');
		String temp = m.substring(index + 1);
		if (domainExtensions.contains(temp.toUpperCase())) {
			return true;
		}
		return false;
	}
}