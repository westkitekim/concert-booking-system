package kr.hhplus.be.server.domain.concert;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConcertRepository {

    Optional<Concert> findById(Long concertId);

    List<Concert> findAll();

    List<Concert> findByTitleContaining(String keyword);
}

