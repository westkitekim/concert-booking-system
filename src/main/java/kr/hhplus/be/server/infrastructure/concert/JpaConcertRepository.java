package kr.hhplus.be.server.infrastructure.concert;

import kr.hhplus.be.server.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByTitleContaining(String keyword);
}
