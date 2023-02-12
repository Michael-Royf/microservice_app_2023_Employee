package com.michael.departmentService.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @SequenceGenerator(
            name = "department_sequence",
            sequenceName = "department_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
    @Column(name = "department_id", nullable = false, updatable = false)
    private Long id;
    @Column(name = "department_name", nullable = false)
    private String departmentName;
    @Column(name = "department_description", nullable = false)
    private String departmentDescription;
    @Column(name = "department_code", nullable = false, unique = true)
    private String departmentCode;
}
