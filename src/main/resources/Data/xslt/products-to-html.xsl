<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/">
        <html lang="fr">
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Liste des Produits</title>
                <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&amp;display=swap" rel="stylesheet"/>
                <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
                <style>
                    body {
                    font-family: 'Roboto', sans-serif;
                    line-height: 1.6;
                    color: #333;
                    max-width: 1200px;
                    margin: 0 auto;
                    padding: 20px;
                    background-color: #f4f4f4;
                    }
                    h1 {
                    color: #2c3e50;
                    text-align: center;
                    margin-bottom: 30px;
                    }
                    table {
                    width: 100%;
                    border-collapse: separate;
                    border-spacing: 0 10px;
                    }
                    tr {
                    background-color: white;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                    transition: transform 0.2s ease;
                    }
                    tr:hover {
                    transform: translateY(-3px);
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                    }
                    th {
                    background-color: #3498db;
                    color: white;
                    font-weight: bold;
                    text-transform: uppercase;
                    padding: 12px;
                    text-align: left;
                    }
                    td {
                    padding: 12px;
                    }
                    .product-image {
                    width: 100px;
                    height: 100px;
                    object-fit: cover;
                    border-radius: 4px;
                    }
                    .product-name {
                    font-weight: bold;
                    color: #2c3e50;
                    }
                    .product-brand {
                    color: #7f8c8d;
                    font-size: 0.9em;
                    }
                    .product-price {
                    font-weight: bold;
                    color: #27ae60;
                    }
                    .stock-low {
                    color: #e74c3c;
                    }
                    .stock-medium {
                    color: #f39c12;
                    }
                    .stock-high {
                    color: #27ae60;
                    }
                    footer {
                    margin-top: 30px;
                    text-align: center;
                    color: #7f8c8d;
                    font-size: 0.9em;
                    }
                </style>
            </head>
            <body>
                <header>
                    <h1><i class="fas fa-box-open"></i> Liste des Produits</h1>
                </header>
                <main>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Marque</th>
                                <th>Prix</th>
                                <th>Stock</th>
                                <th>Image</th>
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:for-each select="//product">
                                <tr>
                                    <td><xsl:value-of select="id"/></td>
                                    <td class="product-name"><xsl:value-of select="name"/></td>
                                    <td class="product-brand"><xsl:value-of select="brand"/></td>
                                    <td class="product-price"><xsl:value-of select="price"/>€</td>
                                    <td>
                                        <xsl:variable name="stock" select="number(stock)"/>
                                        <xsl:attribute name="class">
                                            <xsl:choose>
                                                <xsl:when test="$stock &lt; 10">stock-low</xsl:when>
                                                <xsl:when test="$stock &lt; 50">stock-medium</xsl:when>
                                                <xsl:otherwise>stock-high</xsl:otherwise>
                                            </xsl:choose>
                                        </xsl:attribute>
                                        <xsl:value-of select="$stock"/>
                                    </td>
                                    <td>
                                        <img src="{image}" alt="{name}" class="product-image"/>
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </tbody>
                    </table>
                </main>
                <footer>
                    <p>© 2023 Liste des Produits. Tous droits réservés.</p>
                </footer>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>