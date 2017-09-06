package services;

import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.db.mongo.MongoEntityDaoAdapter;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.messaging.AppPublisher;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Provider;

import static org.mockito.Mockito.mock;

public class EntityServiceTest {
    @Mock
    private MongoEntityDaoAdapter entityDao = mock(MongoEntityDaoAdapter.class);

    @Mock
    private Provider<CrudDao<Entity>> daoProvider = mock(Provider.class);

    @Mock
    private AppPublisher publisher = mock(AppPublisher.class);

    @InjectMocks
    private EntityService entityService = new EntityService(publisher, daoProvider);

    private final static int ID = 1;
    private Entity entity = new Entity(ID, "test");

    @Before
    public void setUp() throws Exception {
        Mockito.when(daoProvider.get()).thenReturn(entityDao);
    }

    @Test
    public void create() {
        Mockito.when(entityDao.get(ID)).thenReturn(null);
        entityService.create(entity);

        Mockito.when(entityDao.get(ID)).thenReturn(entity);
        entityService.create(entity);

        Mockito.verify(entityDao, Mockito.times(1)).create(entity);
        Mockito.verify(entityDao, Mockito.times(1)).update(entity);
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
        entityService.update(entity);

        Mockito.when(entityDao.get(ID)).thenReturn(null);
        entityService.update(entity);

        Mockito.verify(entityDao, Mockito.times(1)).update(entity);
        Mockito.verify(entityDao, Mockito.times(1)).create(entity);
    }

    @Test
    public void delete() {
        entityService.delete(ID);
        Mockito.verify(entityDao, Mockito.times(1)).delete(ID);
    }
}
