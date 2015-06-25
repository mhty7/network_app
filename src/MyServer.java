import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mhty7
 *
 */
public class MyServer {

	/**
	 * 
	 */
	private static int uNum=0;
	
	private Hashtable<String,MyConnection> userHashTable;
	private Hashtable<ServerThread,String> threadLookup;
	private Hashtable<String,String> clientsData;
	
	public MyServer() {
		userHashTable= new Hashtable<String,MyConnection>();
		threadLookup= new Hashtable<ServerThread,String>();
		clientsData=new Hashtable<String,String>();
	}
	
	private synchronized void broadcastMessage(String msg,ServerThread thr){
		String sender="";
		if(thr!=null){
			if(threadLookup.containsKey(thr)==true){
				sender = threadLookup.get(thr);
			}
			else{
				sender ="anonymous";
			}
		}
		else{
			sender = "Server message";
		}
		
		for (Enumeration<MyConnection> e = userHashTable.elements(); e.hasMoreElements();)
			e.nextElement().sendMessage(sender+" : "+msg);
	}
	
	private synchronized void broadcastState(String message){
		for (Enumeration<MyConnection> e = userHashTable.elements(); e.hasMoreElements();)
			e.nextElement().sendMessage(message);
	}
	
	private synchronized void whisperMessage(String msg,ServerThread from,String to){
		if(from!=null){
			if(threadLookup.containsKey(from)==true){
				if(to!=null){
					if(userHashTable.containsKey(to)==true){
						userHashTable.get(to).sendMessage("["+threadLookup.get(from)+" whispers] "+msg);
					}
				}
				else{
					to=threadLookup.get(from);
					userHashTable.get(to).sendMessage("Server message"+" : "+"Invalid command "+msg);
				}
			}
		}
	}
	
	
	//
	public void notifyMessage(String input, ServerThread sthr) {
		String pst;
		Pattern p;
		Matcher m;
		

		
		if(input.charAt(0)==MySetting.INITIAL_PROTOCOL){
			
			pst="(^"+MySetting.CHANGE_STATUS_PROTOCOL+")(\\s+)(.+)";
			p=Pattern.compile(pst);
			m=p.matcher(input);
			if(m.find()){
				updateUser(sthr,null,m.group(3));
				return;
			}
			
			
			pst="(^"+MySetting.CHANGE_NAME_PROTOCOL+")(\\s+)([^\\s]+)";
			p=Pattern.compile(pst);
			m=p.matcher(input);
			if(m.find()){
				updateUser(sthr,m.group(3),null);
				return;
			}
			
			
			pst="(^"+MySetting.WHISPER_PROTOCOL+")(\\s+)([^\\s]+)(\\s+)(.+)";
			p=Pattern.compile(pst);
			m=p.matcher(input);
			if(m.find()){
				whisperMessage(m.group(5),sthr,m.group(3));
				return;
			}
			
			pst="(^"+MySetting.QUIT_PROTOCOL+")(\\s*)";
			p=Pattern.compile(pst);
			m=p.matcher(input);
			if(m.find()){
				removeUser(sthr);
				return;
			}
			

			whisperMessage(input,sthr,null);
			
		}
		else{
			broadcastMessage(input,sthr);
			return;
		}
	}
	
	private synchronized void updateClient(){
		String key="";
		String val="";
		String msg ="";
		Enumeration<String> e = clientsData.keys();
		while(e.hasMoreElements()){
			key=e.nextElement();
			val=clientsData.get(key);
			msg+=key+" - "+val+";";
		}
		broadcastState(MySetting.UPDATE_CLIENT_PROTOCOL+" "+msg);
	}
	
	private synchronized boolean addToTable(String uname,MyConnection mc,ServerThread thr,String newStatus){
		boolean b=false;
		if(uname!=null&&mc!=null&&thr!=null){
			if(threadLookup.containsKey(thr)==false){
				if(userHashTable.containsKey(uname)==false){
					if(clientsData.containsKey(uname)==false){
						threadLookup.put(thr, uname);
						userHashTable.put(uname, mc);
						clientsData.put(uname,(newStatus!=null)?newStatus:"");
						b=true;
					}
				}
			}
		}
		return b;
	}
	private synchronized boolean removeFromTable(ServerThread thr,String uname){
		boolean b=false;
		if(thr!=null&&uname!=null){
			if(threadLookup.containsKey(thr)==true){
				if(userHashTable.containsKey(uname)==true){
					if(clientsData.containsKey(uname)==true){
						threadLookup.remove(thr);
						userHashTable.remove(uname);
						clientsData.remove(uname);
						b=true;
					}
				}
			}
		}
		return b;
	}
	
	private synchronized void addUser(String uname,MyConnection mc,ServerThread thr){
		if(uname!=null&&mc!=null&&thr!=null){
			if(addToTable(uname,mc,thr,null)==true){
				updateClient();
				broadcastMessage(uname+" has connected",null);
			}
		}
	}
	private synchronized void removeUser(ServerThread thr){
		if(thr!=null){
			String uname=threadLookup.get(thr);
			if(removeFromTable(thr,uname)==true){
				thr.halt();
				updateClient();
				broadcastMessage(uname+" has disconnected",null);
			}
		}
	}
	
	private synchronized void updateUser(ServerThread thr,String newName,String newStatus){
		if(newName!=null){
			if(userHashTable.containsKey(newName)){
				return;
			}
		}
		
		if(threadLookup.containsKey(thr)==true){
			String name = threadLookup.get(thr);
			String status = clientsData.get(name);
			if(userHashTable.containsKey(name)==true){
				MyConnection tmp= userHashTable.get(name);
				
				if(removeFromTable(thr,name)==true){
					if(addToTable((newName!=null)?newName:name,tmp,thr,(newStatus!=null)?newStatus:status)==true){
						updateClient();
						if(newName!=null){
							broadcastMessage(name+" has changed name to "+newName,null);
							
						}
						else if(newStatus!=null){
							broadcastMessage(name+" has changed status to "+'"'+newStatus+'"',null);
						}
					}
				}
			}
		}
	}
	
	
	private void init(){
		
		try{
			System.out.println("S:Starting server...");
			ServerSocket ssocket = new ServerSocket(8888);
			//System.out.println("S:Waiting for connections to come...");
			
			while(true){
				
				Socket s= ssocket.accept();
				MyConnection mc =new MyConnection(s);
				ServerThread sthr = new ServerThread(mc,this);
				addUser("Client"+uNum++,mc,sthr);
				sthr.start();
				
			
			}
		}catch(Exception e){
			System.out.println("S: x_x"+e);
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		MyServer obj=new MyServer();
		obj.init();
	}


	

}
