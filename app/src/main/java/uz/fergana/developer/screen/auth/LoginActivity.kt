package uz.fergana.developer.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import uz.fergana.developer.R
import uz.fergana.developer.databinding.ActivityLoginBinding
import uz.fergana.developer.model.CategoryModel
import uz.fergana.developer.model.RegionModel
import uz.fergana.developer.screen.MainViewModel
import uz.fergana.developer.screen.main.MainActivity
import uz.fergana.developer.utils.Prefs

class LoginActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityLoginBinding
    var category: CategoryModel? = null
    var mMap: GoogleMap? = null
    var region: RegionModel? = null
    private var state = LoginState.LOGIN
    lateinit var viewModel: MainViewModel


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.progress.observe(this, Observer {
            binding.flProgress.visibility = if(it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_LONG
            ).show()
        })

        viewModel.loginData.observe(this, Observer {
            Prefs.setUser(it)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.registrationData.observe(this, Observer {
            Prefs.setUser(it)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        val findFragmentById = supportFragmentManager.findFragmentById(R.id.map)
        (findFragmentById as SupportMapFragment).getMapAsync(this)
//
//        val phoneListener = MaskedTextChangedListener(
//            "+998 ([00]) [000] [00] [00]",
//            true,
//            binding.edPhone,
//            null,
//            null
//        )
//        binding.edPhone.addTextChangedListener(phoneListener)
//        binding.edPhone.onFocusChangeListener = phoneListener

        binding.btnNext.setOnClickListener {
            when (state) {
                LoginState.LOGIN -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    return@setOnClickListener
                    if (binding.edPhone.text.length < 10 || binding.edPassword.text.length < 2) {
                        Toast.makeText(
                            this,
                            "Iltimos barcha maydonlarni to'ldiring!",
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }
                    viewModel.login(
                        binding.edPhone.text.toString(),
                        binding.edPassword.text.toString()
                    )
                }
                LoginState.REGISTRATION -> {
                    if (binding.edPhone.text.length < 10 || binding.edPassword.text.length < 2 || binding.edFullname.text.length < 2 || (!binding.rbWorker.isChecked && !binding.rbUser.isChecked)) {
                        Toast.makeText(
                            this,
                            "Iltimos barcha maydonlarni to'ldiring!",
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }
                    viewModel.registration(
                        binding.edPhone.text.toString(),
                        binding.edPassword.text.toString(),
                        if (binding.rbWorker.isChecked) "worker" else "user",
                        region?.id ?: 0,
                        binding.edFullname.text.toString(),
                        mMap!!.cameraPosition.target.latitude,
                        mMap!!.cameraPosition.target.longitude,
                        binding.edExprience.text.toString().toIntOrNull() ?: 0,
                        category?.id ?: 0,
                        "",
                        binding.edComment.text.toString()
                    )
                }
            }
        }

        initData()

        viewModel.getFilters()
    }

    fun initData() {
        binding.tilFullname.visibility = View.GONE
        binding.tilRegion.visibility = View.GONE
        binding.rgUserType.visibility = View.GONE
        binding.tilCategory.visibility = View.GONE
        binding.tilExprience.visibility = View.GONE
        binding.tilPassword.visibility = View.GONE
        binding.tilComment.visibility = View.GONE
        binding.cardViewLocation.visibility = View.GONE

        when (state) {
            LoginState.LOGIN -> {
                binding.tilPassword.visibility = View.VISIBLE
                binding.tvSignOrRegistration.text = "Ro'yhatdan o'tish"
                binding.btnNext.text = "Tizimga kirish"
                binding.tvSignOrRegistration.setOnClickListener {
                    state = LoginState.REGISTRATION
                    initData()
                }
            }
            LoginState.REGISTRATION -> {
                binding.tilPassword.visibility = View.VISIBLE
                binding.tilFullname.visibility = View.VISIBLE
                binding.tilRegion.visibility = View.VISIBLE
                binding.rgUserType.visibility = View.VISIBLE

                binding.tvSignOrRegistration.text = "Tizimga kirish"
                binding.btnNext.text = "Ro'yhatdan o'tish"
                binding.tvSignOrRegistration.setOnClickListener {
                    state = LoginState.LOGIN
                    initData()
                }

                binding.edRegion.setOnClickListener {
                    val popupMenu = PopupMenu(this, it)
                    val menu = popupMenu.menu
                    viewModel.regionsData.value?.forEach {
                        menu.add(it.id, it.id, it.id, it.title)
                    }
                    popupMenu.setOnMenuItemClickListener {
                        region =
                            viewModel.regionsData.value?.filter { region -> region.id == it.itemId }
                                ?.firstOrNull()
                        binding.edRegion.setText(region?.title)
                        return@setOnMenuItemClickListener true
                    }
                    popupMenu.show()
                }

                if (binding.rbWorker.isChecked) {
                    binding.tilComment.visibility = View.VISIBLE
                    binding.cardViewLocation.visibility = View.VISIBLE
                    binding.tilCategory.visibility = View.VISIBLE
                    binding.tilExprience.visibility = View.VISIBLE

                    binding.edCategory.setOnClickListener {
                        val popupMenu = PopupMenu(this, it)
                        val menu = popupMenu.menu
                        viewModel.categoriesData.value?.forEach {
                            menu.add(it.id, it.id, it.id, it.title)
                        }
                        popupMenu.setOnMenuItemClickListener {
                            category =
                                viewModel.categoriesData.value?.filter { category -> category.id == it.itemId }
                                    ?.firstOrNull()
                            binding.edCategory.setText(category?.title)
                            return@setOnMenuItemClickListener true
                        }
                        popupMenu.show()
                    }
                }

                binding.rgUserType.setOnCheckedChangeListener { radioGroup, i ->
                    initData()
                }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mMap?.setOnCameraMoveStartedListener {
            binding.cardViewLocation.parent.requestDisallowInterceptTouchEvent(true)

        }

        mMap?.setOnCameraIdleListener {
            binding.cardViewLocation.parent.requestDisallowInterceptTouchEvent(false)

        }
    }
}