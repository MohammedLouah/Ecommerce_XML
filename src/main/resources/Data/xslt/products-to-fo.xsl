<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:template match="/products">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4"
                                       page-width="210mm" page-height="297mm"
                                       margin-top="1cm" margin-bottom="1cm"
                                       margin-left="1.5cm" margin-right="1.5cm">
                    <fo:region-body margin-top="2cm" margin-bottom="2cm"/>
                    <fo:region-before extent="2cm"/>
                    <fo:region-after extent="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="10pt" text-align="right" color="#666666">
                        Liste des Produits - Page <fo:page-number/>
                    </fo:block>
                </fo:static-content>

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="8pt" text-align="center" color="#666666">
                        © 2023 Votre Entreprise - Tous droits réservés
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="24pt" font-weight="bold" color="#3498db" text-align="center" space-after="15mm">
                        Liste des Produits
                    </fo:block>

                    <fo:table table-layout="fixed" width="100%" border-collapse="separate" space-after="5mm">
                        <fo:table-column column-width="15%"/>
                        <fo:table-column column-width="10%"/>
                        <fo:table-column column-width="25%"/>
                        <fo:table-column column-width="20%"/>
                        <fo:table-column column-width="15%"/>
                        <fo:table-column column-width="15%"/>

                        <fo:table-header>
                            <fo:table-row font-weight="bold" background-color="#3498db" color="white">
                                <xsl:call-template name="table-header-cell">
                                    <xsl:with-param name="content">Image</xsl:with-param>
                                </xsl:call-template>
                                <xsl:call-template name="table-header-cell">
                                    <xsl:with-param name="content">ID</xsl:with-param>
                                </xsl:call-template>
                                <xsl:call-template name="table-header-cell">
                                    <xsl:with-param name="content">Nom</xsl:with-param>
                                </xsl:call-template>
                                <xsl:call-template name="table-header-cell">
                                    <xsl:with-param name="content">Marque</xsl:with-param>
                                </xsl:call-template>
                                <xsl:call-template name="table-header-cell">
                                    <xsl:with-param name="content">Prix</xsl:with-param>
                                </xsl:call-template>
                                <xsl:call-template name="table-header-cell">
                                    <xsl:with-param name="content">Stock</xsl:with-param>
                                </xsl:call-template>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <xsl:for-each select="product">
                                <fo:table-row>
                                    <fo:table-cell border="1pt solid #bdc3c7" padding="4pt">
                                        <fo:block>
                                            <fo:external-graphic src="{image}" content-height="1cm" content-width="scale-to-fit" scaling="uniform"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <xsl:call-template name="table-cell">
                                        <xsl:with-param name="content" select="id"/>
                                    </xsl:call-template>
                                    <xsl:call-template name="table-cell">
                                        <xsl:with-param name="content" select="name"/>
                                    </xsl:call-template>
                                    <xsl:call-template name="table-cell">
                                        <xsl:with-param name="content" select="brand"/>
                                    </xsl:call-template>
                                    <xsl:call-template name="table-cell">
                                        <xsl:with-param name="content" select="concat(price, ' €')"/>
                                        <xsl:with-param name="text-align" select="'right'"/>
                                    </xsl:call-template>
                                    <xsl:call-template name="table-cell">
                                        <xsl:with-param name="content" select="stock"/>
                                        <xsl:with-param name="text-align" select="'right'"/>
                                    </xsl:call-template>
                                </fo:table-row>
                            </xsl:for-each>
                        </fo:table-body>
                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template name="table-header-cell">
        <xsl:param name="content"/>
        <fo:table-cell border="1pt solid #3498db" padding="4pt">
            <fo:block font-weight="bold"><xsl:value-of select="$content"/></fo:block>
        </fo:table-cell>
    </xsl:template>

    <xsl:template name="table-cell">
        <xsl:param name="content"/>
        <xsl:param name="text-align" select="'left'"/>
        <fo:table-cell border="1pt solid #bdc3c7" padding="4pt">
            <fo:block text-align="{$text-align}"><xsl:value-of select="$content"/></fo:block>
        </fo:table-cell>
    </xsl:template>

</xsl:stylesheet>