package api.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

import api.entities.Ingredient;
import api.entities.Ingredient.IngredientPermissionLevel;
import api.repositories.IngredientRepository;

@CrossOrigin(origins = "http://127.0.0.1:8081")
@RestController
public class IngredientController {

	@Autowired
	private IngredientRepository ingredientRepository;

	@RequestMapping(value = "/user/ingredient", method = RequestMethod.GET)
	public @ResponseBody List<Ingredient> getUsersIngredient(@RequestParam String userId) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Iterator<Ingredient> iterator = ingredientRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Ingredient next = iterator.next();
			if (next.getOwnerId().equals(Integer.parseInt(userId))) {
				ingredients.add(next);
			}
		}
		return ingredients;
	}

	@RequestMapping(value = "/user/ingredient/expiring_soon", method = RequestMethod.GET)
	public @ResponseBody List<Ingredient> getUsersExpiringIngredients(@RequestParam String userId,
			@RequestParam String timezoneCode) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Iterator<Ingredient> iterator = ingredientRepository.findAll().iterator();
		while (iterator.hasNext()) {
			Ingredient next = iterator.next();
			if (next.getOwnerId().equals(Integer.parseInt(userId))) {
				LocalDate now = LocalDate.now(ZoneId.of(timezoneCode));
				if(now.until(next.getExpirationDate(), ChronoUnit.DAYS) < 4) {
					ingredients.add(next);
				}				
			}
		}
		return ingredients;
	}

	@RequestMapping(value = "/ingredient", method = RequestMethod.GET)
	public @ResponseBody Ingredient getIngredient(@RequestParam String id) {
		Optional<Ingredient> ingredient = ingredientRepository.findById(Integer.parseInt(id));
		if (ingredient.isPresent()) {
			return ingredient.get();
		}
		throw new RuntimeException(String.format("Unable to find ingredient with id: %s", id));
	}

	@RequestMapping(value = "/ingredient/all", method = RequestMethod.GET)
	public @ResponseBody Iterable<Ingredient> getAllIngredients() {
		return ingredientRepository.findAll();
	}

	@RequestMapping(value = "/ingredient", method = RequestMethod.POST)
	public @ResponseBody Ingredient postIngredient(@RequestParam String name, @RequestParam String ownerId,
			@RequestParam String expirationYear, @RequestParam String expirationMonth,
			@RequestParam String expirationDayOfMonth) {
		Ingredient toStore = new Ingredient();
		toStore.setName(name);
		toStore.setOwnerId(Integer.valueOf(ownerId));
		toStore.setExpirationDate(LocalDate.of(Integer.parseInt(expirationYear), Integer.parseInt(expirationMonth),
				Integer.parseInt(expirationDayOfMonth)));
		toStore.setIngredientPermissionLevel(IngredientPermissionLevel.NOT_UP_FOR_GRABS);
		ingredientRepository.save(toStore);
		return toStore;
	}

	@RequestMapping(value = "/ingredient", method = RequestMethod.DELETE)
	public void deleteIngredient(@RequestParam String id) {
		ingredientRepository.deleteById(Integer.parseInt(id));
	}

}
