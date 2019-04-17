package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

	private List<Ingredient> ingredients;

	private List<User> friends;

	public User(String name, List<Ingredient> ingredients, List<User> friends) {
		this.name = name;
		this.ingredients = ingredients;
		this.friends = friends;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<Ingredient> getIngredients() {
		return this.ingredients;
	}

	public List<User> getFriends() {
		return this.friends;
	}

}
