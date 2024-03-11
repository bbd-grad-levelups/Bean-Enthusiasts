package com.bbd.shared.request_model;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BanBeanRequest {
  @NonNull
  private int bean_id;
  @NonNull
  private boolean is_banned;
}