package com.bmstu.testingsystem.form_data

data class Button (
        var type: String = "",
        var text: String = "",
        var actionUrl: String = ""
)

fun getApproveReject() : List<Button> {
    val buttons: MutableList<Button> = arrayListOf()
    buttons.add(Button("btn-success", "Принять", "admin/approve"))
    buttons.add(Button("btn-danger", "Отклонить", "admin/reject"))
    return buttons
}

fun getPassStatisticDelete() : List<Button> {
    val buttons: MutableList<Button> = arrayListOf()
    buttons.add(Button("btn-success", "Пройти", "exam_page"))
    buttons.add(Button("btn-primary", "Результаты", "my_exams/results"))
    buttons.add(Button("btn-danger", "Удалить", "my_exams/delete"))
    return buttons
}