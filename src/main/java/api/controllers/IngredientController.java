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
	public Ingredient getIngredient(@RequestParam(value = "name") String name) {
		Iterator<Ingredient> iterator = ingredientRepository.findAll().iterator();
		while(iterator.hasNext()) {
			Ingredient next = iterator.next();
			if(next.getName().equals(name)) {
				return next;
			}
		}
		return null;
	}

	@RequestMapping(value = "/ingredient", method = RequestMethod.POST)
	public @ResponseBody String postIngredient(@RequestParam String name,
			@RequestParam(value = "expirationYear") String expirationYear,
			@RequestParam(value = "expirationMonth") String expirationMonth,
			@RequestParam(value = "expirationDayOfMonth") String expirationDayOfMonth) {
		Ingredient toStore = new Ingredient(name, LocalDate.of(Integer.parseInt(expirationYear),
				Integer.parseInt(expirationMonth), Integer.parseInt(expirationDayOfMonth)),
				IngredientPermissionLevel.NOT_UP_FOR_GRABS);
		ingredientRepository.save(toStore);
		return "Saved";
	}

	@RequestMapping(value = "/ingredient", method = RequestMethod.DELETE)
	public void deleteIngredient(@RequestParam(value = "name") String name) {
		if (name == null) {
			// TODO send 500
		}
		// TODO delete Ingredient and return 200
	}

}
