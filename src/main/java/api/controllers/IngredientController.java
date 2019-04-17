package api.controllers;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import representations.Ingredient;

@RestController
public class IngredientController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/ingredient")
	public Ingredient ingredient(@RequestParam(value = "name", defaultValue = "defaultIngredient") String name,
			@RequestParam(value = "expirationYear", defaultValue = "0") String expirationYear,
			@RequestParam(value = "expirationMonth", defaultValue = "1") String expirationMonth,
			@RequestParam(value = "expirationDayOfMonth", defaultValue = "1") String expirationDayOfMonth) {
		return new Ingredient(counter.incrementAndGet(), name, LocalDate.of(Integer.parseInt(expirationYear),
				Integer.parseInt(expirationMonth), Integer.parseInt(expirationDayOfMonth)));
	}

}
