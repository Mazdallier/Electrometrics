package org.halvors.electrometrics.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.halvors.electrometrics.Reference;
import org.halvors.electrometrics.client.gui.component.IGuiComponent;
import org.halvors.electrometrics.common.component.IComponent;
import org.halvors.electrometrics.common.tile.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SideOnly(Side.CLIENT)
public abstract class GuiComponentContainer extends GuiContainer implements IGui {
	final Set<IComponent> components = new HashSet<>();

	final ResourceLocation defaultResource = new ResourceLocation(Reference.PREFIX + "gui/guiContainerBlank.png");
	final TileEntity tileEntity;

	GuiComponentContainer(TileEntity tileEntity, Container container) {
		super(container);

		this.tileEntity = tileEntity;
	}

	public void renderScaledText(String text, int x, int y, int color, int maxX) {
		int length = fontRendererObj.getStringWidth(text);

		if (length <= maxX) {
			fontRendererObj.drawString(text, x, y, color);
		} else {
			float scale = (float) maxX / length;
			float reverse = 1 / scale;
			float yAdd = 4 - (scale * 8) / 2F;

			GL11.glPushMatrix();

			GL11.glScalef(scale, scale, scale);
			fontRendererObj.drawString(text, (int) (x * reverse), (int) ((y * reverse) + yAdd), color);

			GL11.glPopMatrix();
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		fontRendererObj.drawString(tileEntity.getName(), (xSize / 2) - (fontRendererObj.getStringWidth(tileEntity.getName()) / 2), 6, 0x404040);
		fontRendererObj.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);

		int xAxis = (mouseX - (width - xSize) / 2);
		int yAxis = (mouseY - (height - ySize) / 2);

		for (IComponent component : components) {
			if (component instanceof IGuiComponent) {
				IGuiComponent guiComponent = (IGuiComponent) component;
				guiComponent.renderForeground(xAxis, yAxis);
			}
		}
	}

	protected boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
		return func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY); // isPointInRegion
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(defaultResource);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;

		drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);

		int xAxis = mouseX - guiWidth;
		int yAxis = mouseY - guiHeight;

		for (IComponent component : components) {
			if (component instanceof IGuiComponent) {
				IGuiComponent guiComponent = (IGuiComponent) component;
				guiComponent.renderBackground(xAxis, yAxis, guiWidth, guiHeight);
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		int xAxis = (mouseX - (width - xSize) / 2);
		int yAxis = (mouseY - (height - ySize) / 2);

		for (IComponent component : components) {
			if (component instanceof IGuiComponent) {
				IGuiComponent guiComponent = (IGuiComponent) component;
				guiComponent.preMouseClicked(xAxis, yAxis, button);
			}
		}

		super.mouseClicked(mouseX, mouseY, button);

		for (IComponent component : components) {
			if (component instanceof IGuiComponent) {
				IGuiComponent guiComponent = (IGuiComponent) component;
				guiComponent.mouseClicked(xAxis, yAxis, button);
			}
		}
	}

	@Override
	protected void drawCreativeTabHoveringText(String text, int x, int y) {
		func_146283_a(Collections.singletonList(text), x, y);
	}

	@Override
	protected void func_146283_a(List list, int x, int y) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT + GL11.GL_LIGHTING_BIT);
		super.func_146283_a(list, x, y);
		GL11.glPopAttrib();
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button, long ticks) {
		super.mouseClickMove(mouseX, mouseY, button, ticks);

		int xAxis = (mouseX - (width - xSize) / 2);
		int yAxis = (mouseY - (height - ySize) / 2);

		for (IComponent component : components) {
			if (component instanceof IGuiComponent) {
				IGuiComponent guiComponent = (IGuiComponent) component;
				guiComponent.mouseClickMove(xAxis, yAxis, button, ticks);
			}
		}
	}

	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int type) {
		super.mouseMovedOrUp(mouseX, mouseY, type);

		int xAxis = (mouseX - (width - xSize) / 2);
		int yAxis = (mouseY - (height - ySize) / 2);

		for (IComponent component : components) {
			if (component instanceof IGuiComponent) {
				IGuiComponent guiComponent = (IGuiComponent) component;
				guiComponent.mouseMovedOrUp(xAxis, yAxis, type);
			}
		}
	}

	public void handleMouse(Slot slot, int slotIndex, int button, int modifier) {
		handleMouseClick(slot, slotIndex, button, modifier);
	}

	@Override
	public void drawTexturedRect(int x, int y, int u, int v, int w, int h) {
		drawTexturedModalRect(x, y, u, v, w, h);
	}

	@Override
	public void drawTexturedRectFromIcon(int x, int y, IIcon icon, int w, int h) {
		drawTexturedModelRectFromIcon(x, y, icon, w, h);
	}

	@Override
	public void displayTooltip(String text, int x, int y) {
		drawCreativeTabHoveringText(text, x, y);
	}

	@Override
	public void displayTooltips(List<String> list, int xAxis, int yAxis) {
		func_146283_a(list, xAxis, yAxis);
	}

	@Override
	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}
}