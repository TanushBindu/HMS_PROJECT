package com.hms.controller;

import com.hms.model.Invoice;
import com.hms.service.InvoiceService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    /**
     * ✅ Combined Invoice & Income Page
     */
    @GetMapping
    public String showInvoiceDashboard(Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        Map<String, Object> stats = invoiceService.getIncomeStats();

        model.addAttribute("invoices", invoices);
        model.addAttribute("invoice", new Invoice());
        model.addAllAttributes(stats);

        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.findAll());

        return "invoice"; // invoice.html (your combined UI)
    }

    /**
     * ✅ Save a new invoice
     */
    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute("invoice") Invoice invoice) {
        invoiceService.save(invoice);
        return "redirect:/invoice";
    }

    /**
     * ✅ Download all invoices as CSV
     */
    @GetMapping("/download/csv")
    public void downloadAllCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=invoices.csv");

        List<Invoice> invoices = invoiceService.findAll();
        PrintWriter writer = response.getWriter();

        writer.println("ID,Patient,Doctor,Treatment,Amount,Payment Mode,Status,Date");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Invoice i : invoices) {
            writer.printf("%d,%s,%s,%s,%.2f,%s,%s,%s%n",
                    i.getId(), i.getPatientName(), i.getDoctorName(), i.getTreatment(),
                    i.getAmount(), i.getPaymentMode(), i.getStatus(),
                    i.getDate() != null ? i.getDate().format(fmt) : "");
        }
        writer.flush();
        writer.close();
    }

    /**
     * ✅ Download all invoices as PDF
     */
    @GetMapping("/download/pdf")
    public void downloadAllPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoices.pdf");

        List<Invoice> invoices = invoiceService.findAll();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Hospital Invoice Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now()));
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 2, 2, 2, 1.5f, 2, 2, 2});

        String[] headers = {"ID", "Patient", "Doctor", "Treatment", "Amount", "Payment Mode", "Status", "Date"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Invoice i : invoices) {
            table.addCell(String.valueOf(i.getId()));
            table.addCell(i.getPatientName());
            table.addCell(i.getDoctorName());
            table.addCell(i.getTreatment());
            table.addCell(String.valueOf(i.getAmount()));
            table.addCell(i.getPaymentMode());
            table.addCell(i.getStatus());
            table.addCell(i.getDate() != null ? i.getDate().format(fmt) : "");
        }

        document.add(table);
        document.close();
    }

    /**
     * ✅ Download single invoice as PDF
     */
    @GetMapping("/download/pdf/{id}")
    public void downloadSinglePDF(@PathVariable Long id, HttpServletResponse response)
            throws IOException, DocumentException {
        Invoice invoice = invoiceService.findById(id);
        if (invoice == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Invoice #" + id, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("Patient: " + invoice.getPatientName()));
        document.add(new Paragraph("Doctor: " + invoice.getDoctorName()));
        document.add(new Paragraph("Treatment: " + invoice.getTreatment()));
        document.add(new Paragraph("Amount: ₹" + invoice.getAmount()));
        document.add(new Paragraph("Payment Mode: " + invoice.getPaymentMode()));
        document.add(new Paragraph("Status: " + invoice.getStatus()));
        document.add(new Paragraph("Date: " + invoice.getDate()));

        document.close();
    }

    /**
     * ✅ Download single invoice as CSV
     */
    @GetMapping("/download/csv/{id}")
    public void downloadSingleCSV(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Invoice invoice = invoiceService.findById(id);
        if (invoice == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
            return;
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Patient,Doctor,Treatment,Amount,Payment Mode,Status,Date");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        writer.printf("%d,%s,%s,%s,%.2f,%s,%s,%s%n",
                invoice.getId(), invoice.getPatientName(), invoice.getDoctorName(),
                invoice.getTreatment(), invoice.getAmount(), invoice.getPaymentMode(),
                invoice.getStatus(), invoice.getDate() != null ? invoice.getDate().format(fmt) : "");

        writer.flush();
        writer.close();
    }
}
