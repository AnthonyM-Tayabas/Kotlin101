package csv.masters.kotlin101.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import csv.masters.kotlin101.databinding.ActivityMainBinding

// A: Implementing Click Listener via Inheritance
//class MainActivity : AppCompatActivity(), View.OnClickListener {
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        with(binding) {
            btClick.setOnClickListener {
                tvMyText.text = "Kotlin Masters 101 With View Binding"
            }
        }

        // A: Implementing Click Listener via Inheritance
//        button.setOnClickListener(this)
    }

    // A: Implementing Click Listener via Inheritance
//    override fun onClick(p0: View?) {
//        textView.text = "Kotlin Masters 101 From Inheritance"
//    }
}