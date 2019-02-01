package basic;
//**//
public class TextChatLogger implements IChatLogger {

	@Override
	public void clientConnected(String ip) {
		// TODO Auto-generated method stub
		System.out.println("le client est connecte " +ip);
	}

	@Override
	public void clientDisconnected(String ip, String name) {
		// TODO Auto-generated method stub
		System.out.println("le client est déconnecte "+ip);
		System.out.print("le nom est : "+name);
	}

	@Override
	public void clientGotName(String ip, String name) {
		// TODO Auto-generated method stub
		System.out.println(ip);
		System.out.print("le nom est : "+name);
	}

	@Override
	public void clientGotCommand(String name, int command) {
		// TODO Auto-generated method stub
		System.out.print("le nom est : "+name);
		System.out.println(command);
	}

	@Override
	public void publicChat(String from, String msg) {
		// TODO Auto-generated method stub
		System.out.println("l'utilisateur est "+from);
		System.out.println("le message est "+ msg);
	}

	@Override
	public void privateChat(String from, String to, String msg) {
		// TODO Auto-generated method stub
		System.out.println("l'utilisateur est "+from);
		System.out.println("le destinateur est "+to);
		System.out.println("le message est "+ msg);
	}

	@Override
	public void systemMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println("le message est "+ msg);
	}

}
