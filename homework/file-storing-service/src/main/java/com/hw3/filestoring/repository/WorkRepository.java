package com.hw3.filestoring.repository;

import com.hw3.filestoring.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    List<Work> findByAssignmentId(String assignmentId);
    List<Work> findByStudentId(String studentId);
    List<Work> findByAssignmentIdAndStudentIdNot(String assignmentId, String studentId);
}