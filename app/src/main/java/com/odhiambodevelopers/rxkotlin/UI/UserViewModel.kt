package com.odhiambodevelopers.rxkotlin.UI

 import android.os.Bundle
 import android.util.Log
 import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
 import com.odhiambodevelopers.rxkotlin.database.models.User
 import com.odhiambodevelopers.rxkotlin.repository.UserRepository
 import kotlinx.coroutines.Dispatchers
 import kotlinx.coroutines.launch
 import kotlinx.coroutines.withContext

class UserViewModel(private val userRepo: UserRepository, private val savedStateHandle: SavedStateHandle) : ViewModel(), LifecycleObserver {

    private val TAG = "UserViewModel"
    private var _userName = MutableLiveData("Loading")
    val userName: LiveData<String>
        get() = _userName
    private var _userSurname = MutableLiveData("Loading")
    val userSurname: LiveData<String>
        get() = _userSurname
    private var _userEmail = MutableLiveData("Loading")
    val userEmail: LiveData<String>
        get() = _userEmail
    private lateinit var user: User

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = savedStateHandle.getLiveData<Long>(UserListFragment.USER_ID_KEY)
            user = userRepo.getUserWithId(userId.value ?: -1)
            Log.d(TAG, "user with id ${user.userId}")
            withContext(Dispatchers.Main) {
                _userName.value = user.userName
                _userSurname.value = user.userSurname
                _userEmail.value = user.userEmail
            }

        }
    }

    fun delete() {
        Log.d(TAG, "deleting user with id ${user.userId}")
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.deleteUser(user)
        }
    }

    fun save(userName: String, userSurname: String, userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            user.userName = userName
            user.userSurname = userSurname
            user.userEmail = userEmail
            userRepo.updateUser(user)
        }
    }
}

class UserViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val dependency: UserRepository,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return UserViewModel(dependency, handle) as T
    }
}