package com.watercanedelivery.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.watercanedelivery.app.R
import com.watercanedelivery.app.databinding.ActivityMainBinding
import com.watercanedelivery.utils.SharedPreferenceUtil
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var sharedPreferenceUtil: SharedPreferenceUtil
     var activityMainBinding: ActivityMainBinding?=null
    private var navController: NavController? = null
    private var drawerLayout: DrawerLayout? = null
    private var appBarConfiguration: AppBarConfiguration? = null
    var navigationView: NavigationView? = null
    private val TAG = javaClass.simpleName
    private var toolbar: Toolbar? = null
    private var navHostFragment: NavHostFragment? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding?.root)
//        actionBar!!.setDisplayHomeAsUpEnabled(false)


        /*     drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
             navigationView = findViewById<NavigationView>(R.id.nav_view)
             toolbar = findViewById<Toolbar>(R.id.toolbar)
             navHostFragment =
                 supportFragmentManager.findFragmentById(R.id.nav_controller) as NavHostFragment?
             if (BuildConfig.DEBUG && navHostFragment == null) {
                 error("Assertion failed")
             }
             navController = navHostFragment!!.getNavController()
             drawerLayout = activityMainBinding!!.drawerLayout
             actionBarDrawerToggle = ActionBarDrawerToggle(
                 this,
                 drawerLayout,
                 toolbar,
                 R.string.navigation_drawer_open,
                 R.string.navigation_drawer_close
             )
             drawerLayout!!.addDrawerListener(actionBarDrawerToggle!!)
             actionBarDrawerToggle!!.syncState()
             NavigationUI.setupWithNavController(activityMainBinding?.navView!!, navController!!)
             navController!!.addOnDestinationChangedListener(OnDestinationChangedListener { controller: NavController?, destination: NavDestination, arguments: Bundle? ->
                 when (destination.id) {
                     R.id.homeFragment -> {
                     }
                     R.id.customerListFragment -> {
                     }
                     R.id.vechicleAllowanceFragment -> {
                     }
                 }
             })
             val navigationView = findViewById<NavigationView>(R.id.nav_view)
             navigationView.menu.findItem(R.id.nav_item_Logout)
                 .setOnMenuItemClickListener { menuItem: MenuItem? ->
                     logout()
                     true
                 }
         }

         override fun onSupportNavigateUp(): Boolean {
             return NavigationUI.navigateUp(
                 Navigation.findNavController(this, R.id.nav_controller),
                 drawerLayout
             )
         }

         private fun logout() {
             startActivity(Intent(this, LoginActivity::class.java))
             finish()
         }


         override fun onDestroy() {
             super.onDestroy()
             Log.i(TAG, "onDestroy: ")
         }*/








        toolbar = findViewById(R.id.toolbar)
        drawerLayout = activityMainBinding?.drawerLayout
        val topLevelDestinations: MutableSet<Int> = HashSet()
        topLevelDestinations.add(R.id.homeFragment)
        topLevelDestinations.add(R.id.vechicleAllowanceFragment)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
            .setOpenableLayout(drawerLayout)
            .build()
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_controller) as NavHostFragment?)!!
        navController = navHostFragment.navController
        setSupportActionBar(toolbar)
//        NavigationUI.setupActionBarWithNavController(this, navController);
        //        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(activityMainBinding?.navView!!, navController!!)
        NavigationUI.setupActionBarWithNavController(
            this,
            navController!!,
            appBarConfiguration!!
        )

        /*   NavigationUI.setupActionBarWithNavController(this,
                this.navController,
                this.appBarConfiguration);*/


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.menu.findItem(R.id.nav_item_Logout)
            .setOnMenuItemClickListener { menuItem: MenuItem? ->
                logout()
                true
            }
    }

    private fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        sharedPreferenceUtil.clearAllData()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_controller),
            appBarConfiguration!!
        ) || super.onSupportNavigateUp()
    }
}