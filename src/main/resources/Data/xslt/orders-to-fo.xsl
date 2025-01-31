<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-height="29.7cm" page-width="21cm" margin-top="1cm" margin-bottom="1cm" margin-left="1cm" margin-right="1cm">
                    <fo:region-body margin-top="2cm" margin-bottom="2cm"/>
                    <fo:region-before extent="2cm"/>
                    <fo:region-after extent="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block text-align="center" font-size="16pt" font-weight="bold" color="#3498db" padding-bottom="5mm">
                        Rapport des Commandes
                    </fo:block>
                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center" font-size="10pt">
                        Page <fo:page-number/> sur <fo:page-number-citation ref-id="last-page"/>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates select="//order"/>
                    <fo:block id="last-page"/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="order">
        <fo:block margin-bottom="1cm" border="1pt solid #e0e0e0" padding="5mm" break-after="page">
            <fo:block font-size="14pt" font-weight="bold" color="#3498db" margin-bottom="3mm">
                Commande #<xsl:value-of select="id"/>
            </fo:block>

            <fo:table width="100%" margin-bottom="5mm">
                <fo:table-column column-width="33%"/>
                <fo:table-column column-width="33%"/>
                <fo:table-column column-width="34%"/>
                <fo:table-body>
                    <fo:table-row>
                        <fo:table-cell><fo:block>Client ID: <xsl:value-of select="clientId"/></fo:block></fo:table-cell>
                        <fo:table-cell><fo:block>Date: <xsl:value-of select="date"/></fo:block></fo:table-cell>
                        <fo:table-cell>
                            <fo:block>
                                Statut:
                                <xsl:choose>
                                    <xsl:when test="status = 'completed'">
                                        <fo:inline color="#27ae60">Terminée</fo:inline>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <fo:inline color="#f39c12">En cours de traitement</fo:inline>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>

            <fo:table width="100%" border-collapse="collapse">
                <fo:table-column column-width="20%"/>
                <fo:table-column column-width="20%"/>
                <fo:table-column column-width="20%"/>
                <fo:table-column column-width="20%"/>
                <fo:table-column column-width="20%"/>

                <fo:table-header>
                    <fo:table-row background-color="#f2f2f2">
                        <fo:table-cell border="1pt solid #e0e0e0" padding="2mm"><fo:block font-weight="bold">Produit ID</fo:block></fo:table-cell>
                        <fo:table-cell border="1pt solid #e0e0e0" padding="2mm"><fo:block font-weight="bold">Quantité</fo:block></fo:table-cell>
                        <fo:table-cell border="1pt solid #e0e0e0" padding="2mm"><fo:block font-weight="bold">Prix unitaire</fo:block></fo:table-cell>
                        <fo:table-cell border="1pt solid #e0e0e0" padding="2mm"><fo:block font-weight="bold">Remise</fo:block></fo:table-cell>
                        <fo:table-cell border="1pt solid #e0e0e0" padding="2mm"><fo:block font-weight="bold">Total</fo:block></fo:table-cell>
                    </fo:table-row>
                </fo:table-header>

                <fo:table-body>
                    <xsl:apply-templates select="orderLines/orderLine"/>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

    <xsl:template match="orderLine">
        <fo:table-row>
            <fo:table-cell border="1pt solid #e0e0e0" padding="2mm"><fo:block><xsl:value-of select="productId"/></fo:block></fo:table-cell>
            <fo:table-cell border="1pt solid #e0e0e0" padding="2mm"><fo:block><xsl:value-of select="quantity"/></fo:block></fo:table-cell>
            <fo:table-cell border="1pt solid #e0e0e0" padding="2mm"><fo:block>$<xsl:value-of select="price"/></fo:block></fo:table-cell>
            <fo:table-cell border="1pt solid #e0e0e0" padding="2mm">
                <fo:block>
                    <xsl:choose>
                        <xsl:when test="discount">
                            <xsl:value-of select="discount"/>%
                        </xsl:when>
                        <xsl:otherwise>-</xsl:otherwise>
                    </xsl:choose>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell border="1pt solid #e0e0e0" padding="2mm">
                <fo:block>
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
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

</xsl:stylesheet>