package com.pluralsight.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.pluralsight.dynamodb.dao.ItemDao;
import com.pluralsight.dynamodb.domain.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestItemDao {

    static AmazonDynamoDB dynamodb;
    static ItemDao itemDao;

    @Before
    public void before() {
        dynamodb = DynamoDBEmbedded.create().amazonDynamoDB();
        Utils.createTables(dynamodb);
        itemDao = new ItemDao(dynamodb);
    }

    @Test
    public void testDynamoDB() {
        Item item = itemDao.put(new Item());
        assertNotNull(itemDao.get(item.getId()));
    }
}
