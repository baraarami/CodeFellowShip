package com.example.CodeFellowship.domain;

import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String body;

    private Date date;


    @ManyToOne
    @JoinColumn(name = "applicationUser_id")
    private ApplicationUser applicationUser;

    public Post(){

    }

    public Post (String body , ApplicationUser applicationUser){
        this.body=body;
        this.applicationUser=applicationUser;
        this.date=new Date();
    }
    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser){
        this.applicationUser=applicationUser;
    }

    public Long getId() {
        return id;
    }

    public String getBody(){
        return body;
    }
    public void setBody(String body){
        this.body=body;
    }

    public Date getDate() {
        return date;
    }

    public String getFormatedDate(){
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }



}
