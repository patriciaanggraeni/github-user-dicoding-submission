package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.util.FunctionHelper

class MainActivity : AppCompatActivity(), FunctionHelper {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment:NavHostFragment
    private lateinit var appBar: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment= supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBar = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navigation = findNavController(R.id.fragmentContainerView)
        return navigation.navigateUp(appBar) ||super.onSupportNavigateUp()
    }
}