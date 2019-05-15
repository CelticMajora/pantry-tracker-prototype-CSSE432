package ui.models;

import java.io.Serializable;
import java.time.LocalDate;

import api.entities.Ingredient.IngredientPermissionLevel;

public class Ingredient implements Serializable {
	
	private int id;
	
	private String name;
	
	private LocalDate expirationDate;
	
	private IngredientPermissionLevel ingredientPermissionLevel;
	
	private int ownerId;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
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
	
	public int getOwnerId() {
		return this.ownerId;
	}
	
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
