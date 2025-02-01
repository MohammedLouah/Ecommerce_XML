<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>

    <xsl:param name="invoiceId"/>

    <xsl:template match="/">
        <xsl:apply-templates select="//invoice[id=$invoiceId]"/>
    </xsl:template>

    <xsl:template match="invoice">
        <invoice>
            <xsl:copy-of select="*"/>
        </invoice>
    </xsl:template>

</xsl:stylesheet>

