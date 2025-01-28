package com.example.Ecommerce.Repositories;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public abstract class AbstractXmlRepository<T, W> {
    protected final Class<W> wrapperClass;
    protected final String xmlFilePath;
    protected final JAXBContext jaxbContext;

    protected AbstractXmlRepository(Class<W> wrapperClass, String xmlFilePath) throws JAXBException {
        this.wrapperClass = wrapperClass;
        this.xmlFilePath = xmlFilePath;
        this.jaxbContext = JAXBContext.newInstance(wrapperClass);

        if (!new File(xmlFilePath).exists()) {
            createEmptyXmlStructure();
        }
    }

    protected abstract void createEmptyXmlStructure() throws JAXBException;

    protected W readXml() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (W) unmarshaller.unmarshal(new File(xmlFilePath));
    }

    protected void writeXml(W wrapper) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(wrapper, new File(xmlFilePath));
    }
}
