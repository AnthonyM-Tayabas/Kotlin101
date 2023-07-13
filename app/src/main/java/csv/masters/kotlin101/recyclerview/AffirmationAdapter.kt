package csv.masters.kotlin101.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import csv.masters.kotlin101.R
import csv.masters.kotlin101.model.Affirmation

class AffirmationAdapter (
    private val context: Context,
    private val dataset: List<Affirmation>
): RecyclerView.Adapter<AffirmationAdapter.AffirmationViewHolder>()
{
    class AffirmationViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.tvTitle)
        val imageView: ImageView = view.findViewById(R.id.ivIcon)
        val cvAffirmation: CardView = view.findViewById(R.id.cvAffirmation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AffirmationViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.item_affirmation, parent, false
        )
        return AffirmationViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: AffirmationViewHolder, position: Int) {
        val item = dataset[position]
//        if (item.trueOrFalse) {
            //holder.checkbox
//        }
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
        holder.cvAffirmation.setOnClickListener {
            //network call - eeeeennnggggk
            Toast.makeText(context, context.getString(item.stringResourceId), Toast.LENGTH_SHORT).show()
        }
    }
}