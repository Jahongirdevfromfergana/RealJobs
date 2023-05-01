package uz.fergana.developer.screen.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import uz.fergana.developer.R
import uz.fergana.developer.databinding.ActivityMainBinding
import uz.fergana.developer.screen.main.favorites.FavoritesFragment
import uz.fergana.developer.screen.main.home.HomeFragment
import uz.fergana.developer.screen.main.map.MapFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var homeFragment = HomeFragment.newInstance()
    var mapFragment = MapFragment.newInstance()
    var favoritesFragment = FavoritesFragment.newInstance()
    var activeFragment: Fragment = homeFragment

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.container, homeFragment)
            .addToBackStack(null).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, mapFragment)
            .addToBackStack(null).hide(mapFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, favoritesFragment)
            .addToBackStack(null).hide(favoritesFragment).commit()
        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        binding.navBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(homeFragment).addToBackStack(null).commit()
                    activeFragment = homeFragment
                }
                R.id.mapFragment -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(mapFragment)
                        .addToBackStack(null).commit()
                    activeFragment = mapFragment
                }
                R.id.favoritesFragment -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(favoritesFragment).addToBackStack(null).commit()
                    activeFragment = favoritesFragment
                    favoritesFragment.loadData()
                }
            }

            return@setOnItemSelectedListener true
        }

        binding.imgMenu.setOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)
        }
    }

}
