package com.grofandriska.JPAdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "employees"/*.....*/)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_name", length = 200, nullable = false/*....*/)
    private String name;

    @ElementCollection
    @CollectionTable(name = "nicknames", joinColumns = @JoinColumn(name = "emp_id"))
    @Column(name = "nickname")
    private Set<String> nicknames;

    @ElementCollection
    @CollectionTable(name = "bookings", joinColumns = @JoinColumn(name = "emp_id"))
    @AttributeOverride(name = "startDate", column = @Column(name = "start_date"))
    @AttributeOverride(name = "daysTaken", column = @Column(name = "days"))
    private Set<VacationEntry> vacationBookings;

    @ElementCollection
    @CollectionTable(name = "phoneNumbers",joinColumns = @JoinColumn(name = "emp_id"))
    @MapKeyColumn(name = "phone_type")
    @Column(name = "phone_number")
    private Map<String, String> phoneNumbers;

    public Employee(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee(Map<String, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Employee(Long id, String name, Set<String> nicknames) {
        this.id = id;
        this.name = name;
        this.nicknames = nicknames;
    }

    @PostPersist
    public void debugPersist() {
        System.out.println(name + "  " + id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
