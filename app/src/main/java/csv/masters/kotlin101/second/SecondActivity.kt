package csv.masters.kotlin101.second

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import csv.masters.kotlin101.R
import csv.masters.kotlin101.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messageFromFirstActivity = intent.getStringExtra(EXTRA_MESSAGE)

        // Handling passed data from first page
        messageFromFirstActivity?.let { message ->
                binding.tvSecondPage.text = message
        } ?: kotlin.run {
            binding.tvSecondPage.text = "Hello from Second Page"
        }

        // Responding to first page
        binding.btReply.setOnClickListener {
            val intent = Intent().apply {
                putExtra(EXTRA_REPLY, binding.etReplyMessage.text.toString())
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        const val EXTRA_REPLY = "EXTRA_REPLY"
    }
}