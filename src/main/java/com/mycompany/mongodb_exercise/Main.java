/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodb_exercise;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.BsonValue;
import org.bson.Document;

/**
 *
 * @author alexander
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Document> s = getTopMentioner(10);
        int count = 1;
        for (Document doc : s) {
            System.out.println(count++ + ": " + doc.getString("_id") + " with " + doc.getInteger("tweeted") + " tweets");
        }
        //System.out.println("User Count: " + getUserCount());
    }
    
    private static ArrayList<Document> getTopMentioner(int limit){
        MongoClientURI connStr = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connStr);

        MongoDatabase db = mongoClient.getDatabase("social_net");
        MongoCollection<Document> col = db.getCollection("tweets");
        
        ArrayList<Document> stuff = col.aggregate(Arrays.asList(
            new Document("$match", new Document("text", new Document("$regex", ".*@.*"))),
            new Document("$group", new Document("_id", new Document("user", "$user").append("tweetId", "$id"))),
            new Document("$group", new Document("_id", "$_id.user").append("tweeted", new Document("$sum", 1))),
            new Document("$sort", new Document("tweeted", -1)),
            new Document("$limit", limit)
        ), Document.class).into(new ArrayList<>());
        
        
        //db.tweets.aggregate([{$match: {"text": {$regex: ".*@.*"}}}, 
            //{$group: {_id :{user: "$user", tweetId: "$id"}}}, 
            //{$group: {_id: "$_id.user", tweeted: {$sum: 1}}}, 
            //{$sort: {tweeted: -1}}, 
            //{$limit: 10}])
        return stuff;
    }
    
    private static long getUserCount() {
        MongoClientURI connStr = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connStr);

        MongoDatabase db = mongoClient.getDatabase("social_net");
        MongoCollection<Document> col = db.getCollection("tweets");
        
        ArrayList<BsonValue> dis = col.distinct("user", BsonValue.class)
                .into(new ArrayList<>());
        
        return dis.size();
    }
}
