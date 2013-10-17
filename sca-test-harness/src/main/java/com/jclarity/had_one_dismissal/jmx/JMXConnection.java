package com.jclarity.had_one_dismissal.jmx;

import java.io.Closeable;
import java.io.IOException;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.google.common.io.Closeables;
import com.jclarity.had_one_dismissal.api.HadOneDismissal;

public abstract class JMXConnection implements Closeable {
    protected static final String JMX_SERVER_HOST = HadOneDismissal.HOST;

    private final String name;

    private JMXConnector cachedConnector;

    public JMXConnection(String host, int port) {
        this.name = host + ":" + port;
    }

    protected <BeanType> BeanType getBean(String mxbeanName, Class<BeanType> clazz) throws JMXConnectionException {
        try {
            MBeanServerConnection connection = connect();
            return JMX.newMXBeanProxy(connection, new ObjectName(mxbeanName), clazz);
        } catch (MalformedObjectNameException e) {
            throw new JMXConnectionException(name, e);
        }
    }

    private MBeanServerConnection connect() throws JMXConnectionException {
        if (cachedConnector != null) {
            try {
                return cachedConnector.getMBeanServerConnection();
            } catch (IOException e) {
                throw new JMXConnectionException(name, e);
            }
        }

        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + name + "/jmxrmi");
            JMXConnector connector = JMXConnectorFactory.connect(url);
            connector.connect();
            this.cachedConnector = connector;
            return cachedConnector.getMBeanServerConnection();
        } catch (IOException e) {
            throw new JMXConnectionException(name, e);
        }
    }

    @Override
    public void close() {
        Closeables.closeQuietly(cachedConnector);
    }
}
