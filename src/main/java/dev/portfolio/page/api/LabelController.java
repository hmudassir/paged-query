package dev.portfolio.page.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.portfolio.page.service.LabelService;

@RestController
public class LabelController {

	private final LabelService labels;

	public LabelController(LabelService labels) {
		this.labels = labels;
	}

	@GetMapping("/api/labels")
	public PageResponse<LabelResponse> list(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "2") int size) {
		if (page < 0 || size < 1 || size > 20) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page must be >= 0 and size must be 1 to 20");
		}
		return labels.page(page, size);
	}
}
