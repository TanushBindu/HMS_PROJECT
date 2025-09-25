package com.hms.utils;

import com.hms.model.Invoice;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generateInvoicePdf(Invoice invoice) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Invoice ID: " + invoice.getId()));
        document.add(new Paragraph("Patient: " + (invoice.getPatient() != null ? invoice.getPatient().getName() : "N/A")));
        document.add(new Paragraph("Doctor: " + (invoice.getDoctor() != null ? invoice.getDoctor().getName() : "N/A")));
        document.add(new Paragraph("Treatment: " + invoice.getTreatment()));
        document.add(new Paragraph("Amount: " + invoice.getAmount()));
        document.add(new Paragraph("Paid: " + (invoice.isPaid() ? "Yes" : "No")));
        document.add(new Paragraph("Payment Mode: " + invoice.getPaymentMode()));
        document.add(new Paragraph("Date: " + invoice.getCreatedAt()));

        document.close();
        return baos.toByteArray();
    }
}
