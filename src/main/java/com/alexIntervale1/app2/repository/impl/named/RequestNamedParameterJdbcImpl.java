package com.alexIntervale1.app2.repository.impl.named;

import com.alexIntervale1.app2.repository.RequestJdbcRepo;
import com.alexIntervale1.app2.model.RequestMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
@RequiredArgsConstructor
@Repository
@Slf4j
public class RequestNamedParameterJdbcImpl implements RequestJdbcRepo {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public RequestMessage save(RequestMessage requestMessage) {
        String saveSql = "INSERT INTO request (personal_number, name, surname, progress_control,correlationid)" +
                " values (:personal_number, :name, :surname, :progress_control, :correlationid)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("personal_number", requestMessage.getPersonalNumber())
                .addValue("name", requestMessage.getName())
                .addValue("surname", requestMessage.getSurname())
                .addValue("progress_control", requestMessage.getProgressControl())
                .addValue("correlationid", requestMessage.getCorrelationID());

        int saveRequest = jdbcTemplate.update(saveSql, paramSource);
        RequestMessage requestMessageSave = null;
        if (saveRequest == 1) {
            requestMessageSave = findByPersonalNumber(requestMessage.getPersonalNumber());
        }
        return requestMessageSave;
    }

    @Override
    public RequestMessage update(RequestMessage requestMessage) {
        String updateSql = "UPDATE request SET personal_number= :personal_number, name= :name, surname= :surname, " +
                "progress_control= :progress_control, correlationid= :correlationid WHERE id= :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("personal_number", requestMessage.getPersonalNumber())
                .addValue("name", requestMessage.getName())
                .addValue("surname", requestMessage.getSurname())
                .addValue("progress_control", requestMessage.getProgressControl())
                .addValue("correlationid", requestMessage.getCorrelationID())
                .addValue("id", requestMessage.getId());
        int updateRequest = jdbcTemplate.update(updateSql, parameterSource);
        RequestMessage requestMessageUpdate = null;
        if (updateRequest == 1) {
            requestMessageUpdate = findById(requestMessage.getId());
        }
        return requestMessageUpdate;
    }

    @Override
    public void deleteById(Long id) {
        String delSql = "delete from request where id = :id";
        SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(delSql, paramSource);
    }

    @Override
    public List<RequestMessage> findAll() {
        String findAllSql = "select * from request";
        return jdbcTemplate.query(findAllSql, new RequestRowMapper());
    }

    @Override
    public RequestMessage findById(Long id) {
        String findIdSql = "select * from request where id = :id";
        SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(findIdSql, paramSource, new RequestRowMapper());
    }

    @Override
    public int countByProgressControlIsLike(String str) {
        int resultValueOfNumberOfRecords = 0;
        String sqlCount = "select count(*) from request where progress_control = :progress_control";
        SqlParameterSource paramSource = new MapSqlParameterSource("progress_control", str);
        resultValueOfNumberOfRecords = jdbcTemplate.queryForObject(sqlCount, paramSource, Integer.class);
        return resultValueOfNumberOfRecords;
    }

    @Override
    public RequestMessage findFirstByProgressControlIsLike(String str) {
        String findByProgressControl = "select * from request where progress_control = :progress_control limit 1";
        SqlParameterSource paramSource = new MapSqlParameterSource("progress_control", str);
        if (countByProgressControlIsLike(str) > 0) {
            try {
                return jdbcTemplate.queryForObject(findByProgressControl, paramSource, new RequestRowMapper());
            } catch (DataAccessException e) {
                log.trace("expected: " + e.getMessage());
            }
        }
        return null;
    }

    public RequestMessage findByPersonalNumber(Long personal_number) {
        String findPersonalNumberSql = "select * from request where personal_number = :personal_number";
        SqlParameterSource paramSource = new MapSqlParameterSource("personal_number", personal_number);
        return jdbcTemplate.queryForObject(findPersonalNumberSql, paramSource, new RequestRowMapper());
    }

    static class RequestRowMapper implements RowMapper<RequestMessage> {
        @Override
        public RequestMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
            return RequestMessage.builder()
                    .id(rs.getLong("id"))
                    .personalNumber(rs.getLong("personal_number"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .progressControl(rs.getString("progress_control"))
                    .correlationID(rs.getString("correlationid"))
                    .build();
        }
    }

}