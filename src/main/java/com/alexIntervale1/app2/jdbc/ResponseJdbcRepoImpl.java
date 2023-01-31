package com.alexIntervale1.app2.jdbc;

import com.alexIntervale1.app2.model.ResponseMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@Repository
//@Primary
public class ResponseJdbcRepoImpl implements ResponseJdbcRepo {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(ResponseMessage responseMessage) {
        String saveSql = "INSERT INTO response " +
                "(personal_number, accrual_amount, payable_amount, ordinance_number, date_of_the_decree, code_article, correlationid) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(saveSql
                , responseMessage.getPersonalNumber()
                , responseMessage.getAccrualAmount()
                , responseMessage.getPayableAmount()
                , responseMessage.getOrdinanceNumber()
                , responseMessage.getDateOfTheDecree()
                , responseMessage.getCodeArticle()
                , responseMessage.getCorrelationID()
        );
    }

    @Override
    public int update(ResponseMessage responseMessage) {
        String updateSql = "UPDATE response SET " +
                "personal_number = ?, accrual_amount = ?, payable_amount = ?, ordinance_number = ?, date_of_the_decree = ?" +
                ", code_article = ?, correlationid = ? WHERE id = ?";
        return jdbcTemplate.update(updateSql
                , responseMessage.getPersonalNumber()
                , responseMessage.getAccrualAmount()
                , responseMessage.getPayableAmount()
                , responseMessage.getOrdinanceNumber()
                , responseMessage.getDateOfTheDecree()
                , responseMessage.getCodeArticle()
                , responseMessage.getCorrelationID()
                , responseMessage.getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        String delSql = "delete from response where id = ?";
        jdbcTemplate.update(delSql, id);
    }

    @Override
    public List<ResponseMessage> findAll() {
        String findAllSql = "select * from response";
        return jdbcTemplate.query(findAllSql, new ResponseRowMapper());
    }

    @Override
    public ResponseMessage findById(Long id) {
        String findIdSql = "select * from response where id = ?";
        return jdbcTemplate.queryForObject(findIdSql, new ResponseRowMapper());
    }


    @Override
    public ResponseMessage findByPersonalNumber(Long aLong) {
        String findPersonalNumberSql = "select * from response where personal_number = ?";
        return jdbcTemplate.queryForObject(findPersonalNumberSql, new ResponseRowMapper(), aLong);
    }

    class ResponseRowMapper implements RowMapper<ResponseMessage> {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = new Date();
        Instant instant = date.toInstant();
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
