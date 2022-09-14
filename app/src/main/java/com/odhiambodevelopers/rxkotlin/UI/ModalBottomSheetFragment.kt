package com.odhiambodevelopers.rxkotlin.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.odhiambodevelopers.rxkotlin.AddBillActivity
import com.odhiambodevelopers.rxkotlin.AddUserActivity
import com.odhiambodevelopers.rxkotlin.databinding.FragmentModalBottomSheetBinding

class ModalBottomSheetFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentModalBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModalBottomSheetBinding.inflate(inflater, container, false)

        binding.addUserLayout.setOnClickListener { startAddUserActivity() }
        binding.addBillLayout.setOnClickListener { startAddBillActivity() }

        return binding.root
    }

    private fun startAddUserActivity() {
        Toast.makeText(requireContext(), "Add user clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(activity, AddUserActivity::class.java)
        startActivity(intent)
        dismiss()
    }

    private fun startAddBillActivity() {
        Toast.makeText(requireContext(), "Add bill clicked", Toast.LENGTH_SHORT).show()
        Intent(activity, AddBillActivity::class.java).also { startActivity(it) }
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d(TAG, "ModalBottomSheet destroyed")
        dismiss()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}