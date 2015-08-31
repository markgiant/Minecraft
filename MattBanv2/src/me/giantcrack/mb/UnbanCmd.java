 package me.giantcrack.mb;

 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.OfflinePlayer;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;

 public class UnbanCmd implements CommandExecutor
 {
   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
   {
     if ((cmd.getName().equalsIgnoreCase("unban")) || (cmd.getName().equalsIgnoreCase("pardon")))
     {
       if (!sender.hasPermission("mattban.staff"))
       {
         sender.sendMessage(Main.prefix + ChatColor.DARK_RED + "No Permission!");
         return true;
       }
       if ((args.length == 0) || (args.length > 1))
       {
         sender.sendMessage(Main.prefix + ChatColor.GREEN + "/" + cmd.getName() + " <username>");
         return true;
       }
       OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
       if (BanManager.getInstance().getBan(op) != null)
       {
         BanManager.getInstance().unbanPlayer(op);
         Bukkit.broadcast(Main.prefix + ChatColor.WHITE + op.getName() + " has been unbanned by " + sender.getName() + "!", "mattban.staff");
         return true;
       }
       if (TempbanManager.getInstance().getBan(op) != null)
       {
         TempbanManager.getInstance().unbanPlayer(op);
         Bukkit.broadcast(Main.prefix + ChatColor.WHITE + op.getName() + " has been unbanned by " + sender.getName() + "!", "mattban.staff");
         return true;
       }
       sender.sendMessage(Main.prefix + ChatColor.RED + "That player is not banned!");
       return true;
     }
     return false;
   }
 }

