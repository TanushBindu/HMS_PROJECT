package com.hms.controller;

import com.hms.model.BiomedicalWaste;
import com.hms.service.BiomedicalWasteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/biomedical-waste")
public class BiomedicalWasteController {

    private final BiomedicalWasteService service;

    public BiomedicalWasteController(BiomedicalWasteService service) {
        this.service = service;
    }

    @GetMapping
    public String listWaste(Model model) {
        List<BiomedicalWaste> list = service.getAllWaste();
        model.addAttribute("biomedicalWasteList", list);
        return "invoice"; // Use your existing invoice.html
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("waste", new BiomedicalWaste());
        return "waste_form"; // Optional separate modal form if needed
    }

    @PostMapping("/save")
    public String saveWaste(@ModelAttribute BiomedicalWaste waste) {
        service.saveWaste(waste);
        return "redirect:/invoice";
    }

    @GetMapping("/edit/{id}")
    public String editWaste(@PathVariable Long id, Model model) {
        BiomedicalWaste waste = service.getById(id);
        model.addAttribute("waste", waste);
        return "waste_form"; // Optional edit form
    }

    @GetMapping("/delete/{id}")
    public String deleteWaste(@PathVariable Long id) {
        service.deleteWaste(id);
        return "redirect:/biomedical-waste";
    }
}
