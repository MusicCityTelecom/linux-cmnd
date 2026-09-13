//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.06.08 à 03:47:53 PM CEST 
//


package org.apache.bval.jsr.xml;

import javax.validation.executable.ExecutableType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, ExecutableType>
{


    public ExecutableType unmarshal(String value) {
        return (javax.validation.executable.ExecutableType.valueOf(value));
    }

    public String marshal(ExecutableType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
