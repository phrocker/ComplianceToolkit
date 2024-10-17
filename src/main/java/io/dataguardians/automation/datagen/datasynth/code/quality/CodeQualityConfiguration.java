package io.dataguardians.automation.datagen.datasynth.code.quality;

import io.dataguardians.automation.datagen.datasynth.rules.ComplianceConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
/**
 * Code quality configuration object that extends the compliance configuration.
 */
public class CodeQualityConfiguration extends ComplianceConfiguration {

    String codeUrl;
    @Builder.Default
    int maxMethodLength = -1;
}
