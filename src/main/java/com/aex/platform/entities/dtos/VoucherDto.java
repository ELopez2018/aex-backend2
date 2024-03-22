package com.aex.platform.entities.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class VoucherDto {
    private Long transactionType;
    private Long transactionId;
    private MultipartFile[] image;
    private Boolean main;
    private String observations;
}
