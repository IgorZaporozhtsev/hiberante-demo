package org.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "persons")
@ToString
//@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

//    @Version
//    private Long version;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<Note> notes = new ArrayList<>();

    public void addNode(Note note){
        note.setPerson(this);
        notes.add(note);
    }

}
