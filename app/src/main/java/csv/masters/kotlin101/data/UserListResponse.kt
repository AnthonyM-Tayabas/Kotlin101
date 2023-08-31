package csv.masters.kotlin101.data


import com.google.gson.annotations.SerializedName

data class UserListResponse(
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
)