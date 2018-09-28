#### Specify sort key

    @DynnamoDBRangeKey(attributeName="messageId")
    
#### Local Secondary index

    @DynamoDBIndexRangeKey(localSecondaryIndex="lsi",
    attributeName="Time")
    
#### Global Secondary index partition

    @DynamoDBindexHashKey(globalSecondaryIndexName="gsi",
    attributeName="UserId")
    
#### Global Secondary index sort key

    @DynamoDBIndexRangeKey(globalSecondaryIndexName="gsi",
    attributeName="Time")
    