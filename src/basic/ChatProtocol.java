package basic;
import java.util.Collection;

public interface ChatProtocol {

	default void sendName(String name){}
	default void sendNameOK() {}
	default void sendNameBad() {}
	default void sendMessage(String user, String msg) {}
	default void sendAskUserList() {}
	default void sendUserList (Collection <String> ulist) {}
	default void sendPrivateMessage(String from, String to, String msg) {}
	default void sendQuit() {}
	//////////////////////////////////////////////
	default void sendCreateRoom(String room) {}
	default void sendRoomOK(String room) {}
	default void sendRoomBad(String room) {}
	default void deleteRoom(String room) {}
	default void sendRoomList(Collection <String> rlist) {}
	default void sendRoomMessage (String room,String user, String msg) {}
	default void enterRoom(String room) {}
	default void leaveRoom(String room) {}
	default void sendAskRoomUserList (String room) {}
	default void sendRoomUserList(Collection <String> ulist,String room) {}
	default void sendEror(String string) {}
	
}
