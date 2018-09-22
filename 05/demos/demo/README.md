
# Install dependencies

The project depends on `amazon-dynamodb-transactions`. To install it execute  following commands:

```sh
git clone https://github.com/awslabs/dynamodb-transactions
cd dynamodb-transactions
mvn install
```

# Build 

To build the project and run unit tests run:

```sh
mvn test
```

# Run

To run demo applications open the project in an IDE and run the following class:

```java
com.pluralsight.dynamodb.Main
```

Make sure that you have AWS credentilas exported as environment variables or storred in the following file:

```
~/.aws/credentials
```

with credentials in the following format:

```
[default]
aws_access_key_id = XXXXXXXXXXXXXXXXXXXX
aws_secret_access_key = xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

# SQLite dependency

DynamoDBLocal depends on native libraries that are copied into `target/lib` during the build process. If you have an exception like:

```
com.almworks.sqlite4java.SQLiteException: [-91] cannot load library: com.almworks.sqlite4java.SQLiteException: [-91] sqlite4java cannot find native library
    at com.almworks.sqlite4java.SQLite.loadLibrary(SQLite.java:97)
    ...
```

Check if you have libraries for your platform in `target/lib`.

# Lambda function

Source code for DynamoDB/CloudSearch integration is located in the `cloud_search_lambda` folder.

* lambda.js - contains lambda function that synchronizes DynamoDB with CloudSearch
* test_item.json - contains test data for the lambda function


