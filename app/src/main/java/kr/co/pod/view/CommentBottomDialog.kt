package kr.co.pod.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_comment.*
import kr.co.pod.R

class CommentBottomDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_comment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureListener()
    }

    private fun configureListener() {
        commentEditLayout?.setOnClickListener {
            showEditCommentBottomFragment()
        }
    }

    private fun showEditCommentBottomFragment() {
        val commentEditBottomDialog = CommentEditBottomDialog()
        commentEditBottomDialog.show(requireActivity().supportFragmentManager, CommentEditBottomDialog.TAG)
    }

    companion object {
        const val TAG = "CommentBottomDialog"
    }
}