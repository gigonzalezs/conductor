package com.netflix.conductor.core.observer;

import com.netflix.conductor.common.run.Workflow;
import com.netflix.conductor.model.WorkflowModel;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class WorkflowTerminationObserver implements WorkflowStatusObserver {

    private final WorkflowStatusPublisher workflowStatusPublisher;
    private final Map<String, CompletableFuture<Workflow>> completionByWorkflowId = new HashMap<>();

    public WorkflowTerminationObserver(WorkflowStatusPublisher workflowStatusPublisher) {
        this.workflowStatusPublisher = workflowStatusPublisher;
    }

    @PostConstruct
    private void init() {
        workflowStatusPublisher.subscribe(this);
    }
    @Override
    public void notify(String workflowId, WorkflowModel.Status status) {
        if (completionByWorkflowId.containsKey(workflowId) && status.isTerminal()) {
            completionByWorkflowId.get(workflowId).complete(null) ;//TODO: complete
        }
    }
}
