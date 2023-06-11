package org.example.demo.orm.introduction;

import org.example.demo.entity.Person;

import javax.persistence.Persistence;

/**
 * Hello world!
 */
public class Demo1IntroductionToORM {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("default");

        var entityManager = emf.createEntityManager();
        var person = entityManager.find(Person.class, 21L);
        System.out.println(person);

        //start Transaction
        entityManager.getTransaction().begin();

        var merlinManson = new Person();
        merlinManson.setFirstName("Merlin");
        merlinManson.setLastName("Manson");
        //entityManager.persist(merlinManson);

        var jeniferAnniston = new Person();
        jeniferAnniston.setFirstName("Jenifer");
        jeniferAnniston.setLastName("Anniston ");
        //entityManager.persist(jeniferAnniston);

        //end Transaction
        entityManager.getTransaction().commit();


        entityManager.createQuery("select p from Person p where p.firstName = :name", Person.class)
                .setParameter("name", "Jenifer")
                .getResultStream()
                .forEach(System.out::println);


        entityManager.close();
    }
}
