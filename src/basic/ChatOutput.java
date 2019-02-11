package basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;

public class ChatOutput implements ChatProtocol {
	private PrintWriter os;
	private OutputStream oso;

	public ChatOutput(OutputStream out) throws IOException {
		this.os = new PrintWriter(out, true);
		this.oso = out;
	}

	public synchronized void sendName(String name) {
		os.println("NAME");
		os.println(name);
	}

	public synchronized void sendMessage(String user, String msg) {
		os.println("MESSAGE");
		os.println(user);
		os.println(msg);
	}

	public synchronized void sendPrivateMessage(String from, String to, String msg) {
		os.println("PRIVATE MESSAGE");
		os.println(from);
		os.println(to);
		os.println(msg);
	}

	public synchronized void sendUserList(Collection<String> ulist) {
		os.println("ULIST");
		ulist.forEach(os::println);
		os.println(".");
	}

	public synchronized void sendAskUserList() {
		os.println("AULIST");

	}

	public synchronized void sendQuit() {
		os.println("QUIT");
	}

	public synchronized void sendNameOK() {
		os.println("NAME OK");
	}

	public synchronized void sendNameBad() {
		os.println("NAME BAD");

	}

	public synchronized void createRoom(String room) {
		os.println("CREATE ROOM");
		os.println(room);
	}

	public synchronized void sendRoomOK(String room) {
		os.println("ROOM OK");
		os.println(room);
		// TODO Auto-generated method stub

	}

	public synchronized void sendRoomBad(String room) {
		// TODO Auto-generated method stub
		os.println("ROOM BAD");
		os.println(room);
	}

	public synchronized void deleteRoom(String room) {
		// TODO Auto-generated method stub
		os.println("DELETE ROOM");
		os.println(room);
	}

	public synchronized void sendRoomList(Collection<String> rlist) {
		os.println("RLIST");
		rlist.forEach(os::println);
		os.println(".");
	}

	public synchronized void sendRoomMessage(String room, String user, String msg) {
		os.println("ROOM MESSAGE");
		os.println(room);
		os.println(user);
		os.println(msg);
	}

	public synchronized void enterRoom(String room) {
		// TODO Auto-generated method stub
		os.println("ENTER ROOM");
		os.println(room);
	}

	public synchronized void leaveRoom(String room) {
		// TODO Auto-generated method stub
		os.println("LEAVE ROOM");
		os.println(room);
	}

	public synchronized void sendAskRoomUserList(String room) {
		os.println("ARULIST");
		os.println(room);
	}

	public synchronized void sendRoomUserList(Collection<String> ulist, String room) {
		os.println("RULIST");
		os.println(room);
		ulist.forEach(os::println);
		os.println(".");
	}

	public synchronized void sendEror(String string) {
		os.println("ERR");
		os.println(string);
		// TODO Auto-generated method stub

	}
	public synchronized void sendProposeFile(String user, String fName) {
		os.println("PROPOSE FILE");
		os.println(user);
		os.println(fName);
	}
	public synchronized void sendAcceptFile(String user, String fName) {
		os.println("ACCEPT FILE");
		os.println(user);
		os.println(fName);
	}
	public synchronized void sendRefusFile(String user, String fName) {
		os.println("REFUS FILE");
		os.println(user);
		os.println(fName);
	}

	public synchronized void sendFile(String to, String fName, File f) {
		try (FileInputStream fi = new FileInputStream(f)) {
			os.println("SEND FILE");
			os.println(to);
			os.println(fName);
			os.println(f.length());
			os.flush();
			byte buf[] = new byte[8192];
			int len = 0;
			while ((len = fi.read(buf)) != -1) {
				oso.write(buf, 0, len);
			}
		} catch (IOException ex) {
			System.out.println(ex + "erreur");
		}
	}
	
}

