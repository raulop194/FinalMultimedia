package es.nexcreep.testing.youradventure.utils

import java.math.BigInteger
import java.security.MessageDigest

class AuthUtils {
    companion object {
        fun String.sha256(): String {
            val md = MessageDigest.getInstance("SHA-256")
            return BigInteger(1, md.digest(toByteArray()))
                .toString(16)
                .padStart(32, '0')
        }
    }
}