package com.example.googlemap

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private var map: GoogleMap? = null


    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
        map?.uiSettings.apply {
            this?.isZoomControlsEnabled = true
            this?.isScrollGesturesEnabled = true
            this?.isZoomGesturesEnabled = true
        }
        setMapStyle(map)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_types_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.normal_map -> {
                map?.mapType = GoogleMap.MAP_TYPE_NORMAL
            }

            R.id.hybrid_map -> {
                map?.mapType = GoogleMap.MAP_TYPE_HYBRID
            }

            R.id.terrain_map -> {
                map?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }

            R.id.sat_map -> {
                map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }

            R.id.none_map -> {
                map?.mapType = GoogleMap.MAP_TYPE_NONE
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    fun mapType(item: MenuItem) {
        Log.d("MapFragment", "Menu item selected: ${item.itemId}") // Check if this method is called

        when (item.itemId) {
            R.id.normal_map ->
                map?.mapType = GoogleMap.MAP_TYPE_NORMAL

            R.id.hybrid_map ->
                map?.mapType = GoogleMap.MAP_TYPE_HYBRID

            R.id.terrain_map ->
                map?.mapType = GoogleMap.MAP_TYPE_TERRAIN

            R.id.sat_map ->
                map?.mapType = GoogleMap.MAP_TYPE_SATELLITE

            R.id.none_map ->
                map?.mapType = GoogleMap.MAP_TYPE_NONE


        }


    }

    private fun setMapStyle(googleMap: GoogleMap?) {
        try {

            val success = googleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.style
                )
            )
            if (success==false) {
                Log.d("Maps", "Style parsing failed")
            }
        } catch (e: Exception) {
            Log.e("Maps", e.toString())
        }
    }
}