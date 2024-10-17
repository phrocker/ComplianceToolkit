package io.dataguardians.automation.datagen.datasynth.code.java.ast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassType {
    private String variableName;
    private String variableType;
}
