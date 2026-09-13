/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.tool.ANTLRMessage;
import groovyjarjarantlr4.v4.tool.ANTLRToolListener;
import org.stringtemplate.v4.ST;

public class DefaultToolListener
implements ANTLRToolListener {
    public Tool tool;

    public DefaultToolListener(Tool tool) {
        this.tool = tool;
    }

    @Override
    public void info(String msg) {
        if (this.tool.errMgr.formatWantsSingleLineMessage()) {
            msg = msg.replace('\n', ' ');
        }
        System.out.println(msg);
    }

    @Override
    public void error(ANTLRMessage msg) {
        ST msgST = this.tool.errMgr.getMessageTemplate(msg);
        String outputMsg = msgST.render();
        if (this.tool.errMgr.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replace('\n', ' ');
        }
        System.err.println(outputMsg);
    }

    @Override
    public void warning(ANTLRMessage msg) {
        ST msgST = this.tool.errMgr.getMessageTemplate(msg);
        String outputMsg = msgST.render();
        if (this.tool.errMgr.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replace('\n', ' ');
        }
        System.err.println(outputMsg);
    }
}

