package ui.models;

import java.io.Serializable;

public class FriendsWith implements Serializable {
	
	private int id;
	
	private int userId;
	
	private int friendId;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public void setUserId(int id) {
		this.id = id;
	}
	
	public int getFriendId() {
		return this.friendId;
	}
	
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

}
