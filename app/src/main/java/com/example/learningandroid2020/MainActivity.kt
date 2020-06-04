package com.example.learningandroid2020

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    companion object {
        const val COLLECTION_KEY = "Chat"
        const val DOCUMENT_KEY = "Message"
        const val NAME_FIELD = "Name"
        const val TEXT_FIELD = "Text"
    }

    private val firestoreChat by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(DOCUMENT_KEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realtimeUpdateListener()
        buttonSendMessage.setOnClickListener{ sendMessage() }
    }

    private fun sendMessage() {
        val newMessage = mapOf(
            NAME_FIELD to edit_name.text.toString(),
            TEXT_FIELD to edit_message.text.toString())
        firestoreChat.set(newMessage)
            .addOnSuccessListener( {
                Toast.makeText(this@MainActivity, "Message Sent", Toast.LENGTH_SHORT).show()
            })
            .addOnFailureListener { e -> Log.e("ERROR", e.message) }
    }

    private fun realtimeUpdateListener() {
        firestoreChat.addSnapshotListener { documentSnapshot, e ->
            when {
                e != null -> Log.e("ERROR", e.message)
                documentSnapshot != null && documentSnapshot.exists() -> {
                    with(documentSnapshot) {
                        textDisplay.text = "${data[NAME_FIELD]}:${data[TEXT_FIELD]}"
                    }
                }
            }
        }
    }
}
