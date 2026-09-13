/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.web.cookie;

import java.io.Serializable;
import lombok.Generated;

public class CookieGenerationContext
implements Serializable {
    public static final CookieGenerationContext EMPTY = CookieGenerationContext.builder().build();
    private static final long serialVersionUID = -3058351444389458036L;
    private static final int DEFAULT_REMEMBER_ME_MAX_AGE = 7889231;
    private String name;
    private String comment;
    private String path;
    private int maxAge;
    private boolean secure;
    private String domain;
    private int rememberMeMaxAge;
    private boolean httpOnly;
    private String sameSitePolicy;

    @Generated
    private static String $default$comment() {
        return "CAS Cookie";
    }

    @Generated
    private static String $default$path() {
        return "";
    }

    @Generated
    private static int $default$maxAge() {
        return -1;
    }

    @Generated
    private static boolean $default$secure() {
        return true;
    }

    @Generated
    private static String $default$domain() {
        return "";
    }

    @Generated
    private static int $default$rememberMeMaxAge() {
        return 7889231;
    }

    @Generated
    private static boolean $default$httpOnly() {
        return true;
    }

    @Generated
    private static String $default$sameSitePolicy() {
        return "";
    }

    @Generated
    protected CookieGenerationContext(CookieGenerationContextBuilder<?, ?> b) {
        this.name = b.name;
        this.comment = b.comment$set ? b.comment$value : CookieGenerationContext.$default$comment();
        this.path = b.path$set ? b.path$value : CookieGenerationContext.$default$path();
        this.maxAge = b.maxAge$set ? b.maxAge$value : CookieGenerationContext.$default$maxAge();
        this.secure = b.secure$set ? b.secure$value : CookieGenerationContext.$default$secure();
        this.domain = b.domain$set ? b.domain$value : CookieGenerationContext.$default$domain();
        this.rememberMeMaxAge = b.rememberMeMaxAge$set ? b.rememberMeMaxAge$value : CookieGenerationContext.$default$rememberMeMaxAge();
        this.httpOnly = b.httpOnly$set ? b.httpOnly$value : CookieGenerationContext.$default$httpOnly();
        this.sameSitePolicy = b.sameSitePolicy$set ? b.sameSitePolicy$value : CookieGenerationContext.$default$sameSitePolicy();
    }

    @Generated
    public static CookieGenerationContextBuilder<?, ?> builder() {
        return new CookieGenerationContextBuilderImpl();
    }

    @Generated
    public String toString() {
        return "CookieGenerationContext(name=" + this.name + ", comment=" + this.comment + ", path=" + this.path + ", maxAge=" + this.maxAge + ", secure=" + this.secure + ", domain=" + this.domain + ", rememberMeMaxAge=" + this.rememberMeMaxAge + ", httpOnly=" + this.httpOnly + ", sameSitePolicy=" + this.sameSitePolicy + ")";
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getComment() {
        return this.comment;
    }

    @Generated
    public String getPath() {
        return this.path;
    }

    @Generated
    public int getMaxAge() {
        return this.maxAge;
    }

    @Generated
    public boolean isSecure() {
        return this.secure;
    }

    @Generated
    public String getDomain() {
        return this.domain;
    }

    @Generated
    public int getRememberMeMaxAge() {
        return this.rememberMeMaxAge;
    }

    @Generated
    public boolean isHttpOnly() {
        return this.httpOnly;
    }

    @Generated
    public String getSameSitePolicy() {
        return this.sameSitePolicy;
    }

    @Generated
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Generated
    public void setPath(String path) {
        this.path = path;
    }

    @Generated
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Generated
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    @Generated
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Generated
    public void setRememberMeMaxAge(int rememberMeMaxAge) {
        this.rememberMeMaxAge = rememberMeMaxAge;
    }

    @Generated
    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    @Generated
    public void setSameSitePolicy(String sameSitePolicy) {
        this.sameSitePolicy = sameSitePolicy;
    }

    @Generated
    public void setName(String name) {
        this.name = name;
    }

    @Generated
    private static final class CookieGenerationContextBuilderImpl
    extends CookieGenerationContextBuilder<CookieGenerationContext, CookieGenerationContextBuilderImpl> {
        @Generated
        private CookieGenerationContextBuilderImpl() {
        }

        @Override
        @Generated
        protected CookieGenerationContextBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public CookieGenerationContext build() {
            return new CookieGenerationContext(this);
        }
    }

    @Generated
    public static abstract class CookieGenerationContextBuilder<C extends CookieGenerationContext, B extends CookieGenerationContextBuilder<C, B>> {
        @Generated
        private String name;
        @Generated
        private boolean comment$set;
        @Generated
        private String comment$value;
        @Generated
        private boolean path$set;
        @Generated
        private String path$value;
        @Generated
        private boolean maxAge$set;
        @Generated
        private int maxAge$value;
        @Generated
        private boolean secure$set;
        @Generated
        private boolean secure$value;
        @Generated
        private boolean domain$set;
        @Generated
        private String domain$value;
        @Generated
        private boolean rememberMeMaxAge$set;
        @Generated
        private int rememberMeMaxAge$value;
        @Generated
        private boolean httpOnly$set;
        @Generated
        private boolean httpOnly$value;
        @Generated
        private boolean sameSitePolicy$set;
        @Generated
        private String sameSitePolicy$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B name(String name) {
            this.name = name;
            return this.self();
        }

        @Generated
        public B comment(String comment) {
            this.comment$value = comment;
            this.comment$set = true;
            return this.self();
        }

        @Generated
        public B path(String path) {
            this.path$value = path;
            this.path$set = true;
            return this.self();
        }

        @Generated
        public B maxAge(int maxAge) {
            this.maxAge$value = maxAge;
            this.maxAge$set = true;
            return this.self();
        }

        @Generated
        public B secure(boolean secure) {
            this.secure$value = secure;
            this.secure$set = true;
            return this.self();
        }

        @Generated
        public B domain(String domain) {
            this.domain$value = domain;
            this.domain$set = true;
            return this.self();
        }

        @Generated
        public B rememberMeMaxAge(int rememberMeMaxAge) {
            this.rememberMeMaxAge$value = rememberMeMaxAge;
            this.rememberMeMaxAge$set = true;
            return this.self();
        }

        @Generated
        public B httpOnly(boolean httpOnly) {
            this.httpOnly$value = httpOnly;
            this.httpOnly$set = true;
            return this.self();
        }

        @Generated
        public B sameSitePolicy(String sameSitePolicy) {
            this.sameSitePolicy$value = sameSitePolicy;
            this.sameSitePolicy$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "CookieGenerationContext.CookieGenerationContextBuilder(name=" + this.name + ", comment$value=" + this.comment$value + ", path$value=" + this.path$value + ", maxAge$value=" + this.maxAge$value + ", secure$value=" + this.secure$value + ", domain$value=" + this.domain$value + ", rememberMeMaxAge$value=" + this.rememberMeMaxAge$value + ", httpOnly$value=" + this.httpOnly$value + ", sameSitePolicy$value=" + this.sameSitePolicy$value + ")";
        }
    }
}

