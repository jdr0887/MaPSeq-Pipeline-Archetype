#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package edu.unc.mapseq.messaging;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class MessagingTest {

    @Test
    public void testQueue() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(String.format("nio://%s:61616",
                "localhost"));
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("queue/${parentArtifactId}");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            String format = "{${symbol_escape}"account_name${symbol_escape}":${symbol_escape}"%s${symbol_escape}",${symbol_escape}"entities${symbol_escape}":[{${symbol_escape}"entity_type${symbol_escape}":${symbol_escape}"WorkflowRun${symbol_escape}",${symbol_escape}"name${symbol_escape}":${symbol_escape}"jdr-test-%d${symbol_escape}"}]}";
            for (int i = 0; i < 10; ++i) {
                producer.send(session.createTextMessage(String.format(format, System.getProperty("user.name"), i)));
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
