/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodb_exercise;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.BsonValue;
import org.bson.Document;

/**
 *
 * @author alexander
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("User Count: " + getUserCount());
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
