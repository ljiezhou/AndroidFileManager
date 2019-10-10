package com.zhou.androidfilebrowser

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.Menu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.*
import com.zhou.androidfilebrowser.base.BaseActivity
import com.zhou.androidfilebrowser.ui.home.HomeFragment
import com.zhou.androidfilebrowser.filemanager.FileHelper
import com.zhou.androidfilebrowser.uitl.LogcatUtil

class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var fragment: Fragment
    private lateinit var homeFragment: HomeFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        super.initData()
        homeFragment = HomeFragment.newInstance(FileHelper.getSDPath())
        fragment = homeFragment
    }

    override fun initView() {
        super.initView()
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            openDirectory()
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment!!).commit()

        checkPermission()
    }

    var onNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.nav_home -> {
                homeFragment?.loadDirectory()
                true
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        true
    }


    private fun openDirectory() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        }
        startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_DIRECTORY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val directoryUri = data?.data ?: return
            Log.d("MainActivity", directoryUri.path)
            contentResolver.takePersistableUriPermission(
                directoryUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            LogcatUtil.d(directoryUri)
//            showDirectoryContents(directoryUri)

        }
    }
    //android 6.0 得申请权限，但是暂时未用到，原因未知。
    private val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 12
    override fun onRequestPermissionsResult(requestCode: Int,  permissions: Array<String>,  grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(homeFragment!=null) homeFragment.loadDirectory()
            } else {
                toast("没有权限")
            }
        }
    }

    private fun checkPermission(): Boolean {
        try {
            val permissionsList = ArrayList<String>()

            if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (permissionsList.size > 0) {
                ActivityCompat.requestPermissions(this@MainActivity, permissionsList.toTypedArray<String>(), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS)
                return false
            }
            return true
        } catch (e: Exception) {
            return true
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
}

private const val OPEN_DIRECTORY_REQUEST_CODE = 0xf11e