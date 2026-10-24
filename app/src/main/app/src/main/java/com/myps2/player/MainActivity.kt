package com.myps2.player

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var status: TextView

    private val gamePicker =
        registerForActivityResult(
            android.app.ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                if (uri != null) {
                    status.text =
                        "Game selected:\n${uri.lastPathSegment}"
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val root = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(28, 28, 28, 28)
                setBackgroundColor(Color.rgb(16, 21, 34))
            }

            val title = TextView(this).apply {
                text = "MY PS2 PLAYER"
                textSize = 28f
                setTextColor(Color.WHITE)
                gravity = Gravity.CENTER
            }

            status = TextView(this).apply {
                text = "Select a PS2 game file"
                textSize = 16f
                setTextColor(Color.LTGRAY)
                gravity = Gravity.CENTER
                setPadding(0, 24, 0, 24)
            }

            val selectButton = Button(this).apply {
                text = "SELECT PS2 GAME"
                setOnClickListener {
                    val intent = Intent(
                        Intent.ACTION_OPEN_DOCUMENT
                    ).apply {
                        addCategory(
                            Intent.CATEGORY_OPENABLE
                        )
                        type = "*/*"
                    }
                    gamePicker.launch(intent)
                }
            }

            val emulatorButton = Button(this).apply {
                text = "OPEN EMULATOR"
                setOnClickListener {
                    Toast.makeText(
                        this@MainActivity,
                        "PS2 emulator is not connected yet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            root.addView(title)
            root.addView(status)
            root.addView(selectButton)
            root.addView(emulatorButton)

            setContentView(root)

        } catch (e: Exception) {
            Toast.makeText(
                this,
                "App error: ${e.javaClass.simpleName}",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }
}
