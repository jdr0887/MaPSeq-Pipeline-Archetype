#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package edu.unc.mapseq.messaging.${mapseqDotPackageName};

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.unc.mapseq.workflow.WorkflowBeanService;

public class ${mapseqWorkflowName}WorkflowMessageService {

    private final Logger logger = LoggerFactory.getLogger(${mapseqWorkflowName}WorkflowMessageService.class);

    private Connection connection;

    private Session session;

    private ConnectionFactory connectionFactory;

    private ${mapseqWorkflowName}WorkflowMessageListener messageListener;

    private String destinationName;

    public ${mapseqWorkflowName}WorkflowMessageService() {
        super();
    }

    public void start() throws Exception {
        logger.debug("ENTERING start()");
        this.connection = connectionFactory.createConnection();
        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = this.session.createQueue(this.destinationName);
        MessageConsumer consumer = this.session.createConsumer(destination);
        consumer.setMessageListener(getMessageListener());
        this.connection.start();
    }

    public void stop() throws Exception {
        logger.debug("ENTERING stop()");
        if (this.session != null) {
            this.session.close();
        }
        if (this.connection != null) {
            this.connection.stop();
            this.connection.close();
        }
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ${mapseqWorkflowName}WorkflowMessageListener getMessageListener() {
        return messageListener;
    }

    public void setMessageListener(${mapseqWorkflowName}WorkflowMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

}
