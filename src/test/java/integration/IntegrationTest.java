package integration;

import com.softserveinc.dropwizard_test.App;
import com.softserveinc.dropwizard_test.AppConfiguration;
import com.softserveinc.dropwizard_test.entity.Entity;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {

    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-config.yml");

    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE = new DropwizardAppRule<>(App.class, CONFIG_PATH);

    private static MongodExecutable mongodExe;
    private static MongodProcess mongod;

    private final Entity entity = new Entity(1, "test");

    @BeforeClass
    public static void setUp() throws Exception {
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        mongodExe = runtime
                    .prepare(new MongodConfigBuilder()
                            .version(Version.Main.PRODUCTION)
                            .net(new Net(RULE.getConfiguration().mongohost, RULE.getConfiguration().mongoport, Network.localhostIsIPv6()))
                            .build());
        mongod = mongodExe.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (mongod != null) {
            mongod.stop();
            mongodExe.stop();
        }
    }


    @Test
    public void _01create() {
        Entity newEntity = RULE.client()
                .target(String.format(("http://localhost:%d/entity"), RULE.getLocalPort()))
                .request()
                .post(javax.ws.rs.client.Entity.json(entity))
                .readEntity(Entity.class);
        Assert.assertEquals(newEntity, entity);
    }

    @Test
    public void _02read() {
        Entity readEntity = RULE.client()
                .target(String.format(("http://localhost:%d/entity/%s"), RULE.getLocalPort(), String.valueOf(entity.getId())))
                .request()
                .get()
                .readEntity(Entity.class);
        Assert.assertEquals(entity, readEntity);
    }

    @Test
    public void _03readAll() {
        List<Entity> entities = RULE.client()
                .target(String.format(("http://localhost:%d/entity/"), RULE.getLocalPort()))
                .request()
                .get()
                .readEntity(new GenericType<List<Entity>>(){});

        Assert.assertNotNull(entities);
        Assert.assertEquals(entity, entities.get(0));
    }

    @Test
    public void _04update() {
        Entity newEntity = new Entity(entity.getId(), "updated");
        Entity updated = RULE.client()
                .target(String.format(("http://localhost:%d/entity/%s"), RULE.getLocalPort(), String.valueOf(entity.getId())))
                .request()
                .put(javax.ws.rs.client.Entity.json(newEntity))
                .readEntity(Entity.class);

        Assert.assertEquals(newEntity, updated);
        Assert.assertEquals(entity.getId(), updated.getId());
    }

    @Test
    public void _05delete() {
        Response response = RULE.client()
                .target(String.format(("http://localhost:%d/entity/%s"), RULE.getLocalPort(), String.valueOf(entity.getId())))
                .request()
                .delete();
        Assert.assertEquals(200, response.getStatus());

        Response readResponse = RULE.client()
                .target(String.format(("http://localhost:%d/entity/%s"), RULE.getLocalPort(), String.valueOf(entity.getId())))
                .request()
                .get();
        Assert.assertEquals(404, readResponse.getStatus());
    }
}
