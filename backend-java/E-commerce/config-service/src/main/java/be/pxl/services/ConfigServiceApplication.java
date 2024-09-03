package be.pxl.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
//bootstrap config-first = config service will start first (before eureka discovery server)
//this means that EVERY service will get iets config from here
//this service will not be registered by the discovery service because it is not needed afterward
//the other way would be that the discovery server starts first
public class ConfigServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}
