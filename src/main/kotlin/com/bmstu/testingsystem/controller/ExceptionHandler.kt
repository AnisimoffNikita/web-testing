package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.exception.AppException
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [(AppException::class)])
    fun handlerAnnotationException(exception: AppException, model: Model) : String {
        model.addAttribute("info", exception.message)
        return "error_page"
    }

}
