<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
    <xsl:attribute-set name="tableBorder">
        <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
    </xsl:attribute-set>
    <xsl:template match="billFormList">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21.0cm" margin="1cm">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="16pt" font-weight="bold" space-after="5mm">Invoice
                    </fo:block>
                    <fo:block font-size="10pt" font-weight="bold" space-after="5mm">Date
                        <xsl:value-of select="date"/>
                    </fo:block>
                    <fo:block font-size="14pt" font-weight="bold">Order#
                        <xsl:value-of select="orderId"/>
                    </fo:block>
                    <fo:block font-size="12pt">
                        <fo:table table-layout="fixed" width="100%" border-collapse="separate">
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-column column-width="4cm"/>
                            <fo:table-column column-width="5cm"/>
                            <fo:table-header>
                                <fo:table-cell
                                        xsl:use-attribute-sets="tableBorder">
                                    <fo:block font-weight="bold">Name</fo:block>
                                </fo:table-cell>
                                <fo:table-cell
                                        xsl:use-attribute-sets="tableBorder">
                                    <fo:block font-weight="bold">Mrp</fo:block>
                                </fo:table-cell>
                                <fo:table-cell
                                        xsl:use-attribute-sets="tableBorder">
                                    <fo:block font-weight="bold">Quantity</fo:block>
                                </fo:table-cell>
                                <fo:table-cell
                                        xsl:use-attribute-sets="tableBorder">
                                    <fo:block font-weight="bold">Selling Price</fo:block>
                                </fo:table-cell>
                            </fo:table-header>
                            <fo:table-body end-indent="0in">
                                <xsl:apply-templates select="billForm"/>
                            </fo:table-body>
                        </fo:table>
                        <fo:block font-size="16pt" font-family="Helvetica"
                                  color="black" font-weight="bold" space-after="5mm" space-before="15pt">
                            Total: Rs.
                            <xsl:value-of select="total" />
                        </fo:block>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="billForm">
        <fo:table-row>
<!--            <xsl:if test="designation = 'Manager'">-->
<!--                <xsl:attribute name="font-weight">bold</xsl:attribute>-->
<!--            </xsl:if>-->
            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                <fo:block>
                    <xsl:value-of select="name"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                <fo:block>
                    <xsl:value-of select="mrp"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block xsl:use-attribute-sets="tableBorder">
                    <xsl:value-of select="quantity"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block xsl:use-attribute-sets="tableBorder">
                    <xsl:value-of select="sellingPrice"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
</xsl:stylesheet>