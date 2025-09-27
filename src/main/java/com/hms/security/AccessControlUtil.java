package com.hms.security;

import com.hms.model.AccessControl;
import com.hms.model.Role;
import com.hms.model.Staff;
import com.hms.repository.AccessControlRepository;
import com.hms.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccessControlUtil {

    @Autowired
    private final StaffRepository staffRepository;
    @Autowired
    private final AccessControlRepository accessControlRepository;

    @Autowired
    public AccessControlUtil(StaffRepository staffRepository, AccessControlRepository accessControlRepository) {
        this.staffRepository = staffRepository;
        this.accessControlRepository = accessControlRepository;
    }

    public boolean hasAccess(String username, String module) {
        Optional<Staff> staff = staffRepository.findByUsername(username);
        if (staff == null) return false;

        // ✅ Admin always has access
        if (staff.get().getRole() .equals(Role.ADMIN)) {
            return true;
        }

        List<AccessControl> accessList = accessControlRepository.findByStaffId(staff.get().getId());
        for (AccessControl ac : accessList) {
            if (ac.getModule().getName().equalsIgnoreCase(module) && ac.isCanAccess()) {
                return true;
            }
        }
        return false;
    }
}
