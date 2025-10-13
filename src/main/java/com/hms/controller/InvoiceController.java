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

    // ===== CSV Downloads =====
    @GetMapping("/download/csv")
    public void downloadCSV(HttpServletResponse response) throws IOException {
        downloadCSVHelper(invoiceRepository.findAll(), response, "invoices.csv");
    }

    @GetMapping("/download/csv/{id}")
    public void downloadCSVById(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
        downloadCSVHelper(List.of(invoice), response, "invoice_" + id + ".csv");
    }

    private void downloadCSVHelper(List<Invoice> invoices, HttpServletResponse response, String filename) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

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

    // ===== PDF Downloads =====
    @GetMapping("/download/pdf")
    public void downloadPDF(HttpServletResponse response) throws IOException, DocumentException {
        downloadPDFHelper(invoiceRepository.findAll(), response, "invoices.pdf");
    }

    @GetMapping("/download/pdf/{id}")
    public void downloadPDFById(@PathVariable Long id, HttpServletResponse response) throws IOException, DocumentException {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
        downloadPDFHelper(List.of(invoice), response, "invoice_" + id + ".pdf");
    }

    private void downloadPDFHelper(List<Invoice> invoices, HttpServletResponse response, String filename) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        Document document = new Document(PageSize.A4, 36, 36, 54, 36);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // ===== Hospital Logo =====
        String logoPath = "src/main/resources/static/img/img.png"; // uploaded logo
        Image logo = Image.getInstance(logoPath);
        logo.scaleToFit(100, 100);
        logo.setAlignment(Element.ALIGN_LEFT);
        document.add(logo);

        // ===== Hospital Info =====
        Font hospitalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLUE);
        Paragraph hospitalName = new Paragraph("My Hospital Name", hospitalFont);
        hospitalName.setAlignment(Element.ALIGN_CENTER);
        document.add(hospitalName);

        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Paragraph address = new Paragraph("123 Main Street, City, State, ZIP\nPhone: 123-456-7890", infoFont);
        address.setAlignment(Element.ALIGN_CENTER);
        document.add(address);

        document.add(Chunk.NEWLINE);

        // ===== Invoice Title =====
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(Chunk.NEWLINE);

        // ===== Table =====
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 5, 2, 2, 2});

        // Header row
        String[] headers = {"ID", "Description / Treatment", "Unit Cost", "Qty", "Total"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Rows
        double grandTotal = 0.0;
        for (Invoice i : invoices) {
            table.addCell(String.valueOf(i.getId()));
            table.addCell(i.getTreatment());
            table.addCell(String.format("%.2f", i.getAmount())); // Assuming unit cost = total
            table.addCell("1"); // Quantity
            table.addCell(String.format("%.2f", i.getAmount()));
            grandTotal += i.getAmount() != null ? i.getAmount() : 0.0;
        }

        document.add(table);

        // ===== Total =====
        Paragraph total = new Paragraph("TOTAL: $" + String.format("%.2f", grandTotal),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK));
        total.setAlignment(Element.ALIGN_RIGHT);
        total.setSpacingBefore(10);
        document.add(total);

        // ===== Thank You =====
        Paragraph thankYou = new Paragraph("Thank you for your visit!", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12));
        thankYou.setAlignment(Element.ALIGN_CENTER);
        thankYou.setSpacingBefore(20);
        document.add(thankYou);

        document.close();
    }


}
