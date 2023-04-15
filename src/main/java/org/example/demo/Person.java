package org.example.demo;

import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;


}
