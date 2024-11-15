package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.BlankHub;
import com.smallaxe.blank_creator.blank.models.BlankHubCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankHubEditDto;
import com.smallaxe.blank_creator.entity.User;

import javax.security.auth.login.CredentialException;
import java.util.List;

public interface BlankHubService {
    BlankHub create(BlankHubCreateDto blankHubCreateDto) throws CredentialException;

    BlankHub edit(BlankHubEditDto blankHubEditDto) throws CredentialException;

    List<BlankHub> getHubs() throws CredentialException;

    BlankHub getHubById(String id) throws CredentialException;

    boolean isCanAccessToBlankHub(BlankHub blankHub, User user);
}
