package com.example.Ecommerce.Repositories;


import com.example.Ecommerce.Entities.Person;
import com.example.Ecommerce.Wrappers.Persons;

import javax.xml.bind.JAXBException;
import java.util.Optional;

public class PersonRepository extends AbstractXmlRepository<Person, Persons>{

    private static final String XML_FILE = "src/main/resources/Data/xml/Persons.xml"; // Fichier XML contenant les utilisateurs

    public PersonRepository() throws JAXBException {
        super(Persons.class, XML_FILE); // Initialise la gestion XML avec la classe wrapper
    }

    @Override
    protected void createEmptyXmlStructure() throws JAXBException {
        writeXml(new Persons()); // Création d'un fichier XML vide si non existant
    }

    public Optional<Person> findByEmail(String email) throws JAXBException {
        return readXml().getPersons().stream()
                .filter(person -> person.getEmail().equalsIgnoreCase(email)) // Recherche d'un utilisateur par e-mail
                .findFirst();
    }





}
