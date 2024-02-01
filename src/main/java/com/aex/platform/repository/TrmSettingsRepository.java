package com.aex.platform.repository;

import com.aex.platform.entities.Transaction;
import com.aex.platform.entities.TrmSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrmSettingsRepository extends JpaRepository<TrmSetting, Long> {
}
