package org.dotwebstack.framework.config;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.dotwebstack.framework.InformationProduct;
import org.dotwebstack.framework.Registry;
import org.dotwebstack.framework.Source;
import org.dotwebstack.framework.vocabulary.ELMO;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;

@Service
public class ConfigLoader implements ResourceLoaderAware {

  private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

  private ConfigProperties configProperties;

  private Registry registry;

  private ResourceLoader resourceLoader;

  @Autowired
  public ConfigLoader(ConfigProperties productProperties, Registry productRegistry) {
    this.configProperties = productProperties;
    this.registry = productRegistry;
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @PostConstruct
  public void loadConfiguration() throws IOException {
    Resource[] resources =
        ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(
            String.format("classpath:%s/**", configProperties.getConfigPath()));

    if (resources.length == 0) {
      logger.info("No product configuration files found.");
      return;
    }

    Model configurationModel = loadResources(resources);
    registerInformationProducts(configurationModel);
  }

  private Model loadResources(Resource[] resources) throws IOException {
    Model configurationModel = new LinkedHashModel();

    for (Resource configResource : resources) {
      InputStream configResourceStream = configResource.getInputStream();
      String extension = FilenameUtils.getExtension(configResource.getFilename());

      if (!ConfigFileFormats.containsExtension(extension)) {
        logger.debug("File extension not supported, ignoring file: \"%s\".",
            configResource.getFilename());
        continue;
      }

      try {
        Model model =
            Rio.parse(configResourceStream, ELMO.NAMESPACE, ConfigFileFormats.getFormat(extension));
        configurationModel.addAll(model);
      } catch (RDFParseException ex) {
        throw new ConfigException(ex.getMessage(), ex);
      } finally {
        configResourceStream.close();
      }
    }

    return configurationModel;
  }

  private void registerInformationProducts(Model configurationModel) {
    for (Statement typeStatement : configurationModel.filter(null, RDF.TYPE,
        ELMO.INFORMATION_PRODUCT)) {
      InformationProduct product =
          createInformationProductFromModel((IRI) typeStatement.getSubject());
      registry.registerInformationProduct(product);
      logger.debug("Loaded product \"%s\".", product.getIdentifier());
    }
  }

  private InformationProduct createInformationProductFromModel(IRI identifier) {
    return new InformationProduct(identifier, new Source() {});
  }

}
