/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.Currency;
import lombok.Data;

/**
 *
 * @author estar
 */
@Data
public class TransactionCreateDto {
    private Long clientId;
    private Long recipientId;
    private Long statusId;
    private Long cashierId;
    private Long issuingBankId;
    private Long receivingBankId;
    private Long correspondentId;
    private Double amountSent;
    private Double amountReceived;   
}
