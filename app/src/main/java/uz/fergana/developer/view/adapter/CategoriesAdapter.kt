package uz.fergana.developer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.fergana.developer.R
import uz.fergana.developer.databinding.CategoryItemLayoutBinding
import uz.fergana.developer.model.CategoryModel


interface CategoriesAdapterCallback{
    fun onClickItem(item: CategoryModel)
}

class CategoriesAdapter(val items: List<CategoryModel>, val callback: CategoriesAdapterCallback): RecyclerView.Adapter<CategoriesAdapter.ItemHolder>(){

    inner class ItemHolder(val binding: CategoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            items.forEach {
                it.checked = false
            }
            item.checked = true
            callback.onClickItem(item)
            notifyDataSetChanged()
        }
        holder.binding.tvTitle.text = item.title
        holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, if(item.checked) R.color.colorAccept else R.color.colorPrimary))
    }

    override fun getItemCount(): Int = items.size
}