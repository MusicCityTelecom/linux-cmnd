//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.08 à 03:47:53 PM CEST 
//


package org.apache.bval.jsr.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour executable-validationType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="executable-validationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="default-validated-executable-types" type="{http://xmlns.jcp.org/xml/ns/validation/configuration}default-validated-executable-typesType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executable-validationType", namespace = "http://xmlns.jcp.org/xml/ns/validation/configuration", propOrder = {
    "defaultValidatedExecutableTypes"
})
public class ExecutableValidationType {

    @XmlElement(name = "default-validated-executable-types")
    protected DefaultValidatedExecutableTypesType defaultValidatedExecutableTypes;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;

    /**
     * Obtient la valeur de la propriété defaultValidatedExecutableTypes.
     * 
     * @return
     *     possible object is
     *     {@link DefaultValidatedExecutableTypesType }
     *     
     */
    public DefaultValidatedExecutableTypesType getDefaultValidatedExecutableTypes() {
        return defaultValidatedExecutableTypes;
    }

    /**
     * Définit la valeur de la propriété defaultValidatedExecutableTypes.
     * 
     * @param value
     *     allowed object is
     *     {@link DefaultValidatedExecutableTypesType }
     *     
     */
    public void setDefaultValidatedExecutableTypes(DefaultValidatedExecutableTypesType value) {
        this.defaultValidatedExecutableTypes = value;
    }

    /**
     * Obtient la valeur de la propriété enabled.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getEnabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    /**
     * Définit la valeur de la propriété enabled.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

}
