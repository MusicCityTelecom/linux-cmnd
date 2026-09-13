/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.ExceptionListener
 *  javax.jms.IllegalStateException
 *  javax.jms.JMSContext
 *  javax.jms.JMSException
 *  javax.jms.QueueConnection
 *  javax.jms.QueueConnectionFactory
 *  javax.jms.Session
 *  javax.jms.TopicConnection
 *  javax.jms.TopicConnectionFactory
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.jms.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateException;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

public class SingleConnectionFactory
implements ConnectionFactory,
QueueConnectionFactory,
TopicConnectionFactory,
ExceptionListener,
InitializingBean,
DisposableBean {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Nullable
    private ConnectionFactory targetConnectionFactory;
    @Nullable
    private String clientId;
    @Nullable
    private ExceptionListener exceptionListener;
    private boolean reconnectOnException = false;
    @Nullable
    private Connection connection;
    @Nullable
    private Boolean pubSubMode;
    @Nullable
    private AggregatedExceptionListener aggregatedExceptionListener;
    private int startedCount = 0;
    private final Object connectionMonitor = new Object();

    public SingleConnectionFactory() {
    }

    public SingleConnectionFactory(Connection targetConnection) {
        Assert.notNull((Object)targetConnection, (String)"Target Connection must not be null");
        this.connection = targetConnection;
    }

    public SingleConnectionFactory(ConnectionFactory targetConnectionFactory) {
        Assert.notNull((Object)targetConnectionFactory, (String)"Target ConnectionFactory must not be null");
        this.targetConnectionFactory = targetConnectionFactory;
    }

    public void setTargetConnectionFactory(@Nullable ConnectionFactory targetConnectionFactory) {
        this.targetConnectionFactory = targetConnectionFactory;
    }

    @Nullable
    public ConnectionFactory getTargetConnectionFactory() {
        return this.targetConnectionFactory;
    }

    public void setClientId(@Nullable String clientId) {
        this.clientId = clientId;
    }

    @Nullable
    protected String getClientId() {
        return this.clientId;
    }

    public void setExceptionListener(@Nullable ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    @Nullable
    protected ExceptionListener getExceptionListener() {
        return this.exceptionListener;
    }

    public void setReconnectOnException(boolean reconnectOnException) {
        this.reconnectOnException = reconnectOnException;
    }

    protected boolean isReconnectOnException() {
        return this.reconnectOnException;
    }

    public void afterPropertiesSet() {
        if (this.connection == null && this.getTargetConnectionFactory() == null) {
            throw new IllegalArgumentException("Target Connection or ConnectionFactory is required");
        }
    }

    public Connection createConnection() throws JMSException {
        return this.getSharedConnectionProxy(this.getConnection());
    }

    public Connection createConnection(String username, String password) throws JMSException {
        throw new IllegalStateException("SingleConnectionFactory does not support custom username and password");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public QueueConnection createQueueConnection() throws JMSException {
        Connection con;
        Object object = this.connectionMonitor;
        synchronized (object) {
            this.pubSubMode = Boolean.FALSE;
            con = this.createConnection();
        }
        if (!(con instanceof QueueConnection)) {
            throw new IllegalStateException("This SingleConnectionFactory does not hold a QueueConnection but rather: " + con);
        }
        return (QueueConnection)con;
    }

    public QueueConnection createQueueConnection(String username, String password) throws JMSException {
        throw new IllegalStateException("SingleConnectionFactory does not support custom username and password");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TopicConnection createTopicConnection() throws JMSException {
        Connection con;
        Object object = this.connectionMonitor;
        synchronized (object) {
            this.pubSubMode = Boolean.TRUE;
            con = this.createConnection();
        }
        if (!(con instanceof TopicConnection)) {
            throw new IllegalStateException("This SingleConnectionFactory does not hold a TopicConnection but rather: " + con);
        }
        return (TopicConnection)con;
    }

    public TopicConnection createTopicConnection(String username, String password) throws JMSException {
        throw new IllegalStateException("SingleConnectionFactory does not support custom username and password");
    }

    public JMSContext createContext() {
        return this.obtainTargetConnectionFactory().createContext();
    }

    public JMSContext createContext(String userName, String password) {
        return this.obtainTargetConnectionFactory().createContext(userName, password);
    }

    public JMSContext createContext(String userName, String password, int sessionMode) {
        return this.obtainTargetConnectionFactory().createContext(userName, password, sessionMode);
    }

    public JMSContext createContext(int sessionMode) {
        return this.obtainTargetConnectionFactory().createContext(sessionMode);
    }

    private ConnectionFactory obtainTargetConnectionFactory() {
        ConnectionFactory target = this.getTargetConnectionFactory();
        Assert.state((target != null ? 1 : 0) != 0, (String)"'targetConnectionFactory' is required");
        return target;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Connection getConnection() throws JMSException {
        Object object = this.connectionMonitor;
        synchronized (object) {
            if (this.connection == null) {
                this.initConnection();
            }
            return this.connection;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void initConnection() throws JMSException {
        if (this.getTargetConnectionFactory() == null) {
            throw new java.lang.IllegalStateException("'targetConnectionFactory' is required for lazily initializing a Connection");
        }
        Object object = this.connectionMonitor;
        synchronized (object) {
            if (this.connection != null) {
                this.closeConnection(this.connection);
            }
            this.connection = this.doCreateConnection();
            this.prepareConnection(this.connection);
            if (this.startedCount > 0) {
                this.connection.start();
            }
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Established shared JMS Connection: " + this.connection));
            }
        }
    }

    public void onException(JMSException ex) {
        this.logger.info((Object)"Encountered a JMSException - resetting the underlying JMS Connection", (Throwable)ex);
        this.resetConnection();
    }

    public void destroy() {
        this.resetConnection();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resetConnection() {
        Object object = this.connectionMonitor;
        synchronized (object) {
            if (this.connection != null) {
                this.closeConnection(this.connection);
            }
            this.connection = null;
        }
    }

    protected Connection doCreateConnection() throws JMSException {
        ConnectionFactory cf = this.getTargetConnectionFactory();
        if (Boolean.FALSE.equals(this.pubSubMode) && cf instanceof QueueConnectionFactory) {
            return ((QueueConnectionFactory)cf).createQueueConnection();
        }
        if (Boolean.TRUE.equals(this.pubSubMode) && cf instanceof TopicConnectionFactory) {
            return ((TopicConnectionFactory)cf).createTopicConnection();
        }
        return this.obtainTargetConnectionFactory().createConnection();
    }

    protected void prepareConnection(Connection con) throws JMSException {
        if (this.getClientId() != null) {
            con.setClientID(this.getClientId());
        }
        if (this.aggregatedExceptionListener != null) {
            con.setExceptionListener((ExceptionListener)this.aggregatedExceptionListener);
        } else if (this.getExceptionListener() != null || this.isReconnectOnException()) {
            ExceptionListener listenerToUse = this.getExceptionListener();
            if (this.isReconnectOnException()) {
                this.aggregatedExceptionListener = new AggregatedExceptionListener();
                this.aggregatedExceptionListener.delegates.add(this);
                if (listenerToUse != null) {
                    this.aggregatedExceptionListener.delegates.add(listenerToUse);
                }
                listenerToUse = this.aggregatedExceptionListener;
            }
            con.setExceptionListener(listenerToUse);
        }
    }

    @Nullable
    protected Session getSession(Connection con, Integer mode) throws JMSException {
        return null;
    }

    protected Session createSession(Connection con, Integer mode) throws JMSException {
        int ackMode;
        boolean transacted = mode == 0;
        int n = ackMode = transacted ? 1 : mode;
        if (Boolean.FALSE.equals(this.pubSubMode) && con instanceof QueueConnection) {
            return ((QueueConnection)con).createQueueSession(transacted, ackMode);
        }
        if (Boolean.TRUE.equals(this.pubSubMode) && con instanceof TopicConnection) {
            return ((TopicConnection)con).createTopicSession(transacted, ackMode);
        }
        return con.createSession(transacted, ackMode);
    }

    protected void closeConnection(Connection con) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Closing shared JMS Connection: " + con));
        }
        try {
            try {
                if (this.startedCount > 0) {
                    con.stop();
                }
            }
            finally {
                con.close();
            }
        }
        catch (IllegalStateException ex) {
            this.logger.debug((Object)("Ignoring Connection state exception - assuming already closed: " + (Object)((Object)ex)));
        }
        catch (Throwable ex) {
            this.logger.warn((Object)"Could not close shared JMS Connection", ex);
        }
    }

    protected Connection getSharedConnectionProxy(Connection target) {
        ArrayList<Class<TopicConnection>> classes = new ArrayList<Class<TopicConnection>>(3);
        classes.add(Connection.class);
        if (target instanceof QueueConnection) {
            classes.add(QueueConnection.class);
        }
        if (target instanceof TopicConnection) {
            classes.add(TopicConnection.class);
        }
        return (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), ClassUtils.toClassArray(classes), (InvocationHandler)new SharedConnectionInvocationHandler());
    }

    private class AggregatedExceptionListener
    implements ExceptionListener {
        final Set<ExceptionListener> delegates = new LinkedHashSet<ExceptionListener>(2);

        private AggregatedExceptionListener() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onException(JMSException ex) {
            LinkedHashSet<ExceptionListener> copy;
            Iterator iterator = SingleConnectionFactory.this.connectionMonitor;
            synchronized (iterator) {
                copy = new LinkedHashSet<ExceptionListener>(this.delegates);
            }
            for (ExceptionListener listener : copy) {
                listener.onException(ex);
            }
        }
    }

    private class SharedConnectionInvocationHandler
    implements InvocationHandler {
        @Nullable
        private ExceptionListener localExceptionListener;
        private boolean locallyStarted = false;

        private SharedConnectionInvocationHandler() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Nullable
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            switch (method.getName()) {
                case "equals": {
                    Object other = args[0];
                    if (proxy == other) {
                        return true;
                    }
                    if (other == null || !Proxy.isProxyClass(other.getClass())) {
                        return false;
                    }
                    InvocationHandler otherHandler = Proxy.getInvocationHandler(other);
                    return otherHandler instanceof SharedConnectionInvocationHandler && this.factory() == ((SharedConnectionInvocationHandler)otherHandler).factory();
                }
                case "hashCode": {
                    return System.identityHashCode(this.factory());
                }
                case "toString": {
                    return "Shared JMS Connection: " + SingleConnectionFactory.this.getConnection();
                }
                case "setClientID": {
                    String currentClientId = SingleConnectionFactory.this.getConnection().getClientID();
                    if (currentClientId != null && currentClientId.equals(args[0])) {
                        return null;
                    }
                    throw new IllegalStateException("setClientID call not supported on proxy for shared Connection. Set the 'clientId' property on the SingleConnectionFactory instead.");
                }
                case "setExceptionListener": {
                    Object object = SingleConnectionFactory.this.connectionMonitor;
                    synchronized (object) {
                        if (SingleConnectionFactory.this.aggregatedExceptionListener != null) {
                            ExceptionListener listener = (ExceptionListener)args[0];
                            if (listener != this.localExceptionListener) {
                                if (this.localExceptionListener != null) {
                                    ((SingleConnectionFactory)SingleConnectionFactory.this).aggregatedExceptionListener.delegates.remove(this.localExceptionListener);
                                }
                                if (listener != null) {
                                    ((SingleConnectionFactory)SingleConnectionFactory.this).aggregatedExceptionListener.delegates.add(listener);
                                }
                                this.localExceptionListener = listener;
                            }
                            return null;
                        }
                        throw new IllegalStateException("setExceptionListener call not supported on proxy for shared Connection. Set the 'exceptionListener' property on the SingleConnectionFactory instead. Alternatively, activate SingleConnectionFactory's 'reconnectOnException' feature, which will allow for registering further ExceptionListeners to the recovery chain.");
                    }
                }
                case "getExceptionListener": {
                    Object object = SingleConnectionFactory.this.connectionMonitor;
                    synchronized (object) {
                        if (this.localExceptionListener != null) {
                            return this.localExceptionListener;
                        }
                        return SingleConnectionFactory.this.getExceptionListener();
                    }
                }
                case "start": {
                    this.localStart();
                    return null;
                }
                case "stop": {
                    this.localStop();
                    return null;
                }
                case "close": {
                    this.localStop();
                    Object object = SingleConnectionFactory.this.connectionMonitor;
                    synchronized (object) {
                        if (this.localExceptionListener != null) {
                            if (SingleConnectionFactory.this.aggregatedExceptionListener != null) {
                                ((SingleConnectionFactory)SingleConnectionFactory.this).aggregatedExceptionListener.delegates.remove(this.localExceptionListener);
                            }
                            this.localExceptionListener = null;
                        }
                    }
                    return null;
                }
                case "createSession": 
                case "createQueueSession": 
                case "createTopicSession": {
                    Session session;
                    Integer mode = 1;
                    if (!ObjectUtils.isEmpty((Object[])args)) {
                        if (args.length == 1) {
                            mode = (Integer)args[0];
                        } else if (args.length == 2) {
                            boolean transacted = (Boolean)args[0];
                            Integer ackMode = (Integer)args[1];
                            mode = transacted ? 0 : ackMode;
                        }
                    }
                    if ((session = SingleConnectionFactory.this.getSession(SingleConnectionFactory.this.getConnection(), mode)) == null) break;
                    if (!method.getReturnType().isInstance(session)) {
                        String msg = "JMS Session does not implement specific domain: " + session;
                        try {
                            session.close();
                        }
                        catch (Throwable ex) {
                            SingleConnectionFactory.this.logger.trace((Object)"Failed to close newly obtained JMS Session", ex);
                        }
                        throw new IllegalStateException(msg);
                    }
                    return session;
                }
            }
            try {
                return method.invoke(SingleConnectionFactory.this.getConnection(), args);
            }
            catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void localStart() throws JMSException {
            Object object = SingleConnectionFactory.this.connectionMonitor;
            synchronized (object) {
                if (!this.locallyStarted) {
                    this.locallyStarted = true;
                    if (SingleConnectionFactory.this.startedCount == 0 && SingleConnectionFactory.this.connection != null) {
                        SingleConnectionFactory.this.connection.start();
                    }
                    SingleConnectionFactory.this.startedCount++;
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void localStop() throws JMSException {
            Object object = SingleConnectionFactory.this.connectionMonitor;
            synchronized (object) {
                if (this.locallyStarted) {
                    this.locallyStarted = false;
                    if (SingleConnectionFactory.this.startedCount == 1 && SingleConnectionFactory.this.connection != null) {
                        SingleConnectionFactory.this.connection.stop();
                    }
                    if (SingleConnectionFactory.this.startedCount > 0) {
                        SingleConnectionFactory.this.startedCount--;
                    }
                }
            }
        }

        private SingleConnectionFactory factory() {
            return SingleConnectionFactory.this;
        }
    }
}

