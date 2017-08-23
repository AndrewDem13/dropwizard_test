package resources;

import com.google.common.collect.ImmutableList;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.resource.EntityResource;
import com.softserveinc.dropwizard_test.service.impl.EntityService;
import org.junit.*;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

public class EntityResourceTest {

    private final static EntityService entityService = mock(EntityService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new EntityResource(entityService))
            .build();

    private static final Entity entity = new Entity("test");
    private static final String path = entity.getMessage();
    private static final Entity updatedEntity = new Entity("updated");
    private static final ImmutableList<Entity> list = ImmutableList.of(entity);

    @Before
    public void setUp() throws Exception {
        when(entityService.getAll()).thenReturn(list);
        when(entityService.get(anyString())).thenReturn(entity);
        when(entityService.delete(entity.getMessage())).thenReturn(true);
        when(entityService.update(anyString(), any())).thenReturn(updatedEntity);
    }

    @After
    public void tearDown() throws Exception {
        reset(entityService);
    }

    @Test
    public void testDeleteEntity() {
        Response response = resources.target("/test-entity/" + path).request().delete();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetAllEntities() {
        Response response = resources.target("/test-entity").request().get();
        Assert.assertEquals(302, response.getStatus());
    }

    @Test
    public void testGetEntity() {
        Entity entity = resources.client().target("/test-entity").path(path).request().get(Entity.class);

        Response response = resources.target("/test-entity/" + path).request().get();
        Assert.assertEquals(302, response.getStatus());
    }

    @Test
    public void createEntity() {
        Response post = resources.target("/test-entity").request().post(javax.ws.rs.client.Entity.json(entity));
        Assert.assertEquals(201, post.getStatus());
    }

    @Test
    public void updateTest() {
        Response updated = resources.target("/test-entity/" + path).request().put(javax.ws.rs.client.Entity.json(new Entity("updated")));
        Assert.assertEquals(200, updated.getStatus());
    }
}
