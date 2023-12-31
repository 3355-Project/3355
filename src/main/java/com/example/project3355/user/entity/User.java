package com.example.project3355.user.entity;

import com.example.project3355.user.dto.UserPasswordUpdateRequestDto;
import com.example.project3355.user.dto.UserProfileUpdateRequestDto;
import com.example.project3355.usercard.UserCard;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  @Column
  private String introduce;

  @OneToMany(mappedBy = "user")
  private Set<UserCard> userCards = new HashSet<>();

  public User(String username, String password, String email, String introduce) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.introduce = introduce;
  }

  public void update(UserProfileUpdateRequestDto requestDto) {
    this.email = requestDto.getEmail();
    this.introduce = requestDto.getIntroduce();
  }

}
