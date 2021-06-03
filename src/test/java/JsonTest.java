import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class JsonTest {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:3000";
    }
    private Data[] getEmployeeDetails() {
        Response response = RestAssured.get(RestAssured.baseURI + "/contact");
        System.out.println("Employees in JSON Server are: \n" + response.asString());
        return new Gson().fromJson(response.asString(), Data[].class);
    }

    @Test
    void givenContactsInJSONServer_WhenFetched_ShouldMatchCount() {
        Data[] contactData = getEmployeeDetails();
        RestAPI contactRestAPI = new RestAPI(Arrays.asList(contactData));
        long entries = contactRestAPI.countEntries();
        Assertions.assertEquals(2, entries);
    }
}