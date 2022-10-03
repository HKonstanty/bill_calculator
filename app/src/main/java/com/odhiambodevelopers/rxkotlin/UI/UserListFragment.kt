package com.odhiambodevelopers.rxkotlin.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.odhiambodevelopers.rxkotlin.UserDetailsActivity
import com.odhiambodevelopers.rxkotlin.database.AppDatabase
import com.odhiambodevelopers.rxkotlin.database.UserDao
import com.odhiambodevelopers.rxkotlin.databinding.FragmentUserListBinding
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class UserListFragment : Fragment() {

    companion object {
        const val USER_ID_KEY = "user_id"
        const val USER_KEY = "user"
    }

    private val TAG = "UserListFragment"
//    public val USER_ID_KEY = "user_id"
//    val USER_KEY = "user"
    private var columnCount = 1
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private val adapter by lazy { UserRecyclerViewAdapter() }
//    private lateinit var adapter : UserRecyclerViewAdapter
    private val compositeDisposable = CompositeDisposable()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(layoutInflater)

        database = AppDatabase.getInstance(requireContext())
        userDao = database.userDao

        //display data
        val display = dataDisplay(requireContext()).subscribe()
        compositeDisposable.add(display)
//        dataDisplay(requireContext()).subscribe()


//        adapter = UserRecyclerViewAdapter()
//        adapter.onItemClick = {
//            Toast.makeText(requireContext(), "Clicked ${it.userName}", Toast.LENGTH_SHORT).show()
//        }
        adapter.onItemClick = {
            //Toast.makeText(requireContext(), "Clicked ${it.userName}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Item clicked ${it.userName}")
            val intent = Intent(requireContext(), UserDetailsActivity::class.java).apply {
                putExtra(USER_KEY, it)
                putExtra(USER_ID_KEY, it.userId)
            }
            startActivity(intent)
        }

        return binding.root
    }

    private fun dataDisplay(context: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>>(){

            //creating and submiting list to the recyclerview
            val myList = userDao.getAllUsers()
            adapter.submitList(myList)
            binding.list.adapter = adapter

        }.toFlowable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
            }
            .doOnError {
                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}