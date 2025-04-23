package kr.hhplus.be.server.domain.venue;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venue")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    public Venue(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Test only
    public Venue(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}


