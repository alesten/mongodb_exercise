/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mongodb_exercise;

import java.util.List;

/**
 *
 * @author alexander
 */
public class Main {

    public static void main(String[] args) {
        //You might have to change your port back to 27017 - since i use another port on my machine :)
        MongoDBQueries mdb = new MongoDBQueries("mongodb://localhost:27018", "social_net", "tweets");

        System.out.println("\nNumber of Twitter accounts\n" + mdb.getNumberOfUsersInDatabase());
        System.out.println("\nMost active users");
        printList(mdb.getTopTenActiveUsers());
        System.out.println("\nPeople that @mention others most");
        printList(mdb.getTopTenLinkedUsers());
    }

    private static void printList(List<Object> list) {

        List<Object> activeList = list;
        int count = 1;

        for (Object object : activeList) {
            System.out.println(count++  + ": " +  object.toString());
        }

    }
}
