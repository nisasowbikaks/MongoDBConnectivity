package com.example;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Create a MongoDB client
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        
        // Get the database
        MongoDatabase database = mongoClient.getDatabase("college");
        
        // Get the collection
        MongoCollection<Document> collection = database.getCollection("registrations");
        
        // Use Case: Insert two user registrations for events
        // Create documents to insert
        Document registration1 = new Document("name", "Rasika")
            .append("event_name", "Tech Conference")
            .append("registration_date", new Date());

        Document registration2 = new Document("name", "Nisa Sowbika")
            .append("event_name", "Engineering Seminar")
            .append("registration_date", new Date());
        
        // Insert documents into the collection
        collection.insertOne(registration1);
        collection.insertOne(registration2);
        
        System.out.println("Documents inserted successfully!");

        // Test Case 1: Verify if registrations have been inserted
        System.out.println("Verify registration records inserted.");
        
        // List the documents (registrations)
        FindIterable<Document> documents = collection.find();
        boolean isRegistration1Inserted = false;
        boolean isRegistration2Inserted = false;

        for (Document doc : documents) {
            String name = doc.getString("name");
            String eventName = doc.getString("event_name");
            if (name.equals("Rasika") && eventName.equals("Tech Conference")) {
                isRegistration1Inserted = true;
            }
            if (name.equals("Nisa Sowbika") && eventName.equals("Engineering Seminar")) {
                isRegistration2Inserted = true;
            }
        }

        // Test Result: Check if both registrations are present
        if (isRegistration1Inserted && isRegistration2Inserted) {
            System.out.println("Both registrations are inserted.");
        } else {
            System.out.println("One or both registrations are missing.");
        }

        // Test Case 2: Attempt to insert the same user for the same event twice
        System.out.println("Attempt to insert duplicate registration.");

        try {
            // Try inserting the same registration again
            collection.insertOne(registration1);  // Try inserting the same user for the same event
            System.out.println("Duplicate registration allowed.");
        } catch (Exception e) {
            System.out.println("Duplicate registration prevented.");
        }

        // Close the connection
        mongoClient.close();
    }
}
