package com.pluralsight.dynamodb.domain

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "msisdn")
class Msisdn {
    @DynamoDBHashKey
    var msisdn:String?=null
    @DynamoDBAttribute
    var country:String?=null
    @DynamoDBAttribute
    var timestamp:String?=null

    override fun toString(): String {
        return "Msisdn : $msisdn + country = $country"
    }

}