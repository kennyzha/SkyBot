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

package ml.duncte123.skybot.commands.music;

import ml.duncte123.skybot.audio.GuildMusicManager;
import ml.duncte123.skybot.objects.command.Command;
import ml.duncte123.skybot.utils.AirUtils;
import ml.duncte123.skybot.utils.AudioUtils;
import ml.duncte123.skybot.utils.Settings;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class PPlayCommand extends Command {

    public final static String help = "add a playlist to the queue.";

    /**
     * This is the executeCommand of the command, the thing you want the command to to needs to be in here
     * @param args The command agruments
     * @param event a instance of {@link net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent GuildMessageReceivedEvent}
     */
    @Override
    public void executeCommand(String[] args, GuildMessageReceivedEvent event) {

        if(!event.getGuild().getAudioManager().isConnected()){
            sendMsg(event, "I'm not in a voice channel, use `"+ Settings.prefix+"join` to make me join a channel");
            return;
        }

        if(!event.getGuild().getAudioManager().getConnectedChannel().getMembers().contains(event.getMember())){
            sendMsg(event, "I'm sorry, but you have to be in the same channel as me to use any music related commands");
            return;
        }

        if(args.length < 1){
            sendMsg(event, "To few arguments, use `"+ Settings.prefix+"pplay <media link>`");
            return;
        }

        AudioUtils au = AirUtils.audioUtils;

        Guild guild = event.getGuild();
        GuildMusicManager mng = au.getMusicManager(guild);

        String toPlay = StringUtils.join(Arrays.asList(args), " ");
        if(!AirUtils.isURL(toPlay)){
            toPlay = "ytsearch: " + toPlay;
        }

        au.loadAndPlay(mng, event.getChannel(), toPlay, true);

    }

    /**
     * The usage instructions of the command
     * @return a String
     */
    @Override
    public String help() {
        // TODO Auto-generated method stub
        return help;
    }

    @Override
    public String getName() {
        return "pplay";
    }

}
