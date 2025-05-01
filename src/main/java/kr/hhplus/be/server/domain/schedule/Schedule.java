package kr.hhplus.be.server.domain.schedule;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.TestOnly;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private Long venueId;

    @Column(nullable = false)
    private LocalDateTime startAt;

    public Schedule(Long concertId, Long venueId, LocalDateTime startAt) {
        this.concertId = concertId;
        this.venueId = venueId;
        this.startAt = startAt;
    }

    @TestOnly
    public Schedule(Long id, Long concertId, Long venueId, LocalDateTime startAt) {
        this.id = id;
        this.concertId = concertId;
        this.venueId = venueId;
        this.startAt = startAt;
    }

}
