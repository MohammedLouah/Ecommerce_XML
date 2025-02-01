<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/">
        <html lang="fr">
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Rapport des Factures</title>
                <style>
                    body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    line-height: 1.6;
                    color: #333;
                    background-color: #f0f4f8;
                    margin: 0;
                    padding: 20px;
                    }
                    .container {
                    max-width: 800px;
                    margin: 0 auto;
                    background-color: #ffffff;
                    box-shadow: 0 0 20px rgba(0,0,0,0.1);
                    border-radius: 8px;
                    padding: 30px;
                    }
                    h1 {
                    color: #2c3e50;
                    text-align: center;
                    margin-bottom: 30px;
                    font-size: 2.5em;
                    border-bottom: 2px solid #3498db;
                    padding-bottom: 10px;
                    }
                    .invoice {
                    background-color: #ffffff;
                    border: 1px solid #e0e0e0;
                    border-radius: 8px;
                    margin-bottom: 20px;
                    overflow: hidden;
                    transition: all 0.3s ease;
                    }
                    .invoice:hover {
                    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
                    transform: translateY(-3px);
                    }
                    .invoice-header {
                    background-color: #3498db;
                    color: white;
                    padding: 15px;
                    font-size: 1.2em;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    }
                    .invoice-body {
                    padding: 20px;
                    }
                    .invoice-body p {
                    margin: 10px 0;
                    }
                    .status {
                    font-weight: bold;
                    text-transform: uppercase;
                    font-size: 0.9em;
                    }
                    .status-paid {
                    color: #27ae60;
                    }
                    .status-pending {
                    color: #f39c12;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Rapport des Factures</h1>
                    <xsl:apply-templates select="//invoice"/>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="invoice">
        <div class="invoice">
            <div class="invoice-header">
                <span>Facture #<xsl:value-of select="id"/></span>
                <span class="status">
                    <xsl:attribute name="class">
                        status
                        <xsl:choose>
                            <xsl:when test="status = 'paid'">status-paid</xsl:when>
                            <xsl:otherwise>status-pending</xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                    <xsl:value-of select="status"/>
                </span>
            </div>
            <div class="invoice-body">
                <p><strong>ID de commande:</strong> <xsl:value-of select="orderId"/></p>
                <p><strong>Date:</strong> <xsl:value-of select="date"/></p>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>