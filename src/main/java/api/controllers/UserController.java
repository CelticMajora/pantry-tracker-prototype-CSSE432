package api.controllers;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.FriendRequestFor;
import api.entities.FriendsWith;
import api.entities.Ingredient;
import api.entities.User;
import api.repositories.FriendRequestForRepository;
import api.repositories.FriendsWithRepository;
import api.repositories.IngredientRepository;
import api.repositories.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FriendsWithRepository friendsRepository;

	@Autowired
	private FriendRequestForRepository friendRequestRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public @ResponseBody User getUser(@RequestParam String id) {
		Optional<User> user = userRepository.findById(Integer.parseInt(id));
		if(user.isPresent()) {
			return user.get();
		}
		throw new RuntimeException(String.format("Unable to find user with id: %s", id));
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody User postUser(@RequestParam String name) {
		User toStore = new User();
		toStore.setName(name);
		userRepository.save(toStore);
		return toStore;
	}

	@RequestMapping(value = "/user/all", method = RequestMethod.GET)
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public void deleteUser(@RequestParam String id) {
		Iterator<FriendsWith> friendsIterator = friendsRepository.findAll().iterator();
		while (friendsIterator.hasNext()) {
			FriendsWith next = friendsIterator.next();
			if (next.getUserId().equals(Integer.parseInt(id)) || next.getFriendId().equals(Integer.parseInt(id))) {
				friendsRepository.delete(next);
			}
		}

		Iterator<Ingredient> ingredientsIterator = ingredientRepository.findAll().iterator();
		while (ingredientsIterator.hasNext()) {
			Ingredient next = ingredientsIterator.next();
			if (next.getOwnerId().equals(Integer.parseInt(id))) {
				ingredientRepository.delete(next);
			}
		}

		Iterator<FriendRequestFor> friendRequestsIterator = friendRequestRepository.findAll().iterator();
		while (friendRequestsIterator.hasNext()) {
			FriendRequestFor next = friendRequestsIterator.next();
			if (next.getUserId().equals(Integer.parseInt(id))
					|| next.getFriendRequestedId().equals(Integer.parseInt(id))) {
				friendRequestRepository.delete(next);
			}
		}

		userRepository.deleteById(Integer.parseInt(id));
	}

}
