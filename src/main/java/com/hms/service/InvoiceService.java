package com.hms.service;

import com.hms.model.Invoice;
import com.hms.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        invoiceRepository.deleteById(id);
    }

    public List<Invoice> search(String keyword) {
        return invoiceRepository
                .findByPatientNameContainingIgnoreCaseOrDoctorNameContainingIgnoreCaseOrTreatmentContainingIgnoreCase(
                        keyword, keyword, keyword);
    }
}
