package csv.masters.kotlin101.data

import com.google.gson.annotations.SerializedName

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
