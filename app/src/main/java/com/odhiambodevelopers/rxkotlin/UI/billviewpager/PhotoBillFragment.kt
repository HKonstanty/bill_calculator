package com.odhiambodevelopers.rxkotlin.UI.billviewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odhiambodevelopers.rxkotlin.R
import com.odhiambodevelopers.rxkotlin.databinding.FragmentPhotoBillBinding


class PhotoBillFragment : Fragment() {

    private var _binding: FragmentPhotoBillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoBillBinding.inflate(layoutInflater, container, false)
        return return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}