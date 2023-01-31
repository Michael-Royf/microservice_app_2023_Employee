package com.michael.departmentService.repository;

import com.michael.departmentService.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentCode(String departmentCode);
}
