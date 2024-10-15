package io.dataguardians.callbacks;

import io.dataguardians.model.ScriptOutput;

public interface OutputCallback {

    void onOutput(ScriptOutput outputToAppend);
}
