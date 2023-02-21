package es.nexcreep.testing.youradventure

import org.junit.Test
import java.math.BigInteger
import java.security.MessageDigest

class SecurityUnitTest {
    fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, md.digest(toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }

    @Test
    fun sha256_test() {
        print("Password".sha256())
    }
}