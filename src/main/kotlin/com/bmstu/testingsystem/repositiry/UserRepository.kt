package com.bmstu.testingsystem.repositiry

import com.bmstu.testingsystem.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
interface UserRepository : CrudRepository<User, UUID> {
    fun findByUsername(name: String): User?
}