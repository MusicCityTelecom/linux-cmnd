/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.ajax;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.eclipse.jetty.util.DateCache;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class JSONDateConvertor
implements JSON.Convertor {
    private static final Logger LOG = Log.getLogger(JSONDateConvertor.class);
    private final boolean _fromJSON;
    private final DateCache _dateCache;
    private final SimpleDateFormat _format;

    public JSONDateConvertor() {
        this(false);
    }

    public JSONDateConvertor(boolean fromJSON) {
        this("EEE MMM dd HH:mm:ss zzz yyyy", TimeZone.getTimeZone("GMT"), fromJSON);
    }

    public JSONDateConvertor(String format, TimeZone zone, boolean fromJSON) {
        this._dateCache = new DateCache(format, null, zone);
        this._fromJSON = fromJSON;
        this._format = new SimpleDateFormat(format);
        this._format.setTimeZone(zone);
    }

    public JSONDateConvertor(String format, TimeZone zone, boolean fromJSON, Locale locale) {
        this._dateCache = new DateCache(format, locale, zone);
        this._fromJSON = fromJSON;
        this._format = new SimpleDateFormat(format, new DateFormatSymbols(locale));
        this._format.setTimeZone(zone);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object fromJSON(Map map) {
        if (!this._fromJSON) {
            throw new UnsupportedOperationException();
        }
        try {
            SimpleDateFormat simpleDateFormat = this._format;
            synchronized (simpleDateFormat) {
                return this._format.parseObject((String)map.get("value"));
            }
        }
        catch (Exception e) {
            LOG.warn(e);
            return null;
        }
    }

    @Override
    public void toJSON(Object obj, JSON.Output out) {
        String date = this._dateCache.format((Date)obj);
        if (this._fromJSON) {
            out.addClass(obj.getClass());
            out.add("value", date);
        } else {
            out.add(date);
        }
    }
}

