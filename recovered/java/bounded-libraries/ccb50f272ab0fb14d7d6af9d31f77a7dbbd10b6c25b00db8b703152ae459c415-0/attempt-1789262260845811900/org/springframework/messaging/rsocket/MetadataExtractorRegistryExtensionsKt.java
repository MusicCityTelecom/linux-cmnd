/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  org.springframework.core.ParameterizedTypeReference
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.rsocket;

import java.util.Map;
import java.util.function.BiConsumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.rsocket.MetadataExtractorRegistry;
import org.springframework.util.MimeType;

@Metadata(mv={1, 1, 18}, bv={1, 0, 3}, k=2, d1={"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0002\u001aI\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062&\b\b\u0010\u0007\u001a \u0012\u0004\u0012\u0002H\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00030\t\u0012\u0004\u0012\u00020\u00010\bH\u0086\b\u001a-\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0086\b\u00a8\u0006\f"}, d2={"metadataToExtract", "", "T", "", "Lorg/springframework/messaging/rsocket/MetadataExtractorRegistry;", "mimeType", "Lorg/springframework/util/MimeType;", "mapper", "Lkotlin/Function2;", "", "", "name", "spring-messaging"})
public final class MetadataExtractorRegistryExtensionsKt {
    public static final /* synthetic */ <T> void metadataToExtract(MetadataExtractorRegistry $this$metadataToExtract, MimeType mimeType, String name) {
        int $i$f$metadataToExtract = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$metadataToExtract, (String)"$this$metadataToExtract");
        Intrinsics.checkParameterIsNotNull((Object)mimeType, (String)"mimeType");
        Intrinsics.needClassReification();
        $this$metadataToExtract.metadataToExtract(mimeType, (ParameterizedTypeReference)new ParameterizedTypeReference<T>(){}, name);
    }

    public static /* synthetic */ void metadataToExtract$default(MetadataExtractorRegistry $this$metadataToExtract, MimeType mimeType, String name, int n, Object object) {
        if ((n & 2) != 0) {
            name = null;
        }
        boolean $i$f$metadataToExtract = false;
        Intrinsics.checkParameterIsNotNull((Object)$this$metadataToExtract, (String)"$this$metadataToExtract");
        Intrinsics.checkParameterIsNotNull((Object)mimeType, (String)"mimeType");
        Intrinsics.needClassReification();
        $this$metadataToExtract.metadataToExtract(mimeType, (ParameterizedTypeReference)new /* invalid duplicate definition of identical inner class */, name);
    }

    public static final /* synthetic */ <T> void metadataToExtract(MetadataExtractorRegistry $this$metadataToExtract, MimeType mimeType, Function2<? super T, ? super Map<String, Object>, Unit> mapper) {
        int $i$f$metadataToExtract = 0;
        Intrinsics.checkParameterIsNotNull((Object)$this$metadataToExtract, (String)"$this$metadataToExtract");
        Intrinsics.checkParameterIsNotNull((Object)mimeType, (String)"mimeType");
        Intrinsics.checkParameterIsNotNull(mapper, (String)"mapper");
        Intrinsics.needClassReification();
        Function2<? super T, ? super Map<String, Object>, Unit> function2 = mapper;
        $this$metadataToExtract.metadataToExtract(mimeType, (ParameterizedTypeReference)new ParameterizedTypeReference<T>(){}, (BiConsumer)new BiConsumer(function2){
            private final /* synthetic */ Function2 function;
            {
                this.function = function2;
            }

            public final /* synthetic */ void accept(Object p0, Object p1) {
                Intrinsics.checkExpressionValueIsNotNull((Object)this.function.invoke(p0, p1), (String)"invoke(...)");
            }
        });
    }
}

