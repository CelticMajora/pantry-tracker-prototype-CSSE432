package api.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

	private LocalDate expirationDate;

	private IngredientPermissionLevel ingredientPermissionLevel;

	public Integer getId() {
		return this.id;
	}
	
	public void setID(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getExpirationDate() {
		return this.expirationDate;
	}
	
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public IngredientPermissionLevel getIngredientPermissionLevel() {
		return this.ingredientPermissionLevel;
	}
	
	public void setIngredientPermissionLevel(IngredientPermissionLevel ingredientPermissionLevel) {
		this.ingredientPermissionLevel = ingredientPermissionLevel;
	}

	public enum IngredientPermissionLevel {
		FREE_TO_SHARE, WILLING_TO_BARTER, NOT_UP_FOR_GRABS
	}

}
