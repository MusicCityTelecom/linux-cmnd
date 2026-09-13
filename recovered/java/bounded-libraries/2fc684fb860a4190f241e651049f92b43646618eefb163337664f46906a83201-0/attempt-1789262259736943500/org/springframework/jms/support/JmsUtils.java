/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.IllegalStateException
 *  javax.jms.InvalidClientIDException
 *  javax.jms.InvalidDestinationException
 *  javax.jms.InvalidSelectorException
 *  javax.jms.JMSException
 *  javax.jms.JMSSecurityException
 *  javax.jms.MessageConsumer
 *  javax.jms.MessageEOFException
 *  javax.jms.MessageFormatException
 *  javax.jms.MessageNotReadableException
 *  javax.jms.MessageNotWriteableException
 *  javax.jms.MessageProducer
 *  javax.jms.QueueBrowser
 *  javax.jms.QueueRequestor
 *  javax.jms.ResourceAllocationException
 *  javax.jms.Session
 *  javax.jms.TransactionInProgressException
 *  javax.jms.TransactionRolledBackException
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.jms.support;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.QueueBrowser;
import javax.jms.QueueRequestor;
import javax.jms.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.IllegalStateException;
import org.springframework.jms.InvalidClientIDException;
import org.springframework.jms.InvalidDestinationException;
import org.springframework.jms.InvalidSelectorException;
import org.springframework.jms.JmsException;
import org.springframework.jms.JmsSecurityException;
import org.springframework.jms.MessageEOFException;
import org.springframework.jms.MessageFormatException;
import org.springframework.jms.MessageNotReadableException;
import org.springframework.jms.MessageNotWriteableException;
import org.springframework.jms.ResourceAllocationException;
import org.springframework.jms.TransactionInProgressException;
import org.springframework.jms.TransactionRolledBackException;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public abstract class JmsUtils {
    private static final Log logger = LogFactory.getLog(JmsUtils.class);

    public static void closeConnection(@Nullable Connection con) {
        JmsUtils.closeConnection(con, false);
    }

    public static void closeConnection(@Nullable Connection con, boolean stop) {
        block9: {
            if (con != null) {
                try {
                    if (stop) {
                        try {
                            con.stop();
                            break block9;
                        }
                        finally {
                            con.close();
                        }
                    }
                    con.close();
                }
                catch (javax.jms.IllegalStateException ex) {
                    logger.debug((Object)("Ignoring Connection state exception - assuming already closed: " + (Object)((Object)ex)));
                }
                catch (JMSException ex) {
                    logger.debug((Object)"Could not close JMS Connection", (Throwable)ex);
                }
                catch (Throwable ex) {
                    logger.debug((Object)"Unexpected exception on closing JMS Connection", ex);
                }
            }
        }
    }

    public static void closeSession(@Nullable Session session) {
        if (session != null) {
            try {
                session.close();
            }
            catch (JMSException ex) {
                logger.trace((Object)"Could not close JMS Session", (Throwable)ex);
            }
            catch (Throwable ex) {
                logger.trace((Object)"Unexpected exception on closing JMS Session", ex);
            }
        }
    }

    public static void closeMessageProducer(@Nullable MessageProducer producer) {
        if (producer != null) {
            try {
                producer.close();
            }
            catch (JMSException ex) {
                logger.trace((Object)"Could not close JMS MessageProducer", (Throwable)ex);
            }
            catch (Throwable ex) {
                logger.trace((Object)"Unexpected exception on closing JMS MessageProducer", ex);
            }
        }
    }

    public static void closeMessageConsumer(@Nullable MessageConsumer consumer) {
        if (consumer != null) {
            boolean wasInterrupted = Thread.interrupted();
            try {
                consumer.close();
            }
            catch (JMSException ex) {
                logger.trace((Object)"Could not close JMS MessageConsumer", (Throwable)ex);
            }
            catch (Throwable ex) {
                logger.trace((Object)"Unexpected exception on closing JMS MessageConsumer", ex);
            }
            finally {
                if (wasInterrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void closeQueueBrowser(@Nullable QueueBrowser browser) {
        if (browser != null) {
            try {
                browser.close();
            }
            catch (JMSException ex) {
                logger.trace((Object)"Could not close JMS QueueBrowser", (Throwable)ex);
            }
            catch (Throwable ex) {
                logger.trace((Object)"Unexpected exception on closing JMS QueueBrowser", ex);
            }
        }
    }

    public static void closeQueueRequestor(@Nullable QueueRequestor requestor) {
        if (requestor != null) {
            try {
                requestor.close();
            }
            catch (JMSException ex) {
                logger.trace((Object)"Could not close JMS QueueRequestor", (Throwable)ex);
            }
            catch (Throwable ex) {
                logger.trace((Object)"Unexpected exception on closing JMS QueueRequestor", ex);
            }
        }
    }

    public static void commitIfNecessary(Session session) throws JMSException {
        Assert.notNull((Object)session, (String)"Session must not be null");
        try {
            session.commit();
        }
        catch (javax.jms.IllegalStateException | javax.jms.TransactionInProgressException throwable) {
            // empty catch block
        }
    }

    public static void rollbackIfNecessary(Session session) throws JMSException {
        Assert.notNull((Object)session, (String)"Session must not be null");
        try {
            session.rollback();
        }
        catch (javax.jms.IllegalStateException | javax.jms.TransactionInProgressException throwable) {
            // empty catch block
        }
    }

    public static String buildExceptionMessage(JMSException ex) {
        String message = ex.getMessage();
        Exception linkedEx = ex.getLinkedException();
        if (linkedEx != null) {
            if (message == null) {
                message = linkedEx.toString();
            } else {
                String linkedMessage = linkedEx.getMessage();
                if (linkedMessage != null && !message.contains(linkedMessage)) {
                    message = message + "; nested exception is " + linkedEx;
                }
            }
        }
        return message;
    }

    public static JmsException convertJmsAccessException(JMSException ex) {
        Assert.notNull((Object)((Object)ex), (String)"JMSException must not be null");
        if (ex instanceof javax.jms.IllegalStateException) {
            return new IllegalStateException((javax.jms.IllegalStateException)((Object)ex));
        }
        if (ex instanceof javax.jms.InvalidClientIDException) {
            return new InvalidClientIDException((javax.jms.InvalidClientIDException)ex);
        }
        if (ex instanceof javax.jms.InvalidDestinationException) {
            return new InvalidDestinationException((javax.jms.InvalidDestinationException)ex);
        }
        if (ex instanceof javax.jms.InvalidSelectorException) {
            return new InvalidSelectorException((javax.jms.InvalidSelectorException)ex);
        }
        if (ex instanceof JMSSecurityException) {
            return new JmsSecurityException((JMSSecurityException)ex);
        }
        if (ex instanceof javax.jms.MessageEOFException) {
            return new MessageEOFException((javax.jms.MessageEOFException)ex);
        }
        if (ex instanceof javax.jms.MessageFormatException) {
            return new MessageFormatException((javax.jms.MessageFormatException)ex);
        }
        if (ex instanceof javax.jms.MessageNotReadableException) {
            return new MessageNotReadableException((javax.jms.MessageNotReadableException)ex);
        }
        if (ex instanceof javax.jms.MessageNotWriteableException) {
            return new MessageNotWriteableException((javax.jms.MessageNotWriteableException)ex);
        }
        if (ex instanceof javax.jms.ResourceAllocationException) {
            return new ResourceAllocationException((javax.jms.ResourceAllocationException)ex);
        }
        if (ex instanceof javax.jms.TransactionInProgressException) {
            return new TransactionInProgressException((javax.jms.TransactionInProgressException)((Object)ex));
        }
        if (ex instanceof javax.jms.TransactionRolledBackException) {
            return new TransactionRolledBackException((javax.jms.TransactionRolledBackException)((Object)ex));
        }
        return new UncategorizedJmsException(ex);
    }
}

