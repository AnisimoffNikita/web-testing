package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.controller.SignUp
import com.bmstu.testingsystem.domain.Person
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.form_data.UserData
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("userService")
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    override fun registerUser(registrationData: SignUp.RegistrationData): User? {
        val user = userRepository.findByUsername(registrationData.username)
        if (user != null) {
            return null
        }

        val newUser = User(registrationData.username, registrationData.email, registrationData.password)
        userRepository.save(newUser)
        return newUser
    }

    override fun updateUser(user: User, newUserData: UserData): Boolean {
        newUserData.username?: throw IllegalStateException()
        newUserData.email?: throw IllegalStateException()
        newUserData.password?: throw IllegalStateException()

        val findByUsername = userRepository.findByUsername(newUserData.username!!)
        if (findByUsername != null && findByUsername.id != user.id)
            return false

        if (user.username != newUserData.username)
            user.username = newUserData.username!!

        if (user.email != newUserData.email)
            user.email = newUserData.email!!

        if (user.password != newUserData.password)
            user.password = newUserData.password!!

        val newPerson = Person(newUserData.firstName, newUserData.lastName, newUserData.birthday, newUserData.avatar)
        val oldPerson = user.person

        if (oldPerson.firstName != newPerson.firstName)
            oldPerson.firstName = newPerson.firstName

        if (oldPerson.lastName != newPerson.lastName)
            oldPerson.lastName = newPerson.lastName

        if (oldPerson.avatar != newPerson.avatar)
            oldPerson.avatar = newPerson.avatar

        if (oldPerson.birthday == null || oldPerson.birthday != newPerson.birthday)
            oldPerson.birthday = newPerson.birthday

        userRepository.save(user)
        return true
    }

    override fun updateAvatar(user: User, path: String) {
        user.person.avatar = path
        userRepository.save(user)
    }

    override fun getAvatar(user: User): String {
        return "/avatar/" + (user.person.avatar ?: "avatar.png")
    }
}
