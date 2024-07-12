package ism.core;

public class MySql extends DataBaseImpl{
    public MySql(){
        this.driver="com.mysql.cj.jdbc.Driver";
        this.url="jdbc:mysql://localhost:3306/gestion_cours_bd";
        this.user="root";
        this.password="";
    }
}
