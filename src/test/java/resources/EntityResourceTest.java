package resources;

import com.softserveinc.dropwizard_test.resource.EntityResource;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class EntityResourceTest {
    @Mock
    private EntityService entityService;

    @InjectMocks
    private EntityResource resource;

    @Test
    public void create() throws Exception {
        resource.add(Mockito.any());
        Mockito.verify(entityService, Mockito.times(1)).createEntity(Mockito.any());
    }

    @Test
    public void readOne() throws Exception {
        resource.getByMessage(Mockito.anyString());
        Mockito.verify(entityService, Mockito.times(1)).getEntity(Mockito.anyString());
    }

    @Test
    public void read() throws Exception {
        resource.get();
        Mockito.verify(entityService, Mockito.times(1)).getAll();
    }

    @Test
    public void update() throws Exception {
        resource.update(Mockito.anyString(), Mockito.any());
        Mockito.verify(entityService, Mockito.times(1)).updateEntity(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void delete() throws Exception {
        resource.delete(Mockito.anyString());
        Mockito.verify(entityService, Mockito.times(1)).deleteEntity(Mockito.anyString());
    }
}
