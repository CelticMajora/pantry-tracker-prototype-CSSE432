package api.representations;

import java.util.List;

public class User {

	private final long id;
	private final String name;
	private final List<Ingredient> ingredients;

	public User(long id, String name, List<Ingredient> ingredients) {
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public List<Ingredient> getIngredients() {
		return this.ingredients;
	}

}
