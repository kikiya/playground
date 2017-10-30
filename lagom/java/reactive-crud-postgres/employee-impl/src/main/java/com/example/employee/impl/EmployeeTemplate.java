package com.example.employee.impl;

import akka.Done;
import akka.stream.Materializer;
import akka.stream.alpakka.slick.javadsl.Slick;
import akka.stream.alpakka.slick.javadsl.SlickRow;
import akka.stream.alpakka.slick.javadsl.SlickSession;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.example.employee.api.Employee;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@Singleton
public class EmployeeTemplate implements SimpleCrudTemplate<Employee> {

    private final Materializer materializer;
    private final SlickSession slickSession;

    @Inject
    public EmployeeTemplate(Materializer materializer){
        slickSession = SlickSession.forConfig("slick-postgres");
        this.materializer = materializer;
    }

    @Override
    public CompletionStage<Done> create(Employee employee) {
        final CompletionStage<Done> done =
                Source.single(employee).via(Slick.flow(
                        slickSession,
                        employee1 -> "INSERT INTO import.employee_chicago_salaries VALUES ('" + employee.name + "','"
                                + employee.title+ "','" + employee.department+ "'," + employee.salary+ ")"
                )).runWith(Sink.ignore(), materializer);
        return done;
    }

    @Override
    public CompletionStage<Optional<Employee>> retrieve(String id) {
        String selectEmployee = String.format("SELECT * FROM import.employee_chicago_salaries WHERE Name = '%s' LIMIT 1", id);

       return Slick.source(slickSession,
                    selectEmployee,
                (SlickRow row) -> new Employee(row.nextString(), row.nextString(), row.nextString(), row.nextFloat(), 0F))
                .runWith(Sink.headOption(), materializer);
       }

    @Override
    public CompletionStage<Done> update(Employee employee) {
        String updateEmployee = String.format("UPDATE import.employee_chicago_salaries\n" +
                "         SET name='%s', title='%s', department='%s', salary=%f\n" +
                "         WHERE name = '%s'", employee.name, employee.title,
                employee.department, employee.salary, employee.name);

        return Source.single(employee).via(Slick.flow(
                slickSession,
                employee_ -> updateEmployee
                )).runWith(Sink.ignore(), materializer);
    }

    @Override
    public CompletionStage<Done> delete(String id) {
        String deleteEmployee = String.format("DELETE FROM import.employee_chicago_salaries WHERE Name = '%s' ", id);
        CompletionStage<Done> done =
                Source.single(id).via(Slick.flow(
                        slickSession,
                        id_ -> deleteEmployee
                        )).runWith(Sink.ignore(), materializer);
        return done;
    }

    @Override
    public CompletionStage<List<Employee>> getDocs (){
        String sample = "SELECT * FROM import.employee_chicago_salaries LIMIT 100";
        return Slick.source(slickSession,
                sample,
                (SlickRow row) -> new Employee(row.nextString(), row.nextString(), row.nextString(), row.nextFloat(), 0F))
                .runWith(Sink.seq(), materializer);
    }

}
