package com.model;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedSalaryResult {
    private List<SalaryView> salaryViews;
    private int totalPages;
}
