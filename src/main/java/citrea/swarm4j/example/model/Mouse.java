package citrea.swarm4j.example.model;

import citrea.swarm4j.model.Host;
import citrea.swarm4j.model.Model;
import citrea.swarm4j.model.SwarmException;
import citrea.swarm4j.model.annotation.SwarmField;
import citrea.swarm4j.model.annotation.SwarmType;
import citrea.swarm4j.model.spec.SpecToken;
import citrea.swarm4j.model.value.JSONValue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author aleksisha
 *         Date: 04.09.2014
 *         Time: 01:25
 */
@SwarmType
public class Mouse extends Model {

    @SwarmField
    public String symbol;
    @SwarmField
    public Integer x;
    @SwarmField
    public Integer y;
    @SwarmField
    public Integer ms;

    public Mouse(SpecToken id, Host host) throws SwarmException {
        super(id, host);
    }

    public Mouse(JSONValue initialState, Host host) throws SwarmException {
        super(initialState, host);
    }
}
