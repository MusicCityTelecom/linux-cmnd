/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.security.web.savedrequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.security.web.savedrequest.FastHttpDateFormat;
import org.springframework.security.web.savedrequest.SavedRequest;

class SavedRequestAwareWrapper
extends HttpServletRequestWrapper {
    protected static final Log logger = LogFactory.getLog(SavedRequestAwareWrapper.class);
    protected static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");
    protected static Locale defaultLocale = Locale.getDefault();
    protected SavedRequest savedRequest = null;
    protected final SimpleDateFormat[] formats = new SimpleDateFormat[3];

    SavedRequestAwareWrapper(SavedRequest saved, HttpServletRequest request) {
        super(request);
        this.savedRequest = saved;
        this.formats[0] = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        this.formats[1] = new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US);
        this.formats[2] = new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US);
        this.formats[0].setTimeZone(GMT_ZONE);
        this.formats[1].setTimeZone(GMT_ZONE);
        this.formats[2].setTimeZone(GMT_ZONE);
    }

    public long getDateHeader(String name) {
        String value = this.getHeader(name);
        if (value == null) {
            return -1L;
        }
        long result = FastHttpDateFormat.parseDate(value, this.formats);
        if (result != -1L) {
            return result;
        }
        throw new IllegalArgumentException(value);
    }

    public String getHeader(String name) {
        List<String> values = this.savedRequest.getHeaderValues(name);
        return values.isEmpty() ? null : values.get(0);
    }

    public Enumeration getHeaderNames() {
        return new Enumerator<String>(this.savedRequest.getHeaderNames());
    }

    public Enumeration getHeaders(String name) {
        return new Enumerator<String>(this.savedRequest.getHeaderValues(name));
    }

    public int getIntHeader(String name) {
        String value = this.getHeader(name);
        return value != null ? Integer.parseInt(value) : -1;
    }

    public Locale getLocale() {
        List<Locale> locales = this.savedRequest.getLocales();
        return locales.isEmpty() ? Locale.getDefault() : locales.get(0);
    }

    public Enumeration getLocales() {
        List<Locale> locales = this.savedRequest.getLocales();
        if (locales.isEmpty()) {
            locales = new ArrayList<Locale>(1);
            locales.add(Locale.getDefault());
        }
        return new Enumerator<Locale>(locales);
    }

    public String getMethod() {
        return this.savedRequest.getMethod();
    }

    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value != null) {
            return value;
        }
        String[] values = this.savedRequest.getParameterValues(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    public Map getParameterMap() {
        Set<String> names = this.getCombinedParameterNames();
        HashMap<String, String[]> parameterMap = new HashMap<String, String[]>(names.size());
        for (String name : names) {
            parameterMap.put(name, this.getParameterValues(name));
        }
        return parameterMap;
    }

    private Set<String> getCombinedParameterNames() {
        HashSet<String> names = new HashSet<String>();
        names.addAll(super.getParameterMap().keySet());
        names.addAll(this.savedRequest.getParameterMap().keySet());
        return names;
    }

    public Enumeration getParameterNames() {
        return new Enumerator<String>(this.getCombinedParameterNames());
    }

    public String[] getParameterValues(String name) {
        String[] savedRequestParams = this.savedRequest.getParameterValues(name);
        String[] wrappedRequestParams = super.getParameterValues(name);
        if (savedRequestParams == null) {
            return wrappedRequestParams;
        }
        if (wrappedRequestParams == null) {
            return savedRequestParams;
        }
        List<String> wrappedParamsList = Arrays.asList(wrappedRequestParams);
        ArrayList<String> combinedParams = new ArrayList<String>(wrappedParamsList);
        for (String savedRequestParam : savedRequestParams) {
            if (wrappedParamsList.contains(savedRequestParam)) continue;
            combinedParams.add(savedRequestParam);
        }
        return combinedParams.toArray(new String[0]);
    }
}

