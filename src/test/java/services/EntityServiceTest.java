package services;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.db.mongo.MongoEntityDaoAdapter;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.messaging.AppPublisher;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Provider;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

public class EntityServiceTest {
    @Mock
    private static MongoEntityDaoAdapter entityDao = mock(MongoEntityDaoAdapter.class);

    @Mock
    private static Provider<CrudDao<Entity>> daoProvider = mock(Provider.class);

    @Mock
    private AppPublisher publisher = mock(AppPublisher.class);

    @Mock
    private static MetricRegistry metricRegistry = mock(MetricRegistry.class);

    @BeforeClass
    public static void beforClassSetup() {
        Mockito.when(metricRegistry.histogram(anyString())).thenReturn(mock(Histogram.class));
        Mockito.when(daoProvider.get()).thenReturn(entityDao);
    }

    @InjectMocks
    private EntityService entityService = new EntityService(publisher, daoProvider, metricRegistry);

    private final static int ID = 1;
    private Entity entity = new Entity(ID, "test");

    @After
    public void tearDown() throws Exception {
        reset(entityDao);
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
        Mockito.when(daoProvider.get().update(entity)).thenReturn(entity);
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
