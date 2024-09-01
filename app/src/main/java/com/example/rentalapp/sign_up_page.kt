package com.example.rentalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class sign_up_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainact)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the button and set up the click listener
        val signUpButton = findViewById<Button>(R.id.button4)
        signUpButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.editTextText).text.toString()
            val email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()

            // Validate user input
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Send data to the server
                sendUserDataToServer(username, email, password)
            }
        }
    }

    private fun sendUserDataToServer(username: String, email: String, password: String) {
        val urlString = "https://rental-app.valorantmaniabd.com/signup.php"

        Thread {
            try {
                // Create the URL object
                val url = URL(urlString)

                // Open the connection
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "POST"
                urlConnection.doOutput = true // Enable output to send data

                // Prepare the data to send
                val postData = "user_fullname=$username&user_email=$email&user_password=$password"

                // Write data to output stream
                val outputStreamWriter = OutputStreamWriter(urlConnection.outputStream)
                outputStreamWriter.write(postData)
                outputStreamWriter.flush()

                // Get the server response
                val responseCode = urlConnection.responseCode
                val responseMessage = if (responseCode == HttpURLConnection.HTTP_OK) {
                    urlConnection.inputStream.bufferedReader().use { it.readText() }
                } else {
                    "Error: $responseCode"
                }

                // Update the UI on the main thread
                runOnUiThread {
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Toast.makeText(this, "Signup successful", Toast.LENGTH_LONG).show()
                        redirectToHomePage() // Redirect to home page after success
                    } else {
                        Toast.makeText(this, responseMessage, Toast.LENGTH_LONG).show()
                    }
                }

                // Close the connection
                urlConnection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.start() // Start the thread to perform network operation
    }

    // Function to redirect to home page
    private fun redirectToHomePage() {
        val intent = Intent(this, dashboard::class.java) // Replace with your home page activity
        startActivity(intent)
        finish() // Optionally close the current signup activity
    }
}