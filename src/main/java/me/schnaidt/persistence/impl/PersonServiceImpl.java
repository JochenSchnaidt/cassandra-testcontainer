package me.schnaidt.persistence.impl;

import me.schnaidt.persistence.PersonService;
import me.schnaidt.persistence.domain.Person;
import me.schnaidt.persistence.repo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

  private static final Logger LOG = LoggerFactory.getLogger(PersonServiceImpl.class);

  @Autowired
  private PersonRepository personRepository;

  @Override
  public void persist(String name, int age) {

    String id = name.replace(" ", "").toLowerCase();

    Person person = new Person(id, name, age);

    Person persisted = personRepository.save(person);

    LOG.info("persisted: {}", persisted);
  }

  @Override
  public Optional<Person> findById(String id) {
    return personRepository.findById(id.toLowerCase());
  }

}
