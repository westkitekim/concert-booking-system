package kr.hhplus.be.server.application.integration;

import kr.hhplus.be.server.domain.queuetoken.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.QueueTokenRepository;
import kr.hhplus.be.server.domain.queuetoken.TokenGenerator;
import kr.hhplus.be.server.domain.reservation.Reservation;
import kr.hhplus.be.server.domain.reservation.ReservationCommand;
import kr.hhplus.be.server.domain.reservation.ReservationInfo;
import kr.hhplus.be.server.domain.reservation.ReservationService;
import kr.hhplus.be.server.domain.seat.Seat;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import kr.hhplus.be.server.domain.seat.SeatStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class ReservationIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("hhplus")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    ReservationService reservationService;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    QueueTokenRepository queueTokenRepository;

    @Autowired
    TokenGenerator tokenGenerator;

    @Test
    void 좌석_예약이_정상적으로_진행된다() {
        Seat seat = new Seat(1L, "A", 1, SeatStatusEnum.AVAILABLE);
        seatRepository.save(seat);

        QueueToken token = new QueueToken(1L, tokenGenerator.generateToken(1L), 1);
        queueTokenRepository.save(token);

        ReservationCommand command = new ReservationCommand(
                token.getToken(), 1L, 1L, 1L, seat.getSeatId(), new BigDecimal("30000")
        );

        ReservationInfo reservationInfo = reservationService.reserveSeat(command);

        assertThat(reservationInfo).isNotNull();
        assertThat(reservationInfo.seatId()).isEqualTo(seat.getSeatId());
    }

    @Test
    void 임시배정_좌석이면_예약이_실패한다() {
        // given
        Seat seat = new Seat(1L, "A", 2, SeatStatusEnum.HELD);
        seatRepository.save(seat);

        QueueToken token = new QueueToken(1L, tokenGenerator.generateToken(1L), 1);
        queueTokenRepository.save(token);

        ReservationCommand command = new ReservationCommand(
                token.getToken(), 1L, 1L, 1L, seat.getSeatId(), new BigDecimal("30000")
        );

        assertThatThrownBy(() -> reservationService.reserveSeat(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("예약 불가한 좌석입니다");
    }

    @Test
    void 만료된_토큰이면_예약이_실패한다() {
        Seat seat = new Seat(1L, "A", 3, SeatStatusEnum.AVAILABLE);
        seatRepository.save(seat);

        QueueToken token = new QueueToken(1L, tokenGenerator.generateToken(1L), 1);
        queueTokenRepository.save(token);

        ReservationCommand command = new ReservationCommand(
                token.getToken(), 1L, 1L, 1L, seat.getSeatId(), new BigDecimal("30000")
        );

        assertThatThrownBy(() -> reservationService.reserveSeat(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("만료된 토큰입니다");
    }
}