package com.hollingsworth.ars_creo;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CreoConfig {

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue WHEEL_BASE_SPEED;
    public static ForgeConfigSpec.IntValue WHEEL_BONUS_SPEED;
    public static ForgeConfigSpec.DoubleValue WHEEL_STRESS_CAPACITY;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        WHEEL_BASE_SPEED = SERVER_BUILDER.comment("Base speed of the wheel").defineInRange("wheelBaseSpeed", 8, 0, Integer.MAX_VALUE);
        WHEEL_BONUS_SPEED = SERVER_BUILDER.comment("Speed of the wheel with a gold block in front").defineInRange("wheelMaxSpeed", 32, 0, Integer.MAX_VALUE);
        WHEEL_STRESS_CAPACITY = SERVER_BUILDER.comment("Stress capacity of the wheel").defineInRange("wheelStressCapacity", 8, 0.0, Double.MAX_VALUE);
        SERVER_CONFIG = SERVER_BUILDER.build();
    }
    
}
