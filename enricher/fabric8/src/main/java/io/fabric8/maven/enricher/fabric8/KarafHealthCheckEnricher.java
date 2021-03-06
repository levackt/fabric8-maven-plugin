package io.fabric8.maven.enricher.fabric8;

import io.fabric8.kubernetes.api.model.Probe;
import io.fabric8.kubernetes.api.model.ProbeBuilder;
import io.fabric8.maven.enricher.api.AbstractHealthCheckEnricher;
import io.fabric8.maven.enricher.api.EnricherContext;

import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 * Enriches Karaf containers with health check probes.
 */
public class KarafHealthCheckEnricher extends AbstractHealthCheckEnricher {

    private static final int DEFAULT_HEALTH_CHECK_PORT = 8181;

    public KarafHealthCheckEnricher(EnricherContext buildContext) {
        super(buildContext, "karaf-health-check");
    }


    @Override
    protected Probe getReadinessProbe() {
        Probe probe = discoverKarafProbe("/readiness-check", 10);
        return probe;
    }

    @Override
    protected Probe getLivenessProbe() {
        Probe probe = discoverKarafProbe("/health-check", 180);
        return probe;
    }

    //
    // Karaf has a readiness/health URL exposed if the fabric8-karaf-check feature is installed.
    //
    private Probe discoverKarafProbe(String path, int initialDelay) {

        for (Plugin plugin : this.getProject().getBuildPlugins()) {
            if ("karaf-maven-plugin".equals(plugin.getArtifactId())) {
                Xpp3Dom configuration = (Xpp3Dom) plugin.getConfiguration();
                if (configuration == null)
                    return null;
                Xpp3Dom startupFeatures = configuration.getChild("startupFeatures");
                if (startupFeatures == null)
                    return null;

                for (Xpp3Dom feature : startupFeatures.getChildren("feature")) {
                    if ("fabric8-karaf-checks".equals(feature.getValue())) {
                        // TODO: handle the case where the user changes the default port
                        return new ProbeBuilder().withNewHttpGet().
                                withNewPort(DEFAULT_HEALTH_CHECK_PORT).withPath(path).endHttpGet().withInitialDelaySeconds(initialDelay).build();
                    }
                }
            }
        }
        return null;
    }

}
