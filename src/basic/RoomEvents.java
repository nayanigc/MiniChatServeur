package basic;

public interface RoomEvents {
	public void roomUserListChanged(String room);
	public void roomChatMessageSent(String room, String from, String message);
}
