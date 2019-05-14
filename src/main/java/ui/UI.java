package ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import ui.models.FriendRequestFor;
import ui.models.Ingredient;
import ui.models.User;

public class UI {

	private API api;
	private Scanner scan;
	private Optional<User> user;

	public UI(API api) {
		this.api = api;
		this.scan = new Scanner(System.in);
		this.user = Optional.empty();
	}

	public void login() {
		System.out.println("Do you have an existing user?(y/n)");
		while (true) {
			String input = scan.nextLine();
			if (input.trim().equals("y")) {
				loginSpecificUser();
				System.out.println(String.format("User %s is logged in. Their id is %d.", this.user.get().getName(),
						this.user.get().getId()));
				break;
			} else if (input.trim().equals("n")) {
				this.createNewUser();
				System.out.println(String.format("User %s is logged in. Their id is %d.", this.user.get().getName(),
						this.user.get().getId()));
				break;
			} else {
				System.out.println("Please enter (y/n)");
			}
		}
	}

	public void displayLoggedInUserInformation() {
		if (this.user.isPresent()) {
			this.displayUserInformation(this.user.get());
		} else {
			System.err.println("No User Logged In");
			System.exit(1);
		}
	}

	private void displayUserInformation(User user) {
		List<Ingredient> userIngredients = this.api.getUserIngredients(user.getId());
		List<Ingredient> userIngredientsExpiringSoon = this.api.getUserIngredientsExpiringSoon(user.getId(),
				"UTC-05:00");// TODO make this an input option
		List<User> userFriends = this.api.getUsersFriends(user.getId());
		List<FriendRequestFor> friendRequestsReceived = this.api.getFriendRequestsReceived(user.getId());
		List<User> friendRequestsReceivedUsers = new LinkedList<User>();
		for (FriendRequestFor received : friendRequestsReceived) {
			friendRequestsReceivedUsers.add(api.getUser(received.getUserId()));
		}
		List<FriendRequestFor> friendRequestsSent = this.api.getFriendRequestsSent(user.getId());
		List<User> friendRequestsSentUsers = new LinkedList<User>();
		for (FriendRequestFor sent : friendRequestsSent) {
			friendRequestsSentUsers.add(api.getUser(sent.getFriendRequestedId()));
		}

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(String.format("User: %s", user.getName()));
		System.out.println("Ingredients Expiring Soon:");
		for (int i = 0; i < userIngredientsExpiringSoon.size(); i++) {
			System.out.println(String.format("%d:  %s  -  Expiring: %s  Permission Level: %s", i,
					userIngredientsExpiringSoon.get(i).getName(),
					userIngredientsExpiringSoon.get(i).getExpirationDate().toString(),
					userIngredientsExpiringSoon.get(i).getIngredientPermissionLevel()));
		}
		System.out.println("All Ingredients:");
		for (int i = 0; i < userIngredients.size(); i++) {
			System.out.println(String.format("%d:  %s  -  Expiring: %s  Permission Level: %s", i,
					userIngredients.get(i).getName(),
					userIngredients.get(i).getExpirationDate().toString(),
					userIngredients.get(i).getIngredientPermissionLevel()));
		}
		System.out.println("Friend List:");
		for(int i = 0; i < userFriends.size(); i++) {
			System.out.println(String.format("%d:  %s", i, userFriends.get(i).getName()));
		}
		System.out.println("Friend Requests Received:");
		for(int i = 0; i < friendRequestsReceivedUsers.size(); i++) {
			System.out.println(String.format("%d:  %s", i, friendRequestsReceivedUsers.get(i).getName()));
		}
		System.out.println("Friend Requests Sent:");
		for(int i = 0; i < friendRequestsSentUsers.size(); i++) {
			System.out.println(String.format("%d:  %s", i, friendRequestsSentUsers.get(i).getName()));
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	private void createNewUser() {
		System.out.println("Please enter a name for your user:");
		String name = scan.nextLine();
		this.user = Optional.of(this.api.postUser(name));
		System.out.println(String.format("REMEMBER THIS NUMBER: %d. IT IS YOUR ID TO LOGIN IN THE FUTURE",
				this.user.get().getId()));
	}

	private void loginSpecificUser() {
		System.out.println("Please enter your User id:");
		int id = scan.nextInt();
		this.user = Optional.of(this.api.getUser(id));
	}

}
