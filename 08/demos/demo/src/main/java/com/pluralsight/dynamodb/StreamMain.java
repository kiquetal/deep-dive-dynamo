package com.pluralsight.dynamodb;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.streamsadapter.AmazonDynamoDBStreamsAdapterClient;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.metrics.impl.NullMetricsFactory;
import com.pluralsight.dynamodb.streams.StreamsRecordProcessorFactory;

public class StreamMain {
    public static void main(String... args) {
        String streamArn = "arn:aws:dynamodb:us-east-1:913036176028:table/Orders/stream/2017-08-08T21:21:26.080";
        ProfileCredentialsProvider streamsCredentials = new ProfileCredentialsProvider();

        AmazonDynamoDBStreamsAdapterClient adapterClient =
                new AmazonDynamoDBStreamsAdapterClient(
                        streamsCredentials,
                        new ClientConfiguration()
                );

        KinesisClientLibConfiguration workerConfig = new KinesisClientLibConfiguration(
                "streams-adapter-demo",
                streamArn,
                streamsCredentials,
                "streams-demo-worker"
        )
        .withInitialPositionInStream(InitialPositionInStream.LATEST);

        Worker worker = new Worker.Builder()
                .recordProcessorFactory(new StreamsRecordProcessorFactory())
                .config(workerConfig)
                .kinesisClient(adapterClient)
                .build();

        worker.run();
    }
}
