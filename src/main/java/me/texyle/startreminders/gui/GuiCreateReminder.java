package me.texyle.startreminders.gui;

import java.io.IOException;
import java.util.ArrayList;

import me.texyle.startreminders.reminders.ReminderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

public class GuiCreateReminder extends GuiScreen {
	private int maxLineLength = 40;
	private int textLineWidth = 200;
	private int offset = 0;
	protected GuiButton saveButton;
	private GuiTextField textLine1;
	private GuiTextField textLine2;
	private GuiTextField textLine3;
	private GuiTextField textLine4;
	private GuiTextField textLine5;
	private GuiTextField textX;
	private GuiTextField textY;
	private GuiTextField textZ;
	protected String titleText = "  Create reminder  ";
		
	@Override
	public void initGui() {
		super.initGui();
		
		saveButton = new GuiButton(5, 0, this.height/14*10, "Save");
		saveButton.xPosition = (this.width - saveButton.width) / 2;
		buttonList.add(saveButton);
		
		createTextLines();
	}
	
	@Override
	public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {		
		this.drawDefaultBackground();
		super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(3, 3, 3);
		int width = fontRendererObj.getStringWidth(titleText)*3;
		fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + titleText, (this.width-width)/6, this.height/15, 0xFFFFFF, true);
		GlStateManager.popMatrix();
						
		fontRendererObj.drawString("Line 1: ", textLine1.xPosition-40, textLine1.yPosition+6, 0xFFFFFF, true);
		fontRendererObj.drawString("Line 2: ", textLine2.xPosition-40, textLine2.yPosition+6, 0xFFFFFF, true);
		fontRendererObj.drawString("Line 3: ", textLine3.xPosition-40, textLine3.yPosition+6, 0xFFFFFF, true);
		fontRendererObj.drawString("Line 4: ", textLine4.xPosition-40, textLine4.yPosition+6, 0xFFFFFF, true);
		fontRendererObj.drawString("Line 5: ", textLine5.xPosition-40, textLine5.yPosition+6, 0xFFFFFF, true);
		fontRendererObj.drawString("Location: ", textLine5.xPosition, textX.yPosition+6, 0xFFFFFF, true);
		
		textLine1.drawTextBox();
		textLine2.drawTextBox();
		textLine3.drawTextBox();
		textLine4.drawTextBox();
		textLine5.drawTextBox();
		
		textX.drawTextBox();
		textY.drawTextBox();
		textZ.drawTextBox();
	}
	
	private void createTextLines() {
		int xPos = (this.width-textLineWidth)/2;
		int y = this.height/14;
		
		FontRenderer fontRendererObj = mc.getMinecraft().fontRendererObj;
		
		textLine1 = new GuiTextField(0, fontRendererObj, xPos, y*4, textLineWidth, 20);
		textLine1.setMaxStringLength(maxLineLength);
		textLine1.setFocused(true);
		
		textLine2 = new GuiTextField(1, fontRendererObj, xPos, y*5, textLineWidth, 20);
		textLine2.setMaxStringLength(maxLineLength);
		
		textLine3 = new GuiTextField(2,fontRendererObj, xPos, y*6, textLineWidth, 20);
		textLine3.setMaxStringLength(maxLineLength);
		
		textLine4 = new GuiTextField(3, fontRendererObj, xPos, y*7, textLineWidth, 20);
		textLine4.setMaxStringLength(maxLineLength);
		
		textLine5 = new GuiTextField(4, fontRendererObj, xPos, y*8, textLineWidth, 20);
		textLine5.setMaxStringLength(maxLineLength);
		
		EntityPlayerSP player = mc.getMinecraft().thePlayer;
		
		textX = new GuiTextField(6, fontRendererObj, xPos+textLineWidth/5*2-28, y*9, textLineWidth/5, 20);
		textX.setText(Integer.toString((int) player.posX));
		textY = new GuiTextField(7, fontRendererObj, xPos+textLineWidth/5*3-14, y*9, textLineWidth/5, 20);
		textY.setText(Integer.toString((int) player.posY));
		textZ = new GuiTextField(8, fontRendererObj, xPos+textLineWidth/5*4, y*9, textLineWidth/5, 20);
		textZ.setText(Integer.toString((int) player.posZ));
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		textLine1.textboxKeyTyped(typedChar, keyCode);
		textLine2.textboxKeyTyped(typedChar, keyCode);
		textLine3.textboxKeyTyped(typedChar, keyCode);
		textLine4.textboxKeyTyped(typedChar, keyCode);
		textLine5.textboxKeyTyped(typedChar, keyCode);
		
		if (Character.isDigit(typedChar)) {
			textX.textboxKeyTyped(typedChar, keyCode);
			textY.textboxKeyTyped(typedChar, keyCode);
			textZ.textboxKeyTyped(typedChar, keyCode);
		}
		
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void updateScreen() {
		textLine1.updateCursorCounter();
		textLine2.updateCursorCounter();
		textLine3.updateCursorCounter();
		textLine4.updateCursorCounter();
		textLine5.updateCursorCounter();
		textX.updateCursorCounter();
		textY.updateCursorCounter();
		textZ.updateCursorCounter();
		
		super.updateScreen();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 5) {
			ArrayList<String> lines = new ArrayList();
			if (textLine1.getText().length() > 0) lines.add(textLine1.getText());
			if (textLine2.getText().length() > 0) lines.add(textLine2.getText());
			if (textLine3.getText().length() > 0) lines.add(textLine3.getText());
			if (textLine4.getText().length() > 0) lines.add(textLine4.getText());
			if (textLine5.getText().length() > 0) lines.add(textLine5.getText());
						
			int x = Integer.parseInt(textX.getText());
			int y = Integer.parseInt(textY.getText());
			int z = Integer.parseInt(textZ.getText());
			
			if (lines.size() > 0)
				ReminderManager.createReminder(lines, x, y, z);
			
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		
		super.actionPerformed(button);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		textLine1.mouseClicked(mouseX, mouseY, mouseButton);
		textLine2.mouseClicked(mouseX, mouseY, mouseButton);
		textLine3.mouseClicked(mouseX, mouseY, mouseButton);
		textLine4.mouseClicked(mouseX, mouseY, mouseButton);
		textLine5.mouseClicked(mouseX, mouseY, mouseButton);
		textX.mouseClicked(mouseX, mouseY, mouseButton);
		textY.mouseClicked(mouseX, mouseY, mouseButton);
		textZ.mouseClicked(mouseX, mouseY, mouseButton);
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void drawDefaultBackground() {
		super.drawDefaultBackground();
	}
}
