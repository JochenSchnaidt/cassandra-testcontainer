package me.schnaidt.persistence.repo;

import me.schnaidt.persistence.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {

  // additional custom finder methods go here

}
