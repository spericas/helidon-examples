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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.helidon.grpc.api.Grpc;
import io.helidon.microprofile.grpc.client.GrpcConfigurablePort;
import io.helidon.microprofile.testing.junit5.HelidonTest;

import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.helidon.examples.microprofile.grpc.TaskService.TASK_MESSAGE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@HelidonTest
class TaskServiceTest {

    private static final Tasks.IdMessage ID_MESSAGE =
            Tasks.IdMessage.newBuilder()
                    .setId("1")
                    .build();

    @Inject
    private WebTarget webTarget;

    @Inject
    @Grpc.GrpcProxy
    private TaskServiceClient client;

    @BeforeEach
    void updatePort() {
        if (client instanceof GrpcConfigurablePort c) {
            c.channelPort(webTarget.getUri().getPort());
        }
    }

    @Test
    void testCreateTask() {
        Tasks.TaskMessage res = client.createTask(TASK_MESSAGE);
        assertThat(res, is(TASK_MESSAGE));
    }

    @Test
    void testGetTask() {
        Tasks.StatusMessage res = client.getTask(ID_MESSAGE);
        assertThat(res.getStatus(), is("OK"));
        assertThat(res.getTask(), is(TASK_MESSAGE));
    }

    @Test
    void testUpdateTask() {
        Tasks.StatusMessage res = client.updateTask(ID_MESSAGE);
        assertThat(res.getStatus(), is("OK"));
    }

    @Test
    void testDeleteTask() {
        Tasks.StatusMessage res = client.deleteTask(ID_MESSAGE);
        assertThat(res.getStatus(), is("OK"));
    }

    @Test
    void testGetTasks() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<Tasks.TaskMessage> collector = new ArrayList<>();
        client.getTasks(new StreamObserver<>() {
            @Override
            public void onNext(Tasks.TaskMessage taskMessage) {
                collector.add(taskMessage);
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });
        assertThat(latch.await(2, TimeUnit.SECONDS), is(true));
        assertThat(collector.size(), is(2));
        assertThat(collector.getFirst(), is(TASK_MESSAGE));
        assertThat(collector.getLast(), is(TASK_MESSAGE));
    }
}

