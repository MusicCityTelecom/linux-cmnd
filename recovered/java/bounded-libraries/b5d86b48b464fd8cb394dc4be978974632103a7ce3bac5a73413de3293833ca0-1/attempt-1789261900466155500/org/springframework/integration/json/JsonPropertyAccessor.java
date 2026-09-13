/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  org.springframework.expression.AccessException
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.PropertyAccessor
 *  org.springframework.expression.TypedValue
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.integration.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Iterator;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class JsonPropertyAccessor
implements PropertyAccessor {
    private static final Class<?>[] SUPPORTED_CLASSES = new Class[]{String.class, JsonNodeWrapper.class, JsonNode.class};
    private ObjectMapper objectMapper = new ObjectMapper();

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull((Object)objectMapper, (String)"'objectMapper' cannot be null");
        this.objectMapper = objectMapper;
    }

    public Class<?>[] getSpecificTargetClasses() {
        return SUPPORTED_CLASSES;
    }

    public boolean canRead(EvaluationContext context, Object target, String name) throws AccessException {
        JsonNode node;
        try {
            node = this.asJson(target);
        }
        catch (AccessException e) {
            return false;
        }
        Integer index = JsonPropertyAccessor.maybeIndex(name);
        if (node instanceof ArrayNode) {
            return index != null;
        }
        return true;
    }

    private JsonNode asJson(Object target) throws AccessException {
        if (target instanceof JsonNode) {
            return (JsonNode)target;
        }
        if (target instanceof JsonNodeWrapper) {
            return ((JsonNodeWrapper)target).getRealNode();
        }
        if (target instanceof String) {
            try {
                return this.objectMapper.readTree((String)target);
            }
            catch (JsonProcessingException e) {
                throw new AccessException("Exception while trying to deserialize String", (Exception)((Object)e));
            }
        }
        throw new IllegalStateException("Can't happen. Check SUPPORTED_CLASSES");
    }

    private static Integer maybeIndex(String name) {
        try {
            return Integer.valueOf(name);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public TypedValue read(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
        JsonNode node = this.asJson(target);
        Integer index = JsonPropertyAccessor.maybeIndex(name);
        if (index != null && node.has(index.intValue())) {
            return JsonPropertyAccessor.typedValue(node.get(index.intValue()));
        }
        return JsonPropertyAccessor.typedValue(node.get(name));
    }

    public boolean canWrite(EvaluationContext context, Object target, String name) {
        return false;
    }

    public void write(EvaluationContext context, Object target, String name, Object newValue) {
        throw new UnsupportedOperationException("Write is not supported");
    }

    private static TypedValue typedValue(JsonNode json) throws AccessException {
        if (json == null) {
            return TypedValue.NULL;
        }
        if (json.isValueNode()) {
            return new TypedValue(JsonPropertyAccessor.getValue(json));
        }
        return new TypedValue(JsonPropertyAccessor.wrap(json));
    }

    private static Object getValue(JsonNode json) throws AccessException {
        if (json.isTextual()) {
            return json.textValue();
        }
        if (json.isNumber()) {
            return json.numberValue();
        }
        if (json.isBoolean()) {
            return json.asBoolean();
        }
        if (json.isNull()) {
            return null;
        }
        if (json.isBinary()) {
            try {
                return json.binaryValue();
            }
            catch (IOException e) {
                throw new AccessException("Can not get content of binary value: " + json, (Exception)e);
            }
        }
        throw new IllegalArgumentException("Json is not ValueNode.");
    }

    public static Object wrap(JsonNode json) throws AccessException {
        if (json == null) {
            return null;
        }
        if (json instanceof ArrayNode) {
            return new ArrayNodeAsList((ArrayNode)json);
        }
        if (json.isValueNode()) {
            return JsonPropertyAccessor.getValue(json);
        }
        return new ComparableJsonNode(json);
    }

    static interface JsonNodeWrapper<T>
    extends Comparable<T> {
        public String toString();

        public JsonNode getRealNode();
    }

    static class ArrayNodeAsList
    extends AbstractList<Object>
    implements JsonNodeWrapper<Object> {
        private final ArrayNode delegate;

        ArrayNodeAsList(ArrayNode node) {
            this.delegate = node;
        }

        @Override
        public JsonNode getRealNode() {
            return this.delegate;
        }

        @Override
        public String toString() {
            return this.delegate.toString();
        }

        @Override
        public Object get(int index) {
            int i = index < 0 ? this.delegate.size() + index : index;
            try {
                return JsonPropertyAccessor.wrap(this.delegate.get(i));
            }
            catch (AccessException ex) {
                throw new IllegalArgumentException(ex);
            }
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public Iterator<Object> iterator() {
            return new Iterator<Object>(){
                private final Iterator<JsonNode> it;
                {
                    this.it = delegate.iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.it.hasNext();
                }

                @Override
                public Object next() {
                    try {
                        return JsonPropertyAccessor.wrap(this.it.next());
                    }
                    catch (AccessException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            };
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof JsonNodeWrapper) {
                return this.delegate.equals((Object)((JsonNodeWrapper)o).getRealNode()) ? 0 : 1;
            }
            return this.delegate.equals(o) ? 0 : 1;
        }
    }

    static class ComparableJsonNode
    implements JsonNodeWrapper<ComparableJsonNode> {
        private final JsonNode delegate;

        ComparableJsonNode(JsonNode delegate) {
            this.delegate = delegate;
        }

        @Override
        public JsonNode getRealNode() {
            return this.delegate;
        }

        @Override
        public String toString() {
            return this.delegate.toString();
        }

        @Override
        public int compareTo(ComparableJsonNode o) {
            return this.delegate.equals((Object)o.delegate) ? 0 : 1;
        }
    }
}

