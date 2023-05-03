package core.network.api

import com.example.android.core.model.Coupon
import com.example.android.core.model.CouponPackage
import core.network.Resp
import core.network.RespWithoutData
import httpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


fun Apis.Coupons.getMyCoupons() = callbackFlow {
    httpClient.get("filter/me/coupons").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Coupon>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Coupons.getCouponDetail(couponId: Int) = callbackFlow {
    httpClient.get("coupons/$couponId").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<Coupon>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Coupons.getMyCouponPackages() = callbackFlow {
    httpClient.get("filter/coupons_packages/me").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<CouponPackage>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}


fun Apis.Coupons.getAllCouponPackage() = callbackFlow {
    httpClient.get("filter/coupon_package/all").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<CouponPackage>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Coupons.getAllCoupons() = callbackFlow {
    httpClient.get("filter/coupons/all").apply {
        if (status.isSuccess()) {
            val resp = body<Resp<List<Coupon>>>()
            if (resp.code == 200) {
                send(resp.data)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}

fun Apis.Coupons.receiveCoupon(id: Int) = callbackFlow {
    httpClient.post("filter/coupon/${id}/receive").apply {
        if (status.isSuccess()) {
            val resp = body<RespWithoutData>()
            if (resp.code == 200) {
                send(null)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}


fun Apis.Coupons.redeemCoupon(couponPackageId: Int) = callbackFlow {
    httpClient.post("filter/coupons_packages/${couponPackageId}").apply {
        if (status.isSuccess()) {
            val resp = body<RespWithoutData>()
            if (resp.code == 200) {
                send(null)
            } else this@callbackFlow.cancel(resp.msg)
        } else this@callbackFlow.cancel(status.description)
        awaitClose { }
    }
}