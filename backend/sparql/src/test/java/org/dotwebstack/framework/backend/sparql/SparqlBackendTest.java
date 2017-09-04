package org.dotwebstack.framework.backend.sparql;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.dotwebstack.framework.test.DBEERPEDIA;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SparqlBackendTest {

  @Mock
  private SPARQLRepository repository;

  @Mock
  private SparqlBackendSourceFactory sourceFactory;

  @Test
  public void builder() {
    // Act
    SparqlBackend backend =
        new SparqlBackend.Builder(DBEERPEDIA.BACKEND, repository, sourceFactory).build();

    // Assert
    assertThat(backend.getIdentifier(), equalTo(DBEERPEDIA.BACKEND));
    assertThat(backend.getRepository(), equalTo(repository));
    assertThat(backend.getSourceFactory(), equalTo(sourceFactory));
  }

  @Test
  public void reuseConnection() {
    // Arrange
    SparqlBackend backend =
        new SparqlBackend.Builder(DBEERPEDIA.BACKEND, repository, sourceFactory).build();
    when(repository.getConnection()).thenReturn(mock(RepositoryConnection.class));

    // Act
    RepositoryConnection repositoryConnection1 = backend.getConnection();
    RepositoryConnection repositoryConnection2 = backend.getConnection();

    // Assert
    assertThat(repositoryConnection1, notNullValue());
    assertThat(repositoryConnection1, equalTo(repositoryConnection2));
    verify(repository).getConnection();
  }

}
