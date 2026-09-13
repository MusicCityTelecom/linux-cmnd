/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.tool.ErrorType;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class OutputModelWalker {
    Tool tool;
    STGroup templates;

    public OutputModelWalker(Tool tool, STGroup templates) {
        this.tool = tool;
        this.templates = templates;
    }

    public ST walk(OutputModelObject omo, boolean header) {
        Field[] fields;
        ST st;
        Class<?> cl = omo.getClass();
        String templateName = cl.getSimpleName();
        if (templateName == null) {
            this.tool.errMgr.toolError(ErrorType.NO_MODEL_TO_TEMPLATE_MAPPING, cl.getSimpleName());
            return new ST("[" + templateName + " invalid]");
        }
        if (header) {
            templateName = templateName + "Header";
        }
        if ((st = this.templates.getInstanceOf(templateName)) == null) {
            this.tool.errMgr.toolError(ErrorType.CODE_GEN_TEMPLATES_INCOMPLETE, templateName);
            return new ST("[" + templateName + " invalid]");
        }
        if (st.impl.formalArguments == null) {
            this.tool.errMgr.toolError(ErrorType.CODE_TEMPLATE_ARG_ISSUE, templateName, "<none>");
            return st;
        }
        Map formalArgs = st.impl.formalArguments;
        Set argNames = formalArgs.keySet();
        Iterator arg_it = argNames.iterator();
        String modelArgName = (String)arg_it.next();
        st.add(modelArgName, (Object)omo);
        HashSet<String> usedFieldNames = new HashSet<String>();
        for (Field fi : fields = cl.getFields()) {
            ModelElement annotation = fi.getAnnotation(ModelElement.class);
            if (annotation == null) continue;
            String fieldName = fi.getName();
            if (!usedFieldNames.add(fieldName)) {
                this.tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, "Model object " + omo.getClass().getSimpleName() + " has multiple fields named '" + fieldName + "'");
                continue;
            }
            if (formalArgs.get(fieldName) == null) continue;
            try {
                List<OutputModelObject> o = fi.get(omo);
                if (o instanceof OutputModelObject) {
                    OutputModelObject nestedOmo = (OutputModelObject)((Object)o);
                    ST nestedST = this.walk(nestedOmo, header);
                    st.add(fieldName, (Object)nestedST);
                    continue;
                }
                if (o instanceof Collection || o instanceof OutputModelObject[]) {
                    if (o instanceof OutputModelObject[]) {
                        o = Arrays.asList((OutputModelObject[])o);
                    }
                    Collection nestedOmos = o;
                    for (Object nestedOmo : nestedOmos) {
                        if (nestedOmo == null) continue;
                        ST nestedST = this.walk((OutputModelObject)nestedOmo, header);
                        st.add(fieldName, (Object)nestedST);
                    }
                    continue;
                }
                if (o instanceof Map) {
                    Map nestedOmoMap = (Map)((Object)o);
                    LinkedHashMap m = new LinkedHashMap();
                    for (Map.Entry entry : nestedOmoMap.entrySet()) {
                        ST nestedST = this.walk((OutputModelObject)entry.getValue(), header);
                        m.put(entry.getKey(), nestedST);
                    }
                    st.add(fieldName, m);
                    continue;
                }
                if (o == null) continue;
                this.tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, "not recognized nested model element: " + fieldName);
            }
            catch (IllegalAccessException iae) {
                this.tool.errMgr.toolError(ErrorType.CODE_TEMPLATE_ARG_ISSUE, templateName, fieldName);
            }
        }
        return st;
    }
}

