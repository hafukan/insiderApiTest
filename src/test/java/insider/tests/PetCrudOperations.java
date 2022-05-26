package insider.tests;
import com.google.gson.Gson;
import insider.base.TestBase;
import insider.model.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PetCrudOperations extends TestBase {

    private Integer id;

    @Test(priority = 1)
    public void savePet() {
        Response response = super.getGiven()
                .body(new Gson().toJson(getPetRequestObject()))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post("/pet");
        response.then().log().all();
        response.then().statusCode(200);
        id = response.jsonPath().get("id");
        Assert.assertEquals(id, getPetRequestObject().getId(),"ID's not matched");
        String categoryName = response.jsonPath().get("category.name").toString();
        Assert.assertEquals(categoryName,getPetRequestObject().getCategory().getName(),"Category names not matched");
    }

    @Test(priority = 2,dependsOnMethods = {"savePet"})
    public void getPetById() {
        Response response = super.getGiven().get("/pet/{id}", id);
        response.then().log().all();
        response.then().statusCode(200);
        Integer petId = response.jsonPath().get("id");
        Assert.assertEquals(petId,id,"ID's not matched");
        String categoryName = response.jsonPath().get("category.name").toString();
        Assert.assertEquals(categoryName,getPetRequestObject().getCategory().getName(),"Category names not matched");
    }

    @Test(priority = 3,dependsOnMethods = {"savePet","getPetById"})
    public void getPetByStatus() {
        Response response = super.getGiven().get("pet/findByStatus?status=" + getPetRequestObject().getStatus());
        response.then().log().all();
        response.then().statusCode(200);
    }

    @Test(priority = 4,dependsOnMethods = {"savePet","getPetById","getPetByStatus"})
    public void updatePet() {
        String newPetName = "mavi";
        Pet petRequest = getPetRequestObject();
        petRequest.setName(newPetName);
        setPetRequestObject(petRequest);
        Response response = super.getGiven()
                .body(getPetRequestObject())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .put("/pet");
        response.then().log().all();
        response.then().statusCode(200);
        id = response.jsonPath().get("id");
        Assert.assertEquals(id, getPetRequestObject().getId(),"ID's not matched");
        String checkPetName = response.jsonPath().get("name").toString();
        Assert.assertEquals(checkPetName,newPetName,"Pat names not matched");
    }

    @Test(priority = 5,dependsOnMethods = {"savePet","getPetById","getPetByStatus","updatePet"})
    public void deletePet(){
        Response response = super.getGiven().delete("/pet/{petId}", id);
        response.then().log().all();
        response.then().statusCode(200);
    }
}
