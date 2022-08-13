package com.rcalencar.kotlinextensionfunction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rcalencar.kotlinextensionfunction.databinding.FragmentFirstBinding
import java.text.NumberFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewMsrpValue.text = formatCurrency(order.msrp)
        binding.groupDiscount.visibleIfNotNull(order.discount) {
            binding.textviewDiscountValue.text = formatCurrency(it)
        }
        binding.groupTax.visibleIfNotNull(order.tax) {
            binding.textviewTaxValue.text = formatCurrency(it)
        }
        binding.groupMemo.visibleIfNotNull(order.memo) {
            binding.textviewMemoValue.text = it.uppercase()
        }
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Order(val msrp: Float, val discount: Float?, val tax: Float?, val memo: String?)
val order = Order(120f, null, 18f, "Paid in full")

fun <T> View.visibleIfNotNull(value: T?, body: (T) -> Unit) {
    if (value == null) visibility = View.GONE
    else {
        visibility = View.VISIBLE
        body(value)
    }
}

fun formatCurrency(value: Float): String {
    val locale = Locale.getDefault()
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    return numberFormat.format(value)
}
