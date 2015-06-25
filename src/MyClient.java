import java.net.*;

/**
 * @author mhty7
 *
 */
public class MyClient extends MyChat {

	/**
	 * 
	 */
	
	Socket s;
	MyConnection mc;
	public MyClient() {
		super();
	}

	public void init(){
		try{
			System.out.println("C:Connecting to server...");
			s=new Socket("localhost",8888);
			mc=new MyConnection(s);
			
			System.out.println("C: Connected");
			
			MyChatHandler mch = new MyChatHandler(this);

			MessageReceiveThread sendthr = new MessageReceiveThread(mc,mch);
			sendthr.start();

			
		}catch(Exception e){
			System.out.println("C: x_x" +e);
			e.printStackTrace();
		}
		
	}
	
	protected void sendMessage(String msg){
		MessageSendThread recievethr = new MessageSendThread(mc);
		recievethr.setInput(msg);
		recievethr.start();
	}
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyClient obj = new MyClient();
		obj.init();

	}

}
