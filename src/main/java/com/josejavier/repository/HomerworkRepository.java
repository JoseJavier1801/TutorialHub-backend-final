package com.josejavier.repository;

import com.josejavier.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomerworkRepository extends JpaRepository<Homework, Integer> {

}
