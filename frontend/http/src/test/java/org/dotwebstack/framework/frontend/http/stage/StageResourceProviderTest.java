package org.dotwebstack.framework.frontend.http.stage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.dotwebstack.framework.ApplicationProperties;
import org.dotwebstack.framework.config.ConfigurationBackend;
import org.dotwebstack.framework.config.ConfigurationException;
import org.dotwebstack.framework.frontend.http.site.Site;
import org.dotwebstack.framework.frontend.http.site.SiteResourceProvider;
import org.dotwebstack.framework.test.DBEERPEDIA;
import org.dotwebstack.framework.vocabulary.ELMO;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.GraphQuery;
import org.eclipse.rdf4j.query.impl.IteratingGraphQueryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.sail.SailRepositoryConnection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StageResourceProviderTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Mock
  private ApplicationProperties applicationProperties;

  @Mock
  private SiteResourceProvider siteResourceProvider;

  @Mock
  private Site site;

  @Mock
  private ConfigurationBackend configurationBackend;

  @Mock
  private SailRepository configurationRepository;

  @Mock
  private SailRepositoryConnection configurationRepositoryConnection;

  @Mock
  private GraphQuery graphQuery;

  private ValueFactory valueFactory = SimpleValueFactory.getInstance();

  private StageResourceProvider stageResourceProvider;

  @Before
  public void setUp() {
    stageResourceProvider = new StageResourceProvider(configurationBackend, siteResourceProvider,
        applicationProperties);

    when(configurationBackend.getRepository()).thenReturn(configurationRepository);
    when(configurationRepository.getConnection()).thenReturn(configurationRepositoryConnection);
    when(configurationRepositoryConnection.prepareGraphQuery(anyString())).thenReturn(graphQuery);

    when(siteResourceProvider.get(any())).thenReturn(site);

    when(applicationProperties.getSystemGraph()).thenReturn(DBEERPEDIA.SYSTEM_GRAPH_IRI);
  }

  @Test
  public void constructor_ThrowsException_WithMissingConfigurationBackend() {
    // Assert
    thrown.expect(NullPointerException.class);

    // Act
    new StageResourceProvider(null, siteResourceProvider, applicationProperties);
  }

  @Test
  public void constructor_ThrowsException_WithMissingSiteResourceProvider() {
    // Assert
    thrown.expect(NullPointerException.class);

    // Act
    new StageResourceProvider(configurationBackend, null, applicationProperties);
  }

  @Test
  public void constructor_ThrowsException_WithMissingApplicationProperties() {
    // Assert
    thrown.expect(NullPointerException.class);

    // Act
    new StageResourceProvider(configurationBackend, siteResourceProvider, null);
  }

  @Test
  public void loadResources_LoadStage_WithValidData() {
    // Arrange
    when(graphQuery.evaluate()).thenReturn(new IteratingGraphQueryResult(ImmutableMap.of(),
        ImmutableList.of(valueFactory.createStatement(DBEERPEDIA.STAGE, RDF.TYPE, ELMO.STAGE),
            valueFactory.createStatement(DBEERPEDIA.STAGE, ELMO.SITE_PROP, DBEERPEDIA.SITE),
            valueFactory.createStatement(DBEERPEDIA.STAGE, ELMO.BASE_PATH, DBEERPEDIA.BASE_PATH))));

    // Act
    stageResourceProvider.loadResources();

    // Assert
    assertThat(stageResourceProvider.getAll().entrySet(), hasSize(1));
    Stage stage = stageResourceProvider.get(DBEERPEDIA.STAGE);
    assertThat(stage, is(not(nullValue())));
    assertThat(stage.getSite(), equalTo(site));
    assertThat(stage.getBasePath(), equalTo(DBEERPEDIA.BASE_PATH.stringValue()));
  }

  @Test
  public void loadResources_LoadMultipleStages_WithValidData() {
    // Arrange
    when(graphQuery.evaluate()).thenReturn(new IteratingGraphQueryResult(ImmutableMap.of(),
        ImmutableList.of(valueFactory.createStatement(DBEERPEDIA.STAGE, RDF.TYPE, ELMO.STAGE),
            valueFactory.createStatement(DBEERPEDIA.STAGE, ELMO.SITE_PROP, DBEERPEDIA.SITE),
            valueFactory.createStatement(DBEERPEDIA.STAGE, ELMO.BASE_PATH, DBEERPEDIA.BASE_PATH),
            valueFactory.createStatement(DBEERPEDIA.SECOND_STAGE, RDF.TYPE, ELMO.STAGE),
            valueFactory.createStatement(DBEERPEDIA.SECOND_STAGE, ELMO.SITE_PROP,
                DBEERPEDIA.SITE))));

    // Act
    stageResourceProvider.loadResources();

    // Assert
    assertThat(stageResourceProvider.getAll().entrySet(), hasSize(2));
  }

  @Test
  public void loadResources_ThrowsException_WithMissingSite() {
    // Arrange
    when(graphQuery.evaluate()).thenReturn(new IteratingGraphQueryResult(ImmutableMap.of(),
        ImmutableList.of(valueFactory.createStatement(DBEERPEDIA.STAGE, RDF.TYPE, ELMO.STAGE))));

    // Assert
    thrown.expect(ConfigurationException.class);
    thrown.expectMessage(String.format("No <%s> statement has been found for stage <%s>.",
        ELMO.SITE_PROP, DBEERPEDIA.STAGE));

    // Act
    stageResourceProvider.loadResources();

  }

}
