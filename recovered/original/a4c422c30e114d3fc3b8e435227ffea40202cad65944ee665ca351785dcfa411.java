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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour constraint-definitionType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="constraint-definitionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="validated-by" type="{http://xmlns.jcp.org/xml/ns/validation/mapping}validated-byType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="annotation" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "constraint-definitionType", propOrder = {
    "validatedBy"
})
public class ConstraintDefinitionType {

    @XmlElement(name = "validated-by", required = true)
    protected ValidatedByType validatedBy;
    @XmlAttribute(name = "annotation", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String annotation;

    /**
     * Obtient la valeur de la propriété validatedBy.
     * 
     * @return
     *     possible object is
     *     {@link ValidatedByType }
     *     
     */
    public ValidatedByType getValidatedBy() {
        return validatedBy;
    }

    /**
     * Définit la valeur de la propriété validatedBy.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidatedByType }
     *     
     */
    public void setValidatedBy(ValidatedByType value) {
        this.validatedBy = value;
    }

    /**
     * Obtient la valeur de la propriété annotation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Définit la valeur de la propriété annotation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnotation(String value) {
        this.annotation = value;
    }

}
