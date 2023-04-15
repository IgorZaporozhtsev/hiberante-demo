package org.example.demo;

import javax.persistence.Persistence;

/**
 * Hello world!
 */
public class DemoApp {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("default");
        var entityManager = emf.createEntityManager();

        var person = entityManager.find(Person.class, 1L);
        entityManager.close();

        System.out.println(person);


    }
}
