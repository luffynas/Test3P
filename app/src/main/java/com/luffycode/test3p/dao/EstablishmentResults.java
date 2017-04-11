package com.luffycode.test3p.dao;

import java.util.List;

/**
 * Created by Luffynas on 4/11/2017.
 */

public class EstablishmentResults {
    int status;
    String message;
    boolean error;
    List<Establishment> results;

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

    public List<Establishment> getResults() {
        return results;
    }

    public void setResults(List<Establishment> results) {
        this.results = results;
    }
}
