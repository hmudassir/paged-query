package dev.portfolio.page.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.portfolio.page.api.LabelResponse;
import dev.portfolio.page.api.PageResponse;
import dev.portfolio.page.persistence.LabelRepository;

@Service
public class LabelService {

	private final LabelRepository labels;

	public LabelService(LabelRepository labels) {
		this.labels = labels;
	}

	@Transactional(readOnly = true)
	public PageResponse<LabelResponse> page(int page, int size) {
		var result = labels.findAll(PageRequest.of(page, size, Sort.by("id")));
		var items = result.getContent().stream()
				.map(label -> new LabelResponse(label.getId(), label.getName()))
				.toList();
		return new PageResponse<>(items, result.getNumber(), result.getSize(), result.getTotalElements(), result.getTotalPages());
	}
}
