package fr.sma.sy.tests.integrations;

import fr.sma.svc.sy.userservice.model.ListUserResponse;
import fr.sma.svc.sy.userservice.model.User;
import fr.sma.sy.mappers.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.core.Every;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.containsString;
import static  org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@Slf4j
public class UserControllerTests {

    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    private final static String API_USER_SERVICE = "/users";
    private  ObjectMapperUtils objectMapperUtils;

    @Test
    public void testRetrieveUser() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        log.info("Test testRetrieveUser");
        ResponseEntity<ListUserResponse> response =
        restTemplate.getForEntity(createURLWithPort(API_USER_SERVICE),ListUserResponse.class);
        List<User> listeUtilisateurs = objectMapperUtils.mapAll(response.getBody(), User.class);
        log.info("Résultat appel service :  " + response.getBody());
        log.info("Nombre d'utilisateurs : " + listeUtilisateurs.size());
        assertThat(listeUtilisateurs, (Every.everyItem(HasPropertyWithValue.hasProperty("email", containsString("sma")))));
        // String expected = " [{\"id\":1,\"firstName\":\"KARIM\",\"lastName\":\"ZOUBA\",\"email\":\"karim_zouba@groupe-sma.fr\"}]";
        // JSONAssert.assertEquals(expected, response.getBody(), true);

    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
