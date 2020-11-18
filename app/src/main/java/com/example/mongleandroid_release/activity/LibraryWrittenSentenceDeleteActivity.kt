package com.example.mongleandroid_release.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.dialog.DialogChangePassword
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.customEnqueue
import com.example.mongleandroid_release.network.data.request.RequestChangePasswordData
import com.example.mongleandroid_release.network.data.response.ResponseDeleteSentenceWritten
import com.example.mongleandroid_release.network.data.response.ResponseSearchRecentData
import com.example.mongleandroid_release.network.data.response.ResponseSearchRecentDeleteData
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_library_written_sentence_delete.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryWrittenSentenceDeleteActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_written_sentence_delete)



        tv_delete_yes.setOnClickListener{

            //삭제히기
            deleteLibraryWrittenSentence()
            finish()

        }

        tv_delete_no.setOnClickListener {
            finish()
        }
    }

    private fun deleteLibraryWrittenSentence() {

        requestToServer.service.DeleteSentenceWritten(
            token = this.let{ SharedPreferenceController.getAccessToken(it) },
            params = intent.getIntExtra("param", 0)

        ).enqueue(
            object : Callback<ResponseDeleteSentenceWritten> {
                override fun onResponse(
                    call: Call<ResponseDeleteSentenceWritten>,
                    response: Response<ResponseDeleteSentenceWritten>
                ) {
                    if (response.isSuccessful) {

                    }
                }

                override fun onFailure(call: Call<ResponseDeleteSentenceWritten>, t: Throwable) {
                    Log.d("내 서재 쓴 문장 삭제 통신 실패", "$t")

                }

            }
        )
    }
}