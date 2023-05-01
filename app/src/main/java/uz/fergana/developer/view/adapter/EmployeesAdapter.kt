package uz.fergana.developer.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.fergana.developer.databinding.WorkerItemLayyoutBinding
import uz.fergana.developer.model.WorkerModel
import uz.fergana.developer.utils.Prefs


class EmployeesAdapter(val items: List<WorkerModel>) :
    RecyclerView.Adapter<EmployeesAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: WorkerItemLayyoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            WorkerItemLayyoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.imgFavorite.setOnClickListener {
            Prefs.setFavorite(item)
            holder.binding.imgFavorite.setColorFilter(if(Prefs.checkFavorite(item)) Color.RED else Color.GRAY)
        }

        holder.binding.imgFavorite.setColorFilter(if(Prefs.checkFavorite(item)) Color.RED else Color.GRAY)
        holder.binding.tvFullname.text = item.fullname
        holder.binding.tvComment.text = item.comment
        holder.binding.tvCategory.text = item.category_name
        holder.binding.tvLocation.text = item.region_name
        Glide.with(holder.itemView).load(item.image).into(holder.binding.imgAvatar)

    }

    override fun getItemCount(): Int = items.size
}