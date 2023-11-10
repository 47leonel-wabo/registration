package com.wbt.registrationapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wbt.registrationapp.databinding.ActivityPreviewBinding
import java.io.Serializable

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding
    private lateinit var profile: Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profile = getSerializeData(this@PreviewActivity, "PROFILE_DATA", Profile::class.java)
        val formattedMessage = """
            Hello ${profile.firstName} ${profile.lastName}, 
            
            We have received your registration in position of ${profile.jobPosition}.
            
            Your account was successfully created with the following credentials,
            email: ${profile.email},
            password: ${profile.password}
            
            Thank you for your application, best regard!
            Good day!!
        """.trimIndent()

        binding.previewMessageText.text = formattedMessage

        // Share message via SMS
        binding.buttonShareSms.setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                this.data = Uri.parse("smsto:${profile.phone}")
                this.putExtra("sms_body", formattedMessage)
            }.let {
                if (it.resolveActivity(packageManager) != null) {
                    startActivity(it)
                } else {
                    Toast.makeText(
                        this@PreviewActivity,
                        "Unable to resolve the SEND_TO action",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Share message via Whatsapp
        binding.buttonShareWhatsapp.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                this.putExtra(Intent.EXTRA_TEXT, formattedMessage)
                this.type = "text/plain"
            }.let {
                startActivity(Intent.createChooser(it, null))
            }
        }
    }

    // Dealing with deprecated 'getSerializableExtra' in api 33 and above
    private fun <T : Serializable?> getSerializeData(
        activity: Activity,
        key: String,
        objectClass: Class<T>
    ): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) activity.intent.getSerializableExtra(
            key,
            objectClass
        )!!
        else activity.intent.getSerializableExtra(key) as T
    }

}