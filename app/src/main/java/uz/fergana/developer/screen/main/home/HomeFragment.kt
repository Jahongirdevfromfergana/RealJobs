package uz.fergana.developer.screen.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import uz.fergana.developer.MyApp
import uz.fergana.developer.databinding.FragmentHomeBinding
import uz.fergana.developer.model.CategoryModel
import uz.fergana.developer.view.adapter.CategoriesAdapter
import uz.fergana.developer.view.adapter.CategoriesAdapterCallback
import uz.fergana.developer.view.adapter.EmployeesAdapter

class HomeFragment: Fragment(), CategoriesAdapterCallback {
    lateinit var binding: FragmentHomeBinding

    companion object{
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerCategories.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerCategories.adapter = CategoriesAdapter(MyApp.categories, this)

        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.recycler.adapter = EmployeesAdapter(MyApp.employees)
    }
    override fun onClickItem(item: CategoryModel) {
        binding.recycler.adapter = EmployeesAdapter(if(item.id == 0) MyApp.employees else MyApp.employees.filter { it.category_id == item.id })
    }

}