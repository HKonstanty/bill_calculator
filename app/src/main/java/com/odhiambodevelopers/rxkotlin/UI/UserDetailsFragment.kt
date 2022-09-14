package com.odhiambodevelopers.rxkotlin.UI

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.odhiambodevelopers.rxkotlin.MainActivity
import com.odhiambodevelopers.rxkotlin.R
import com.odhiambodevelopers.rxkotlin.database.AppDatabase
import com.odhiambodevelopers.rxkotlin.database.models.User
import com.odhiambodevelopers.rxkotlin.databinding.FragmentUserDetailsBinding
import com.odhiambodevelopers.rxkotlin.repository.UserRepository
import kotlinx.coroutines.launch

class UserDetailsFragment : Fragment() {

    private val TAG = "UserDetailsFragment"
    var user: User? = null
    ///var userId: Long = -1
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private var isEditing = false
    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            this,
            UserRepository(AppDatabase.getInstance(requireContext()).userDao),
            requireActivity().intent?.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailsBinding.inflate(layoutInflater)

        viewModel.userName.observe(viewLifecycleOwner, Observer<String> {
            (binding.userNameEt as TextView).text = it ?: "error"
        })
        viewModel.userSurname.observe(viewLifecycleOwner, Observer<String> {
            (binding.userSurnameEt as TextView).text = it ?: "error"
        })
        viewModel.userEmail.observe(viewLifecycleOwner, Observer<String> {
            (binding.userEmailEt as TextView).text = it ?: "error"
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.editBtn.setOnClickListener { edit() }
        binding.deleteBtn.setOnClickListener { delete() }
//        binding.saveBtn.setOnClickListener { save() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_details_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_save)
        item.isVisible = isEditing
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                Toast.makeText(requireContext(), "Action save", Toast.LENGTH_SHORT).show()
                save()
                return true
            }
            R.id.action_edit -> {
                Toast.makeText(requireContext(), "Action edit", Toast.LENGTH_SHORT).show()
                edit()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun edit() {
//        binding.deleteBtn.isVisible = false
//        binding.saveBtn.isVisible = true
//        binding.userNameEt.isEnabled = true
//        binding.userSurnameEt.isEnabled = true
//        binding.userEmailEt.isEnabled = true
        isEditing = !isEditing
        binding.userNameEt.isEnabled = isEditing
        binding.userSurnameEt.isEnabled = isEditing
        binding.userEmailEt.isEnabled = isEditing
        requireActivity().invalidateOptionsMenu()
    }

    private fun delete() {
        viewModel.delete()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    private fun save() {
        viewModel.save(
            binding.userNameEt.text.toString(),
            binding.userSurnameEt.text.toString(),
            binding.userEmailEt.text.toString()
        )
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }



}