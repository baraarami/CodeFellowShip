package com.example.CodeFellowship.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.catalina.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sun.util.calendar.BaseCalendar;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.*;

@Entity
@JsonIgnoreProperties(value = { " posts "})
public class ApplicationUser<set> implements UserDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;


    private String password;

    @Column(unique = true)
    private String username;

    private String firstName;
    private String lastName;
    private String bio ;
    private Date dataOfBirth;

    @OneToMany(mappedBy = "applicationUser")
    private List<Post> posts;


    @ManyToMany(mappedBy = "applicationUser")
    private List<Post> posts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id" , referencedColumnName = "id"))
    private set<Role> roles = new HashSet<>();

    public ApplicationUser(){}

    public ApplicationUser(String password, String username, String firstName, String lastName, String bio, Date dataOfBirth) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.dataOfBirth =  dataOfBirth;
    }

    public Set<Role> getRoles() {
        return  roles;
    }

    public void setRoles(set<Role> roles) {
        this.roles = roles;
    }
    public void setRole(Role newRole){
        roles.add(newRole);
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
        Set<Role> roles = getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
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

