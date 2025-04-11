package kr.hhplus.be.server.domain.venue;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository {
    Optional<Venue> findById(Long id);
    List<Venue> findAll();
}

