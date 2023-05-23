package com.example.cryproapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.cryproapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = navHostFragment!!.findNavController()


        val popupMenu = PopupMenu(this,binding.bottomBar)
        popupMenu.inflate(R.menu.bottom_nav_menu8)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)
    }
}