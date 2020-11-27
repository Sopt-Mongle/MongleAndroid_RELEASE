package com.mongle.mongleandroid_release.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mongle.mongleandroid_release.R
import com.mongle.mongleandroid_release.change_gone
import com.mongle.mongleandroid_release.change_visible
import kotlinx.android.synthetic.main.activity_service_policy.*

class ServicePolicyActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_policy)

        activity_service_policy_btn_back.setOnClickListener {
            finish()
        }

        activity_service_policy_sv.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            change_visible(activity_service_policy_top_blur)
            if(scrollY == 0) {
                change_gone(activity_service_policy_top_blur)
            }
        }

        textView41.text = """- 불법성 활동
- 불법 사행성, 도박 사이트를 홍보
- 불법 제품 또는 인터넷에서 판매 금지된 물품을
   판매하거나 홍보
- 범법 행위에 대한 동기 부여 및 실행에 도움이 되는 
   정보를 제공
- 악성코드, 바이러스 등의 프로그램을 설치/유포하
   여 고객의 정상적인 서비스 이용을 저해하거나, 고
   객의 개인정보를 탈취하려고하는 경우
- 타인의 저작물을 불법적인 경로로 획득할 수 있는
  정보나 방법을 제공 (예:무료 다운로드, 프리서버, 
  CD키. 공유 등)
- 타인의 권리에 속하는 상표권, 의장권 등을 무단으
   로 침해
- 타인의 개인정보를 포함하고 있는 내용을 작성
- 타인의 개인정보 및 계정, 기기를 도용/탈취하여 
   서비스를 가입하거나, 이용하는 경우
- 음란, 청소년유해 활동
- 과도한 신체 노출이나 음란한 행위를 묘사
- 성매매 관련 정보를 공유
- 타인에게 성적 수치심이나 불쾌감을 유발할 수 
   있는 내용을 작성
- 일반적인 사람이 보기에 혐오스럽고 눈살이 
   찌푸려지는 내용을 작성
- 차별/갈등 조장 활동
- 성별, 종교, 장애, 연령, 사회적 신분, 인종, 지역, 
   직업 등을 차별하거나 이에 대한 편견을 조장하는 
   내용을 작성
- 도배/광고/홍보/스팸 활동
- 동일한 내용을 동일 서비스 또는 여러 서비스에 
   반복적으로 등록 (행운의 글)
- 상업적 또는 홍보/광고, 악의적인 목적으로 서비
  스의 시스템 취약점을 이용하여 서비스를 가입/
  활동  하는 경우
- 계정 거래/양도/대리/교환 활동
- 계정 및 계정 내 컨텐츠를 타인에게  판매, 양도, 
  대여하거나, 타인에게 그 이용을 허락 또는 이를 
  시도하는 행위
- 타인의 계정 및 계정 내 컨텐츠를 취득하기 위해 
  구매, 양수, 교환을 시도하거나, 이를 타인에게 
  알선하는 활동
- 타인을 기망하여 타인의 계정 및 계정 내 컨텐츠를 
  탈취하는 행위
- 기타 서비스에서 금지하는 활동
- 정상적인 서비스 이용으로 볼 수 없는 다량의 계정 
  생성 및 서비스 가입/탈퇴, 반복적 유사 활동
- 서비스 명칭 또는 회사 임직원이나 서비스와 관련한
  운영진을 사칭하여 다른 고객을 속이거나 이득을 취
  하는 등 피해와 혼란을 주는 행위
- 욕설/비속어/은어 등 통상적인 금기어 사용과 그 외
  회사와 고객이 공유하는 상식과 사회 통념에 반하는
  비정상적인 활동"""
    }
}