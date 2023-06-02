package com.example.test.wish.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "wishnew")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id", insertable = false)
    private Integer wishId;

    @Column(name = "mem_account")
    private String memAccount;

    @Column(name = "wish_subject")
    private String wishSubject;

    @Column(name = "wish_location")
    private String wishLocation;
    @Column(name = "wish_students")
    private String wishStudents;
    @Column(name = "wish_period")
    private String wishPeriod;
    @Column(name = "wish_salary")
    private String wishSalary;
    @Column(name = "wish_content", columnDefinition = "TEXT")
    private String wishContent;
    @Column(name = "wish_email")
    private String wishEmail;
    @Column(name = "create_time", insertable = false)
    private Timestamp createTime;
    @Column(name = "update_time", insertable = false)
    private Timestamp updateTime;
    @Column(name = "wish_status", insertable = false)
    private Integer WishStatus;

    public void addAttribute(String memAccount) {
    }

//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    @Override
//    public String toString() {
//        return "Wish{" +
//                "id=" + id +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                '}';
//    }



}
