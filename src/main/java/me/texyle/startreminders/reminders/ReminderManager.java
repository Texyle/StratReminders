package me.texyle.startreminders.reminders;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import me.texyle.startreminders.StratReminders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ReminderManager {
	private static String fileLocation = "StratReminders/reminders.json";
	private static ArrayList<Reminder> reminderList = new ArrayList();
	
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if(StratReminders.showKey.isKeyDown()) {
			for (Reminder reminder : reminderList) {
				reminder.render(event.partialTicks);
			}
		}
	}
	
	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent event) {
	    if(StratReminders.createKey.isKeyDown()) {
	    	Minecraft minecraft = Minecraft.getMinecraft();
	    	EntityPlayerSP player = minecraft.thePlayer;
	    	WorldClient world = minecraft.theWorld;
	    		    	
	    	player.openGui(StratReminders.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
	    } else if (StratReminders.editKey.isKeyDown()) {
	    	Minecraft minecraft = Minecraft.getMinecraft();
	    	EntityPlayerSP player = minecraft.thePlayer;
	    	WorldClient world = minecraft.theWorld;
	    	
	    	player.openGui(StratReminders.instance, 2, world, (int) player.posX, (int) player.posY, (int) player.posZ);
	    }
	}
	
	public static void createReminder(ArrayList<String> lines, int x, int y, int z) {
		Reminder reminder = new Reminder(lines, x, y+2, z);
		reminderList.add(reminder);
		saveToFile();
		
		String str = EnumChatFormatting.DARK_AQUA + "[StratReminders] ";
		str += EnumChatFormatting.AQUA + "Reminder saved at coordinates: ";
		str += EnumChatFormatting.GRAY + "[" + x + ", " + y + ", " + z + "]";
		ChatComponentText message = new ChatComponentText(str);
		Minecraft.getMinecraft().thePlayer.addChatMessage(message);
	}
	
	public static void saveToFile() {
		GsonBuilder builder = new GsonBuilder(); 
		builder.setPrettyPrinting(); 
		Gson gson = builder.create();
		
		String jsonString = gson.toJson(reminderList);
		
		try {
			File file = new File(fileLocation);
			if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
			file.createNewFile();
			
			FileWriter fw = new FileWriter(fileLocation);
			fw.write(jsonString);
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadFromFile() {
		Gson gson = new Gson();
		
		Type listType = new TypeToken<ArrayList<Reminder>>(){}.getType();
		
		try {
			File file = new File(fileLocation);
			if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
			if (!file.createNewFile())
				reminderList = gson.fromJson(new FileReader(fileLocation), listType);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
}
