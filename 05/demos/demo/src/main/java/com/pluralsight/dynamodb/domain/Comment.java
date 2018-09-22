package com.pluralsight.dynamodb.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.pluralsight.dynamodb.LocalDateTimeConverter;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "Comments")
public class Comment {
    @DynamoDBHashKey
    private String itemId;

    @DynamoDBRangeKey
    @DynamoDBAutoGeneratedKey
    private String messageId;

    @DynamoDBAttribute
    private String message;

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime dateTime;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "UserID-index")
    private String userId;

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "UserID-index",
            localSecondaryIndexName = "Rating-index")
    private int rating;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "itemId='" + itemId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", userId='" + userId + '\'' +
                ", rating=" + rating +
                '}';
    }
}