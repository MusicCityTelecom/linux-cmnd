/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import javax.ws.rs.core.Response;

public final class Statuses {
    public static Response.StatusType from(int code) {
        Response.Status result = Response.Status.fromStatusCode(code);
        return result != null ? result : new StatusImpl(code, "");
    }

    public static Response.StatusType from(int code, String reason) {
        return new StatusImpl(code, reason);
    }

    public static Response.StatusType from(Response.StatusType status, String reason) {
        return new StatusImpl(status.getStatusCode(), reason);
    }

    private Statuses() {
        throw new AssertionError((Object)"Instantiation not allowed.");
    }

    private static final class StatusImpl
    implements Response.StatusType {
        private final int code;
        private final String reason;
        private final Response.Status.Family family;

        private StatusImpl(int code, String reason) {
            this.code = code;
            this.reason = reason;
            this.family = Response.Status.Family.familyOf(code);
        }

        @Override
        public int getStatusCode() {
            return this.code;
        }

        @Override
        public String getReasonPhrase() {
            return this.reason;
        }

        public String toString() {
            return this.reason;
        }

        @Override
        public Response.Status.Family getFamily() {
            return this.family;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Response.StatusType)) {
                return false;
            }
            Response.StatusType status = (Response.StatusType)o;
            if (this.code != status.getStatusCode()) {
                return false;
            }
            if (this.family != status.getFamily()) {
                return false;
            }
            return !(this.reason != null ? !this.reason.equals(status.getReasonPhrase()) : status.getReasonPhrase() != null);
        }

        public int hashCode() {
            int result = this.code;
            result = 31 * result + (this.reason != null ? this.reason.hashCode() : 0);
            result = 31 * result + this.family.hashCode();
            return result;
        }
    }
}

