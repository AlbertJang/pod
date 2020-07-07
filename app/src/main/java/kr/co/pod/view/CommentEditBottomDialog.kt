package kr.co.pod.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_edit_comment.*
import kr.co.pod.R


class CommentEditBottomDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_edit_comment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    override fun onResume() {
        super.onResume()

        commentEditText.post{
            commentEditText.requestFocus()

            val inputMethodManager: InputMethodManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(commentEditText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    companion object {
        const val TAG = "CommentEditBottomDialog"
    }
}