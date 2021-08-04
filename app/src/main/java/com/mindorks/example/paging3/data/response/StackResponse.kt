package com.mindorks.example.paging3.data.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StackResponse(
    @SerializedName("has_more") val hasMore: Boolean?, // true
    @SerializedName("items") val items: List<Item> = emptyList(),
    @SerializedName("quota_max") val quotaMax: Int?, // 300
    @SerializedName("quota_remaining") val quotaRemaining: Int? // 290
) {
    @Keep
    data class Item(
        @SerializedName("accepted_answer_id") val acceptedAnswerId: Int?, // 68645076
        @SerializedName("answer_count") val answerCount: Int?, // 0
        @SerializedName("closed_date") val closedDate: Int?, // 1628048684
        @SerializedName("closed_reason") val closedReason: String?, // Duplicate
        @SerializedName("content_license") val contentLicense: String?, // CC BY-SA 4.0
        @SerializedName("creation_date") val creationDate: Int?, // 1628049590
        @SerializedName("is_answered") val isAnswered: Boolean?, // false
        @SerializedName("last_activity_date") val lastActivityDate: Int?, // 1628049590
        @SerializedName("last_edit_date") val lastEditDate: Int?, // 1628049576
        @SerializedName("link") val link: String?, // https://stackoverflow.com/questions/68645199/excel-add-1-00-to-xs-xl-price-and-show-in-xxl-price-in-the-same-cell
        @SerializedName("owner") val owner: Owner?,
        @SerializedName("question_id") val questionId: Int?, // 68645199
        @SerializedName("score") val score: Int?, // 0
        @SerializedName("tags") val tags: List<String?>?,
        @SerializedName("title") val title: String?, // Excel, Add $1.00 to XS-XL price and show in XXL price in the same cell
        @SerializedName("view_count") val viewCount: Int? // 1
    ) {
        @Keep
        data class Owner(
            @SerializedName("accept_rate") val acceptRate: Int?, // 63
            @SerializedName("account_id") val accountId: Int?, // 19723917
            @SerializedName("display_name") val displayName: String?, // My Eyes
            @SerializedName("link") val link: String?, // https://stackoverflow.com/users/14440895/my-eyes
            @SerializedName("profile_image") val profileImage: String?, // https://lh6.googleusercontent.com/-w6vdKXVVnV0/AAAAAAAAAAI/AAAAAAAAAAA/AMZuuckUIAidYeuN73e4ECuAwNt_d1z6QQ/s96-c/photo.jpg?sz=128
            @SerializedName("reputation") val reputation: Int?, // 1
            @SerializedName("user_id") val userId: Int?, // 14440895
            @SerializedName("user_type") val userType: String? // registered
        )
    }
}