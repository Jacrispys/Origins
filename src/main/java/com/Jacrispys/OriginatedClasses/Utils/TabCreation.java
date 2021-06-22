package com.Jacrispys.OriginatedClasses.Utils;

import com.Jacrispys.OriginatedClasses.API.TabAPI;
import com.Jacrispys.OriginatedClasses.Files.ClassData;
import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.chat;

public class TabCreation extends TabAPI implements Listener {

    private OriginatedClassesMain plugin;

    public TabCreation(OriginatedClassesMain plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public EntityPlayer ep = createPlayers("00", chat("     &a&lPlayers &f(" + Bukkit.getOnlinePlayers().size() + ")"), "ewogICJ0aW1lc3RhbXAiIDogMTYwMjg1MDUxNTc1NiwKICAicHJvZmlsZUlkIiA6ICJmNjE1NzFmMjY1NzY0YWI5YmUxODcyMjZjMTEyYWEwYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJGZWxpeF9NYW5nZW5zZW4iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY2NWViYWVlYzAxNzBjODU1NjIwOThjOGM4ZTczYjI4MGE2ZjkyNzRhN2M2NTA5ZTYyODhjMGFhZmE3MmRlMSIKICAgIH0KICB9Cn0=", "qqd6gRWTese1v+7JuA2bv52/307wTfWogHo+yeg9n00SxLTHBrkcWrYHcwXeXiQXI3szoT9VZy1PiyuCCx33vmCqR/2WXGyUOI1c00lzhXbqCvB/AKRN7wQKtZnJi4urV986LYYiNpeK/RI8Z9qy/N9Jw5PJj/K+DKQdA8DCy8rf73EKTEfxht3g/ITI/2rRXpMlen9V2j7GAUwAq7jlXLz0ZmAnpt7C682uCCp+vOaI3ebX9GH6BYw47mGzrD8RDxcBFBCUquhIOKfdCuJcIGjqvM03Ilx8Y8ZbxaNYsi4Ij+BIXN5JhboceVaP/hAXqw/+jnVD8yYDhfIrTlWc4cVaep1l1/5hawcRsWSQ5FXFc1+lSiNMJ0FzpRG+aBJD/u8t6M5b8CX1RWk8c4dxSwU3U/3LmisS5GZ7zNxgBKV7jPqXJcbb0HNV6v03MxGGpmxml5AFzrF7HnzDldh3kJPdjVlGHU6t5Am0RfRCYpKcCdQIS+qhbXxUV4UiQnAjnHmecZmWwKFTlpNd3u4kPQllafFqQkJ6xDHxiAyNYfEJaBz5STwJ0o1ZRKlTmT3ICIB8vqR76CPiHJj1mG+kvaTHcCflpllb4b/aBOklUVuO3CFYETXd9oD2VU80bU8r+agy7qIHIDMEZ+kaDfFAvOBnCv9IVghxpU+gw7Lubvc=");
    public static HashMap<String, EntityPlayer> removablePlayerList = new HashMap<>();
    public static HashMap<UUID, EntityPlayer> logout = new HashMap<>();

    @EventHandler
    public void tabHandler(PlayerJoinEvent e) {
        removePlayer(e.getPlayer());
        headerText(chat("&bYou are playing on &e&lPREPGAMES.US.TO \n"), chat("\n&aFor more information and the store, visit: &c&lSTORE.PREPGAMES.US.TO\n"), e.getPlayer());
        for (int i = 0; i < 80; i++) {
                if (i != 0 && i != 20 && i != 40 && i != 60) {
                   if(i < 21) {
                        if(i < 10) {
                            EntityPlayer removablePlayer = createPlayers("00" + i, chat(""),
                                    "eyJ0aW1lc3RhbXAiOjE1MjY2MTI4ODk4MjIsInByb2ZpbGVJZCI6ImIwZDczMmZlMDBmNzQwN2U5ZTdmNzQ2MzAxY2Q5OGNhIiwicHJvZmlsZU5hbWUiOiJPUHBscyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U3NzYxODIxMjUyOTc5OGM3MTliNzE2MmE0NzNhNjg1YzQzNTczMjBhODY5NjE2NWU3OTY3OTBiOTBmYmE2NyJ9fX0=",
                                    "KTnUMhshvKQ+4SYIxvHcu7kTrqw9It+v1oTjW7Xr3aGAGVwNH+z5PLCAY87UOytgoUUAI/mKAIAR8LzOb/x+z84v5lkVCr9pLV9zrzEZTC+jbzkwOMZ5nG1c4YMrh5p5sc7Abnm0dSE/3zLbH9Fev75HuTd9BgqqO1UjRM+sovuO+Gen4ZWRfliT+IFsALPO+3QCWoY2Cay9ZfPT4X7IJrX1GKfl0IiByVA9snyADH8LlwoNwAbe+v++1sy6G36xwrACdqQ9MLBMznpgUJbHlJmxuBsuioPymCAOaQUesRI3Yi053ZfABB+a7wU3tf9h0uNCUoWYr7w7e/N/3wDaphltH8A9MzDc9fFHjcen3+T4Ehl+0MKpY44eWTV6K22vQHuhO4h1c8ruvNTBlimTK27fc3uHhm9TL6ieXqf3UrSqA9bNqfnwHfFVdKXOMZ0cPPit7r6f3PmiVXteE+WijkN8PPzZtfEqU58jPKh3tAo4QzXYEgyGztY9NSGqCfvqBXMYIJgKgUPO3f5aUDmLwI2f1gZvWBsJ+VYHtMonBrIDg5U1bKsSzsQXNZZ+k55Zxe/1i8TEI4YsFGTYGco1UOd1KE+67XaQoPqAPyorNYhWeVmKSiGiHLhFt2RaE1mUf64pKTcyINyXmVlJKIMLIN4yvgAYFREAu/OA1GY6lt8="
                            );
                            removablePlayerList.put(e.getPlayer().getUniqueId() + "00" + i, removablePlayer);
                            addPlayers(e.getPlayer(),removablePlayer);
                        } else {
                            EntityPlayer removablePlayer = createPlayers("0" + i, chat(""),
                                    "eyJ0aW1lc3RhbXAiOjE1MjY2MTI4ODk4MjIsInByb2ZpbGVJZCI6ImIwZDczMmZlMDBmNzQwN2U5ZTdmNzQ2MzAxY2Q5OGNhIiwicHJvZmlsZU5hbWUiOiJPUHBscyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U3NzYxODIxMjUyOTc5OGM3MTliNzE2MmE0NzNhNjg1YzQzNTczMjBhODY5NjE2NWU3OTY3OTBiOTBmYmE2NyJ9fX0=",
                                    "KTnUMhshvKQ+4SYIxvHcu7kTrqw9It+v1oTjW7Xr3aGAGVwNH+z5PLCAY87UOytgoUUAI/mKAIAR8LzOb/x+z84v5lkVCr9pLV9zrzEZTC+jbzkwOMZ5nG1c4YMrh5p5sc7Abnm0dSE/3zLbH9Fev75HuTd9BgqqO1UjRM+sovuO+Gen4ZWRfliT+IFsALPO+3QCWoY2Cay9ZfPT4X7IJrX1GKfl0IiByVA9snyADH8LlwoNwAbe+v++1sy6G36xwrACdqQ9MLBMznpgUJbHlJmxuBsuioPymCAOaQUesRI3Yi053ZfABB+a7wU3tf9h0uNCUoWYr7w7e/N/3wDaphltH8A9MzDc9fFHjcen3+T4Ehl+0MKpY44eWTV6K22vQHuhO4h1c8ruvNTBlimTK27fc3uHhm9TL6ieXqf3UrSqA9bNqfnwHfFVdKXOMZ0cPPit7r6f3PmiVXteE+WijkN8PPzZtfEqU58jPKh3tAo4QzXYEgyGztY9NSGqCfvqBXMYIJgKgUPO3f5aUDmLwI2f1gZvWBsJ+VYHtMonBrIDg5U1bKsSzsQXNZZ+k55Zxe/1i8TEI4YsFGTYGco1UOd1KE+67XaQoPqAPyorNYhWeVmKSiGiHLhFt2RaE1mUf64pKTcyINyXmVlJKIMLIN4yvgAYFREAu/OA1GY6lt8="
                            );
                            removablePlayerList.put(e.getPlayer().getUniqueId() + "0" + i, removablePlayer);
                            addPlayers(e.getPlayer(),removablePlayer);
                        }

                    } else {
                        addPlayers(e.getPlayer(), createPlayers(String.valueOf(i), chat(""),
                                "eyJ0aW1lc3RhbXAiOjE1MjY2MTI4ODk4MjIsInByb2ZpbGVJZCI6ImIwZDczMmZlMDBmNzQwN2U5ZTdmNzQ2MzAxY2Q5OGNhIiwicHJvZmlsZU5hbWUiOiJPUHBscyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U3NzYxODIxMjUyOTc5OGM3MTliNzE2MmE0NzNhNjg1YzQzNTczMjBhODY5NjE2NWU3OTY3OTBiOTBmYmE2NyJ9fX0=",
                                "KTnUMhshvKQ+4SYIxvHcu7kTrqw9It+v1oTjW7Xr3aGAGVwNH+z5PLCAY87UOytgoUUAI/mKAIAR8LzOb/x+z84v5lkVCr9pLV9zrzEZTC+jbzkwOMZ5nG1c4YMrh5p5sc7Abnm0dSE/3zLbH9Fev75HuTd9BgqqO1UjRM+sovuO+Gen4ZWRfliT+IFsALPO+3QCWoY2Cay9ZfPT4X7IJrX1GKfl0IiByVA9snyADH8LlwoNwAbe+v++1sy6G36xwrACdqQ9MLBMznpgUJbHlJmxuBsuioPymCAOaQUesRI3Yi053ZfABB+a7wU3tf9h0uNCUoWYr7w7e/N/3wDaphltH8A9MzDc9fFHjcen3+T4Ehl+0MKpY44eWTV6K22vQHuhO4h1c8ruvNTBlimTK27fc3uHhm9TL6ieXqf3UrSqA9bNqfnwHfFVdKXOMZ0cPPit7r6f3PmiVXteE+WijkN8PPzZtfEqU58jPKh3tAo4QzXYEgyGztY9NSGqCfvqBXMYIJgKgUPO3f5aUDmLwI2f1gZvWBsJ+VYHtMonBrIDg5U1bKsSzsQXNZZ+k55Zxe/1i8TEI4YsFGTYGco1UOd1KE+67XaQoPqAPyorNYhWeVmKSiGiHLhFt2RaE1mUf64pKTcyINyXmVlJKIMLIN4yvgAYFREAu/OA1GY6lt8="
                                )
                        );
                    }
                } else {
                    switch (i) {
                        case 0:
                            addPlayers(e.getPlayer(), ep);
                            break;
                        case 20:
                            addPlayers(e.getPlayer(),
                                    createPlayers("20", chat("     &5&lSocial &cWIP"),
                                            "eyJ0aW1lc3RhbXAiOjE1NDczMzkwOTAzNDIsInByb2ZpbGVJZCI6ImMxZWQ5N2Q0ZDE2NzQyYzI5OGI1ODFiZmRiODhhMjFmIiwicHJvZmlsZU5hbWUiOiJ5b2xvX21hdGlzIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lYTMyMzhjYzBlM2M3Y2QxNjA5ZDI4NmI0ZjdjYTFhNTk2YTNiMjZhMjA3ZTQ5NGIxMzM5NThkZTJlZWRmN2VkIn19fQ==",
                                            "jkjCIfqeM27L57+ZPjFjDVaYg5B19xsa7yp88aZgGPUdGjNz4tFwEpOpiGmav66jLzVXzrH7eK/7hcJHPfBFmLlu8mZmgMXH9pl/ueoeuhnSB7EJHc64hJyYqIGh+/zC2rCtMrUfQWOv2yDBgPmZQ/1D+8RMCpOoAGumnkxrq2GExDfwJSVdZ8O6t9R4vRZ7+dJe16OP/rVr0cReU8MQSWVhHooubYyd5WAZRN0HJ5L5k3OI6uIPjOgbT9kM0AUPzQ2YTpq2Z6YQCvmvPyDYFHO8QcTN6BAurcPG5DeAzSG3vhWODLbjfuTJu0DXn4sZ5cfVUra6XurkAwrJA1ZlZU+ZsqOF2Hp2kZrw+LXNragcdVuaZz4aNiLpf5/7E2Obyb1zOLynxADyCWrOrRy539l2DUj3rue1AFM+ExH0eyUvhL3jzaj5RectfG31lN7TauFrXZahmN6e7WUNG3lyrxhKoOomrJQfy/tLAFMluS3j4CWaAjDqUsHqxNCBrQyM27vvn5j2OJK4M8Z3v2dodanEew9wMWdZCHm0mOx598C0tqNSBqP2FKAWUbjIhYOqDLXnauvByOyZdopbcz5Zz2W46bf0qR7r9y3mSUnms+pLozoQFBlxVm+gui7V2S90uNQroSRJJ0JcvMhRx5JnM2Z/yWRCbc++E9+ZGuN11ks=")
                            );
                            break;
                        case 40:
                            addPlayers(e.getPlayer(),
                                    createPlayers("40", chat("     &3&lSelected Class: &b" + ClassData.getClassStorage().get(e.getPlayer().getUniqueId() + " Class")),
                                            "eyJ0aW1lc3RhbXAiOjE1NDczMzg1MjkxNTIsInByb2ZpbGVJZCI6ImUzYjQ0NWM4NDdmNTQ4ZmI4YzhmYTNmMWY3ZWZiYThlIiwicHJvZmlsZU5hbWUiOiJNaW5pRGlnZ2VyVGVzdCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM4NjA1MTVkOTU4MDgwNjdmZDNmZGE0MmE3ZmFkZTc5MTlmY2UyNjAwMDBlZTFlYWQwYjAxNzA1N2VhZjk2In19fQ==",
                                            "Nd45zSjU5S1N0ZcaBjZvo2a97h1dtixQBcvKZ2PHWFdErg31iLMTst4htmIH7hVogaMECG9H+x1ukLWnudF2QNZC6kDlTIQzEMyERoBMDkSMpWVgJZO/UmJAncxmJWO8RMscumeiqGLHgM+wVPLzQIzB5JwJmbjTqgHzjqs0eDUdrdftiVcLdaYFXWuk1M98Ixr6+q37nIpLWheAWKqFnL+s42w38GAzkO3G1z8D1sUcR1JG3hFShzuGw10lfwzljolqdXp4xRDDjQY09WbHSH66hwivlPA4ejIaooralu6Q1GWn9wB+rbN8aAowrSPH57TqIXxQ2yfH3lhgkFXCLP1Lt9kbMN20HDnFQLH66VopFQXUlWlyYQGCnBbERSOH5aDSIVYTwZMFLHu52hOoyEfhHSSIdCrEpRkGTnmXCpLgIL3fTNdi9N5zvISol/FSMJAZRWAgsODSY9RhoWwZHU+F9tnto+9TjGsauX/qlEIa+XTcWprthXAMXnuF22gd9eCyFTvI0a3rWqY3tSlpn5wohhZCKklsuojBgQJoiDao9H6QE64lEiugmoB8Y1Id/OlA9lum5lyGQzvAfqoc334usujKm0MG2tPWYTo3J91ZwdRSMATXN1h449ObJybRU+TbwNZrQt6GJYeEKtdx2JIaDsl9ANOx3xMsT4jUSJQ=")
                            );
                            break;
                        case 60:
                            addPlayers(e.getPlayer(),
                                    createPlayers("60", chat("     &c&lMore Info Coming Soon..."),
                                            "ewogICJ0aW1lc3RhbXAiIDogMTYxNTMxNTk5ODIwMiwKICAicHJvZmlsZUlkIiA6ICIyNzc1MmQ2ZTUyYmM0MzVjYmNhOWQ5NzY1MjQ2YWNhNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJkZW1pbWVkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2U4NzM3MWE2YzA3NmU2YmZlMjRmZjYwMGE3YTkzNjRkNTJiOTQ1N2E2YmQyODhjZWRlNmUzYjM1ZjFkNjJmNTciLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==",
                                               "u/WkOgfNmrKDhQHd8ZUEXGP2T5dWY9aW80slkclthButy01+I3V1WPvhevju6MSRuCPIXGSoChL4CtwUfLLOy7Oa9EgjuiysOAFQ1StiPeovyauLF6KTHGExY5Q/aPBqt95xspu4D4iiBMolpOFDUUO134dkaw28WQZ4qM3KhcEUVLEZoRxg0DgQKNNEptZ5ymWrtMBjmqfrMLOYEvw9mhuwzxOSx1MiWfINVIWNOpgFjRFuznRiPCYl81Tn5eBJbUfGWlwUoq+tEi6OpGwXi7McfYfnep10T/cGItAbUeoSc+v5aIrV1zf10Mg0d8Kxrf4a/76u0e16yJHhwfGqaL2ama6IUZRjBxYLGRZr5iC7o+619HNCaJTfH2EZSvu3bqddqdEy8gby5YR2xO9A02CitBw1mMqpuUVQbdfphnPWUBuGzwNcr8jIPkQhYUE4UdLxd3LpsIqZ6eRhvhi4MFDxTQsHd2j6L4cNz3eTRfmomL2xfHvREL55l49u8GPn249CQJNg+1ttgeAbyVjCHwxswoqjzAbZagr5RTciCA4iUibdYa+V0/saXsMJQPOobgTjBVo/+2Pbz0RtT6Ac2XdnQiL/NH0C4fajN8+PiWbb0BIhcU9kpGNfhBjEGVqFQOKS6ppARX/jtf9kZaBJAdRa5ggjDjxjpdfXKREyU8k=")
                            );
                            break;
                        default:
                            break;
                    }

                }
            }
        for(Player players : Bukkit.getOnlinePlayers()) {
            // Player Count
            ep.listName = new ChatComponentText(chat("     &a&lPlayers &f(" + Bukkit.getOnlinePlayers().size() + ")"));
            PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ep.getBukkitEntity().getHandle());
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
        }
        newPlayerList(e.getPlayer());
        updatedPlayerList(e.getPlayer(), true);

    }

    @EventHandler
    public void disconnect(PlayerQuitEvent e) {
        for(Player players : Bukkit.getOnlinePlayers()) {
            ep.listName = new ChatComponentText(chat("     &a&lPlayers &f(" + (Bukkit.getOnlinePlayers().size() - 1) + ")"));
            PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ep.getBukkitEntity().getHandle());
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
        }
        updatedPlayerList(e.getPlayer(), false);
    }
}
