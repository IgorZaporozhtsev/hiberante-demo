package org.example.demo.session.cache;

import org.example.demo.entity.Person;
import org.example.demo.util.SessionUtil;

public class Demo2SessionAndCache {

    public static void main(String[] args) {
        //1. різні об'єкти в пам'яті, Hibernate зберігає Entity в рамках однієї сесії і вона на шариться між сесіями
        //firstExample();

        //2. В рамках сессії якщо післся першого select змінити поле в БД то виведе теж самий об'єкт у другому select
        // що і у першому select з тим самими полями (це поведінка як repeatable read)
        secondExample();



    }
    private static void secondExample(){
        SessionUtil.doInSession(entityManager -> {
            var person = entityManager.find(Person.class, 1L);
            System.out.println(person);

            var theSamePerson = entityManager
                    .createQuery("select p from Person p where p.firstName = :firstName", Person.class)
                    .setParameter("firstName", "Tom")
                    .getSingleResult();
            System.out.println(theSamePerson);

            System.out.println(person == theSamePerson);
        });
    }

    private static void firstExample(){
        //--
        var person21 = SessionUtil.doInSessionReturning(em -> em.find(Person.class, 1L));
        var thSamePerson21 = SessionUtil.doInSessionReturning(em -> em.find(Person.class, 1L));
        System.out.println(person21 == thSamePerson21); //різні об'єкти в пам'яті, Hibernate зберігає Entity
        // в рамках однієї сесії і вона на шариться між сесіями
        //--
    }





}
