package com.aex.platform.entities.dtos;

import com.aex.platform.entities.User;
import lombok.Data;

@Data
public class CorrespondentDTO {

  private User user;

  private String nit;

  private String tradename;

  private String officePhone;

  private String mainImage;

  private String advertisingImages;

}
