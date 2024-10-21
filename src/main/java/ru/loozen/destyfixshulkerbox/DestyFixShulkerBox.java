package ru.loozen.destyfixshulkerbox;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.FileConfiguration;

public class DestyFixShulkerBox extends JavaPlugin implements Listener {

    private String noShulkerMessage;

    @Override
    public void onEnable() {
        // Регистрация слушателя событий
        Bukkit.getPluginManager().registerEvents(this, this);

        // Сохранение конфигурации с значениями по умолчанию
        saveDefaultConfig(); // Создает config.yml, если его нет

        // Загрузка сообщения из конфигурации
        loadConfig();
    }

    private void loadConfig() {
        // Загрузка сообщения из конфигурации
        FileConfiguration config = getConfig(); // Получаем актуальную конфигурацию
        noShulkerMessage = config.getString("no-shulker-message", "§cВы не можете складывать шалкеры в эндер-сундук.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("destyfixshulkerbox") || command.getName().equalsIgnoreCase("dfsb")) {
            if (sender.hasPermission("destyfixshulkerbox.reload")) {
                reloadConfig(); // Перезагрузка конфигурации
                loadConfig(); // Обновляем значения после перезагрузки
                sender.sendMessage("§aКонфигурация успешно перезагружена.");
                return true;
            } else {
                sender.sendMessage("§cУ вас нет прав для выполнения этой команды.");
            }
        } else if (command.getName().equalsIgnoreCase("reload")) {
            if (sender.hasPermission("destyfixshulkerbox.reload")) {
                reloadConfig(); // Перезагрузка конфигурации
                loadConfig(); // Обновляем значения после перезагрузки
                sender.sendMessage("§aКонфигурация успешно перезагружена.");
                return true;
            } else {
                sender.sendMessage("§cУ вас нет прав для выполнения этой команды.");
            }
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Проверяем, что инвентарь - это эндер-сундук
        if (event.getInventory().getType() == InventoryType.ENDER_CHEST) {
            // Проверяем, что игрок - это не оператор
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if (!player.isOp()) {
                    // Проверяем, что игрок пытается положить шалкер в сундук
                    ItemStack currentItem = event.getCurrentItem();
                    if (currentItem != null && isShulkerBox(currentItem.getType())) {
                        // Отменяем действие
                        event.setCancelled(true);
                        player.sendMessage(noShulkerMessage); // Используем сообщение из конфигурации
                    }
                }
            }
        }
    }

    // Метод для проверки, является ли материал шалкером
    private boolean isShulkerBox(Material material) {
        return material == Material.SHULKER_BOX || material == Material.WHITE_SHULKER_BOX ||
                material == Material.ORANGE_SHULKER_BOX || material == Material.MAGENTA_SHULKER_BOX ||
                material == Material.LIGHT_BLUE_SHULKER_BOX || material == Material.YELLOW_SHULKER_BOX ||
                material == Material.LIME_SHULKER_BOX || material == Material.PINK_SHULKER_BOX ||
                material == Material.GRAY_SHULKER_BOX || material == Material.LIGHT_GRAY_SHULKER_BOX ||
                material == Material.CYAN_SHULKER_BOX || material == Material.PURPLE_SHULKER_BOX ||
                material == Material.BLUE_SHULKER_BOX || material == Material.BROWN_SHULKER_BOX ||
                material == Material.GREEN_SHULKER_BOX || material == Material.RED_SHULKER_BOX ||
                material == Material.BLACK_SHULKER_BOX;
    }
}