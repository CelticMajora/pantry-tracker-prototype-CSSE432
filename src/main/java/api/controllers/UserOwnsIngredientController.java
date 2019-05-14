package api.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.UserOwnsIngredient;
import api.repositories.UserOwnsIngredientRepository;

@CrossOrigin(origins = "http://127.0.0.1:8081")
@RestController
public class UserOwnsIngredientController {

	@Autowired
	private UserOwnsIngredientRepository userOwnsIngredientRepository;

	@RequestMapping(value = "/ownership", method = RequestMethod.GET)
	public @ResponseBody List<UserOwnsIngredient> getOwnership(@RequestParam String id) {
		List<UserOwnsIngredient> ownership = new LinkedList<UserOwnsIngredient>();
		Iterator<UserOwnsIngredient> iterator = userOwnsIngredientRepository.findAll().iterator();
		while (iterator.hasNext()) {
			UserOwnsIngredient next = iterator.next();
			if (next.getUserId().equals(Integer.parseInt(id))) {
				ownership.add(next);
			}
		}
		return ownership;
	}

	@RequestMapping(value = "/ownership/all", method = RequestMethod.GET)
	public @ResponseBody Iterable<UserOwnsIngredient> getAllOwnership() {
		return userOwnsIngredientRepository.findAll();
	}
	
	@RequestMapping(value = "/ownership", method = RequestMethod.POST)
	public @ResponseBody String postOwnership(@RequestParam String userId, @RequestParam String ingredientId) {
		UserOwnsIngredient toStore = new UserOwnsIngredient();
		toStore.setUserId(Integer.parseInt(userId));
		toStore.setIngredientId(Integer.parseInt(ingredientId));
		userOwnsIngredientRepository.save(toStore);
		return "Saved";
	}
}
