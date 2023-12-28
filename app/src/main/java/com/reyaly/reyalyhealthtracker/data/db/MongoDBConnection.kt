package com.reyaly.reyalyhealthtracker.data.db

//import org.bson.Document
//import com.mongodb.client.MongoClients
//import com.mongodb.client.MongoCollection
//import com.mongodb.client.model.Aggregates
//import com.mongodb.client.model.Filters
//import com.mongodb.client.MongoClient
//import android.content.Context
//import android.util.Log
//import com.reyaly.reyalyhealthtracker.R
//
//
//object MongoDBConnection {
//
//    val TAG = "MongoDB"
//
//    fun getMongoClient(activityContext: Context): MongoClient {
//
//        val connectionString = activityContext.getString(R.string.mongo_db_uri)
//        Log.d(TAG, connectionString)
//        val db = activityContext.getString(R.string.mongo_db_name)
//
////        val mongoClient = MongoClient.create(connectionString)
////        val database = mongoClient.getDatabase("sample_mflix")
//
//        // Create a new client and connect to the server
////        MongoClient.create(connectionString).use { mongoClient ->
////            val database = mongoClient.getDatabase(db)
////            runBlocking {
////                database.runCommand(Document("ping", 1))
////            }
////            Log.d(TAG, "Pinged your deployment. You successfully connected to MongoDB!")
////        }
//
////        val mongoClient = MongoClient.create(connectionString)
////        val database = mongoClient.getDatabase(db)
//
//        return MongoClients.create(connectionString)
//    }
//}
