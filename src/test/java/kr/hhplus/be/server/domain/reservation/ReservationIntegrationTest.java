package kr.hhplus.be.server.domain.reservation;

import kr.hhplus.be.server.domain.queuetoken.QueueTokenRepository;
import kr.hhplus.be.server.domain.seat.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

//    @Test
//    void 실제_좌석_예약이_진행된다() {
//        // given
//        // seat, queueToken, user 등 DB에 저장
//
//        // when
//        ReservationCommand cmd = new ReservationCommand(...);
//        reservationService.reserveSeat(cmd);
//
//        // then
//        assertTrue(reservationRepository.existsByScheduleIdAndSeatId(...));
//    }
}
