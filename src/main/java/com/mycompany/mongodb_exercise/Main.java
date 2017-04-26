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
import org.bson.Document;

/**
 *
 * @author alexander
 */
public class Main {
    public static void main(String[] args) {
        MongoClientURI connStr = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connStr);

        MongoDatabase db = mongoClient.getDatabase("social_net");
        MongoCollection<Document> collection = db.getCollection("tweets");

        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }
}
