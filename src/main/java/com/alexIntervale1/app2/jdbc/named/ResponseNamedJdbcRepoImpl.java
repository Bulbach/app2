package com.alexIntervale1.app2.jdbc.named;

import com.alexIntervale1.app2.model.ResponseMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ResponseNamedJdbcRepoImpl implements ResponseNamedJdbcRepo {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int save(ResponseMessage responseMessage) {
        String saveSql = "INSERT INTO response " +
                "(personal_number, accrual_amount, payable_amount, ordinance_number, date_of_the_decree, code_article, correlationid) " +
                "VALUES(:personal_number, :accrual_amount, :payable_amount, :ordinance_number, :date_of_the_decree, :code_article, :correlationid)";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("personal_number", responseMessage.getPersonalNumber())
                .addValue("accrual_amount", responseMessage.getAccrualAmount())
                .addValue("payable_amount", responseMessage.getPayableAmount())
                .addValue("ordinance_number", responseMessage.getOrdinanceNumber())
                .addValue("date_of_the_decree", responseMessage.getDateOfTheDecree())
                .addValue("code_article", responseMessage.getCodeArticle())
                .addValue("correlationid", responseMessage.getCorrelationID());
        return jdbcTemplate.update(saveSql, paramSource);
    }

    public int update(ResponseMessage responseMessage) {
        String updateSql = "UPDATE response SET " +
                "personal_number = :personal_number, accrual_amount = :accrual_amount, payable_amount = :payable_amount, " +
                "ordinance_number = :ordinance_number, date_of_the_decree = :date_of_the_decree, code_article = :code_article" +
                ", correlationid = :correlationid WHERE id = :id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("personal_number", responseMessage.getPersonalNumber())
                .addValue("accrual_amount", responseMessage.getAccrualAmount())
                .addValue("payable_amount", responseMessage.getPayableAmount())
                .addValue("ordinance_number", responseMessage.getOrdinanceNumber())
                .addValue("date_of_the_decree", responseMessage.getDateOfTheDecree())
                .addValue("code_article", responseMessage.getCodeArticle())
                .addValue("correlationid", responseMessage.getCorrelationID())
                .addValue("id", responseMessage.getId());
        return jdbcTemplate.update(updateSql, paramSource);
    }

    @Override
    public void deleteById(Long id) {
        String delSql = "delete from response where id = :id";
        SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(delSql, paramSource);
    }

    @Override
    public List<ResponseMessage> findAll() {
        String findAllSql = "select * from response";
        return jdbcTemplate.query(findAllSql, new ResponseRowMapper());
    }

    @Override
    public ResponseMessage findById(Long id) {
        String findIdSql = "select * from response where id = :id";
        SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(findIdSql, paramSource, new ResponseRowMapper());
    }

    public ResponseMessage findByPersonalNumber(Long personal_number) {
        String findPersonalNumberSql = "select * from response where personal_number = :personal_number";
        SqlParameterSource paramSource = new MapSqlParameterSource("personal_number", personal_number);
        return jdbcTemplate.queryForObject(findPersonalNumberSql,paramSource ,new ResponseRowMapper());
    }

    static class ResponseRowMapper implements RowMapper<ResponseMessage> {
        @Override
        public ResponseMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ResponseMessage.builder()
                    .id(rs.getLong("id"))
                    .personalNumber(rs.getLong("personal_number"))
                    .accrualAmount(rs.getDouble("accrual_amount"))
                    .payableAmount(rs.getDouble("payable_amount"))
                    .ordinanceNumber(rs.getLong("ordinance_number"))
                    .dateOfTheDecree(rs.getDate("date_of_the_decree").toLocalDate())
                    .codeArticle(rs.getDouble("code_article"))
                    .correlationID(rs.getString("correlationid"))
                    .build();
        }
    }
}
