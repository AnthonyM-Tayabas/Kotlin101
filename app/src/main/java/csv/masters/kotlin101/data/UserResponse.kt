package csv.masters.kotlin101.data


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    var `data`: List<User>,
    @SerializedName("page")
    var page: Int,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("support")
    var support: Support,
    @SerializedName("total")
    var total: Int,
    @SerializedName("total_pages")
    var totalPages: Int
) {
    data class User(
        @SerializedName("avatar")
        var avatar: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("first_name")
        var firstName: String,
        @SerializedName("id")
        var id: Int,
        @SerializedName("last_name")
        var lastName: String
    ) {
        fun fullName() = "$firstName $lastName"
    }

    data class Support(
        @SerializedName("text")
        var text: String,
        @SerializedName("url")
        var url: String
    )
}