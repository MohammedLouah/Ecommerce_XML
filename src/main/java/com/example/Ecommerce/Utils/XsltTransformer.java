package com.example.Ecommerce.Utils;

import org.apache.fop.apps.*;
import org.springframework.stereotype.Component;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXResult;
import java.io.*;
import java.util.UUID;

@Component
public class XsltTransformer {
    private static final String RESOURCES_PATH = "src/main/resources/Data/";
    private static final String XSLT_PATH = RESOURCES_PATH + "xslt/";
    private static final String TEMP_PATH = RESOURCES_PATH + "xml/";
    private final FopFactory fopFactory;

    public XsltTransformer() throws IOException {
        // Initialisation de FOP
        this.fopFactory = FopFactory.newInstance(new File(".").toURI());
        // Création du dossier temp s'il n'existe pas
        new File(TEMP_PATH).mkdirs();
    }

    /**
     * Génère un fichier HTML à partir d'un fichier XML en utilisant un template XSLT
     *
     * @param xmlSource    Fichier XML source
     * @param xsltTemplate Nom du template XSLT (sans extension)
     * @return Le fichier HTML généré
     */
    public File generateHtml(File xmlSource, String xsltTemplate) throws TransformerException {
        String outputPath = TEMP_PATH + UUID.randomUUID() + ".html";
        File outputFile = new File(outputPath);

        // Validation des fichiers d'entrée
        if (!xmlSource.exists() || !xmlSource.isFile()) {
            throw new IllegalArgumentException("Le fichier XML source est introuvable ou invalide : " + xmlSource.getPath());
        }
        File xsltFile = new File(XSLT_PATH + xsltTemplate + "-to-html.xsl");
        if (!xsltFile.exists() || !xsltFile.isFile()) {
            throw new IllegalArgumentException("Le template XSLT est introuvable : " + xsltFile.getPath());
        }

        try {
            // Configuration du transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(xsltFile);
            Transformer transformer = factory.newTransformer(xslt);

            // Transformation
            Source xml = new StreamSource(xmlSource);
            Result output = new StreamResult(outputFile);
            transformer.transform(xml, output);

            return outputFile;
        } catch (TransformerException e) {
            throw new TransformerException("Échec de la génération du fichier HTML : " + e.getMessage(), e);
        }
    }

    /**
     * Génère un fichier PDF à partir d'un fichier XML en utilisant un template XSLT-FO
     *
     * @param xmlSource    Fichier XML source
     * @param xsltTemplate Nom du template XSLT (sans extension)
     * @return Le fichier PDF généré
     */
    public File generatePdf(File xmlSource, String xsltTemplate) throws TransformerException, IOException {
        String outputPath = TEMP_PATH + UUID.randomUUID() + ".pdf";
        File outputFile = new File(outputPath);

        // Validation des fichiers d'entrée
        if (!xmlSource.exists() || !xmlSource.isFile()) {
            throw new IllegalArgumentException("Le fichier XML source est introuvable ou invalide : " + xmlSource.getPath());
        }
        File xsltFile = new File(XSLT_PATH + xsltTemplate + "-to-fo.xsl");
        if (!xsltFile.exists() || !xsltFile.isFile()) {
            throw new IllegalArgumentException("Le template XSLT-FO est introuvable : " + xsltFile.getPath());
        }

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            // Configuration FOP
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Configuration XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xsltSource = new StreamSource(xsltFile);
            Transformer transformer = factory.newTransformer(xsltSource);

            // Transformation
            Source xml = new StreamSource(xmlSource);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xml, res);

            return outputFile;
        } catch (Exception e) {
            throw new TransformerException("Échec de la génération du fichier PDF : " + e.getMessage(), e);
        }
    }

    /**
     * Nettoie les fichiers temporaires plus vieux qu'une heure
     */
    public void cleanupTempFiles() {
        File tempDir = new File(TEMP_PATH);
        if (tempDir.exists()) {
            File[] files = tempDir.listFiles();
            if (files != null) {
                long now = System.currentTimeMillis();
                long hour = 60 * 60 * 1000;

                for (File file : files) {
                    if (now - file.lastModified() > hour) {
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     * Génère une facture au format HTML
     *
     * @param invoiceId ID de la facture à transformer
     * @return Le fichier HTML généré
     */
    public File generateInvoiceHtml(String invoiceId) throws TransformerException {
        File xmlSource = new File(RESOURCES_PATH + "xml/Invoices.xml");
        if (!xmlSource.exists()) {
            throw new IllegalArgumentException("Le fichier Invoices.xml est introuvable");
        }

        // Création d'un fichier XML temporaire contenant uniquement la facture demandée
        File singleInvoiceXml = extractSingleInvoice(xmlSource, invoiceId);
        try {
            return generateHtml(singleInvoiceXml, "invoice");
        } finally {
            // Nettoyage du fichier temporaire
            singleInvoiceXml.delete();
        }
    }

    /**
     * Génère une facture au format PDF
     *
     * @param invoiceId ID de la facture à transformer
     * @return Le fichier PDF généré
     */
    public File generateInvoicePdf(String invoiceId) throws TransformerException, IOException {
        File xmlSource = new File(RESOURCES_PATH + "xml/Invoices.xml");
        if (!xmlSource.exists()) {
            throw new IllegalArgumentException("Le fichier Invoices.xml est introuvable");
        }

        // Création d'un fichier XML temporaire contenant uniquement la facture demandée
        File singleInvoiceXml = extractSingleInvoice(xmlSource, invoiceId);
        try {
            return generatePdf(singleInvoiceXml, "invoice");
        } finally {
            // Nettoyage du fichier temporaire
            singleInvoiceXml.delete();
        }
    }

    /**
     * Extrait une facture spécifique du fichier XML source
     *
     * @param sourceFile Fichier XML source contenant toutes les factures
     * @param invoiceId ID de la facture à extraire
     * @return Fichier XML temporaire contenant uniquement la facture demandée
     */
    private File extractSingleInvoice(File sourceFile, String invoiceId) throws TransformerException {
        try {
            // Configuration du transformer pour l'extraction
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File(XSLT_PATH + "extract-invoice.xsl"));
            Transformer transformer = factory.newTransformer(xslt);

            // Définition du paramètre pour l'ID de la facture
            transformer.setParameter("invoiceId", invoiceId);

            // Création du fichier temporaire
            File tempFile = new File(TEMP_PATH + "single_invoice_" + UUID.randomUUID() + ".xml");
            Result output = new StreamResult(tempFile);

            // Transformation
            Source xml = new StreamSource(sourceFile);
            transformer.transform(xml, output);

            return tempFile;
        } catch (Exception e) {
            throw new TransformerException("Échec de l'extraction de la facture : " + e.getMessage(), e);
        }
    }
}
