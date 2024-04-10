/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.repository;

import com.aex.platform.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author estar
 */
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findAllByClientId(Long clientId);
  List<Transaction> findAllByClientIdIn(Collection<Long> clientId);
  @Query("select t from Transaction t where " +
          "t.status in :statusId " +
          "and t.correspondent is not null " +
          "order by t.id")
  List<Transaction> findAllByStatusIn(@Param("statusId") Collection<Long>  statusId);

  @Query("SELECT SUM(t.amountSent) FROM Transaction t "
          + "WHERE 1=1 "
          + "AND t.correspondent.user.id = :userId "
          + "AND t.currencyFrom = :currency "
          + "AND t.status <> 3"
  )
  Double getTransactionTotal(@Param("userId") Long userId,  @Param("currency") String currency);
  @Query("SELECT SUM(t.amountReceived) FROM Transaction t "
          + "WHERE 1=1 "
          + "AND t.cashier.id = :cashierId "
          + "AND t.status <> 3 "
          + "AND t.currencyTo = :currency "
  )
  Double getTransactionTotalCashierByCurrency(@Param("cashierId") Long cashierId, @Param("currency") String currency);

  @Query("SELECT t FROM Transaction t "
          + "WHERE 1=1 "
          + "AND t.cashier.id = :cashierId "
          + "AND t.status <> 3 "
          + "AND t.currencyTo = :currency "
  )
  Optional<List<Transaction>> getTransactionByCashierByCurrency(@Param("cashierId") Long cashierId, @Param("currency") String currency);

  @Query("SELECT t FROM Transaction t "
          + "WHERE 1=1 "
          + "AND t.correspondent.user.id = :userId "
          + "AND t.currencyFrom = :currency "
          + "AND t.status <> 3"
  )
  Optional<List<Transaction>>  getTransactionByCorrespondentByCurrency(@Param("userId") Long userId,  @Param("currency") String currency);
}
