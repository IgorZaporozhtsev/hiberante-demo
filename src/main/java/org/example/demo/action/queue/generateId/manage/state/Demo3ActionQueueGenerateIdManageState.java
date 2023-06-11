package org.example.demo.action.queue.generateId.manage.state;

import org.example.demo.entity.Person;

import javax.persistence.Persistence;

public class Demo3ActionQueueGenerateIdManageState {

    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("default");
        var entityManager = emf.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            var person = new Person();
            person.setFirstName("Saski");
            person.setLastName("Uchiha");

            entityManager.persist(person);

            entityManager.detach(person); // removes entity from the session
            entityManager.refresh(person); // loads new version of data from the DB
            entityManager.clear(); // removes everything from session
            var atachedPerson = entityManager.merge(person);// add person to session, SELECT than compare in cache, and merge difference


            System.out.println(person);

            entityManager.getTransaction().commit();
        }catch (Exception exception){
            entityManager.getTransaction().rollback();
            throw exception;
        }finally {
            entityManager.close();
        }


    }
}
