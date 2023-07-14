package csv.masters.kotlin101.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import csv.masters.kotlin101.R
import csv.masters.kotlin101.data.Datasource
import csv.masters.kotlin101.databinding.ActivityMyMotivationBinding

class MyMotivation : AppCompatActivity() {

    private lateinit var binding: ActivityMyMotivationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMotivationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myDataSet = Datasource().loadAffirmations()

        val affirmationAdapter = AffirmationAdapterBinding(this@MyMotivation, myDataSet)
        affirmationAdapter.setOnItemClickListener {
            Toast.makeText(this@MyMotivation, getString(it.stringResourceId), Toast.LENGTH_SHORT).show()
        }

//        val recyclerView = findViewById<RecyclerView>(R.id.rvAffirmation)
//        recyclerView.apply {
        binding.rvAffirmation.apply {
//            adapter = AffirmationAdapter(this@MyMotivation, myDataSet)
            adapter = affirmationAdapter
//            layoutManager = LinearLayoutManager(this@MyMotivation)

            layoutManager = GridLayoutManager(this@MyMotivation, 2)

            setHasFixedSize(true)
        }
    }
}