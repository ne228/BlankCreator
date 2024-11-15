package com.smallaxe.blank_creator.blank.repository;


import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.entity.BlankHub;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlankHubRepository extends JpaRepository<BlankHub, String > {

    BlankHub findBlankHubById(String id);
    List<BlankHub> findBlankHubByUser_Id(String userId, Sort sort);

    BlankHub findBlankHubByUser_IdAndId(String userId, String hubId);
}
