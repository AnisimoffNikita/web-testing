package com.bmstu.testingsystem.form_data

enum class Error  {
    noExam,
    deletedExam,
    noUser,
    noPermission
}

fun getErrorInfo(error: Error) : String =
    when(error) {
        Error.noExam -> "Такого теста не существует"
        Error.noUser -> "Такого пользователя не существует"
        Error.deletedExam -> "Тест был удалён"
        Error.noPermission -> "Вы не можете просматривать эту страницу"
    }