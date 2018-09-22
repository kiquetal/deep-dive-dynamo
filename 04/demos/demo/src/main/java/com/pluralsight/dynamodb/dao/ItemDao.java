package com.pluralsight.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.pluralsight.dynamodb.domain.Item;

import java.util.List;

public class ItemDao {

    private final DynamoDBMapper mapper;

    public ItemDao(AmazonDynamoDB dynamoDb) {
        this.mapper = new DynamoDBMapper(dynamoDb);
    }

    public Item put(Item item) {
        mapper.save(item, DynamoDBMapperConfig
                .builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                .build());

        return item;
    }

    public Item get(String id) {
        return mapper.load(Item.class, id);
    }

    public void update(Item item) {
        mapper.save(item);
    }

    public void delete(String id) {
        Item item = new Item();
        item.setId(id);

        mapper.delete(item);
    }

    public List<Item> getAll() {
        return mapper.scan(Item.class, new DynamoDBScanExpression());
    }
}
