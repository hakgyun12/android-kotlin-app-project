package org.techtown.notesapp.util

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.techtown.notesapp.R
import org.techtown.notesapp.databinding.FragmentCreateNoteBinding
import org.techtown.notesapp.databinding.FragmentNotesBottomSheetBinding

class NoteBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNotesBottomSheetBinding? = null
    private val binding get() = _binding!!

    var selectedColor = "#171C26"

    companion object{
        fun newInstance(): NoteBottomSheetFragment{
            val args = Bundle()
            val fragment = NoteBottomSheetFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet, null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior = param.behavior

        if(behavior is BottomSheetBehavior<*>){
            behavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when(newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    /**
     * create listener function and send broadcast from bottom sheet to fragment
     *
     * dismiss() : dismiss() 메서드는 화면에서 다이얼로그를 종료하는 역할을 하고 스레드 세이프 하다. 스레드 세이프 하다는 것으로 UI 스레드만이 다이얼로그를 종료시킨다는 것도 알 수 있다.
     * */
    private fun setListener(){
        _binding!!.fNote1.setOnClickListener {

            _binding!!.imgNote1.setImageResource(R.drawable.ic_tick)
            _binding!!.imgNote2.setImageResource(0)
            _binding!!.imgNote4.setImageResource(0)
            _binding!!.imgNote5.setImageResource(0)
            _binding!!.imgNote6.setImageResource(0)
            _binding!!.imgNote7.setImageResource(0)
            selectedColor = "#4e33ff"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Blue")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        _binding!!.fNote2.setOnClickListener {

            _binding!!.imgNote1.setImageResource(0)
            _binding!!.imgNote2.setImageResource(R.drawable.ic_tick)
            _binding!!.imgNote4.setImageResource(0)
            _binding!!.imgNote5.setImageResource(0)
            _binding!!.imgNote6.setImageResource(0)
            _binding!!.imgNote7.setImageResource(0)
            selectedColor = "#ffd633"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Yellow")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        _binding!!.fNote4.setOnClickListener {

            _binding!!.imgNote1.setImageResource(0)
            _binding!!.imgNote2.setImageResource(0)
            _binding!!.imgNote4.setImageResource(R.drawable.ic_tick)
            _binding!!.imgNote5.setImageResource(0)
            _binding!!.imgNote6.setImageResource(0)
            _binding!!.imgNote7.setImageResource(0)
            selectedColor = "#ae3b76"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Purple")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        _binding!!.fNote5.setOnClickListener {

            _binding!!.imgNote1.setImageResource(0)
            _binding!!.imgNote2.setImageResource(0)
            _binding!!.imgNote4.setImageResource(0)
            _binding!!.imgNote5.setImageResource(R.drawable.ic_tick)
            _binding!!.imgNote6.setImageResource(0)
            _binding!!.imgNote7.setImageResource(0)
            selectedColor = "#0aebaf"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Green")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        _binding!!.fNote6.setOnClickListener {

            _binding!!.imgNote1.setImageResource(0)
            _binding!!.imgNote2.setImageResource(0)
            _binding!!.imgNote4.setImageResource(0)
            _binding!!.imgNote5.setImageResource(0)
            _binding!!.imgNote6.setImageResource(R.drawable.ic_tick)
            _binding!!.imgNote7.setImageResource(0)
            selectedColor = "#ff7746"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Orange")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        _binding!!.fNote7.setOnClickListener {

            _binding!!.imgNote1.setImageResource(0)
            _binding!!.imgNote2.setImageResource(0)
            _binding!!.imgNote4.setImageResource(0)
            _binding!!.imgNote5.setImageResource(0)
            _binding!!.imgNote6.setImageResource(0)
            _binding!!.imgNote7.setImageResource(R.drawable.ic_tick)
            selectedColor = "#202734"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Black")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        _binding!!.layoutImage.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
    }
}