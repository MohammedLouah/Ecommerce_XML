<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" indent="yes"/>

    <!-- Variables globales -->
    <xsl:variable name="orders" select="document('../../Data/xml/Orders.xml')/orders"/>
    <xsl:variable name="products" select="document('../../Data/xml/Products.xml')/products"/>
    <xsl:variable name="clients" select="document('../../Data/xml/Persons.xml')/persons"/>

    <xsl:template match="/">
        <html>
            <head>
                <style>
                    body {
                    font-family: Arial, sans-serif;
                    margin: 20px; /* Ajout de marge autour de tout le document */
                    padding: 0;
                    }
                    h1 {
                    color: #3498db;
                    text-align: center;
                    font-size: 24px;
                    margin-bottom: 20px; /* Marge en bas du titre */
                    }
                    .invoice-header {
                    margin-bottom: 30px;
                    padding: 10px;
                    border-bottom: 2px solid #3498db;
                    }
                    .invoice-header h2 { margin: 0; }
                    .invoice-info, .client-info {
                    margin-bottom: 20px;
                    padding: 10px;
                    border: 1px solid #3498db; /* Bordure ajoutée pour mieux délimiter les sections */
                    }
                    .invoice-info table, .client-info table {
                    width: 100%;
                    margin-top: 10px; /* Marge ajoutée au-dessus des tableaux */
                    }
                    .invoice-info td, .client-info td {
                    padding: 10px;
                    }
                    .invoice-info .bold, .client-info .bold {
                    font-weight: bold;
                    }
                    .product-table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 30px; /* Marge en haut de la table des produits */
                    }
                    .product-table th, .product-table td {
                    border: 1px solid #dee2e6;
                    padding: 8px;
                    text-align: center;
                    }
                    .product-table th {
                    background-color: #f8f9fa;
                    }
                    .total {
                    text-align: right;
                    margin-top: 20px;
                    font-size: 16px;
                    font-weight: bold;
                    padding-right: 20px; /* Marge à droite du total */
                    }
                    .footer {
                    text-align: center;
                    font-size: 12px;
                    color: #7f8c8d;
                    margin-top: 40px;
                    }
                </style>
            </head>
            <body>
                <div class="invoice-header">
                    <h1>Détails de la Facture</h1>
                </div>

                <xsl:apply-templates select="invoice"/>

                <div class="footer">
                    <p>Ce document est une facture générée automatiquement.</p>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="invoice">
        <xsl:variable name="current-order" select="$orders/order[id = current()/orderId]"/>
        <xsl:variable name="current-client" select="$clients/person[id = $current-order/clientId]"/>

        <!-- En-tête de la facture -->
        <div class="invoice-info">
            <h2>Facture #<xsl:value-of select="id"/></h2>
            <table>
                <tr>
                    <td class="bold">Date:</td>
                    <td><xsl:value-of select="date"/></td>
                </tr>
                <tr>
                    <td class="bold">Statut:</td>
                    <td><span style="color: #27ae60;"><xsl:value-of select="status"/></span></td>
                </tr>
            </table>
        </div>

        <!-- Informations client -->
        <div class="client-info">
            <h3 class="bold">Informations Client</h3>
            <p><xsl:value-of select="$current-client/firstName"/> <xsl:value-of select="$current-client/lastName"/></p>
            <p>Email: <xsl:value-of select="$current-client/email"/></p>
            <p>Téléphone: <xsl:value-of select="$current-client/phone"/></p>
        </div>

        <!-- Table des produits -->
        <table class="product-table">
            <thead>
                <tr>
                    <th>Produit</th>
                    <th>Marque</th>
                    <th>Quantité</th>
                    <th>Prix Unit.</th>
                    <th>Remise</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="$current-order/orderLines/orderLine">
                    <xsl:variable name="current-product" select="$products/product[id = current()/productId]"/>
                    <xsl:variable name="line-total" select="$current-product/price * quantity * (1 - discount div 100)"/>

                    <tr>
                        <td><xsl:value-of select="$current-product/name"/></td>
                        <td><xsl:value-of select="$current-product/brand"/></td>
                        <td><xsl:value-of select="quantity"/></td>
                        <td><xsl:value-of select="format-number($current-product/price, '#.00')"/> €</td>
                        <td><xsl:value-of select="discount"/>%</td>
                        <td><xsl:value-of select="format-number($line-total, '#.00')"/> €</td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>

        <!-- Calcul du total -->
        <xsl:variable name="order-total">
            <xsl:call-template name="calculate-total">
                <xsl:with-param name="order-lines" select="$current-order/orderLines/orderLine"/>
            </xsl:call-template>
        </xsl:variable>

        <!-- Affichage du total -->
        <div class="total">
            <p>Total: <xsl:value-of select="format-number($order-total, '#.00')"/> €</p>
        </div>
    </xsl:template>

    <!-- Template récursif pour calculer le total -->
    <xsl:template name="calculate-total">
        <xsl:param name="order-lines"/>
        <xsl:param name="total" select="0"/>

        <xsl:choose>
            <xsl:when test="$order-lines">
                <xsl:variable name="current-line" select="$order-lines[1]"/>
                <xsl:variable name="current-product" select="$products/product[id = $current-line/productId]"/>
                <xsl:variable name="line-total" select="$current-product/price * $current-line/quantity * (1 - $current-line/discount div 100)"/>

                <xsl:call-template name="calculate-total">
                    <xsl:with-param name="order-lines" select="$order-lines[position() > 1]"/>
                    <xsl:with-param name="total" select="$total + $line-total"/>
                </xsl:call-template>
            </xsl:when>

            <xsl:otherwise>
                <xsl:value-of select="$total"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
