package com.amdocs.phonedepot.dto;

import lombok.Data;

/**
 * @author Dhanapal
 */
@Data
public class ReportSalesWeeklyDTO {
    private int weekday;
    private double total;
}
