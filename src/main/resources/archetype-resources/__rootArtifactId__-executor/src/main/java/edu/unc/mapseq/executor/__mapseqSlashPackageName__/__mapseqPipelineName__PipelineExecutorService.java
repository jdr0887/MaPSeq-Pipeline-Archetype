#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package edu.unc.mapseq.executor.${mapseqDotPackageName};

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ${mapseqPipelineName}PipelineExecutorService {

    private final Logger logger = LoggerFactory.getLogger(${mapseqPipelineName}PipelineExecutorService.class);

    private final Timer mainTimer = new Timer();

    private ${mapseqPipelineName}PipelineExecutorTask task;

    private Long period = Long.valueOf(5);

    public ${mapseqPipelineName}PipelineExecutorService() {
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

    public ${mapseqPipelineName}PipelineExecutorTask getTask() {
        return task;
    }

    public void setTask(${mapseqPipelineName}PipelineExecutorTask task) {
        this.task = task;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

}
