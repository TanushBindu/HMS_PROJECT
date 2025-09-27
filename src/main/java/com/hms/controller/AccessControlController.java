package com.hms.controller;

import com.hms.model.AccessControl;
import com.hms.model.Staff;
import com.hms.model.Module;
import com.hms.repository.AccessControlRepository;
import com.hms.repository.ModuleRepository;
import com.hms.repository.StaffRepository;
import com.hms.service.AccessControlService;
import com.hms.service.ModuleService;
import com.hms.service.PatientService;
import com.hms.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/access-control")
public class AccessControlController {

    private AccessControlRepository accessControlRepository;
    private StaffRepository staffRepository;
    private ModuleRepository moduleRepository;

    @Autowired
    private AccessControlService accessControlService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ModuleService moduleService;
    @Autowired
    public AccessControlController(AccessControlService accessControlService,
                                   StaffService staffService) {
        this.accessControlService = accessControlService;
        this.staffService = staffService;
    }

    @GetMapping("/{staffId}")
    public String showAccessControlForm(@PathVariable Long staffId, Model model) {
        Staff staff = staffService.findById(staffId);
        if (staff == null) {
            throw new RuntimeException("Staff not found");
        }

        List<Module> modules = moduleService.findAll();
        Map<Long, Boolean> accessMap = accessControlService.getAccessMapForStaff(staffId);

        model.addAttribute("staff", staff);
        model.addAttribute("modules", modules);
        model.addAttribute("accessMap", accessMap);

        return "access-control-form"; // must exist in templates folder
    }

    // Get current access map for a staff: moduleId -> true/false
    public Map<Long, Boolean> getAccessMapForStaff(Long staffId) {
        List<AccessControl> accessList = accessControlRepository.findByStaffId(staffId);
        Map<Long, Boolean> accessMap = new HashMap<>();
        for (AccessControl ac : accessList) {
            accessMap.put(ac.getModule().getId(), ac.isCanAccess());
        }
        return accessMap;
    }

    // Update access for staff based on selected module IDs
    public void updateStaffAccess(Long staffId, List<Long> moduleIds) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        accessControlRepository.deleteByStaffId(staffId);

        if (moduleIds != null) {
            for (Long moduleId : moduleIds) {
                Module module = moduleRepository.findById(moduleId)
                        .orElseThrow(() -> new RuntimeException("Module not found"));

                AccessControl ac = new AccessControl();
                ac.setStaff(staff);   // use managed entity
                ac.setModule(module); // use managed entity
                ac.setCanAccess(true);

                accessControlRepository.save(ac);
            }
        }
    }



    // List all access controls
    @GetMapping
    public String listAccessControl(Model model) {
        model.addAttribute("accessList", accessControlService.findAll());
        model.addAttribute("staffList", staffService.findAll());
        return "access-control"; // 👈 must match templates/access-control.html
    }

    @GetMapping("/staff")
    public String getAllStaff(Model model) {
        List<Staff> staffList = staffService.findAll();
        System.out.println("Staff list in controller: " + staffList.size());
        model.addAttribute("staffList", staffList);
        return "staff-list"; // HTML page name
    }

//    @GetMapping("/access-control/{staffId}")
//    public String accessControlForm(@PathVariable Long staffId, Model model) {
//        Staff staff = staffService.findById(staffId);
//        List<Module> modules = moduleService.findAll(); // uses com.hms.model.Module
//        Map<Long, Boolean> accessMap = accessControlService.getAccessMapForStaff(staffId);
//
//        model.addAttribute("staff", staff);
//        model.addAttribute("modules", modules);
//        model.addAttribute("accessMap", accessMap);
//
//        return "access-control-form";
//    }

    @PostMapping("/access-control/{staffId}")
    public String saveAccessControl(@PathVariable Long staffId,
                                    @RequestParam(required=false) List<Long> moduleIds) {
        // Save the modules that are checked (moduleIds)
        accessControlService.updateStaffAccess(staffId, moduleIds);
        return "redirect:/staff";
    }

    // Edit form
    @GetMapping("/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AccessControl ac = accessControlService.findById(id);
        if (ac == null) return "redirect:/access-control/list";
        model.addAttribute("ac", ac);
        return "access-control-form";
    }

    // Update
    @PostMapping("/{staffId}")
    public String updateAccessControl(
            @PathVariable Long staffId,
            @RequestParam(value = "moduleIds", required = false) List<Long> moduleIds) {

        accessControlService.updateStaffAccess(staffId, moduleIds);
        return "redirect:/staff"; // back to staff list
    }
}
