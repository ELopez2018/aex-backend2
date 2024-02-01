package com.aex.platform.entities.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransantionOld {
    private Long id;
    private Integer consigned;
    private Long user_code;
    private Long id_marker;
    private Long userSend;
    private Long userReceived;
    private String codeSend;
    private String fromtype;
    private Double fromValue;
    private String totype;
    private Double toValue;
    private Integer ifDonate;
    private Integer donatePorcent;
    private Integer typeDonate;
    private Integer donateValue;
    private Double totalSend;
    private String methodPay;
    private Integer idBankCount;
    private String bank_count;
    private Long id_bank;
    private String bank_name;
    private String bank_type;
    private Long dateSend;
    private Long dateReceive;
    private String state;
    private String comprobant;
    private String comprobant2;
    private String comprobant3;
    private String Comentary;
}
//getBank_count,getUserReceived