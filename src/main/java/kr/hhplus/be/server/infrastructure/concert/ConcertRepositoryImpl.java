package kr.hhplus.be.server.infrastructure.concert;

import kr.hhplus.be.server.domain.concert.Concert;
import kr.hhplus.be.server.domain.concert.ConcertRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private final JpaConcertRepository jpaRepository;

    public ConcertRepositoryImpl(JpaConcertRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Concert> findById(Long concertId) {
        return jpaRepository.findById(concertId);
    }

    @Override
    public List<Concert> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Concert> findByTitleContaining(String keyword) {
        return jpaRepository.findByTitleContaining(keyword);
    }
}
