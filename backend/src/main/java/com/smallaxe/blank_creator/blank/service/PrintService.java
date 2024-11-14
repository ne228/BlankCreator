package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.Blank;

import java.util.List;

public interface PrintService {
    byte[] print(List<Blank> blanks) throws Exception;
}
