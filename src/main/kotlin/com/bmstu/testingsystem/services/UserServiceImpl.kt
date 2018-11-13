package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.controller.EditProfile
import com.bmstu.testingsystem.controller.SignUp
import com.bmstu.testingsystem.domain.Person
import com.bmstu.testingsystem.domain.User
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

    override fun updateUser(user: User, newUserData: EditProfile.UserData): Boolean {
        val findByUsername = userRepository.findByUsername(newUserData.username)
        if (findByUsername != null && findByUsername.id != user.id)
            return false

        if (!user.username.equals(newUserData.username))
            user.username = newUserData.username

        if (!user.email.equals(newUserData.email))
            user.email = newUserData.email

        if (!user.password.equals(newUserData.password))
            user.password = newUserData.password

        val newPerson = Person(newUserData.firstName, newUserData.lastName, newUserData.birthday, newUserData.avatar)
        val oldPerson = user.person

        if (!oldPerson.firstName.equals(newPerson.firstName))
            oldPerson.firstName = newPerson.firstName

        if (!oldPerson.lastName.equals(newPerson.lastName))
            oldPerson.lastName = newPerson.lastName

        if (!oldPerson.avatar.equals(newPerson.avatar))
            oldPerson.avatar = newPerson.avatar

        if (oldPerson.birthday == null || !oldPerson.birthday!!.equals(newPerson.birthday))
            oldPerson.birthday = newPerson.birthday

        userRepository.save(user)
        return true
    }
}
