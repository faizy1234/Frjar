package com.example.frjarcustomer.ui.screen.auth

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.collections.firstOrNull

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _showProductsByAddress = MutableStateFlow(false)
    val showProductsByAddress: StateFlow<Boolean> = _showProductsByAddress.asStateFlow()

    private val _selectedLatLng = MutableStateFlow(LatLng(24.7136, 46.6753))
    val selectedLatLng: StateFlow<LatLng> = _selectedLatLng.asStateFlow()

    private val _isLoadingAddress = MutableStateFlow(false)
    val isLoadingAddress: StateFlow<Boolean> = _isLoadingAddress.asStateFlow()

    fun setShowProductsByAddress(value: Boolean) = _showProductsByAddress.update { value }

    fun setAddress(value: String) = _address.update { value }

    fun onMapDragEnd(latLng: LatLng) {
        _selectedLatLng.update { latLng }
        fetchAddressFromLatLng(latLng)
    }

    private fun fetchAddressFromLatLng(latLng: LatLng) {

        _isLoadingAddress.update { true }
        val geocoder = Geocoder(context, Locale.ENGLISH)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            geocoder.getFromLocation(
                latLng.latitude,
                latLng.longitude,
                1,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        val address = addresses.firstOrNull()?.getAddressLine(0)
                        address?.let { _address.update { it } }
                        _isLoadingAddress.update { false }
                    }

                    override fun onError(errorMessage: String?) {
                        _isLoadingAddress.update { false }
                    }
                }
            )

        } else {

            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                        ?.firstOrNull()
                        ?.getAddressLine(0)
                }
                result?.let { _address.update { it } }
                _isLoadingAddress.update { false }
            }
        }
    }

}