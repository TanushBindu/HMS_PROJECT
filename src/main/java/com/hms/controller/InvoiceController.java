package com.hms.controller;

import com.hms.model.Invoice;
import com.hms.repository.InvoiceRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    // 🟢 View all invoices
    @GetMapping("/invoice")
    public String getInvoices(Model model) {
        List<Invoice> invoices = invoiceRepository.findAll();
        model.addAttribute("invoices", invoices);
        return "invoice";
    }

    // 🟢 Save new invoice (used by form submission)
    @PostMapping("/invoice/save")
    public String saveInvoice(Invoice invoice) {
        if (invoice.getDate() == null) {
            invoice.setDate(java.time.LocalDateTime.now());
        }
        invoiceRepository.save(invoice);
        return "redirect:/invoice";
    }

    // 🟢 Download CSV
    @GetMapping("/invoice/download/csv")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=invoices.csv");

        List<Invoice> invoices = invoiceRepository.findAll();
        PrintWriter writer = response.getWriter();

        writer.println("ID,Patient,Doctor,Treatment,Amount,Payment Mode,Status,Date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Invoice invoice : invoices) {
            writer.println(String.format("%d,%s,%s,%s,%.2f,%s,%s,%s",
                    invoice.getId(),
                    invoice.getPatientName(),
                    invoice.getDoctorName(),
                    invoice.getTreatment(),
                    invoice.getAmount(),
                    invoice.getPaymentMode(),
                    invoice.getStatus(),
                    invoice.getDate() != null ? invoice.getDate().format(formatter) : ""));
        }

        writer.flush();
        writer.close();
    }

    // 🟢 Download PDF
    @GetMapping("/invoice/download/pdf")
    public void downloadPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoices.pdf");

        List<Invoice> invoices = invoiceRepository.findAll();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Invoice Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        String[] headers = {"ID", "Patient", "Doctor", "Treatment", "Amount", "Payment Mode", "Status"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        for (Invoice invoice : invoices) {
            table.addCell(String.valueOf(invoice.getId()));
            table.addCell(invoice.getPatientName());
            table.addCell(invoice.getDoctorName());
            table.addCell(invoice.getTreatment());
            table.addCell(String.valueOf(invoice.getAmount()));
            table.addCell(invoice.getPaymentMode());
            table.addCell(invoice.getStatus());
        }

        document.add(table);
        document.close();
    }
}
