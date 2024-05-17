package com.techfirm.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techfirm.stock.model.enumeration.ReturnReason;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ReturnedSalesRequest {
    private Long salesId;
    private List<ProductReturn> productReturn;
    private ReturnReason reasonForReturn;
    private String additionalComments;
    private LocalDate returnDate = LocalDate.now();
}
