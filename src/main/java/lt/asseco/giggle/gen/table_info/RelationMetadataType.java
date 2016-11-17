//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.21 at 04:36:02 PM EEST 
//


package lt.asseco.giggle.gen.table_info;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelationMetadataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelationMetadataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="primarySchema" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="primaryTable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="primaryColumn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="foreignSchema" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="foreignTable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="foreignColumn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationMetadataType", propOrder = {
    "primarySchema",
    "primaryTable",
    "primaryColumn",
    "foreignSchema",
    "foreignTable",
    "foreignColumn"
})
public class RelationMetadataType {

    @XmlElement(required = true)
    protected String primarySchema;
    @XmlElement(required = true)
    protected String primaryTable;
    @XmlElement(required = true)
    protected String primaryColumn;
    @XmlElement(required = true)
    protected String foreignSchema;
    @XmlElement(required = true)
    protected String foreignTable;
    @XmlElement(required = true)
    protected String foreignColumn;

    /**
     * Gets the value of the primarySchema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimarySchema() {
        return primarySchema;
    }

    /**
     * Sets the value of the primarySchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimarySchema(String value) {
        this.primarySchema = value;
    }

    /**
     * Gets the value of the primaryTable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryTable() {
        return primaryTable;
    }

    /**
     * Sets the value of the primaryTable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryTable(String value) {
        this.primaryTable = value;
    }

    /**
     * Gets the value of the primaryColumn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryColumn() {
        return primaryColumn;
    }

    /**
     * Sets the value of the primaryColumn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryColumn(String value) {
        this.primaryColumn = value;
    }

    /**
     * Gets the value of the foreignSchema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignSchema() {
        return foreignSchema;
    }

    /**
     * Sets the value of the foreignSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignSchema(String value) {
        this.foreignSchema = value;
    }

    /**
     * Gets the value of the foreignTable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignTable() {
        return foreignTable;
    }

    /**
     * Sets the value of the foreignTable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignTable(String value) {
        this.foreignTable = value;
    }

    /**
     * Gets the value of the foreignColumn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignColumn() {
        return foreignColumn;
    }

    /**
     * Sets the value of the foreignColumn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignColumn(String value) {
        this.foreignColumn = value;
    }

}