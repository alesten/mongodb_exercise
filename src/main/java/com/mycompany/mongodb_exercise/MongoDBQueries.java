/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodb_exercise;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author jonassimonsen
 */
public class MongoDBQueries {

    private String clientURI;
    private String dbName;
    private String collectionName;
    MongoCollection<Document> collection;

    public MongoDBQueries(String clientURI, String dbName, String collectionName) {
        this.clientURI = clientURI;
        this.dbName = dbName;
        this.collectionName = collectionName;
        MongoClientURI connStr = new MongoClientURI(clientURI);
        MongoClient mongoClient = new MongoClient(connStr);
        MongoDatabase db = mongoClient.getDatabase(dbName);
        collection = db.getCollection(collectionName);
    }

    // How many users are there in our database?
    public int getNumberOfUsersInDatabase() {
        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$group", new Document("_id", "$user")), // Gathers a group of all users.
                new Document("$group", new Document("_id", null).append("count", new Document("$sum", 1))), // Calculates sum for the already-retrieved group of users, putting the result of $sum into the variable "count".
                new Document("$project", new Document("_id", 0).append("count", 1)) // Returns, with the append specifying the inclusion of the field "count" in the return result.
        ));

        int count = 0;

        for (Document dbObject : output) {

            count = (int) dbObject.get("count");
        }
        return count;
    }

    // Which Twitter users link the most to other Twitter users? (Provide the top ten.) 
    public List<Object> getTopTenActiveUsers() {
        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                // "user","$user" means that we're creating variables "user", and inserting variables from every user by saying $user.
                // Could, for example, write '22' and we would create 'user' variables only for user 22.
                new Document("$group", new Document("_id", new Document("user", "$user").append("tweet_id", "$id"))), // Get sets of every user and tweet association.
                new Document("$group", new Document("_id", "$_id.user").append("tweet_count", new Document("$sum", 1))), // Get the sum of every occurence of a unique user id.
                new Document("$project", new Document("_id", 0).append("user", "$_id").append("tweet_count", 1)), // Cuts off unnecessary data, leaving only the users and their tweet counts.
                new Document("$sort", new Document("tweet_count", -1)), //-1 sorts in descending order.
                new Document("$limit", 10) // Get the top 10.
        )).allowDiskUse(Boolean.TRUE);

        List<Object> topTenActiveUsers = new ArrayList<>();

        for (Document dbObject : output) {

            topTenActiveUsers.add(dbObject.get("user") + ", " + dbObject.get("tweet_count"));
        }

        return topTenActiveUsers;
    }

    // Who are the most active users? (Provide top ten.)
    public List<Object> getTopTenLinkedUsers() {
        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                new Document("$match", new Document("text", new Document("$regex", ".*@.*"))),
                new Document("$group", new Document("_id", new Document("user", "$user").append("tweet_id", "$id"))),
                new Document("$group", new Document("_id", "$_id.user").append("tweet_count", new Document("$sum", 1))),
                new Document("$project", new Document("_id", 0).append("user", "$_id").append("tweet_count", 1)),
                new Document("$sort", new Document("tweet_count", -1)),
                new Document("$limit", 10)
        )).allowDiskUse(Boolean.TRUE);

        List<Object> topTenLinkedUsers = new ArrayList<>();

        for (Document dbObject : output) {
            topTenLinkedUsers.add(dbObject.get("user") + ", " + dbObject.get("tweet_count"));
        }

        return topTenLinkedUsers;
    }
}
