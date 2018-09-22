package com.pluralsight.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.transactions.Transaction;
import com.amazonaws.services.dynamodbv2.transactions.TransactionManager;
import com.pluralsight.dynamodb.domain.Comment;
import com.pluralsight.dynamodb.domain.Item;

import java.util.List;

public class CommentDao {

    private final DynamoDBMapper mapper;

    private final TransactionManager txManager;

    public CommentDao(AmazonDynamoDB dynamoDB) {
        this.mapper = new DynamoDBMapper(dynamoDB);
        this.txManager = new TransactionManager(dynamoDB, "Transactions", "TransactionImages");
    }

    public Comment put(Comment comment) {
        Transaction transaction = txManager.newTransaction();

        transaction.save(comment);

        Item item = new Item();
        item.setId(comment.getItemId());

        item = transaction.load(item);
        item.setTotalComments(item.getTotalComments() + 1);
        item.setTotalRating(item.getTotalRating() + comment.getRating());

        transaction.save(item);

        transaction.commit();

        return comment;
    }

    public Comment get(String itemId, String messageId) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        comment.setMessageId(messageId);

        return mapper.load(comment);
    }

    public void delete(String itemId, String messageId) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        comment.setMessageId(messageId);

        mapper.delete(comment);
    }

    public List<Comment> getAll() {
        return mapper.scan(Comment.class, new DynamoDBScanExpression());
    }

    public List<Comment> getAllForItem(String itemId) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
                .withHashKeyValues(comment);

        return mapper.query(Comment.class, queryExpression);
    }

    public List<Comment> allForItemWithRating(String itemId, int minRating) {
        Comment comment = new Comment();
        comment.setItemId(itemId);
        DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
                .withHashKeyValues(comment)
                .withRangeKeyCondition(
                        "rating",
                        new Condition()
                                .withComparisonOperator(ComparisonOperator.GE)
                                .withAttributeValueList(new AttributeValue().withN(Integer.toString(minRating)))
                );

        return mapper.query(Comment.class, queryExpression);
    }

    public List<Comment> allForUser(String userId) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
                .withHashKeyValues(comment)
                .withConsistentRead(false);

        return mapper.query(Comment.class, queryExpression);
    }

}
