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
                <xsl:for-each select="ns2:TableInfoXml/tableInfo">
                
                <p>
                    <div><xsl:value-of select="tableType" /><xsl:text>   </xsl:text><xsl:value-of select="tableName"/> </div>
                    <table border="1" width="100%">
					<tr bgcolor="#E6E6FA">
						<th>DB column</th>
                        <th>Column type</th>
						<th>Description</th>
					</tr>
                    <xsl:apply-templates select="column"/>
                    </table>
                </p>
                </xsl:for-each>
			</body>
		</html>
	</xsl:template>

    <xsl:template match="column">
            <tr>
                <td><xsl:value-of select="name"/></td>
                <td><xsl:value-of select="type"/> <xsl:text> (</xsl:text> <xsl:value-of select="size"/> <xsl:text>) </xsl:text></td>
                <td>
                    <xsl:if test="nullable='0'" >Not null.<br /></xsl:if>
                    <xsl:value-of select="remarks"/>
                </td>
            </tr>
    </xsl:template>
    
</xsl:stylesheet>