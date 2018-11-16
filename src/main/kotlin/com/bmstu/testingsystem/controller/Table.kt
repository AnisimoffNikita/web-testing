package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.services.ExamServiceImpl
import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import java.sql.Date
import java.text.DateFormat
import java.util.*

@Controller
class Table {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @Autowired
    private lateinit var testService: ExamServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                java.util.Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("/my_passed_exams")
    fun getMyPassedExams(model: Model, authentication: Authentication?): String {
        if (authentication == null)
            return "redirect:/sign_in"
        model.addAttribute("title", "Пройденные тесты")


        var table = TableData()
        table.headers.add("h1")
        table.headers.add("h2")

        var row1 = Row()
        row1.cells.add(Cell("row1", "my_exams"))
        row1.cells.add(Cell("row2", "my_exams"))

        var row2 = Row()
        row2.cells.add(Cell(Date(System.currentTimeMillis())))
        row2.cells.add(Cell(Date(System.currentTimeMillis())))

        table.rows.add(row1)
        table.rows.add(row2)

        model.addAttribute("table", table)

        return "table"
    }
}


data class TableData (
        var headers: MutableList<String> = arrayListOf(),
        var rows: MutableList<Row> = arrayListOf()
)

data class Row (
    var cells: MutableList<Cell> = arrayListOf()
)

data class Cell (
        var data: Any = "",
        var link: String = ""
)