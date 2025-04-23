package kr.hhplus.be.server.interfaces.concert;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.concert.ConcertService;
import kr.hhplus.be.server.domain.concert.dto.ConcertInfo;
import kr.hhplus.be.server.domain.concert.dto.FilterConcertQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts")
@Tag(name = "Concert", description = "콘서트 조회 API")
public class ConcertController {

    private final ConcertService concertService;
    private final ConcertResponseMapper mapper;

    @GetMapping
    @Operation(summary = "콘서트 전체 목록 조회", description = "필터 조건에 따른 콘서트 목록을 조회합니다.")
    public List<ConcertResponse> getConcerts(@ModelAttribute ConcertRequest request) {
        FilterConcertQuery query = new FilterConcertQuery(
                request.title(),
                request.venueId(),
                request.from(),
                request.to()
        );
        List<ConcertInfo> result = concertService.getConcertsWithSchedules(query);
        return result.stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{concertId}/available-dates")
    @Operation(summary = "예약 가능 날짜 조회", description = "예약 가능한 날짜 목록을 반환합니다.")
    public ConcertResponse getAvailableDates(@PathVariable Long concertId) {
        ConcertInfo info = concertService.getAvailableDates(concertId).get(0);  // 항상 하나의 Concert만 리턴됨
        return mapper.toResponse(info);
    }

    @GetMapping("/{concertId}/available-seats")
    @Operation(summary = "예약 가능 좌석 조회", description = "예약 가능한 좌석 목록을 반환합니다.")
    public ConcertResponse getAvailableSeats(@PathVariable Long concertId) {
        ConcertInfo info = concertService.getAvailableSeats(concertId).get(0);
        return mapper.toResponse(info);
    }
}


