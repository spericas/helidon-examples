# Web Server Integration and HTTP Signatures

This example demonstrates Integration of WebServer
based application with Security component and HTTP Signatures.

## Contents

There are two examples with exactly the same behavior
1. builder - shows how to programmatically secure application
2. config - shows how to secure application with configuration
    1. see `src/main/resources/service1.yaml` and `src/main/resources/service2.conf` for configuration
3. Each consists of two services
    1. "public" service protected by basic authentication (for simplicity)
    2. "internal" service protected by a combination of basic authentication (for user propagation) and http signature
    (for service authentication)

## Steps to generate keystore (optional)
1. run the scripts ``automatic-store-generator.sh`` (openssl and keytool are required to be present on the PATH)
2. move generated ``keystore.p12`` into the ``main/resources`` folder

## Build and run

```shell
mvn package
java -jar target/helidon-examples-security-webserver-signatures.jar
```

Try the endpoints:
```shell
curl -u "jack:changeit" http://localhost:8080/service1
curl -u "jill:changeit" http://localhost:8080/service1-rsa
curl -v -u "john:changeit" http://localhost:8080/service1
```
