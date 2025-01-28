package com.example.Ecommerce.Repositories;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;

public interface XmlRepository<T, ID> {
    List<T> findAll() throws JAXBException;
    Optional<T> findById(ID id) throws JAXBException;
    void save(T entity) throws JAXBException;
    void update(T entity) throws JAXBException;
    void deleteById(ID id) throws JAXBException;
}