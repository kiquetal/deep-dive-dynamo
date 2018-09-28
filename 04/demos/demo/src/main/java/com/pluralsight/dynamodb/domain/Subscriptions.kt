package com.pluralsight.dynamodb.domain

import com.amazonaws.services.dynamodbv2.datamodeling.*

@DynamoDBTable(tableName = "subscriptions")
class Subscriptions
{
    @DynamoDBHashKey
    var msisdn:String?=null
    @DynamoDBRangeKey
    var timestamp:String?=null
    @DynamoDBAttribute
    var plans:String?=null
    @DynamoDBIndexRangeKey(localSecondaryIndexName = "lsi",
            attributeName = "countryCode")
    var countryCode:String?=null

    override fun toString(): String {
        return "Obj{Msisdn = $msisdn,  plans=$plans, timestamp=$timestamp}"
    }


}
