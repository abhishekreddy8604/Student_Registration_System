package com.spring.springjpa.repository;

import com.spring.springjpa.Model.Logs;

import java.util.List;

public interface LogsRepository {

//    Logs findById(int id);

    List<Logs> findAll();
}
