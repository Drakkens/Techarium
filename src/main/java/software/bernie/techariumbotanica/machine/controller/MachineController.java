package software.bernie.techariumbotanica.machine.controller;

import javafx.util.Pair;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.inventory.container.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import software.bernie.techariumbotanica.machine.interfaces.IFactory;
import software.bernie.techariumbotanica.client.screen.draw.IDrawable;
import software.bernie.techariumbotanica.machine.addon.energy.EnergyStorageAddon;
import software.bernie.techariumbotanica.machine.interfaces.IContainerComponentProvider;
import software.bernie.techariumbotanica.machine.interfaces.IWidgetProvider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static software.bernie.techariumbotanica.client.screen.draw.GuiAddonTextures.BOTARIUM_BASE_TIER_1;

public class MachineController implements IWidgetProvider, IContainerComponentProvider {

    protected final Supplier<BlockPos> posSupplier;
    protected final TileEntity tile;

    private final int tier;
    private Pair<Integer, Integer> backgroundSizeXY;

    private IDrawable background;

    private Map<Integer, Pair<Integer, Integer>> playerInvSlotsXY = new HashMap<>();

    private boolean isPowered;
    private EnergyStorageAddon energyStorage;
    private final LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.of(this::getEnergyStorage);



    public MachineController(TileEntity tile, Supplier<BlockPos> posSupplier, int tier) {
        this.posSupplier = posSupplier;
        this.tile = tile;
        this.background = BOTARIUM_BASE_TIER_1;
        this.backgroundSizeXY = new Pair<>(204, 183);
        this.tier = tier;
        this.isPowered = false;
    }

    @Nonnull
    public EnergyStorageAddon getEnergyStorage() {
        return energyStorage;
    }

    public int getTier() {
        return tier;
    }

    public IDrawable getBackground() {
        return background;
    }

    public boolean isPowered() {
        return isPowered;
    }

    public void setPowered(boolean powered) {
        isPowered = powered;
    }

    public void setEnergyStorage(int capacity, int maxIO, int xPos, int yPos) {
        int newX = xPos;
        if(backgroundSizeXY.getKey() % 2 != 0){
            newX ++;
        }
        energyStorage = new EnergyStorageAddon(tier * capacity, tier * maxIO, newX, yPos, backgroundSizeXY);
    }

    public Pair<Integer, Integer> getBackgroundSizeXY() {
        return backgroundSizeXY;
    }

    public void setBackground(IDrawable background, int sizeX, int sizeY) {
        this.backgroundSizeXY = new Pair<>(sizeX, sizeY);
        this.background = background;
    }

    public Map<Integer, Pair<Integer, Integer>> getPlayerInvSlotsXY() {
        if (playerInvSlotsXY.isEmpty()) {
            return getNormalSlotLocations();
        }
        return playerInvSlotsXY;
    }

    public void setPlayerInvSlotsXY(Map<Integer, Pair<Integer, Integer>> playerInvSlotsXY) {
        this.playerInvSlotsXY = playerInvSlotsXY;
    }

    private Map<Integer, Pair<Integer, Integer>> getNormalSlotLocations() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                playerInvSlotsXY.put(j + i * 9 + 9, new Pair<>(7 + j * 18, 103 + i * 18));
            }
        }
        return playerInvSlotsXY;
    }

    public LazyOptional<IEnergyStorage> getLazyEnergyStorage() {
        return lazyEnergyStorage;
    }

    @Override
    public List<IFactory<? extends Widget>> getGuiWidgets() {
        List<IFactory<? extends Widget>> widgets = new ArrayList<>();
        if (isPowered) {
            widgets.addAll(energyStorage.getGuiWidgets());
        }

        return widgets;
    }

    @Override
    public List<IFactory<? extends Slot>> getContainerComponents() {
        List<IFactory<? extends Slot>> components = new ArrayList<>();


        return components;
    }
}
