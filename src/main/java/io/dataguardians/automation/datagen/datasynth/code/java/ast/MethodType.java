package io.dataguardians.automation.datagen.datasynth.code.java.ast;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodType {
    private String methodName;
    private String methodReturnType;

    private String accessModifier;

    private String impl;

    @Builder.Default
    private List<ClassType> methodArguments = new ArrayList<>();
}
