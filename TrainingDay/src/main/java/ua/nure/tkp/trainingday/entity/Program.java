package ua.nure.tkp.trainingday.entity;

import javax.persistence.*;

@Entity
@Table(name = "Program")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name="duration")
    private Integer duration;
    @Column(name = "muscleGroup")
    private String group;

    public Program(String name, Integer duration, String group, String description) {
        this.name = name;
        this.duration = duration;
        this.group = group;
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Column(name="description")
    private String description;

    public Program() {
    }

    public Program(String name, Integer duration, String description) {
        this.name = name;
        this.duration = duration;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
