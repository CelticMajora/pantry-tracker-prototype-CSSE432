package api.controllers;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.representations.Ingredient;
import api.representations.User;

@RestController
public class UserController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/user")
	public User user(@RequestParam(value = "name", defaultValue = "defaultName") String name) {
		return new User(counter.incrementAndGet(), name, new LinkedList<Ingredient>());
	}

}
