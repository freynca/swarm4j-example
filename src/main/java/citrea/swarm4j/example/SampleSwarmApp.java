package citrea.swarm4j.example;

/**
 * Created with IntelliJ IDEA.
 *
 * @author aleksisha
 *         Date: 03/11/13
 *         Time: 16:42
 */

import citrea.swarm4j.example.model.Mouse;
import citrea.swarm4j.model.Host;
import citrea.swarm4j.model.SwarmException;
import citrea.swarm4j.model.spec.SpecToken;
import citrea.swarm4j.server.SwarmServer;
import citrea.swarm4j.storage.InMemoryStorage;
import citrea.swarm4j.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * SwarmServerApplication
 *
 * Created with IntelliJ IDEA.
 *
 * @author aleksisha
 *         Date: 25/10/13
 *         Time: 20:38
 */
@Configuration
//  @Import({StorageConfig.class})
@ComponentScan(basePackages = {"citrea.swarm4j"})
@EnableAutoConfiguration
public class SampleSwarmApp implements CommandLineRunner {

    public static final Logger logger = LoggerFactory.getLogger(SampleSwarmApp.class);

    public static final SpecToken SWARM = new SpecToken("#swarm~0");

    @Autowired
    private Storage storage;

    @Autowired
    private Host host;

    @Autowired
    private SwarmServer swarmServer;

    @Bean
    public Storage createStorage() {
        InMemoryStorage storage = new InMemoryStorage(new SpecToken("#dummy"));
        return storage;
    }

    @Bean
    public Host createHost() throws SwarmException {
        Host host = new Host(SWARM, createStorage());
        host.registerType(Mouse.class);
        return host;
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleSwarmApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        swarmServer.setHost(host);
        swarmServer.start();
        BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            try {
                String cmd = in.readLine();
                if (cmd == null) {
                    continue;
                }
                if ( cmd.equals( "exit" ) ) {
                    swarmServer.stop();
                    break;
                } else if (cmd.equals("restart")) {
                    swarmServer.stop();
                    swarmServer.start();
                }
            } catch (InterruptedException e) {
                logger.warn("run interrupted", e);
                break;
            } catch (NullPointerException e) {
                break;
            }
        }
        logger.info("exit");
        System.exit(0);
    }
}
