package com.moonlyte.socialapp.features.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.moonlyte.loginmodule.view.LoginActivity
import com.moonlyte.socialapp.R
import com.moonlyte.socialapp.databinding.ActivityMainBinding
import com.moonlyte.socialapp.features.account.UsersActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityMainBinding.handler = this
    }

    fun loginClick() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("MainActivity","Success")
                val intent = Intent(this, UsersActivity::class.java)
                startActivity(intent)
                finish()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("MainActivity","Canceled")
            }
        }
    }
}
