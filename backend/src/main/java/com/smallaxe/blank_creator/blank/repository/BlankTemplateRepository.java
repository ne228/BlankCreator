package com.smallaxe.blank_creator.blank.repository;

import com.smallaxe.blank_creator.blank.entity.BlankTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlankTemplateRepository extends JpaRepository<BlankTemplate, String> {

    List<BlankTemplate> findBlankTemplateByUser_Id(String id, Sort dateCreate);

    BlankTemplate findBlankTemplateByUser_IdAndId(String id, String blankTemplateId, Sort dateCreate);
}
