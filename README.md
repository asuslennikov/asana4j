# Asana-4J 
An unofficial Java client library for the [Asana](https://asana.com/) API v1.0.

## License
This library licensed under the [MIT License (MIT)](https://opensource.org/licenses/MIT)

## Current status
* Authentication - ok (OAuth, Implicit Grant, Authorization Code Grant)
* API options - ok
* Errors - basic handling (currently in work)
* Custom External Data - no support
* Attachments - ok
* Events - no support
* Projects - full support for operations, no parsing for fields: followers, team, created_at, modified_at
* Stories - ok
* Tags - no support
* Tasks - full support for operations, no parsing for fields: membership, tags
* Teams - no support
* Typeahead - no support
* Users - ok
* Webhooks - no support
* Workspaces - partially, no support for organisations


## Requirements & dependencies
* Java 7 or greater
* [org.json](http://www.json.org/java/)

## Building The Code
Clone the repo and perform the following command from the root directory:
```shell
mvn clean install -P all
```

## Usage & examples
You can add this library to your project via maven dependencies:
```shell
<dependency>
  <groupId>ru.jewelline.asana4j</groupId>
  <artifactId>api</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
  <groupId>ru.jewelline.asana4j</groupId>
  <artifactId>core</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
  <groupId>ru.jewelline.asana4j</groupId>
  <artifactId>utils-se</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```
Java SE example can be found in the /asana4j-example-se
