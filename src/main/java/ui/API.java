package ui;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

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
		User user = restTemplate.getForObject(url, User.class);
		return user;
	}

	public User postUser(String name) {
		String url = String.format("%s/user?name=%s", this.baseUrl, name);
		User user = restTemplate.postForObject(url, null, User.class);
		return user;
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
		List<Ingredient> userIngredients = response.getBody();
		return userIngredients;
	}

	public List<Ingredient> getUserIngredientsExpiringSoon(int userId, String timezoneCode) {
		String url = String.format("%s/user/ingredient?userId=%d&timezoneCode=%s", this.baseUrl, userId, timezoneCode);
		ResponseEntity<List<Ingredient>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Ingredient>>() {
				});
		List<Ingredient> userIngredientsExpiringSoon = response.getBody();
		return userIngredientsExpiringSoon;
	}

	public Ingredient getIngredient(int id) {
		String url = String.format("%s/ingredient?id=%d", this.baseUrl, id);
		Ingredient ingredient = restTemplate.getForObject(url, Ingredient.class);
		return ingredient;
	}

	public Ingredient postIngredient(String name, int ownerId, int experationYear, int expirationMonth,
			int expirationDayOfMonth) {
		String url = String.format(
				"%s/ingredient?name=%s&ownerId=%d&expirationYear=%d&expirationMonth=%d&expirationDayOfMonth=%d",
				this.baseUrl, name, ownerId, experationYear, expirationMonth, expirationDayOfMonth);
		Ingredient ingredient = restTemplate.postForObject(url, null, Ingredient.class);
		return ingredient;
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
		List<User> friends = response.getBody();
		return friends;
	}

	public List<FriendsWith> getAllFriends() {
		String url = String.format("%s/friends/all", this.baseUrl);
		ResponseEntity<List<FriendsWith>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<FriendsWith>>() {
				});
		List<FriendsWith> friendsWithList = response.getBody();
		return friendsWithList;
	}

}