package com.example.mp.infra.user.balance

import com.example.mp.domain.user.Id
import com.example.mp.domain.user.balance.Amount
import com.example.mp.domain.user.balance.Balance
import com.example.mp.domain.user.balance.Currency
import com.example.mp.domain.user.balance.MonetaryAmount
import com.example.mp.domain.user.balance.UserBalanceRepository
import com.example.mp.infra.user.balance.UserBalanceRowMapper.Fields.AMOUNT
import com.example.mp.infra.user.balance.UserBalanceRowMapper.Fields.CURRENCY
import com.example.mp.infra.user.balance.UserBalanceRowMapper.Fields.TABLE_NAME
import com.example.mp.infra.user.balance.UserBalanceRowMapper.Fields.USER_ID
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.UUID

@Repository
class DbUserBalanceRepository(private val jdbcTemplate: JdbcTemplate): UserBalanceRepository {

    private val rowMapper = UserBalanceRowMapper()

    override fun add(balance: Balance) {
        TODO("Not yet implemented")
    }

    override fun get(userId: Id): Balance = jdbcTemplate
            .query(FIND_BY_USER_ID, rowMapper, userId.value)
            .first()

    companion object {
        private const val FIND_BY_USER_ID ="SELECT * FROM $TABLE_NAME WHERE $USER_ID = ?"
    }
}

class UserBalanceRowMapper : RowMapper<Balance> {

    override fun mapRow(resultSet: ResultSet, row: Int): Balance = Balance(
            userId = Id(resultSet.getObject(USER_ID, UUID::class.java)),
            monetaryAmount = MonetaryAmount(
                    amount = Amount.from(resultSet.getString(AMOUNT)),
                    currency = Currency.from(resultSet.getString(CURRENCY))
            )
    )

    object Fields {
        const val TABLE_NAME = "user_balance"

        const val USER_ID = "user_id"
        const val AMOUNT = "amount"
        const val CURRENCY = "currency"
    }
}
