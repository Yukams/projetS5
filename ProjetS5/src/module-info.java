module projetS5 {
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.java;
    requires com.google.gson;
    requires AbsoluteLayout;
    //requires AbsoluteLayout;
    exports back.dbobjects;
    exports back.main;
    exports front.client;
    exports back.frontobjects;
}