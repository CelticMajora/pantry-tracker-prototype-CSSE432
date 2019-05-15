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

import api.entities.FriendRequestFor;
import api.entities.FriendsWith;
import api.entities.User;
import api.repositories.FriendRequestForRepository;
import api.repositories.FriendsWithRepository;
import api.repositories.UserRepository;

@CrossOrigin(origins = "http://127.0.0.1:8081")
@RestController
public class FriendRequestForController {

	@Autowired
	private FriendRequestForRepository friendRequestForRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FriendsWithRepository friendsWithRepository;

	@RequestMapping(value = "/friend_requests/all", method = RequestMethod.GET)
	public @ResponseBody List<FriendRequestFor> getAllFriendRequests() {
		List<FriendRequestFor> friendRequests = new ArrayList<FriendRequestFor>();
		Iterator<FriendRequestFor> iterator = friendRequestForRepository.findAll().iterator();
		while (iterator.hasNext()) {
			friendRequests.add(iterator.next());
		}
		return friendRequests;
	}

	@RequestMapping(value = "/friend_requests/received", method = RequestMethod.GET)
	public @ResponseBody List<FriendRequestFor> getFriendRequestsReceived(@RequestParam String userId) {
		List<FriendRequestFor> friendRequests = new ArrayList<FriendRequestFor>();
		Iterator<FriendRequestFor> iterator = friendRequestForRepository.findAll().iterator();
		while (iterator.hasNext()) {
			FriendRequestFor next = iterator.next();
			if (next.getFriendRequestedId().equals(Integer.parseInt(userId))) {
				friendRequests.add(next);
			}
		}
		return friendRequests;
	}

	@RequestMapping(value = "/friend_requests/sent", method = RequestMethod.GET)
	public @ResponseBody List<FriendRequestFor> getFriendRequestsSent(@RequestParam String userId) {
		List<FriendRequestFor> friendRequests = new ArrayList<FriendRequestFor>();
		Iterator<FriendRequestFor> iterator = friendRequestForRepository.findAll().iterator();
		while (iterator.hasNext()) {
			FriendRequestFor next = iterator.next();
			if (next.getUserId().equals(Integer.parseInt(userId))) {
				friendRequests.add(next);
			}
		}
		return friendRequests;
	}

	@RequestMapping(value = "/friend_requests", method = RequestMethod.POST)
	public @ResponseBody FriendRequestFor postFriendRequest(@RequestParam String userId,
			@RequestParam String friendId) {
		Optional<User> user = userRepository.findById(Integer.parseInt(userId));
		Optional<User> friend = userRepository.findById(Integer.parseInt(friendId));
		if (user.isPresent() && friend.isPresent()) {
			Iterator<FriendRequestFor> iterator = friendRequestForRepository.findAll().iterator();
			boolean foundMatch = false;
			while (iterator.hasNext()) {
				FriendRequestFor next = iterator.next();
				if ((next.getUserId().equals(Integer.parseInt(userId))
						&& next.getFriendRequestedId().equals(Integer.parseInt(friendId)))
						|| (next.getUserId().equals(Integer.parseInt(friendId))
								&& next.getFriendRequestedId().equals(Integer.parseInt(userId)))) {
					foundMatch = true;
				}
			}
			if(!foundMatch) {
				FriendRequestFor friendRequest = new FriendRequestFor();
				friendRequest.setUserId(Integer.parseInt(userId));
				friendRequest.setFriendRequestedId(Integer.parseInt(friendId));
				friendRequestForRepository.save(friendRequest);
				return friendRequest;
			}
			throw new RuntimeException("Friend Request already exists.");
		}
		throw new RuntimeException(
				String.format("Unable to find user with id: %s or friend with id: %s", userId, friendId));
	}

	@RequestMapping(value = "/friend_requests/accept", method = RequestMethod.POST)
	public @ResponseBody String acceptFriendRequest(@RequestParam String friendRequestId) {
		Optional<FriendRequestFor> friendRequest = friendRequestForRepository
				.findById(Integer.parseInt(friendRequestId));
		if (friendRequest.isPresent()) {
			FriendsWith friendTo = new FriendsWith();
			friendTo.setUserId(friendRequest.get().getUserId());
			friendTo.setFriendId(friendRequest.get().getFriendRequestedId());

			FriendsWith friendFrom = new FriendsWith();
			friendFrom.setUserId(friendRequest.get().getFriendRequestedId());
			friendFrom.setFriendId(friendRequest.get().getUserId());

			friendsWithRepository.save(friendTo);
			friendsWithRepository.save(friendFrom);

			friendRequestForRepository.delete(friendRequest.get());
			return "Saved";
		}
		throw new RuntimeException(String.format("Unable to find friend request with id: %s", friendRequestId));
	}
	
	@RequestMapping(value = "/friend_requests/reject", method = RequestMethod.POST)
	public @ResponseBody String rejectFriendRequest(@RequestParam String friendRequestId) {
		Optional<FriendRequestFor> friendRequest = friendRequestForRepository
				.findById(Integer.parseInt(friendRequestId));
		if (friendRequest.isPresent()) {
			friendRequestForRepository.delete(friendRequest.get());
			return "Saved";
		}
		throw new RuntimeException(String.format("Unable to find friend request with id: %s", friendRequestId));
	}
}
