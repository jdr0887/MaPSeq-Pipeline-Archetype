#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package edu.unc.mapseq.workflow.${mapseqDotPackageName};

import java.io.File;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.renci.jlrm.condor.CondorJob;
import org.renci.jlrm.condor.CondorJobEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.unc.mapseq.module.core.CatCLI;
import edu.unc.mapseq.module.core.EchoCLI;
import edu.unc.mapseq.pipeline.AbstractPipeline;
import edu.unc.mapseq.pipeline.PipelineException;
import edu.unc.mapseq.pipeline.PipelineJobFactory;

public class ${mapseqPipelineName}Pipeline extends AbstractPipeline {

    private final Logger logger = LoggerFactory.getLogger(${mapseqPipelineName}Pipeline.class);

    public ${mapseqPipelineName}Pipeline() {
        super();
    }

    @Override
    public String getName() {
        return ${mapseqPipelineName}Pipeline.class.getSimpleName().replace("Pipeline", "");
    }

    @Override
    public String getVersion() {
        ResourceBundle bundle = ResourceBundle.getBundle("edu/unc/mapseq/workflow/${mapseqSlashPackageName}/workflow");
        String version = bundle.getString("version");
        return StringUtils.isNotEmpty(version) ? version : "0.0.1-SNAPSHOT";
    }

    @Override
    public Graph<CondorJob, CondorJobEdge> createGraph() throws PipelineException {
        logger.info("ENTERING createGraph()");

        DirectedGraph<CondorJob, CondorJobEdge> graph = new DefaultDirectedGraph<CondorJob, CondorJobEdge>(
                CondorJobEdge.class);

        int count = 0;
        String uuid = UUID.randomUUID().toString();

        // new job
        CondorJob helloJob = PipelineJobFactory.createJob(++count, EchoCLI.class, getWorkflowPlan(), null, false);
        helloJob.setSiteName("test");
        helloJob.addArgument(EchoCLI.GREETING, "Hello");
        File helloJobOutput = new File(getPipelineBeanService().getAttributes().get("outputDirectory"), String.format(
                "hello-%s.txt", uuid));
        helloJob.addArgument(EchoCLI.OUTPUT, helloJobOutput.getPath());
        graph.addVertex(helloJob);

        // new job
        CondorJob worldJob = PipelineJobFactory.createJob(++count, EchoCLI.class, getWorkflowPlan(), null, false);
        worldJob.setSiteName("test");
        worldJob.addArgument(EchoCLI.GREETING, "World!");
        File worldJobOutput = new File(getPipelineBeanService().getAttributes().get("outputDirectory"), String.format(
                "world-%s.txt", uuid));
        worldJob.addArgument(EchoCLI.OUTPUT, worldJobOutput.getPath());
        graph.addVertex(worldJob);

        // new job
        CondorJob catJob = PipelineJobFactory.createJob(++count, CatCLI.class, getWorkflowPlan(), null, false);
        catJob.setSiteName("test");
        catJob.addArgument(CatCLI.FILES, helloJobOutput);
        catJob.addArgument(CatCLI.FILES, worldJobOutput);
        File catJobOutput = new File(getPipelineBeanService().getAttributes().get("outputDirectory"), String.format(
                "${parentArtifactId}-%s.txt", uuid));
        catJob.addArgument(CatCLI.OUTPUT, catJobOutput.getPath());
        graph.addVertex(catJob);
        graph.addEdge(helloJob, catJob);
        graph.addEdge(worldJob, catJob);

        return graph;
    }

}
