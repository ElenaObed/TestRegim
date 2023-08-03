import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator(){
    }
    private static void sendRequest(RegistrationDto user){
        // сам запрос
        given()
                .spec(requestSpec)
                .body(new RegistrationDto("vasya", "password", "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin (){
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPassword(){
        String password = faker.internet().password();
        return password;
    }
    public static RegistrationDto getUser(String status) {
        RegistrationDto user = new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        return user;
    }
    public static RegistrationDto getRegisteredUser(String status) {
        RegistrationDto registeredUser = getUser(status);
        sendRequest(registeredUser);
        return registeredUser;
    }
    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }

}
