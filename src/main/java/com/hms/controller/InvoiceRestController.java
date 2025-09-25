package com.hms.controller;

import com.hms.dto.InvoiceDto;
import com.hms.model.Invoice;
import com.hms.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceRestController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/{id}")
    public InvoiceDto getInvoice(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return new InvoiceDto(invoice);
    }
}
