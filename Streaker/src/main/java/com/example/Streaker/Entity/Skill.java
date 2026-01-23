package com.example.Streaker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="skills")
@Data     // Lombok is implemented instead of getters and setters
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Here we used nullable to make the user not to enter the accurate data and not to keep empty
    @Column(nullable = false)
    private String name;
    //Here the category is like ex: java,DSA,JS...etc. whatever you want do
    @Column(nullable = false)
    private String category;
    //Here this is for to indicate no of unproductive days.
    @Column(nullable = false)
    private int decayDays;
    // this is for status of activeness
    @Column(nullable = false)
    private boolean active = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    private LocalDate lastPracticedDate;

}
