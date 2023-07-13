package com.example.demo.model;

import java.util.Set;

public class UserDTO {

  private String id;
  private String username;
  private String email;
  private Set<Role> roles;
  private Set<String> chatRoomIds;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<String> getChatRoomIds() {
    return chatRoomIds;
  }

  public void setChatRoomIds(Set<String> chatRoomIds) {
    this.chatRoomIds = chatRoomIds;
  }
}
