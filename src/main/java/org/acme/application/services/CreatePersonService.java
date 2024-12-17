package org.acme.application.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import org.acme.infrastructure.adapters.persistence.Person;
import org.acme.infrastructure.adapters.persistence.PersonRepository;
import org.acme.shared.dto.CreatePersonDto;

@ApplicationScoped
public class CreatePersonService {

   @Inject
   PersonRepository personRepository;

   @Transactional
   public Person createPerson(String personName) {
      Person person = new Person();
      person.setAge(10);
      person.setName(personName);
      personRepository.persist(person);

      return person;
   }

   @Transactional
   public Person createPerson(CreatePersonDto createPersonDto) {
      Person person = new Person();
      person.setAge(createPersonDto.getAge());
      person.setName(createPersonDto.getName());
      personRepository.persist(person);

      return person;
   }

}
