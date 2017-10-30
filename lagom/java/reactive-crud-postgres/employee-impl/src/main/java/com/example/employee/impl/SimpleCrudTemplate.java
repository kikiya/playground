package com.example.employee.impl;

import akka.Done;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface SimpleCrudTemplate<T> {

    CompletionStage<Done> create(T t);

    CompletionStage<Optional<T>> retrieve(String id);

    CompletionStage<Done> update(T t);

    CompletionStage<Done> delete(String id);

    CompletionStage<List<T>>  getDocs ();
}
