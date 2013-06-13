#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package edu.unc.mapseq.commands.${mapseqDotPackageName};

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;

import edu.unc.mapseq.config.MaPSeqConfigurationService;
import edu.unc.mapseq.dao.MaPSeqDAOBean;

@Command(scope = "${mapseqGroupName}", name = "run-pipeline", description = "Run ${mapseqTitle} Pipeline")
public class ${mapseqPipelineName}RunPipelineAction extends AbstractAction {

    @Argument(index = 0, name = "workflowRunName", description = "WorkflowRun.name", required = true, multiValued = false)
    private String workflowRunName;

    private MaPSeqDAOBean maPSeqDAOBean;

    private MaPSeqConfigurationService maPSeqConfigurationService;

    public ${mapseqPipelineName}RunPipelineAction() {
        super();
    }

    @Override
    public Object doExecute() {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(String.format("nio://%s:61616",
                maPSeqConfigurationService.getWebServiceHost("localhost")));

        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("queue/${mapseqDotPackageName}");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            String format = "{${symbol_escape}"account_name${symbol_escape}":${symbol_escape}"%s${symbol_escape}",${symbol_escape}"entities${symbol_escape}":[{${symbol_escape}"entity_type${symbol_escape}":${symbol_escape}"WorkflowRun${symbol_escape}",${symbol_escape}"name${symbol_escape}":${symbol_escape}"%s${symbol_escape}"}]}";
            producer.send(session.createTextMessage(String.format(format, System.getProperty("user.name"),
                    workflowRunName)));
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

        return null;
    }

    public String getWorkflowRunName() {
        return workflowRunName;
    }

    public void setWorkflowRunName(String workflowRunName) {
        this.workflowRunName = workflowRunName;
    }

    public MaPSeqDAOBean getMaPSeqDAOBean() {
        return maPSeqDAOBean;
    }

    public void setMaPSeqDAOBean(MaPSeqDAOBean maPSeqDAOBean) {
        this.maPSeqDAOBean = maPSeqDAOBean;
    }

    public MaPSeqConfigurationService getMaPSeqConfigurationService() {
        return maPSeqConfigurationService;
    }

    public void setMaPSeqConfigurationService(MaPSeqConfigurationService maPSeqConfigurationService) {
        this.maPSeqConfigurationService = maPSeqConfigurationService;
    }

}
