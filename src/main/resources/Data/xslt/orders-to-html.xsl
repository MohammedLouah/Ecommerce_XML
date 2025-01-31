<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/">
        <html lang="fr">
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Rapport des Commandes</title>
                <style>
                    body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    line-height: 1.6;
                    color: #333;
                    background-color: #f4f7f9;
                    margin: 0;
                    padding: 20px;
                    }
                    .container {
                    max-width: 1200px;
                    margin: 0 auto;
                    background-color: #ffffff;
                    box-shadow: 0 0 10px rgba(0,0,0,0.1);
                    border-radius: 8px;
                    padding: 20px;
                    }
                    h1 {
                    color: #2c3e50;
                    text-align: center;
                    margin-bottom: 30px;
                    font-size: 2.5em;
                    }
                    .order {
                    margin-bottom: 40px;
                    border: 1px solid #e0e0e0;
                    border-radius: 8px;
                    overflow: hidden;
                    }
                    .order-header {
                    background-color: #3498db;
                    color: white;
                    padding: 15px;
                    font-size: 1.2em;
                    }
                    .order-header h2 {
                    margin: 0;
                    }
                    .order-details {
                    display: flex;
                    justify-content: space-between;
                    padding: 10px 15px;
                    background-color: #ecf0f1;
                    }
                    .order-details p {
                    margin: 5px 0;
                    }
                    table {
                    width: 100%;
                    border-collapse: collapse;
                    }
                    th, td {
                    text-align: left;
                    padding: 12px;
                    border-bottom: 1px solid #e0e0e0;
                    }
                    th {
                    background-color: #f2f2f2;
                    font-weight: bold;
                    }
                    tr:hover {
                    background-color: #f5f5f5;
                    }
                    .status-completed {
                    color: #27ae60;
                    font-weight: bold;
                    }
                    .status-processing {
                    color: #f39c12;
                    font-weight: bold;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Rapport des Commandes</h1>
                    <xsl:apply-templates select="//order"/>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="order">
        <div class="order">
            <div class="order-header">
                <h2>Commande #<xsl:value-of select="id"/></h2>
            </div>
            <div class="order-details">
                <p><strong>Client ID:</strong> <xsl:value-of select="clientId"/></p>
                <p><strong>Date:</strong> <xsl:value-of select="date"/></p>
                <p><strong>Statut:</strong>
                    <xsl:choose>
                        <xsl:when test="status = 'completed'">
                            <span class="status-completed">Terminée</span>
                        </xsl:when>
                        <xsl:otherwise>
                            <span class="status-processing">En cours de traitement</span>
                        </xsl:otherwise>
                    </xsl:choose>
                </p>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Produit ID</th>
                        <th>Quantité</th>
                        <th>Prix unitaire</th>
                        <th>Remise</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <xsl:apply-templates select="orderLines/orderLine"/>
                </tbody>
            </table>
        </div>
    </xsl:template>

    <xsl:template match="orderLine">
        <tr>
            <td><xsl:value-of select="productId"/></td>
            <td><xsl:value-of select="quantity"/></td>
            <td>$<xsl:value-of select="price"/></td>
            <td>
                <xsl:choose>
                    <xsl:when test="discount">
                        <xsl:value-of select="discount"/>%
                    </xsl:when>
                    <xsl:otherwise>-</xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:variable name="total">
                    <xsl:choose>
                        <xsl:when test="discount">
                            <xsl:value-of select="price * quantity * (1 - discount div 100)"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="price * quantity"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                $<xsl:value-of select="format-number($total, '#.00')"/>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>