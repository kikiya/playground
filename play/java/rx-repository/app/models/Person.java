package models;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.mongodb.async.client.MongoCollection;
//import com.mongodb.client.MongoCollection;
import com.google.inject.Inject;
import com.mongodb.WriteResult;
import com.mongodb.rx.client.MongoClient;
//import com.mongodb.rx.client.MongoCollection;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

public class Person {


        @Inject
        public static PlayJongo jongo; // = Play.application().injector().instanceOf(PlayJongo.class);

        public static MongoCollection people() {
            return jongo.getCollection("people");
        }

        @JsonProperty("_id")
        public ObjectId id;

        public String name;

        public WriteResult insert(Person person) {

            return people().insert(person);
        }

        public void remove() {
            people().remove(id);
        }

        public static Person findByName(String name) {
            return people().findOne("{name: #}", name).as(Person.class);
        }



}

