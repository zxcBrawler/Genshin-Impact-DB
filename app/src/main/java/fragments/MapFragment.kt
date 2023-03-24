package fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.genshinhelper.R


class MapFragment : Fragment() {


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_map, container,false)
        val map : WebView = view.findViewById(R.id.map_web)
        map.webViewClient = WebViewClient()
        map.apply {
            loadUrl("https://genshin-info.ru/interaktivnaya-karta/")
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
        }
        return view
    }
}