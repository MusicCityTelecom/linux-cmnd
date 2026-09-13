/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.soap.mychoice;

import com.tpvision.smartinstall.soap.mychoice.Array;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ArrayOfString")
public class ArrayOfString
extends Array {
    public List<String> getStringList() {
        ArrayList<String> stringList = new ArrayList<String>();
        for (Object obj : this.getAny()) {
            stringList.add(String.valueOf(((Element)obj).getChildNodes().item(0).getNodeValue()));
        }
        return stringList;
    }
}

