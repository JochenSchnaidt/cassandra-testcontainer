package me.schnaidt.persistence;

import me.schnaidt.persistence.domain.Person;

import java.util.Optional;

public interface PersonService {

  void persist(String name, int age);

  Optional<Person> findById(String id);

}
