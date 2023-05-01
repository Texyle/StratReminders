package me.texyle.startreminders.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import me.texyle.startreminders.reminders.Reminder;
import me.texyle.startreminders.reminders.ReminderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class GuiEditReminders extends GuiCreateReminder {
	private GuiButton buttonLeft;
	private GuiButton buttonRight;
	private GuiButton buttonSort;
	private GuiButton buttonDelete;
	private ArrayList<Reminder> sortedReminderList;
	private Reminder selectedReminder;
	private int selectedIndex = 1;
	String numStr;
	String sortBy = "Last created";
	
	public GuiEditReminders() {
		sortedReminderList = new ArrayList<>(ReminderManager.getReminderList());
		
		if (sortedReminderList.size() == 0) {
			Minecraft.getMinecraft().displayGuiScreen(null);
			return;
		}
		
		sortReminders();
		selectedIndex = 1;
		numStr = selectedIndex + "/";
		numStr += sortedReminderList.size();
		selectedReminder = sortedReminderList.get(0);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		if (sortedReminderList.size() == 0) {
			Minecraft.getMinecraft().displayGuiScreen(null);
			return;
		}
		
		titleText = "  Edit reminders  ";
		
		buttonLeft = new GuiButton(9, 0, this.height/14*10, "<");
		buttonLeft.setWidth(24);
		buttonLeft.xPosition = saveButton.xPosition + (saveButton.width/7*2);
		buttonList.add(buttonLeft);
		buttonRight = new GuiButton(10, 20, this.height/14*10, ">");
		buttonRight.setWidth(24);
		buttonRight.xPosition = saveButton.xPosition + (saveButton.width/7*5) - buttonRight.width;
		buttonList.add(buttonRight);
		saveButton.id = 11;
		buttonSort = new GuiButton(12, textLine1.xPosition+textLine1.width+20, textLine1.yPosition, "Sorting: " + EnumChatFormatting.GREEN + sortBy);
		buttonSort.setWidth(120);
		buttonList.add(buttonSort);
		buttonDelete = new GuiButton(13, saveButton.xPosition, this.height/14*11+24, EnumChatFormatting.RED + "Delete");
		buttonList.add(buttonDelete);
		
		saveButton.yPosition = this.height/14*11;	
		
		fillTextLines();
	}
	
	@Override
	public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
		if (sortedReminderList.size() == 0) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		
		this.drawDefaultBackground();
		super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
		
		int w = fontRendererObj.getStringWidth(numStr);
		int posX = saveButton.xPosition + (int) (saveButton.width/7*3) + (saveButton.width/7 - w) / 2;
		fontRendererObj.drawString(numStr, posX, buttonLeft.yPosition+6, 0xFFFFFF, true);
	}
	
	private void fillTextLines() {
		ArrayList<String> lines = selectedReminder.lines;
		
		textLine1.setText("");
		textLine2.setText("");
		textLine3.setText("");
		textLine4.setText("");
		textLine5.setText("");
		
		// lmao
		if (lines.size() > 0) {
			textLine1.setText(lines.get(0));
			if (lines.size() > 1) {
				textLine2.setText(lines.get(1));
				if (lines.size() > 2) {
					textLine3.setText(lines.get(2));
					if (lines.size() > 3) {
						textLine4.setText(lines.get(3));
						if (lines.size() > 4) {
							textLine5.setText(lines.get(4));
						}
					}
				}
			}
		}
		
		textX.setText("" + selectedReminder.posX);
		textY.setText("" + selectedReminder.posY);
		textZ.setText("" + selectedReminder.posZ);
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
	protected void actionPerformed(GuiButton button) throws IOException {
		
		if (button.id == buttonLeft.id) {
			if (selectedIndex > 1) {
				selectedReminder = sortedReminderList.get(--selectedIndex-1);
				numStr = selectedIndex + "/" + sortedReminderList.size();
				fillTextLines();
			}
		} else if (button.id == buttonRight.id) {
			if (selectedIndex < sortedReminderList.size()) {
				selectedReminder = sortedReminderList.get(++selectedIndex-1);
				numStr = selectedIndex + "/" + sortedReminderList.size();
				fillTextLines();
			}
		} else if (button.id == saveButton.id) {
			updateReminder();
		} else if (button.id == buttonSort.id) {
			if (sortBy == "Nearest") {
				sortBy = "Last created";
			} else if (sortBy == "Last created") {
				sortBy = "Nearest";
			}
			sortReminders();
			
			selectedIndex = 1;
			numStr = selectedIndex + "/";
			numStr += sortedReminderList.size();
			selectedReminder = sortedReminderList.get(0);
			fillTextLines();
			buttonSort.displayString = "Sorting: " + EnumChatFormatting.GREEN + sortBy;
		} else if (button.id == buttonDelete.id) {
			ReminderManager.deleteReminder(selectedReminder);
			
			if (sortedReminderList.size() == 1) {
				Minecraft.getMinecraft().displayGuiScreen(null);
				return;
			}
			
			sortReminders();
			selectedIndex = 1;
			numStr = selectedIndex + "/";
			numStr += sortedReminderList.size();
			selectedReminder = sortedReminderList.get(0);
			fillTextLines();
		}
		
		super.actionPerformed(button);
	}
	
	private void sortReminders() {
		sortedReminderList = new ArrayList<>(ReminderManager.getReminderList());
		
		if (sortBy == "Nearest") {			
			EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
			
			int posX = (int) player.posX;
			int posY = (int) player.posY;
			int posZ = (int) player.posZ;
			
			int n = sortedReminderList.size()-1;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n-i; j++) {
					Reminder r1 = sortedReminderList.get(j);
					Reminder r2 = sortedReminderList.get(j+1);
					if (player.getDistance(r1.posX, r1.posY, r1.posZ) > player.getDistance(r2.posX, r2.posY, r2.posZ)) {
						Collections.swap(sortedReminderList, j, j+1);
					}
				}
			}
			
		} else if (sortBy == "Last created") {
			Collections.reverse(sortedReminderList);
		}
	}
	
	private void updateReminder() {
		ArrayList<String> lines = new ArrayList();
		if (textLine1.getText().length() > 0) lines.add(textLine1.getText());
		if (textLine2.getText().length() > 0) lines.add(textLine2.getText());
		if (textLine3.getText().length() > 0) lines.add(textLine3.getText());
		if (textLine4.getText().length() > 0) lines.add(textLine4.getText());
		if (textLine5.getText().length() > 0) lines.add(textLine5.getText());
					
		int x = Integer.parseInt(textX.getText());
		int y = Integer.parseInt(textY.getText());
		int z = Integer.parseInt(textZ.getText());
		
		selectedReminder.lines = lines;
		selectedReminder.posX = x;
		selectedReminder.posY = y;
		selectedReminder.posZ = z;
		
		String str = EnumChatFormatting.DARK_AQUA + "[StratReminders] ";
		str += EnumChatFormatting.AQUA + "Reminder updated";
		ChatComponentText message = new ChatComponentText(str);
		Minecraft.getMinecraft().thePlayer.addChatMessage(message);
		
		Minecraft.getMinecraft().displayGuiScreen(null);
		
		ReminderManager.saveToFile();
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
