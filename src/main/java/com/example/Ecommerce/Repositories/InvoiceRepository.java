package com.example.Ecommerce.Repositories;

import com.example.Ecommerce.Entities.Invoice;
import com.example.Ecommerce.Utils.XPathProcessor;
import com.example.Ecommerce.Utils.XmlValidator;
import com.example.Ecommerce.Wrappers.Invoices;
import com.example.Ecommerce.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.*;

@Repository
public class InvoiceRepository extends AbstractXmlRepository<Invoice, Invoices> {
    private static final String XML_FILE_PATH = "src/main/resources/Data/xml/Invoices.xml";

    private final XmlValidator xmlValidator;
    private final XPathProcessor xPathProcessor;

    @Autowired
    public InvoiceRepository(XmlValidator xmlValidator, XPathProcessor xPathProcessor) throws JAXBException {
        super(Invoices.class, XML_FILE_PATH);
        this.xmlValidator = xmlValidator;
        this.xPathProcessor = xPathProcessor;
    }

    @Override
    protected void createEmptyXmlStructure() throws JAXBException {
        Invoices emptyInvoices = new Invoices();
        emptyInvoices.setInvoices(new ArrayList<>());
        writeXml(emptyInvoices);
    }

    public List<Invoice> findAll() throws JAXBException {
        try {
            return xPathProcessor.executeInvoiceQuery("//invoice");
        } catch (Exception e) {
            throw new JAXBException("Failed to find all invoices", e);
        }
    }

    public Optional<Invoice> findById(String id) throws JAXBException {
        try {
            String xpathExpression = String.format("//invoice[id='%s']", id);
            return xPathProcessor.executeSingleInvoiceQuery(xpathExpression);
        } catch (Exception e) {
            throw new JAXBException("Failed to find invoice by ID", e);
        }
    }

    public void save(Invoice invoice) throws JAXBException, ValidationException {
        try {
            xmlValidator.validateInvoice(invoice);

            if (invoice.getId() == null) {
                invoice.setId(UUID.randomUUID().toString());
            }

            Invoices invoices = readXml();
            invoices.getInvoices().add(invoice);
            validateAndWrite(invoices);
        } catch (Exception e) {
            throw new JAXBException("Failed to save invoice", e);
        }
    }

    public void update(Invoice updatedInvoice) throws JAXBException, ValidationException {
        try {
            xmlValidator.validateInvoice(updatedInvoice);

            String othersXpath = String.format("//invoice[id!='%s']", updatedInvoice.getId());
            List<Invoice> otherInvoices = xPathProcessor.executeInvoiceQuery(othersXpath);

            Invoices invoices = new Invoices();
            otherInvoices.add(updatedInvoice);
            invoices.setInvoices(otherInvoices);

            validateAndWrite(invoices);
        } catch (Exception e) {
            throw new JAXBException("Failed to update invoice", e);
        }
    }

    public void deleteById(String id) throws JAXBException {
        try {
            String othersXpath = String.format("//invoice[id!='%s']", id);
            List<Invoice> remainingInvoices = xPathProcessor.executeInvoiceQuery(othersXpath);

            Invoices invoices = new Invoices();
            invoices.setInvoices(remainingInvoices);
            validateAndWrite(invoices);
        } catch (Exception e) {
            throw new JAXBException("Failed to delete invoice", e);
        }
    }

    public List<Invoice> findByOrderId(String orderId) throws JAXBException {
        try {
            String xpathExpression = String.format("//invoice[orderId='%s']", orderId);
            return xPathProcessor.executeInvoiceQuery(xpathExpression);
        } catch (Exception e) {
            throw new JAXBException("Failed to find invoices by order ID", e);
        }
    }

    private void validateAndWrite(Invoices invoices) throws Exception {
        xmlValidator.validateXmlFile(new File(XML_FILE_PATH), "invoice");
        writeXml(invoices);
    }
}