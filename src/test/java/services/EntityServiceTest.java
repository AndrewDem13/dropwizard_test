package services;

import com.softserveinc.dropwizard_test.db.CrudDao;
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
    private String string = "test";

    @Test
    public void create() {
        entityService.create(entity);
        Mockito.verify(entityDao, Mockito.times(1)).create(entity);
    }

    @Test
    public void readOne() {
        entityService.get(string);
        Mockito.verify(entityDao, Mockito.times(1)).get(string);
    }

    @Test
    public void read() {
        entityService.getAll();
        Mockito.verify(entityDao, Mockito.times(1)).getAll();
    }

    @Test
    public void update() {
        entityService.update(string, entity);
        Mockito.verify(entityDao, Mockito.times(1)).update(string, entity);
    }

    @Test
    public void delete() {
        entityService.delete(string);
        Mockito.verify(entityDao, Mockito.times(1)).delete(string);
    }
}
