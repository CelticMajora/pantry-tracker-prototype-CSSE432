package ui;

import java.time.Month;
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
					userIngredients.get(i).getName(), userIngredients.get(i).getExpirationDate().toString(),
					userIngredients.get(i).getIngredientPermissionLevel()));
		}
		System.out.println("Friend List:");
		for (int i = 0; i < userFriends.size(); i++) {
			System.out.println(String.format("%d:  %s", i, userFriends.get(i).getName()));
		}
		System.out.println("Friend Requests Received:");
		for (int i = 0; i < friendRequestsReceivedUsers.size(); i++) {
			System.out.println(String.format("%d:  %s", i, friendRequestsReceivedUsers.get(i).getName()));
		}
		System.out.println("Friend Requests Sent:");
		for (int i = 0; i < friendRequestsSentUsers.size(); i++) {
			System.out.println(String.format("%d:  %s", i, friendRequestsSentUsers.get(i).getName()));
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	public void runCommands() {
		while (true) {
			System.out.println("Enter a command(type help to get a description of all commands)");
			String command = this.scan.nextLine();
			if (command.equals("save-ingredient")) {
				this.saveIngredient();
			} else if (command.equals("view-profile")) {
				this.displayLoggedInUserInformation();
			} else if (command.equals("view-friend-profile")) {
				this.viewFriendProfile();
			} else if (command.equals("request-friend")) {
				this.requestFriend();
			} else if (command.equals("accept-request")) {
				this.acceptRequest();
			} else if (command.equals("reject-request")) {
				this.rejectRequest();
			} else if (command.equals("delete-account")) {
				api.deleteUser(this.user.get().getId());
				this.user = Optional.empty();
				this.login();
			} else if (command.equals("delete-ingredient")) {
				this.deleteUserIngredient();
			} else if (command.equals("help")) {
				this.displayHelpScreen();
			} else if (command.equals("quit")) {
				break;
			} else if (command.equals("logout")) {
				this.user = Optional.empty();
				this.login();
			} else {
				System.out.println("Command not recognized. Type \"help\" to see a list of commands");
			}
		}
	}

	private void rejectRequest() {
		List<FriendRequestFor> received = api.getFriendRequestsReceived(this.user.get().getId());
		if (received.isEmpty()) {
			System.out.println("No friend requests");
			return;
		}

		List<User> receivedUsers = new LinkedList<User>();
		for (FriendRequestFor rec : received) {
			receivedUsers.add(api.getUser(rec.getUserId()));
		}

		System.out.println("Pending Friend Requests");
		for (int i = 0; i < receivedUsers.size(); i++) {
			System.out.println(String.format("%d:  %s", i, receivedUsers.get(i).getName()));
		}

		int requestIndex = 0;
		while (true) {
			System.out.println(String.format("Pick a request number to reject: 0-%d", receivedUsers.size() - 1));
			requestIndex = this.scan.nextInt();
			if(requestIndex >= 0 && requestIndex < receivedUsers.size()) {
				break;
			}
			System.out.println("The number you chose was invalid");
		}
		
		for(FriendRequestFor rec : received) {
			if(rec.getUserId() == receivedUsers.get(requestIndex).getId()) {
				api.rejectFriendRequest(rec.getId());
				break;
			}
		}
	}

	private void acceptRequest() {
		List<FriendRequestFor> received = api.getFriendRequestsReceived(this.user.get().getId());
		if (received.isEmpty()) {
			System.out.println("No friend requests");
			return;
		}

		List<User> receivedUsers = new LinkedList<User>();
		for (FriendRequestFor rec : received) {
			receivedUsers.add(api.getUser(rec.getUserId()));
		}

		System.out.println("Pending Friend Requests");
		for (int i = 0; i < receivedUsers.size(); i++) {
			System.out.println(String.format("%d:  %s", i, receivedUsers.get(i).getName()));
		}

		int requestIndex = 0;
		while (true) {
			System.out.println(String.format("Pick a request number to accept: 0-%d", receivedUsers.size() - 1));
			requestIndex = this.scan.nextInt();
			if(requestIndex >= 0 && requestIndex < receivedUsers.size()) {
				break;
			}
			System.out.println("The number you chose was invalid");
		}
		
		for(FriendRequestFor rec : received) {
			if(rec.getUserId() == receivedUsers.get(requestIndex).getId()) {
				api.acceptFriendRequest(rec.getId());
				break;
			}
		}
	}

	private void requestFriend() {
		List<User> allUsers = api.getAllUsers();
		List<User> usersFriends = api.getUsersFriends(this.user.get().getId());
		List<FriendRequestFor> requestsSent = api.getFriendRequestsSent(this.user.get().getId());
		List<FriendRequestFor> requestsReceived = api.getFriendRequestsReceived(this.user.get().getId());

		List<User> validUsers = new LinkedList<User>();
		for (User possibleUser : allUsers) {
			boolean shouldAdd = true;
			for (User currentFriend : usersFriends) {
				if (possibleUser.getId() == currentFriend.getId()) {
					shouldAdd = false;
				}
			}
			for (FriendRequestFor sent : requestsSent) {
				if (possibleUser.getId() == sent.getUserId()) {
					shouldAdd = false;
				}
			}
			for (FriendRequestFor received : requestsReceived) {
				if (possibleUser.getId() == received.getFriendRequestedId()) {
					shouldAdd = false;
				}
			}
			if (shouldAdd) {
				validUsers.add(possibleUser);
			}
		}

		if (validUsers.isEmpty()) {
			System.out.println("No friend options");
			return;
		}

		System.out.println("Possible Friend Options");
		for (int i = 0; i < validUsers.size(); i++) {
			System.out.println(String.format("%d:  %s", i, validUsers.get(i).getName()));
		}

		int requestIndex;
		while (true) {
			System.out.println(String.format("Request a friend by number: 0-%d", validUsers.size() - 1));
			requestIndex = this.scan.nextInt();
			if (requestIndex >= 0 && requestIndex < validUsers.size()) {
				break;
			}
			System.out.println("The number you chose was invalid");
		}

		api.postFriendRequest(this.user.get().getId(), validUsers.get(requestIndex).getId());
	}

	private void deleteUserIngredient() {
		List<Ingredient> userIngredients = this.api.getUserIngredients(this.user.get().getId());
		System.out.println("All Ingredients:");
		for (int i = 0; i < userIngredients.size(); i++) {
			System.out.println(String.format("%d:  %s  -  Expiring: %s  Permission Level: %s", i,
					userIngredients.get(i).getName(), userIngredients.get(i).getExpirationDate().toString(),
					userIngredients.get(i).getIngredientPermissionLevel()));
		}
		int ingredientIndex = 0;
		while (true) {
			System.out.println(
					String.format("Please choose an ingredient number to delete: 0-%d", userIngredients.size() - 1));
			ingredientIndex = this.scan.nextInt();
			if (ingredientIndex >= 0 && ingredientIndex < userIngredients.size()) {
				break;
			}
			System.out.println("The number you chose was invalid");
		}

		api.deleteIngredient(userIngredients.get(ingredientIndex).getId());
	}

	private void saveIngredient() {
		System.out.println("Save a New Ingredient");
		System.out.println("Enter an ingredient name:");
		String name = this.scan.nextLine();
		int month = 1;
		int dayOfMonth = 1;
		System.out.println("Please enter a expiration year");
		int year = this.scan.nextInt();
		while (true) {
			System.out.println("Please enter a month between 1 and 12");
			month = this.scan.nextInt();
			if (month > 0 && month < 13) {
				break;
			}
			System.out.println("Your input month is invalid.");
		}
		int numDays = Month.of(month).maxLength();
		while (true) {
			System.out.println(String.format("Please enter a day of month between 1 and %d", numDays));
			dayOfMonth = this.scan.nextInt();
			if (month > 0 && month <= numDays) {
				break;
			}
			System.out.println("Your input day of month is invalid.");
		}
		this.api.postIngredient(name, this.user.get().getId(), year, month, dayOfMonth);
	}

	private void viewFriendProfile() {
		List<User> userFriends = this.api.getUsersFriends(this.user.get().getId());
		if (userFriends.isEmpty()) {
			System.out.println("You have not found any friends yet.");
			return;
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Friend List:");
		for (int i = 0; i < userFriends.size(); i++) {
			System.out.println(String.format("%d:  %s", i, userFriends.get(i).getName()));
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		int friendIndex = 0;
		while (true) {
			System.out.println(String.format("Please choose a friend number to view: 0-%d", userFriends.size() - 1));
			friendIndex = this.scan.nextInt();
			if (friendIndex < 0 || friendIndex >= userFriends.size()) {
				System.out.println("The chosen number was invalid");
			} else {
				break;
			}
		}

		this.displayUserInformation(userFriends.get(friendIndex));
	}

	private void displayHelpScreen() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Help Table");
		System.out.println("save-ingredient   -   Save a new ingredient");
		System.out.println("request-friend   -   Create a new friend request");
		System.out.println("accept-request   -   Accept a pending friend request");
		System.out.println("reject-request   -   Reject a pending friend request");
		System.out.println("delete-account   -   Delete your account");
		System.out.println("delete-ingredient   -   Delete a specific ingredient");
		System.out.println("quit   -   Close out of the program");
		System.out.println("view-profile   -   View your profile");
		System.out.println("view-friend-profile   -   View a friend's profile");
		System.out.println("logout   -   Logout of your profile");
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
