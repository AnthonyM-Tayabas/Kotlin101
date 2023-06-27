package csv.masters.kotlin101.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import csv.masters.kotlin101.databinding.ActivityMainBinding
import csv.masters.kotlin101.second.SecondActivity
import csv.masters.kotlin101.second.SecondActivity.Companion.EXTRA_REPLY

// A: Implementing Click Listener via Inheritance
//class MainActivity : AppCompatActivity(), View.OnClickListener {
class MainActivity : AppCompatActivity() {

    private val STATE_MESSAGE = "STATE_MESSAGE"

    private lateinit var binding: ActivityMainBinding

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        savedInstanceState?.let { savedBundle ->
            binding.tvMyText.text = savedBundle.getString(STATE_MESSAGE, "Hello World")
        }

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

            // Opening a new activity expecting a result from second page
            btOpenActivityForResults?.setOnClickListener {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra(SecondActivity.EXTRA_MESSAGE, "First page needs your reply")
                startForResult.launch(intent)
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
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_MESSAGE, binding.tvMyText.text.toString())
        super.onSaveInstanceState(outState)
    }
}