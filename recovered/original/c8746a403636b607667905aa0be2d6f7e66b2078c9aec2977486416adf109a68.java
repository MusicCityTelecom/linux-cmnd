//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.08 à 03:47:53 PM CEST 
//


package org.apache.bval.jsr.xml;

import java.util.ArrayList;
import java.util.List;
import javax.validation.executable.ExecutableType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour default-validated-executable-typesType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="default-validated-executable-typesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="executable-type" maxOccurs="unbounded"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="NONE"/&gt;
 *               &lt;enumeration value="CONSTRUCTORS"/&gt;
 *               &lt;enumeration value="NON_GETTER_METHODS"/&gt;
 *               &lt;enumeration value="GETTER_METHODS"/&gt;
 *               &lt;enumeration value="ALL"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "default-validated-executable-typesType", namespace = "http://xmlns.jcp.org/xml/ns/validation/configuration", propOrder = {
    "executableType"
})
public class DefaultValidatedExecutableTypesType {

    @XmlElement(name = "executable-type", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected List<ExecutableType> executableType;

    /**
     * Gets the value of the executableType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the executableType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExecutableType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<ExecutableType> getExecutableType() {
        if (executableType == null) {
            executableType = new ArrayList<ExecutableType>();
        }
        return this.executableType;
    }

}
