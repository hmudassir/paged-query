package dev.portfolio.page;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PageQueryTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void theRepositoryPageBecomesAResponseRecord() throws Exception {
		mvc.perform(get("/api/labels").param("page", "0").param("size", "2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items.length()").value(2))
				.andExpect(jsonPath("$.items[0].name").value("One"))
				.andExpect(jsonPath("$.items[1].name").value("Two"))
				.andExpect(jsonPath("$.page").value(0))
				.andExpect(jsonPath("$.size").value(2))
				.andExpect(jsonPath("$.totalItems").value(5))
				.andExpect(jsonPath("$.totalPages").value(3))
				.andExpect(jsonPath("$.content").doesNotExist());

		mvc.perform(get("/api/labels").param("page", "2").param("size", "2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items.length()").value(1))
				.andExpect(jsonPath("$.items[0].name").value("Five"))
				.andExpect(jsonPath("$.totalItems").value(5));
	}

	@Test
	void anOutOfRangeSizeIsRejected() throws Exception {
		mvc.perform(get("/api/labels").param("size", "21"))
				.andExpect(status().isBadRequest());
	}
}
