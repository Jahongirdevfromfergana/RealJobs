package uz.fergana.developer.screen.main.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import uz.fergana.developer.MyApp
import uz.fergana.developer.R
import uz.fergana.developer.databinding.FragmentMapBinding


class MapFragment : Fragment(), LocationListener, OnMapReadyCallback {
    lateinit var binding: FragmentMapBinding
    private var mMap: GoogleMap? = null
    private var locationManager: LocationManager? = null
    var lastLocation: Location? = null
    var limitKm = 1

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationManager =
            requireActivity().getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardViewRadies.setOnClickListener {
            val popupMenu = PopupMenu(requireActivity(), it)
            val menu = popupMenu.menu
            menu.add(1, 1, 1, "1 km radius")
            menu.add(10, 10, 10, "10 km radius")
            menu.add(100, 100, 100, "100 km radius")

            popupMenu.setOnMenuItemClickListener {
                binding.tvRadius.text = it.title
                limitKm = it.itemId
                lastLocation = null
                setMapData()
                return@setOnMenuItemClickListener true
            }

            popupMenu.show()
        }
        getLocation()
    }


    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Dexter.withContext(requireActivity()).withPermissions(
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        if (p0?.areAllPermissionsGranted() == true) {
                            getLocation()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest()
                    }
                }).check()
            return
        }
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.0f, this)
    }

    @SuppressLint("MissingPermission")
    override fun onLocationChanged(p0: Location) {
        if (p0 != null && lastLocation == null) {
            if (mMap != null) {
                mMap?.isMyLocationEnabled = true
                lastLocation = p0
                setMapData()
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        setMapData()
    }

    fun setMapData(){
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val position = LatLng(location.latitude, location.longitude)
            if (mMap != null) {
                mMap?.isMyLocationEnabled = true
                mMap?.clear()
                var zoom = 14f
                if (limitKm == 10) {
                    zoom = 11f
                } else if (limitKm == 100) {
                    zoom = 8f
                }
                mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom))
                mMap?.addCircle(
                    CircleOptions().center(position).radius(limitKm * 1000.0)
                        .strokeColor(Color.parseColor("#0A17FF"))
                        .fillColor(Color.parseColor("#5C5E67FF"))
                )
                var employees = MyApp.employees.filter {
                    var targetlocation = Location(LocationManager.GPS_PROVIDER)
                    targetlocation.latitude = it.location_lat
                    targetlocation.longitude = it.location_lon

                    location!!.distanceTo(targetlocation) <= (limitKm*1000)
                }
                employees.forEach {
                    mMap?.addMarker(MarkerOptions().position(LatLng(it.location_lat, it.location_lon)).title(it.comment).snippet(it.fullname))
                }
            }
        }
    }

}