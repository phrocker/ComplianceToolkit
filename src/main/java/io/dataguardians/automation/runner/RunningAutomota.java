package io.dataguardians.automation.runner;

import io.dataguardians.automation.sideeffects.SideEffect;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
public class RunningAutomota extends Automota {

  AutomationRunner runner;
  Future<Set<SideEffect>> future;

  public boolean isDone() throws ExecutionException, InterruptedException {
    if (null != future) {
      var res = future.isDone();
      if (res) {
        future.get();
        return true;
      }
      return false;
    }
    throw new RuntimeException("Cannot determine if future is done");
  }

  public static RunningAutomotaBuilder createBuilderFrom(Automota other) {
    return RunningAutomota.builder()
        .asUser(other.getAsUser())
        .identifier(other.getIdentifier())
        .systems(other.getSystems())
        .instanceIdentifier(other.getInstanceIdentifier())
        .sideEffects(other.getSideEffects());
  }
}
