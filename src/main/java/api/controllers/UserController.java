package api.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.FriendsWith;
import api.entities.User;
import api.repositories.FriendsWithRepository;
import api.repositories.UserRepository;

@CrossOrigin(origins = "http://127.0.0.1:8081")
@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FriendsWithRepository friendsRepository;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public @ResponseBody User getUser(@RequestParam String id) {
		System.out.println(id);
		Iterator<User> iterator = userRepository.findAll().iterator();
		while(iterator.hasNext()) {
			User next = iterator.next();
			if(next.getId().equals(Integer.parseInt(id))) {
				System.out.println(next);
				return next;
			}
		}
		throw new RuntimeException(String.format("Unable to find user with id: %s", id));
	}
	@RequestMapping(value = "/user/friends", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersFriends(@RequestParam String id) {
		ArrayList<User> friends = new ArrayList<User>(); 
		Iterator<FriendsWith> iterator = friendsRepository.findAll().iterator();
		while(iterator.hasNext()) {
			FriendsWith next = iterator.next();
			if(next.getUserId().equals(Integer.parseInt(id))) {
				Optional<User> ans = userRepository.findById(next.getFriendId());
				if(ans.isPresent()){
					friends.add(ans.get());					
				}
			}
		}
		return friends;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody String postUser(@RequestParam String name) {
		User toStore = new User();
		toStore.setName(name);
		userRepository.save(toStore);
		return "Saved";
	}
	
	@RequestMapping(value = "/user/all", method = RequestMethod.GET)
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public void deleteUser(@RequestParam String name) {
		// TODO delete User and return 200
	}

}
