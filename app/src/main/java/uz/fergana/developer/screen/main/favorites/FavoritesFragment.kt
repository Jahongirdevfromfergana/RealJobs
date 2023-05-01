package uz.fergana.developer.screen.main.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import uz.fergana.developer.MyApp
import uz.fergana.developer.databinding.FragmentFavoritesBinding
import uz.fergana.developer.utils.Prefs
import uz.fergana.developer.view.adapter.EmployeesAdapter


class FavoritesFragment : Fragment() {
    lateinit var binding: FragmentFavoritesBinding

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.recycler.adapter = EmployeesAdapter(MyApp.employees.filter { worker ->
            Prefs.getFavorites().filter { it == worker.id }.isNotEmpty()
        })
    }

    fun loadData(){
        binding.recycler.adapter = EmployeesAdapter(MyApp.employees.filter { worker ->
            Prefs.getFavorites().filter { it == worker.id }.isNotEmpty()
        })
    }

}