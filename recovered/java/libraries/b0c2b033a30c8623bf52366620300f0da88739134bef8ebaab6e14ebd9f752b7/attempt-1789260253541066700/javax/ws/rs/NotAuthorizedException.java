/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class NotAuthorizedException
extends ClientErrorException {
    private static final long serialVersionUID = -3156040750581929702L;
    private transient List<Object> challenges;

    public NotAuthorizedException(Object challenge, Object ... moreChallenges) {
        super(NotAuthorizedException.createUnauthorizedResponse(challenge, moreChallenges));
        this.challenges = NotAuthorizedException.cacheChallenges(challenge, moreChallenges);
    }

    public NotAuthorizedException(String message, Object challenge, Object ... moreChallenges) {
        super(message, NotAuthorizedException.createUnauthorizedResponse(challenge, moreChallenges));
        this.challenges = NotAuthorizedException.cacheChallenges(challenge, moreChallenges);
    }

    public NotAuthorizedException(Response response) {
        super(NotAuthorizedException.validate(response, Response.Status.UNAUTHORIZED));
    }

    public NotAuthorizedException(String message, Response response) {
        super(message, NotAuthorizedException.validate(response, Response.Status.UNAUTHORIZED));
    }

    public NotAuthorizedException(Throwable cause, Object challenge, Object ... moreChallenges) {
        super(NotAuthorizedException.createUnauthorizedResponse(challenge, moreChallenges), cause);
        this.challenges = NotAuthorizedException.cacheChallenges(challenge, moreChallenges);
    }

    public NotAuthorizedException(String message, Throwable cause, Object challenge, Object ... moreChallenges) {
        super(message, NotAuthorizedException.createUnauthorizedResponse(challenge, moreChallenges), cause);
        this.challenges = NotAuthorizedException.cacheChallenges(challenge, moreChallenges);
    }

    public NotAuthorizedException(Response response, Throwable cause) {
        super(NotAuthorizedException.validate(response, Response.Status.UNAUTHORIZED), cause);
    }

    public NotAuthorizedException(String message, Response response, Throwable cause) {
        super(message, NotAuthorizedException.validate(response, Response.Status.UNAUTHORIZED), cause);
    }

    public List<Object> getChallenges() {
        if (this.challenges == null) {
            this.challenges = (List)this.getResponse().getHeaders().get("WWW-Authenticate");
        }
        return this.challenges;
    }

    private static Response createUnauthorizedResponse(Object challenge, Object[] otherChallenges) {
        if (challenge == null) {
            throw new NullPointerException("Primary challenge parameter must not be null.");
        }
        Response.ResponseBuilder builder = Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", challenge);
        if (otherChallenges != null) {
            for (Object oc : otherChallenges) {
                builder.header("WWW-Authenticate", oc);
            }
        }
        return builder.build();
    }

    private static List<Object> cacheChallenges(Object challenge, Object[] moreChallenges) {
        ArrayList<Object> temp = new ArrayList<Object>(1 + (moreChallenges == null ? 0 : moreChallenges.length));
        temp.add(challenge);
        if (moreChallenges != null) {
            temp.addAll(Arrays.asList(moreChallenges));
        }
        return Collections.unmodifiableList(temp);
    }
}

