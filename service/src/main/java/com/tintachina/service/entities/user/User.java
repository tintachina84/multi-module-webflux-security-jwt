package com.tintachina.service.entities.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author seonghyeon.seo
 */
@AllArgsConstructor
@Builder
@Data
@Table("users")
public class User {

  @Id
  private Long id;
  private String name;
  private String password;
  private String email;
  private String role;
  private String status;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime modifiedAt;
}
