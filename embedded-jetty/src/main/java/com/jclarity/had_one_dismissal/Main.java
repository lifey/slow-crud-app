package com.jclarity.had_one_dismissal;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class Main {

    public static void main(String[] args) throws Exception {
        File jettyFile = new File("/tmp/jetty.xml");
        System.setProperty("jetty.xml", jettyFile.getAbsolutePath());
        
        Server server = new Server(8080);
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("WEB-INF/classes/jetty.xml");
        Files.copy(in, jettyFile.toPath(), REPLACE_EXISTING);
        
        XmlConfiguration config = new XmlConfiguration(jettyFile.toURL());
        config.configure(server);
        
        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setExtractWAR(true);
        context.setContextPath("/");

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
