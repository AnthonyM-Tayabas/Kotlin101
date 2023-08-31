package csv.masters.kotlin101.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import csv.masters.kotlin101.data.api.RetrofitClient
import csv.masters.kotlin101.data.api.UserInterface
import csv.masters.kotlin101.databinding.ActivityMainBinding
import csv.masters.kotlin101.recyclerview.MyMotivation
import csv.masters.kotlin101.retrofit.RetrofitActivity
import csv.masters.kotlin101.second.SecondActivity
import csv.masters.kotlin101.second.SecondActivity.Companion.EXTRA_REPLY
import csv.masters.kotlin101.third.ThirdActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

// A: Implementing Click Listener via Inheritance
//class MainActivity : AppCompatActivity(), View.OnClickListener {
class MainActivity : AppCompatActivity() {

    private val STATE_MESSAGE = "STATE_MESSAGE"

    private lateinit var binding: ActivityMainBinding

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkHardwarePermission()

        // A: Implementing Click Listener via Inheritance
//        button.setOnClickListener(this)

        // B: Layout and Java Class binding without using view binding feature
//        setContentView(R.layout.activity_main)
//
//        val textView = findViewById<TextView>(R.id.tvMyText)
//        textView = findViewById<TextView>(R.id.tvMyText)
//        val button = findViewById<Button>(R.id.btClick)
//
//        Log.d(MainActivity::class.java.simpleName, "Button instance: $button")
//
//        button.setOnClickListener {
//            textView.text = "Kotlin Masters 101"
//        }
//
//                binding.btClick.setOnClickListener {
//            binding.tvMyText.text = "Kotlin Masters 101 With View Binding"
//        }

        // C: Layout and Java Class binding using view binding feature
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Log.d("Lifecycle", "${MainActivity::class.java.simpleName} onCreate()")

        savedInstanceState?.let { savedBundle ->
            binding.tvMyText.text = savedBundle.getString(STATE_MESSAGE, "Hello World")
        }

        Log.d(MainActivity::class.java.simpleName, "This is debug")
        Log.e(MainActivity::class.java.simpleName, "This is error")
        Log.w(MainActivity::class.java.simpleName, "This is warning")
        Log.i(MainActivity::class.java.simpleName, "This is info")
        Log.v(MainActivity::class.java.simpleName, "This is verbose")

        with(binding) {
            btClick.setOnClickListener {
                tvMyText.text = "Event Handler 101 With View Binding"
            }

            // Opening a new activity using explicit intent
            btOpenActivity?.setOnClickListener {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)

                if (binding.etDataForNextPage?.text.toString().isNotEmpty()) {
                    // Adding data that will be passed to your next activity
                    intent.putExtra(SecondActivity.EXTRA_MESSAGE, binding.etDataForNextPage?.text.toString())
                }

                startActivity(intent)
            }

            btOpenThird?.setOnClickListener {
                startActivity(Intent(this@MainActivity, ThirdActivity::class.java))
            }

            // Opening a new activity expecting a result from second page
            btOpenActivityForResults?.setOnClickListener {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra(SecondActivity.EXTRA_MESSAGE, "First page needs your reply")
                startForResult.launch(intent)
                val intentForThirdActivity = Intent(this@MainActivity, ThirdActivity::class.java)
                startForResultOnPage3.launch(intentForThirdActivity)
                //startActivityForResult()
            }

            // Implicit intent to open URL via device's default browser app
            btOpenInBrowser?.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https:${etData?.text}")
                }
                startActivity(intent)
            }

            // Implicit intent to open URL via device's default phone app
            btDialNumber?.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${etData?.text}")
                }
                startActivity(intent)
            }

            btOpenRecyclerView?.setOnClickListener {
                val intent = Intent(this@MainActivity, MyMotivation::class.java)
                startActivity(intent)
            }

            btOpenRetrofit?.setOnClickListener {
                startActivity(Intent(this@MainActivity, RetrofitActivity::class.java))
            }

            btMainThread?.setOnClickListener {
                factorialOnMainThread(binding.etInput?.text.toString().toInt())
            }

            btCoroutine?.setOnClickListener {
                factorialOnCoroutine(binding.etInput?.text.toString().toInt())
            }

        }
    }

    private fun checkHardwarePermission() {
        if(applicationContext.packageManager.hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY
        )) {
            Toast.makeText(this@MainActivity, "With Camera", Toast.LENGTH_SHORT).show()

            when {
                ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(this@MainActivity, "Thank you for granting access", Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(CAMERA) -> {
                    Toast.makeText(this@MainActivity, "This makes us sad", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Individual permission request
//                    requestPermissionLauncher.launch(
//                        Manifest.permission.CAMERA
//                    )

                    requestPermissions(
                        arrayOf(CAMERA),
                        REQ_CAMERA
                    )
                }
            }



        } else {
            Toast.makeText(this@MainActivity, "Without Camera", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQ_CAMERA -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "Thank you for the access", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Why?", Toast.LENGTH_SHORT).show()
                }

            }
            else -> {

            }
        }
    }

    private fun factorialOnMainThread(input: Int) {
        var factorial = BigInteger.ONE

        for (i in 1 .. input) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }

        Log.d("FACTORIAL", "Output: $factorial")
        binding.tvOutput?.text = factorial.toString()
    }

    private fun factorialOnCoroutine(input: Int) {
        lifecycleScope.launch {
            withContext(Dispatchers.Default) {
                var factorial = BigInteger.ONE

                for (i in 1 .. input) {
                    factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
                }

                Log.d("FACTORIAL", "Output: $factorial")

                withContext(Dispatchers.Main) {
                    binding.tvOutput?.text = factorial.toString()
                }

            }
        }
    }

    companion object {
        private const val CAMERA = Manifest.permission.CAMERA
        private const val REQ_CAMERA = 1
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "${MainActivity::class.java.simpleName} onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "${MainActivity::class.java.simpleName} onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "${MainActivity::class.java.simpleName} onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "${MainActivity::class.java.simpleName} onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Lifecycle", "${MainActivity::class.java.simpleName} onRestart()")
    }

    // A: Implementing Click Listener via Inheritance
//    override fun onClick(p0: View?) {
//        textView.text = "Kotlin Masters 101 From Inheritance"
//    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.let { intent ->
                binding.tvResponse?.text = intent.getStringExtra(EXTRA_REPLY)
            }
        }
    }

    private val startForResultOnPage3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.let { intent ->
                binding.tvResponse?.text = intent.getStringExtra(EXTRA_REPLY)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Toast.makeText(this@MainActivity, "RESULT: Thank you for granting access", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "RESULT: This makes us sad", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_MESSAGE, binding.tvMyText.text.toString())
        super.onSaveInstanceState(outState)
    }
}