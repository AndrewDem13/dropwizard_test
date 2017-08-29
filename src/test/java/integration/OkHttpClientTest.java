package integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import okhttp3.*;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OkHttpClientTest {

    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-config.yml");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @ClassRule
    public static final DropwizardAppRule<AppConfiguration> RULE = new DropwizardAppRule<>(App.class, CONFIG_PATH);

    private static MongodExecutable mongodExe;
    private static MongodProcess mongod;

    private final String URL = String.format(("http://localhost:%d/entity/"), RULE.getLocalPort());
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
    public void _01create() throws Exception {
        String json = MAPPER.writeValueAsString(entity);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        Response response = CLIENT.newCall(request).execute();

        Assert.assertEquals(201, response.code());
        Entity createdEntity = MAPPER.readValue(response.body().string(), Entity.class);
        Assert.assertEquals(entity, createdEntity);
    }

    @Test
    public void _02read() throws Exception {
        Request request = new Request.Builder()
                .url(URL + entity.getId())
                .get()
                .build();
        Response response = CLIENT.newCall(request).execute();

        Assert.assertEquals(200, response.code());
        Entity readEntity = MAPPER.readValue(response.body().string(), Entity.class);
        Assert.assertEquals(entity, readEntity);
    }

    @Test
    public void _03readAll() throws Exception {
        Request request = new Request.Builder()
                .url(URL)
                .get()
                .build();
        Response response = CLIENT.newCall(request).execute();

        Assert.assertEquals(200, response.code());
        List readEntities = MAPPER.readValue(response.body().string(), new TypeReference<List<Entity>>(){});
        Assert.assertEquals(1, readEntities.size());
        Assert.assertEquals(entity, readEntities.get(0));
    }

    @Test
    public void _04update() throws Exception {
        Entity newEntity = new Entity(entity.getId(), "updated");
        String json = MAPPER.writeValueAsString(newEntity);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(URL + entity.getId())
                .put(body)
                .build();
        Response response = CLIENT.newCall(request).execute();

        Assert.assertEquals(200, response.code());
        Entity readEntity = MAPPER.readValue(response.body().string(), Entity.class);
        Assert.assertEquals(newEntity, readEntity);
    }

    @Test
    public void _05delete() throws Exception {
        Request request = new Request.Builder()
                .url(URL + entity.getId())
                .delete()
                .build();
        Response response = CLIENT.newCall(request).execute();

        Assert.assertEquals(200, response.code());

        Request getRequest = new Request.Builder()
                .url(URL + entity.getId())
                .get()
                .build();
        Response getResponse = CLIENT.newCall(getRequest).execute();

        Assert.assertEquals(404, getResponse.code());
    }
}
