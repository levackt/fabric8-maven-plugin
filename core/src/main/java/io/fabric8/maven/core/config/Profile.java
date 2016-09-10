/*
 * Copyright 2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package io.fabric8.maven.core.config;

import java.io.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * A profile is a named configuration with enricher and generator configs.
 *
 * @author roland
 * @since 24/07/16
 */
public class Profile {

    /**
     * Profile name
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Enricher configurations
     */
    @JsonProperty(value = "enricher")
    private ProcessorConfig enricherConfig;

    /**
     * Generator configurations
     */
    @JsonProperty(value = "generator")
    private ProcessorConfig generatorConfig;

    public String getName() {
        return name;
    }

    public ProcessorConfig getEnricherConfig() {
        return enricherConfig;
    }

    public ProcessorConfig getGeneratorConfig() {
        return generatorConfig;
    }

}
