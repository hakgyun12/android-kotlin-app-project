package org.techtown.notesapp

import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class CreateNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    // 노트마다 색상을 구별하기 위한 variable 변수
    var selectedColor = "#171C26"

    // 유저가 현재 작성한 시간을 나타내기 위한 variable 변수
    var currentDate: String? = null

    // 유저의 Storage 허가를 받기 위한 변수
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    private var selectedImagePath = ""


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
            //replaceFragment(HomeFragment.newInstance(), false)
            requireActivity().supportFragmentManager.popBackStack()
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
            notes.imgPath = selectedImagePath

            context?.let{
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                _binding!!.etNoteTitle.setText("")
                _binding!!.etNoteSubTitle.setText("")
                _binding!!.etNoteDesc.setText("")
                _binding!!.imgNote.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
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
            var actionColor = p1!!.getStringExtra("action")

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

                "Image" -> {
                    readStorageTask()
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

    private fun hasReadStoragePerm(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun readStorageTask(){
        if(hasReadStoragePerm()){
            pickImageFromGallery()
        }else{
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.storage_permission_text),
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE

            )
        }
    }

    private fun pickImageFromGallery(){
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if(intent.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(intent, REQUEST_CODE_IMAGE)
        }
    }

    private fun getPathFromUri(contentUri: Uri): String?{
        var filePath:String? = null
        var cursor = requireActivity().contentResolver.query(contentUri, null, null, null, null)
        if(cursor == null){
            filePath = contentUri.path
        }else{
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                var selectedImageUrl = data.data
                if (selectedImageUrl != null){
                    try {
                        var inputStream = requireActivity().contentResolver.openInputStream(selectedImageUrl)
                        var bitmap = BitmapFactory.decodeStream(inputStream)
                        _binding!!.imgNote.setImageBitmap(bitmap)
                        _binding!!.imgNote.visibility = View.VISIBLE

                        selectedImagePath = getPathFromUri(selectedImageUrl)!!
                    }catch (e: Exception){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, requireActivity())
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)){
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onRationaleAccepted(requestCode: Int) {
    }

    override fun onRationaleDenied(requestCode: Int) {
    }
}