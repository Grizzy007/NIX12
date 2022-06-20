package ua.nure.tkp.trainingday.entity;

public enum Permission {
    READ("read"),
    WRITE("write");

    private final String permisssion;

    Permission(String permisssion){
        this.permisssion=permisssion;
    }

    public String getPermisssion() {
        return permisssion;
    }
}
