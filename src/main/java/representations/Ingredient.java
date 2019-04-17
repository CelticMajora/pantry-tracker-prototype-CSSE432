package representations;

import java.time.LocalDate;

public class Ingredient {

	private final long id;
	private final String name;
	private final LocalDate expirationDate;
	private final IngredientPermissionLevel ingredientPermissionLevel;
	
	public Ingredient(long id, String name, LocalDate expirationDate, IngredientPermissionLevel ingredientPermissionLevel){
		this.id = id;
		this.name = name;
		this.expirationDate = expirationDate;
		this.ingredientPermissionLevel = ingredientPermissionLevel;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public LocalDate getExpirationDate() {
		return this.expirationDate;
	}
	
	public IngredientPermissionLevel getIngredientPermissionLevel() {
		return this.ingredientPermissionLevel;
	}

	public enum IngredientPermissionLevel {
		FREE_TO_SHARE, WILLING_TO_BARTER, NOT_UP_FOR_GRABS
	}

}
