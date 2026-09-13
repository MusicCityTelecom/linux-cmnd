/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.io.IOException;

public interface CharStream {
    public char readChar() throws IOException;

    public int getBeginColumn();

    public int getBeginLine();

    public int getEndColumn();

    public int getEndLine();

    public void backup(int var1);

    public char beginToken() throws IOException;

    public String getImage();

    public char[] getSuffix(int var1);

    public void done();

    public int getTabSize();

    public void setTabSize(int var1);

    public boolean isTrackLineColumn();

    public void setTrackLineColumn(boolean var1);
}

