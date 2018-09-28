package com.pluralsight.dynamodb.dao

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator
import com.amazonaws.services.dynamodbv2.model.Condition
import com.amazonaws.services.dynamodbv2.transactions.Transaction
import com.amazonaws.services.dynamodbv2.transactions.TransactionManager
import com.pluralsight.dynamodb.domain.Comment
import com.pluralsight.dynamodb.domain.Item

class CommentDao(dynamoDB: AmazonDynamoDB) {

    private val mapper: DynamoDBMapper

    private val txManager: TransactionManager

    val all: List<Comment>
        get() = mapper.scan(Comment::class.java, DynamoDBScanExpression())

    init {
        this.mapper = DynamoDBMapper(dynamoDB)
        this.txManager = TransactionManager(dynamoDB, "Transactions", "TransactionImages")
    }

    fun put(comment: Comment): Comment {
        val transaction = txManager.newTransaction()

        transaction.save(comment)

        var item = Item()
        item.id = comment.itemId

        item = transaction.load(item)
        item.totalComments = item.totalComments + 1
        item.totalRating = item.totalRating + comment.rating

        transaction.save(item)

        transaction.commit()

        return comment
    }

    operator fun get(itemId: String, messageId: String): Comment {
        val comment = Comment()
        comment.itemId = itemId
        comment.messageId = messageId

        return mapper.load(comment)
    }

    fun delete(itemId: String, messageId: String) {
        val comment = Comment()
        comment.itemId = itemId
        comment.messageId = messageId

        mapper.delete(comment)
    }

    fun getAllForItem(itemId: String): List<Comment> {
        val comment = Comment()
        comment.itemId = itemId
        val queryExpression = DynamoDBQueryExpression<Comment>()
                .withHashKeyValues(comment)

        return mapper.query(Comment::class.java, queryExpression)
    }

    fun allForItemWithRating(itemId: String, minRating: Int): List<Comment> {
        val comment = Comment()
        comment.itemId = itemId
        val queryExpression = DynamoDBQueryExpression<Comment>()
                .withHashKeyValues(comment)
                .withRangeKeyCondition(
                        "rating",
                        Condition()
                                .withComparisonOperator(ComparisonOperator.GE)
                                .withAttributeValueList(AttributeValue().withN(Integer.toString(minRating)))
                )

        return mapper.query(Comment::class.java, queryExpression)
    }

    fun allForUser(userId: String): List<Comment> {
        val comment = Comment()
        comment.userId = userId
        val queryExpression = DynamoDBQueryExpression<Comment>()
                .withHashKeyValues(comment)
                .withConsistentRead(false)

        return mapper.query(Comment::class.java, queryExpression)
    }

}
