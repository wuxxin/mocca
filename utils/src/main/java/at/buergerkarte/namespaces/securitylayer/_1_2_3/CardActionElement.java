//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.31 at 07:38:37 PM CET 
//


package at.buergerkarte.namespaces.securitylayer._1_2_3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for CardActionElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardActionElement">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="Action" use="required" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}CardActionType" />
 *       &lt;attribute name="ApplicationIdentifier" use="required" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}ApplicationIdentifierType" />
 *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardActionElement", propOrder = {
    "value"
})
public class CardActionElement {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "Action", required = true)
    protected CardActionType action;
    @XmlAttribute(name = "ApplicationIdentifier", required = true)
    protected ApplicationIdentifierType applicationIdentifier;
    @XmlAttribute(name = "Name")
    protected String name;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link CardActionType }
     *     
     */
    public CardActionType getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardActionType }
     *     
     */
    public void setAction(CardActionType value) {
        this.action = value;
    }

    /**
     * Gets the value of the applicationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationIdentifierType }
     *     
     */
    public ApplicationIdentifierType getApplicationIdentifier() {
        return applicationIdentifier;
    }

    /**
     * Sets the value of the applicationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationIdentifierType }
     *     
     */
    public void setApplicationIdentifier(ApplicationIdentifierType value) {
        this.applicationIdentifier = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}