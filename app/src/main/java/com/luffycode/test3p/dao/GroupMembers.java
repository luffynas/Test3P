package com.luffycode.test3p.dao;

import java.util.List;

/**
 * Created by Luffynas on 4/11/2017.
 */

public class GroupMembers {
    int status;
    String message;
    boolean error;
    List<Person> results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Person> getResults() {
        return results;
    }

    public void setResults(List<Person> results) {
        this.results = results;
    }
}
