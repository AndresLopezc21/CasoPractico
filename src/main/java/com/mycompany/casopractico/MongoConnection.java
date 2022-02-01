
package com.mycompany.casopractico;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;


public class MongoConnection {
    MongoClient mongoClient = null;
    public MongoClient crearConexion(){
        String servidor = "mongodb+srv://andres:123@cluster0.kif0z.mongodb.net/casoprac?retryWrites=true&w=majority";

        mongoClient = MongoClients.create(servidor);
        System.out.println("Connected successfully to server.");

        return mongoClient;
    }

    public MongoClient connectCollection(String nameCollection){

        MongoDatabase database = mongoClient.getDatabase("casoprac");
        MongoCollection<Document> collection = database.getCollection(nameCollection);
        System.out.println(collection.countDocuments());

        return null;
    }

    public MongoClient addField(String nombre, String apellidop,String apellidom,String calle,String numcas, String col, String codpos, String tel, String rfc,String status, String observaciones,String customid){
            MongoDatabase database = mongoClient.getDatabase("casoprac");
            MongoCollection<Document> collection = database.getCollection("tablainfo");

            Document document = new Document("_id", new ObjectId());

            document.append("Nombre", nombre)
                    .append("Apellido Paterno", apellidop)
                    .append("Apellido Materno", apellidom)
                    .append("Calle", calle)
                    .append("Numero de casa", numcas)
                    .append("Colonia", col)
                    .append("Codigo Postal", codpos)
                    .append("Telefono", tel)
                    .append("RFC", rfc)
                    .append("Estatus", status)
                    .append("Observaciones", observaciones)
                    .append("IdUnica", customid);
                    
            


            collection.insertOne(document);

            return null;
    }
    

    void addField(String text, String enviado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public List<String> getRows(){
        MongoDatabase database = mongoClient.getDatabase("casoprac");
        MongoCollection<Document> collection = database.getCollection("tablainfo");
        MongoCursor<Document> cursor = collection.find().iterator();
        List<String> documents = new ArrayList();


        while(cursor.hasNext()) {
            String json = JSON.serialize( cursor.next() );
            System.out.println(json);
            documents.add(json);
        }


        return documents;
    }
    public List<Object> updateDocument(String id, String status, String observaciones){
            MongoDatabase database = mongoClient.getDatabase("casoprac");
            MongoCollection<Document> collection = database.getCollection("tablainfo");

            Document query = new Document().append("IdUnica", id);

            Bson updates = Updates.combine(
                    Updates.set("Estatus", status), Updates.set("Observaciones", observaciones));
            UpdateOptions options = new UpdateOptions().upsert(true);
            UpdateResult result = collection.updateOne(query, updates, options);

            return null;
    }


}