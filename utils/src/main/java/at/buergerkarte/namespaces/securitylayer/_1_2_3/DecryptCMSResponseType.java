//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.01 at 04:42:31 PM CEST 
//


package at.buergerkarte.namespaces.securitylayer._1_2_3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DecryptCMSResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DecryptCMSResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DecryptedData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DecryptCMSResponseType", propOrder = {
    "decryptedData"
})
public class DecryptCMSResponseType {

    @XmlElement(name = "DecryptedData", required = true)
    protected byte[] decryptedData;

    /**
     * Gets the value of the decryptedData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDecryptedData() {
        return decryptedData;
    }

    /**
     * Sets the value of the decryptedData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDecryptedData(byte[] value) {
        this.decryptedData = value;
    }

}