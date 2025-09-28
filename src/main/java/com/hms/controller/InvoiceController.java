package com.hms.controller;

import com.hms.model.Invoice;
import com.hms.model.Patient;
import com.hms.model.Staff;
import com.hms.service.InvoiceService;
import com.hms.service.PatientService;
import com.hms.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private final InvoiceService invoiceService;
    @Autowired
    private final PatientService patientService;
    @Autowired
    private final StaffService staffService;

    public InvoiceController(InvoiceService invoiceService,
                             PatientService patientService,
                             StaffService staffService) {
        this.invoiceService = invoiceService;
        this.patientService = patientService;
        this.staffService = staffService;
    }

    @GetMapping("/list")
    public String listInvoices(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Invoice> invoices;
        if (keyword != null && !keyword.isEmpty()) {
            invoices = invoiceService.search(keyword);
        } else {
            invoices = invoiceService.findAll();
        }

        model.addAttribute("invoices", invoices);
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", staffService.getAllStaff());
        model.addAttribute("newInvoice", new Invoice());
        return "invoice";
    }

    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute("newInvoice") Invoice invoice) {
        Optional<Patient> patient = patientService.getPatientById(invoice.getPatientId());
        if (patient != null) {
            invoice.setPatientName(patient.get().getName());
        }

        Optional<Staff> doctor = staffService.findById(invoice.getDoctorId());
        if (doctor != null) {
            invoice.setDoctorName(doctor.get().getName());
        }

        invoiceService.save(invoice);
        return "redirect:/invoice/list";
    }

    @GetMapping
    public String invoicePage(Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        model.addAttribute("invoices", invoices);
        return "invoice"; // maps to invoice.html
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Invoice getInvoice(@PathVariable Long id) {
        return invoiceService.findById(id).orElse(null);
    }

    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteById(id);
        return "redirect:/invoice/list";
    }

    // 🔹 PDF/Email/WhatsApp stubs (implement later)
    @GetMapping("/generate-pdf/{id}")
    @ResponseBody
    public String generatePdf(@PathVariable Long id) {
        return "PDF generated for invoice " + id;
    }

    @GetMapping("/send-email/{id}")
    @ResponseBody
    public String sendEmail(@PathVariable Long id) {
        return "Email sent for invoice " + id;
    }

    @GetMapping("/send-whatsapp/{id}")
    @ResponseBody
    public String sendWhatsapp(@PathVariable Long id) {
        return "WhatsApp message sent for invoice " + id;
    }
}
