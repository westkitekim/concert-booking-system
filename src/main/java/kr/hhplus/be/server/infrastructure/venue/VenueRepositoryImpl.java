package kr.hhplus.be.server.infrastructure.venue;

import kr.hhplus.be.server.domain.venue.Venue;
import kr.hhplus.be.server.domain.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VenueRepositoryImpl implements VenueRepository {

    private final JpaVenueRepository jpaVenueRepository;

    @Override
    public Optional<Venue> findById(Long id) {
        return jpaVenueRepository.findById(id);
    }

    @Override
    public List<Venue> findAll() {
        return jpaVenueRepository.findAll();
    }
}
