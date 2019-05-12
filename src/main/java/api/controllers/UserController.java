package api.controllers;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.User;
import api.repositories.UserRepository;

@CrossOrigin(origins = "127.0.0.1:8081/pantry.html")
@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;

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
