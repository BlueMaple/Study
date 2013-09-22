import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public class MainClient {
	private static MainClient client;
	private Socket clientSocket;
	
	/**
	 * IP地址
	 */
	private String IP = "localhost";
	/**
	 * 服务端口
	 */
	private int serverPort = 4567;

	/**
	 * 运行状态
	 */
	private int Status = 0;
	/**
	 * 命令条数
	 */
	private int Commands = 20;
	/**
	 * 心跳间隔
	 */
	private long HeartBeat = 2000;

	
	private MainClient(){
		try {
			clientSocket = new Socket(IP, serverPort);
			clientSocket.setSoTimeout(3000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("主机TMD没开！！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static MainClient getMainClient(){
		if(client == null){
			client = new MainClient();
		}
		
		return client;
	}
	
	public void start(String s) throws UnknownHostException, IOException, InterruptedException{
		int index = 0;
		while(index < 1){
			OutputStream outputStream = clientSocket.getOutputStream();
			outputStream.write(get16Bytes(s));

			outputStream.flush();
			
			index++;
			Thread.sleep(HeartBeat);
		}
		
	}
	
	private byte[] get16Bytes(String s) {
		// TODO Auto-generated method stub
		byte[] sByte = s.getBytes();
		byte[] result = new byte[sByte.length];
		for(int i = 0 ; i < s.length() ; i+=2){
			byte front = Byte.decode("0x"+s.charAt(i)).byteValue();
			front = (byte) ((byte) front << 4);
			byte back = Byte.decode("0x"+s.charAt(i+1)).byteValue();
			
			result[i/2] = (byte)(front ^ back);
		}
		
		return result;
	}

	public void closeSocket() {
		// TODO Auto-generated method stub
		if(clientSocket != null &&!clientSocket.isClosed()){
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getReply() {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[256];
		String result = null;
		try {
			InputStream is = clientSocket.getInputStream();
			is.read(bytes);
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				builder.append(String.format("%02x", bytes[i]));
			}

			result = builder.toString();
			for(int i = result.length()-1 ; i >= 1 ; i--){
				if(result.charAt(i) == 'a' && result.charAt(i-1) == 'a'){
					result = result.substring(0, i+1);
					break;
				}
			}
			
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

//	public static void main(String[] args){
//		try {
//			MainClient.getMainClient().start();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
