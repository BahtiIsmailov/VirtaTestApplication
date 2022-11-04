package com.example.testapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.testapplication.app.AppPrefsKey
import com.example.testapplication.presentation.login.LoginFragment
import com.example.testapplication.presentation.station.StationFragment
import com.example.testapplication.utils.prefs.SharedWorker
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var sharedWorker: SharedWorker
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var PERMISSION_ID = 44

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLastLocation()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (checkToken()){
            supportFragmentManager.commit {
                add<StationFragment>(R.id.fragment_container_view)
            }
        }else{
            supportFragmentManager.commit {
                add<LoginFragment>(R.id.fragment_container_view)
            }
        }


    }
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient?.lastLocation
                    ?.addOnCompleteListener { task ->
                        val location = task.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            sharedWorker.saveMediate(AppPrefsKey.LOCATION,"${location.latitude}-${location.longitude} ")
                        }
                    }
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient?.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            Toast.makeText(applicationContext, "Latitude: " + mLastLocation!!.latitude + "", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Longitude: " + mLastLocation.longitude + "", Toast.LENGTH_SHORT).show()

        }
    }

    // method to check for permissions
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

     }

    // method to request for permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    // method to check
    // if location is enabled
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // If everything is alright then
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

    private fun checkToken() : Boolean{
         return sharedWorker.isAllExists(AppPrefsKey.AUTH_KEY)
    }
}


/*
# The test assignment:

## Resources:
- Design specification: https://app.abstract.com/share/321c1d45-9a8f-4b48-a0e4-8fd196df161a
- Icons are in the file attached.
- Base url is `https://apitest.virta.fi/v4` API doc is a Swagger file attached.
- The user credentials are: `candidate1@virta.global` pw: `1Candidate!`

## Requirements:
1. On launch show login screen if not logged in yet. Login endpoint: `POST https://apitest.virta.fi/v4/auth`
- If login was successful, save token and go to stations list screen.
2. If user already logged in, show station list
3. Station list screen should show the list of 10 stations (ordered by distance to user current location, nearest first). Stations endpoint: `GET https://apitest.virta.fi/v4/stations`
- Please also take in consideration that the API expects coordinate values
4. Extra task: Create a custom settings page in the app. It is not included on the design, you can use your own idea for the layout. The page should have two feature toggles:
- One toggle which hides "kW" label on the main screen of the app.
- Another toggle which hides the distance label when it is turned on.

## Some additional notes:
1. The assignment is not about your UI/UX design skill. We don't want you to spend too much time on
making screens 100% matching our specs. We don't care if the distance between labels or font size are wrong.
2. The orientation can be locked to portrait only.
3. You can use a dependency to manage networking, but for the rest of the solution please do not use
any third-party tool or framework.
4. Use best pratices in your own opinion.
5. Please give us your thought process about your choices of technology, pattern and your practices.
 */