/*
 * Decompiled with CFR 0.152.
 */
package org.aspectj.weaver;

import java.util.ArrayList;
import java.util.Collections;
import org.aspectj.bridge.ISourceLocation;
import org.aspectj.util.GenericSignature;
import org.aspectj.util.GenericSignatureParser;
import org.aspectj.weaver.AjAttribute;
import org.aspectj.weaver.BCException;
import org.aspectj.weaver.ISourceContext;
import org.aspectj.weaver.ReferenceType;
import org.aspectj.weaver.ReferenceTypeDelegate;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.SourceContextImpl;

public abstract class AbstractReferenceTypeDelegate
implements ReferenceTypeDelegate {
    private String sourcefilename = "<Unknown>";
    private ISourceContext sourceContext = SourceContextImpl.UNKNOWN_SOURCE_CONTEXT;
    protected boolean exposedToWeaver;
    protected ReferenceType resolvedTypeX;
    protected GenericSignature.ClassSignature cachedGenericClassTypeSignature;
    public static final String UNKNOWN_SOURCE_FILE = "<Unknown>";

    public AbstractReferenceTypeDelegate(ReferenceType resolvedTypeX, boolean exposedToWeaver) {
        this.resolvedTypeX = resolvedTypeX;
        this.exposedToWeaver = exposedToWeaver;
    }

    @Override
    public final boolean isClass() {
        return !this.isAspect() && !this.isInterface();
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public boolean doesNotExposeShadowMungers() {
        return false;
    }

    @Override
    public boolean isExposedToWeaver() {
        return this.exposedToWeaver;
    }

    @Override
    public ReferenceType getResolvedTypeX() {
        return this.resolvedTypeX;
    }

    @Override
    public final String getSourcefilename() {
        return this.sourcefilename;
    }

    public final void setSourcefilename(String sourceFileName) {
        this.sourcefilename = sourceFileName;
        if (sourceFileName != null && sourceFileName.equals(UNKNOWN_SOURCE_FILE)) {
            this.sourcefilename = "Type '" + this.getResolvedTypeX().getName() + "' (no debug info available)";
        } else {
            String pname = this.getResolvedTypeX().getPackageName();
            if (pname != null) {
                this.sourcefilename = pname.replace('.', '/') + '/' + sourceFileName;
            }
        }
        if (this.sourcefilename != null && this.sourceContext instanceof SourceContextImpl) {
            ((SourceContextImpl)this.sourceContext).setSourceFileName(this.sourcefilename);
        }
    }

    public ISourceLocation getSourceLocation() {
        return this.getSourceContext().makeSourceLocation(0, 0);
    }

    @Override
    public ISourceContext getSourceContext() {
        return this.sourceContext;
    }

    public void setSourceContext(ISourceContext isc) {
        this.sourceContext = isc;
    }

    public GenericSignature.ClassSignature getGenericClassTypeSignature() {
        String sig;
        if (this.cachedGenericClassTypeSignature == null && (sig = this.getDeclaredGenericSignature()) != null) {
            GenericSignatureParser parser = new GenericSignatureParser();
            this.cachedGenericClassTypeSignature = parser.parseAsClassSignature(sig);
        }
        return this.cachedGenericClassTypeSignature;
    }

    protected GenericSignature.FormalTypeParameter[] getFormalTypeParametersFromOuterClass() {
        GenericSignature.ClassSignature outerSig;
        ArrayList typeParameters = new ArrayList();
        ResolvedType outerClassType = this.getOuterClass();
        if (!(outerClassType instanceof ReferenceType)) {
            if (outerClassType == null) {
                return GenericSignature.FormalTypeParameter.NONE;
            }
            if (System.getProperty("aspectj.debug565713", "false").toLowerCase().equals("true")) {
                System.out.println("DEBUG 565713: Whilst processing type '" + this.resolvedTypeX.getSignature() + "' - cannot cast the outer type to a reference type.  Signature=" + outerClassType.getSignature() + " toString()=" + outerClassType.toString() + " class=" + outerClassType.getClassName());
                return GenericSignature.FormalTypeParameter.NONE;
            }
            throw new BCException("Whilst processing type '" + this.resolvedTypeX.getSignature() + "' - cannot cast the outer type to a reference type.  Signature=" + outerClassType.getSignature() + " toString()=" + outerClassType.toString() + " class=" + outerClassType.getClassName());
        }
        ReferenceType outer = (ReferenceType)outerClassType;
        ReferenceTypeDelegate outerDelegate = outer.getDelegate();
        AbstractReferenceTypeDelegate outerObjectType = (AbstractReferenceTypeDelegate)outerDelegate;
        if (outerObjectType.isNested()) {
            GenericSignature.FormalTypeParameter[] parentParams = outerObjectType.getFormalTypeParametersFromOuterClass();
            Collections.addAll(typeParameters, parentParams);
        }
        if ((outerSig = outerObjectType.getGenericClassTypeSignature()) != null) {
            Collections.addAll(typeParameters, outerSig.formalTypeParameters);
        }
        GenericSignature.FormalTypeParameter[] ret = new GenericSignature.FormalTypeParameter[typeParameters.size()];
        typeParameters.toArray(ret);
        return ret;
    }

    @Override
    public boolean copySourceContext() {
        return true;
    }

    @Override
    public int getCompilerVersion() {
        return AjAttribute.WeaverVersionInfo.getCurrentWeaverMajorVersion();
    }

    @Override
    public void ensureConsistent() {
    }

    @Override
    public boolean isWeavable() {
        return false;
    }

    @Override
    public boolean hasBeenWoven() {
        return false;
    }
}

