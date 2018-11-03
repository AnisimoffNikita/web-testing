package com.bmstu.testing_system.repositiry

import com.bmstu.testing_system.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByLogin(name: String): User?
}