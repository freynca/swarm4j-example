package citrea.swarm4j.example;

/**
 * Created with IntelliJ IDEA.
 *
 * @author aleksisha
 *         Date: 03/11/13
 *         Time: 16:42
 */

import citrea.swarm4j.server.EmptyUpstreamFactory;
import citrea.swarm4j.server.SwarmServer;
import citrea.swarm4j.server.Swarm;
import citrea.swarm4j.model.Type;
import citrea.swarm4j.server.UpstreamFactory;
import citrea.swarm4j.spec.Spec;
import citrea.swarm4j.spec.SpecToken;
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
import java.util.Date;

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
@Import({StorageConfig.class})
@ComponentScan(basePackages = {"citrea.swarm4j"})
@EnableAutoConfiguration
public class SampleSwarmApp implements CommandLineRunner {

    public static final Logger logger = LoggerFactory.getLogger(SampleSwarmApp.class);

    @Autowired
    private SwarmServer swarmServer;

    @Autowired
    private Swarm swarm;

    @Bean
    public UpstreamFactory upstreamFactory() {
        return new EmptyUpstreamFactory();
    }

    @Bean
    public Swarm swarm() {
        String procId = SpecToken.date2ts(new Date());
        Swarm bean = new Swarm(new SpecToken("Swarm"), new SpecToken(procId));
        {
            Type type = new Type(bean, new Spec("/Type1"));
            type.registerField(new Type.FieldDescription("field11"));
            type.registerField(new Type.FieldDescription("field12"));
            bean.registerType(type);
        }
        {
            Type type = new Type(bean, new Spec("/Type2"));
            type.registerField(new Type.FieldDescription("field21"));
            type.registerField(new Type.FieldDescription("field22"));
            bean.registerType(type);
        }
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleSwarmApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        swarmServer.setSwarm(swarm);
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
