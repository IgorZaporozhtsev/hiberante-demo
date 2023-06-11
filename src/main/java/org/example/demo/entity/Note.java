package org.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "notes")
@ToString(exclude = "person")
@Getter
@Setter
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String body;

    @OneToOne(cascade = CascadeType.ALL)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Note(String body) {
        this.body = body;
    }
}
