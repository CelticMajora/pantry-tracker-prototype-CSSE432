package api.controllers;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import representations.Ingredient;
import representations.User;

@RestController
public class UserController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public User getUser(@RequestParam(value = "name", defaultValue = "defaultName") String name) {
		//TODO change param to id and return out of database
		return new User(counter.incrementAndGet(), name, new LinkedList<Ingredient>());
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public void postUser(@RequestParam(value = "name") String name) {
		if(name == null) {
			//TODO send 500
		}
		//TODO store new User and return 200
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public void deleteUser(@RequestParam(value = "name") String name) {
		if(name == null) {
			//TODO send 500
		}
		//TODO delete User and return 200
	}

}
