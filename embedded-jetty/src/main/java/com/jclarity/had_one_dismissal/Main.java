package com.jclarity.had_one_dismissal;


import static java.lang.Integer.parseInt;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(parseInt(args[0]));
        configureJettyXml(server);

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
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

    private static void configureJettyXml(Server server) throws IOException,
            SAXException, MalformedURLException, Exception {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("WEB-INF/classes/jetty.xml");
        if (in != null) {
            File jettyFile = new File("/tmp/jetty.xml");
            System.setProperty("jetty.xml", jettyFile.getAbsolutePath());

            Files.copy(in, jettyFile.toPath(), REPLACE_EXISTING);
            
            XmlConfiguration config = new XmlConfiguration(jettyFile.toURL());
            config.configure(server);
        }
    }

}
