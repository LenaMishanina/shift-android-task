package com.task.shiftapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.task.shiftapp.data.model.user.User
import com.task.shiftapp.databinding.ActivityProfileBinding
import com.task.shiftapp.utils.Constants.EXTRA_USER_KEY
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = getUserFromIntent()

        if (user == null) {
            Toast.makeText(this@ProfileActivity, resources.getString(R.string.error_upload_data), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadProfile(user)
        setupBackPressHandler()

        binding.tvPhone.setOnClickListener {
            dialPhoneNumber(user.phone)
        }

        binding.tvEmail.setOnClickListener {
            sendEmailApp(user.email)
        }
    }

    private fun getUserFromIntent(): User? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(EXTRA_USER_KEY, User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_USER_KEY)
        }
    }

    private fun loadProfile(user: User) = with(binding) {
        tvName.text = user.name.toString()
        tvGender.text = user.gender
        tvPhone.text = user.phone
        tvEmail.text = user.email
        val age = "${user.dob.age} ${resources.getString(R.string.age_end)}"
        tvAge.text = age
        tvDoB.text = formatDate(user.dob.date)
        tvStreet.text = user.location.street.toString()
        val location = "${user.location.country}, ${user.location.city}"
        tvLocation.text = location
        Picasso.get()
            .load(user.picture.medium)
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .into(ivProfile)

        val genderImage = if (user.gender == "male") R.drawable.ic_gender_male else R.drawable.ic_gender_female
        ivGender.setImageResource(genderImage)
    }

    private fun formatDate(isoDateString: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(isoDateString)

        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return date?.let { outputFormat.format(it) }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun dialPhoneNumber(phoneNumber: String) {
        try {
            val cleanedNumber = phoneNumber.replace(Regex("[^+0-9]"), "")

            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanedNumber"))

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, resources.getString(R.string.no_dialer_found), Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, resources.getString(R.string.error_fail_dialer), Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun sendEmailApp(email: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                type = "message/rfc822"
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this@ProfileActivity, resources.getString(R.string.no_email_send_found), Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this@ProfileActivity, resources.getString(R.string.error_fail_email_send), Toast.LENGTH_SHORT).show()
        }
    }
}
