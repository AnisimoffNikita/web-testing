package com.bmstu.testingsystem.domain

import java.util.*
import javax.persistence.*
import javax.persistence.JoinColumn
import javax.persistence.GeneratedValue




@Entity
@Table(name = "user_data")
data class User (

        var username: String,

        var email: String,

        var password: String

) {
    @GeneratedValue
    @Id
    val id: UUID = UUID.randomUUID()


    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="person_id")
    var person: Person = Person()


//    @OneToMany(
//            fetch = FetchType.LAZY,
//            cascade = [CascadeType.ALL],
//            mappedBy = "user")
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="user_id", referencedColumnName="id")
    val tests: MutableList<Test> = arrayListOf()

//    @OneToMany(
//            fetch = FetchType.LAZY,
//            cascade = [CascadeType.ALL],
//            mappedBy = "user")
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="user_id", referencedColumnName="id")
    val results: MutableList<TestResult> = arrayListOf()

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.USER

}

