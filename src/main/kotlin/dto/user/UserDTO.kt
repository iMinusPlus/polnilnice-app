package dto.user

data class UserDTO(val id: String?, var username: String, var password: String, var email: String) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "username" to username,
            "password" to password,
            "email" to email
        )
    }
}