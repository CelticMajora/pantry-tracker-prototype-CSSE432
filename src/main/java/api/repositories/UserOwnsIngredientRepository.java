package api.repositories;

import org.springframework.data.repository.CrudRepository;

import api.entities.UserOwnsIngredient;

public interface UserOwnsIngredientRepository extends CrudRepository<UserOwnsIngredient, Integer> {

}
