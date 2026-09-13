/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.simpl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class PropertySettingJobFactory
extends SimpleJobFactory {
    private boolean warnIfNotFound = false;
    private boolean throwIfNotFound = false;

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        Job job = super.newJob(bundle, scheduler);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(scheduler.getContext());
        jobDataMap.putAll(bundle.getJobDetail().getJobDataMap());
        jobDataMap.putAll(bundle.getTrigger().getJobDataMap());
        this.setBeanProps(job, jobDataMap);
        return job;
    }

    protected void setBeanProps(Object obj, JobDataMap data) throws SchedulerException {
        BeanInfo bi = null;
        try {
            bi = Introspector.getBeanInfo(obj.getClass());
        }
        catch (IntrospectionException e) {
            this.handleError("Unable to introspect Job class.", e);
        }
        PropertyDescriptor[] propDescs = bi.getPropertyDescriptors();
        for (Map.Entry entry : data.getWrappedMap().entrySet()) {
            String name = (String)entry.getKey();
            String c = name.substring(0, 1).toUpperCase(Locale.US);
            String methName = "set" + c + name.substring(1);
            Method setMeth = this.getSetMethod(methName, propDescs);
            Class<?> paramType = null;
            Object o = null;
            try {
                if (setMeth == null) {
                    this.handleError("No setter on Job class " + obj.getClass().getName() + " for property '" + name + "'");
                    continue;
                }
                paramType = setMeth.getParameterTypes()[0];
                o = entry.getValue();
                Object parm = null;
                if (paramType.isPrimitive()) {
                    if (o == null) {
                        this.handleError("Cannot set primitive property '" + name + "' on Job class " + obj.getClass().getName() + " to null.");
                        continue;
                    }
                    if (paramType.equals(Integer.TYPE)) {
                        if (o instanceof String) {
                            parm = Integer.valueOf((String)o);
                        } else if (o instanceof Integer) {
                            parm = o;
                        }
                    } else if (paramType.equals(Long.TYPE)) {
                        if (o instanceof String) {
                            parm = Long.valueOf((String)o);
                        } else if (o instanceof Long) {
                            parm = o;
                        }
                    } else if (paramType.equals(Float.TYPE)) {
                        if (o instanceof String) {
                            parm = Float.valueOf((String)o);
                        } else if (o instanceof Float) {
                            parm = o;
                        }
                    } else if (paramType.equals(Double.TYPE)) {
                        if (o instanceof String) {
                            parm = Double.valueOf((String)o);
                        } else if (o instanceof Double) {
                            parm = o;
                        }
                    } else if (paramType.equals(Boolean.TYPE)) {
                        if (o instanceof String) {
                            parm = Boolean.valueOf((String)o);
                        } else if (o instanceof Boolean) {
                            parm = o;
                        }
                    } else if (paramType.equals(Byte.TYPE)) {
                        if (o instanceof String) {
                            parm = Byte.valueOf((String)o);
                        } else if (o instanceof Byte) {
                            parm = o;
                        }
                    } else if (paramType.equals(Short.TYPE)) {
                        if (o instanceof String) {
                            parm = Short.valueOf((String)o);
                        } else if (o instanceof Short) {
                            parm = o;
                        }
                    } else if (paramType.equals(Character.TYPE)) {
                        if (o instanceof String) {
                            String str = (String)o;
                            if (str.length() == 1) {
                                parm = Character.valueOf(str.charAt(0));
                            }
                        } else if (o instanceof Character) {
                            parm = o;
                        }
                    }
                } else if (o != null && paramType.isAssignableFrom(o.getClass())) {
                    parm = o;
                }
                if (o != null && parm == null) {
                    this.handleError("The setter on Job class " + obj.getClass().getName() + " for property '" + name + "' expects a " + paramType + " but was given " + o.getClass().getName());
                    continue;
                }
                setMeth.invoke(obj, parm);
            }
            catch (NumberFormatException nfe) {
                this.handleError("The setter on Job class " + obj.getClass().getName() + " for property '" + name + "' expects a " + paramType + " but was given " + o.getClass().getName(), nfe);
            }
            catch (IllegalArgumentException e) {
                this.handleError("The setter on Job class " + obj.getClass().getName() + " for property '" + name + "' expects a " + paramType + " but was given " + o.getClass().getName(), e);
            }
            catch (IllegalAccessException e) {
                this.handleError("The setter on Job class " + obj.getClass().getName() + " for property '" + name + "' could not be accessed.", e);
            }
            catch (InvocationTargetException e) {
                this.handleError("The setter on Job class " + obj.getClass().getName() + " for property '" + name + "' could not be invoked.", e);
            }
        }
    }

    private void handleError(String message) throws SchedulerException {
        this.handleError(message, null);
    }

    private void handleError(String message, Exception e) throws SchedulerException {
        if (this.isThrowIfPropertyNotFound()) {
            throw new SchedulerException(message, e);
        }
        if (this.isWarnIfPropertyNotFound()) {
            if (e == null) {
                this.getLog().warn(message);
            } else {
                this.getLog().warn(message, (Throwable)e);
            }
        }
    }

    private Method getSetMethod(String name, PropertyDescriptor[] props) {
        for (int i = 0; i < props.length; ++i) {
            Method wMeth = props[i].getWriteMethod();
            if (wMeth == null || wMeth.getParameterTypes().length != 1 || !wMeth.getName().equals(name)) continue;
            return wMeth;
        }
        return null;
    }

    public boolean isThrowIfPropertyNotFound() {
        return this.throwIfNotFound;
    }

    public void setThrowIfPropertyNotFound(boolean throwIfNotFound) {
        this.throwIfNotFound = throwIfNotFound;
    }

    public boolean isWarnIfPropertyNotFound() {
        return this.warnIfNotFound;
    }

    public void setWarnIfPropertyNotFound(boolean warnIfNotFound) {
        this.warnIfNotFound = warnIfNotFound;
    }
}

