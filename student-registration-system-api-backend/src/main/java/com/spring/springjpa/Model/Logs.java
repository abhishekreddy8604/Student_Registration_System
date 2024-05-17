package com.spring.springjpa.Model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Logs {

    private int id;

    private String userName;

    private Date opTime;

    private String tableName;

    private String operation;

    private String tupleKeyvalue;

    public Logs(int id, String userName, Date opTime, String tableName, String operation, String tupleKeyvalue) {
        this.id = id;
        this.userName = userName;
        this.opTime = opTime;
        this.tableName = tableName;
        this.operation = operation;
        this.tupleKeyvalue = tupleKeyvalue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpTime() {
        return new SimpleDateFormat("dd-MMM-yyyy").format(opTime);
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTupleKeyvalue() {
        return tupleKeyvalue;
    }

    public void setTupleKeyvalue(String tupleKeyvalue) {
        this.tupleKeyvalue = tupleKeyvalue;
    }
}