package com.example.project_ice

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class login : Fragment() {
    var user: FirebaseUser? = null
    lateinit var facebookSignInButton: LoginButton
    var callbackManager: CallbackManager? = null
    // Firebase Auth Object.
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())
        AppEventsLogger.activateApp(activity!!.baseContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val button : Button = view.findViewById(R.id.login)
        val loginbutton = view.findViewById(R.id.com_facebook_button_xout) as Button

        button.setOnClickListener {
            var username: EditText = view.findViewById(R.id.username)
            var password: EditText = view.findViewById(R.id.password)
            Toast.makeText(context,"Login Success ", Toast.LENGTH_LONG).show()

            val menu_view = menu()
            val fm =fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.contentContainer,menu_view,"menu_view")
            transaction.addToBackStack("menu_view")
            transaction.commit()
        }

        callbackManager = CallbackManager.Factory.create()
        facebookSignInButton  = view.findViewById(R.id.com_facebook_button_xout) as LoginButton
        firebaseAuth = FirebaseAuth.getInstance()
        facebookSignInButton.setReadPermissions("email")


        // If using in a fragment
        facebookSignInButton.setFragment(this)

        val token: AccessToken?
        token = AccessToken.getCurrentAccessToken()

        if (token != null) { //Means user is not logged in
            LoginManager.getInstance().logOut()
        }
        loginbutton.setOnClickListener {

        }
        // Callback registration
        facebookSignInButton.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) { // App code
                handleFacebookAccessToken(loginResult!!.accessToken)
            }
            override fun onCancel() { // App code
            }
            override fun onError(exception: FacebookException) { // App code
            }
        })

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) { // App code
                    handleFacebookAccessToken(loginResult!!.accessToken)
                }

                override fun onCancel() { // App code
                }
                override fun onError(exception: FacebookException) { // App code
                }
            })


        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token : AccessToken) {


        Log.d(ContentValues.TAG, "handleFacebookAccessToken:" + token)
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "signInWithCredential:success")

                user = firebaseAuth!!.currentUser

                val profile = profile().newInstance(user!!.photoUrl.toString(),user!!.displayName.toString())
                val fm = fragmentManager
                val transaction : FragmentTransaction = fm!!.beginTransaction()
                transaction.replace(R.id.contentContainer, profile,"profile")
                transaction.addToBackStack("profile")
                transaction.commit()

            } else {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "signInWithCredential:failure", task.getException())
                Toast.makeText(activity!!.baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
