package com.fusiontech.milkevich.tasktracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusiontech.milkevich.tasktracker.constant.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usr")
public class User extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  @JsonIgnore
  private String password;
  @Enumerated(value = EnumType.STRING)
  private UserRole role;
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Task> tasks;
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Comment> comment;

}
