package io.dataguardians.util;

import io.dataguardians.automation.sideeffects.SideEffect;

public class PrintUtils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printWarning(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    public static void printWarning(SideEffect effect) {
        System.out.println(
            ANSI_RED
                + "SideEffect"
                + ANSI_RESET
                + "{"
                + ANSI_YELLOW
                + "type="
                + ANSI_RESET
                + ANSI_GREEN
                + effect.getType()
                + ANSI_RESET
                + ","
                + ANSI_YELLOW
                + "asset='"
                + ANSI_RESET
                + effect.getAsset()
                + '\''
                + ","
                + " assetLocation='"
                + effect.getAssetLocation()
                + '\''
                + ", "
                + ANSI_YELLOW
                + " sideEffectDescription='"
                + ANSI_RESET
                + effect.getSideEffectDescription()
                + '\''
                + "} ");
    }
}
