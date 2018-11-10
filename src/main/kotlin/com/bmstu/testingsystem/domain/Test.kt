package com.bmstu.testingsystem.domain


import com.bmstu.testingsystem.helper.JpaQuestionConverterJson
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "test_data")
data class Test (

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User,

        val name: String,

        val description: String,

        val createdAt: Date,

        @Convert(converter = JpaQuestionConverterJson::class)
        val questions: List<Question>

) {
    @GeneratedValue
    @Id
    val id: UUID = UUID.randomUUID()

    val passCount: Int = 0

    @Enumerated(EnumType.STRING)
    var status: TestStatus = TestStatus.PENDING

//    @OneToMany(
//            fetch = FetchType.LAZY,
//            cascade = [CascadeType.ALL],
//            mappedBy = "test")
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="test_id", referencedColumnName="id")
    val results: MutableList<TestResult> = arrayListOf()
}



