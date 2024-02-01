package com.aex.platform.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactsOlds extends  UserOld{
    private String name_bank;
    private String prefijo;
}
