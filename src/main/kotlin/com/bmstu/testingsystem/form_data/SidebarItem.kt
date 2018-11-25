package com.bmstu.testingsystem.form_data

data class Sidebar (
        var activeItem: Int = 0,
        var items: List<SidebarItem> = emptyList<SidebarItem>()
)

data class SidebarItem (
        var text: String = "",
        var link: String = ""
)

fun getCreatedPassed(active: Int) : Sidebar {
    val sidebarItems: MutableList<SidebarItem> = arrayListOf()
    sidebarItems.add(SidebarItem("Созданные", "my_exams"))
    sidebarItems.add(SidebarItem("Пройденные", "my_passed_exams"))
    return Sidebar(active, sidebarItems)
}

fun getUsersExamsNewExams(active: Int) : Sidebar {
    val sidebarItems: MutableList<SidebarItem> = arrayListOf()
    sidebarItems.add(SidebarItem( "Все пользователи", "admin/all_users"))
    sidebarItems.add(SidebarItem( "Все тесты", "admin/all_exams"))
    sidebarItems.add(SidebarItem("Новые тесты", "admin/new_exams"))
    return Sidebar(active, sidebarItems)
}