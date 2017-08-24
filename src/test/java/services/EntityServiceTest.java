package services;

import com.softserveinc.dropwizard_test.db.impl.MongoEntityDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class EntityServiceTest {
    @Mock
    private MongoEntityDao entityDao = mock(MongoEntityDao.class);

    @InjectMocks
    private EntityService entityService = new EntityService(entityDao);


    private Entity entity = mock(Entity.class);
    private int id = 1;

    @Test
    public void create() {
        entityService.create(entity);
        Mockito.verify(entityDao, Mockito.times(1)).create(entity);
    }

    @Test
    public void readOne() {
        entityService.get(id);
        Mockito.verify(entityDao, Mockito.times(1)).get(id);
    }

    @Test
    public void read() {
        entityService.getAll();
        Mockito.verify(entityDao, Mockito.times(1)).getAll();
    }

    @Test
    public void update() {
        entityService.update(id, entity);
        Mockito.verify(entityDao, Mockito.times(1)).update(id, entity);
    }

    @Test
    public void delete() {
        entityService.delete(id);
        Mockito.verify(entityDao, Mockito.times(1)).delete(id);
    }
}
