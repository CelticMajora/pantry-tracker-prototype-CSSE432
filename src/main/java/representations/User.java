package representations;

import java.util.List;

public class User {

	private final long id;
	private final String name;
	private final List<Ingredient> ingredients;
	private final List<User> friends;

	public User(long id, String name, List<Ingredient> ingredients, List<User> friends) {
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
		this.friends = friends;
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
	
	public List<User> getFriends(){
		return this.friends;
	}

}
