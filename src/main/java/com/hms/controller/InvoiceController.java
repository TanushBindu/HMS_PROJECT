package com.hms.controller;

import com.hms.model.Invoice;
import com.hms.model.Patient;
import com.hms.model.Doctor;
import com.hms.service.InvoiceService;
import com.hms.service.PatientService;
import com.hms.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    // List all invoices with optional search
    @GetMapping
    public String listInvoices(Model model,
                               @RequestParam(value = "search", required = false) String search) {
        List<Invoice> invoices;
        if (search != null && !search.isEmpty()) {
            invoices = invoiceService.searchInvoices(search);
        } else {
            invoices = invoiceService.getAllInvoices();
        }
        model.addAttribute("invoices", invoices);
        return "invoices"; // Thymeleaf template: invoices.html
    }

    @GetMapping("/search")
    public String searchInvoices(@RequestParam("keyword") String keyword, Model model) {
        List<Invoice> invoices = invoiceService.searchInvoices(keyword);
        model.addAttribute("invoices", invoices);
        return "invoices";
    }

    // Show form to create a new invoice
    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "add-invoice"; // Thymeleaf template: add-invoice.html
    }

    // Handle form submission for creating invoice
    @PostMapping("/add")
    public String createInvoice(@ModelAttribute Invoice invoice,
                                @RequestParam Long patientId,
                                @RequestParam Long doctorId) {
        invoiceService.createInvoice(invoice, patientId, doctorId);
        return "redirect:/invoices";
    }

    // Show form to edit an invoice
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "edit-invoice"; // Thymeleaf template: edit-invoice.html
    }

    // Handle form submission for editing
    @PostMapping("/edit/{id}")
    public String updateInvoice(@PathVariable Long id,
                                @ModelAttribute Invoice invoice,
                                @RequestParam Long patientId,
                                @RequestParam Long doctorId) {
        invoiceService.updateInvoice(id, invoice, patientId, doctorId);
        return "redirect:/invoices";
    }

    // Delete invoice
    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return "redirect:/invoices";
    }

    // Generate and download PDF
    @GetMapping("/pdf/{id}")
    public void downloadInvoicePdf(@PathVariable Long id, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf");
            byte[] pdfBytes = invoiceService.generatePdf(id);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
