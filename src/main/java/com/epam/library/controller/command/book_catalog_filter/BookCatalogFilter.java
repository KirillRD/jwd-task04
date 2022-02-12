package com.epam.library.controller.command.book_catalog_filter;

import com.epam.library.controller.command.book_catalog_filter.impl.*;
import com.epam.library.constant.BookCatalogFilterName;
import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * Class for building a map to filter books
 */
public class BookCatalogFilter {

    private static final String SAVED_AUTHORS = "saved_authors";
    private static final String SAVED_GENRES = "saved_genres";

    private static final Map<String, FilterField> filterFields = Map.ofEntries(
            Map.entry(BookCatalogFilterName.SORT, new SortField()),
            Map.entry(BookCatalogFilterName.FREE_INSTANCES, new FreeInstanceField()),
            Map.entry(BookCatalogFilterName.AUTHORS, new AuthorField()),
            Map.entry(BookCatalogFilterName.GENRES, new GenreField()),
            Map.entry(BookCatalogFilterName.PUBLISHER, new PublisherField()),
            Map.entry(BookCatalogFilterName.TYPE, new TypeField()),
            Map.entry(BookCatalogFilterName.PAGES_FROM, new PageField()),
            Map.entry(BookCatalogFilterName.PAGES_TO, new PageField()),
            Map.entry(BookCatalogFilterName.PUBLICATION_YEAR_FROM, new PublicationYearField()),
            Map.entry(BookCatalogFilterName.PUBLICATION_YEAR_TO, new PublicationYearField()),
            Map.entry(BookCatalogFilterName.NAME, new DefaultField()),
            Map.entry(BookCatalogFilterName.ISBN, new DefaultField()),
            Map.entry(BookCatalogFilterName.ISSN, new DefaultField())
    );

    private Map<String, Object> filters;
    private Set<String> filterNames;
    private String sortValue;
    private List<String> authorsID;
    private List<String> genresID;

    public BookCatalogFilter () {
        filters = new LinkedHashMap<>();
        filterNames = new HashSet<>(BookCatalogFilterName.bookCatalogFilterName);
        sortValue = null;
        authorsID = new ArrayList<>();
        genresID = new ArrayList<>();
    }

    /**
     * Returns map which consists of the filter field name and its value
     * @param request
     * @return map which consists of the filter field name and its value
     */
    public Map<String, Object> buildFilter(HttpServletRequest request) {

        Map<String, String[]> requestParameterMap = request.getParameterMap();

        for (String filterName : requestParameterMap.keySet()) {
            if (filterNames.contains(filterName)) {
                for (String filterValue : requestParameterMap.get(filterName)) {
                    if (!filterValue.isEmpty()) {
                        FilterField filterField = filterFields.get(filterName);
                        filterField.execute(filterName, filterValue, this, request);
                    }
                }
            }
        }
        if (!authorsID.isEmpty()) {
            request.setAttribute(SAVED_AUTHORS, authorsID);
            filters.put(BookCatalogFilterName.AUTHORS, authorsID);
        }

        if (!genresID.isEmpty()) {
            request.setAttribute(SAVED_GENRES, genresID);
            filters.put(BookCatalogFilterName.GENRES, genresID);
        }

        if (sortValue != null) {
            filters.put(BookCatalogFilterName.SORT, sortValue);
        } else {
            filters.put(BookCatalogFilterName.SORT, BookCatalogFilterName.NAME_ASCENDING);
        }

        return filters;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public Set<String> getFilterNames() {
        return filterNames;
    }

    public List<String> getAuthorsID() {
        return authorsID;
    }

    public List<String> getGenresID() {
        return genresID;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }
}
