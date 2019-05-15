package ui.models;

import java.io.Serializable;

public class FriendRequestFor implements Serializable {
	
	private int id;
	
	private int userId;
	
	private int friendRequestedId;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getFriendRequestedId() {
		return this.friendRequestedId;
	}
	
	public void setFriendRequestedId(int friendRequestedId) {
		this.friendRequestedId = friendRequestedId;
	}

}
