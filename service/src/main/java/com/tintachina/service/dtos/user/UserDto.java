package com.tintachina.service.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author seonghyeon.seo
 */
@AllArgsConstructor
@Builder
@Data
public class UserDto {

  private String id;
  private String name;
  private String password;
  private String email;
  private List<String> roles;
  private String status;
  private String createdAt;
  private String modifiedAt;
}
