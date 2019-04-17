package api.controllers;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import representations.Ingredient;

@RestController
public class IngredientController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(value = "/ingredient", method = RequestMethod.GET)
	public Ingredient getIngredient(@RequestParam(value = "name", defaultValue = "defaultIngredient") String name,
			@RequestParam(value = "expirationYear", defaultValue = "0") String expirationYear,
			@RequestParam(value = "expirationMonth", defaultValue = "1") String expirationMonth,
			@RequestParam(value = "expirationDayOfMonth", defaultValue = "1") String expirationDayOfMonth) {
		return new Ingredient(counter.incrementAndGet(), name, LocalDate.of(Integer.parseInt(expirationYear),
				Integer.parseInt(expirationMonth), Integer.parseInt(expirationDayOfMonth)));
	}
	
	@RequestMapping(value = "/ingredient", method = RequestMethod.POST)
	public void postIngredient(@RequestParam(value = "name") String name) {
		if(name == null) {
			//TODO send 500
		}
		//TODO store new Ingredient and return 200
	}
	
	@RequestMapping(value = "/ingredient", method = RequestMethod.DELETE)
	public void deleteIngredient(@RequestParam(value = "name") String name) {
		if(name == null) {
			//TODO send 500
		}
		//TODO delete Ingredient and return 200
	}

}
