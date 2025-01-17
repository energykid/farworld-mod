package net.ennway.farworld.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.Random;
import java.util.random.RandomGenerator;

public class BasicLootModifier extends LootModifier {

    public static final MapCodec<BasicLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            // LootModifier#codecStart adds the conditions field.
            LootModifier.codecStart(inst).and(inst.group(
                    Codec.FLOAT.fieldOf("chance").forGetter(e -> e.chance),
                    Codec.INT.fieldOf("amount").forGetter(e -> e.amount),
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item_type").forGetter(e -> e.item_type)
            )).apply(inst, BasicLootModifier::new)
    );


    // Our extra properties.
    private final float chance;
    private final int amount;
    private final Item item_type;

    // First constructor parameter is the list of conditions. The rest is our extra properties.
    public BasicLootModifier(LootItemCondition[] conditions, float chance, int amount, Item item_type) {
        super(conditions);
        this.chance = chance;
        this.amount = amount;
        this.item_type = item_type;
    }

    // Return our codec here.
    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    // This is where the magic happens. Use your extra properties here if needed.
    // Parameters are the existing loot, and the loot context.
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < this.chance){
            generatedLoot.add(new ItemStack(this.item_type, this.amount));
        }
        return generatedLoot;
    }
}