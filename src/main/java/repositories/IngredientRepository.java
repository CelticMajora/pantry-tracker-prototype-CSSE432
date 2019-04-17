package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {

}
