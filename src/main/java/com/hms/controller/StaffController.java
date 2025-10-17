package com.hms.controller;

import com.hms.model.Staff;
import com.hms.repository.StaffRepository;
import com.hms.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;
    private StaffRepository staffRepository;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String listStaff(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Staff> staffList = (keyword != null && !keyword.isEmpty())
                ? staffService.searchStaff(keyword)
                : staffService.getAllStaff();
        model.addAttribute("staffList", staffList);
        model.addAttribute("newStaff", new Staff());
        model.addAttribute("editStaff", new Staff());
        return "staff";
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Staff getStaff(@PathVariable Long id) {
        return staffService.getStaffById(id);
    }


    @PostMapping("/save")
    public String saveStaff(@ModelAttribute Staff staff) {
        // ✅ Use service — do NOT use repository directly
        Staff existing = staffService.getByUsername(staff.getUsername());
        if (existing != null) {
            // handle duplicate username logic
            return "redirect:/staff/create?error=exists";
        }

        staffService.saveStaff(staff);
        return "redirect:/staff/list";
    }

    @PostMapping("/update")
    public String updateStaff(@ModelAttribute("editStaff") Staff staff) {
        staffService.saveStaff(staff);
        return "redirect:/staff";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/staff";
    }
}
