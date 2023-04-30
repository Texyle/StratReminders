package me.texyle.startreminders.reminders;

import java.util.ArrayList;

import me.texyle.startreminders.utils.DrawUtils;
import net.minecraft.util.BlockPos;

public class Reminder {
	public ArrayList<String> lines;
	public int posX;
	public int posY;
	public int posZ;
	
	public Reminder(ArrayList<String> lines, int x, int y, int z) {
		this.lines = new ArrayList(lines);
		posX = x;
		posY = y;
		posZ = z;
	}
	
	public void render(float partialTicks) {
		BlockPos blockPos = new BlockPos(posX, posY, posZ);
		DrawUtils.drawTextAtCoords(lines, blockPos, partialTicks);
	}
}
