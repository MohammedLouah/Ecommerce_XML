package com.example.Ecommerce.Services;

import com.example.Ecommerce.Entities.Person;
import com.example.Ecommerce.Repositories.PersonRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Optional;


@Service
public class PersonService {


    private final PersonRepository personRepository;

    public PersonService() throws JAXBException {
        this.personRepository = new PersonRepository(); // Instanciation du repository
    }

    /**
     * Authentifie un utilisateur en vérifiant son e-mail et mot de passe
     * @param email Email de l'utilisateur
     * @param password Mot de passe saisi
     * @return Un objet Person si l'authentification réussit, sinon vide
     */
    public Optional<Person> authenticate(String email, String password) throws JAXBException {
        return personRepository.findByEmail(email)
                .filter(person -> person.getPassword().equals(password));
    }


}
