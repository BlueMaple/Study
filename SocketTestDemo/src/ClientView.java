import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ClientView {
	private static JFrame jFrame;
	private static JComboBox<String> commandBox;
	private static JTextArea server_reply_text;
	private static JButton button;
	private static Map<String , String> itemMap;
	private static MainClient client = MainClient.getMainClient();

	public static void main(String[] args) {
		jFrame = new JFrame("客户端界面");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		jFrame.setBounds(((int)dimension.getWidth() - 200)/2-100, ((int)dimension.getHeight())/2-300, 550, 450);
		jFrame.setResizable(false);
		jFrame.setLayout(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel input_command = new JLabel("命令:");
		input_command.setBounds(10, 10, 100, 50);
		jFrame.add(input_command);
		
		commandBox = new JComboBox<String>();
		createItems();
		commandBox.setSelectedIndex(-1);
		commandBox.setEditable(true);
		commandBox.setBounds(50, 15, 400, 50);
		commandBox.setVisible(true);
		jFrame.add(commandBox);
		
		button = new JButton("发送");
		button.setBounds(50, 75, 150, 40);
		jFrame.add(button);
		jFrame.setVisible(true);
		
		server_reply_text = new JTextArea();
		server_reply_text.setBounds(50, 150, 400, 250);
		jFrame.add(server_reply_text);
		
		addListener();
	}

	private static void createItems() {
		// TODO Auto-generated method stub
		itemMap = new HashMap<String , String>();
		Properties prop = new Properties();
		try {
			InputStream fis = new BufferedInputStream(new FileInputStream("commands.properties"));
			prop.load(fis);
            Enumeration<?> en = prop.propertyNames();
            while (en.hasMoreElements()) {
            	String key = new String(((String)en.nextElement()).getBytes() , "UTF-8");
                String property = prop.getProperty(key);              
                itemMap.put(key, property);
            }

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<Entry<String , String>> iter = itemMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String , String> entry = iter.next();
			String key = entry.getKey();
			commandBox.addItem(key);
		}

	}

	private static void addListener() {
		// TODO Auto-generated method stub
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String txt = (String)commandBox.getSelectedItem();
				String command = txt;
				if(itemMap.containsKey(txt)){
					command = itemMap.get(txt);
				}
				
				try {
					client.start(command);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String reply = client.getReply();
				if(reply != null){
					reply += server_reply_text.getText();
					server_reply_text.setText("\n"+reply);
				}
			}
		});

		commandBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		jFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				client.closeSocket();
			}
		});
	}
}
