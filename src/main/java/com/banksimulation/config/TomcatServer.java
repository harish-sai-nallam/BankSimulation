package com.banksimulation.config;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.catalina.Context;
import org.glassfish.jersey.servlet.ServletContainer;
import org.apache.catalina.Wrapper;

public class TomcatServer {
    private static final Logger logger=LogManager.getLogger(TomcatServer.class);

    public static void startServer() throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        String webappDirLocation = "src/main/webapp/";
        Context context = tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

        Wrapper jerseyServlet = context.createWrapper();
        jerseyServlet.setName("jersey-servlet");
        jerseyServlet.setServletClass(ServletContainer.class.getName());
        jerseyServlet.addInitParameter("javax.ws.rs.Application",
                "com.banksimulation.config.ApplicationApiMapper");
        jerseyServlet.setLoadOnStartup(1);
        context.addChild(jerseyServlet);
        context.addServletMappingDecoded("/bank/*", "jersey-servlet");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        logger.info("Tomcat is running!");
        logger.info("Tomcat port is set to: " + tomcat.getConnector().getPort());
        tomcat.getServer().await();
    }
}
