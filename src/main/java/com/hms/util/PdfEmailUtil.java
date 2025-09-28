package com.hms.util;

import com.hms.model.Invoice;
import com.hms.service.InvoiceService;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.MimeMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
public class PdfEmailUtil {

    private final InvoiceService invoiceService;

    public PdfEmailUtil(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // generate a simple invoice PDF and return bytes
    @GetMapping("/generate-pdf/{id}")
    @ResponseBody
    public String generatePdf(@PathVariable Long id) {
        Invoice invoice = invoiceService.findById(id).orElse(null);
        if (invoice == null) {
            return "Invoice not found!";
        }

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.beginText();
            content.newLineAtOffset(50, 750);
            content.showText("Invoice #" + invoice.getId());
            content.endText();

            content.setFont(PDType1Font.HELVETICA, 12);
            content.beginText();
            content.newLineAtOffset(50, 720);
            content.showText("Patient: " + invoice.getPatientName());
            content.newLineAtOffset(0, -20);
            content.showText("Doctor: " + invoice.getDoctorName());
            content.newLineAtOffset(0, -20);
            content.showText("Treatment: " + invoice.getTreatment());
            content.newLineAtOffset(0, -20);
            content.showText("Payment: " + invoice.getPaymentMode());
            content.newLineAtOffset(0, -20);
            content.showText("Status: " + invoice.getStatus());
            content.endText();

            content.close();

            String fileName = "Invoice_" + invoice.getId() + ".pdf";
            doc.save(fileName);

            return "PDF saved as " + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating PDF: " + e.getMessage();
        }
    }

    // send email with PDF attachment using JavaMailSender
    public void sendInvoiceEmail(JavaMailSender mailSender, Invoice inv, String toEmail, byte[] pdfBytes) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("Invoice #" + (inv.getId() != null ? inv.getId() : ""));
        helper.setText("Dear " + inv.getPatientName() + ",\n\nPlease find attached your invoice.\n\nRegards,\nHospital");

        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        helper.addAttachment("invoice-" + (inv.getId() != null ? inv.getId() : "new") + ".pdf", resource);

        mailSender.send(message);
    }
}
