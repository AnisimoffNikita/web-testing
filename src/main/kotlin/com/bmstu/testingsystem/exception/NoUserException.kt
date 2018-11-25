package com.bmstu.testingsystem.exception

import com.bmstu.testingsystem.form_data.Error
import com.bmstu.testingsystem.form_data.getErrorInfo

class NoUserException : AppException() {
    override val message: String?
        get() = getErrorInfo(Error.noUser)
}