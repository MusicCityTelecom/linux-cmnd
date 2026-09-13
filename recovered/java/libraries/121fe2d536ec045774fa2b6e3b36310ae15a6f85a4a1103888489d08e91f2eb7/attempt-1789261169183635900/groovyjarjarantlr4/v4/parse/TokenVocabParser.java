/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.parse;

import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenVocabParser {
    protected final Grammar g;

    public TokenVocabParser(Grammar g) {
        this.g = g;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public Map<String, Integer> load() {
        LinkedHashMap<String, Integer> tokens;
        block25: {
            tokens = new LinkedHashMap<String, Integer>();
            int maxTokenType = -1;
            File fullFile = this.getImportedVocabFile();
            FileInputStream fis = null;
            BufferedReader br = null;
            Tool tool = this.g.tool;
            String vocabName = this.g.getOptionString("tokenVocab");
            Pattern tokenDefPattern = Pattern.compile("([^\n]+?)[ \\t]*?=[ \\t]*?([0-9]+)");
            fis = new FileInputStream(fullFile);
            InputStreamReader isr = tool.grammarEncoding != null ? new InputStreamReader((InputStream)fis, tool.grammarEncoding) : new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String tokenDef = br.readLine();
            int lineNum = 1;
            while (tokenDef != null) {
                Matcher matcher = tokenDefPattern.matcher(tokenDef);
                if (matcher.find()) {
                    int tokenType;
                    String tokenID = matcher.group(1);
                    String tokenTypeS = matcher.group(2);
                    try {
                        tokenType = Integer.valueOf(tokenTypeS);
                    }
                    catch (NumberFormatException nfe) {
                        tool.errMgr.toolError(ErrorType.TOKENS_FILE_SYNTAX_ERROR, vocabName + ".tokens", " bad token type: " + tokenTypeS, lineNum);
                        tokenType = 0;
                    }
                    tool.log("grammar", "import " + tokenID + "=" + tokenType);
                    tokens.put(tokenID, tokenType);
                    maxTokenType = Math.max(maxTokenType, tokenType);
                    ++lineNum;
                } else if (tokenDef.length() > 0) {
                    tool.errMgr.toolError(ErrorType.TOKENS_FILE_SYNTAX_ERROR, vocabName + ".tokens", " bad token def: " + tokenDef, lineNum);
                }
                tokenDef = br.readLine();
            }
            try {
                if (br != null) {
                    br.close();
                }
                break block25;
            }
            catch (IOException ioe) {
                tool.errMgr.toolError(ErrorType.ERROR_READING_TOKENS_FILE, ioe, fullFile, ioe.getMessage());
            }
            break block25;
            catch (FileNotFoundException fnfe) {
                block26: {
                    GrammarAST inTree = this.g.ast.getOptionAST("tokenVocab");
                    String inTreeValue = inTree.getToken().getText();
                    if (vocabName.equals(inTreeValue)) {
                        tool.errMgr.grammarError(ErrorType.CANNOT_FIND_TOKENS_FILE_REFD_IN_GRAMMAR, this.g.fileName, inTree.getToken(), fullFile);
                        break block26;
                    }
                    tool.errMgr.toolError(ErrorType.CANNOT_FIND_TOKENS_FILE_GIVEN_ON_CMDLINE, fullFile, this.g.name);
                }
                try {
                    if (br != null) {
                        br.close();
                    }
                }
                catch (IOException ioe) {
                    tool.errMgr.toolError(ErrorType.ERROR_READING_TOKENS_FILE, ioe, fullFile, ioe.getMessage());
                }
            }
            catch (Exception e) {
                tool.errMgr.toolError(ErrorType.ERROR_READING_TOKENS_FILE, e, fullFile, e.getMessage());
                {
                    catch (Throwable throwable) {
                        try {
                            if (br != null) {
                                br.close();
                            }
                        }
                        catch (IOException ioe) {
                            tool.errMgr.toolError(ErrorType.ERROR_READING_TOKENS_FILE, ioe, fullFile, ioe.getMessage());
                        }
                        throw throwable;
                    }
                }
                try {
                    if (br != null) {
                        br.close();
                    }
                }
                catch (IOException ioe) {
                    tool.errMgr.toolError(ErrorType.ERROR_READING_TOKENS_FILE, ioe, fullFile, ioe.getMessage());
                }
            }
        }
        return tokens;
    }

    public File getImportedVocabFile() {
        String vocabName = this.g.getOptionString("tokenVocab");
        File f = new File(this.g.tool.libDirectory, File.separator + vocabName + ".tokens");
        if (f.exists()) {
            return f;
        }
        f = new File(this.g.tool.outputDirectory, vocabName + ".tokens");
        if (f.exists()) {
            return f;
        }
        String fileDirectory = this.g.fileName.lastIndexOf(File.separatorChar) == -1 ? "." : this.g.fileName.substring(0, this.g.fileName.lastIndexOf(File.separatorChar));
        return new File(fileDirectory, vocabName + ".tokens");
    }
}

