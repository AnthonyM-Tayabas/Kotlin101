package csv.masters.kotlin101.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import csv.masters.kotlin101.R
import csv.masters.kotlin101.data.Datasource

class MyMotivation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_motivation)

        val myDataSet = Datasource().loadAffirmations()

        val affirmationAdapter = AffirmationAdapterBinding(this@MyMotivation, myDataSet)
        affirmationAdapter.setOnItemClickListener {
            Toast.makeText(this@MyMotivation, getString(it.stringResourceId), Toast.LENGTH_SHORT).show()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvAffirmation)
        recyclerView.apply {
//            adapter = AffirmationAdapter(this@MyMotivation, myDataSet)
            adapter = affirmationAdapter
//            layoutManager = LinearLayoutManager(this@MyMotivation)

            layoutManager = GridLayoutManager(this@MyMotivation, 2)

            setHasFixedSize(true)
        }
    }
}