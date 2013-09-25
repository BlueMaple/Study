package com.fix.obd.tcp.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import com.fix.obd.protocol.ODBProtocol;
import com.fix.obd.util.IdPropertiesUtil;

/**
 * @title UploadTerminalDataTask.java
 * @package com.fix.obd.tcp.thread
 * @description TODO
 * @author youme.ma
 * @date 2013-9-3 下午2:50:02
 * @version V1.0
 */
public class UploadTerminalDataTask implements Runnable {
	private Socket socket;

	/**
	 * 输入流数据
	 */
	private InputStream is;
	/**
	 * 输出流数据
	 */
	private OutputStream os;

	public UploadTerminalDataTask(Socket socket) {
		this.socket = socket;
	}

	private static final int BUFFERSIZE = 512;
	/**
	 * 日志对象
	 */
	private Logger logger = Logger.getLogger(UploadTerminalDataTask.class);

	@Override
	public void run() {
		try {
			SocketAddress terminalAddress = socket.getRemoteSocketAddress();
			logger.info("终端连接地址：" + terminalAddress);

			is = socket.getInputStream();
			os = socket.getOutputStream();

			@SuppressWarnings("unused")
			int recvMsgSize;

			byte[] receiveBuf = new byte[BUFFERSIZE];
			while ((recvMsgSize = is.read(receiveBuf)) != -1) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < receiveBuf.length; i++) {
					stringBuilder.append(String.format("%02x", receiveBuf[i]));
				}
				logger.info("读取终端参数：" + stringBuilder);
				String str = stringBuilder.toString();
				str = str.substring(str.indexOf("aa")+2);
				str = str.substring(0,str.lastIndexOf("aa"));
				String[] messages = str.split("aaaa");
				for(int i=0;i<messages.length;i++){
					System.out.println("Message:" + messages[i]);
					try {
						String operationId = messages[i].substring(26,30);
						IdPropertiesUtil p = new IdPropertiesUtil();
						String propertyName = p.getProtocolById(operationId);
						if(propertyName!=null&&!"".equals(propertyName)){
							Constructor con = Class.forName("com.fix.obd.protocol.impl." + propertyName).getConstructor(String.class);
							ODBProtocol odbProtocol = (ODBProtocol) con.newInstance(messages[i]);
							odbProtocol.DBOperation();
							byte[] responseBytes = odbProtocol.replyToClient();
							if(responseBytes!=null)
								os.write(responseBytes);
						}
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
