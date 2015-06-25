import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */

/**
 * @author mhty7
 *
 */
public class MyChatHandler {

	/**
	 * 
	 */
	private MyChat mc;
	
	
	public MyChatHandler(MyChat mc) {
		this.mc=(MyChat)mc;
	}
	
	public void handleWindow(String msg){
		
		String pst;
		Pattern p;
		Matcher m;
		
		pst="(^"+MySetting.UPDATE_CLIENT_PROTOCOL+")(\\s+)(.*)";
		p=Pattern.compile(pst);
		m=p.matcher(msg);
		
		if(m.find()){
			scourClientWindow();
			
			for(String s : m.group(3).split(";")){
				pst="([^\\s]+)(\\s?)(-)(\\s?)(.*)";
				p=Pattern.compile(pst);
				m=p.matcher(s);
				if(m.find()){
					writeToClientWindow(m.group(1).trim()+((m.group(5).equals(""))?"":" - "+m.group(5).trim()));
				}
			}
			return;
		}
		
		
		

		writeToChatWindow(msg);
		
	}
	

	private void scourClientWindow(){
		mc.scourClientWindow();
	}
	private void writeToClientWindow(String msg){
		mc.writeToWindow(msg+"\n", MySetting.Type.ONLINE_CLIENT);
	}
	
	
	private void writeToChatWindow(String msg){

		mc.writeToWindow(msg+"\n",MySetting.Type.CHAT_WINDOW);
		
	}
	

	


}
