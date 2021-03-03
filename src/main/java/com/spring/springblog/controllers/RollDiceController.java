package com.spring.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ThreadLocalRandom;

@Controller
public class RollDiceController {
    //TODO: Create a page at /roll-dice that asks the user to guess
    // a number. There should be links on this page for 1 through 6
    // that should make a GET request to /roll-dice/n where n is
    // the number guessed. This page should display a random
    // number (the dice roll), the user's guess and a message
    // based on whether or not they guessed the correct number.

    @GetMapping("/roll-dice")
    public String rollDiceView(Model model) {
        model.addAttribute("title", "Roll Dice");
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{n}")
    public String rollDiceN(@PathVariable int n, Model model) {
        String message;

        int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);

        if(n == randomNum) {
            message = "Hooray! You guessed correctly!";
        } else {
            message = "Sorry! Try again!";
        }

        model.addAttribute("randomNumber", randomNum);
        model.addAttribute("n", n);
        model.addAttribute("message", message);
        model.addAttribute("title", "Roll Results");

        return "roll-results";
    }
}
