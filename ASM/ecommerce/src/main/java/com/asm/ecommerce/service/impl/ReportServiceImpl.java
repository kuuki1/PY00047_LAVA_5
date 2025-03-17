package com.asm.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm.ecommerce.repository.ProductOrderRepository;
import com.asm.ecommerce.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Override
    public List<Object[]> getTotalProductsSoldByDay() {
        return productOrderRepository.getTotalProductsSoldByDay();
    }

    @Override
    public List<Object[]> getTotalProductsSoldByMonth() {
        return productOrderRepository.getTotalProductsSoldByMonth();
    }

    @Override
    public List<Object[]> getTotalProductsSoldByYear() {
        return productOrderRepository.getTotalProductsSoldByYear();
    }

    @Override
    public List<Object[]> getTotalIncomeByDay() {
        return productOrderRepository.getTotalIncomeByDay();
    }

    @Override
    public List<Object[]> getTotalIncomeByMonth() {
        return productOrderRepository.getTotalIncomeByMonth();
    }

    @Override
    public List<Object[]> getTotalIncomeByYear() {
        return productOrderRepository.getTotalIncomeByYear();
    }

    @Override
    public List<Object[]> getTotalProductsSoldByCategory() {
        return productOrderRepository.getTotalProductsSoldByCategory();
    }
}
