package uz.fergana.developer.screen.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.fergana.developer.databinding.ActivitySplashBinding
import uz.fergana.developer.screen.auth.LoginActivity


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }

}
