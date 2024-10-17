package io.dataguardians.automation.datagen.datasynth;

import java.util.Optional;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Generic interface for sampler capabilities.
 */
public interface SamplerBase {

    Optional<JsonNode> nextSample();
}
