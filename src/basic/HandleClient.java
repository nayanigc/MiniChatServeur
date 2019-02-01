package basic;

import java.io.IOException;
import java.net.Socket;

public class HandleClient implements Runnable, ChatProtocol, ChatModelEvents {

	private final Socket s;

	private ChatOutput cho;
	private ChatInput chi;
	private String name = "";
	private IChatLogger logger = null;

	private enum ClientState {
		ST_INIT, ST_NORMAL
	};

	private ClientState state = ClientState.ST_INIT;

	private boolean stop = false;

	public HandleClient(Socket s, IChatLogger logger) throws IOException {
		this.s = s;
		this.logger = logger;
	}

	public synchronized void finish() {

		try {
			s.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (name != null)
			ChatModel.unregisterUser(name);
		logger.clientDisconnected(s.toString(), name);

	}

	@Override
	public void sendName(String name) {
		ChatProtocol.super.sendName(name);
		String newName = name;
		if (ChatModel.existUserName(newName)) {
			cho.sendNameBad();
		} else {
			if (state == ClientState.ST_INIT) {
				ChatModel.registerUser(newName, this);
				state = ClientState.ST_NORMAL;
			} else {
				ChatModel.renameUser(this.name, newName, this);
			}
			this.name = newName;
			cho.sendNameOK();
			logger.clientGotName(s.toString(), name);
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String user, String msg) {
		// TODO Auto-generated method stub
		if (state == ClientState.ST_INIT)
			return;
		ChatModel.sendChatMessage(name, msg);
		logger.publicChat(name, msg);

	}

	@Override
	public void sendAskUserList() {
		// TODO Auto-generated method stub
		if (state == ClientState.ST_INIT)
			return;
		cho.sendUserList(ChatModel.getUserNames());
	}

	@Override
	public void sendPrivateMessage(String from, String to, String msg) {
		// TODO Auto-generated method stub
		ChatProtocol.super.sendPrivateMessage(from, to, msg);
		if (state == ClientState.ST_INIT)
			return;
		ChatModel.sendPrivateChatMessage(name, to, msg);
		logger.privateChat(name, to, msg);
	}

	@Override
	public void sendQuit() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void run() {
		try (Socket s1 = s) {
			cho = new ChatOutput(s1.getOutputStream());
			chi = new ChatInput(s1.getInputStream(), this);
			chi.doRun();
		} catch (IOException ex) {
			if (name != null)
				ChatModel.unregisterUser(name);
			logger.clientDisconnected(s.toString(), name);
			/*
			 * if (!stop) { finish(); }
			 */
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void userListChanged() {
		cho.sendUserList(ChatModel.getUserNames());
		// TODO Auto-generated method stub

	}

	@Override
	public void chatMessageSent(String from, String message) {
		if (!from.equals(name)) {
			cho.sendMessage(from, message);
		}
		// ChatModel.chat
		// TODO Auto-generated method stub

	}

	@Override
	public void privateChatMessageSent(String from, String to, String message) {
		if (to.equals(name)) {
			cho.sendPrivateMessage(from, to, message);
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdownRequested() {
		stop = true;
		finish();
		// TODO Auto-generated method stub

	}

	public void sendCreateRoom(String room) {
		if (state == ClientState.ST_INIT) {
			return;
		}
		if (ChatModel.existRoom(room))
			cho.sendRoomBad(room);
		else {
			ChatModel.addRoom(room, name);
			cho.sendRoomOK(room);
		}
	}

	public void sendRoomMessage(String room, String from, String message) {
		if (state == ClientState.ST_INIT) {
			cho.sendEror("Not initialized…");
		}
		if (ChatModel.roomHasUser(room, name)) {
			ChatModel.roomSendChatMessage(room, name, message);
		} else {
			cho.sendEror("Not in room…");
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void roomListChanged() {
		if (state == ClientState.ST_INIT)
			return;
		cho.sendRoomList(ChatModel.getRooms());
		// TODO Auto-generated method stub

	}

	@Override
	public void roomUserListChanged(String room) {
		// TODO Auto-generated method stub
		if (state == ClientState.ST_INIT) return;
		cho.sendRoomUserList(ChatModel.roomGetUserList(room), room);

	}

	@Override
	public void roomChatMessageSent(String room, String from, String message) {
		// TODO Auto-generated method stub
		if (ChatModel.roomHasUser(room, from)) {
			cho.sendRoomMessage(room, from, message);
		}
	}

}
