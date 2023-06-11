package org.example.demo;

import org.example.demo.entity.Address;
import org.example.demo.entity.Author;
import org.example.demo.entity.Note;
import org.example.demo.entity.Person;
import org.example.demo.util.SessionUtil;

import javax.persistence.EntityManager;

public class Main {

    public static void main(String[] args) {

        SessionUtil.doInSession(entityManager -> {
            //setUpData(entityManager);
            //fetchNPlusOneData(entityManager);
            fetchWithEntityGraph(entityManager); //fix N+1
        });
    }

    private static void fetchWithEntityGraph(EntityManager entityManager) {
        var query = entityManager.createQuery("from Person", Person.class);
        var entityGraph = entityManager.createEntityGraph(Person.class);
        entityGraph.addAttributeNodes("address");
        entityGraph.addSubgraph("notes")
                .addAttributeNodes("author");

        query.setHint("javax.persistence.fetchgraph", entityGraph);

        var persons = query.getResultList();
        for (Person person : persons) {
            System.out.println(person.getAddress().getCity());
            for (Note note : person.getNotes()) {
                System.out.println(note.getBody());
            }
        }

    }

    private static void fetchNPlusOneData(EntityManager entityManager) {
        var query = entityManager.createQuery("from Person", Person.class);
        var persons = query.getResultList();

        for (Person person : persons) {
            System.out.println(person.getAddress().getCity());
            for (Note note : person.getNotes()) {
                System.out.println(note.getBody());
            }
        }
    }

    private static void setUpData(EntityManager entityManager) {
        var address1 = new Address("London", "DC");
        var address2 = new Address("NewHampshire", "RD");
        var address3 = new Address("Attomole", "DF");

        var person1 = new Person("Naruto", "Uzumaki");
        person1.setAddress(address1);
        var batabayo = new Note("Batabayo");
        batabayo.setAuthor(new Author("None"));
        person1.addNote(batabayo);

        var person2 = new Person("Minato", "Kazamaki");
        person2.setAddress(address2);
        var lightMan = new Note("Light Man");
        lightMan.setAuthor(new Author("None"));

        person2.addNote(lightMan);

        var person3 = new Person("Saski", "Uchikha");
        person3.setAddress(address3);

        var sharingan = new Note("Sharingan");
        sharingan.setAuthor(new Author("None"));
        person3.addNote(sharingan);

        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
    }
}
