package com.talkdesk.controller

import org.json.JSONArray
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class RestControllerAdviceTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should aggregate four numbers valid`() {
        val payload = listOf("+1983236248", "+1 7490276403", "001382355A", "+351917382672", "+35191734022")
        mockMvc.perform(
            MockMvcRequestBuilders.post("/aggregate")
                .content(JSONArray(payload).toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().json("{ '1': { 'Technology': 1, 'Banking': 1 }, '3519173': { 'Clothing': 2 } }"));
    }

    @Test
    fun `should aggregate no one numbers because they dont are valid with more than 13 characters`() {
        val payload = listOf("+19832362483443434343", "+1 74902764037490276403", "001382355A001382355", "+351917382672191738267", "+35191734021734022")
        mockMvc.perform(
            MockMvcRequestBuilders.post("/aggregate")
                .content(JSONArray(payload).toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(content().json("{}s"));
    }
}
