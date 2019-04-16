package api.representations;

import java.time.LocalDate;

public class Ingredient {
	
	private final long id;
	private final String name;
	private final LocalDate expirationDate;
	
	public Ingredient(long id, String name, LocalDate expirationDate){
		this.id = id;
		this.name = name;
		this.expirationDate = expirationDate;
	}
	
	public long getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public LocalDate getExpirationDate(){
		return this.expirationDate;
	}

}
