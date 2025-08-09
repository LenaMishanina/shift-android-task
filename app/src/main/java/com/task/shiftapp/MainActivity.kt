package com.task.shiftapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.shiftapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.task.shiftapp.data.api.ApiClient.Companion.api
import com.task.shiftapp.data.model.user.User
import com.task.shiftapp.ui.UserAdapter
import com.task.shiftapp.utils.Constants.USER_LIST_KEY
import com.task.shiftapp.utils.Constants.USER_PREF
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefs: SharedPreferences
    private val adapter = UserAdapter()
    private val users = mutableListOf<User>()
    private var isAddPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = getSharedPreferences(USER_PREF, MODE_PRIVATE)
        loadUsers()

        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView() = with(binding.rcUsers) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = this@MainActivity.adapter

        addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                hideInputAndClear()
                return false
            }
        })
    }

    private fun setupClickListeners() = with(binding) {
        btnAddUsers.setOnClickListener {
            if (isAddPressed) {
                val count = edUserCount.text.toString().toIntOrNull() ?: 1
                if (count in 1..20) {
                    addUsersToRC(count)
                    hideInputAndClear()
                } else {
                    edUserCount.text.clear()
                }
            } else {
                showInput()
            }
        }

        btnRemoveUsers.setOnClickListener {
            removeAllUsers()
        }

    }

    private fun addUsersToRC(num: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newUsers = (1..num).mapNotNull {
                    api.getRandomUser().results.firstOrNull()
                }

                withContext(Dispatchers.Main) {
                    val updatedList = ArrayList(users).apply { addAll(newUsers) }
                    users.clear()
                    users.addAll(updatedList)
                    adapter.submitList(updatedList)
                    saveUsers()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun removeAllUsers() {
        if (users.isNotEmpty()) {
            users.clear()
            adapter.submitList(emptyList())
            saveUsers()
        } else {
            Toast.makeText(this, "Список пуст", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showInput() = with(binding) {
        edUserCount.visibility = View.VISIBLE
        isAddPressed = true

        edUserCount.requestFocus()
        showKeyboard()
    }

    private fun hideInputAndClear() = with(binding) {
        if (isAddPressed) {
            edUserCount.visibility = View.GONE
            edUserCount.text.clear()
            hideKeyboard()
            isAddPressed = false
        }
    }

    private fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.edUserCount, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.edUserCount.windowToken, 0)
    }

    private fun loadUsers() {
        val json = sharedPrefs.getString(USER_LIST_KEY, null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<User>>() {}.type
            val savedUsers = Gson().fromJson<List<User>>(json, type)
            users.clear()
            users.addAll(savedUsers)
            adapter.submitList(users.toList())
        }
    }

    private fun saveUsers() {
        val json = Gson().toJson(users)
        sharedPrefs.edit().putString(USER_LIST_KEY, json).apply()
    }

    override fun onPause() {
        super.onPause()
        saveUsers()
    }

}
