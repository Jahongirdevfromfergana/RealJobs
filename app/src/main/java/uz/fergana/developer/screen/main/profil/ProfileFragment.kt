package uz.fergana.developer.screen.main.profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.fergana.developer.databinding.FragmentProfileBinding


class ProfileFragment: Fragment() {
    lateinit var binding: FragmentProfileBinding
    companion object{
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}