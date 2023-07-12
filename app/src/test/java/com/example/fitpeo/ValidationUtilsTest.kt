package com.example.fitpeo

import com.example.fitpeo.model.ResponseData
import com.example.fitpeo.util.ValidationUtils
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidationUtilsTest {
    @Test
    fun validateValidUrlTest() {
        var item = ResponseData(
            1,
            1,
            "accusamus beatae ad facilis cum similique qui sunt",
            "https://via.placeholder.com/600/92c952",
            "https://via.placeholder.com/150/92c952"
        )
        assertEquals(true, ValidationUtils.validateUrl(item))
        assertEquals(true, ValidationUtils.validateThumbnail(item))
    }

    @Test
    fun validateEmptyUrlTest() {
        var item = ResponseData(
            1,
            1,
            "accusamus beatae ad facilis cum similique qui sunt",
            "",
            ""
        )
        assertEquals(false, ValidationUtils.validateUrl(item))
        assertEquals(false, ValidationUtils.validateThumbnail(item))
    }
}