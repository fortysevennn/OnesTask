package com.tugaypamuk.app.onestask.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.tugaypamuk.app.onestask.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var toolbar : MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        setUpNavigationGraph()
        setUpNavigationGraph()


    }

    private fun setUpNavigationGraph(){
       setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this,navController)
        toolbar.setupWithNavController(navController)

    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
          menuInflater.inflate(R.menu.toolbar_menu,menu)
        when(navController.currentDestination?.id){
            R.id.fragmentLogs2 -> {
                menu?.findItem(R.id.fragmentLogs)?.isVisible = false
                menu?.findItem(R.id.fragmentSettings)?.isVisible = false
            }
            R.id.fragmentSettings2 -> {
                menu?.findItem(R.id.fragmentLogs)?.isVisible = false
                menu?.findItem(R.id.fragmentSettings)?.isVisible = false
            }
        }
        return true
    }
}