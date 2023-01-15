package com.alexIntervale1.app2.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity
@AllArgsConstructor
@Table(name = "request")
public class RequestMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "personalNumber")
    private Long personalNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "progressControl")
    private String progressControl ;
    @Column(name = "correlationID")
    private String correlationID;


    public RequestMessage(){
       this.progressControl= "in work";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RequestMessage that = (RequestMessage) o;
        return personalNumber != null && Objects.equals(personalNumber, that.personalNumber);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
