package asklepios.thinkit;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import asklepios.thinkit.api.*;
import asklepios.thinkit.models.*;
import asklepios.thinkit.services.*;
import org.apache.xmlrpc.*;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

@SpringBootApplication
@ComponentScan(basePackages = { "asklepios.thinkit", "asklepios.thinkit.api", "asklepios.thinkit.services",
        "asklepios.thinkit.models" })
public class ServerApplication implements CommandLineRunner {
    private final String custom_header = "dasee1213d";
    private final ScheduleApiController scheduleApiController;
    private final DataProcessingService dataProcessingService;

    public ServerApplication(ScheduleApiController scheduleApiController, DataProcessingService dataProcessingService) {
        this.scheduleApiController = scheduleApiController;
        this.dataProcessingService = dataProcessingService;

    }

    

    @Override
    public void run(String... arg0) throws Exception {

        try {

            System.out.println("Attempting to start XML-RPC Server...");

            WebServer server = new WebServer(9000);
            XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
            PropertyHandlerMapping handlerMapping = new PropertyHandlerMapping();
            handlerMapping.addHandler("virtualcoffee", DataProcessingService.class);
            xmlRpcServer.setHandlerMapping(handlerMapping);

            XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            serverConfig.setEnabledForExtensions(true);
            serverConfig.setContentLengthOptional(false);
            System.out.println("Started successfully.");
            System.out.println("Accepting requests. (Halt program to stop.)");
            server.start();

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception);
        }

        if (arg0.length > 0 && arg0[0].equals("exitcode")) {

            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(ServerApplication.class).run(args);
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
