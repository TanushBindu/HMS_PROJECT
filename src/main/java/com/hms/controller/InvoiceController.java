package com.hms.controller;

import com.hms.model.Invoice;
import com.hms.repository.InvoiceRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping
    public String viewInvoices(Model model) {
        List<Invoice> invoices = invoiceRepository.findAll();
        model.addAttribute("invoices", invoices);
        return "invoice";
    }

    @PostMapping("/generate")
    public String generateInvoice(@ModelAttribute Invoice invoice) {
        invoiceRepository.save(invoice);
        return "redirect:/invoice";
    }

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

    @GetMapping("/download/pdf")
    public void downloadPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoices.pdf");

        List<Invoice> invoices = invoiceRepository.findAll();

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
}
