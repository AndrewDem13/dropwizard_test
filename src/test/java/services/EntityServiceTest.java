package services;

import com.softserveinc.dropwizard_test.db.impl.MongoEntityDaoAdapter;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class EntityServiceTest {
    @Mock
    private MongoEntityDaoAdapter entityDao = mock(MongoEntityDaoAdapter.class);

    @InjectMocks
    private EntityService entityService = new EntityService(entityDao);

    private final static int ID = 1;
    private Entity entity = new Entity(ID, "test");

    @Test
    public void create() {
        Mockito.when(entityDao.get(ID)).thenReturn(null);
        entityService.create(entity);

        Mockito.when(entityDao.get(ID)).thenReturn(entity);
        entityService.create(entity);

        Mockito.verify(entityDao, Mockito.times(1)).create(entity);
        Mockito.verify(entityDao, Mockito.times(1)).update(ID, entity);
    }

    @Test
    public void readOne() {
        entityService.get(ID);
        Mockito.verify(entityDao, Mockito.times(1)).get(ID);
    }

    @Test
    public void read() {
        entityService.getAll();
        Mockito.verify(entityDao, Mockito.times(1)).getAll();
    }

    @Test
    public void update() {
        Mockito.when(entityDao.get(ID)).thenReturn(entity);
        entityService.update(ID, entity);

        Mockito.when(entityDao.get(ID)).thenReturn(null);
        entityService.update(ID, entity);

        Mockito.verify(entityDao, Mockito.times(1)).update(ID, entity);
        Mockito.verify(entityDao, Mockito.times(1)).create(entity);
    }

    @Test
    public void delete() {
        entityService.delete(ID);
        Mockito.verify(entityDao, Mockito.times(1)).delete(ID);
    }
}
