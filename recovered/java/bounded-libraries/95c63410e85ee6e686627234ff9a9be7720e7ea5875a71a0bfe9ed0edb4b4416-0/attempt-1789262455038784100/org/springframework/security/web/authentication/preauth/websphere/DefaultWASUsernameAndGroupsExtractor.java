/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.security.web.authentication.preauth.websphere;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.preauth.websphere.WASUsernameAndGroupsExtractor;

final class DefaultWASUsernameAndGroupsExtractor
implements WASUsernameAndGroupsExtractor {
    private static final Log logger = LogFactory.getLog(DefaultWASUsernameAndGroupsExtractor.class);
    private static final String PORTABLE_REMOTE_OBJECT_CLASSNAME = "javax.rmi.PortableRemoteObject";
    private static final String USER_REGISTRY = "UserRegistry";
    private static Method getRunAsSubject = null;
    private static Method getGroupsForUser = null;
    private static Method getSecurityName = null;
    private static Method narrow = null;
    private static Class<?> wsCredentialClass = null;

    DefaultWASUsernameAndGroupsExtractor() {
    }

    @Override
    public List<String> getGroupsForCurrentUser() {
        return DefaultWASUsernameAndGroupsExtractor.getWebSphereGroups(DefaultWASUsernameAndGroupsExtractor.getRunAsSubject());
    }

    @Override
    public String getCurrentUserName() {
        return DefaultWASUsernameAndGroupsExtractor.getSecurityName(DefaultWASUsernameAndGroupsExtractor.getRunAsSubject());
    }

    private static String getSecurityName(Subject subject) {
        Object credential;
        logger.debug((Object)LogMessage.format((String)"Determining Websphere security name for subject %s", (Object)subject));
        String userSecurityName = null;
        if (subject != null && (credential = subject.getPublicCredentials(DefaultWASUsernameAndGroupsExtractor.getWSCredentialClass()).iterator().next()) != null) {
            userSecurityName = (String)DefaultWASUsernameAndGroupsExtractor.invokeMethod(DefaultWASUsernameAndGroupsExtractor.getSecurityNameMethod(), credential, new Object[0]);
        }
        logger.debug((Object)LogMessage.format((String)"Websphere security name is %s for subject %s", (Object)subject, userSecurityName));
        return userSecurityName;
    }

    private static Subject getRunAsSubject() {
        logger.debug((Object)"Retrieving WebSphere RunAs subject");
        return (Subject)DefaultWASUsernameAndGroupsExtractor.invokeMethod(DefaultWASUsernameAndGroupsExtractor.getRunAsSubjectMethod(), null, new Object[0]);
    }

    private static List<String> getWebSphereGroups(Subject subject) {
        return DefaultWASUsernameAndGroupsExtractor.getWebSphereGroups(DefaultWASUsernameAndGroupsExtractor.getSecurityName(subject));
    }

    private static List<String> getWebSphereGroups(String securityName) {
        InitialContext context = null;
        try {
            context = new InitialContext();
            Object objRef = context.lookup(USER_REGISTRY);
            Object userReg = DefaultWASUsernameAndGroupsExtractor.invokeMethod(DefaultWASUsernameAndGroupsExtractor.getNarrowMethod(), null, objRef, Class.forName("com.ibm.websphere.security.UserRegistry"));
            logger.debug((Object)LogMessage.format((String)"Determining WebSphere groups for user %s using WebSphere UserRegistry %s", (Object)securityName, (Object)userReg));
            Collection groups = (Collection)DefaultWASUsernameAndGroupsExtractor.invokeMethod(DefaultWASUsernameAndGroupsExtractor.getGroupsForUserMethod(), userReg, securityName);
            logger.debug((Object)LogMessage.format((String)"Groups for user %s: %s", (Object)securityName, (Object)groups));
            ArrayList<String> arrayList = new ArrayList<String>(groups);
            return arrayList;
        }
        catch (Exception ex) {
            logger.error((Object)"Exception occured while looking up groups for user", (Throwable)ex);
            throw new RuntimeException("Exception occured while looking up groups for user", ex);
        }
        finally {
            DefaultWASUsernameAndGroupsExtractor.closeContext(context);
        }
    }

    private static void closeContext(Context context) {
        try {
            if (context != null) {
                context.close();
            }
        }
        catch (NamingException ex) {
            logger.debug((Object)"Exception occured while closing context", (Throwable)ex);
        }
    }

    private static Object invokeMethod(Method method, Object instance, Object ... args) {
        try {
            return method.invoke(instance, args);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            String message = "Error while invoking method " + method.getClass().getName() + "." + method.getName() + "(" + Arrays.asList(args) + ")";
            logger.error((Object)message, (Throwable)ex);
            throw new RuntimeException(message, ex);
        }
    }

    private static Method getMethod(String className, String methodName, String[] parameterTypeNames) {
        try {
            Class<?> c = Class.forName(className);
            int len = parameterTypeNames.length;
            Class[] parameterTypes = new Class[len];
            for (int i = 0; i < len; ++i) {
                parameterTypes[i] = Class.forName(parameterTypeNames[i]);
            }
            return c.getDeclaredMethod(methodName, parameterTypes);
        }
        catch (ClassNotFoundException ex) {
            logger.error((Object)("Required class" + className + " not found"));
            throw new RuntimeException("Required class" + className + " not found", ex);
        }
        catch (NoSuchMethodException ex) {
            logger.error((Object)("Required method " + methodName + " with parameter types (" + Arrays.asList(parameterTypeNames) + ") not found on class " + className));
            throw new RuntimeException("Required class" + className + " not found", ex);
        }
    }

    private static Method getRunAsSubjectMethod() {
        if (getRunAsSubject == null) {
            getRunAsSubject = DefaultWASUsernameAndGroupsExtractor.getMethod("com.ibm.websphere.security.auth.WSSubject", "getRunAsSubject", new String[0]);
        }
        return getRunAsSubject;
    }

    private static Method getGroupsForUserMethod() {
        if (getGroupsForUser == null) {
            getGroupsForUser = DefaultWASUsernameAndGroupsExtractor.getMethod("com.ibm.websphere.security.UserRegistry", "getGroupsForUser", new String[]{"java.lang.String"});
        }
        return getGroupsForUser;
    }

    private static Method getSecurityNameMethod() {
        if (getSecurityName == null) {
            getSecurityName = DefaultWASUsernameAndGroupsExtractor.getMethod("com.ibm.websphere.security.cred.WSCredential", "getSecurityName", new String[0]);
        }
        return getSecurityName;
    }

    private static Method getNarrowMethod() {
        if (narrow == null) {
            narrow = DefaultWASUsernameAndGroupsExtractor.getMethod(PORTABLE_REMOTE_OBJECT_CLASSNAME, "narrow", new String[]{Object.class.getName(), Class.class.getName()});
        }
        return narrow;
    }

    private static Class<?> getWSCredentialClass() {
        if (wsCredentialClass == null) {
            wsCredentialClass = DefaultWASUsernameAndGroupsExtractor.getClass("com.ibm.websphere.security.cred.WSCredential");
        }
        return wsCredentialClass;
    }

    private static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            logger.error((Object)("Required class " + className + " not found"));
            throw new RuntimeException("Required class " + className + " not found", ex);
        }
    }
}

