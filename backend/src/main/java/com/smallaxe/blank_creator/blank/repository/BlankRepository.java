package com.smallaxe.blank_creator.blank.repository;

import com.smallaxe.blank_creator.blank.entity.Blank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlankRepository extends JpaRepository<Blank, String> {
    Blank findBlankById(String id);
}
