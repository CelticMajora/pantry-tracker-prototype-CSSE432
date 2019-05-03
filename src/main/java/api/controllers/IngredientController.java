package api.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.entities.Ingredient;
import api.entities.Ingredient.IngredientPermissionLevel;
import api.repositories.IngredientRepository;

@RestController
public class IngredientController {

	@Autowired
	private IngredientRepository ingredientRepository;

	
	@RequestMapping(value = "/user/ingredient", method = RequestMethod.GET)
	public @ResponseBody List<Ingredient> getUsersIngredient(@RequestParam String userId) {
		ArrayList<Ingredient> ans = new ArrayList<Ingredient>();
		Iterator<Ingredient> iterator = ingredientRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Ingredient next = iterator.next();
			if (next.getOwnerId().equals(Integer.parseInt(userId))) {
				ans.add(next);
			}
		}
		return ans;
	}
	
	@RequestMapping(value = "/ingredient", method = RequestMethod.GET)
	public @ResponseBody Ingredient getIngredient(@RequestParam String id) {
		Iterator<Ingredient> iterator = ingredientRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Ingredient next = iterator.next();
			if (next.getId().equals(Integer.parseInt(id))) {
				return next;
			}
		}
		throw new RuntimeException(String.format("Unable to find ingredient with id: %s", id));
	}
	
	@RequestMapping(value = "/ingredient/all", method = RequestMethod.GET)
	public @ResponseBody Iterable<Ingredient> getAllIngredients(){
		return ingredientRepository.findAll();
	}

	@RequestMapping(value = "/user/ingredient", method = RequestMethod.POST)
	public @ResponseBody String postIngredient(@RequestParam String name, @RequestParam String ownerId, @RequestParam String expirationYear,
			@RequestParam String expirationMonth, @RequestParam String expirationDayOfMonth) {
		Ingredient toStore = new Ingredient();
		toStore.setName(name);
		toStore.setOwnerId(Integer.valueOf(ownerId));
		toStore.setExpirationDate(LocalDate.of(Integer.parseInt(expirationYear), Integer.parseInt(expirationMonth),
				Integer.parseInt(expirationDayOfMonth)));
		toStore.setIngredientPermissionLevel(IngredientPermissionLevel.NOT_UP_FOR_GRABS);
		ingredientRepository.save(toStore);
		return "Added";
	}

	@RequestMapping(value = "/user/ingredient", method = RequestMethod.DELETE)
	public void deleteIngredientByUser(@RequestParam String userId, @RequestParam String id) {
		ingredientRepository.deleteById(Integer.parseInt(id));
	}
	
	@RequestMapping(value = "/ingredient", method = RequestMethod.DELETE)
	public void deleteIngredient(@RequestParam String id) {
		ingredientRepository.deleteById(Integer.parseInt(id));
	}

}
