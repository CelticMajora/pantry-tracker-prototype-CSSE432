package ui;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

import ui.models.FriendRequestFor;
import ui.models.FriendsWith;
import ui.models.Ingredient;
import ui.models.User;

public class API {

	private RestTemplate restTemplate;

	private String baseUrl;

	public API(String ip, String port) {
		this.baseUrl = "http://" + ip + ":" + port;
		this.restTemplate = new RestTemplate();
	}

	public User getUser(int id) {
		String url = String.format("%s/user?id=%d", this.baseUrl, id);
		return restTemplate.getForObject(url, User.class);
	}

	public User postUser(String name) {
		String url = String.format("%s/user?name=%s", this.baseUrl, name);
		return restTemplate.postForObject(url, null, User.class);
	}

	public List<User> getAllUsers() {
		String url = String.format("%s/user/all", this.baseUrl);
		ResponseEntity<Iterable<User>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<Iterable<User>>() {
				});
		Iterable<User> users = response.getBody();
		return Lists.newLinkedList(users);
	}

	public void deleteUser(int id) {
		String url = String.format("%s/user?id=%d", this.baseUrl, id);
		restTemplate.delete(url);
	}

	public List<Ingredient> getUserIngredients(int userId) {
		String url = String.format("%s/user/ingredient?userId=%d", this.baseUrl, userId);
		ResponseEntity<List<Ingredient>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Ingredient>>() {
				});
		return response.getBody();
	}

	public List<Ingredient> getUserIngredientsExpiringSoon(int userId, String timezoneCode) {
		String url = String.format("%s/user/ingredient?userId=%d&timezoneCode=%s", this.baseUrl, userId, timezoneCode);
		ResponseEntity<List<Ingredient>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Ingredient>>() {
				});
		return response.getBody();
	}

	public Ingredient getIngredient(int id) {
		String url = String.format("%s/ingredient?id=%d", this.baseUrl, id);
		return restTemplate.getForObject(url, Ingredient.class);
	}

	public Ingredient postIngredient(String name, int ownerId, int experationYear, int expirationMonth,
			int expirationDayOfMonth) {
		String url = String.format(
				"%s/ingredient?name=%s&ownerId=%d&expirationYear=%d&expirationMonth=%d&expirationDayOfMonth=%d",
				this.baseUrl, name, ownerId, experationYear, expirationMonth, expirationDayOfMonth);
		return restTemplate.postForObject(url, null, Ingredient.class);
	}

	public void deleteIngredient(int id) {
		String url = String.format("%s/ingredient?id=%d", this.baseUrl, id);
		restTemplate.delete(url);
	}

	public List<User> getUsersFriends(int userId) {
		String url = String.format("%s/friends?userId=%d", this.baseUrl, userId);
		ResponseEntity<List<User>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				});
		return response.getBody();
	}

	public List<FriendsWith> getAllFriends() {
		String url = String.format("%s/friends/all", this.baseUrl);
		ResponseEntity<List<FriendsWith>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<FriendsWith>>() {
				});
		return response.getBody();
	}

	public List<FriendRequestFor> getAllFriendRequests() {
		String url = String.format("%s/friend_requests/all", this.baseUrl);
		ResponseEntity<List<FriendRequestFor>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<FriendRequestFor>>() {
				});
		return response.getBody();
	}

	public List<FriendRequestFor> getFriendRequestsReceived(int userId) {
		String url = String.format("%s/friend_requests/received?userId=%d", this.baseUrl, userId);
		ResponseEntity<List<FriendRequestFor>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<FriendRequestFor>>() {
				});
		return response.getBody();
	}
	
	public List<FriendRequestFor> getFriendRequestsSent(int userId) {
		String url = String.format("%s/friend_requests/sent?userId=%d", this.baseUrl, userId);
		ResponseEntity<List<FriendRequestFor>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<FriendRequestFor>>() {
				});
		return response.getBody();
	}
	
	public FriendRequestFor postFriendRequest(int userId, int friendId) {
		String url = String.format("%s/friend_requests?userId=%d&friendId=%d", this.baseUrl, userId, friendId);
		return restTemplate.postForObject(url, null, FriendRequestFor.class);
	}
	
	public void acceptFriendRequest(int friendRequestId) {
		String url = String.format("%s/friend_requests/accept?friendRequestId=%d", friendRequestId);
		restTemplate.postForObject(url, null, String.class);
	}
	
	public void rejectFriendRequest(int friendRequestId) {
		String url = String.format("%s/friend_requests/reject?friendRequestId=%d", friendRequestId);
		restTemplate.postForObject(url, null, String.class);
	}

}
