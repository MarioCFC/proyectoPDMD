package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.LoginAndRegisterActivity
import retrofit2.Response

data class ErrorResponse(
    @SerializedName("status") var status: Int? = null,
    @SerializedName("message") var message: String? = null
) {

    companion object {
        fun gestionarError(responseErrorBodyString:String, activity: AppCompatActivity) {
            var error: ErrorResponse =
                Gson().fromJson(responseErrorBodyString, ErrorResponse::class.java)
            if (error.status == 401) {
                LoginAndRegisterActivity.logOut(activity)
            } else {
                Toast.makeText(
                    activity!!, error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}