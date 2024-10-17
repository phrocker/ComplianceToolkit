package io.dataguardians.automation.auditing;

import java.util.Optional;

public interface AuditorRule {

  Optional<Trigger> trigger(String text);

  boolean configure(String configuration);

  TriggerAction describeAction();

  boolean requiresSanitized();
}
//
