package com.spring.project.myproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Dining_Review")
public class DiningReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    @Column(name = "User_Name")
    String userName;
    @Column(name = "User_Id")
    Long userId;
    @Column(name = "Peanut")
    Integer peanutScore;
    @Column(name = "Egg")
    Integer eggScore;
    @Column(name = "Dairy")
    Integer dairyScore;
    @Column(name = "Commentary")
    String commentary;

}
