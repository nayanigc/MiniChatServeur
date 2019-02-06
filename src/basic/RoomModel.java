package basic;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class RoomModel {
	String master;
	String roomName;
	final private Map<String, RoomEvents> roomObserverList = new TreeMap<>();

	public RoomModel(String roomName, String master, RoomEvents handler) {
		this.roomName = roomName;
		this.master = master;
		roomObserverList.put(roomName, handler);

	}

	private void notifyUserListChanged() {
		roomObserverList.values().forEach(c -> c.roomUserListChanged(roomName));
	}

	public synchronized void registerUser(String user, RoomEvents handler) {
		roomObserverList.put(user, handler);
		notifyUserListChanged();
	}

	public synchronized void unregisterUser(String user) {
		if (user.equals(master)) {
			master = null;
		}
		roomObserverList.remove(user);
		notifyUserListChanged();

	}

	public synchronized void chatMessage(String from, String message) {
		roomObserverList.values().forEach(c -> c.roomChatMessageSent(roomName, from, message));
	}

	public synchronized boolean canDelete(String name) {
		return name.equals(master);
	}

	public synchronized Collection<String> userList() {
		return roomObserverList.keySet();

	}

	public synchronized int userCount() {
		return roomObserverList.size();

	}

	public synchronized boolean hasUser(String user) {
		return roomObserverList.containsKey(user);

	}

	public synchronized void userRenamed(String name, String newName) {
		roomObserverList.remove(name);
		registerUser(newName, roomObserverList.get(name));
		notifyUserListChanged();
	}
}
