package csv.masters.kotlin101.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import csv.masters.kotlin101.databinding.ItemAffirmationBinding
import csv.masters.kotlin101.model.Affirmation

class AffirmationAdapterBinding(
    private val context: Context,
    private val dataset: List<Affirmation>
    ): RecyclerView.Adapter<AffirmationAdapterBinding.AffirmationViewHolder>() {

    inner class AffirmationViewHolder(val binding: ItemAffirmationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AffirmationViewHolder {
        val binding = ItemAffirmationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AffirmationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: AffirmationViewHolder, position: Int) {
        val item = dataset[position]
        holder.itemView.apply {
            with(holder.binding) {
                tvTitle.text = context.resources.getString(item.stringResourceId)
                ivIcon.setImageResource(item.imageResourceId)
            }
            setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    private var onItemClickListener: ((Affirmation) -> Unit)? = null

    fun setOnItemClickListener(listener: (Affirmation) -> Unit) {
        onItemClickListener = listener
    }

}