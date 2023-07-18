package csv.masters.kotlin101.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import csv.masters.kotlin101.data.UserResponse
import csv.masters.kotlin101.databinding.ItemUserBinding

class UserAdapter(private val users: List<UserResponse.User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.itemView.apply {
            with(holder.binding) {
                tvFullName.text = user.fullName()
                tvEmail.text = user.email

                Glide.with(this@apply)
                    .load(user.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
                    .into(ivAvatar)
            }

            setOnClickListener {
                onClickListener?.let { it(user) }
            }
        }
    }

    private var onClickListener: ((UserResponse.User) -> Unit)? = null

    fun setOnClickListener(listener: (UserResponse.User) -> Unit) {
        onClickListener = listener
    }


}