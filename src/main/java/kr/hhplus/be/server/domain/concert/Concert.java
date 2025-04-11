package kr.hhplus.be.server.domain.concert;

import kr.hhplus.be.server.domain.schedule.Schedule;
import lombok.Getter;

import java.util.List;

@Getter
public class Concert {
    private final Long id;
    private final String title;
    private final List<Schedule> schedules;

    public Concert(Long id, String title, List<Schedule> schedules) {
        this.id = id;
        this.title = title;
        this.schedules = schedules;

    }
}

