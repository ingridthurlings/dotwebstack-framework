package org.dotwebstack.framework.frontend.ld.mappers;

import javax.ws.rs.HttpMethod;
import lombok.NonNull;
import org.dotwebstack.framework.frontend.http.HttpConfiguration;
import org.dotwebstack.framework.frontend.ld.handlers.RedirectionRequestHandler;
import org.dotwebstack.framework.frontend.ld.redirection.Redirection;
import org.dotwebstack.framework.frontend.ld.redirection.RedirectionResourceProvider;
import org.glassfish.jersey.server.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LdRedirectionRequestMapper {

  private static final Logger LOG = LoggerFactory.getLogger(LdRedirectionRequestMapper.class);

  private final RedirectionResourceProvider redirectionResourceProvider;

  @Autowired
  public LdRedirectionRequestMapper(
      @NonNull RedirectionResourceProvider redirectionResourceProvider) {
    this.redirectionResourceProvider = redirectionResourceProvider;
  }

  public void loadRedirections(HttpConfiguration httpConfiguration) {
    for (Redirection redirection : redirectionResourceProvider.getAll().values()) {
      if (redirection.getStage() != null) {
        mapRedirection(redirection, httpConfiguration);
      } else {
        LOG.warn("Redirection '{}' is not mapped to a stage.", redirection.getIdentifier());
      }
    }
  }

  private void mapRedirection(Redirection redirection, HttpConfiguration httpConfiguration) {
    String basePath = redirection.getStage().getFullPath();

    String urlPattern = redirection.getUrlPattern().replaceAll("^\\^", "");
    String absolutePathRegex = String.format("%s{any: %s}", basePath, urlPattern);

    Resource.Builder resourceBuilder = Resource.builder().path(absolutePathRegex);
    resourceBuilder.addMethod(HttpMethod.GET).handledBy(new RedirectionRequestHandler(redirection));

    if (!httpConfiguration.resourceAlreadyRegistered(absolutePathRegex)) {
      httpConfiguration.registerResources(resourceBuilder.build());
      LOG.debug("Mapped GET redirection for request path {}", absolutePathRegex);
    } else {
      LOG.error("Resource <%s> is not registered", absolutePathRegex);
    }
  }

}
