package com.example.trasuatuananh.util

import android.content.Context
import com.example.trasuatuananh.api.model.address.QuanHuyen
import com.example.trasuatuananh.api.model.address.TinhTp
import com.example.trasuatuananh.api.model.address.XaPhuong
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AddressUtils {

    // Hàm đọc tệp JSON từ assets
    private fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    // Hàm phân tích JSON thành Map<String, TinhTp>
    private fun parseJsonToTinhTpMap(jsonString: String): Map<String, TinhTp> {
        val gson = Gson()
        val mapType = object : TypeToken<Map<String, TinhTp>>() {}.type
        return gson.fromJson(jsonString, mapType)
    }

    // Lấy danh sách các Tỉnh/Thành phố
    fun getTinhList(context: Context): List<TinhTp> {
        val jsonString = readJsonFromAssets(context, "tree.json")
        val tinhTpMap = parseJsonToTinhTpMap(jsonString)
        return tinhTpMap.values.toList()
    }

    fun getQuanHuyenListByTinh(context: Context, tinhCode: String): List<QuanHuyen> {
        val jsonString = readJsonFromAssets(context, "tree.json")
        val tinhTpMap = parseJsonToTinhTpMap(jsonString)
        return tinhTpMap[tinhCode]?.quan_huyen?.values?.toList() ?: emptyList()
    }

    // Lấy danh sách các Xã/Phường theo mã Quận/Huyện
    fun getXaPhuongListByQuanHuyen(context: Context, quanHuyenCode: String): List<XaPhuong> {
        val jsonString = readJsonFromAssets(context, "tree.json")
        val tinhTpMap = parseJsonToTinhTpMap(jsonString)

        // Tìm Quận/Huyện trong tất cả các Tỉnh/Thành phố
        for (tinh in tinhTpMap.values) {
            val quanHuyen = tinh.quan_huyen[quanHuyenCode]
            if (quanHuyen != null) {
                return quanHuyen.xa_phuong.values.toList()
            }
        }
        return emptyList() // Trả về danh sách rỗng nếu không tìm thấy
    }
}