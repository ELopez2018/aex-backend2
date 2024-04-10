package com.aex.platform.entities;

import com.aex.platform.entities.dtos.UserAdapter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mobile_payments")
public class MobilePayment extends EntityBase {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name ="document_type")
    private String documentType;

    @Column(name ="document_number")
    private String documentNumber;

    @Column(name ="cellPhone_number")
    private String cellPhoneNumber;

    @Column(name ="trm", nullable = true)
    private Integer trm;

    @Column(name = "currency_from")
    private String currencyFrom;

    @Column(name = "currency_to")
    private String currencyTo;

    @Column(name ="value_receive")
    private Double valueReceive;

    @Column(name ="value_to_send")
    private Double valueToSend;

    @Column(name = "status",nullable = true)
    private Long status;

    @Column(name = "type",nullable = true)
    private String type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mobile_payment_id")  // Esta columna se agregará a la tabla de libros
    private List<Voucher> voucher;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "cashier_id", nullable = true)
    private User cashier;

    @ManyToOne
    @JoinColumn(name = "correspondent_id")
    private Correspondent correspondent;

    public User getClient() {
        if(client == null){
            return null;
        }
        return new UserAdapter(client);
    }
    public User getCashier() {
        if(cashier == null){
            return null;
        }
        return new UserAdapter(cashier);
    }

}
