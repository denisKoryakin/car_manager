package ru.koryakin.carmanager.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.koryakin.carmanager.controller.DTO.Statistic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static java.util.Collections.emptyMap;

@Component
@RequiredArgsConstructor
public class StatisticRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Statistic getTableStatistic() {
        Statistic statistics = namedParameterJdbcTemplate.queryForObject("select count(1) as recCount, max(created) as lastDate, min(created) as firstDate from car", emptyMap(), new RowMapper<Statistic>() {
            @Override
            public Statistic mapRow(ResultSet rs, int rowNum) throws SQLException {
                long count = rs.getInt("recCount");
                if(count == 0) return new Statistic(null, null, 0);
                LocalDateTime first = rs.getTimestamp("firstDate").toLocalDateTime();
                LocalDateTime last = rs.getTimestamp("lastDate").toLocalDateTime();
                return new Statistic(first, last, count);
            }
        });
        return statistics;
    }
}
