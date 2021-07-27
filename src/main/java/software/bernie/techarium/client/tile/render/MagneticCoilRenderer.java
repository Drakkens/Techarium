package software.bernie.techarium.client.tile.render;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.techarium.client.tile.model.MagneticCoilModel;
import software.bernie.techarium.registry.BlockRegistry;
import software.bernie.techarium.tile.magneticcoils.MagneticCoilTile;

public class MagneticCoilRenderer extends GeoBlockRenderer<MagneticCoilTile> {
	
	public MagneticCoilRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new MagneticCoilModel());
	}
	
	@Override
	public void renderEarly(MagneticCoilTile animatable, MatrixStack stackIn, float ticks,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float partialTicks) {
		
		BlockState state = animatable.getLevel().getBlockState(animatable.getBlockPos());
		DirectionProperty directionState = BlockRegistry.GRAVMAGNET.getBlock().getDirectionProperty();
		
		if (state.hasProperty(directionState)) {
			Direction dir = state.getValue(directionState);
			stackIn.translate(0, 0, 0);
			if (dir == Direction.UP)
				stackIn.translate(0, -0.5, -0.5);
			else if (dir == Direction.DOWN)
				stackIn.translate(0, -0.5, 0.5);
		}
		
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red,
				green, blue, partialTicks);
	}
	
	@Override
	public RenderType getRenderType(MagneticCoilTile animatable, float partialTicks, MatrixStack stack,
									@Nullable IRenderTypeBuffer renderTypeBuffer,
									@Nullable IVertexBuilder vertexBuilder, int packedLightIn,
									ResourceLocation textureLocation) {
		return RenderType.entityCutoutNoCull(this.getTextureLocation(animatable));
	}
}
