package com.examen.hexagonal.infraestructure.rest.retrofit;

import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.infraestructure.response.SunatResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("v2/sunat/ruc/full")
    Call<SunatResponse> findSunat(@Header("Authorization") String token,
                               @Query("numero") String ruc);
}
