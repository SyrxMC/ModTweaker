package modtweaker2.mixin.mixins.client.actuallyadditions;

import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.blocks.render.RenderReconstructorLens;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderReconstructorLens.class)
public class RenderReconstructorLensMixin {

    @Inject(method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntity;DDDF)V", at = @At("HEAD"), cancellable = true)
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, CallbackInfo ci) {
        if (tile instanceof TileEntityAtomicReconstructor) {
            ItemStack stack = ((TileEntityAtomicReconstructor) tile).getStackInSlot(0);
            if (stack != null && !(stack.getItem() instanceof ILensItem)) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                int meta = tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
                if (meta == 0) {
                    GL11.glTranslatef(0.0F, -0.5F, 0.0F);
                    GL11.glTranslatef(-0.25F, 0.0F, -0.25F);
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (meta == 1) {
                    GL11.glTranslatef(0.0F, -1.53125F, 0.0F);
                    GL11.glTranslatef(-0.25F, 0.0F, -0.25F);
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (meta == 2) {
                    GL11.glTranslatef(0.0F, -1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.0F, -0.5F);
                    GL11.glTranslatef(-0.25F, -0.25F, 0.0F);
                }

                if (meta == 3) {
                    GL11.glTranslatef(0.0F, -1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.0F, 0.53125F);
                    GL11.glTranslatef(-0.25F, -0.25F, 0.0F);
                }

                if (meta == 4) {
                    GL11.glTranslatef(0.0F, -1.0F, 0.0F);
                    GL11.glTranslatef(0.53125F, 0.0F, 0.0F);
                    GL11.glTranslatef(0.0F, -0.25F, 0.25F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (meta == 5) {
                    GL11.glTranslatef(0.0F, -1.0F, 0.0F);
                    GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
                    GL11.glTranslatef(0.0F, -0.25F, 0.25F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                }

                Block block = Block.getBlockFromItem(stack.getItem());
                GL11.glScalef(0.5F, 0.5F, 0.5F);

                if (block.equals(Blocks.air)) {
                    AssetUtil.renderItemInWorld(stack, 0);
                } else {
                    GL11.glTranslatef(0.0F, .5F, 0.0F);
                    GL11.glTranslatef(.5F, 0.0F, 0.0F);
                    GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);

                    if (meta == 0)
                        GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                    else if (meta == 1)
                        GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);

                    AssetUtil.renderBlockInWorld(block, 0);
                }

                GL11.glPopMatrix();
                ci.cancel();
            }
        }
    }
}
