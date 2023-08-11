package danekerscode.eventservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import danekerscode.eventservice.enums.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate time;

    @OneToOne
    @JoinColumn
    private Address address;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private String title;

    private String description;
}
