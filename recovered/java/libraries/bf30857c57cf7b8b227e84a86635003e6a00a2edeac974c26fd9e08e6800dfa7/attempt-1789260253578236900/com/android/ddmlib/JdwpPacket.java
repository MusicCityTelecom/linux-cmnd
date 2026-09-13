/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.BadPacketException;
import com.android.ddmlib.ChunkHandler;
import com.android.ddmlib.Log;
import com.android.ddmlib.jdwp.JdwpCommands;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

public final class JdwpPacket {
    public static final int JDWP_HEADER_LEN = 11;
    private static final int REPLY_PACKET = 128;
    private final ByteBuffer mBuffer;
    private int mLength;
    private int mId;
    private int mFlags;
    private int mCmdSet;
    private int mCmd;
    private int mErrCode;
    private static int sSerialId = 0x40000000;

    JdwpPacket(ByteBuffer buf) {
        this.mBuffer = buf;
    }

    void finishPacket(int cmdSet, int cmd, int payloadLength) {
        ByteOrder oldOrder = this.mBuffer.order();
        this.mBuffer.order(ChunkHandler.CHUNK_ORDER);
        this.mLength = 11 + payloadLength;
        this.mId = JdwpPacket.getNextSerial();
        this.mFlags = 0;
        this.mCmdSet = cmdSet;
        this.mCmd = cmd;
        this.mBuffer.putInt(0, this.mLength);
        this.mBuffer.putInt(4, this.mId);
        this.mBuffer.put(8, (byte)this.mFlags);
        this.mBuffer.put(9, (byte)this.mCmdSet);
        this.mBuffer.put(10, (byte)this.mCmd);
        this.mBuffer.order(oldOrder);
        this.mBuffer.position(this.mLength);
    }

    private static synchronized int getNextSerial() {
        return sSerialId++;
    }

    public ByteBuffer getPayload() {
        int oldPosn = this.mBuffer.position();
        this.mBuffer.position(11);
        ByteBuffer buf = this.mBuffer.slice();
        this.mBuffer.position(oldPosn);
        if (this.mLength > 0) {
            buf.limit(this.mLength - 11);
        }
        buf.order(ChunkHandler.CHUNK_ORDER);
        return buf;
    }

    public boolean isReply() {
        return (this.mFlags & 0x80) != 0;
    }

    boolean isError() {
        return this.isReply() && this.mErrCode != 0;
    }

    boolean isEmpty() {
        return this.mLength == 11;
    }

    public int getId() {
        return this.mId;
    }

    int getLength() {
        return this.mLength;
    }

    void write(SocketChannel chan) throws IOException {
        assert (this.mLength > 0);
        int oldPosn = this.mBuffer.position();
        this.mBuffer.position(0);
        this.mBuffer.limit(this.mLength);
        while (this.mBuffer.position() != this.mBuffer.limit()) {
            chan.write(this.mBuffer);
        }
        assert (this.mBuffer.position() == this.mLength);
        this.mBuffer.limit(this.mBuffer.capacity());
        this.mBuffer.position(oldPosn);
    }

    void move(ByteBuffer buf) {
        int oldPosn = this.mBuffer.position();
        this.mBuffer.position(0);
        this.mBuffer.limit(this.mLength);
        buf.put(this.mBuffer);
        this.mBuffer.limit(this.mBuffer.capacity());
        this.mBuffer.position(oldPosn);
    }

    void consume() {
        this.mBuffer.flip();
        this.mBuffer.position(this.mLength);
        this.mBuffer.compact();
        this.mLength = 0;
    }

    static JdwpPacket findPacket(ByteBuffer buf) {
        int count = buf.position();
        if (count < 11) {
            return null;
        }
        ByteOrder oldOrder = buf.order();
        buf.order(ChunkHandler.CHUNK_ORDER);
        int length = buf.getInt(0);
        int id = buf.getInt(4);
        int flags = buf.get(8) & 0xFF;
        int cmdSet = buf.get(9) & 0xFF;
        int cmd = buf.get(10) & 0xFF;
        buf.order(oldOrder);
        if (length < 11) {
            throw new BadPacketException();
        }
        if (count < length) {
            return null;
        }
        JdwpPacket pkt = new JdwpPacket(buf);
        pkt.mLength = length;
        pkt.mId = id;
        pkt.mFlags = flags;
        if ((flags & 0x80) == 0) {
            pkt.mCmdSet = cmdSet;
            pkt.mCmd = cmd;
            pkt.mErrCode = -1;
        } else {
            pkt.mCmdSet = -1;
            pkt.mCmd = -1;
            pkt.mErrCode = cmdSet | cmd << 8;
        }
        return pkt;
    }

    public String toString() {
        return this.isReply() ? " < # " + this.mId : " > " + this.mCmdSet + "." + this.mCmd + " # " + this.mId;
    }

    public boolean is(int cmdSet, int cmd) {
        return cmdSet == this.mCmdSet && cmd == this.mCmd;
    }

    public void log(String action) {
        if (Log.isAtLeast(Log.LogLevel.DEBUG)) {
            if (this.isReply()) {
                Log.d("jdwp", String.format("%s: jdwp reply: id=%d, length=%d, flags=%d, error=%d", action, this.mId, this.mLength, this.mFlags, this.mErrCode));
            } else {
                Log.d("jdwp", String.format("%s: jdwp request: id=%d, length=%d, flags=%d, cmdSet=%s, cmd=%s", action, this.mId, this.mLength, this.mFlags, JdwpCommands.commandSetToString(this.mCmdSet), JdwpCommands.commandToString(this.mCmdSet, this.mCmd)));
            }
        }
    }
}

