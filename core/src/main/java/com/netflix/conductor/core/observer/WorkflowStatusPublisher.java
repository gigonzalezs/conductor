/*
 * Copyright 2025 Conductor Authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netflix.conductor.core.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.netflix.conductor.model.WorkflowModel;

@Component
public class WorkflowStatusPublisher {

    private final List<WorkflowStatusObserver> observers = new ArrayList<>();

    @Async
    public void notify(String workflowId, WorkflowModel.Status status) {
        observers.forEach(observer -> observer.notify(workflowId, status));
    }

    public void subscribe(WorkflowStatusObserver observer) {
        observers.add(observer);
    }

    public void unSubscribe(WorkflowStatusObserver observer) {
        observers.remove(observer);
    }
}
