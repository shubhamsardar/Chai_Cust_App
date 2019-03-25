package `in`.co.tripin.chahiyecustomer.Adapters

import `in`.co.tripin.chahiyecustomer.R
import android.content.Context
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.GoogleMap
import org.w3c.dom.Text


class InfoWindowCustom(internal var context: Context) : GoogleMap.InfoWindowAdapter {
    internal lateinit var inflater: LayoutInflater
    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // R.layout.echo_info_window is a layout in my
        // res/layout folder. You can provide your own
        val v = inflater.inflate(R.layout.infowindow_tapri, null)

        val title = v.findViewById(R.id.name) as TextView
        val subtitle = v.findViewById(R.id.distance) as TextView
        //val mobile = v.findViewById<TextView>(R.id.mobile)
        title.text = marker.title
        subtitle.text = marker.snippet
        return v
    }
}