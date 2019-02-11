package basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

public class ChatInput {
	private ChatProtocol handler;
	private InputStream in;

	public ChatInput(InputStream in, ChatProtocol handler) {
		this.in = in;
		this.handler = handler;
	}

	public void doRun() throws IOException {
		String strMsg, strName, room;
		ArrayList<String> userList;
		ArrayList<String> roomList = new ArrayList<>();
		try (LineBufferedInputStream is = new LineBufferedInputStream(in)) {
			while (true) {
				String line = is.readLine();
				if (line == null)
					throw new IOException();
				switch (line) {
				case "NAME":
					strName = is.readLine();
					handler.sendName(strName);
					break;
				case "MESSAGE":
					strName = is.readLine();
					strMsg = is.readLine();
					handler.sendMessage(strName, strMsg);
					break;
				case "PRIVATE MESSAGE":
					strName = is.readLine();
					String strTo = is.readLine();
					strMsg = is.readLine();
					handler.sendPrivateMessage(strName, strTo, strMsg);
					break;
				case "ULIST":
					userList = new ArrayList<>();
					String x;
					while (!(x = is.readLine()).equals(".")) {
						userList.add(x);
					}

					handler.sendUserList(userList);
					break;
				case "AULIST":
					handler.sendAskUserList();
					break;
				case "QUIT":
					handler.sendQuit();
					break;
				case "NAME OK":
					handler.sendNameOK();
					break;
				case "NAME BAD":
					handler.sendNameBad();
					break;
				case "CREATE ROOM":
					room = is.readLine();
					// roomList.add(room);
					handler.sendCreateRoom(room);
					break;
				case "ROOM MESSAGE":
					room = is.readLine();
					strName = is.readLine();
					strMsg = is.readLine();
					handler.sendRoomMessage(room, strName, strMsg);
					break;
				case "ROOM OK":
					room = is.readLine();
					handler.sendRoomOK(room);
					break;
				case "ROOM BAD":
					room = is.readLine();
					handler.sendRoomBad(room);
					break;
				case "DELETE ROOM":
					room = is.readLine();
					handler.deleteRoom(room);
					break;
				case "RLIST":
					roomList = new ArrayList<>();
					String y;
					while (!(y = is.readLine()).equals(".")) {
						roomList.add(y);
					}
					handler.sendRoomList(roomList);
					break;
				case "ENTER ROOM":
					room = is.readLine();
					handler.enterRoom(room);
					break;
				case "LEAVE ROOM":
					room = is.readLine();
					handler.leaveRoom(room);
					break;
				case "ARULIST":
					room = is.readLine();
					handler.sendAskRoomUserList(room);
					break;
				case "RULIST":
					room = is.readLine();
					userList = new ArrayList<>();
					String l;
					while (!(l = is.readLine()).equals(".")) {
						userList.add(l);
					}
					handler.sendRoomUserList(userList, room);
					break;
				case "ERR":
					room = is.readLine();
					handler.sendEror(room);
					break;
				case "PROPOSE FILE":
					strName = is.readLine();
					String FName = is.readLine();
					handler.sendProposeFile(strName, FName);
					break;
				case "ACCEPT FILE":
					strName = is.readLine();
					String FName1 = is.readLine();
					handler.sendAcceptFile(strName, FName1);
					break;
				case "REFUS FILE":
					strName = is.readLine();
					String FName2 = is.readLine();
					handler.sendRefusFile(strName, FName2);
					break;
				case "SEND FILE":
					strName = is.readLine();
					String FName3 = is.readLine();
					int FSize = Integer.parseInt(is.readLine());
					File f = File.createTempFile(FName3, "txt");
					try (FileOutputStream fo = new FileOutputStream(f)) {
						byte buf[] = new byte [8192];
						int len = 0;
						int reste = FSize;
						while (reste > 0 && len != -1) {
							int toRead = buf.length;
							if (toRead > reste)
								toRead = reste;
							len = is.read(buf, 0, toRead);
							fo.write(buf, 0, len);
							reste -= len;
						}
					}
					handler.sendFile(strName, FName3, f);
					break;
				default:
					try {
						throw new ChatProtocolException("Invalid input");
					} catch (ChatProtocolException e) {
						System.out.println("le message " + e);
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
				}

			}

		}
	}

	public ChatProtocol getHandler() {
		return handler;
	}

	public void setHandler(ChatProtocol handler) {
		this.handler = handler;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}
}
