package fr.sma.sy.tests.unit;


import fr.sma.svc.sy.userservice.model.User;
import fr.sma.sy.mappers.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static  org.hamcrest.MatcherAssert.assertThat;


@Slf4j
public class ObjectMapperTests {

    @Test
    public void mapAllTest() {
    log.info("Conversion");
    String flux = "{\"id\": 1,\"firstName\": \"KARIM\",\"lastName\": \"ZOUBA\",\"email\": \"karim_zouba@groupe-sma.fr\"}";
    User user  = ObjectMapperUtils.mapJson(flux, User.class);
    assertThat(user.getEmail(), containsString("sma"));

    }
}
