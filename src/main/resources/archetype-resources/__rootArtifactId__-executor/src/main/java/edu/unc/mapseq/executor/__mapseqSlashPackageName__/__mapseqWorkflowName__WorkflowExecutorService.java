#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package edu.unc.mapseq.executor.${mapseqDotPackageName};

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ${mapseqWorkflowName}WorkflowExecutorService {

    private final Logger logger = LoggerFactory.getLogger(${mapseqWorkflowName}WorkflowExecutorService.class);

    private final Timer mainTimer = new Timer();

    private ${mapseqWorkflowName}WorkflowExecutorTask task;

    private Long period = Long.valueOf(5);

    public ${mapseqWorkflowName}WorkflowExecutorService() {
        super();
    }

    public void start() throws Exception {
        logger.info("ENTERING start()");
        long delay = 1 * 60 * 1000; // 1 minute
        mainTimer.scheduleAtFixedRate(task, delay, period * 60 * 1000);
    }

    public void stop() throws Exception {
        logger.info("ENTERING stop()");
        mainTimer.purge();
        mainTimer.cancel();
    }

    public ${mapseqWorkflowName}WorkflowExecutorTask getTask() {
        return task;
    }

    public void setTask(${mapseqWorkflowName}WorkflowExecutorTask task) {
        this.task = task;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

}
