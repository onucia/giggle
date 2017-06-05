<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:ns2="http://www.example.org/object-config/">

	<xsl:template match="/">
		<html>
			<head>
				<style>
					table {
						border-collapse: collapse;
						width: 100%;
					}
					td {
						font-family: "Courier New";
					}
				</style> 

			</head>
			<body>
				<h2>
					<center>Database info result</center>
				</h2>
                

				<table border="1">
					<tr bgcolor="#E6E6FA">
						<th>Type</th>
						<th>Table name</th>
                        <th>Columns</th>
						<th>Unique indexes</th>
						<th>Imported keys</th>
                        <th>Exported keys</th>
                        <th>Comments</th>
					</tr>
					<xsl:for-each select="ns2:TableInfoXml/tableInfo">
						<tr>
                            <td>
                                <xsl:value-of select="tableType" />
                            </td>
							<td>
                                <xsl:value-of select="concat(schemaName, '.', tableName)"/>
                                <br/>
                                <xsl:text> ( </xsl:text>
                                <xsl:for-each select="primaryKey">
                                    <xsl:value-of select="text()"/>
                                    <xsl:text>, </xsl:text>
                                </xsl:for-each>
                                <xsl:text> ) </xsl:text>
							</td>
							<td>
                                <xsl:for-each select="column">
                                    <xsl:value-of select="name/text()"/>
                                    <xsl:text>, </xsl:text>
                                </xsl:for-each>
							</td>
                            <td>
                                <xsl:for-each select="uniqueIndex">
                                    <xsl:value-of select="text()"/>
                                    <xsl:text>, </xsl:text>
                                </xsl:for-each>
                            </td>
							<td>
                                <xsl:attribute name="class" >
                                     <xsl:text>importedKeys</xsl:text>   
                                </xsl:attribute>
                                <table border="1">
                                    <tr>
                                        <th>Primary table</th>
                                        <th>Primary column</th>
                                        <th>FK column</th>
                                    </tr>
                                    <xsl:apply-templates select="importedKeyList"/>
                                </table>
							</td>
                            <td>
                                <xsl:attribute name="class" >
                                     <xsl:text>exportedKeys</xsl:text>   
                                </xsl:attribute> 
                                        <table border="1">
                                        <tr>
                                            <th>Foreigh table</th>
                                            <th>Foreign column</th>
                                        </tr>                     
                                        <xsl:apply-templates select="exportedKeyList"/>
                                        </table>
                            </td>
                            <td>
                                <xsl:value-of select="remarks" />
                            </td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
    
    <xsl:template match="importedKeyList">
            <tr>
                <td><xsl:value-of select="concat(primarySchema, '.', primaryTable)"/></td>
                <td><xsl:value-of select="primaryColumn"/></td>
                <td><xsl:value-of select="foreignColumn"/></td>
            </tr>
    </xsl:template>
    
    <xsl:template match="exportedKeyList">
            <tr>
                <td><xsl:value-of select="concat(foreignSchema, '.', foreignTable)"/></td>
                <td><xsl:value-of select="foreignColumn"/></td>
            </tr>
    </xsl:template>
</xsl:stylesheet>