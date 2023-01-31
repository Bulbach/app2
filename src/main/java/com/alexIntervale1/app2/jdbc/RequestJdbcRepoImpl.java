package com.alexIntervale1.app2.jdbc;

import com.alexIntervale1.app2.model.RequestMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
@RequiredArgsConstructor
@Repository
//@Primary
@Slf4j
public class RequestJdbcRepoImpl implements RequestJdbcRepo {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(RequestMessage requestMessage) {
        String saveSql = "INSERT INTO request (personal_number, name, surname, progress_control,correlationid) VALUES(?,?,?,?,?)";
        return jdbcTemplate.update(saveSql
                , requestMessage.getPersonalNumber()
                , requestMessage.getName()
                , requestMessage.getSurname()
                , requestMessage.getProgressControl()
                , requestMessage.getCorrelationID()
        );
    }

    @Override
    public int update(RequestMessage requestMessage) {
        String updateSql = "UPDATE request SET personal_number=?, name=?, surname=?, progress_control=?, correlationid=? WHERE id=?";
        return jdbcTemplate.update(updateSql
                , requestMessage.getPersonalNumber()
                , requestMessage.getName()
                , requestMessage.getSurname()
                , requestMessage.getProgressControl()
                , requestMessage.getCorrelationID()
                , requestMessage.getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        String delSql = "delete from request where id = ?";
        jdbcTemplate.update(delSql, id);
    }

    @Override
    public List<RequestMessage> findAll() {
        String findAllSql = "select * from request";
        return jdbcTemplate.query(findAllSql, new RequestRowMapper());
    }

    @Override
    public RequestMessage findById(Long id) {
        String findIdSql = "select * from request where id = ?";
        return jdbcTemplate.queryForObject(findIdSql, new RequestRowMapper());
    }

    @Override
    public int countByProgressControlIsLike(String str) {
        String sqlCount = "select count(*) from request where progress_control = ?";

        return jdbcTemplate.queryForObject(sqlCount, Integer.class, str);
    }

    @Override
    public RequestMessage findFirstByProgressControlIsLike(String str) {
        String findByProgressControl = "select * from request where progress_control = ? limit 1";
        if (countByProgressControlIsLike(str) > 0) {
        try {
            return jdbcTemplate.queryForObject(findByProgressControl, new RequestRowMapper(), str);
        } catch (DataAccessException e) {
            log.trace("expected: " + e.getMessage());
        }
        }
        return null;
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
