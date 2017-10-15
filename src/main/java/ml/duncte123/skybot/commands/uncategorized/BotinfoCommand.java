/*
 *
 * MIT License
 *
 * Copyright (c) 2017 Duncan Sterken
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ml.duncte123.skybot.commands.uncategorized;

import com.sedmelluq.discord.lavaplayer.tools.PlayerLibrary;
import com.sun.management.OperatingSystemMXBean;
import ml.duncte123.skybot.objects.command.Command;
import ml.duncte123.skybot.utils.EmbedUtils;
import ml.duncte123.skybot.utils.Settings;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.lang.management.ManagementFactory;
import java.sql.Time;
import java.text.DecimalFormat;

/**
 * Created by Duncan on 11-7-2017.
 */
public class BotinfoCommand extends Command {

    /**
     * This is the executeCommand of the command, the thing you want the command to to needs to be in here
     * @param args The command agruments
     * @param event a instance of {@link net.dv8tion.jda.core.events.message.MessageReceivedEvent MessageReceivedEvent}
     */
    @Override
    public void executeCommand(String[] args, GuildMessageReceivedEvent event) {
        User u = event.getJDA().getSelfUser();

        String OS = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getName();
        OS = OS + " " + ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getArch() + " " + ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getVersion();
        String cpu0 = new DecimalFormat("###.###%").format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getProcessCpuLoad());
        String cpu2 = new DecimalFormat("###.###%").format(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getSystemCpuLoad());
        int cpu1 = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
        long ram0 = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1000000;
        long ram1 = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax() / 1000000;
        long up = ManagementFactory.getRuntimeMXBean().getUptime();
        Time upp = new Time(up - 3600000);

        MessageEmbed eb = EmbedUtils.defaultEmbed()
                .setDescription("Here is some information about me \uD83D\uDE09")
                .setThumbnail(u.getEffectiveAvatarUrl())
                .addField("General info", "**Creator:** duncte123#1245\n" +
                        "**Invite:** [You can invite me by clicking here](https://bots.discord.pw/bots/210363111729790977)\n" +
                        "**Github:** [https://github.com/duncte123/SkyBot](https://github.com/duncte123/SkyBot)\n" +
                        "**Guilds:** " + event.getJDA().asBot().getShardManager().getGuildCache().size() + "\n" +
                        "**Bot version:** " + Settings.version, true)
                .addField("System info", "**Operating System:** " + OS + "\n" +
                        "**Uptime:** " + upp.toString() + "\n" +
                        "**Ram:** "  + ram0 +"MB/" + ram1 + "MB\n" +
                        "**CPU Usage:** " + cpu0 + " / " + cpu2 + " (" + cpu1 +" Cores)" , false)
                .addField("Lib info", "JDA version: " + JDAInfo.VERSION + "\nLavaPlayer version: " + PlayerLibrary.VERSION, false)
                .addField("Donate", "If you want to help me out and support the bot please consider to [donate](https://paypal.me/duncte123) any amount.", false)
                .build();
        sendEmbed(event, eb);
    }

    /**
     * The usage instructions of the command
     * @return a String
     */
    @Override
    public String help() {
        return "Gets some info about the bot\nUsage: `"+ Settings.prefix+getName()+"`";
    }

    @Override
    public String getName() {
        return "botinfo";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"about"};
    }
}
