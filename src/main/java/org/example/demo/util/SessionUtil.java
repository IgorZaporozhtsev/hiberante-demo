package org.example.demo.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Consumer;
import java.util.function.Function;

public class SessionUtil {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public static <T> T doInSessionReturning(Function<EntityManager, T> entityManagerFunction) {
        var em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            var result = entityManagerFunction.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static void doInSession(Consumer<EntityManager> entityManagerConsumer) {
        doInSessionReturning(entityManager -> {
            entityManagerConsumer.accept(entityManager);
            return null;
        });
    }
}
