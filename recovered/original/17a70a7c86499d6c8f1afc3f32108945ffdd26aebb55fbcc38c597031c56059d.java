//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.08 à 03:47:53 PM CEST 
//


package org.apache.bval.jsr.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour constructorType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="constructorType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parameter" type="{http://xmlns.jcp.org/xml/ns/validation/mapping}parameterType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="cross-parameter" type="{http://xmlns.jcp.org/xml/ns/validation/mapping}crossParameterType" minOccurs="0"/&gt;
 *         &lt;element name="return-value" type="{http://xmlns.jcp.org/xml/ns/validation/mapping}returnValueType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="ignore-annotations" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "constructorType", propOrder = {
    "parameter",
    "crossParameter",
    "returnValue"
})
public class ConstructorType {

    protected List<ParameterType> parameter;
    @XmlElement(name = "cross-parameter")
    protected CrossParameterType crossParameter;
    @XmlElement(name = "return-value")
    protected ReturnValueType returnValue;
    @XmlAttribute(name = "ignore-annotations")
    protected Boolean ignoreAnnotations;

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParameterType }
     * 
     * 
     */
    public List<ParameterType> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<ParameterType>();
        }
        return this.parameter;
    }

    /**
     * Obtient la valeur de la propriété crossParameter.
     * 
     * @return
     *     possible object is
     *     {@link CrossParameterType }
     *     
     */
    public CrossParameterType getCrossParameter() {
        return crossParameter;
    }

    /**
     * Définit la valeur de la propriété crossParameter.
     * 
     * @param value
     *     allowed object is
     *     {@link CrossParameterType }
     *     
     */
    public void setCrossParameter(CrossParameterType value) {
        this.crossParameter = value;
    }

    /**
     * Obtient la valeur de la propriété returnValue.
     * 
     * @return
     *     possible object is
     *     {@link ReturnValueType }
     *     
     */
    public ReturnValueType getReturnValue() {
        return returnValue;
    }

    /**
     * Définit la valeur de la propriété returnValue.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnValueType }
     *     
     */
    public void setReturnValue(ReturnValueType value) {
        this.returnValue = value;
    }

    /**
     * Obtient la valeur de la propriété ignoreAnnotations.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIgnoreAnnotations() {
        return ignoreAnnotations;
    }

    /**
     * Définit la valeur de la propriété ignoreAnnotations.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIgnoreAnnotations(Boolean value) {
        this.ignoreAnnotations = value;
    }

}
