package com.asm.ecommerce.service;

import java.util.List;

public interface ReportService {
    List<Object[]> getTotalProductsSoldByDay();
    List<Object[]> getTotalProductsSoldByMonth();
    List<Object[]> getTotalProductsSoldByYear();
    List<Object[]> getTotalIncomeByDay();
    List<Object[]> getTotalIncomeByMonth();
    List<Object[]> getTotalIncomeByYear();
    List<Object[]> getTotalProductsSoldByCategory();
}
