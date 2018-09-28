package com.pluralsight.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.pluralsight.dynamodb.dao.ItemDao;
import com.pluralsight.dynamodb.dao.SubscriptionDao;
import com.pluralsight.dynamodb.domain.Item;
import com.pluralsight.dynamodb.domain.Msisdn;
import com.pluralsight.dynamodb.domain.Subscriptions;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;

public class TestItemDao {

    static AmazonDynamoDB dynamodb;
    static ItemDao itemDao;

    @Before
    public void before() {
      System.setProperty("sqlite4java.library.path", "native-libs");

        dynamodb = DynamoDBEmbedded.create().amazonDynamoDB();
        Utils.createTables(dynamodb);
        itemDao = new ItemDao(dynamodb);
    }

    @Test
    public void testMsisdnTable() {
        Msisdn m=new Msisdn();
        m.setCountry("CO");
        m.setMsisdn("57123123213");
        Subscriptions sub=new Subscriptions();
        sub.setMsisdn("57123123213");

        sub.setTimestamp(LocalDateTime.now().toString());
        SubscriptionDao subDao=new SubscriptionDao(dynamodb);
        subDao.createSubscription(sub);

       subDao.returnList().forEach(subscriptions -> System.out.println(subscriptions));




    }
    @Test
    public void testDynamoDB() {
        Item item = itemDao.put(new Item());
        System.out.println(item);
        assertNotNull(itemDao.get(item.getId()));
    }
}
