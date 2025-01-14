package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface SpendApi {

  @POST("/addSpend")
  Call<SpendJson> addSpend(@Body SpendJson spend);

  @GET("/categories")
  Call<List<CategoryJson>> getCategories(@Query("username") String username);

  @POST("/category")
  Call<CategoryJson> addCategory(@Body CategoryJson categoryJson);
}
