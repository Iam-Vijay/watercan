package com.watercanedelivery.app.webservice


import com.watercanedelivery.app.data.*
import com.watercanedelivery.app.data.cutomer.CustomerListRespDTO
import com.watercanedelivery.app.data.cutomer.UpdateCustomerDTO
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Karthikeyan on 24-02-2021.
 */
interface APIServices {
    //login
    @FormUrlEncoded
    @POST("login")
    fun loginRequest(
        @Field("username") userName: String,
        @Field("password") password: String
    ): Deferred<Response<LoginResponseDTO>>


    @GET("customers")
    fun customerRequest(): Deferred<Response<CustomerListRespDTO>>

    @GET("brand")
    fun getBrandRequest(): Deferred<Response<BrandResponseDTO>>


    @POST("update_order")
    fun updateOrder(@Body updateCustomerDTO: UpdateCustomerDTO): Deferred<Response<CommonResponseDTO>>

    @FormUrlEncoded
    @POST("expenses")
    fun getExpensesList(@Field("staff_id") staff_id: String): Deferred<Response<StaffResponseDTO>>

    @FormUrlEncoded
    @POST("view_order")
    fun viewCustomer(@Field("id") id: String): Deferred<Response<ViewCustomerDTO>>


    @FormUrlEncoded
    @POST("add_expenses")
    fun addExpenses(
        @Field("staff_id") staff_id: String,
        @Field("remarks") remarks: String,
        @Field("amount") amount: String, @Field("entry_date") entryDate: String
    ): Deferred<Response<CommonResponseDTO>>

    @FormUrlEncoded
    @POST("add_staff_allowance")
    fun addVechAllow(
        @Field("staff_id") staff_id: String,
        @Field("start_km") startKm: String,
        @Field("end_km") endKm: String, @Field("entry_date") entryDate: String
    ): Deferred<Response<CommonResponseDTO>>


    @FormUrlEncoded
    @POST("staff_allowance")
    fun vechicleAllowanceList(@Field("staff_id") staff_id: String): Deferred<Response<VechicelAllowance>>

    @FormUrlEncoded
    @POST("home_screen")
    fun homeScreen(@Field("staff_id") staff_id: String): Deferred<Response<HomeResponseModelDTO>>

    @FormUrlEncoded
    @POST("forget_password")
    fun forgotPassword(@Field("mobile_no") mobile_no: String):Deferred<Response<CommonResponseDTO>>

    @FormUrlEncoded
    @POST("reset_code_check")
    fun resetCode(@Field("mobile_no") mobile_no: String, @Field("code") code: String):Deferred<Response<CommonResponseDTO>>

    @FormUrlEncoded
    @POST("set_password")
    fun changePassword(@Field("staff_id") staff_id: String, @Field("password") password: String):Deferred<Response<CommonResponseDTO>>
    /*register api*/
    /* @POST("users/register")
     fun registerRequest(@Body registerParamDTO: ParamModel): Deferred<Response<RegisterResponseDTO>>

     //login
     @POST("users/login")
     fun loginRequest(@Body loginParamDTO: LoginParamDTO): Deferred<Response<LoginResponseDTO>>

     //forgot password
     @POST("users/forgotpassword")
     fun forgotPasswordRequest(@Body forgotParamDTO: ParamModel): Deferred<Response<CommonResponseDTO>>

     //get the fridge recipe list
     @POST("recipes/fridgerecipes")
     suspend fun fridgeRecipe(@Header("Access-Token") token: String): Deferred<Response<FridgeRecipeResponseDTO>>

     //get the fav list
     @GET("Users/getfavorite/{user_id}/{extended}/{next_page}")
     fun favRecipe(
         @Header("Access-Token") accessToken: String,
         @Path("user_id") userId: Int,
         @Path("extended") status: Boolean,
         @Path("next_page") nextPage: Int
     ): Deferred<Response<FavRecipeResponseDTO>>

     //get the feature recipe list
     @GET("recipes/featuredRecipes")
     fun featureRecipe(@Header("Access-Token") accessToken: String): Deferred<Response<FeatureRecipeResponseDTO>>


     //add/remove the recipe on fav
     @POST("Users/favourite")
     fun addFav(
         @Header("Access-Token") accessToken: String,
         @Body addRemoveFavDTO: AddRemoveFavDTO
     ): Deferred<Response<CommonResponseDTO>>


     @GET("Compartments/getcompartments/{user_id}")
     fun myFridgeListRequest(
         @Header("Content-Type") content_type: String?, @Path("user_id") id: Int,
         @Header("Access-Token") token: String?
     ): Deferred<Response<MyFridgeListResponseDTO>>


     @POST("recipes/recipeInfo")
     fun recipeDetails(
         @Header("Access-Token") token: String?,
         @Body recipeDetailParam: RecipeDetailParamDTO
     ): Deferred<Response<RecipeInfoResponseModel>>*/
}