package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.entity.BlankTemplate;
import com.smallaxe.blank_creator.blank.models.ImportTemplateDto;

import javax.security.auth.login.CredentialException;
import java.util.List;

public interface BLankTemplateService {

     List<BlankTemplate> getBlankTemplates() throws CredentialException;

     BlankTemplate getBlankTemplateById(String blankTemplateId) throws CredentialException;

     Blank addBlankTemplateToHub(ImportTemplateDto dto) throws CredentialException;

}
