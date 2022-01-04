package org.techtown.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment.newInstance(), true)
    }

    fun replaceFragment(fragment: Fragment, istransition: Boolean){
        /**
         * 프래그먼트 매니저를 선언 해준 뒤에 beginTransition()를 호출함으로써 프래그먼트 트랜잭션 작업을 시작할 수 있다.
         * 이후에 프래그먼트에 대한 각종 트랜잭션 작업들(추가, 삭제, 교체)등과 애니메이션, 프래그먼트 백스택 등을 추가해준 뒤에 commit()을 호출하여
         * 트랜잭션을 마무리하게 해줍니다. 이 때 주의해야 할점은 반드시 commit()을 호출해 주어야만 transaction 작업을 정상적으로 수행할 수 있다.
         * */
        val fragmentTransition = supportFragmentManager.beginTransaction()

        if(istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        /**
         * replace + addToBackStack()
         * 화면에 프래그먼트 1개만을 보여줄 때 사용한다. 프래그먼트는 stack에 계속 쌓이지만 화면에 중첩되어 보여지지는 않는다.
         * 새로운 프래그먼트가 replace될 때 이전에 생성된 프래그먼트는 onDestroyView가 됩니다. 뒤로가기를 하게 되면 이전에 생성된 프래그먼트들이
         * 순서대로 onCreateView를 실행하며 재사용됩니다.
         * */

        fragmentTransition.add(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragments = supportFragmentManager.fragments
        if (fragments.size == 0){
            finish()
        }
    }

}