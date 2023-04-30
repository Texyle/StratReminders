package me.texyle.startreminders.gui;

import java.io.IOException;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiEditReminders extends GuiCreateReminder {
	private GuiButton buttonLeft;
	private GuiButton buttonRight;
	
	@Override
	public void initGui() {
		super.initGui();
		
		titleText = "  Edit reminders  ";
		
		buttonLeft = new GuiButton(9, 0, this.height/14*10, "<");
		buttonLeft.setWidth(24);
		buttonLeft.xPosition = saveButton.xPosition + (saveButton.width/7*2);
		buttonList.add(buttonLeft);
		buttonRight = new GuiButton(10, 20, this.height/14*10, ">");
		buttonRight.setWidth(24);
		buttonRight.xPosition = saveButton.xPosition + (saveButton.width/7*5) - buttonRight.width;
		buttonList.add(buttonRight);
		
		saveButton.yPosition = this.height/14*11;
	}
	
	@Override
	public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
		this.drawDefaultBackground();
		super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
		
		String numStr = "1/100";
		int w = fontRendererObj.getStringWidth(numStr);
		int posX = saveButton.xPosition + (int) (saveButton.width/7*3) + (saveButton.width/7 - w) / 2;
		fontRendererObj.drawString(numStr, posX, buttonLeft.yPosition+6, 0xFFFFFF, true);
	}
	
	@Override
	protected void keyTyped(char p_keyTyped_1_, int p_keyTyped_2_) throws IOException {
		super.keyTyped(p_keyTyped_1_, p_keyTyped_2_);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
	}
	
	@Override
	protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
		// TODO Auto-generated method stub
		super.actionPerformed(p_actionPerformed_1_);
	}
	
	@Override
	protected void mouseClicked(int p_mouseClicked_1_, int p_mouseClicked_2_, int p_mouseClicked_3_)
			throws IOException {
		super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_2_, p_mouseClicked_3_);
	}
	
	@Override
	public void drawDefaultBackground() {
		super.drawDefaultBackground();
	}
}
