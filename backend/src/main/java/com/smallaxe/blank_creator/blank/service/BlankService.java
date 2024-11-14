package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.models.BlankCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankEditDto;
import com.smallaxe.blank_creator.entity.User;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.List;

@Service
public interface BlankService {
     Blank create(BlankCreateDto dto) throws CredentialException;

     Blank getBlankById(String blankId) throws CredentialException;

     Blank edit(BlankEditDto dto) throws CredentialException;

     Blank rejectBlank(String blankId) throws CredentialException;

     Blank successBlank(String blankId) throws CredentialException;


     boolean isCanAccessToBlank(Blank blank, User user);

     List<Blank> getAllBlanks() throws CredentialException;
}
