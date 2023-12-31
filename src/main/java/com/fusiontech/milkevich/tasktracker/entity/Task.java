package com.fusiontech.milkevich.tasktracker.entity;

import com.fusiontech.milkevich.tasktracker.constant.TaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Task entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private String description;
  @Enumerated(value = EnumType.STRING)
  private TaskStatus status;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinColumn(name = "user_id")
  private User user;
  @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
  private List<Comment> comment;

}
