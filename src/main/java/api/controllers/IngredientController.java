package api.controllers;

import java.time.LocalDate;
import java.util.Iterator;

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

	@RequestMapping(value = "/ingredient", method = RequestMethod.POST)
	public @ResponseBody String postIngredient(@RequestParam String name, @RequestParam String expirationYear,
			@RequestParam String expirationMonth, @RequestParam String expirationDayOfMonth) {
		Ingredient toStore = new Ingredient();
		toStore.setName(name);
		toStore.setExpirationDate(LocalDate.of(Integer.parseInt(expirationYear), Integer.parseInt(expirationMonth),
				Integer.parseInt(expirationDayOfMonth)));
		toStore.setIngredientPermissionLevel(IngredientPermissionLevel.NOT_UP_FOR_GRABS);
		ingredientRepository.save(toStore);
		return "Saved";
	}

	@RequestMapping(value = "/ingredient", method = RequestMethod.DELETE)
	public void deleteIngredient(@RequestParam String name) {
		// TODO delete Ingredient and return 200
	}

}
