package com.example.CodeFellowship.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = { " posts "})
public class ApplicationUser  implements UserDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;


    private String password;

    @Column(unique = true)
    private String username;

    private String firstName;
    private String lastName;
    private String bio ;
    private Data dataOfBirth;

    @OneToMany(mappedBy = "applicationUser")
    private List<Post> posts;

    public ApplicationUser(){}

    public ApplicationUser(String password, String username, String firstName, String lastName, String bio, Data dataOfBirth) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.dataOfBirth = dataOfBirth;
    }

    public ApplicationUser(String encode, String username, String firstName, String lastName, String bio, String dateOfBirth) {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Data getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(Data dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}

