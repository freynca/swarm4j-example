package citrea.swarm4j.example.model;

import citrea.swarm4j.model.Host;
import citrea.swarm4j.model.Set;
import citrea.swarm4j.model.SwarmException;
import citrea.swarm4j.model.annotation.SwarmType;
import citrea.swarm4j.model.spec.SpecToken;

/**
 * Created with IntelliJ IDEA.
 *
 * @author aleksisha
 *         Date: 05.09.2014
 *         Time: 02:21
 */
@SwarmType
public class Mice extends Set<Mouse> {

    public Mice(SpecToken id, Host host) throws SwarmException {
        super(id, host);
    }


}
