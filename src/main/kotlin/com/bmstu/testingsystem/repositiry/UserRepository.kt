package com.bmstu.testingsystem.repositiry

import com.bmstu.testingsystem.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(name: String): User?
}