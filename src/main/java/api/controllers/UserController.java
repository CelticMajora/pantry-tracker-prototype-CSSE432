package api.controllers;

import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.Ingredient;
import api.entities.User;
import api.repositories.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public @ResponseBody User getUser(@RequestParam String name) {
		Iterator<User> iterator = userRepository.findAll().iterator();
		while(iterator.hasNext()) {
			User next = iterator.next();
			if(next.getName().equals(name)) {
				return next;
			}
		}
		return null;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody String postUser(@RequestParam String name) {
		User toStore = new User(name);
		userRepository.save(toStore);
		return "Saved";
	}

	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public void deleteUser(@RequestParam(value = "name") String name) {
		if (name == null) {
			// TODO send 500
		}
		// TODO delete User and return 200
	}

}
