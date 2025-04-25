# Helidon gRPC MP Example

This examples shows a simple application written using Helidon gRPC MP API:

- TaskService: a gRPC service implementation that uses MP
- TaskServiceClient: an interface from which a client proxy can be created to call TaskService remote methods
- TaskServiceTest: a sample test that starts a server and tests the client and server components
- application.yaml: configuration for server and client channels

## Build and run

```shell
>> mvn package
>> java -jar target/helidon-examples-microprofile-grpc.jar &
```

Note port on which app is running and try:
```shell
>> grpcurl -insecure -proto src/main/proto/tasks.proto localhost:<port> TaskService/GetTasks
{
  "id": "1",
  "title": "my task"
}
{
  "id": "1",
  "title": "my task"
}
```