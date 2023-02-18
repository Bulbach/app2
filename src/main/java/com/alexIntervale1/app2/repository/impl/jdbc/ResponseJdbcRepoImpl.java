package com.alexIntervale1.app2.repository.impl.jdbc;

import com.alexIntervale1.app2.model.ResponseMessage;
import com.alexIntervale1.app2.repository.ResponseJdbcRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Data
@RequiredArgsConstructor
@Repository
public class ResponseJdbcRepoImpl implements ResponseJdbcRepo {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ResponseMessage save(ResponseMessage responseMessage) {
        String saveSql = "INSERT INTO response " +
                "(personal_number, accrual_amount, payable_amount, ordinance_number, date_of_the_decree, code_article, correlationid) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        int saveVerification = jdbcTemplate.update(saveSql
                , responseMessage.getPersonalNumber()
                , responseMessage.getAccrualAmount()
                , responseMessage.getPayableAmount()
                , responseMessage.getOrdinanceNumber()
                , responseMessage.getDateOfTheDecree()
                , responseMessage.getCodeArticle()
                , responseMessage.getCorrelationID());

        ResponseMessage responseMessageSave = null;
        if (saveVerification == 1) {
            responseMessageSave = findByPersonalNumber(responseMessage.getPersonalNumber());
        }
        return responseMessageSave;
    }

    @Override
    public ResponseMessage update(ResponseMessage responseMessage) {
        String updateSql = "UPDATE response SET " +
                "personal_number = ?, accrual_amount = ?, payable_amount = ?, ordinance_number = ?, date_of_the_decree = ?" +
                ", code_article = ?, correlationid = ? WHERE id = ?";
        int updateVerification = jdbcTemplate.update(updateSql
                , responseMessage.getPersonalNumber()
                , responseMessage.getAccrualAmount()
                , responseMessage.getPayableAmount()
                , responseMessage.getOrdinanceNumber()
                , responseMessage.getDateOfTheDecree()
                , responseMessage.getCodeArticle()
                , responseMessage.getCorrelationID()
                , responseMessage.getId());
        ResponseMessage responseMessageSave = null;
        if (updateVerification == 1) {
            responseMessageSave = findById(responseMessage.getId());
        }
        return responseMessageSave;
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
        return jdbcTemplate.queryForObject(findIdSql, new ResponseRowMapper(),id);
    }


    @Override
    public ResponseMessage findByPersonalNumber(Long personal_number) {
        String findPersonalNumberSql = "select * from response where personal_number = ?";
        return jdbcTemplate.queryForObject(findPersonalNumberSql, new ResponseRowMapper(), personal_number);
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
