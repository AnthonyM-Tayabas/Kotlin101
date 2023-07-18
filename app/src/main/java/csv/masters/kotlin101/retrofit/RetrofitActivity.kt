package csv.masters.kotlin101.retrofit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import csv.masters.kotlin101.data.api.RetrofitClient
import csv.masters.kotlin101.data.api.UserInterface
import csv.masters.kotlin101.databinding.ActivityRetrofitBinding
import kotlinx.coroutines.launch

class RetrofitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetrofitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAllUsers()

        Toast.makeText(this@RetrofitActivity, "Welcome to Retrofit Sample", Toast.LENGTH_SHORT).show()
    }

    private fun getAllUsers() {
        val retrofitClient = RetrofitClient.getInstance()
        val userService = retrofitClient.create(UserInterface::class.java)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                val response = userService.getAllUsers()

                if (response.isSuccessful) {
                    response.body()?.let {
                        val userAdapter = UserAdapter(it.data)
                        userAdapter.setOnClickListener { selectedUser ->
                            Toast.makeText(this@RetrofitActivity, selectedUser.fullName(), Toast.LENGTH_SHORT).show()
                        }

                        binding.rvUsers.apply {
                            adapter = userAdapter
                            layoutManager = LinearLayoutManager(this@RetrofitActivity)
                            setHasFixedSize(true)
                        }
                    }
                }

                Log.d("API Response", "$response")
            }
        }

    }
}