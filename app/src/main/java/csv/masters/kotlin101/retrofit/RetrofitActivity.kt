package csv.masters.kotlin101.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import csv.masters.kotlin101.data.User
import csv.masters.kotlin101.data.api.RetrofitClient
import csv.masters.kotlin101.data.api.UserInterface
import csv.masters.kotlin101.data.manager.DataStoreManager
import csv.masters.kotlin101.databinding.ActivityRetrofitBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class RetrofitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetrofitBinding
    private lateinit var retrofitClient: Retrofit
    private lateinit var userService: UserInterface
    private lateinit var dataStoreManager: DataStoreManager

    private var userList: java.util.ArrayList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofitClient = RetrofitClient.getInstance()
        userService = retrofitClient.create(UserInterface::class.java)
        dataStoreManager = DataStoreManager(this@RetrofitActivity)

        lifecycleScope.launch {
            userList = dataStoreManager.getObjectList(USER_LIST_KEY, User::class.java)

            if (userList == null || userList!!.isEmpty()) {
                getAllUsers()
            } else {
                setupRecyclerview()
            }

        }

        Toast.makeText(this@RetrofitActivity, "Welcome to Retrofit Sample", Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerview() {
        val userAdapter = UserAdapter(userList!!)
        userAdapter.setOnClickListener { selectedUser ->
            getUserDetails(selectedUser.id)
        }

        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@RetrofitActivity)
            setHasFixedSize(true)
        }
    }

    private fun getAllUsers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                val response = userService.getAllUsers()
                if (response.isSuccessful) {
                    response.body()?.let {
                        userList = it.data as java.util.ArrayList<User>
                        dataStoreManager.putObject(USER_LIST_KEY, it.data, ArrayList::class.java)
                        setupRecyclerview()
                    }
                } else {
                    Log.e("API Failed", response.errorBody().toString())
                }

                Log.d("API Response", "$response")
            }
        }
    }

    private fun getUserDetails(userId: Int) {
        lifecycleScope.launch {
            val response = userService.getUser(userId)

            if (response.isSuccessful) {
                response.body()?.let {
                    Toast.makeText(this@RetrofitActivity, it.data.email, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val USER_LIST_KEY = "USER_LIST_KEY"
    }
}