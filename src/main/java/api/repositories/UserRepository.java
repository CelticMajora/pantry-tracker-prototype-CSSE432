package api.repositories;

import org.springframework.data.repository.CrudRepository;

import api.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
