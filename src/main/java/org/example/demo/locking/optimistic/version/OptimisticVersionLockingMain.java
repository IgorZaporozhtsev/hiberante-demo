package org.example.demo.locking.optimistic.version;

import org.example.demo.entity.Person;
import org.example.demo.util.SessionUtil;
import org.hibernate.jpa.QueryHints;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OptimisticVersionLockingMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("default");

        // insertPerson();

        //optimisticTypeVersion();
        pessimisticTypeVersion();


    }
    //той хто зробить перший коміт тот і переможе - optimistic
    private static void optimisticTypeVersion() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> SessionUtil.doInSession(entityManagerFirst -> {
            var first = entityManagerFirst.find(Person.class, 2L);
            first.setFirstName("Minato1");

        }));

        executor.execute(() -> SessionUtil.doInSession(entityManagerSecond -> {
            var second = entityManagerSecond.find(Person.class, 2L);
            second.setFirstName("Sakura1");
        }));

    }

    //той хто зробить перший коміт тот і переможе - optimistic

    @Transactional
    private static void pessimisticTypeVersion() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        var entityManager_1 = emf.createEntityManager();
        var entityManager_2 = emf.createEntityManager();

        // begin wto transactions
        entityManager_1.getTransaction().begin();
        entityManager_2.getTransaction().begin();

        //update same column
        var first = entityManager_1.find(
                Person.class,
                2L,
                LockModeType.PESSIMISTIC_READ);

        first.setFirstName("3-Minato");

        var second = entityManager_2.find(Person.class, 2L);
        second.setFirstName("3-Sakura");

        //commit changes
        entityManager_2.getTransaction().commit();
        entityManager_1.getTransaction().commit();


    }


    private static void insertPerson() {
        SessionUtil.doInSession(entityManager -> {
            var merlinManson = new Person();
            merlinManson.setFirstName("Merlin");
            merlinManson.setLastName("Manson");
            entityManager.persist(merlinManson);
        });


    }


}
