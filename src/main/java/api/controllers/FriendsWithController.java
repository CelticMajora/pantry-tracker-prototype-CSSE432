package api.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.FriendsWith;
import api.entities.User;
import api.repositories.FriendsWithRepository;
import api.repositories.UserRepository;

@RestController
public class FriendsWithController {
	
	@Autowired
	private FriendsWithRepository friendsWithRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersFriends(@RequestParam String userId) {
		List<User> friends = new ArrayList<User>();
		Iterator<FriendsWith> iterator = friendsWithRepository.findAll().iterator();
		while (iterator.hasNext()) {
			FriendsWith next = iterator.next();
			if (next.getUserId().equals(Integer.parseInt(userId))) {
				Optional<User> ans = userRepository.findById(next.getFriendId());
				if (ans.isPresent()) {
					friends.add(ans.get());
				}
			}
		}
		return friends;
	}

	@RequestMapping(value = "/friends/all", method = RequestMethod.GET)
	public @ResponseBody List<FriendsWith> getAllFriends() {
		List<FriendsWith> friends = new ArrayList<FriendsWith>();
		Iterator<FriendsWith> iterator = friendsWithRepository.findAll().iterator();
		while (iterator.hasNext()) {
			friends.add(iterator.next());
		}
		return friends;
	}
}
