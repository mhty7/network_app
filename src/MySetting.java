/**
 * 
 */

/**
 * @author mhty7
 *
 */

public class MySetting {

	public final static int INITIAL_ROW_COUNT = 25;
	public final static int INITIAL_COLOMN_COUNT_FOR_CHAT = 40;
	public final static int INITIAL_COLOMN_COUNT_FOR_CLIENTS = 20;
	
	public final static String goodbye="Goodbye.";
	
	public enum Type{
		CHAT_WINDOW,ONLINE_CLIENT
	};
	
	public final static char INITIAL_PROTOCOL ='/';
	public final static String UPDATE_CLIENT_PROTOCOL =INITIAL_PROTOCOL+"updateclient";
	
	public final static String CHANGE_STATUS_PROTOCOL =INITIAL_PROTOCOL+"changestatus";
	public final static String WHISPER_PROTOCOL =INITIAL_PROTOCOL+"whisper";
	public final static String CHANGE_NAME_PROTOCOL =INITIAL_PROTOCOL+"changename";
	public final static String QUIT_PROTOCOL =INITIAL_PROTOCOL+"quit";
	
}
