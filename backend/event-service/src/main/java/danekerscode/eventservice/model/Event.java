package danekerscode.eventservice.model;

import danekerscode.eventservice.enums.EventType;
import danekerscode.eventservice.model.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    @OneToOne
    @JoinColumn
    private Address address;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private String title;

    private String description;
}
