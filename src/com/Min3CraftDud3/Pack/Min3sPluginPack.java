package com.Min3CraftDud3.Pack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Min3sPluginPack extends JavaPlugin implements Listener {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onGPUse(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		try {
			if ((e.getAction() == Action.RIGHT_CLICK_AIR)
					|| (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				if (p.getHealth() <= 6.0) {
					if (p.getItemInHand().getType() == Material.SULPHUR) {
						p.setHealth(p.getMaxHealth());
						p.getItemInHand().setAmount(
								p.getItemInHand().getAmount() - 1);
						p.sendMessage("You used PhoenixPowder.");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (e.getAction() == Action.RIGHT_CLICK_AIR
					|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (p.getItemInHand().getType().equals(Material.SUGAR)) {
					p.getItemInHand().setAmount(
							p.getItemInHand().getAmount() - 1);
					p.addPotionEffect(new PotionEffect(
							PotionEffectType.INVISIBILITY, 400, 1));
					p.sendMessage("You have used Invisibility Powder.");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		if (p.getKiller() instanceof Player) {
			Player killer = e.getEntity().getKiller();
			if (e.getDeathMessage().contains("burned")) {
				killer.getInventory().addItem(
						new ItemStack(Material.SULPHUR, 1));
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		try {
			Player p = e.getPlayer();
			if (p != null
					&& p.getInventory().getBoots().getType() == Material.GOLD_BOOTS) {
				p.setAllowFlight(true);
			} else {
				p.setAllowFlight(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@EventHandler
	public void onGSKill(EntityDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p1 = (Player) e.getEntity();
			if (p1.getKiller() instanceof Player) {
				final Player p2 = (Player) p1.getKiller();
				if (p2.getItemInHand().getType() == Material.GOLD_SWORD) {
					new BukkitRunnable() {
						int seconds = 5;

						public void run() {
							p2.sendMessage(seconds + ".");
							seconds--;
							if (seconds <= 0) {
								p2.teleport(getClosestPlayer(p2, 200.0));
								cancel();
							}
						}
					}.runTaskTimer(this, 0, 20);
				}
			}
		}
	}

	public Player getClosestPlayer(Player player, double radius) {
		double minimalDistance = Math.pow(radius, 2);
		double curDist;
		Player closest = null;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!p.equals(player)) {
				curDist = player.getLocation().distanceSquared(p.getLocation());
				if (curDist < minimalDistance) {
					minimalDistance = curDist;
					closest = p;
				}
			}
		}
		return closest;
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		e.setDroppedExp(0);
	}
}
