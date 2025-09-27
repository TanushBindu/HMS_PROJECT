package com.hms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "access_control")
public class AccessControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @Column(name = "can_access")
    private boolean canAccess;

    public AccessControl() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }

    public boolean isCanAccess() { return canAccess; }
    public void setCanAccess(boolean canAccess) { this.canAccess = canAccess; }
}
