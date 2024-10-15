package io.dataguardians.automation.runner;

import io.dataguardians.automation.sideeffects.SideEffect;
import java.util.List;
import io.dataguardians.model.Host;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Automota {

  List<SideEffect> sideEffects;

  Long asUser;

  Long identifier;

  Long databaseId;

  String instanceIdentifier;

  List<Host> systems;
}
