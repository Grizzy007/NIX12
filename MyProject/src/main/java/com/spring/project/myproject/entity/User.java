package com.spring.project.myproject.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "Full_Name")
    String fullName;
    @Column(name = "City")
    String city;
    @Column(name = "State")
    String state;
    @Column(name = "Zipcode")
    Integer zipcode;
    @Column(name = "Egg")
    Boolean eggAllergy;
    @Column(name = "Peanut")
    Boolean peanutAllergy;
    @Column(name = "Dairy")
    Boolean dairyAllergy;

}
