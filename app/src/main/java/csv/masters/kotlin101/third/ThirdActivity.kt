package csv.masters.kotlin101.third

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import csv.masters.kotlin101.databinding.ActivityThirdBinding

class ThirdActivity: AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("Lifecycle", "${ThirdActivity::class.java.simpleName} onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "${ThirdActivity::class.java.simpleName} onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "${ThirdActivity::class.java.simpleName} onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "${ThirdActivity::class.java.simpleName} onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "${ThirdActivity::class.java.simpleName} onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "${ThirdActivity::class.java.simpleName} onDestroy()")
    }


}