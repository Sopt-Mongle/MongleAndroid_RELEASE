package com.example.mongleandroid_release.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.mongleandroid_release.R
import com.example.mongleandroid_release.change_gone
import com.example.mongleandroid_release.change_visible
import com.example.mongleandroid_release.network.RequestToServer
import com.example.mongleandroid_release.network.SharedPreferenceController
import com.example.mongleandroid_release.network.data.request.RequestDuplicateData
import com.example.mongleandroid_release.network.data.response.ResponseDuplicateData
import com.example.mongleandroid_release.network.data.response.ResponseMainLibraryData
import com.example.mongleandroid_release.network.data.response.ResponseUpdateProfileData
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class ProfileActivity : AppCompatActivity() {

    private val requestToServer = RequestToServer
    private val PICK_FROM_ALBUM = 100
    private var fileUri : Uri? = null
    private var keywordIndex : Int = 0
    private lateinit var profile_name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        activity_profile_sv.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            change_visible(activity_profile_top_blur)
            change_visible(activity_profile_bottom_blur)

            if(scrollY == 0) {
                change_gone(activity_profile_top_blur)
                change_gone(activity_profile_bottom_blur)
            }
        }

        activity_profile_btn_out.setOnClickListener {
            finish()
        }


        fileUri = SharedPreferenceController.getImage(this)?.toUri()

        // 이미지 둥글게
        activity_profile_img.background = ShapeDrawable(OvalShape())
        activity_profile_img.clipToOutline = true

        requestToServer.service.lookLibraryProfile(
            token = SharedPreferenceController.getAccessToken(this)
        ).enqueue(object : Callback<ResponseMainLibraryData> {
            override fun onResponse(
                call: Call<ResponseMainLibraryData>,
                response: Response<ResponseMainLibraryData>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.data.isNullOrEmpty()) {

                    } else {
                        Glide.with(this@ProfileActivity).load(response.body()!!.data[0].img).into(
                            activity_profile_img
                        )

                        activity_profile_et_nickname.setText(response.body()!!.data[0].name)
                        profile_name = response.body()!!.data[0].name

                        when (response.body()!!.data[0].keyword) {
                            "감성자극" -> {
                                activity_profile_btn1.isChecked = true
                                keywordIndex = 1
                            }
                            "동기부여" -> {
                                activity_profile_btn2.isChecked = true
                                keywordIndex = 2
                            }
                            "자기계발" -> {
                                activity_profile_btn3.isChecked = true
                                keywordIndex = 3
                            }
                            "깊은생각" -> {
                                activity_profile_btn4.isChecked = true
                                keywordIndex = 4
                            }
                            "독서기록" -> {
                                activity_profile_btn5.isChecked = true
                                keywordIndex = 5
                            }
                            "일상문장" -> {
                                activity_profile_btn6.isChecked = true
                                keywordIndex = 6
                            }
                        }

                        activity_profile_et_introduce.setText(response.body()!!.data[0].introduce)

                        // 포커스 해제
                        activity_profile_et_nickname.background = resources.getDrawable(
                            R.drawable.et_area,
                            null
                        )
                        change_gone(activity_profile_btn_nickname_erase)
                        change_gone(activity_profile_tv_nickname_cnt)
                        change_gone(activity_profile_tv_nickname_cnt_max)

                        activity_profile_et_introduce.background = resources.getDrawable(
                            R.drawable.et_area,
                            null
                        )
                        change_gone(activity_profile_btn_introduce_erase)
                        change_gone(activity_profile_tv_introduce_cnt)
                        change_gone(activity_profile_tv_introduce_cnt_max)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMainLibraryData>, t: Throwable) {
                Log.d("프로필 받아오기 실패", "$t")
            }

        })


        activity_profile_btn1.setOnClickListener {
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIndex = 1
            remove_keyword_warning()
        }

        activity_profile_btn2.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIndex = 2
            remove_keyword_warning()
        }

        activity_profile_btn3.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIndex = 3
            remove_keyword_warning()
        }

        activity_profile_btn4.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn5.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIndex = 4
            remove_keyword_warning()
        }

        activity_profile_btn5.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn6.isChecked = false
            keywordIndex = 5
            remove_keyword_warning()
        }

        activity_profile_btn6.setOnClickListener {
            activity_profile_btn1.isChecked = false
            activity_profile_btn2.isChecked = false
            activity_profile_btn3.isChecked = false
            activity_profile_btn4.isChecked = false
            activity_profile_btn5.isChecked = false
            keywordIndex = 6
            remove_keyword_warning()
        }

        // 닉네임 입력창 리스너
        activity_profile_et_nickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // 경고문구 해제
                activity_profile_et_nickname.background = resources.getDrawable(
                    R.drawable.et_area_green,
                    null
                )
                change_gone(activity_profile_img_nickname_warning)
                change_gone(activity_profile_tv_nickname_warning)
                change_gone(activity_profile_tv_nickname_exist)
                change_gone(activity_profile_tv_nickname_possible)

                // 실시간 글자수
                if (activity_profile_et_nickname.text.isEmpty()) {
                    change_gone(activity_profile_tv_nickname_cnt)
                    change_gone(activity_profile_tv_nickname_cnt_max)
                } else {
                    change_visible(activity_profile_tv_nickname_cnt)
                    change_visible(activity_profile_tv_nickname_cnt_max)
                    val nickname_length = activity_profile_et_nickname.text.length.toString()
                    activity_profile_tv_nickname_cnt.text = nickname_length
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        activity_profile_et_nickname.setOnFocusChangeListener { _, hasFocus ->
            activity_profile_et_nickname.background = resources.getDrawable(
                R.drawable.et_area_green,
                null
            )
            change_gone(activity_profile_img_nickname_warning)
            change_gone(activity_profile_tv_nickname_warning)
            change_gone(activity_profile_tv_nickname_exist)
            change_gone(activity_profile_tv_nickname_possible)

            // erase
            activity_profile_et_nickname.clearText(activity_profile_btn_nickname_erase)

            if(!hasFocus) {
                activity_profile_et_nickname.background = resources.getDrawable(
                    R.drawable.et_area,
                    null
                )
                change_gone(activity_profile_btn_nickname_erase)

                if(profile_name == activity_profile_et_nickname.text.toString()) {

                } else {
                    // 닉네임 중복체크
                    requestToServer.service.requestDuplicate(
                        RequestDuplicateData(
                            email = "닉네임만체크함",
                            name = activity_profile_et_nickname.text.toString()
                        )
                    ).enqueue(object : Callback<ResponseDuplicateData> {
                        override fun onFailure(call: Call<ResponseDuplicateData>, t: Throwable) {
                            Log.d("error", "duplicate / $t")
                        }

                        override fun onResponse(
                            call: Call<ResponseDuplicateData>,
                            response: Response<ResponseDuplicateData>
                        ) {
                            if (response.isSuccessful) {
                                if (response.body()!!.data.duplicate == "name") {
                                    activity_profile_et_nickname.background = resources.getDrawable(
                                        R.drawable.et_area_red,
                                        null
                                    )
                                    change_visible(activity_profile_img_nickname_warning)
                                    activity_profile_img_nickname_warning.setImageResource(R.drawable.ic_warning)
                                    change_visible(activity_profile_tv_nickname_exist)
                                } else {
                                    change_visible(activity_profile_img_nickname_warning)
                                    activity_profile_img_nickname_warning.setImageResource(R.drawable.ic_possible)
                                    change_visible(activity_profile_tv_nickname_possible)
                                }
                            }
                        }
                    })
                }

            }
        }

        // 소개 입력창 리스너
        activity_profile_et_introduce.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 경고문구 해제
                activity_profile_et_introduce.background = resources.getDrawable(
                    R.drawable.et_area_green,
                    null
                )
                change_gone(activity_profile_img_introduce_warning)
                change_gone(activity_profile_tv_introduce_warning)

                // 실시간 글자수
                if (activity_profile_et_introduce.text.isEmpty()) {
                    change_gone(activity_profile_tv_introduce_cnt)
                    change_gone(activity_profile_tv_introduce_cnt_max)
                } else {
                    change_visible(activity_profile_tv_introduce_cnt)
                    change_visible(activity_profile_tv_introduce_cnt_max)
                    val introduce_length = activity_profile_et_introduce.text.length.toString()
                    activity_profile_tv_introduce_cnt.text = introduce_length
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        activity_profile_et_introduce.setOnFocusChangeListener { _, hasFocus ->
            activity_profile_et_introduce.background = resources.getDrawable(
                R.drawable.et_area_green,
                null
            )
            change_gone(activity_profile_img_introduce_warning)
            change_gone(activity_profile_tv_introduce_warning)

            // erase
            activity_profile_et_introduce.clearText(activity_profile_btn_introduce_erase)

            if(!hasFocus) {
                activity_profile_et_introduce.background = resources.getDrawable(
                    R.drawable.et_area,
                    null
                )
                change_gone(activity_profile_btn_introduce_erase)
            }
        }

        activity_profile_btn_camera.setOnClickListener {
            // 권한 요청
            checkPermission()
        }



        // 다음버튼 눌렀을 때 비어있는 칸 경고문구 설정
        activity_profile_btn_next.setOnClickListener {
            if (activity_profile_et_nickname.text.isEmpty()) {
                activity_profile_et_nickname.background = resources.getDrawable(
                    R.drawable.et_area_red, null
                )
                change_visible(activity_profile_img_nickname_warning)
                activity_profile_img_nickname_warning.setImageResource(R.drawable.ic_warning)
                change_visible(activity_profile_tv_nickname_warning)
                change_gone(activity_profile_tv_nickname_exist)
            } else if (keywordIndex == 0) {
                change_visible(activity_profile_img_keyword_warning)
                change_visible(activity_profile_tv_keyword_warning)
            } else if (activity_profile_et_introduce.text.isEmpty()) {
                activity_profile_et_introduce.background = resources.getDrawable(
                    R.drawable.et_area_red, null
                )
                change_visible(activity_profile_img_introduce_warning)
                change_visible(activity_profile_tv_introduce_warning)
            } else {

                settingDataMultiForm()

                // pref에 저장
                SharedPreferenceController.setName(
                    this,
                    activity_profile_et_nickname.text.toString()
                )

            }

        }

    }

    private fun settingDataMultiForm() {

        // multipart로 변환
        val options = BitmapFactory.Options()
        val inputStream: InputStream = contentResolver.openInputStream(fileUri!!)!!
        val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
        val photoBody = RequestBody.create(
            MediaType.parse("image/jpeg"),
            byteArrayOutputStream.toByteArray()
        )
        val picture_rb = MultipartBody.Part.createFormData(
            "img",
            File(fileUri.toString()).name,
            photoBody
        )


        val name = RequestBody.create(
            MediaType.parse("text/plain"),
            activity_profile_et_nickname.text.toString()
        )

        val keywordIdx = RequestBody.create(
            MediaType.parse("text/plain"),
            keywordIndex.toString()
        )

        val introduce = RequestBody.create(
            MediaType.parse("text/plain"),
            activity_profile_et_introduce.text.toString()
        )

        requestToServer.service.updateProfile(
            token = this.let { SharedPreferenceController.getAccessToken(it) },
            img = picture_rb,
            name = name,
            keywordIdx = keywordIdx,
            introduce = introduce
        ).enqueue(object : Callback<ResponseUpdateProfileData> {
            override fun onFailure(call: Call<ResponseUpdateProfileData>, t: Throwable) {
                Log.d("통신실패", "$t")
            }

            override fun onResponse(
                call: Call<ResponseUpdateProfileData>,
                response: Response<ResponseUpdateProfileData>
            ) {
                if (response.isSuccessful) {
                    val customToast = layoutInflater.inflate(R.layout.toast_update_profile, null)
                    val toast = Toast(applicationContext)
                    toast.duration = Toast.LENGTH_SHORT
                    toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                    toast.view = customToast
                    toast.show()
                }
            }

        })
    }

    private fun remove_keyword_warning() {
        change_gone(activity_profile_img_keyword_warning)
        change_gone(activity_profile_tv_keyword_warning)
    }

    // edittext 지우는 x버튼
    private fun EditText.clearText(button: ImageView) {
        change_visible(button)
        button.setOnClickListener {
            this.setText("")
        }
    }

    private fun checkPermission() {
        var temp : String = ""
        if(ContextCompat.checkSelfPermission
                (this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " "
        }

        if(ContextCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " "
        }

        if(!TextUtils.isEmpty(temp)) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" ").toTypedArray(), 1)
        } else {
            // 모두 허용 상태
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, PICK_FROM_ALBUM)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if(requestCode == 1) {
            // 권한을 허용했을 경우
            var length = permissions.size
            for(i in 0 until length) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("권한 허용 : ", permissions[i])

                    // 권한 요청 성공 - 갤러리 이동
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
                    intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    startActivityForResult(intent, PICK_FROM_ALBUM)
                }
            }
        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_FROM_ALBUM) {
            if(resultCode == Activity.RESULT_OK) {

                fileUri = data?.data!!
                Glide.with(this).load(fileUri).into(activity_profile_img)
                SharedPreferenceController.setImage(this, fileUri!!)
            }
        }
    }

}