package com.example.Ecommerce.Services;

import com.example.Ecommerce.Entities.Invoice;
import com.example.Ecommerce.Exceptions.ValidationException;
import com.example.Ecommerce.Repositories.InvoiceRepository;
import com.example.Ecommerce.Utils.XsltTransformer;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final XsltTransformer xsltTransformer;

    public InvoiceService(InvoiceRepository invoiceRepository, XsltTransformer xsltTransformer) {
        this.invoiceRepository = invoiceRepository;
        this.xsltTransformer = xsltTransformer;
    }

    /**
     * Récupère toutes les factures
     */
    public List<Invoice> findAll() throws JAXBException {
        return invoiceRepository.findAll();
    }

    /**
     * Recherche une facture par son ID
     */
    public Optional<Invoice> findById(String id) throws JAXBException {
        return invoiceRepository.findById(id);
    }

    /**
     * Recherche les factures associées à une commande
     */
    public List<Invoice> findByOrderId(String orderId) throws JAXBException {
        return invoiceRepository.findByOrderId(orderId);
    }

    /**
     * Crée une nouvelle facture
     */
    public Invoice createInvoice(Invoice invoice) throws JAXBException, ValidationException {
        invoiceRepository.save(invoice);
        return invoice;
    }

    /**
     * Met à jour une facture existante
     */
    public void updateInvoice(Invoice invoice) throws JAXBException, ValidationException {
        invoiceRepository.update(invoice);
    }

    /**
     * Supprime une facture
     */
    public void deleteInvoice(String id) throws JAXBException {
        invoiceRepository.deleteById(id);
    }

    /**
     * Exporte toutes les factures au format HTML
     */
    public File exportInvoicesToHtml() throws Exception {
        File xmlSource = new File("src/main/resources/Data/xml/Invoices.xml");
        return xsltTransformer.generateHtml(xmlSource, "invoices");
    }

    /**
     * Exporte toutes les factures au format PDF
     */
    public File exportInvoicesToPdf() throws Exception {
        File xmlSource = new File("src/main/resources/Data/xml/Invoices.xml");
        return xsltTransformer.generatePdf(xmlSource, "invoices");
    }

    /**
     * Exporte une facture spécifique au format HTML
     */
    public File exportInvoiceToHtml(String invoiceId) throws Exception {
        return xsltTransformer.generateInvoiceHtml(invoiceId);
    }

    /**
     * Exporte une facture spécifique au format PDF
     */
    public File exportInvoiceToPdf(String invoiceId) throws Exception {
        return xsltTransformer.generateInvoicePdf(invoiceId);
    }

    /**
     * Met à jour le statut d'une facture
     */
    public void updateInvoiceStatus(String id, String newStatus) throws JAXBException, ValidationException {
        Optional<Invoice> optionalInvoice = findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoice.setStatus(newStatus);
            updateInvoice(invoice);
        } else {
            throw new JAXBException("Facture non trouvée avec l'ID : " + id);
        }
    }
}