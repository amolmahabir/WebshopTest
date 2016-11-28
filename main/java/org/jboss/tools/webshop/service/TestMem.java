package org.jboss.tools.webshop.service;

import java.sql.Connection;
import java.sql.DriverManager;
import org.h2.tools.Server;

public class TestMem {
    public static void main(String... args) throws Exception {

        // open the in-memory database within a VM
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:WEBSHOP");
        conn.createStatement().execute("SELECT * FROM PRODUCT");

        // start a TCP server
        // (either before or after opening the database)
        String[] serverinput = {"-tcpPort", "8082", "-tcpAllowOthers"};
        Server server = Server.createTcpServer(serverinput).start();

        // .. use in embedded mode ..

        // or use it from another process:
        System.out.println("Server started and connection is open.");
        System.out.println("URL: jdbc:h2:" + server.getURL() + "/mem:WEBSHOP");

        // now start the H2 Console here or in another process using
        // java org.h2.tools.Console -web -browser

        System.out.println("Press [Enter] to stop.");
        System.in.read();

        System.out.println("Stopping server and closing the connection");
        server.stop();
        conn.close();
    }
}