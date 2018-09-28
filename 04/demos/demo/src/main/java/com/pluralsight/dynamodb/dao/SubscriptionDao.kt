package com.pluralsight.dynamodb.dao

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.*
import com.pluralsight.dynamodb.domain.Comment
import com.pluralsight.dynamodb.domain.Subscriptions

class SubscriptionDao {

      var dynamoMapper:DynamoDBMapper?=null

      constructor(dynamodb:AmazonDynamoDB){

          this.dynamoMapper= DynamoDBMapper(dynamodb)

      }

    fun createSubscription(sub:Subscriptions):Subscriptions {

        this.dynamoMapper?.save(sub, DynamoDBMapperConfig.builder().withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .build())

        return sub

    }


    fun returnList(): PaginatedScanList<Subscriptions>? {

       val toReturn= this.dynamoMapper?.scan(Subscriptions::class.java, DynamoDBScanExpression())

        return toReturn
    }

}