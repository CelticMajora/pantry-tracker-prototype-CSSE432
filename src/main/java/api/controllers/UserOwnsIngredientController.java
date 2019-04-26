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

import api.entities.FriendsWith;
import api.entities.Ingredient;
import api.repositories.FriendsWithRepository;
import api.repositories.IngredientRepository;

@CrossOrigin(origins = "http://127.0.0.1:8081")
@RestController
public class UserOwnsIngredientController {

	@Autowired
	private IngredientRepository ingredientRepository;

	@RequestMapping(value = "/ownership", method = RequestMethod.GET)
	public @ResponseBody List<Ingredient> getOwnership(@RequestParam String id) {
		List<Ingredient> ownership = new LinkedList<Ingredient>();
		Iterator<Ingredient> iterator = ingredientRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Ingredient next = iterator.next();
			if (next.getOwnerId().equals(Integer.parseInt(id))) {
				ownership.add(next);
			}
		}
		return ownership;
	}

	@RequestMapping(value = "/ownership/all", method = RequestMethod.GET)
	public @ResponseBody Iterable<Ingredient> getAllOwnership() {
		return ingredientRepository.findAll();
	}
	
}
