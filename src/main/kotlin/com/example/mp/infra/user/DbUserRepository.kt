package com.example.mp.infra.user

import com.example.mp.domain.user.Id
import com.example.mp.domain.user.Name
import com.example.mp.domain.user.User
import com.example.mp.domain.user.UserNotFoundException
import com.example.mp.domain.user.UserRepository
import com.example.mp.infra.user.UserRowMapper.Fields.ID
import com.example.mp.infra.user.UserRowMapper.Fields.NAME
import com.example.mp.infra.user.UserRowMapper.Fields.TABLE_NAME
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.UUID

@Repository
class DbUserRepository(private val jdbcTemplate: JdbcTemplate) : UserRepository {

    private val rowMapper = UserRowMapper()

    override fun add(user: User) {
        TODO("Not yet implemented")
    }

    override fun get(id: Id): User = jdbcTemplate
            .query(FIND_BY_ID, rowMapper, id.value)
            .firstOrNull()
            ?: throw UserNotFoundException(id)


    companion object {

        private const val FIND_BY_ID = "SELECT * FROM $TABLE_NAME WHERE $ID = ?"
    }
}

class UserRowMapper : RowMapper<User> {

    override fun mapRow(resultSet: ResultSet, row: Int): User = User(
            id = Id(resultSet.getObject(ID, UUID::class.java)),
            name = Name(resultSet.getString(NAME))
    )

    object Fields {

        const val TABLE_NAME = "user"

        const val ID = "id"
        const val NAME = "name"
    }
}
