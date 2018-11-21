package com.bmstu.testingsystem.form_data

import com.bmstu.testingsystem.domain.User
import java.util.*

data class UserData (
        var username: String? = null,
        var email: String? = null,
        var password: String? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var birthday: Date? = null
) {
    constructor(user: User): this(user.username, user.email, user.password,
            user.person.firstName, user.person.lastName, user.person.birthday)
}