package com.example.person.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

@Value
@JsonDeserialize
@JsonIgnoreProperties (value = { "_id" })
public class Employee {

    //todo weirdly only the name is causing this issue.
    @JsonProperty("Name")
    public final String name;
    public final String title;
    public final String department;
    public final float salary;
    public final float taxesOwed;

    @JsonCreator
    public Employee(@JsonProperty("Name")String name, @JsonProperty("Title")String title, @JsonProperty("Department")String department, @JsonProperty("Salary")float salary, @JsonProperty("Taxes Owed")float taxesOwed)
    {
        this.name = name;
        this.title = title;
        this.department = department;
        this.salary = salary;
        this.taxesOwed = taxesOwed;
    }
}
