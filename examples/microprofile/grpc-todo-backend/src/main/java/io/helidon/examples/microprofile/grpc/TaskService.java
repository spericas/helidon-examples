/*
 * Copyright (c) 2025 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.examples.microprofile.grpc;

import io.helidon.grpc.api.Grpc;

import io.grpc.stub.StreamObserver;
import jakarta.enterprise.context.ApplicationScoped;

@Grpc.GrpcService
@ApplicationScoped
public class TaskService {

    static final Tasks.TaskMessage TASK_MESSAGE =
            Tasks.TaskMessage
                    .newBuilder()
                    .setId("1")
                    .setTitle("my task")
                    .setCompleted(false).build();

    @Grpc.Unary("CreateTask")
    public Tasks.TaskMessage createTask(Tasks.TaskMessage taskMessage) {
        // TODO
        return taskMessage;
    }

    @Grpc.Unary("GetTask")
    public Tasks.StatusMessage getTask(Tasks.IdMessage idMessage) {
        // TODO
        return Tasks.StatusMessage.newBuilder()
                .setStatus("OK")
                .setTask(TASK_MESSAGE)
                .build();
    }

    @Grpc.Unary("UpdateTask")
    public Tasks.StatusMessage updateTask(Tasks.TaskMessage taskMessage) {
        // TODO
        return Tasks.StatusMessage.newBuilder()
                .setStatus("OK")
                .build();
    }

    @Grpc.Unary("DeleteTask")
    public Tasks.StatusMessage deleteTask(Tasks.IdMessage idMessage) {
        // TODO
        return Tasks.StatusMessage.newBuilder()
                .setStatus("OK")
                .build();
    }

    @Grpc.ServerStreaming("GetTasks")
    public void getTasks(StreamObserver<Tasks.TaskMessage> result) {
        // TODO
        result.onNext(TASK_MESSAGE);
        result.onNext(TASK_MESSAGE);
        result.onCompleted();
    }
}

