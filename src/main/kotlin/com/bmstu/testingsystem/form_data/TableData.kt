package com.bmstu.testingsystem.form_data

import com.bmstu.testingsystem.domain.User
import java.util.*

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