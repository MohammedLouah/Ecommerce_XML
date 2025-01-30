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
}
