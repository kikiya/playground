package com.example.employee.impl;

import akka.Done;
import akka.NotUsed;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.example.employee.api.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;

import java.util.List;
import java.util.concurrent.CompletionStage;

@Singleton
public class EmployeeTemplate implements CrudTemplate<Employee> {

    private  MongoCollection collection;
    private  Materializer materializer;
    private ObjectMapper mapper;

    @Inject
    public EmployeeTemplate(MongoCollection collection, Materializer materializer){
        this.collection = collection;
        this.materializer = materializer;
        this.mapper = new ObjectMapper();
    }

    @Override
    public CompletionStage<Done> create(Employee employee) throws JsonProcessingException {
        String employeeString = mapper.writeValueAsString(employee);
        final Source<Document, NotUsed> source =
                Source.fromPublisher(collection.insertOne(Document.parse(employeeString)));
        return source.runWith(Sink.ignore(), materializer);
    }

    @Override
    public CompletionStage<Employee> retrieve(String id) {
        //todo need to use a different field name
        final Source<Document, NotUsed>  source = Source.fromPublisher(collection.find(Filters.eq("Name", id)));
        CompletionStage<Employee> employee = source.map(doc -> mapper.readValue(doc.toJson(), Employee.class))
                .runWith(Sink.head(), materializer);
        return employee;
    }

    @Override
    public CompletionStage<Done> update(Employee employee) throws JsonProcessingException {
        String employeeString = mapper.writeValueAsString(employee);
        final Source<Document, NotUsed>  source = Source.fromPublisher(collection.replaceOne(
                Filters.eq("Name", employee.name), Document.parse(employeeString) ));
        return source.runWith(Sink.ignore(), materializer);
    }

    @Override
    public CompletionStage<Done> delete(String id) {
        final Source<Document, NotUsed>  source = Source.fromPublisher(collection.deleteOne(Filters.eq("Name", id)));
        return source.runWith(Sink.ignore(), materializer);
    }

    public CompletionStage<List<Employee>> getDocs (){
        final Source<Document, NotUsed> source = Source.fromPublisher(collection.find().limit(30));
        CompletionStage<List<Employee>> documents  = source.map(doc-> mapper.readValue(doc.toJson(), Employee.class))
                .runWith(Sink.seq(), materializer);
        return  documents;
    }

}
