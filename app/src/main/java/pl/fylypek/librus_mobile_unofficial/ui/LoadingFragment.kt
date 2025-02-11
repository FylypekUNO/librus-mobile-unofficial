package pl.fylypek.librus_mobile_unofficial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import pl.fylypek.librus_mobile_unofficial.R

class LoadingFragment : Fragment() {

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): LoadingFragment {
            val fragment = LoadingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_MESSAGE, message)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = arguments?.getString(ARG_MESSAGE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loading, container, false)
        val tvMessage = view.findViewById<TextView>(R.id.tvLoadingMessage)
        tvMessage.text = message ?: "Loading..."
        return view
    }
}
