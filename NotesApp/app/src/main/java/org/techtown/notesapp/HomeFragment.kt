package org.techtown.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import org.techtown.notesapp.adapter.NotesAdapter
import org.techtown.notesapp.database.NotesDatabase
import org.techtown.notesapp.entities.Notes
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment() {

    var arrNotes = java.util.ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()

    /**
     * Fragment가 생성될때 호출되는 부분
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    /**
     * onCreate 후에 화면을 구성할 때 호출되는 부분이다.
     * 쉽게 생각하면 onCreateView부분에 Activity에 onCreate 메소드 안에서 사용한 코드를 적으면 된다.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    /**
     * onViewCreated 메소드는 onCreateView에서 return해준 view를 가지고 있다. 따라서
     * onCreate는 객체 생성이후 호출, onCreateView에서 inflater를 이용하여 view를 불러와 view를 리턴해서
     * onViewCreated는 이제 view.findViewById를 통해 사용하는것으로 설계되어 있다.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.setHasFixedSize(true)

        recycler_view.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter!!.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                recycler_view.adapter = notesAdapter
            }
        }

        notesAdapter!!.setOnClickListener(onClicked)

        fabBtnCreateNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(),false)
        }

        search_view.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                var tempArr = ArrayList<Notes>()

                for (arr in arrNotes){
                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())){
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
                return true
            }

        })
    }

    private val onClicked = object :NotesAdapter.OnItemClickListener{
        override fun onClicked(notesId: Int) {

            var fragment :Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",notesId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment,false)
        }
    }

    fun replaceFragment(fragment: Fragment, istransition: Boolean){
        /**
         * 프래그먼트 매니저를 선언 해준 뒤에 beginTransition()를 호출함으로써 프래그먼트 트랜잭션 작업을 시작할 수 있다.
         * 이후에 프래그먼트에 대한 각종 트랜잭션 작업들(추가, 삭제, 교체)등과 애니메이션, 프래그먼트 백스택 등을 추가해준 뒤에 commit()을 호출하여
         * 트랜잭션을 마무리하게 해줍니다. 이 때 주의해야 할점은 반드시 commit()을 호출해 주어야만 transaction 작업을 정상적으로 수행할 수 있다.
         * */
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

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
}