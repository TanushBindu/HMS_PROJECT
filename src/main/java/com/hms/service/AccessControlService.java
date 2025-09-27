package com.hms.service;

import com.hms.repository.AccessControlRepository;
import com.hms.model.AccessControl;
import com.hms.model.Staff;
import com.hms.model.Module;
import com.hms.repository.ModuleRepository;
import com.hms.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class AccessControlService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AccessControlRepository accessControlRepository;

    public AccessControlService(AccessControlRepository accessControlRepository, ModuleRepository moduleRepository) {
        this.accessControlRepository = accessControlRepository;
        this.moduleRepository = moduleRepository;
    }


    public void updateStaffAccess(Long staffId, List<Long> moduleIds) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        // Delete old access
        accessControlRepository.deleteByStaffId(staffId);

        if (moduleIds != null) {
            for (Long moduleId : moduleIds) {
                // Fetch module from DB (managed entity)
                Module module = moduleRepository.findById(moduleId)
                        .orElseThrow(() -> new RuntimeException("Module not found"));

                AccessControl ac = new AccessControl();
                ac.setStaff(staff);    // managed staff
                ac.setModule(module);  // managed module
                ac.setCanAccess(true);

                accessControlRepository.save(ac);
            }
        }
    }


    public AccessControl save(AccessControl ac) { return accessControlRepository.save(ac); }

    public AccessControl update(AccessControl ac) { return accessControlRepository.save(ac); }

    public AccessControl findById(Long id) {
        Optional<AccessControl> optional = accessControlRepository.findById(id);
        return optional.orElse(null);
    }

    // Get current access map for staff
    public Map<Long, Boolean> getAccessMapForStaff(Long staffId) {
        List<AccessControl> list = accessControlRepository.findByStaffId(staffId);
        Map<Long, Boolean> map = new HashMap<>();
        for (AccessControl ac : list) {
            map.put(ac.getModule().getId(), ac.isCanAccess());
        }
        return map;
    }

    // Update staff access
//    public void updateStaffAccess(Long staffId, List<Long> moduleIds) {
//        accessControlRepository.deleteByStaffId(staffId);
//        if (moduleIds != null) {
//            for (Long moduleId : moduleIds) {
//                AccessControl ac = new AccessControl();
//                Staff staff = staffRepository.findById(staffId)
//                        .orElseThrow(() -> new RuntimeException("Staff not found"));
//                ac.setModule(new Module(moduleId));
//                ac.setCanAccess(true);
//                accessControlRepository.save(ac);
//            }
//        }
//    }
    public List<AccessControl> findAll() { return accessControlRepository.findAll(); }

    public void delete(Long id) { accessControlRepository.deleteById(id); }
}
