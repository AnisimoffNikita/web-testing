package com.bmstu.testingsystem.exception

import com.bmstu.testingsystem.form_data.Error
import com.bmstu.testingsystem.form_data.getErrorInfo

class NoPermissionException : AppException() {
    override val message: String?
        get() = getErrorInfo(Error.noPermission)
}