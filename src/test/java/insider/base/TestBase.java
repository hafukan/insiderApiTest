package insider.base;

import insider.model.Category;
import insider.model.Pet;
import insider.model.Tag;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import java.util.List;

public class TestBase {
        private RequestSpecification given;
        private Pet petRequestObject;

        @BeforeClass
        public void beforeClass() {
            RestAssured.baseURI = "https://petstore.swagger.io";
            RestAssured.basePath = "/v2";
            given = RestAssured.given();
            petRequestObject = Pet.builder()
                    .id(25)
                    .name("Boncuk")
                    .category(
                            Category.builder()
                                    .id(1)
                                    .name("Cat")
                                    .build()
                    ).photoUrls(List.of("photoPath"))
                    .tags(
                            List.of(Tag.builder()
                                    .id(1)
                                    .name("Tüylü")
                                    .build())
                    )
                    .status("status")
                    .build();

        }

        public RequestSpecification getGiven() {
            return given;
        }

        public Pet getPetRequestObject() {
            return petRequestObject;
        }

        public void setPetRequestObject(Pet petRequestObject) {
            this.petRequestObject = petRequestObject;
        }
}
