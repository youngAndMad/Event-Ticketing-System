package danekerscode.ticketservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime boughtTime;
    private Long eventId;
    private Long userId;
    private String code;

    {
        this.code = UUID.randomUUID().toString();
        this.boughtTime = LocalDateTime.now();
    }

    public Ticket(Long eventId, Long userId) {
        this.eventId = eventId;
        this.userId = userId;
    }
}
