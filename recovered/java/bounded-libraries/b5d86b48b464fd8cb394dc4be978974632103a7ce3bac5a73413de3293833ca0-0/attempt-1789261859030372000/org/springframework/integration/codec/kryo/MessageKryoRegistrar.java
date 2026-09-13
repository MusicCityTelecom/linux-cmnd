/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.Registration
 *  com.esotericsoftware.kryo.Serializer
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.messaging.support.ErrorMessage
 *  org.springframework.messaging.support.GenericMessage
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.integration.codec.kryo.AbstractKryoRegistrar;
import org.springframework.integration.codec.kryo.MessageHeadersSerializer;
import org.springframework.integration.codec.kryo.MutableMessageHeadersSerializer;
import org.springframework.integration.message.AdviceMessage;
import org.springframework.integration.support.MutableMessage;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;

public class MessageKryoRegistrar
extends AbstractKryoRegistrar {
    private int genericMessageRegistrationId = 43;
    private int errorMessageRegistrationId = 44;
    private int adviceMessageRegistrationId = 45;
    private int mutableMessageRegistrationId = 46;
    private int messageHeadersRegistrationId = 41;
    private int mutableMessageHeadersRegistrationId = 42;
    private int hashMapRegistrationId = 47;
    private int uuidRegistrationId = 48;

    public void setMessageHeadersRegistrationId(int messageHeadersRegistrationId) {
        this.messageHeadersRegistrationId = messageHeadersRegistrationId;
    }

    public void setMutableMessageHeadersRegistrationId(int mutableMessageHeadersRegistrationId) {
        this.mutableMessageHeadersRegistrationId = mutableMessageHeadersRegistrationId;
    }

    public void setGenericMessageRegistrationId(int genericMessageRegistrationId) {
        this.genericMessageRegistrationId = genericMessageRegistrationId;
    }

    public void setErrorMessageRegistrationId(int errorMessageRegistrationId) {
        this.errorMessageRegistrationId = errorMessageRegistrationId;
    }

    public void setAdviceMessageRegistrationId(int adviceMessageRegistrationId) {
        this.adviceMessageRegistrationId = adviceMessageRegistrationId;
    }

    public void setMutableMessageRegistrationId(int mutableMessageRegistrationId) {
        this.mutableMessageRegistrationId = mutableMessageRegistrationId;
    }

    public void setHashMapRegistrationId(int hashMapRegistrationId) {
        this.hashMapRegistrationId = hashMapRegistrationId;
    }

    public void setUuidRegistrationId(int uuidRegistrationId) {
        this.uuidRegistrationId = uuidRegistrationId;
    }

    @Override
    public void registerTypes(Kryo kryo) {
        super.registerTypes(kryo);
        kryo.register(GenericMessage.class, this.genericMessageRegistrationId);
        kryo.register(ErrorMessage.class, this.errorMessageRegistrationId);
        kryo.register(AdviceMessage.class, this.adviceMessageRegistrationId);
        kryo.register(MutableMessage.class, this.mutableMessageRegistrationId);
        kryo.register(HashMap.class, this.hashMapRegistrationId);
        kryo.register(UUID.class, this.uuidRegistrationId);
    }

    @Override
    public List<Registration> getRegistrations() {
        return Arrays.asList(new Registration(MessageHeaders.class, (Serializer)new MessageHeadersSerializer(), this.messageHeadersRegistrationId), new Registration(MutableMessageHeaders.class, (Serializer)new MutableMessageHeadersSerializer(), this.mutableMessageHeadersRegistrationId));
    }
}

