package org.acme.application.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import org.acme.infrastructure.adapters.persistence.Person;
import org.acme.infrastructure.adapters.persistence.PersonRepository;

@ApplicationScoped
public class UpdatePersonService {

   @Inject
   PersonRepository personRepository;

   @Transactional(TxType.REQUIRES_NEW)
   public Person updatePerson(Long personId) {
      Person person = personRepository.findById(personId);
      person.setAge(person.getAge() + 1);
      person.setName(person.getName().toUpperCase());
      personRepository.persist(person);

      return person;
   }

}
