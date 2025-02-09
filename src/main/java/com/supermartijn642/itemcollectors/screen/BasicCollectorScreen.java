package com.supermartijn642.itemcollectors.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.supermartijn642.core.TextComponents;
import com.supermartijn642.core.gui.ScreenUtils;
import com.supermartijn642.core.gui.TileEntityBaseScreen;
import com.supermartijn642.itemcollectors.CollectorTile;
import com.supermartijn642.itemcollectors.ItemCollectors;
import com.supermartijn642.itemcollectors.packet.*;
import net.minecraft.util.math.BlockPos;

/**
 * Created 7/15/2020 by SuperMartijn642
 */
public class BasicCollectorScreen extends TileEntityBaseScreen<CollectorTile> {

    private ShowAreaButton showAreaButton;

    public BasicCollectorScreen(BlockPos pos){
        super(TextComponents.empty().get(), pos);
    }

    @Override
    protected float sizeX(CollectorTile tile){
        return 202;
    }

    @Override
    protected float sizeY(CollectorTile tile){
        return 82;
    }

    @Override
    protected void addWidgets(CollectorTile tile){
        this.addWidget(new ArrowButton(30, 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseXRange(this.tilePos))));
        this.addWidget(new ArrowButton(30, 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseXRange(this.tilePos))));
        this.addWidget(new ArrowButton(73, 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseYRange(this.tilePos))));
        this.addWidget(new ArrowButton(73, 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseYRange(this.tilePos))));
        this.addWidget(new ArrowButton(116, 37, false, () -> ItemCollectors.CHANNEL.sendToServer(new PacketIncreaseZRange(this.tilePos))));
        this.addWidget(new ArrowButton(116, 63, true, () -> ItemCollectors.CHANNEL.sendToServer(new PacketDecreaseZRange(this.tilePos))));
        this.showAreaButton = this.addWidget(new ShowAreaButton(160, 45, () -> ItemCollectors.CHANNEL.sendToServer(new PacketToggleShowArea(this.tilePos))));
        this.showAreaButton.update(tile.showArea);
    }

    @Override
    protected void tick(CollectorTile tile){
        this.showAreaButton.update(tile.showArea);
    }

    @Override
    protected void render(int mouseX, int mouseY, CollectorTile tile){
        this.drawScreenBackground();

        ScreenUtils.drawCenteredString(TextComponents.blockState(tile.getBlockState()).get(), this.sizeX(tile) / 2f, 6);

        ScreenUtils.drawString(TextComponents.translation("gui.itemcollectors.basic_collector.range",
            (tile.rangeX * 2 + 1), (tile.rangeY * 2 + 1), (tile.rangeZ * 2 + 1)).get(), 8, 26);
        ScreenUtils.drawCenteredString(TextComponents.string("x:").get(), 25, 51);
        ScreenUtils.drawCenteredString(TextComponents.string("" + tile.rangeX).get(), 39, 52);
        ScreenUtils.drawCenteredString(TextComponents.string("y:").get(), 68, 51);
        ScreenUtils.drawCenteredString(TextComponents.string("" + tile.rangeY).get(), 82, 52);
        ScreenUtils.drawCenteredString(TextComponents.string("z:").get(), 111, 51);
        ScreenUtils.drawCenteredString(TextComponents.string("" + tile.rangeZ).get(), 125, 52);

        GlStateManager.enableAlphaTest();
    }
}
