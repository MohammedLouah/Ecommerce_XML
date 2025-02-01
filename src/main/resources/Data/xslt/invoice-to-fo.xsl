<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:output method="xml" indent="yes"/>

    <!-- Variables globales -->
    <xsl:variable name="orders" select="document('../../Data/xml/Orders.xml')/orders"/>
    <xsl:variable name="products" select="document('../../Data/xml/Products.xml')/products"/>
    <xsl:variable name="clients" select="document('../../Data/xml/Persons.xml')/persons"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-height="29.7cm" page-width="21cm"
                                       margin-top="1cm" margin-bottom="1cm" margin-left="1cm" margin-right="1cm">
                    <fo:region-body margin-top="2cm" margin-bottom="2cm"/>
                    <fo:region-before extent="2cm"/>
                    <fo:region-after extent="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block text-align="center" font-size="16pt" font-weight="bold" color="#3498db" padding-bottom="5mm">
                        Détails de la Facture
                    </fo:block>
                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center" font-size="10pt">
                        Page <fo:page-number/> sur <fo:page-number-citation ref-id="last-page"/>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates select="invoice"/>
                    <fo:block id="last-page"/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="invoice">
        <xsl:variable name="current-order" select="$orders/order[id = current()/orderId]"/>
        <xsl:variable name="current-client" select="$clients/person[id = $current-order/clientId]"/>

        <!-- En-tête de la facture -->
        <fo:block margin-bottom="1cm" border="1pt solid #e0e0e0" padding="5mm">
            <fo:block font-size="14pt" font-weight="bold" color="#3498db"
                      margin-bottom="3mm" border-bottom="2pt solid #3498db" padding-bottom="2mm">
                Facture #<xsl:value-of select="id"/>
            </fo:block>

            <!-- Informations générales -->
            <fo:table width="100%" margin-top="5mm">
                <fo:table-column column-width="50%"/>
                <fo:table-column column-width="50%"/>
                <fo:table-body>
                    <fo:table-row>
                        <fo:table-cell>
                            <fo:block>
                                <fo:inline font-weight="bold">Date: </fo:inline>
                                <xsl:value-of select="date"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell>
                            <fo:block>
                                <fo:inline font-weight="bold">Statut: </fo:inline>
                                <fo:inline color="#27ae60"><xsl:value-of select="status"/></fo:inline>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>

            <!-- Informations client -->
            <fo:block margin-top="8mm" padding="3mm" background-color="#f8f9fa">
                <fo:block font-weight="bold" margin-bottom="2mm">Informations Client</fo:block>
                <fo:block><xsl:value-of select="$current-client/firstName"/> <xsl:value-of select="$current-client/lastName"/></fo:block>
                <fo:block>Email: <xsl:value-of select="$current-client/email"/></fo:block>
                <fo:block>Téléphone: <xsl:value-of select="$current-client/phone"/></fo:block>
            </fo:block>

            <!-- Table des produits -->
            <fo:block margin-top="8mm">
                <fo:table width="100%" border="1pt solid #dee2e6">
                    <fo:table-column column-width="25%"/>
                    <fo:table-column column-width="15%"/>
                    <fo:table-column column-width="15%"/>
                    <fo:table-column column-width="15%"/>
                    <fo:table-column column-width="15%"/>
                    <fo:table-column column-width="15%"/>

                    <fo:table-header>
                        <fo:table-row background-color="#f8f9fa">
                            <fo:table-cell padding="2mm"><fo:block>Produit</fo:block></fo:table-cell>
                            <fo:table-cell padding="2mm"><fo:block>Marque</fo:block></fo:table-cell>
                            <fo:table-cell padding="2mm"><fo:block>Quantité</fo:block></fo:table-cell>
                            <fo:table-cell padding="2mm"><fo:block>Prix Unit.</fo:block></fo:table-cell>
                            <fo:table-cell padding="2mm"><fo:block>Remise</fo:block></fo:table-cell>
                            <fo:table-cell padding="2mm"><fo:block>Total</fo:block></fo:table-cell>
                        </fo:table-row>
                    </fo:table-header>

                    <fo:table-body>
                        <xsl:for-each select="$current-order/orderLines/orderLine">
                            <xsl:variable name="current-product" select="$products/product[id = current()/productId]"/>
                            <xsl:variable name="line-total" select="$current-product/price * quantity * (1 - discount div 100)"/>

                            <fo:table-row>
                                <fo:table-cell padding="2mm"><fo:block><xsl:value-of select="$current-product/name"/></fo:block></fo:table-cell>
                                <fo:table-cell padding="2mm"><fo:block><xsl:value-of select="$current-product/brand"/></fo:block></fo:table-cell>
                                <fo:table-cell padding="2mm"><fo:block><xsl:value-of select="quantity"/></fo:block></fo:table-cell>
                                <fo:table-cell padding="2mm"><fo:block><xsl:value-of select="format-number($current-product/price, '#.00')"/> €</fo:block></fo:table-cell>
                                <fo:table-cell padding="2mm"><fo:block><xsl:value-of select="discount"/>%</fo:block></fo:table-cell>
                                <fo:table-cell padding="2mm"><fo:block><xsl:value-of select="format-number($line-total, '#.00')"/> €</fo:block></fo:table-cell>
                            </fo:table-row>
                        </xsl:for-each>
                    </fo:table-body>
                </fo:table>
            </fo:block>

            <!-- Calcul du total avec une approche récursive -->
            <xsl:variable name="order-total">
                <xsl:call-template name="calculate-total">
                    <xsl:with-param name="order-lines" select="$current-order/orderLines/orderLine"/>
                </xsl:call-template>
            </xsl:variable>

            <!-- Affichage du total -->
            <fo:block margin-top="5mm" text-align="right" font-weight="bold" font-size="12pt">
                Total: <xsl:value-of select="format-number($order-total, '#.00')"/> €
            </fo:block>

            <!-- Pied de page de la facture -->
            <fo:block margin-top="1cm" text-align="center" font-style="italic" color="#7f8c8d">
                Ce document est une facture générée automatiquement.
            </fo:block>
        </fo:block>
    </xsl:template>

    <!-- Template récursif pour calculer le total -->
    <xsl:template name="calculate-total">
        <xsl:param name="order-lines"/>
        <xsl:param name="total" select="0"/>

        <xsl:choose>
            <!-- S'il reste des lignes à traiter -->
            <xsl:when test="$order-lines">
                <xsl:variable name="current-line" select="$order-lines[1]"/>
                <xsl:variable name="current-product" select="$products/product[id = $current-line/productId]"/>
                <xsl:variable name="line-total" select="$current-product/price * $current-line/quantity * (1 - $current-line/discount div 100)"/>

                <!-- Appel récursif avec le reste des lignes et le total mis à jour -->
                <xsl:call-template name="calculate-total">
                    <xsl:with-param name="order-lines" select="$order-lines[position() > 1]"/>
                    <xsl:with-param name="total" select="$total + $line-total"/>
                </xsl:call-template>
            </xsl:when>

            <!-- Si toutes les lignes ont été traitées, retourner le total -->
            <xsl:otherwise>
                <xsl:value-of select="$total"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>