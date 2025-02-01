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
                        Rapport des Factures
                    </fo:block>
                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center" font-size="10pt">
                        Page <fo:page-number/> sur <fo:page-number-citation ref-id="last-page"/>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates select="//invoice"/>
                    <fo:block id="last-page"/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="invoice">
        <fo:block margin-bottom="1cm" border="1pt solid #e0e0e0" padding="5mm" break-after="page">
            <fo:block font-size="14pt" font-weight="bold" color="#3498db" margin-bottom="3mm" border-bottom="2pt solid #3498db" padding-bottom="2mm">
                Facture #<xsl:value-of select="id"/>
            </fo:block>

            <fo:table width="100%" margin-top="5mm">
                <fo:table-column column-width="50%"/>
                <fo:table-column column-width="50%"/>
                <fo:table-body>
                    <fo:table-row>
                        <fo:table-cell>
                            <fo:block>
                                <fo:inline font-weight="bold">ID de commande: </fo:inline>
                                <xsl:value-of select="orderId"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell>
                            <fo:block>
                                <fo:inline font-weight="bold">Date: </fo:inline>
                                <xsl:value-of select="date"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                        <fo:table-cell number-columns-spanned="2">
                            <fo:block margin-top="5mm">
                                <fo:inline font-weight="bold">Statut: </fo:inline>
                                <fo:inline>
                                    <xsl:attribute name="color">
                                        <xsl:choose>
                                            <xsl:when test="status = 'paid'">#27ae60</xsl:when>
                                            <xsl:otherwise>#f39c12</xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:attribute>
                                    <xsl:value-of select="translate(status, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
                                </fo:inline>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>

            <fo:block margin-top="1cm" text-align="center" font-style="italic" color="#7f8c8d">
                Ce document est une facture générée automatiquement.
            </fo:block>
        </fo:block>
    </xsl:template>

</xsl:stylesheet>