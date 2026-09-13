/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp205.general;

import be.tpvision.smartcontrol.codecs.sicp.Codec;
import be.tpvision.smartcontrol.codecs.sicp205.miscellaneous.BootOnSourceCodec;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import java.util.ArrayList;
import java.util.Map;

public class NumberOfInputSourcesCodec
extends Codec<StringWrapper> {
    private static NumberOfInputSourcesCodec numberOfInputSourcesCodec;

    private NumberOfInputSourcesCodec() {
        super(StringWrapper.class);
    }

    public static synchronized NumberOfInputSourcesCodec getInstance() {
        if (numberOfInputSourcesCodec == null) {
            numberOfInputSourcesCodec = new NumberOfInputSourcesCodec();
        }
        return numberOfInputSourcesCodec;
    }

    @Override
    public byte[] toProtocol(StringWrapper numberOfInputSources) {
        return new byte[0];
    }

    @Override
    public StringWrapper toDomain(byte[] bytes) {
        if (bytes == null || bytes.length < 2) {
            return null;
        }
        ArrayList<String> inputSourceTypeNames = new ArrayList<String>();
        Map<Byte, BootOnSource.VideoSourceType> protocolVideoSourceTypes = BootOnSourceCodec.getProtocolvideosourcetypes();
        int j = bytes.length;
        for (int i = 1; i < j; ++i) {
            byte typeByte = bytes[i];
            if (!protocolVideoSourceTypes.containsKey(typeByte)) continue;
            inputSourceTypeNames.add(protocolVideoSourceTypes.get(typeByte).name());
        }
        return new StringWrapper(String.join((CharSequence)",", inputSourceTypeNames));
    }
}

