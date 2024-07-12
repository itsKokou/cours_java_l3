package ism.repositories.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DataBase {
    void openConnection();
    void closeConnexion();
    ResultSet executeSelect();
    int executeUpdate();
    PreparedStatement getPs();
    void initPrepareStatement(String sql);
}
