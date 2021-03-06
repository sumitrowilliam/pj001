package com.example.williamsumitro.dress.view.presenter.api;

import com.example.williamsumitro.dress.view.model.AddToBag;
import com.example.williamsumitro.dress.view.model.AdvancedSearchResult;
import com.example.williamsumitro.dress.view.model.AllStoreResponse;
import com.example.williamsumitro.dress.view.model.ApproveOrder;
import com.example.williamsumitro.dress.view.model.BagResponse;
import com.example.williamsumitro.dress.view.model.BestResponse;
import com.example.williamsumitro.dress.view.model.Checkout;
import com.example.williamsumitro.dress.view.model.CheckoutResponse;
import com.example.williamsumitro.dress.view.model.CityResponse;
import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.model.DashboardResponse;
import com.example.williamsumitro.dress.view.model.DownlinePartnershipResponse;
import com.example.williamsumitro.dress.view.model.FavoriteResponse;
import com.example.williamsumitro.dress.view.model.FilterProductStore;
import com.example.williamsumitro.dress.view.model.FinancialHistoryResponse;
import com.example.williamsumitro.dress.view.model.NotificationResponse;
import com.example.williamsumitro.dress.view.model.OfferHistoryResponse;
import com.example.williamsumitro.dress.view.model.PartnershipResponse;
import com.example.williamsumitro.dress.view.model.PaymentResponse;
import com.example.williamsumitro.dress.view.model.ProductResponse;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResponse;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResponse;
import com.example.williamsumitro.dress.view.model.Purchase_RejectResponse;
import com.example.williamsumitro.dress.view.model.Purchase_ReviewRatingResponse;
import com.example.williamsumitro.dress.view.model.Purchase_TransactionHistoryResponse;
import com.example.williamsumitro.dress.view.model.RFQResponse;
import com.example.williamsumitro.dress.view.model.RFQ_ActiveResponse;
import com.example.williamsumitro.dress.view.model.RFQ_HistoryResponse;
import com.example.williamsumitro.dress.view.model.Sales_OrderResponse;
import com.example.williamsumitro.dress.view.model.SortByIdResult;
import com.example.williamsumitro.dress.view.model.SortByIdStoreResult;
import com.example.williamsumitro.dress.view.model.StoreDetailResponse;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.model.SubmitReviewRating;
import com.example.williamsumitro.dress.view.model.UplinePartnershipResponse;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.model.WishlistResponse;
import com.example.williamsumitro.dress.view.model.dress_attribute.DressAttribute;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by WilliamSumitro on 05/04/2018.
 */

public interface apiService {
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> req_register(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("full_name") String fullname,
                                    @Field("gender") String gender,
                                    @Field("phone_number") String phonenumber);
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> req_login(@Field("email") String email,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("get_auth_user")
    Call<UserResponse> req_get_auth_user(@Field("token") String token);

    @FormUrlEncoded
    @POST("check_store_name")
    Call<ResponseBody> req_check_store(@Field("store_name") String store_name);

    @FormUrlEncoded
    @POST("register_store_name")
    Call<ResponseBody> req_register_store_name(@Field("token") String token,
                                               @Field("store_name") String storename);


    @POST("get_province_list")
    Call<ProvinceResponse> req_get_province_list();


    @POST("get_courier_list")
    Call<CourierResponse> req_get_courier_list();


    @POST("get_dress_attributes")
    Call<DressAttribute> req_get_dress_attributes();

    @FormUrlEncoded
    @POST("get_city_by_province")
    Call<CityResponse> req_get_city(@Field("province_id") String province_id);

    @FormUrlEncoded
    @POST("get_user_store")
    Call<StoreResponse> req_get_user_store(@Field("token") String token);

    @Multipart
    @POST("add_product")
    Call<ResponseBody> req_add_product(
            @Part("token") RequestBody token,
            @Part("name") RequestBody name,
            @Part("min_order") RequestBody min_order,
            @Part("weight") RequestBody weight,
            @Part("description") RequestBody description,
            @Part("style_id") RequestBody style_id,
            @Part("season_id") RequestBody season_id,
            @Part("neckline_id") RequestBody neckline_id,
            @Part("sleevelength_id") RequestBody sleevelength_id,
            @Part("waiseline_id") RequestBody waiseline_id,
            @Part("material_id") RequestBody material_id,
            @Part("fabrictype_id") RequestBody fabrictype_id,
            @Part("decoration_id") RequestBody decoration_id,
            @Part("patterntype_id") RequestBody patterntype_id,
            @Part MultipartBody.Part[] size,
            @Part MultipartBody.Part[] price,
            @Part MultipartBody.Part photo
    );
    @Multipart
    @POST("register_store")
    Call<ResponseBody> req_register_store(
            @Part("token") RequestBody token,
            @Part("store_name") RequestBody store_name,
            @Part MultipartBody.Part photo,
            @Part MultipartBody.Part banner,
            @Part("description") RequestBody description,
            @Part("established_year") RequestBody established_year,
            @Part("province") RequestBody province,
            @Part("city") RequestBody city,
            @Part("business_type") RequestBody business_type,
            @Part("contact_person_name") RequestBody contact_person_name,
            @Part("contact_person_job_title") RequestBody contact_person_job_title,
            @Part("contact_person_phone_number") RequestBody contact_person_phone_number,
            @Part MultipartBody.Part[] courier,
            @Part MultipartBody.Part ktp,
            @Part MultipartBody.Part siup,
            @Part MultipartBody.Part npwp,
            @Part MultipartBody.Part skdp,
            @Part MultipartBody.Part tdp
    );

    @FormUrlEncoded
    @POST("get_product_detail")
    Call<ProductResponse> req_get_product_detail(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("get_product_detail")
    Call<ProductResponse> req_get_product_detail(@Field("token") String token,
                                                 @Field("product_id") String product_id);

    @POST("add_to_bag")
    Call<ResponseBody> req_add_to_bag(@Body AddToBag addToBag);


    @FormUrlEncoded
    @POST("view_shopping_bag")
    Call<BagResponse> req_view_shopping_bag(@Field("token") String token);

    @FormUrlEncoded
    @POST("get_notification")
    Call<NotificationResponse> req_view_notification(@Field("token") String token);

    @FormUrlEncoded
    @POST("delete_product_from_bag")
    Call<ResponseBody> req_delete_product(@Field("token") String token,
                                          @Field("product_id") String product_id);


    @FormUrlEncoded
    @POST("get_checkout_info")
    Call<CheckoutResponse> req_get_checkout_info(@Field("token") String token,
                                              @Field("destination_city") String destination_city);

    @POST("checkout")
    Call<PaymentResponse> req_checkout(@Body Checkout checkout_courier);

    @FormUrlEncoded
    @POST("get_purchase_payment")
    Call<Purchase_PaymentResponse> req_get_purchase_payment(@Field("token") String token);

    @FormUrlEncoded
    @POST("get_order_status")
    Call<Purchase_OrderResponse> req_get_purchase_orderstatu(@Field("token") String token);

    @FormUrlEncoded
    @POST("confirm_payment")
    Call<ResponseBody> req_confirm_payment(@Field("token") String token,
                                           @Field("transaction_id") String transaction_id,
                                           @Field("date") String date,
                                           @Field("company_bank_id") String company_bank_id,
                                           @Field("amount") String amount,
                                           @Field("sender_bank") String sender_bank,
                                           @Field("sender_account_number") String sender_account_number,
                                           @Field("sender_name") String sender_name,
                                           @Field("note") String note);


    @FormUrlEncoded
    @POST("seller_get_shipping_confirmation")
    Call<Sales_OrderResponse> req_seller_get_shipping(@Field("token") String token);

    @FormUrlEncoded
    @POST("seller_get_order")
    Call<Sales_OrderResponse> req_seller_get_order(@Field("token") String token);

    @POST("approve_order_product")
    Call<ResponseBody> req_approve_order_product(@Body ApproveOrder approveOrder);

    @FormUrlEncoded
    @POST("input_receipt_number")
    Call<ResponseBody> req_input_receipt_number(@Field("token") String token,
                                                @Field("transaction_id") String transaction_id,
                                                @Field("store_id") String store_id,
                                                @Field("receipt_number") String receipt_number);

    @FormUrlEncoded
    @POST("finish_shipping")
    Call<ResponseBody> req_finish_shippin(@Field("token") String token,
                                                @Field("transaction_id") String transaction_id,
                                                @Field("store_id") String store_id);

    @FormUrlEncoded
    @POST("get_receipt_confirmation")
    Call<Sales_OrderResponse> req_get_receipt_confirmation(@Field("token") String token);

    @FormUrlEncoded
    @POST("confirm_receipt")
    Call<ResponseBody> req_confirm_receipt(@Field("token") String token,
                                          @Field("transaction_id") String transaction_id,
                                          @Field("store_id") String store_id);

    @FormUrlEncoded
    @POST("add_to_wishlist")
    Call<ResponseBody> req_add_to_wishlist(@Field("token") String token,
                                           @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("delete_from_wishlist")
    Call<ResponseBody> req_delete_from_wishlist(@Field("token") String token,
                                                @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("my_wishlist")
    Call<WishlistResponse> req_get_my_wishlist(@Field("token") String token);

    @FormUrlEncoded
    @POST("withdraw")
    Call<ResponseBody> req_withdraw(@Field("token") String token,
                                    @Field("amount") String amount,
                                    @Field("bank_name") String bank_name,
                                    @Field("branch") String branch,
                                    @Field("account_number") String account_number,
                                    @Field("name_in_account") String name_in_account,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("get_review_rating")
    Call<Purchase_ReviewRatingResponse> req_get_review_rating(@Field("token") String token);

    @POST("submit_review_rating")
    Call<ResponseBody> req_submit_review_rating(@Body SubmitReviewRating submitReviewRating);

    @FormUrlEncoded
    @POST("transaction_history")
    Call<Purchase_TransactionHistoryResponse> req_transaction_history(@Field("token") String token);

    @FormUrlEncoded
    @POST("get_request_partnership")
    Call<UplinePartnershipResponse> req_get_request_partnership(@Field("token") String token);

    @Multipart
    @POST("submit_request_partnership")
    Call<ResponseBody> req_submit_request_partnership(
            @Part("token") RequestBody token,
            @Part("product_id") RequestBody product_id,
            @Part("min_order") RequestBody min_order,
            @Part MultipartBody.Part[] price
    );

    @FormUrlEncoded
    @POST("upline_get_request_partnership")
    Call<DownlinePartnershipResponse> req_upline_get_request_partnership(@Field("token") String token);

    @FormUrlEncoded
    @POST("accept_partnership")
    Call<ResponseBody> req_accept_partnership(@Field("token") String token,
                                              @Field("partnership_id") String partnership_id);

    @FormUrlEncoded
    @POST("reject_partnership")
    Call<ResponseBody> req_reject_partnership(@Field("token") String token,
                                              @Field("partnership_id") String partnership_id);


    @FormUrlEncoded
    @POST("upline_partner_list")
    Call<PartnershipResponse> req_upline_partner_list(@Field("token") String token);


    @FormUrlEncoded
    @POST("downline_partner_list")
    Call<PartnershipResponse> req_downline_partner_list(@Field("token") String token);

    @FormUrlEncoded
    @POST("get_store_detail")
    Call<StoreDetailResponse> req_get_store_detail(@Field("store_id") String store_id);

    @FormUrlEncoded
    @POST("get_user_store_detail")
    Call<StoreDetailResponse> req_get_user_store_detail(@Field("token") String token,
                                                        @Field("store_id") String store_id);

    @Multipart
    @POST("add_rfq_request")
    Call<ResponseBody> req_add_rfq_request(
            @Part("token") RequestBody token,
            @Part("item_name") RequestBody item_name,
            @Part("description") RequestBody description,
            @Part("qty") RequestBody qty,
            @Part("request_expired") RequestBody request_expired,
            @Part("budget_unit_min") RequestBody budget_unit_min,
            @Part("budget_unit_max") RequestBody budget_unit_max,
            @Part MultipartBody.Part photo
    );

    @FormUrlEncoded
    @POST("seller_get_rfq_request")
    Call<RFQResponse> req_get_rfq_request(@Field("token") String token);

    @Multipart
    @POST("add_rfq_offer")
    Call<ResponseBody> req_add_rfq_offer(
            @Part("token") RequestBody token,
            @Part("rfq_request_id") RequestBody rfq_request_id,
            @Part("description") RequestBody description,
            @Part("price_unit") RequestBody price_unit,
            @Part("weight_unit") RequestBody weight_unit,
            @Part MultipartBody.Part photo
    );

    @FormUrlEncoded
    @POST("view_active_rfq_request")
    Call<RFQ_ActiveResponse> req_get_active_rfq_request(@Field("token") String token);

    @FormUrlEncoded
    @POST("accept_rfq_offer")
    Call<ResponseBody> req_accept_rfq_offer(@Field("token") String token,
                                            @Field("rfq_offer_id") String rfq_offer_id);

    @FormUrlEncoded
    @POST("close_rfq_request")
    Call<ResponseBody> req_close_rfq_request(@Field("token") String token,
                                             @Field("rfq_request_id") String rfq_request_id);

    @FormUrlEncoded
    @POST("rfq_request_history")
    Call<RFQ_HistoryResponse> req_rfq_history(@Field("token") String token);

    @FormUrlEncoded
    @POST("financial_history")
    Call<FinancialHistoryResponse> req_financial_history(@Field("token") String token,
                                                         @Field("year") String year,
                                                         @Field("month") String month);

    @FormUrlEncoded
    @POST("rfq_offer_history")
    Call<OfferHistoryResponse> req_offer_history(@Field("token") String token);

    @POST("get_new_product_detail")
    Call<BestResponse> req_get_new_product();

    @POST("best_seller_product_detail")
    Call<BestResponse> req_get_best_seller();

    @FormUrlEncoded
    @POST("search")
    Call<BestResponse> req_search(@Field("product_name") String product_name);


    @FormUrlEncoded
    @POST("add_to_favorite")
    Call<ResponseBody> req_add_to_favorite(@Field("token") String token,
                                           @Field("store_id") String store_id);


    @FormUrlEncoded
    @POST("delete_from_favorite")
    Call<ResponseBody> req_delete_from_favorite(@Field("token") String token,
                                           @Field("store_id") String store_id);

    @FormUrlEncoded
    @POST("my_favorite")
    Call<FavoriteResponse> req_my_favorite(@Field("token") String token);

    @FormUrlEncoded
    @POST("advance_search")
    Call<AdvancedSearchResult> req_advance_search(@Field("min_order") String min_order,
                                                  @Field("rating_min") String rating_min,
                                                  @Field("rating_max") String rating_max,
                                                  @Field("province") String province,
                                                  @Field("city") String city,
                                                  @Field("courier_id") String courier_id,
                                                  @Field("price_min") String price_min,
                                                  @Field("price_max") String price_max);

    @FormUrlEncoded
    @POST("get_sort_by_id")
    Call<SortByIdResult> req_get_sort_by_id(@Field("sort_id") String sort_id);


    @Multipart
    @POST("update_store_information")
    Call<ResponseBody> req_update_store_information(
            @Part("token") RequestBody token,
            @Part("store_id") RequestBody store_id,
            @Part("name") RequestBody name,
            @Part("business_type") RequestBody business_type,
            @Part("established_year") RequestBody established_year,
            @Part("province") RequestBody province,
            @Part("city") RequestBody city,
            @Part("contact_person_name") RequestBody contact_person_name,
            @Part("contact_person_job_title") RequestBody contact_person_job_title,
            @Part("contact_person_phone_number") RequestBody contact_person_phone_number,
            @Part MultipartBody.Part photo,
            @Part MultipartBody.Part banner,
            @Part("description") RequestBody description
    );


    @Multipart
    @POST("update_store_document")
    Call<ResponseBody> req_update_store_legaldoc(
            @Part("token") RequestBody token,
            @Part("store_id") RequestBody store_id,
            @Part("store_name") RequestBody store_name,
            @Part MultipartBody.Part ktp,
            @Part MultipartBody.Part siup,
            @Part MultipartBody.Part npwp,
            @Part MultipartBody.Part skdp,
            @Part MultipartBody.Part tdp
    );

    @FormUrlEncoded
    @POST("update_user_profile")
    Call<ResponseBody> req_update_user_profile(@Field("token") String token,
                                                  @Field("full_name") String full_name,
                                                  @Field("phone_number") String phone_number);


    @Multipart
    @POST("update_user_image")
    Call<ResponseBody> req_update_user_image(
            @Part("token") RequestBody token,
            @Part MultipartBody.Part avatar
    );

    @FormUrlEncoded
    @POST("update_user_password")
    Call<ResponseBody> req_update_user_password(@Field("token") String token,
                                               @Field("password") String password,
                                               @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("delete_user_store_courier")
    Call<ResponseBody> req_delete_user_store_courier(@Field("token") String token,
                                               @Field("store_id") String store_id,
                                               @Field("courier_id") String courier_id);


    @FormUrlEncoded
    @POST("insert_user_store_courier")
    Call<ResponseBody> req_insert_user_store_courier(@Field("token") String token,
                                               @Field("store_id") String store_id,
                                               @Field("courier_id") String courier_id);


    @POST("get_all_store")
    Call<AllStoreResponse> req_get_all_store();


    @FormUrlEncoded
    @POST("get_sort_by_id_store")
    Call<SortByIdStoreResult> req_get_sort_by_id_store(@Field("store_id") String store_id, @Field("token") String token, @Field("sort_id") String sort_id);

    @FormUrlEncoded
    @POST("get_product_by_style")
    Call<BestResponse> req_get_product_by_style(@Field("style_id") String style_id);

    @FormUrlEncoded
    @POST("delete_all_product_from_bag")
    Call<ResponseBody> req_delete_all_product_from_bag(@Field("token") String token);

    @FormUrlEncoded
    @POST("dashboard")
    Call<DashboardResponse> req_dashboard(@Field("token") String token,
                                          @Field("type") String type);

    @FormUrlEncoded
    @POST("delete_product")
    Call<ResponseBody> req_deleteproduct(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("filter_product_store")
    Call<FilterProductStore> req_filter_product_store(@Field("min_order") String min_order,
                                                @Field("rating_min") String rating_min,
                                                @Field("rating_max") String rating_max,
                                                @Field("store_id") String store_id,
                                                @Field("price_min") String price_min,
                                                @Field("price_max") String price_max);

    @FormUrlEncoded
    @POST("report_product")
    Call<FilterProductStore> req_reportproduct(@Field("token") String token,
                                                      @Field("product_id") String product_id,
                                                      @Field("issue") String issue,
                                                      @Field("comment") String comment);

    @FormUrlEncoded
    @POST("read_notification")
    Call<ResponseBody> req_read_notification(@Field("token") String token,
                                               @Field("notification_id") String notification_id);

    @FormUrlEncoded
    @POST("reject_payment_history")
    Call<Purchase_RejectResponse> req_reject(@Field("token") String token);

}
