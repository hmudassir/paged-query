# Architecture

**Pattern:** Repository, with a page DTO at the boundary. Paging is a query limit. The pattern that keeps the row private is still the Data Transfer Object.

## Boundaries

```text
GET /api/labels?page&size
        |
        v
LabelController
        |
        v
LabelService
  PageRequest.of(page, size, Sort.by("id"))
        |
        v
LabelRepository.findAll(pageable)     one query for the rows, one count
        |
        v
PageResponse
  items are LabelResponse records
  page, size, totalItems, totalPages
```

The repository applies the limit. The service does not load every label and then slice the list. Open-in-view is off, so the records are copied before the transaction ends.

## Request path

1. The controller rejects a page below 0 or a size outside 1–20 with `400`.
2. The repository returns that page, ordered by `id`, so the same request returns the same rows.
3. The service maps `Label` to `LabelResponse` and copies the page numbers into `PageResponse`.

Five seeded rows and `size=2` produce three pages. The last page has one item. `totalItems` stays 5 on every page.

## Failure

| Condition | Result |
| --- | --- |
| `page=-1` or `size=0` or `size=21` | `400` |
| A page past the end, such as `page=9&size=2` | `200`, `items` empty, `totalItems` still 5 |

## What this sample leaves out

Offset pages. A caller who inserts a row while someone walks the pages can see a row twice or skip one. A keyset query (`where id > :last order by id limit :size`) is the next step when that matters. The response record stays the same kind of boundary.
