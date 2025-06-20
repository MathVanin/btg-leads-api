package com.btg.leadsapi.controller;


import com.btg.leadsapi.dto.LeadsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Controller
public class WebController {
    private final SpringTemplateEngine templateEngine;

    public WebController(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping("/form")
    public String showLeadForm(Model model) {
        model.addAttribute("lead", new LeadsDto());
        return "leadform";
    }
}
