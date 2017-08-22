package services;

import com.softserveinc.dropwizard_test.db.EntityDao;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class EntityServiceTest {
    @Mock
    private EntityDao entityDao;

    @InjectMocks
    private EntityService entityService;

    @Test
    public void create() throws Exception {
        entityService.createEntity(Mockito.any());
        Mockito.verify(entityDao, Mockito.times(1)).createEntity(Mockito.any());
    }

    @Test
    public void readOne() throws Exception {
        entityService.getEntity(Mockito.anyString());
        Mockito.verify(entityDao, Mockito.times(1)).getEntity(Mockito.anyString());
    }

    @Test
    public void read() throws Exception {
        entityService.getAll();
        Mockito.verify(entityDao, Mockito.times(1)).getAll();
    }

    @Test
    public void update() throws Exception {
        entityService.updateEntity(Mockito.anyString(), Mockito.any());
        Mockito.verify(entityDao, Mockito.times(1)).updateEntity(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void delete() throws Exception {
        entityService.deleteEntity(Mockito.anyString());
        Mockito.verify(entityDao, Mockito.times(1)).deleteEntity(Mockito.anyString());
    }
}
