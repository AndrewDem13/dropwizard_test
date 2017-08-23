package integration;

import com.softserveinc.dropwizard_test.App;
import com.softserveinc.dropwizard_test.AppConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

public class IntegrationTest {
    @Rule
    public final DropwizardAppRule<AppConfiguration> rule = new DropwizardAppRule<AppConfiguration>(App.class, new AppConfiguration());

    @Test
    public void runTest() {
        Client client = new JerseyClientBuilder().build();
        Response response = client.target(String.format(("http://localhost:%d/test-entity"), rule.getLocalPort())).request().get();
        Assert.assertEquals(200, response.getStatus());
    }
}
