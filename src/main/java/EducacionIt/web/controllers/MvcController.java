package EducacionIt.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MvcController {

    @GetMapping("/home")
    public String  home(Model model){
        String saludo  = "Hola Universo.";
        model.addAttribute("saludo", saludo);
        return "index";
    }

}
