/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  org.springframework.context.ConfigurableApplicationContext
 */
package org.springframework.boot;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Metadata(mv={1, 1, 18}, bv={1, 0, 3}, k=2, d1={"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a.\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u0006H\u0086\b\u00a2\u0006\u0002\u0010\u0007\u001aG\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u00062\u0017\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u00a2\u0006\u0002\b\fH\u0086\b\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, d2={"runApplication", "Lorg/springframework/context/ConfigurableApplicationContext;", "T", "", "args", "", "", "([Ljava/lang/String;)Lorg/springframework/context/ConfigurableApplicationContext;", "init", "Lkotlin/Function1;", "Lorg/springframework/boot/SpringApplication;", "", "Lkotlin/ExtensionFunctionType;", "([Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Lorg/springframework/context/ConfigurableApplicationContext;", "spring-boot"})
public final class SpringApplicationExtensionsKt {
    public static final /* synthetic */ <T> ConfigurableApplicationContext runApplication(String ... args) {
        int $i$f$runApplication = 0;
        Intrinsics.checkParameterIsNotNull((Object)args, (String)"args");
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Object.class, Arrays.copyOf(args, args.length));
        Intrinsics.checkExpressionValueIsNotNull((Object)configurableApplicationContext, (String)"SpringApplication.run(T::class.java, *args)");
        return configurableApplicationContext;
    }

    public static final /* synthetic */ <T> ConfigurableApplicationContext runApplication(String[] args, Function1<? super SpringApplication, Unit> init) {
        int $i$f$runApplication = 0;
        Intrinsics.checkParameterIsNotNull((Object)args, (String)"args");
        Intrinsics.checkParameterIsNotNull(init, (String)"init");
        Class[] classArray = new Class[1];
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        classArray[0] = Object.class;
        SpringApplication springApplication = new SpringApplication(classArray);
        init.invoke((Object)springApplication);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(Arrays.copyOf(args, args.length));
        Intrinsics.checkExpressionValueIsNotNull((Object)configurableApplicationContext, (String)"SpringApplication(T::cla\u2026a).apply(init).run(*args)");
        return configurableApplicationContext;
    }
}

