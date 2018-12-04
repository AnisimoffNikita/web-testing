package com.bmstu.testingsystem.exception

import com.bmstu.testingsystem.form_data.Error
import com.bmstu.testingsystem.form_data.getErrorInfo

class BadUserDataException : AppException() {
    override val message: String = "bad data"
}