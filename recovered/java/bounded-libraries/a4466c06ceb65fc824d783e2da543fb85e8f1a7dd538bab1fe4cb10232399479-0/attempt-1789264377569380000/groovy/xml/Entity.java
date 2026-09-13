/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Buildable
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 */
package groovy.xml;

import groovy.lang.Buildable;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;

public class Entity
implements Buildable,
GroovyObject {
    public static final Entity nbsp;
    public static final Entity iexcl;
    public static final Entity cent;
    public static final Entity pound;
    public static final Entity curren;
    public static final Entity yen;
    public static final Entity brvbar;
    public static final Entity sect;
    public static final Entity uml;
    public static final Entity copy;
    public static final Entity ordf;
    public static final Entity laquo;
    public static final Entity not;
    public static final Entity shy;
    public static final Entity reg;
    public static final Entity macr;
    public static final Entity deg;
    public static final Entity plusmn;
    public static final Entity sup2;
    public static final Entity sup3;
    public static final Entity acute;
    public static final Entity micro;
    public static final Entity para;
    public static final Entity middot;
    public static final Entity cedil;
    public static final Entity sup1;
    public static final Entity ordm;
    public static final Entity raquo;
    public static final Entity frac14;
    public static final Entity frac12;
    public static final Entity frac34;
    public static final Entity iquest;
    public static final Entity Agrave;
    public static final Entity Aacute;
    public static final Entity Acirc;
    public static final Entity Atilde;
    public static final Entity Auml;
    public static final Entity Aring;
    public static final Entity AElig;
    public static final Entity Ccedil;
    public static final Entity Egrave;
    public static final Entity Eacute;
    public static final Entity Ecirc;
    public static final Entity Euml;
    public static final Entity Igrave;
    public static final Entity Iacute;
    public static final Entity Icirc;
    public static final Entity Iuml;
    public static final Entity ETH;
    public static final Entity Ntilde;
    public static final Entity Ograve;
    public static final Entity Oacute;
    public static final Entity Ocirc;
    public static final Entity Otilde;
    public static final Entity Ouml;
    public static final Entity times;
    public static final Entity Oslash;
    public static final Entity Ugrave;
    public static final Entity Uacute;
    public static final Entity Ucirc;
    public static final Entity Uuml;
    public static final Entity Yacute;
    public static final Entity THORN;
    public static final Entity szlig;
    public static final Entity agrave;
    public static final Entity aacute;
    public static final Entity acirc;
    public static final Entity atilde;
    public static final Entity auml;
    public static final Entity aring;
    public static final Entity aelig;
    public static final Entity ccedil;
    public static final Entity egrave;
    public static final Entity eacute;
    public static final Entity ecirc;
    public static final Entity euml;
    public static final Entity igrave;
    public static final Entity iacute;
    public static final Entity icirc;
    public static final Entity iuml;
    public static final Entity eth;
    public static final Entity ntilde;
    public static final Entity ograve;
    public static final Entity oacute;
    public static final Entity ocirc;
    public static final Entity otilde;
    public static final Entity ouml;
    public static final Entity divide;
    public static final Entity oslash;
    public static final Entity ugrave;
    public static final Entity uacute;
    public static final Entity ucirc;
    public static final Entity uuml;
    public static final Entity yacute;
    public static final Entity thorn;
    public static final Entity yuml;
    public static final Entity lt;
    public static final Entity gt;
    public static final Entity amp;
    public static final Entity apos;
    public static final Entity quot;
    public static final Entity OElig;
    public static final Entity oelig;
    public static final Entity Scaron;
    public static final Entity scaron;
    public static final Entity Yuml;
    public static final Entity circ;
    public static final Entity tilde;
    public static final Entity ensp;
    public static final Entity emsp;
    public static final Entity thinsp;
    public static final Entity zwnj;
    public static final Entity zwj;
    public static final Entity lrm;
    public static final Entity rlm;
    public static final Entity ndash;
    public static final Entity mdash;
    public static final Entity lsquo;
    public static final Entity rsquo;
    public static final Entity sbquo;
    public static final Entity ldquo;
    public static final Entity rdquo;
    public static final Entity bdquo;
    public static final Entity dagger;
    public static final Entity Dagger;
    public static final Entity permil;
    public static final Entity lsaquo;
    public static final Entity rsaquo;
    public static final Entity euro;
    private final Object entity;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public Entity(String name) {
        MetaClass metaClass;
        CallSite[] callSiteArray = Entity.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        GStringImpl gStringImpl = new GStringImpl(new Object[]{name}, new String[]{"&", ";"});
        this.entity = gStringImpl;
    }

    public Entity(int name) {
        MetaClass metaClass;
        CallSite[] callSiteArray = Entity.$getCallSiteArray();
        this.metaClass = metaClass = this.$getStaticMetaClass();
        GStringImpl gStringImpl = new GStringImpl(new Object[]{name}, new String[]{"&#", ";"});
        this.entity = gStringImpl;
    }

    public void build(GroovyObject builder) {
        CallSite[] callSiteArray = Entity.$getCallSiteArray();
        callSiteArray[0].call(callSiteArray[1].callGetProperty((Object)builder), this.entity);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != Entity.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    @Generated
    @Internal
    @Transient
    public MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        this.metaClass = this.$getStaticMetaClass();
        return this.metaClass;
    }

    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    static {
        Object object = Entity.$getCallSiteArray()[2].callConstructor(Entity.class, (Object)"nbsp");
        nbsp = (Entity)ScriptBytecodeAdapter.castToType((Object)object, Entity.class);
        Object object2 = Entity.$getCallSiteArray()[3].callConstructor(Entity.class, (Object)"iexcl");
        iexcl = (Entity)ScriptBytecodeAdapter.castToType((Object)object2, Entity.class);
        Object object3 = Entity.$getCallSiteArray()[4].callConstructor(Entity.class, (Object)"cent");
        cent = (Entity)ScriptBytecodeAdapter.castToType((Object)object3, Entity.class);
        Object object4 = Entity.$getCallSiteArray()[5].callConstructor(Entity.class, (Object)"pound");
        pound = (Entity)ScriptBytecodeAdapter.castToType((Object)object4, Entity.class);
        Object object5 = Entity.$getCallSiteArray()[6].callConstructor(Entity.class, (Object)"curren");
        curren = (Entity)ScriptBytecodeAdapter.castToType((Object)object5, Entity.class);
        Object object6 = Entity.$getCallSiteArray()[7].callConstructor(Entity.class, (Object)"yen");
        yen = (Entity)ScriptBytecodeAdapter.castToType((Object)object6, Entity.class);
        Object object7 = Entity.$getCallSiteArray()[8].callConstructor(Entity.class, (Object)"brvbar");
        brvbar = (Entity)ScriptBytecodeAdapter.castToType((Object)object7, Entity.class);
        Object object8 = Entity.$getCallSiteArray()[9].callConstructor(Entity.class, (Object)"sect");
        sect = (Entity)ScriptBytecodeAdapter.castToType((Object)object8, Entity.class);
        Object object9 = Entity.$getCallSiteArray()[10].callConstructor(Entity.class, (Object)"uml");
        uml = (Entity)ScriptBytecodeAdapter.castToType((Object)object9, Entity.class);
        Object object10 = Entity.$getCallSiteArray()[11].callConstructor(Entity.class, (Object)"copy");
        copy = (Entity)ScriptBytecodeAdapter.castToType((Object)object10, Entity.class);
        Object object11 = Entity.$getCallSiteArray()[12].callConstructor(Entity.class, (Object)"ordf");
        ordf = (Entity)ScriptBytecodeAdapter.castToType((Object)object11, Entity.class);
        Object object12 = Entity.$getCallSiteArray()[13].callConstructor(Entity.class, (Object)"laquo");
        laquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object12, Entity.class);
        Object object13 = Entity.$getCallSiteArray()[14].callConstructor(Entity.class, (Object)"not");
        not = (Entity)ScriptBytecodeAdapter.castToType((Object)object13, Entity.class);
        Object object14 = Entity.$getCallSiteArray()[15].callConstructor(Entity.class, (Object)"shy");
        shy = (Entity)ScriptBytecodeAdapter.castToType((Object)object14, Entity.class);
        Object object15 = Entity.$getCallSiteArray()[16].callConstructor(Entity.class, (Object)"reg");
        reg = (Entity)ScriptBytecodeAdapter.castToType((Object)object15, Entity.class);
        Object object16 = Entity.$getCallSiteArray()[17].callConstructor(Entity.class, (Object)"macr");
        macr = (Entity)ScriptBytecodeAdapter.castToType((Object)object16, Entity.class);
        Object object17 = Entity.$getCallSiteArray()[18].callConstructor(Entity.class, (Object)"deg");
        deg = (Entity)ScriptBytecodeAdapter.castToType((Object)object17, Entity.class);
        Object object18 = Entity.$getCallSiteArray()[19].callConstructor(Entity.class, (Object)"plusmn");
        plusmn = (Entity)ScriptBytecodeAdapter.castToType((Object)object18, Entity.class);
        Object object19 = Entity.$getCallSiteArray()[20].callConstructor(Entity.class, (Object)"sup2");
        sup2 = (Entity)ScriptBytecodeAdapter.castToType((Object)object19, Entity.class);
        Object object20 = Entity.$getCallSiteArray()[21].callConstructor(Entity.class, (Object)"sup3");
        sup3 = (Entity)ScriptBytecodeAdapter.castToType((Object)object20, Entity.class);
        Object object21 = Entity.$getCallSiteArray()[22].callConstructor(Entity.class, (Object)"acute");
        acute = (Entity)ScriptBytecodeAdapter.castToType((Object)object21, Entity.class);
        Object object22 = Entity.$getCallSiteArray()[23].callConstructor(Entity.class, (Object)"micro");
        micro = (Entity)ScriptBytecodeAdapter.castToType((Object)object22, Entity.class);
        Object object23 = Entity.$getCallSiteArray()[24].callConstructor(Entity.class, (Object)"para");
        para = (Entity)ScriptBytecodeAdapter.castToType((Object)object23, Entity.class);
        Object object24 = Entity.$getCallSiteArray()[25].callConstructor(Entity.class, (Object)"middot");
        middot = (Entity)ScriptBytecodeAdapter.castToType((Object)object24, Entity.class);
        Object object25 = Entity.$getCallSiteArray()[26].callConstructor(Entity.class, (Object)"cedil");
        cedil = (Entity)ScriptBytecodeAdapter.castToType((Object)object25, Entity.class);
        Object object26 = Entity.$getCallSiteArray()[27].callConstructor(Entity.class, (Object)"sup1");
        sup1 = (Entity)ScriptBytecodeAdapter.castToType((Object)object26, Entity.class);
        Object object27 = Entity.$getCallSiteArray()[28].callConstructor(Entity.class, (Object)"ordm");
        ordm = (Entity)ScriptBytecodeAdapter.castToType((Object)object27, Entity.class);
        Object object28 = Entity.$getCallSiteArray()[29].callConstructor(Entity.class, (Object)"raquo");
        raquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object28, Entity.class);
        Object object29 = Entity.$getCallSiteArray()[30].callConstructor(Entity.class, (Object)"frac14");
        frac14 = (Entity)ScriptBytecodeAdapter.castToType((Object)object29, Entity.class);
        Object object30 = Entity.$getCallSiteArray()[31].callConstructor(Entity.class, (Object)"frac12");
        frac12 = (Entity)ScriptBytecodeAdapter.castToType((Object)object30, Entity.class);
        Object object31 = Entity.$getCallSiteArray()[32].callConstructor(Entity.class, (Object)"frac34");
        frac34 = (Entity)ScriptBytecodeAdapter.castToType((Object)object31, Entity.class);
        Object object32 = Entity.$getCallSiteArray()[33].callConstructor(Entity.class, (Object)"iquest");
        iquest = (Entity)ScriptBytecodeAdapter.castToType((Object)object32, Entity.class);
        Object object33 = Entity.$getCallSiteArray()[34].callConstructor(Entity.class, (Object)"Agrave");
        Agrave = (Entity)ScriptBytecodeAdapter.castToType((Object)object33, Entity.class);
        Object object34 = Entity.$getCallSiteArray()[35].callConstructor(Entity.class, (Object)"Aacute");
        Aacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object34, Entity.class);
        Object object35 = Entity.$getCallSiteArray()[36].callConstructor(Entity.class, (Object)"Acirc");
        Acirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object35, Entity.class);
        Object object36 = Entity.$getCallSiteArray()[37].callConstructor(Entity.class, (Object)"Atilde");
        Atilde = (Entity)ScriptBytecodeAdapter.castToType((Object)object36, Entity.class);
        Object object37 = Entity.$getCallSiteArray()[38].callConstructor(Entity.class, (Object)"Auml");
        Auml = (Entity)ScriptBytecodeAdapter.castToType((Object)object37, Entity.class);
        Object object38 = Entity.$getCallSiteArray()[39].callConstructor(Entity.class, (Object)"Aring");
        Aring = (Entity)ScriptBytecodeAdapter.castToType((Object)object38, Entity.class);
        Object object39 = Entity.$getCallSiteArray()[40].callConstructor(Entity.class, (Object)"AElig");
        AElig = (Entity)ScriptBytecodeAdapter.castToType((Object)object39, Entity.class);
        Object object40 = Entity.$getCallSiteArray()[41].callConstructor(Entity.class, (Object)"Ccedil");
        Ccedil = (Entity)ScriptBytecodeAdapter.castToType((Object)object40, Entity.class);
        Object object41 = Entity.$getCallSiteArray()[42].callConstructor(Entity.class, (Object)"Egrave");
        Egrave = (Entity)ScriptBytecodeAdapter.castToType((Object)object41, Entity.class);
        Object object42 = Entity.$getCallSiteArray()[43].callConstructor(Entity.class, (Object)"Eacute");
        Eacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object42, Entity.class);
        Object object43 = Entity.$getCallSiteArray()[44].callConstructor(Entity.class, (Object)"Ecirc");
        Ecirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object43, Entity.class);
        Object object44 = Entity.$getCallSiteArray()[45].callConstructor(Entity.class, (Object)"Euml");
        Euml = (Entity)ScriptBytecodeAdapter.castToType((Object)object44, Entity.class);
        Object object45 = Entity.$getCallSiteArray()[46].callConstructor(Entity.class, (Object)"Igrave");
        Igrave = (Entity)ScriptBytecodeAdapter.castToType((Object)object45, Entity.class);
        Object object46 = Entity.$getCallSiteArray()[47].callConstructor(Entity.class, (Object)"Iacute");
        Iacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object46, Entity.class);
        Object object47 = Entity.$getCallSiteArray()[48].callConstructor(Entity.class, (Object)"Icirc");
        Icirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object47, Entity.class);
        Object object48 = Entity.$getCallSiteArray()[49].callConstructor(Entity.class, (Object)"Iuml");
        Iuml = (Entity)ScriptBytecodeAdapter.castToType((Object)object48, Entity.class);
        Object object49 = Entity.$getCallSiteArray()[50].callConstructor(Entity.class, (Object)"ETH");
        ETH = (Entity)ScriptBytecodeAdapter.castToType((Object)object49, Entity.class);
        Object object50 = Entity.$getCallSiteArray()[51].callConstructor(Entity.class, (Object)"Ntilde");
        Ntilde = (Entity)ScriptBytecodeAdapter.castToType((Object)object50, Entity.class);
        Object object51 = Entity.$getCallSiteArray()[52].callConstructor(Entity.class, (Object)"Ograve");
        Ograve = (Entity)ScriptBytecodeAdapter.castToType((Object)object51, Entity.class);
        Object object52 = Entity.$getCallSiteArray()[53].callConstructor(Entity.class, (Object)"Oacute");
        Oacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object52, Entity.class);
        Object object53 = Entity.$getCallSiteArray()[54].callConstructor(Entity.class, (Object)"Ocirc");
        Ocirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object53, Entity.class);
        Object object54 = Entity.$getCallSiteArray()[55].callConstructor(Entity.class, (Object)"Otilde");
        Otilde = (Entity)ScriptBytecodeAdapter.castToType((Object)object54, Entity.class);
        Object object55 = Entity.$getCallSiteArray()[56].callConstructor(Entity.class, (Object)"Ouml");
        Ouml = (Entity)ScriptBytecodeAdapter.castToType((Object)object55, Entity.class);
        Object object56 = Entity.$getCallSiteArray()[57].callConstructor(Entity.class, (Object)"times");
        times = (Entity)ScriptBytecodeAdapter.castToType((Object)object56, Entity.class);
        Object object57 = Entity.$getCallSiteArray()[58].callConstructor(Entity.class, (Object)"Oslash");
        Oslash = (Entity)ScriptBytecodeAdapter.castToType((Object)object57, Entity.class);
        Object object58 = Entity.$getCallSiteArray()[59].callConstructor(Entity.class, (Object)"Ugrave");
        Ugrave = (Entity)ScriptBytecodeAdapter.castToType((Object)object58, Entity.class);
        Object object59 = Entity.$getCallSiteArray()[60].callConstructor(Entity.class, (Object)"Uacute");
        Uacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object59, Entity.class);
        Object object60 = Entity.$getCallSiteArray()[61].callConstructor(Entity.class, (Object)"Ucirc");
        Ucirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object60, Entity.class);
        Object object61 = Entity.$getCallSiteArray()[62].callConstructor(Entity.class, (Object)"Uuml");
        Uuml = (Entity)ScriptBytecodeAdapter.castToType((Object)object61, Entity.class);
        Object object62 = Entity.$getCallSiteArray()[63].callConstructor(Entity.class, (Object)"Yacute");
        Yacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object62, Entity.class);
        Object object63 = Entity.$getCallSiteArray()[64].callConstructor(Entity.class, (Object)"THORN");
        THORN = (Entity)ScriptBytecodeAdapter.castToType((Object)object63, Entity.class);
        Object object64 = Entity.$getCallSiteArray()[65].callConstructor(Entity.class, (Object)"szlig");
        szlig = (Entity)ScriptBytecodeAdapter.castToType((Object)object64, Entity.class);
        Object object65 = Entity.$getCallSiteArray()[66].callConstructor(Entity.class, (Object)"agrave");
        agrave = (Entity)ScriptBytecodeAdapter.castToType((Object)object65, Entity.class);
        Object object66 = Entity.$getCallSiteArray()[67].callConstructor(Entity.class, (Object)"aacute");
        aacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object66, Entity.class);
        Object object67 = Entity.$getCallSiteArray()[68].callConstructor(Entity.class, (Object)"acirc");
        acirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object67, Entity.class);
        Object object68 = Entity.$getCallSiteArray()[69].callConstructor(Entity.class, (Object)"atilde");
        atilde = (Entity)ScriptBytecodeAdapter.castToType((Object)object68, Entity.class);
        Object object69 = Entity.$getCallSiteArray()[70].callConstructor(Entity.class, (Object)"auml");
        auml = (Entity)ScriptBytecodeAdapter.castToType((Object)object69, Entity.class);
        Object object70 = Entity.$getCallSiteArray()[71].callConstructor(Entity.class, (Object)"aring");
        aring = (Entity)ScriptBytecodeAdapter.castToType((Object)object70, Entity.class);
        Object object71 = Entity.$getCallSiteArray()[72].callConstructor(Entity.class, (Object)"aelig");
        aelig = (Entity)ScriptBytecodeAdapter.castToType((Object)object71, Entity.class);
        Object object72 = Entity.$getCallSiteArray()[73].callConstructor(Entity.class, (Object)"ccedil");
        ccedil = (Entity)ScriptBytecodeAdapter.castToType((Object)object72, Entity.class);
        Object object73 = Entity.$getCallSiteArray()[74].callConstructor(Entity.class, (Object)"egrave");
        egrave = (Entity)ScriptBytecodeAdapter.castToType((Object)object73, Entity.class);
        Object object74 = Entity.$getCallSiteArray()[75].callConstructor(Entity.class, (Object)"eacute");
        eacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object74, Entity.class);
        Object object75 = Entity.$getCallSiteArray()[76].callConstructor(Entity.class, (Object)"ecirc");
        ecirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object75, Entity.class);
        Object object76 = Entity.$getCallSiteArray()[77].callConstructor(Entity.class, (Object)"euml");
        euml = (Entity)ScriptBytecodeAdapter.castToType((Object)object76, Entity.class);
        Object object77 = Entity.$getCallSiteArray()[78].callConstructor(Entity.class, (Object)"igrave");
        igrave = (Entity)ScriptBytecodeAdapter.castToType((Object)object77, Entity.class);
        Object object78 = Entity.$getCallSiteArray()[79].callConstructor(Entity.class, (Object)"iacute");
        iacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object78, Entity.class);
        Object object79 = Entity.$getCallSiteArray()[80].callConstructor(Entity.class, (Object)"icirc");
        icirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object79, Entity.class);
        Object object80 = Entity.$getCallSiteArray()[81].callConstructor(Entity.class, (Object)"iuml");
        iuml = (Entity)ScriptBytecodeAdapter.castToType((Object)object80, Entity.class);
        Object object81 = Entity.$getCallSiteArray()[82].callConstructor(Entity.class, (Object)"eth");
        eth = (Entity)ScriptBytecodeAdapter.castToType((Object)object81, Entity.class);
        Object object82 = Entity.$getCallSiteArray()[83].callConstructor(Entity.class, (Object)"ntilde");
        ntilde = (Entity)ScriptBytecodeAdapter.castToType((Object)object82, Entity.class);
        Object object83 = Entity.$getCallSiteArray()[84].callConstructor(Entity.class, (Object)"ograve");
        ograve = (Entity)ScriptBytecodeAdapter.castToType((Object)object83, Entity.class);
        Object object84 = Entity.$getCallSiteArray()[85].callConstructor(Entity.class, (Object)"oacute");
        oacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object84, Entity.class);
        Object object85 = Entity.$getCallSiteArray()[86].callConstructor(Entity.class, (Object)"ocirc");
        ocirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object85, Entity.class);
        Object object86 = Entity.$getCallSiteArray()[87].callConstructor(Entity.class, (Object)"otilde");
        otilde = (Entity)ScriptBytecodeAdapter.castToType((Object)object86, Entity.class);
        Object object87 = Entity.$getCallSiteArray()[88].callConstructor(Entity.class, (Object)"ouml");
        ouml = (Entity)ScriptBytecodeAdapter.castToType((Object)object87, Entity.class);
        Object object88 = Entity.$getCallSiteArray()[89].callConstructor(Entity.class, (Object)"divide");
        divide = (Entity)ScriptBytecodeAdapter.castToType((Object)object88, Entity.class);
        Object object89 = Entity.$getCallSiteArray()[90].callConstructor(Entity.class, (Object)"oslash");
        oslash = (Entity)ScriptBytecodeAdapter.castToType((Object)object89, Entity.class);
        Object object90 = Entity.$getCallSiteArray()[91].callConstructor(Entity.class, (Object)"ugrave");
        ugrave = (Entity)ScriptBytecodeAdapter.castToType((Object)object90, Entity.class);
        Object object91 = Entity.$getCallSiteArray()[92].callConstructor(Entity.class, (Object)"uacute");
        uacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object91, Entity.class);
        Object object92 = Entity.$getCallSiteArray()[93].callConstructor(Entity.class, (Object)"ucirc");
        ucirc = (Entity)ScriptBytecodeAdapter.castToType((Object)object92, Entity.class);
        Object object93 = Entity.$getCallSiteArray()[94].callConstructor(Entity.class, (Object)"uuml");
        uuml = (Entity)ScriptBytecodeAdapter.castToType((Object)object93, Entity.class);
        Object object94 = Entity.$getCallSiteArray()[95].callConstructor(Entity.class, (Object)"yacute");
        yacute = (Entity)ScriptBytecodeAdapter.castToType((Object)object94, Entity.class);
        Object object95 = Entity.$getCallSiteArray()[96].callConstructor(Entity.class, (Object)"thorn");
        thorn = (Entity)ScriptBytecodeAdapter.castToType((Object)object95, Entity.class);
        Object object96 = Entity.$getCallSiteArray()[97].callConstructor(Entity.class, (Object)"yuml");
        yuml = (Entity)ScriptBytecodeAdapter.castToType((Object)object96, Entity.class);
        Object object97 = Entity.$getCallSiteArray()[98].callConstructor(Entity.class, (Object)"lt");
        lt = (Entity)ScriptBytecodeAdapter.castToType((Object)object97, Entity.class);
        Object object98 = Entity.$getCallSiteArray()[99].callConstructor(Entity.class, (Object)"gt");
        gt = (Entity)ScriptBytecodeAdapter.castToType((Object)object98, Entity.class);
        Object object99 = Entity.$getCallSiteArray()[100].callConstructor(Entity.class, (Object)"amp");
        amp = (Entity)ScriptBytecodeAdapter.castToType((Object)object99, Entity.class);
        Object object100 = Entity.$getCallSiteArray()[101].callConstructor(Entity.class, (Object)"apos");
        apos = (Entity)ScriptBytecodeAdapter.castToType((Object)object100, Entity.class);
        Object object101 = Entity.$getCallSiteArray()[102].callConstructor(Entity.class, (Object)"quot");
        quot = (Entity)ScriptBytecodeAdapter.castToType((Object)object101, Entity.class);
        Object object102 = Entity.$getCallSiteArray()[103].callConstructor(Entity.class, (Object)"OElig");
        OElig = (Entity)ScriptBytecodeAdapter.castToType((Object)object102, Entity.class);
        Object object103 = Entity.$getCallSiteArray()[104].callConstructor(Entity.class, (Object)"oelig");
        oelig = (Entity)ScriptBytecodeAdapter.castToType((Object)object103, Entity.class);
        Object object104 = Entity.$getCallSiteArray()[105].callConstructor(Entity.class, (Object)"Scaron");
        Scaron = (Entity)ScriptBytecodeAdapter.castToType((Object)object104, Entity.class);
        Object object105 = Entity.$getCallSiteArray()[106].callConstructor(Entity.class, (Object)"scaron");
        scaron = (Entity)ScriptBytecodeAdapter.castToType((Object)object105, Entity.class);
        Object object106 = Entity.$getCallSiteArray()[107].callConstructor(Entity.class, (Object)"Yuml");
        Yuml = (Entity)ScriptBytecodeAdapter.castToType((Object)object106, Entity.class);
        Object object107 = Entity.$getCallSiteArray()[108].callConstructor(Entity.class, (Object)"circ");
        circ = (Entity)ScriptBytecodeAdapter.castToType((Object)object107, Entity.class);
        Object object108 = Entity.$getCallSiteArray()[109].callConstructor(Entity.class, (Object)"tilde");
        tilde = (Entity)ScriptBytecodeAdapter.castToType((Object)object108, Entity.class);
        Object object109 = Entity.$getCallSiteArray()[110].callConstructor(Entity.class, (Object)"ensp");
        ensp = (Entity)ScriptBytecodeAdapter.castToType((Object)object109, Entity.class);
        Object object110 = Entity.$getCallSiteArray()[111].callConstructor(Entity.class, (Object)"emsp");
        emsp = (Entity)ScriptBytecodeAdapter.castToType((Object)object110, Entity.class);
        Object object111 = Entity.$getCallSiteArray()[112].callConstructor(Entity.class, (Object)"thinsp");
        thinsp = (Entity)ScriptBytecodeAdapter.castToType((Object)object111, Entity.class);
        Object object112 = Entity.$getCallSiteArray()[113].callConstructor(Entity.class, (Object)"zwnj");
        zwnj = (Entity)ScriptBytecodeAdapter.castToType((Object)object112, Entity.class);
        Object object113 = Entity.$getCallSiteArray()[114].callConstructor(Entity.class, (Object)"zwj");
        zwj = (Entity)ScriptBytecodeAdapter.castToType((Object)object113, Entity.class);
        Object object114 = Entity.$getCallSiteArray()[115].callConstructor(Entity.class, (Object)"lrm");
        lrm = (Entity)ScriptBytecodeAdapter.castToType((Object)object114, Entity.class);
        Object object115 = Entity.$getCallSiteArray()[116].callConstructor(Entity.class, (Object)"rlm");
        rlm = (Entity)ScriptBytecodeAdapter.castToType((Object)object115, Entity.class);
        Object object116 = Entity.$getCallSiteArray()[117].callConstructor(Entity.class, (Object)"ndash");
        ndash = (Entity)ScriptBytecodeAdapter.castToType((Object)object116, Entity.class);
        Object object117 = Entity.$getCallSiteArray()[118].callConstructor(Entity.class, (Object)"mdash");
        mdash = (Entity)ScriptBytecodeAdapter.castToType((Object)object117, Entity.class);
        Object object118 = Entity.$getCallSiteArray()[119].callConstructor(Entity.class, (Object)"lsquo");
        lsquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object118, Entity.class);
        Object object119 = Entity.$getCallSiteArray()[120].callConstructor(Entity.class, (Object)"rsquo");
        rsquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object119, Entity.class);
        Object object120 = Entity.$getCallSiteArray()[121].callConstructor(Entity.class, (Object)"sbquo");
        sbquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object120, Entity.class);
        Object object121 = Entity.$getCallSiteArray()[122].callConstructor(Entity.class, (Object)"ldquo");
        ldquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object121, Entity.class);
        Object object122 = Entity.$getCallSiteArray()[123].callConstructor(Entity.class, (Object)"rdquo");
        rdquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object122, Entity.class);
        Object object123 = Entity.$getCallSiteArray()[124].callConstructor(Entity.class, (Object)"bdquo");
        bdquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object123, Entity.class);
        Object object124 = Entity.$getCallSiteArray()[125].callConstructor(Entity.class, (Object)"dagger");
        dagger = (Entity)ScriptBytecodeAdapter.castToType((Object)object124, Entity.class);
        Object object125 = Entity.$getCallSiteArray()[126].callConstructor(Entity.class, (Object)"Dagger");
        Dagger = (Entity)ScriptBytecodeAdapter.castToType((Object)object125, Entity.class);
        Object object126 = Entity.$getCallSiteArray()[127].callConstructor(Entity.class, (Object)"permil");
        permil = (Entity)ScriptBytecodeAdapter.castToType((Object)object126, Entity.class);
        Object object127 = Entity.$getCallSiteArray()[128].callConstructor(Entity.class, (Object)"lsaquo");
        lsaquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object127, Entity.class);
        Object object128 = Entity.$getCallSiteArray()[129].callConstructor(Entity.class, (Object)"rsaquo");
        rsaquo = (Entity)ScriptBytecodeAdapter.castToType((Object)object128, Entity.class);
        Object object129 = Entity.$getCallSiteArray()[130].callConstructor(Entity.class, (Object)"euro");
        euro = (Entity)ScriptBytecodeAdapter.castToType((Object)object129, Entity.class);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "leftShift";
        stringArray[1] = "unescaped";
        stringArray[2] = "<$constructor$>";
        stringArray[3] = "<$constructor$>";
        stringArray[4] = "<$constructor$>";
        stringArray[5] = "<$constructor$>";
        stringArray[6] = "<$constructor$>";
        stringArray[7] = "<$constructor$>";
        stringArray[8] = "<$constructor$>";
        stringArray[9] = "<$constructor$>";
        stringArray[10] = "<$constructor$>";
        stringArray[11] = "<$constructor$>";
        stringArray[12] = "<$constructor$>";
        stringArray[13] = "<$constructor$>";
        stringArray[14] = "<$constructor$>";
        stringArray[15] = "<$constructor$>";
        stringArray[16] = "<$constructor$>";
        stringArray[17] = "<$constructor$>";
        stringArray[18] = "<$constructor$>";
        stringArray[19] = "<$constructor$>";
        stringArray[20] = "<$constructor$>";
        stringArray[21] = "<$constructor$>";
        stringArray[22] = "<$constructor$>";
        stringArray[23] = "<$constructor$>";
        stringArray[24] = "<$constructor$>";
        stringArray[25] = "<$constructor$>";
        stringArray[26] = "<$constructor$>";
        stringArray[27] = "<$constructor$>";
        stringArray[28] = "<$constructor$>";
        stringArray[29] = "<$constructor$>";
        stringArray[30] = "<$constructor$>";
        stringArray[31] = "<$constructor$>";
        stringArray[32] = "<$constructor$>";
        stringArray[33] = "<$constructor$>";
        stringArray[34] = "<$constructor$>";
        stringArray[35] = "<$constructor$>";
        stringArray[36] = "<$constructor$>";
        stringArray[37] = "<$constructor$>";
        stringArray[38] = "<$constructor$>";
        stringArray[39] = "<$constructor$>";
        stringArray[40] = "<$constructor$>";
        stringArray[41] = "<$constructor$>";
        stringArray[42] = "<$constructor$>";
        stringArray[43] = "<$constructor$>";
        stringArray[44] = "<$constructor$>";
        stringArray[45] = "<$constructor$>";
        stringArray[46] = "<$constructor$>";
        stringArray[47] = "<$constructor$>";
        stringArray[48] = "<$constructor$>";
        stringArray[49] = "<$constructor$>";
        stringArray[50] = "<$constructor$>";
        stringArray[51] = "<$constructor$>";
        stringArray[52] = "<$constructor$>";
        stringArray[53] = "<$constructor$>";
        stringArray[54] = "<$constructor$>";
        stringArray[55] = "<$constructor$>";
        stringArray[56] = "<$constructor$>";
        stringArray[57] = "<$constructor$>";
        stringArray[58] = "<$constructor$>";
        stringArray[59] = "<$constructor$>";
        stringArray[60] = "<$constructor$>";
        stringArray[61] = "<$constructor$>";
        stringArray[62] = "<$constructor$>";
        stringArray[63] = "<$constructor$>";
        stringArray[64] = "<$constructor$>";
        stringArray[65] = "<$constructor$>";
        stringArray[66] = "<$constructor$>";
        stringArray[67] = "<$constructor$>";
        stringArray[68] = "<$constructor$>";
        stringArray[69] = "<$constructor$>";
        stringArray[70] = "<$constructor$>";
        stringArray[71] = "<$constructor$>";
        stringArray[72] = "<$constructor$>";
        stringArray[73] = "<$constructor$>";
        stringArray[74] = "<$constructor$>";
        stringArray[75] = "<$constructor$>";
        stringArray[76] = "<$constructor$>";
        stringArray[77] = "<$constructor$>";
        stringArray[78] = "<$constructor$>";
        stringArray[79] = "<$constructor$>";
        stringArray[80] = "<$constructor$>";
        stringArray[81] = "<$constructor$>";
        stringArray[82] = "<$constructor$>";
        stringArray[83] = "<$constructor$>";
        stringArray[84] = "<$constructor$>";
        stringArray[85] = "<$constructor$>";
        stringArray[86] = "<$constructor$>";
        stringArray[87] = "<$constructor$>";
        stringArray[88] = "<$constructor$>";
        stringArray[89] = "<$constructor$>";
        stringArray[90] = "<$constructor$>";
        stringArray[91] = "<$constructor$>";
        stringArray[92] = "<$constructor$>";
        stringArray[93] = "<$constructor$>";
        stringArray[94] = "<$constructor$>";
        stringArray[95] = "<$constructor$>";
        stringArray[96] = "<$constructor$>";
        stringArray[97] = "<$constructor$>";
        stringArray[98] = "<$constructor$>";
        stringArray[99] = "<$constructor$>";
        stringArray[100] = "<$constructor$>";
        stringArray[101] = "<$constructor$>";
        stringArray[102] = "<$constructor$>";
        stringArray[103] = "<$constructor$>";
        stringArray[104] = "<$constructor$>";
        stringArray[105] = "<$constructor$>";
        stringArray[106] = "<$constructor$>";
        stringArray[107] = "<$constructor$>";
        stringArray[108] = "<$constructor$>";
        stringArray[109] = "<$constructor$>";
        stringArray[110] = "<$constructor$>";
        stringArray[111] = "<$constructor$>";
        stringArray[112] = "<$constructor$>";
        stringArray[113] = "<$constructor$>";
        stringArray[114] = "<$constructor$>";
        stringArray[115] = "<$constructor$>";
        stringArray[116] = "<$constructor$>";
        stringArray[117] = "<$constructor$>";
        stringArray[118] = "<$constructor$>";
        stringArray[119] = "<$constructor$>";
        stringArray[120] = "<$constructor$>";
        stringArray[121] = "<$constructor$>";
        stringArray[122] = "<$constructor$>";
        stringArray[123] = "<$constructor$>";
        stringArray[124] = "<$constructor$>";
        stringArray[125] = "<$constructor$>";
        stringArray[126] = "<$constructor$>";
        stringArray[127] = "<$constructor$>";
        stringArray[128] = "<$constructor$>";
        stringArray[129] = "<$constructor$>";
        stringArray[130] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[131];
        Entity.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(Entity.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = Entity.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

