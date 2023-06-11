package org.example.demo.relation;

import org.example.demo.entity.Note;
import org.example.demo.entity.Person;
import org.example.demo.util.SessionUtil;

import javax.persistence.TypedQuery;


public class RelationDemo {
    public static void main(String[] args) {
        SessionUtil.doInSession(entityManager -> {
//            var person1 = entityManager.find(Person.class, 1L);
//
//            var note = new Note("Amazing Note");
//            note.setPerson(person1);
//            person1.getNotes().add(note);


            var persons = entityManager.createQuery("select p from Person p ", Person.class)
                    .getResultList();

            for (Person person : persons) {
                if (person.getNotes().size() >=1) {
                    System.out.println("------------- " + person.getFirstName());
                }
            }

        });

    }
}
