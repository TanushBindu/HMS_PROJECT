package com.hms.controller;

import com.hms.model.Invoice;
import com.hms.repository.InvoiceRepository;
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

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    // ✅ Main invoice page
    @GetMapping
    public String viewInvoices(Model model) {
        List<Invoice> invoices = invoiceRepository.findAll();

        // ✅ Add both list and empty invoice object (for modal form)
        model.addAttribute("invoices", invoices);
        model.addAttribute("invoice", new Invoice());

        return "invoice";
    }

    // ✅ Save new invoice from modal form
    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute Invoice invoice) {
        invoiceRepository.save(invoice);
        return "redirect:/invoice";
    }

    // ✅ Download all invoices as CSV
    @GetMapping("/download/csv")
    public void downloadCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=invoices.csv");

        List<Invoice> invoices = invoiceRepository.findAll();
        PrintWriter writer = response.getWriter();

        writer.println("ID,Patient,Doctor,Treatment,Amount,Payment Mode,Status,Date");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Invoice i : invoices) {
            writer.printf("%d,%s,%s,%s,%.2f,%s,%s,%s%n",
                    i.getId(), i.getPatientName(), i.getDoctorName(), i.getTreatment(),
                    i.getAmount(), i.getPaymentMode(), i.getStatus(),
                    i.getDate() != null ? i.getDate().format(formatter) : "");
        }

        writer.flush();
        writer.close();
    }

    // ✅ Download all invoices as PDF
    @GetMapping("/download/pdf")
    public void downloadPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoices.pdf");

        List<Invoice> invoices = invoiceRepository.findAll();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // ✅ Add header
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Hospital Invoice Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("Generated on: " + java.time.LocalDateTime.now()));
        document.add(Chunk.NEWLINE);

        // ✅ Table with headers
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 2, 2, 2, 1.5f, 2, 2, 2});

        String[] headers = {"ID", "Patient", "Doctor", "Treatment", "Amount", "Payment Mode", "Status", "Date"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Invoice i : invoices) {
            table.addCell(String.valueOf(i.getId()));
            table.addCell(i.getPatientName());
            table.addCell(i.getDoctorName());
            table.addCell(i.getTreatment());
            table.addCell(String.valueOf(i.getAmount()));
            table.addCell(i.getPaymentMode());
            table.addCell(i.getStatus());
            table.addCell(i.getDate() != null ? i.getDate().format(formatter) : "");
        }

        document.add(table);
        document.close();
    }

    // ✅ Individual invoice PDF
    @GetMapping("/download/pdf/{id}")
    public void downloadSinglePDF(@PathVariable Long id, HttpServletResponse response) throws IOException, DocumentException {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph title = new Paragraph("Invoice #" + invoice.getId(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell("Patient Name");
        table.addCell(invoice.getPatientName());
        table.addCell("Doctor Name");
        table.addCell(invoice.getDoctorName());
        table.addCell("Treatment");
        table.addCell(invoice.getTreatment());
        table.addCell("Amount");
        table.addCell(String.valueOf(invoice.getAmount()));
        table.addCell("Payment Mode");
        table.addCell(invoice.getPaymentMode());
        table.addCell("Status");
        table.addCell(invoice.getStatus());
        table.addCell("Date");
        table.addCell(invoice.getDate() != null ? invoice.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "");

        document.add(table);
        document.close();
    }

    // ✅ Individual invoice CSV
    @GetMapping("/download/csv/{id}")
    public void downloadSingleCSV(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + id + ".csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Patient,Doctor,Treatment,Amount,Payment Mode,Status,Date");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        writer.printf("%d,%s,%s,%s,%.2f,%s,%s,%s%n",
                invoice.getId(), invoice.getPatientName(), invoice.getDoctorName(), invoice.getTreatment(),
                invoice.getAmount(), invoice.getPaymentMode(), invoice.getStatus(),
                invoice.getDate() != null ? invoice.getDate().format(formatter) : "");

        writer.flush();
        writer.close();
    }
}
