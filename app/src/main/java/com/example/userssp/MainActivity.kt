package com.example.userssp

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userssp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
// import java.util.UUID
// import kotlin.random.Random

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)

        val isFisrtTime = preferences.getBoolean(getString(R.string.sp_first_time), true)
        Log.i("SP Value", "${getString(R.string.sp_first_time)} = $isFisrtTime")

        if (isFisrtTime) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_regiser, null)
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm) { _, _ -> }
                .setNeutralButton(R.string.dialog_neutral, null)
                .create()

            dialog.show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val username = dialogView.findViewById<TextInputEditText>(R.id.etUserName)
                    .text.toString()
                if(username.isBlank()) {
                    Toast.makeText(this, R.string.register_invalid, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), username)
                            .apply()
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
            }

        } else {
            val username = preferences.getString(getString(R.string.sp_username), getString(R.string.hint_username))
            Toast.makeText(this, "¡Bienvenido $username!", Toast.LENGTH_SHORT).show()
        }

        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.rcView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }

        val swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userAdapter.remove(viewHolder.adapterPosition)
            }
        })
        swipeHelper.attachToRecyclerView(binding.rcView)

        /* Log.i("LifeCycle", "OnCreate")
        Log.i("TraceId", generateTraceId())
        Log.i("TraceId+uuid",  UUID.randomUUID().toString()) */
    }

    /* private fun generateTraceId(): String {
        val bytes = ByteArray(16)
        Random.nextBytes(bytes)
        return bytesToHex(bytes)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = "0123456789abcdef".toCharArray()
        val result = StringBuilder(bytes.size * 2)

        for (byte in bytes) {
            val i = byte.toInt()
            result.append(hexChars[i shr 4 and 0x0f])
            result.append(hexChars[i and 0x0f])
        }

        return result.toString()
    } */

    private fun getUsers(): MutableList<User> {
        val users = mutableListOf<User>()

        val alain = User(1, "Alain", "Nicolás", "https://frogames.es/wp-content/uploads/2020/09/alain-1.jpg")
        val samanta = User(2, "Samanta", "Meza", "https://upload.wikimedia.org/wikipedia/commons/b/b2/Samanta_villar.jpg")
        val javier = User(3, "Javier", "Gómez", "https://live.staticflickr.com/974/42098804942_b9ce35b1c8_b.jpg")
        val emma = User(4, "Emma", "Cruz", "https://upload.wikimedia.org/wikipedia/commons/d/d9/Emma_Wortelboer_%282018%29.jpg")

        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)

        return users
    }

    override fun onClick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT).show()
    }
}