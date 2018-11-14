package com.bmstu.testingsystem.domain


import com.bmstu.testingsystem.helper.JpaQuestionConverterJson
import java.util.UUID
import java.sql.Date
import javax.persistence.*


@Entity
@Table(name = "test_data")
data class Exam (
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User,

        val name: String,

        val description: String,

        var createdAt: Date,

        @Convert(converter = JpaQuestionConverterJson::class)
        val questions: List<Question> 

) {


    @GeneratedValue
    @Id
    val id: UUID = UUID.randomUUID()

    var passCount: Int = 0

    @Enumerated(EnumType.STRING)
    var status: ExamStatus = ExamStatus.PENDING

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name="test_id", referencedColumnName="id")
    val results: MutableList<ExamResult> = arrayListOf()
}



