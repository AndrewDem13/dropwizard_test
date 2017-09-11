package resources;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.collect.ImmutableList;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.resource.EntityResource;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.*;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

public class EntityResourceTest {

    private final static EntityService entityService = mock(EntityService.class);
    private final static MetricRegistry metricRegistry = mock(MetricRegistry.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new EntityResource(entityService, metricRegistry))
            .build();

    @BeforeClass
    public static void beforeClassSetUp() {
        when(metricRegistry.timer(anyString())).thenReturn(mock(Timer.class));
    }

    private static final Entity entity = new Entity(1,"test");
    private static final String path = String.valueOf(entity.getId());
    private static final Entity updatedEntity = new Entity(entity.getId(), "updated");
    private static final ImmutableList<Entity> list = ImmutableList.of(entity);

    @Before
    public void setUp() throws Exception {
        when(entityService.getAll()).thenReturn(list);
        when(entityService.get(entity.getId())).thenReturn(entity);
        when(entityService.delete(anyInt())).thenReturn(true);
        when(entityService.update(any(Entity.class))).thenReturn(updatedEntity);
    }

    @After
    public void tearDown() throws Exception {
        reset(entityService);
    }

    @Test
    public void delete() {
        Response response = resources.target("/entity/" + path).request().delete();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void getAll() {
        Response response = resources.target("/entity").request().get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void get() {
        Response response = resources.target("/entity/" + path).request().get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void create() {
        Response post = resources.target("/entity").request().post(javax.ws.rs.client.Entity.json(entity));
        Assert.assertEquals(201, post.getStatus());
    }

    @Test
    public void update() {
        Response updated = resources.target("/entity/" + path).request()
                                    .put(javax.ws.rs.client.Entity.json(updatedEntity));
        Assert.assertEquals(200, updated.getStatus());
    }
}
