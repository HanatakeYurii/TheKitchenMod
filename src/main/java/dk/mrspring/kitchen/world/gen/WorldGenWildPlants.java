package dk.mrspring.kitchen.world.gen;

import cpw.mods.fml.common.IWorldGenerator;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModLogger;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

import static dk.mrspring.kitchen.KitchenBlocks.*;

public class WorldGenWildPlants implements IWorldGenerator
{
    Block[] wildPlants = new Block[]{wild_lettuce, wild_tomato, wild_peanut, wild_strawberry, wild_vanilla, wild_onion};

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider.isSurfaceWorld())
        {
            int x = (chunkX * 16) + random.nextInt(16);
            int z = (chunkZ * 16) + random.nextInt(16);
            int y = world.getTopSolidOrLiquidBlock(x, z);

            int rand = random.nextInt(100);

            if (world.getBlock(x, y - 1, z) == Blocks.grass && world.getWorldInfo().getTerrainType() != WorldType.FLAT && rand < ModConfig.getKitchenConfig().lettuce_spawn_rate)
            {
                Block toGenerate = wildPlants[random.nextInt(wildPlants.length)];
                world.setBlock(x, y, z, toGenerate);
                ModLogger.print(ModLogger.DEBUG, "Generating " + toGenerate.getUnlocalizedName() + " at X: " + x + ", Y: " + y + ", Z: " + z);
            }
        }
    }
}
