package kr.hhplus.be.server.domain.venue;

import lombok.Getter;

@Getter
public class Venue {
    private final Long id;
    private final String name;
    private final String location;

    public Venue(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}

