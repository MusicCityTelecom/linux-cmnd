/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.xml;

import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.google.common.collect.ImmutableMap;
import java.util.StringJoiner;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XPathResolver {
    public static XPathResult resolve(Node node, XPathExpression xPathExpression) {
        try {
            NodeList nodeList = (NodeList)xPathExpression.evaluate(node, XPathConstants.NODESET);
            return new XPathResult(nodeList);
        }
        catch (XPathExpressionException e2) {
            throw new ValidationException("Error evaluating the XPath expression.", e2);
        }
    }

    public static final class XPathResult {
        private static final ImmutableMap<Short, String> TYPE_VALUE_TO_NAME = ImmutableMap.builder().put((short)2, "attribute").put((short)4, "CDATA section").put((short)8, "comment").put((short)11, "document fragment").put((short)9, "document").put((short)10, "document type").put((short)1, "element").put((short)6, "entity").put((short)5, "entity reference").put((short)12, "notation").put((short)7, "processing instruction").put((short)3, "text").build();
        private final NodeList nodeList;

        private XPathResult(NodeList nodeList) {
            this.nodeList = nodeList;
        }

        public String toString() {
            StringJoiner output = new StringJoiner(System.lineSeparator());
            block3: for (int i2 = 0; i2 < this.nodeList.getLength(); ++i2) {
                Node node = this.nodeList.item(i2);
                switch (node.getNodeType()) {
                    case 2: {
                        output.add(((Attr)node).getValue());
                        continue block3;
                    }
                    default: {
                        throw new UnsupportedOperationException("Unsupported XPath expression: cannot extract nodes of type: " + TYPE_VALUE_TO_NAME.getOrDefault(node.getNodeType(), "<unrecognized>"));
                    }
                }
            }
            return output.toString();
        }
    }
}

