import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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
        Assertions.assertEquals(6, entries);
    }

    public Response addContactstoJSONServer(Data restAssuredBookData){
        String contact = new Gson().toJson(restAssuredBookData);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type", "application/json");
        requestSpecification.body(contact);
        return requestSpecification.post("/contact");
    }

    @Test
    public void whenNewContactisInsertedShouldReturnResponseCode201() {
        Data[] contactData = getEmployeeDetails();
        Data jsonServerBookData1 = new Data("5", "sudam", "Singh", "Eon park", "pune", "Maharashtra", "400265", "87906543", "ranas@gmail.com");
        Response response = addContactstoJSONServer(jsonServerBookData1);
        int statusCode = response.statusCode();
        Assertions.assertEquals(201, statusCode);
    }

    @Test
    public void givenContacttoUpdateShouldReturnResponseCode200() {
        Data[] ContactData = getEmployeeDetails();
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body("{\"firstname\":\"Akash\",\"lastname\":\"Shevgan\",\"address\":\"manjri\",\"city\":\"pune\",\"state\":\"Maharashtra\",\"zip\":\"400265\",\"phonenumber\":\"789654433\",\"email\":\"akash@gmail.com\"}");
        Response response = requestSpecification.put("/contact");
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(201, statusCode);
    }
}