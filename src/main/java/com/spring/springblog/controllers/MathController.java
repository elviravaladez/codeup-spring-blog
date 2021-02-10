package com.spring.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {
    //TODO: This controller should listen for requests for several
    // routes that correspond to basic arithmetic operations
    // and produce the result of the arithmetic.

    //TODO: /add/3/and/4
    @GetMapping("/add/{number}/and/{numberTwo}")
    @ResponseBody
    public int addNumbers(@PathVariable int number, @PathVariable int numberTwo) {
        return number + numberTwo;
    }

    //TODO: /subtract/3/from/10
    @GetMapping("/subtract/{number}/from/{numberTwo}")
    @ResponseBody
    public int subtractNumbers(@PathVariable int number, @PathVariable int numberTwo) {
        return numberTwo - number;
    }

    //TODO: /multiply/4/and/5
    @GetMapping("/multiply/{number}/and/{numberTwo}")
    @ResponseBody
    public int multiplyNumbers(@PathVariable int number, @PathVariable int numberTwo) {
        return number * numberTwo;
    }

    //TODO: /divide/6/by/3
    @GetMapping("/divide/{number}/by/{numberTwo}")
    @ResponseBody
    public int divideNumbers(@PathVariable int number, @PathVariable int numberTwo) {
        return number / numberTwo;
    }
}