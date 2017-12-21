package org.dotwebstack.framework.frontend.openapi.handlers;

import com.atlassian.oai.validator.model.ApiOperation;
import io.swagger.models.Swagger;
import io.swagger.models.properties.Property;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.NonNull;
import org.dotwebstack.framework.backend.ResultType;
import org.dotwebstack.framework.frontend.openapi.OpenApiSpecificationExtensions;
import org.dotwebstack.framework.frontend.openapi.entity.Entity;
import org.dotwebstack.framework.frontend.openapi.entity.GraphEntity;
import org.dotwebstack.framework.frontend.openapi.entity.TupleEntity;
import org.dotwebstack.framework.informationproduct.InformationProduct;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.glassfish.jersey.process.Inflector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GetRequestHandler implements Inflector<ContainerRequestContext, Response> {

  private static final Logger LOG = LoggerFactory.getLogger(GetRequestHandler.class);

  private final ApiOperation apiOperation;

  private final InformationProduct informationProduct;

  private final Map<MediaType, Property> schemaMap;

  private final RequestParameterMapper requestParameterMapper;

  private final Swagger swagger;

  private final ApiRequestValidator apiRequestValidator;

  GetRequestHandler(@NonNull ApiOperation apiOperation,
      @NonNull InformationProduct informationProduct, @NonNull Map<MediaType, Property> schemaMap,
      @NonNull RequestParameterMapper requestParameterMapper,
      @NonNull ApiRequestValidator apiRequestValidator, @NonNull Swagger swagger) {
    this.apiRequestValidator = apiRequestValidator;
    this.apiOperation = apiOperation;
    this.informationProduct = informationProduct;
    this.schemaMap = schemaMap;
    this.requestParameterMapper = requestParameterMapper;
    this.swagger = swagger;
  }

  public InformationProduct getInformationProduct() {
    return informationProduct;
  }

  public Map<MediaType, Property> getSchemaMap() {
    return schemaMap;
  }

  @Override
  public Response apply(@NonNull ContainerRequestContext context) {
    String path = context.getUriInfo().getPath();
    LOG.debug("Handling GET request for path {}", path);

    RequestParameters requestParameters = apiRequestValidator.validate(apiOperation, context);

    Map<String, String> parameterValues = requestParameterMapper.map(apiOperation.getOperation(),
        informationProduct, requestParameters);

    Response responseOk = null;

    if (ResultType.TUPLE.equals(informationProduct.getResultType())) {

      TupleQueryResult result = (TupleQueryResult) informationProduct.getResult(parameterValues);

      if (!result.hasNext() && hasIfEmptyOrNullVendorExtension()) {
        return Response.status(Status.NOT_FOUND).build();
      }

      TupleEntity entity =
          TupleEntity.builder().withQueryResult(result).withSchemaMap(schemaMap).build();

      responseOk = responseOk(entity);
    }
    if (ResultType.GRAPH.equals(informationProduct.getResultType())) {
      org.eclipse.rdf4j.query.GraphQueryResult result =
          (org.eclipse.rdf4j.query.GraphQueryResult) informationProduct.getResult(parameterValues);

      // result.hasNext() doesn't give an empty result set for a query to a non-existent resource
      // ...
      // LOG.error("Graph result hasNext: {}", result.hasNext());
      // Model model = QueryResults.asModel(result);
      // LOG.error("Size: {}", model.size());
      // LOG.error("Objects: {}", model.objects());

      if (!result.hasNext() && hasIfEmptyOrNullVendorExtension()) {
        return Response.status(Status.NOT_FOUND).build();
      }

      GraphEntity entity =
          (GraphEntity) GraphEntity.builder().withSchemaMap(schemaMap).withQueryResult(
              result).withApiDefinitions(swagger).withLdPathNamespaces(swagger).build();
      responseOk = responseOk(entity);
    }
    if (responseOk != null) {
      return responseOk;
    } else {
      LOG.error("Result type {} not supported for information product {}",
          informationProduct.getResultType(), informationProduct.getIdentifier());
    }
    return Response.serverError().build();
  }

  private Response responseOk(Entity entity) {
    if (entity != null) {
      return Response.ok(entity).build();
    }
    return null;
  }

  private boolean hasIfEmptyOrNullVendorExtension() {
    String notFoundStatusCode = Integer.toString(Status.NOT_FOUND.getStatusCode());

    Map<String, io.swagger.models.Response> responses = apiOperation.getOperation().getResponses();
    if (responses.containsKey(notFoundStatusCode)) {

      LOG.error("Vendor extensions: {}", responses.get(notFoundStatusCode).getVendorExtensions());
      return responses.get(notFoundStatusCode).getVendorExtensions().containsKey(
          OpenApiSpecificationExtensions.IF_EMPTY_OR_NULL);
    } else {
      // geen 404 gespecificeerd
    }
    return false;
  }

}

