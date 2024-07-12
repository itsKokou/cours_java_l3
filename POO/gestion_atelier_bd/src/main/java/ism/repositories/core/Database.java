package ism.repositories.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Database {
    void openConnection();
    void closeConnexion();
    ResultSet executeSelect(String sql);
    int executeUpdate(String sql);
    PreparedStatement getPs();
}
