/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Interceptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TracingInterceptor
implements Interceptor {
    protected Writer writer = new PrintWriter(System.out);
    private int indent = 0;

    public Writer getWriter() {
        return this.writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public Object beforeInvoke(Object object, String methodName, Object[] arguments) {
        this.write(object, methodName, arguments, "before");
        ++this.indent;
        return null;
    }

    @Override
    public Object afterInvoke(Object object, String methodName, Object[] arguments, Object result) {
        --this.indent;
        this.write(object, methodName, arguments, "after ");
        return result;
    }

    @Override
    public boolean doInvoke() {
        return true;
    }

    private String indent() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.indent; ++i) {
            result.append("  ");
        }
        return result.toString();
    }

    protected void write(Object object, String methodName, Object[] arguments, String origin) {
        try {
            this.writer.write(this.indent());
            this.writer.write(origin);
            this.writer.write(" ");
            Class<?> theClass = object instanceof Class ? (Class<?>)object : object.getClass();
            this.writeInfo(theClass, methodName, arguments);
            this.writer.write("\n");
            this.writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeInfo(Class aClass, String methodName, Object[] arguments) throws IOException {
        String argumentTypes = Stream.of(arguments).map(arg -> arg != null ? arg.getClass().getName() : "java.lang.Object").collect(Collectors.joining(", "));
        StringBuilder result = new StringBuilder(aClass.getName());
        result.append('.').append(methodName).append('(');
        result.append(argumentTypes).append(')');
        this.writer.write(result.toString());
    }
}

