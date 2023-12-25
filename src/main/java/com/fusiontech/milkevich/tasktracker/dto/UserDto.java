package com.fusiontech.milkevich.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusiontech.milkevich.tasktracker.constant.UserRole;
import com.fusiontech.milkevich.tasktracker.entity.User;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractDto implements Serializable, UserDetails {

  private Long id;
  private String name;
  @JsonIgnore
  private String password;
  private UserRole role;
  private Set<TaskDto> tasks;
  private List<CommentDto> comment;

  /**
   * Constructor.
   *
   * @param id       - user id
   * @param name     - user name
   * @param password - user password
   * @param role     - user role
   */
  public UserDto(Long id, String name, String password, UserRole role) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.role = role;
  }

  public UserDto(String name, String password) {
    this.name = name;
    this.password = password;
  }

  /**
   * Builds a UserDetailsImpl object from a User object.
   *
   * @param user The User object to build UserDetailsImpl from.
   * @return The built UserDetailsImpl object.
   */
  public static UserDto build(User user) {
    return new UserDto(
        user.getId(),
        user.getName(),
        user.getPassword(),
        user.getRole()
    );
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return name;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
}
