package com.jclarity.had_one_dismissal;


import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        XmlConfiguration config = new XmlConfiguration(new File("src/main/resources/jetty.xml").toURL());
        config.configure(server);
        
        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setExtractWAR(true);
        context.setContextPath("/");
//        context.setInitParameter( "contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext" );

        ProtectionDomain protectionDomain = Main.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        context.setWar(location.toExternalForm());

        server.setHandler(context);
        try {
            server.start();
            System.in.read();
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

}
