package org.dotwebstack.framework.test;

import java.util.Calendar;
import java.util.Date;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

public final class DBEERPEDIA {

  public static final String NAMESPACE = "http://dbeerpedia.org#";

  public static final Literal BASE_PATH;

  public static final IRI NAME;

  public static final IRI FOUNDATION;

  public static final IRI SINCE;

  public static final IRI PLACE;

  public static final IRI FTE;

  public static final String BREWERY_DAVO_NAME = "Davo Bieren Deventer";

  public static final Literal BREWERY_DAVO;

  public static final String OBJECT_NAMESPACE = "http://dbeerpedia.org/id/";

  public static final IRI URL_PATTERN;

  public static final Literal DOMAIN;

  public static final Literal DOMAIN_NL;

  public static final IRI BACKEND;

  public static final IRI SECOND_BACKEND;

  public static final Literal ENDPOINT;

  public static final IRI BREWERIES;

  public static final IRI TUPLE_BREWERIES;

  public static final IRI GRAPH_BREWERIES;

  public static final Literal BREWERIES_LABEL;

  public static final Literal WINERIES_LABEL;

  public static final Literal SELECT_ALL_QUERY;

  public static final Literal CONSTRUCT_ALL_QUERY;

  public static final Literal ASK_ALL_QUERY;

  public static final Literal MALFORMED_QUERY;

  public static final IRI BROUWTOREN;

  public static final Literal BROUWTOREN_NAME;

  public static final Literal BROUWTOREN_YEAR_OF_FOUNDATION;

  public static final Literal BROUWTOREN_DATE_OF_FOUNDATION;

  public static final Literal BROUWTOREN_PLACE;

  public static final Literal BROUWTOREN_CRAFT_MEMBER;

  public static final Literal BROUWTOREN_FTE;

  public static final Literal BROUWTOREN_LITERS_PER_YEAR;

  public static final IRI MAXIMUS;

  public static final Literal MAXIMUS_NAME;

  public static final Literal MAXIMUS_YEAR_OF_FOUNDATION;

  public static final Literal MAXIMUS_DATE_OF_FOUNDATION;

  public static final Literal MAXIMUS_PLACE;

  public static final Literal MAXIMUS_FTE;

  public static final IRI PERCENTAGES_INFORMATION_PRODUCT;

  public static final IRI ORIGIN_INFORMATION_PRODUCT;

  public static final IRI GRAPH_BREWERY_LIST_REPRESENTATION;

  public static final IRI TUPLE_BREWERY_LIST_REPRESENTATION;

  public static final IRI BREWERY_REPRESENTATION;

  public static final IRI ID2DOC_REDIRECTION;

  public static final IRI ID2DOC_DUMMY_REDIRECTION;

  public static final IRI SUBJECT_FROM_URL;

  public static final Literal SUBJECT_FROM_URL_PATTERN;

  public static final Literal SUBJECT_FROM_URL_TEMPLATE;

  public static final String SUBJECT_PARAMETER_NAME = "SubjectParameter";

  public static final IRI SUBJECT_PARAMETER;

  public static final Literal ID2DOC_URL_PATTERN;

  public static final Literal ID2DOC_TARGET_URL;

  @java.lang.SuppressWarnings("squid:S1075")
  public static final String BREWERY_ID_PATH = "/id/brewery";

  @java.lang.SuppressWarnings("squid:S1075")
  public static final String BREWERY_DOC_PATH = "/doc/brewery";

  public static final IRI BREWERY_APPEARANCE;

  public static final IRI CUSTOM_APPEARANCE_PROP;

  public static final String OPENAPI_DESCRIPTION = "DBeerpedia API";

  public static final String OPENAPI_HOST = "dbpeerpedia.org";

  @java.lang.SuppressWarnings("squid:S1075")
  public static final String OPENAPI_BASE_PATH = "/api/v1";

  public static final String ORG_HOST = "dbeerpedia.org";

  public static final String NL_HOST = "dbeerpedia.nl";

  public static final String SYSTEM_GRAPH = "http://dbeerpedia.nl/configuration/Theatre";

  public static final String RESOURCE_PATH = "file:./config";

  public static final IRI SYSTEM_GRAPH_IRI;

  public static final IRI SITE;

  public static final IRI SITE_NL;

  public static final IRI STAGE;

  public static final IRI SECOND_STAGE;

  public static final String NAME_PARAMETER = "nameParameter";

  public static final IRI NAME_PARAMETER_ID;

  public static final Literal NAME_PARAMETER_VALUE;

  public static final String NAME_PARAMETER_VALUE_STRING;

  public static final IRI PLACE_PARAMETER_ID;

  public static final Literal PLACE_PARAMETER_VALUE;

  public static final String PLACE_PARAMETER_VALUE_STRING;

  public static final String URL_PATTERN_VALUE = "/holyBeer";

  public static final IRI BREWERY_TYPE;

  static {
    ValueFactory valueFactory = SimpleValueFactory.getInstance();
    SYSTEM_GRAPH_IRI = valueFactory.createIRI(DBEERPEDIA.SYSTEM_GRAPH);
    SITE = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Site");
    STAGE = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Stage");
    SECOND_STAGE = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "SecondStage");
    DOMAIN = valueFactory.createLiteral(ORG_HOST);
    BASE_PATH = valueFactory.createLiteral("/special");

    NAME_PARAMETER_ID = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, NAME_PARAMETER);
    NAME_PARAMETER_VALUE_STRING = "name";
    NAME_PARAMETER_VALUE = valueFactory.createLiteral(NAME_PARAMETER_VALUE_STRING);

    PLACE_PARAMETER_ID = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "placeParameter");
    PLACE_PARAMETER_VALUE_STRING = "place";
    PLACE_PARAMETER_VALUE = valueFactory.createLiteral(PLACE_PARAMETER_VALUE_STRING);

    FOUNDATION = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Foundation");
    NAME = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Name");
    SINCE = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Since");
    PLACE = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Place");
    FTE = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "FTE");

    BREWERY_DAVO = valueFactory.createLiteral(BREWERY_DAVO_NAME);

    SITE_NL = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "SiteNL");
    DOMAIN_NL = valueFactory.createLiteral(NL_HOST);

    BACKEND = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Backend");
    SECOND_BACKEND = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "SecondBackend");
    ENDPOINT = valueFactory.createLiteral("http://localhost:8080/sparql", XMLSchema.ANYURI);

    BREWERIES = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Breweries");
    TUPLE_BREWERIES = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "TupleBreweries");
    GRAPH_BREWERIES = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "GraphBreweries");
    BREWERIES_LABEL = valueFactory.createLiteral("Beer breweries in The Netherlands");
    WINERIES_LABEL = valueFactory.createLiteral("Wineries in The Netherlands");
    MALFORMED_QUERY = valueFactory.createLiteral("CONSTRUCT ?s ?p ?o WHERE { ?s ?p ?o }");
    SELECT_ALL_QUERY = valueFactory.createLiteral("SELECT ?s ?p ?o WHERE { ?s ?p ?o }");
    CONSTRUCT_ALL_QUERY = valueFactory.createLiteral("CONSTRUCT { ?s ?p ?o } WHERE { ?s ?p ?o }");
    ASK_ALL_QUERY = valueFactory.createLiteral("ASK WHERE { ?s ?p ?o }");

    BROUWTOREN = valueFactory.createIRI(DBEERPEDIA.OBJECT_NAMESPACE,
        "brewery/900e5c1c-d292-48c8-b9bd-1baf02ee2d2c");
    BROUWTOREN_NAME = valueFactory.createLiteral("Brouwtoren");
    BROUWTOREN_YEAR_OF_FOUNDATION = valueFactory.createLiteral(2014);
    BROUWTOREN_DATE_OF_FOUNDATION =
        valueFactory.createLiteral(createDate(2014, Calendar.JANUARY, 1));
    BROUWTOREN_CRAFT_MEMBER = valueFactory.createLiteral(true);
    BROUWTOREN_FTE = valueFactory.createLiteral(1.8);
    BROUWTOREN_LITERS_PER_YEAR = valueFactory.createLiteral(Long.MAX_VALUE);
    BROUWTOREN_PLACE = valueFactory.createLiteral("Nijmegen");

    MAXIMUS = valueFactory.createIRI(DBEERPEDIA.OBJECT_NAMESPACE,
        "brewery/0c0d7df2-a830-11e7-abc4-cec278b6b50a");
    MAXIMUS_NAME = valueFactory.createLiteral("Maximus");
    MAXIMUS_YEAR_OF_FOUNDATION = valueFactory.createLiteral(2012);
    MAXIMUS_DATE_OF_FOUNDATION =
        valueFactory.createLiteral(createDate(2012, Calendar.SEPTEMBER, 27));
    MAXIMUS_FTE = valueFactory.createLiteral(2.4);
    MAXIMUS_PLACE = valueFactory.createLiteral("Utrecht");

    PERCENTAGES_INFORMATION_PRODUCT = valueFactory.createIRI(DBEERPEDIA.OBJECT_NAMESPACE,
        "brewery/information/alcohol-percentages");
    ORIGIN_INFORMATION_PRODUCT =
        valueFactory.createIRI(DBEERPEDIA.OBJECT_NAMESPACE, "brewery/information/origins");

    GRAPH_BREWERY_LIST_REPRESENTATION =
        valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "GraphBreweryListRepresentation");

    TUPLE_BREWERY_LIST_REPRESENTATION =
        valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "TupleBreweryListRepresentation");

    BREWERY_REPRESENTATION = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "BreweryRepresentation");

    ID2DOC_REDIRECTION = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Id2doc");

    ID2DOC_DUMMY_REDIRECTION = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Id2docDummy");

    ID2DOC_URL_PATTERN = valueFactory.createLiteral("^\\/id\\/(.+)$");

    ID2DOC_TARGET_URL = valueFactory.createLiteral("\\/doc\\/$1");

    SUBJECT_FROM_URL = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "SubjectFromUrl");

    SUBJECT_FROM_URL_PATTERN = valueFactory.createLiteral("http://{domain}/doc/{reference}");

    SUBJECT_FROM_URL_TEMPLATE = valueFactory.createLiteral("http://dbeerpedia.org/id/{reference}");

    SUBJECT_PARAMETER = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, SUBJECT_PARAMETER_NAME);

    BREWERY_APPEARANCE = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "BreweryAppearance");

    CUSTOM_APPEARANCE_PROP = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "customAppearanceProp");

    URL_PATTERN = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "urlPattern");

    BREWERY_TYPE = valueFactory.createIRI(DBEERPEDIA.NAMESPACE, "Brewery");
  }

  private DBEERPEDIA() {
    throw new IllegalStateException(
        String.format("%s is not meant to be instantiated.", DBEERPEDIA.class));
  }

  private static Date createDate(int year, int month, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    return calendar.getTime();
  }

}
