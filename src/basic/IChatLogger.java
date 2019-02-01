package basic;

public interface IChatLogger {
	public void clientConnected(String ip);
	public void clientDisconnected(String ip,String name);
	public void clientGotName(String ip, String name);
	public void clientGotCommand(String name, int command);
	public void publicChat(String from, String msg);
	public void privateChat(String from, String to,String msg);
	public void systemMessage(String msg);
}
