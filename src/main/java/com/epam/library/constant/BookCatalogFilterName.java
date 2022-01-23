package com.epam.library.constant;

import java.util.Set;

public final class BookCatalogFilterName {

    private BookCatalogFilterName() {}

    public static final String NAME = "name";
    public static final String AUTHORS = "authors";
    public static final String GENRES = "genres";
    public static final String PUBLISHER = "publisher";
    public static final String PUBLICATION_YEAR_FROM = "publication_year_from";
    public static final String PUBLICATION_YEAR_TO = "publication_year_to";
    public static final String PAGES_FROM = "pages_from";
    public static final String PAGES_TO = "pages_to";
    public static final String ISBN = "isbn";
    public static final String ISSN = "issn";
    public static final String TYPE = "type";
    public static final String FREE_INSTANCES = "free_instances";
    public static final String SORT = "sort";
    public static final String NAME_ASCENDING = "name_ascending";
    public static final String NAME_DESCENDING = "name_descending";

    public static final Set<String> bookCatalogFilterName = Set.of(
            NAME,
            AUTHORS,
            GENRES,
            PUBLISHER,
            PUBLICATION_YEAR_FROM,
            PUBLICATION_YEAR_TO,
            PAGES_FROM,
            PAGES_TO,
            ISBN,
            ISSN,
            TYPE,
            FREE_INSTANCES,
            SORT
    );

    public static final Set<String> bookCatalogFilterSortValues = Set.of(
            NAME_ASCENDING,
            NAME_DESCENDING
    );
}
