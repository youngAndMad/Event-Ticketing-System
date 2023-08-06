package danekerscode.eventservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mangilik el 64a/4
    private String country; // Kazakhstan
    private String city; // Astana
    private String street; // Mangilik el
    private String mark;// 64a/4
    private String buildingName; // live house

}
