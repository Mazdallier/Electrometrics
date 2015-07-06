package org.halvors.electrometrics.client.key;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyHandler {
    public static boolean getIsKeyPressed(KeyBinding keyBinding) {
        int keyCode = keyBinding.getKeyCode();

        return keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode);
    }
}