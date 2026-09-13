/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.typehandling;

import groovy.lang.MetaMethod;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Consumer;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.metaclass.NewInstanceMetaMethod;

public class NumberMathModificationInfo {
    public static final NumberMathModificationInfo instance = new NumberMathModificationInfo();
    private static final HashSet<String> NAMES = new HashSet();
    public boolean byte_plus;
    public boolean short_plus;
    public boolean int_plus;
    public boolean long_plus;
    public boolean float_plus;
    public boolean double_plus;
    public boolean byte_minus;
    public boolean short_minus;
    public boolean int_minus;
    public boolean long_minus;
    public boolean float_minus;
    public boolean double_minus;
    public boolean byte_multiply;
    public boolean short_multiply;
    public boolean int_multiply;
    public boolean long_multiply;
    public boolean float_multiply;
    public boolean double_multiply;
    public boolean byte_div;
    public boolean short_div;
    public boolean int_div;
    public boolean long_div;
    public boolean float_div;
    public boolean double_div;
    public boolean byte_or;
    public boolean short_or;
    public boolean int_or;
    public boolean long_or;
    public boolean float_or;
    public boolean double_or;
    public boolean byte_and;
    public boolean short_and;
    public boolean int_and;
    public boolean long_and;
    public boolean float_and;
    public boolean double_and;
    public boolean byte_xor;
    public boolean short_xor;
    public boolean int_xor;
    public boolean long_xor;
    public boolean float_xor;
    public boolean double_xor;
    public boolean byte_intdiv;
    public boolean short_intdiv;
    public boolean int_intdiv;
    public boolean long_intdiv;
    public boolean float_intdiv;
    public boolean double_intdiv;
    public boolean byte_mod;
    public boolean short_mod;
    public boolean int_mod;
    public boolean long_mod;
    public boolean float_mod;
    public boolean double_mod;
    public boolean byte_leftShift;
    public boolean short_leftShift;
    public boolean int_leftShift;
    public boolean long_leftShift;
    public boolean float_leftShift;
    public boolean double_leftShift;
    public boolean byte_rightShift;
    public boolean short_rightShift;
    public boolean int_rightShift;
    public boolean long_rightShift;
    public boolean float_rightShift;
    public boolean double_rightShift;
    public boolean byte_rightShiftUnsigned;
    public boolean short_rightShiftUnsigned;
    public boolean int_rightShiftUnsigned;
    public boolean long_rightShiftUnsigned;
    public boolean float_rightShiftUnsigned;
    public boolean double_rightShiftUnsigned;

    private NumberMathModificationInfo() {
    }

    public void checkIfStdMethod(MetaMethod method) {
        if (method.getClass() != NewInstanceMetaMethod.class) {
            String name = method.getName();
            if (method.getParameterTypes().length != 1) {
                return;
            }
            if (!method.getParameterTypes()[0].isNumber && method.getParameterTypes()[0].getTheClass() != Object.class) {
                return;
            }
            if (!NAMES.contains(name)) {
                return;
            }
            this.checkNumberOps(name, method.getDeclaringClass().getTheClass());
        }
    }

    private void checkNumberOps(String name, Class klazz) {
        if ("plus".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_plus = true;
            }, e -> {
                this.short_plus = true;
            }, e -> {
                this.int_plus = true;
            }, e -> {
                this.long_plus = true;
            }, e -> {
                this.float_plus = true;
            }, e -> {
                this.double_plus = true;
            });
        } else if ("minus".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_minus = true;
            }, e -> {
                this.short_minus = true;
            }, e -> {
                this.int_minus = true;
            }, e -> {
                this.long_minus = true;
            }, e -> {
                this.float_minus = true;
            }, e -> {
                this.double_minus = true;
            });
        } else if ("multiply".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_multiply = true;
            }, e -> {
                this.short_multiply = true;
            }, e -> {
                this.int_multiply = true;
            }, e -> {
                this.long_multiply = true;
            }, e -> {
                this.float_multiply = true;
            }, e -> {
                this.double_multiply = true;
            });
        } else if ("div".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_div = true;
            }, e -> {
                this.short_div = true;
            }, e -> {
                this.int_div = true;
            }, e -> {
                this.long_div = true;
            }, e -> {
                this.float_div = true;
            }, e -> {
                this.double_div = true;
            });
        } else if ("or".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_or = true;
            }, e -> {
                this.short_or = true;
            }, e -> {
                this.int_or = true;
            }, e -> {
                this.long_or = true;
            }, e -> {
                this.float_or = true;
            }, e -> {
                this.double_or = true;
            });
        } else if ("and".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_and = true;
            }, e -> {
                this.short_and = true;
            }, e -> {
                this.int_and = true;
            }, e -> {
                this.long_and = true;
            }, e -> {
                this.float_and = true;
            }, e -> {
                this.double_and = true;
            });
        } else if ("xor".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_xor = true;
            }, e -> {
                this.short_xor = true;
            }, e -> {
                this.int_xor = true;
            }, e -> {
                this.long_xor = true;
            }, e -> {
                this.float_xor = true;
            }, e -> {
                this.double_xor = true;
            });
        } else if ("intdiv".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_intdiv = true;
            }, e -> {
                this.short_intdiv = true;
            }, e -> {
                this.int_intdiv = true;
            }, e -> {
                this.long_intdiv = true;
            }, e -> {
                this.float_intdiv = true;
            }, e -> {
                this.double_intdiv = true;
            });
        } else if ("mod".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_mod = true;
            }, e -> {
                this.short_mod = true;
            }, e -> {
                this.int_mod = true;
            }, e -> {
                this.long_mod = true;
            }, e -> {
                this.float_mod = true;
            }, e -> {
                this.double_mod = true;
            });
        } else if ("leftShift".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_leftShift = true;
            }, e -> {
                this.short_leftShift = true;
            }, e -> {
                this.int_leftShift = true;
            }, e -> {
                this.long_leftShift = true;
            }, e -> {
                this.float_leftShift = true;
            }, e -> {
                this.double_leftShift = true;
            });
        } else if ("rightShift".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_rightShift = true;
            }, e -> {
                this.short_rightShift = true;
            }, e -> {
                this.int_rightShift = true;
            }, e -> {
                this.long_rightShift = true;
            }, e -> {
                this.float_rightShift = true;
            }, e -> {
                this.double_rightShift = true;
            });
        } else if ("rightShiftUnsigned".equals(name)) {
            this.doCheckNumberOps(klazz, e -> {
                this.byte_rightShiftUnsigned = true;
            }, e -> {
                this.short_rightShiftUnsigned = true;
            }, e -> {
                this.int_rightShiftUnsigned = true;
            }, e -> {
                this.long_rightShiftUnsigned = true;
            }, e -> {
                this.float_rightShiftUnsigned = true;
            }, e -> {
                this.double_rightShiftUnsigned = true;
            });
        }
    }

    private void doCheckNumberOps(Class klazz, Consumer<Class> byteConsumer, Consumer<Class> shortConsumer, Consumer<Class> intConsumer, Consumer<Class> longConsumer, Consumer<Class> floatConsumer, Consumer<Class> doubleConsumer) {
        if (klazz == Byte.class) {
            byteConsumer.accept(klazz);
        } else if (klazz == Short.class) {
            shortConsumer.accept(klazz);
        } else if (klazz == Integer.class) {
            intConsumer.accept(klazz);
        } else if (klazz == Long.class) {
            longConsumer.accept(klazz);
        } else if (klazz == Float.class) {
            floatConsumer.accept(klazz);
        } else if (klazz == Double.class) {
            doubleConsumer.accept(klazz);
        } else if (klazz == Object.class) {
            byteConsumer.accept(klazz);
            shortConsumer.accept(klazz);
            intConsumer.accept(klazz);
            longConsumer.accept(klazz);
            floatConsumer.accept(klazz);
            doubleConsumer.accept(klazz);
        }
    }

    public static int plus(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static int plus(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static int plus(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static long plus(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (long)op1 + op2;
    }

    private static long plusSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).longValue();
    }

    public static double plus(byte op1, float op2) {
        if (NumberMathModificationInfo.instance.byte_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(byte op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", Float.valueOf(op2))).doubleValue();
    }

    public static double plus(byte op1, double op2) {
        if (NumberMathModificationInfo.instance.byte_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + op2;
    }

    private static double plusSlow(byte op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static int plus(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static int plus(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static int plus(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static long plus(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (long)op1 + op2;
    }

    private static long plusSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).longValue();
    }

    public static double plus(short op1, float op2) {
        if (NumberMathModificationInfo.instance.short_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(short op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", Float.valueOf(op2))).doubleValue();
    }

    public static double plus(short op1, double op2) {
        if (NumberMathModificationInfo.instance.short_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + op2;
    }

    private static double plusSlow(short op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static int plus(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static int plus(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static int plus(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static int plusSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).intValue();
    }

    public static long plus(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (long)op1 + op2;
    }

    private static long plusSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).longValue();
    }

    public static double plus(int op1, float op2) {
        if (NumberMathModificationInfo.instance.int_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(int op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", Float.valueOf(op2))).doubleValue();
    }

    public static double plus(int op1, double op2) {
        if (NumberMathModificationInfo.instance.int_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + op2;
    }

    private static double plusSlow(int op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static long plus(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + (long)op2;
    }

    private static long plusSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).longValue();
    }

    public static long plus(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + (long)op2;
    }

    private static long plusSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).longValue();
    }

    public static long plus(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + (long)op2;
    }

    private static long plusSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).longValue();
    }

    public static long plus(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static long plusSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).longValue();
    }

    public static double plus(long op1, float op2) {
        if (NumberMathModificationInfo.instance.long_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(long op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", Float.valueOf(op2))).doubleValue();
    }

    public static double plus(long op1, double op2) {
        if (NumberMathModificationInfo.instance.long_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + op2;
    }

    private static double plusSlow(long op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static double plus(float op1, byte op2) {
        if (NumberMathModificationInfo.instance.float_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(float op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "plus", op2)).doubleValue();
    }

    public static double plus(float op1, short op2) {
        if (NumberMathModificationInfo.instance.float_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(float op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "plus", op2)).doubleValue();
    }

    public static double plus(float op1, int op2) {
        if (NumberMathModificationInfo.instance.float_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(float op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "plus", op2)).doubleValue();
    }

    public static double plus(float op1, long op2) {
        if (NumberMathModificationInfo.instance.float_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(float op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "plus", op2)).doubleValue();
    }

    public static double plus(float op1, float op2) {
        if (NumberMathModificationInfo.instance.float_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + (double)op2;
    }

    private static double plusSlow(float op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "plus", Float.valueOf(op2))).doubleValue();
    }

    public static double plus(float op1, double op2) {
        if (NumberMathModificationInfo.instance.float_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return (double)op1 + op2;
    }

    private static double plusSlow(float op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "plus", op2)).doubleValue();
    }

    public static double plus(double op1, byte op2) {
        if (NumberMathModificationInfo.instance.double_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + (double)op2;
    }

    private static double plusSlow(double op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static double plus(double op1, short op2) {
        if (NumberMathModificationInfo.instance.double_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + (double)op2;
    }

    private static double plusSlow(double op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static double plus(double op1, int op2) {
        if (NumberMathModificationInfo.instance.double_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + (double)op2;
    }

    private static double plusSlow(double op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static double plus(double op1, long op2) {
        if (NumberMathModificationInfo.instance.double_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + (double)op2;
    }

    private static double plusSlow(double op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static double plus(double op1, float op2) {
        if (NumberMathModificationInfo.instance.double_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + (double)op2;
    }

    private static double plusSlow(double op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", Float.valueOf(op2))).doubleValue();
    }

    public static double plus(double op1, double op2) {
        if (NumberMathModificationInfo.instance.double_plus) {
            return NumberMathModificationInfo.plusSlow(op1, op2);
        }
        return op1 + op2;
    }

    private static double plusSlow(double op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "plus", op2)).doubleValue();
    }

    public static int minus(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static int minus(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static int minus(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static long minus(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (long)op1 - op2;
    }

    private static long minusSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).longValue();
    }

    public static double minus(byte op1, float op2) {
        if (NumberMathModificationInfo.instance.byte_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(byte op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", Float.valueOf(op2))).doubleValue();
    }

    public static double minus(byte op1, double op2) {
        if (NumberMathModificationInfo.instance.byte_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - op2;
    }

    private static double minusSlow(byte op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static int minus(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static int minus(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static int minus(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static long minus(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (long)op1 - op2;
    }

    private static long minusSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).longValue();
    }

    public static double minus(short op1, float op2) {
        if (NumberMathModificationInfo.instance.short_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(short op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", Float.valueOf(op2))).doubleValue();
    }

    public static double minus(short op1, double op2) {
        if (NumberMathModificationInfo.instance.short_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - op2;
    }

    private static double minusSlow(short op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static int minus(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static int minus(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static int minus(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static int minusSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).intValue();
    }

    public static long minus(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (long)op1 - op2;
    }

    private static long minusSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).longValue();
    }

    public static double minus(int op1, float op2) {
        if (NumberMathModificationInfo.instance.int_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(int op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", Float.valueOf(op2))).doubleValue();
    }

    public static double minus(int op1, double op2) {
        if (NumberMathModificationInfo.instance.int_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - op2;
    }

    private static double minusSlow(int op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static long minus(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - (long)op2;
    }

    private static long minusSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).longValue();
    }

    public static long minus(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - (long)op2;
    }

    private static long minusSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).longValue();
    }

    public static long minus(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - (long)op2;
    }

    private static long minusSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).longValue();
    }

    public static long minus(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static long minusSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).longValue();
    }

    public static double minus(long op1, float op2) {
        if (NumberMathModificationInfo.instance.long_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(long op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", Float.valueOf(op2))).doubleValue();
    }

    public static double minus(long op1, double op2) {
        if (NumberMathModificationInfo.instance.long_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - op2;
    }

    private static double minusSlow(long op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static double minus(float op1, byte op2) {
        if (NumberMathModificationInfo.instance.float_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(float op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "minus", op2)).doubleValue();
    }

    public static double minus(float op1, short op2) {
        if (NumberMathModificationInfo.instance.float_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(float op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "minus", op2)).doubleValue();
    }

    public static double minus(float op1, int op2) {
        if (NumberMathModificationInfo.instance.float_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(float op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "minus", op2)).doubleValue();
    }

    public static double minus(float op1, long op2) {
        if (NumberMathModificationInfo.instance.float_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(float op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "minus", op2)).doubleValue();
    }

    public static double minus(float op1, float op2) {
        if (NumberMathModificationInfo.instance.float_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - (double)op2;
    }

    private static double minusSlow(float op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "minus", Float.valueOf(op2))).doubleValue();
    }

    public static double minus(float op1, double op2) {
        if (NumberMathModificationInfo.instance.float_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return (double)op1 - op2;
    }

    private static double minusSlow(float op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "minus", op2)).doubleValue();
    }

    public static double minus(double op1, byte op2) {
        if (NumberMathModificationInfo.instance.double_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - (double)op2;
    }

    private static double minusSlow(double op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static double minus(double op1, short op2) {
        if (NumberMathModificationInfo.instance.double_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - (double)op2;
    }

    private static double minusSlow(double op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static double minus(double op1, int op2) {
        if (NumberMathModificationInfo.instance.double_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - (double)op2;
    }

    private static double minusSlow(double op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static double minus(double op1, long op2) {
        if (NumberMathModificationInfo.instance.double_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - (double)op2;
    }

    private static double minusSlow(double op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static double minus(double op1, float op2) {
        if (NumberMathModificationInfo.instance.double_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - (double)op2;
    }

    private static double minusSlow(double op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", Float.valueOf(op2))).doubleValue();
    }

    public static double minus(double op1, double op2) {
        if (NumberMathModificationInfo.instance.double_minus) {
            return NumberMathModificationInfo.minusSlow(op1, op2);
        }
        return op1 - op2;
    }

    private static double minusSlow(double op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "minus", op2)).doubleValue();
    }

    public static int multiply(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static int multiply(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static int multiply(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static long multiply(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (long)op1 * op2;
    }

    private static long multiplySlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).longValue();
    }

    public static double multiply(byte op1, float op2) {
        if (NumberMathModificationInfo.instance.byte_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(byte op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", Float.valueOf(op2))).doubleValue();
    }

    public static double multiply(byte op1, double op2) {
        if (NumberMathModificationInfo.instance.byte_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * op2;
    }

    private static double multiplySlow(byte op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static int multiply(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static int multiply(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static int multiply(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static long multiply(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (long)op1 * op2;
    }

    private static long multiplySlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).longValue();
    }

    public static double multiply(short op1, float op2) {
        if (NumberMathModificationInfo.instance.short_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(short op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", Float.valueOf(op2))).doubleValue();
    }

    public static double multiply(short op1, double op2) {
        if (NumberMathModificationInfo.instance.short_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * op2;
    }

    private static double multiplySlow(short op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static int multiply(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static int multiply(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static int multiply(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static int multiplySlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).intValue();
    }

    public static long multiply(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (long)op1 * op2;
    }

    private static long multiplySlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).longValue();
    }

    public static double multiply(int op1, float op2) {
        if (NumberMathModificationInfo.instance.int_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(int op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", Float.valueOf(op2))).doubleValue();
    }

    public static double multiply(int op1, double op2) {
        if (NumberMathModificationInfo.instance.int_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * op2;
    }

    private static double multiplySlow(int op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static long multiply(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * (long)op2;
    }

    private static long multiplySlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).longValue();
    }

    public static long multiply(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * (long)op2;
    }

    private static long multiplySlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).longValue();
    }

    public static long multiply(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * (long)op2;
    }

    private static long multiplySlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).longValue();
    }

    public static long multiply(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static long multiplySlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).longValue();
    }

    public static double multiply(long op1, float op2) {
        if (NumberMathModificationInfo.instance.long_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(long op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", Float.valueOf(op2))).doubleValue();
    }

    public static double multiply(long op1, double op2) {
        if (NumberMathModificationInfo.instance.long_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * op2;
    }

    private static double multiplySlow(long op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static double multiply(float op1, byte op2) {
        if (NumberMathModificationInfo.instance.float_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(float op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "multiply", op2)).doubleValue();
    }

    public static double multiply(float op1, short op2) {
        if (NumberMathModificationInfo.instance.float_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(float op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "multiply", op2)).doubleValue();
    }

    public static double multiply(float op1, int op2) {
        if (NumberMathModificationInfo.instance.float_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(float op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "multiply", op2)).doubleValue();
    }

    public static double multiply(float op1, long op2) {
        if (NumberMathModificationInfo.instance.float_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(float op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "multiply", op2)).doubleValue();
    }

    public static double multiply(float op1, float op2) {
        if (NumberMathModificationInfo.instance.float_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * (double)op2;
    }

    private static double multiplySlow(float op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "multiply", Float.valueOf(op2))).doubleValue();
    }

    public static double multiply(float op1, double op2) {
        if (NumberMathModificationInfo.instance.float_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return (double)op1 * op2;
    }

    private static double multiplySlow(float op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "multiply", op2)).doubleValue();
    }

    public static double multiply(double op1, byte op2) {
        if (NumberMathModificationInfo.instance.double_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * (double)op2;
    }

    private static double multiplySlow(double op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static double multiply(double op1, short op2) {
        if (NumberMathModificationInfo.instance.double_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * (double)op2;
    }

    private static double multiplySlow(double op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static double multiply(double op1, int op2) {
        if (NumberMathModificationInfo.instance.double_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * (double)op2;
    }

    private static double multiplySlow(double op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static double multiply(double op1, long op2) {
        if (NumberMathModificationInfo.instance.double_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * (double)op2;
    }

    private static double multiplySlow(double op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static double multiply(double op1, float op2) {
        if (NumberMathModificationInfo.instance.double_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * (double)op2;
    }

    private static double multiplySlow(double op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", Float.valueOf(op2))).doubleValue();
    }

    public static double multiply(double op1, double op2) {
        if (NumberMathModificationInfo.instance.double_multiply) {
            return NumberMathModificationInfo.multiplySlow(op1, op2);
        }
        return op1 * op2;
    }

    private static double multiplySlow(double op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "multiply", op2)).doubleValue();
    }

    public static int div(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static int div(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static int div(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static long div(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (long)op1 / op2;
    }

    private static long divSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).longValue();
    }

    public static double div(byte op1, float op2) {
        if (NumberMathModificationInfo.instance.byte_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(byte op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", Float.valueOf(op2))).doubleValue();
    }

    public static double div(byte op1, double op2) {
        if (NumberMathModificationInfo.instance.byte_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / op2;
    }

    private static double divSlow(byte op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static int div(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static int div(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static int div(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static long div(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (long)op1 / op2;
    }

    private static long divSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).longValue();
    }

    public static double div(short op1, float op2) {
        if (NumberMathModificationInfo.instance.short_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(short op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", Float.valueOf(op2))).doubleValue();
    }

    public static double div(short op1, double op2) {
        if (NumberMathModificationInfo.instance.short_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / op2;
    }

    private static double divSlow(short op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static int div(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static int div(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static int div(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int divSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).intValue();
    }

    public static long div(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (long)op1 / op2;
    }

    private static long divSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).longValue();
    }

    public static double div(int op1, float op2) {
        if (NumberMathModificationInfo.instance.int_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(int op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", Float.valueOf(op2))).doubleValue();
    }

    public static double div(int op1, double op2) {
        if (NumberMathModificationInfo.instance.int_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / op2;
    }

    private static double divSlow(int op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static long div(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / (long)op2;
    }

    private static long divSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).longValue();
    }

    public static long div(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / (long)op2;
    }

    private static long divSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).longValue();
    }

    public static long div(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / (long)op2;
    }

    private static long divSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).longValue();
    }

    public static long div(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static long divSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).longValue();
    }

    public static double div(long op1, float op2) {
        if (NumberMathModificationInfo.instance.long_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(long op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", Float.valueOf(op2))).doubleValue();
    }

    public static double div(long op1, double op2) {
        if (NumberMathModificationInfo.instance.long_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / op2;
    }

    private static double divSlow(long op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static double div(float op1, byte op2) {
        if (NumberMathModificationInfo.instance.float_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(float op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "div", op2)).doubleValue();
    }

    public static double div(float op1, short op2) {
        if (NumberMathModificationInfo.instance.float_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(float op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "div", op2)).doubleValue();
    }

    public static double div(float op1, int op2) {
        if (NumberMathModificationInfo.instance.float_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(float op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "div", op2)).doubleValue();
    }

    public static double div(float op1, long op2) {
        if (NumberMathModificationInfo.instance.float_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(float op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "div", op2)).doubleValue();
    }

    public static double div(float op1, float op2) {
        if (NumberMathModificationInfo.instance.float_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / (double)op2;
    }

    private static double divSlow(float op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "div", Float.valueOf(op2))).doubleValue();
    }

    public static double div(float op1, double op2) {
        if (NumberMathModificationInfo.instance.float_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return (double)op1 / op2;
    }

    private static double divSlow(float op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(Float.valueOf(op1), "div", op2)).doubleValue();
    }

    public static double div(double op1, byte op2) {
        if (NumberMathModificationInfo.instance.double_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / (double)op2;
    }

    private static double divSlow(double op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static double div(double op1, short op2) {
        if (NumberMathModificationInfo.instance.double_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / (double)op2;
    }

    private static double divSlow(double op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static double div(double op1, int op2) {
        if (NumberMathModificationInfo.instance.double_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / (double)op2;
    }

    private static double divSlow(double op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static double div(double op1, long op2) {
        if (NumberMathModificationInfo.instance.double_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / (double)op2;
    }

    private static double divSlow(double op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static double div(double op1, float op2) {
        if (NumberMathModificationInfo.instance.double_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / (double)op2;
    }

    private static double divSlow(double op1, float op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", Float.valueOf(op2))).doubleValue();
    }

    public static double div(double op1, double op2) {
        if (NumberMathModificationInfo.instance.double_div) {
            return NumberMathModificationInfo.divSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static double divSlow(double op1, double op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "div", op2)).doubleValue();
    }

    public static int or(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static int or(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static int or(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static long or(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return (long)op1 | op2;
    }

    private static long orSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).longValue();
    }

    public static int or(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static int or(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static int or(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static long or(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return (long)op1 | op2;
    }

    private static long orSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).longValue();
    }

    public static int or(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static int or(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static int or(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static int orSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).intValue();
    }

    public static long or(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return (long)op1 | op2;
    }

    private static long orSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).longValue();
    }

    public static long or(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | (long)op2;
    }

    private static long orSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).longValue();
    }

    public static long or(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | (long)op2;
    }

    private static long orSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).longValue();
    }

    public static long or(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | (long)op2;
    }

    private static long orSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).longValue();
    }

    public static long or(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_or) {
            return NumberMathModificationInfo.orSlow(op1, op2);
        }
        return op1 | op2;
    }

    private static long orSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "or", op2)).longValue();
    }

    public static int and(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static int and(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static int and(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static long and(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return (long)op1 & op2;
    }

    private static long andSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).longValue();
    }

    public static int and(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static int and(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static int and(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static long and(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return (long)op1 & op2;
    }

    private static long andSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).longValue();
    }

    public static int and(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static int and(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static int and(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static int andSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).intValue();
    }

    public static long and(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return (long)op1 & op2;
    }

    private static long andSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).longValue();
    }

    public static long and(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & (long)op2;
    }

    private static long andSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).longValue();
    }

    public static long and(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & (long)op2;
    }

    private static long andSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).longValue();
    }

    public static long and(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & (long)op2;
    }

    private static long andSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).longValue();
    }

    public static long and(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_and) {
            return NumberMathModificationInfo.andSlow(op1, op2);
        }
        return op1 & op2;
    }

    private static long andSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "and", op2)).longValue();
    }

    public static int xor(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static int xor(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static int xor(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static long xor(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return (long)op1 ^ op2;
    }

    private static long xorSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).longValue();
    }

    public static int xor(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static int xor(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static int xor(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static long xor(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return (long)op1 ^ op2;
    }

    private static long xorSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).longValue();
    }

    public static int xor(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static int xor(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static int xor(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static int xorSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).intValue();
    }

    public static long xor(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return (long)op1 ^ op2;
    }

    private static long xorSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).longValue();
    }

    public static long xor(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ (long)op2;
    }

    private static long xorSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).longValue();
    }

    public static long xor(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ (long)op2;
    }

    private static long xorSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).longValue();
    }

    public static long xor(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ (long)op2;
    }

    private static long xorSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).longValue();
    }

    public static long xor(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_xor) {
            return NumberMathModificationInfo.xorSlow(op1, op2);
        }
        return op1 ^ op2;
    }

    private static long xorSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "xor", op2)).longValue();
    }

    public static int intdiv(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static int intdiv(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static int intdiv(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static long intdiv(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return (long)op1 / op2;
    }

    private static long intdivSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).longValue();
    }

    public static int intdiv(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static int intdiv(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static int intdiv(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static long intdiv(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return (long)op1 / op2;
    }

    private static long intdivSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).longValue();
    }

    public static int intdiv(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static int intdiv(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static int intdiv(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static int intdivSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).intValue();
    }

    public static long intdiv(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return (long)op1 / op2;
    }

    private static long intdivSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).longValue();
    }

    public static long intdiv(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / (long)op2;
    }

    private static long intdivSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).longValue();
    }

    public static long intdiv(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / (long)op2;
    }

    private static long intdivSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).longValue();
    }

    public static long intdiv(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / (long)op2;
    }

    private static long intdivSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).longValue();
    }

    public static long intdiv(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_intdiv) {
            return NumberMathModificationInfo.intdivSlow(op1, op2);
        }
        return op1 / op2;
    }

    private static long intdivSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "intdiv", op2)).longValue();
    }

    public static int mod(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static int mod(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static int mod(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static long mod(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return (long)op1 % op2;
    }

    private static long modSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).longValue();
    }

    public static int mod(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static int mod(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static int mod(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static long mod(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return (long)op1 % op2;
    }

    private static long modSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).longValue();
    }

    public static int mod(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static int mod(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static int mod(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static int modSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).intValue();
    }

    public static long mod(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return (long)op1 % op2;
    }

    private static long modSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).longValue();
    }

    public static long mod(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % (long)op2;
    }

    private static long modSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).longValue();
    }

    public static long mod(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % (long)op2;
    }

    private static long modSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).longValue();
    }

    public static long mod(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % (long)op2;
    }

    private static long modSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).longValue();
    }

    public static long mod(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_mod) {
            return NumberMathModificationInfo.modSlow(op1, op2);
        }
        return op1 % op2;
    }

    private static long modSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "mod", op2)).longValue();
    }

    public static int leftShift(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static int leftShift(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static int leftShift(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static long leftShift(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return (long)op1 << (int)op2;
    }

    private static long leftShiftSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).longValue();
    }

    public static int leftShift(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static int leftShift(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static int leftShift(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static long leftShift(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return (long)op1 << (int)op2;
    }

    private static long leftShiftSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).longValue();
    }

    public static int leftShift(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static int leftShift(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static int leftShift(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << op2;
    }

    private static int leftShiftSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).intValue();
    }

    public static long leftShift(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return (long)op1 << (int)op2;
    }

    private static long leftShiftSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).longValue();
    }

    public static long leftShift(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << (int)((long)op2);
    }

    private static long leftShiftSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).longValue();
    }

    public static long leftShift(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << (int)((long)op2);
    }

    private static long leftShiftSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).longValue();
    }

    public static long leftShift(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << (int)((long)op2);
    }

    private static long leftShiftSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).longValue();
    }

    public static long leftShift(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_leftShift) {
            return NumberMathModificationInfo.leftShiftSlow(op1, op2);
        }
        return op1 << (int)op2;
    }

    private static long leftShiftSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "leftShift", op2)).longValue();
    }

    public static int rightShift(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static int rightShift(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static int rightShift(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static long rightShift(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return (long)op1 >> (int)op2;
    }

    private static long rightShiftSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).longValue();
    }

    public static int rightShift(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static int rightShift(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static int rightShift(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static long rightShift(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return (long)op1 >> (int)op2;
    }

    private static long rightShiftSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).longValue();
    }

    public static int rightShift(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static int rightShift(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static int rightShift(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> op2;
    }

    private static int rightShiftSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).intValue();
    }

    public static long rightShift(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return (long)op1 >> (int)op2;
    }

    private static long rightShiftSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).longValue();
    }

    public static long rightShift(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> (int)((long)op2);
    }

    private static long rightShiftSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).longValue();
    }

    public static long rightShift(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> (int)((long)op2);
    }

    private static long rightShiftSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).longValue();
    }

    public static long rightShift(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> (int)((long)op2);
    }

    private static long rightShiftSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).longValue();
    }

    public static long rightShift(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_rightShift) {
            return NumberMathModificationInfo.rightShiftSlow(op1, op2);
        }
        return op1 >> (int)op2;
    }

    private static long rightShiftSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShift", op2)).longValue();
    }

    public static int rightShiftUnsigned(byte op1, byte op2) {
        if (NumberMathModificationInfo.instance.byte_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(byte op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static int rightShiftUnsigned(byte op1, short op2) {
        if (NumberMathModificationInfo.instance.byte_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(byte op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static int rightShiftUnsigned(byte op1, int op2) {
        if (NumberMathModificationInfo.instance.byte_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(byte op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static long rightShiftUnsigned(byte op1, long op2) {
        if (NumberMathModificationInfo.instance.byte_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return (long)op1 >>> (int)op2;
    }

    private static long rightShiftUnsignedSlow(byte op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).longValue();
    }

    public static int rightShiftUnsigned(short op1, byte op2) {
        if (NumberMathModificationInfo.instance.short_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(short op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static int rightShiftUnsigned(short op1, short op2) {
        if (NumberMathModificationInfo.instance.short_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(short op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static int rightShiftUnsigned(short op1, int op2) {
        if (NumberMathModificationInfo.instance.short_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(short op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static long rightShiftUnsigned(short op1, long op2) {
        if (NumberMathModificationInfo.instance.short_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return (long)op1 >>> (int)op2;
    }

    private static long rightShiftUnsignedSlow(short op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).longValue();
    }

    public static int rightShiftUnsigned(int op1, byte op2) {
        if (NumberMathModificationInfo.instance.int_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(int op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static int rightShiftUnsigned(int op1, short op2) {
        if (NumberMathModificationInfo.instance.int_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(int op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static int rightShiftUnsigned(int op1, int op2) {
        if (NumberMathModificationInfo.instance.int_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> op2;
    }

    private static int rightShiftUnsignedSlow(int op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).intValue();
    }

    public static long rightShiftUnsigned(int op1, long op2) {
        if (NumberMathModificationInfo.instance.int_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return (long)op1 >>> (int)op2;
    }

    private static long rightShiftUnsignedSlow(int op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).longValue();
    }

    public static long rightShiftUnsigned(long op1, byte op2) {
        if (NumberMathModificationInfo.instance.long_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> (int)((long)op2);
    }

    private static long rightShiftUnsignedSlow(long op1, byte op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).longValue();
    }

    public static long rightShiftUnsigned(long op1, short op2) {
        if (NumberMathModificationInfo.instance.long_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> (int)((long)op2);
    }

    private static long rightShiftUnsignedSlow(long op1, short op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).longValue();
    }

    public static long rightShiftUnsigned(long op1, int op2) {
        if (NumberMathModificationInfo.instance.long_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> (int)((long)op2);
    }

    private static long rightShiftUnsignedSlow(long op1, int op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).longValue();
    }

    public static long rightShiftUnsigned(long op1, long op2) {
        if (NumberMathModificationInfo.instance.long_rightShiftUnsigned) {
            return NumberMathModificationInfo.rightShiftUnsignedSlow(op1, op2);
        }
        return op1 >>> (int)op2;
    }

    private static long rightShiftUnsignedSlow(long op1, long op2) {
        return ((Number)InvokerHelper.invokeMethod(op1, "rightShiftUnsigned", op2)).longValue();
    }

    static {
        Collections.addAll(NAMES, "plus", "minus", "multiply", "div", "compareTo", "or", "and", "xor", "intdiv", "mod", "leftShift", "rightShift", "rightShiftUnsigned");
    }
}

