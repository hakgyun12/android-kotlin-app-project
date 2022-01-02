package org.techtown.notesapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.launch
import org.techtown.notesapp.database.NotesDatabase
import org.techtown.notesapp.databinding.FragmentCreateNoteBinding
import org.techtown.notesapp.entities.Notes
import org.techtown.notesapp.util.NoteBottomSheetFragment
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    // 노트마다 색상을 구별하기 위한 variable 변수
    var selectedColor = "#171C26"

    // 유저가 현재 작성한 시간을 나타내기 위한 variable 변수
    var currentDate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )
        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
        val currentDate  = sdf.format(Date())
        _binding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))

        _binding!!.tvDateTime.text = currentDate

        _binding!!.imgDone.setOnClickListener {
            //saveNote
            saveNote()
            Log.d("TEST", "true")
        }

        _binding!!.imgBack.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
            //requireActivity().supportFragmentManager.popBackStack()
        }

        _binding!!.imgMore.setOnClickListener {
            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance()
            noteBottomSheetFragment.show(requireActivity().supportFragmentManager, "Note Bottom Sheet Fragment")
        }
    }

    private fun saveNote(){
        if(_binding!!.etNoteTitle.text.isNullOrEmpty()){
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }
        if(_binding!!.etNoteSubTitle.text.isNullOrEmpty()){
            Toast.makeText(context, "Note Sub Title Required", Toast.LENGTH_SHORT).show()
        }
        if(_binding!!.etNoteDesc.text.isNullOrEmpty()){
            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val notes = Notes()
            notes.title = _binding!!.etNoteTitle.text.toString()
            notes.subTitle = _binding!!.etNoteSubTitle.text.toString()
            notes.noteText = _binding!!.etNoteDesc.text.toString()
            notes.dateTime = currentDate
            notes.color = selectedColor

            Log.d("TEST", notes.title!!)
            Log.d("TEST", notes.subTitle!!)
            Log.d("TEST", notes.noteText!!)

            context?.let{
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                _binding!!.etNoteTitle.setText("")
                _binding!!.etNoteSubTitle.setText("")
                _binding!!.etNoteDesc.setText("")

            }
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

    private val BroadcastReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            var actionColor = p1!!.getStringExtra("actionColor")

            when(actionColor!!){
                "Blue" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    _binding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Yellow" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    _binding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Purple" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    _binding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Green" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    _binding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Orange" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    _binding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Black" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    _binding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                else -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    _binding!!.colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

            }
        }
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }
}